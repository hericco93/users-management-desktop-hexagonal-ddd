package com.jcaa.usersmanagement.infrastructure.adapter.out;

import com.jcaa.usersmanagement.application.port.out.ConsultasHotelPort;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLHotelAdapter implements ConsultasHotelPort {
    private final String URL = "jdbc:mysql://localhost:3306/safaris_hotels_db";
    private final String USER = "root";

    private final String PASSWORD = System.getenv("DB_PASSWORD");

    private Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    @Override
    public List<String> listarHoteles() {
        List<String> lista = new ArrayList<>();
        String sql = "SELECT * FROM hotel";
        try (Connection conn = conectar(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(rs.getString("id") + " | " + rs.getString("nombre") + " - " + rs.getInt("categoria") + " Estrellas");
            }
        } catch (SQLException e) { System.out.println("Error BD: " + e.getMessage()); }
        return lista;
    }

    @Override
    public List<String> listarHabitacionesDisponibles(String hotelId) {
        List<String> lista = new ArrayList<>();
        String sql = "SELECT * FROM habitacion WHERE hotel_id = ? AND disponible = true";
        try (Connection conn = conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, hotelId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                lista.add("Habitación " + rs.getString("numero") + " (" + rs.getString("tipo") + ") - $" + rs.getDouble("precio"));
            }
        } catch (SQLException e) { System.out.println("Error BD: " + e.getMessage()); }
        return lista;
    }

    @Override
    public List<String> obtenerReservasActivasPorCliente(String clienteId) {
        List<String> lista = new ArrayList<>();
        String sql = "SELECT * FROM reserva WHERE cliente_id = ? AND estado = 'ACTIVA'";
        try (Connection conn = conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, clienteId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                lista.add("Reserva " + rs.getString("id") + " | Hab: " + rs.getString("habitacion_id") + " | Del " + rs.getDate("fecha_inicio") + " al " + rs.getDate("fecha_fin"));
            }
        } catch (SQLException e) { System.out.println("Error BD: " + e.getMessage()); }
        return lista;
    }

    @Override
    public List<String> obtenerHistorialReservasCliente(String clienteId) {
        List<String> lista = new ArrayList<>();
        String sql = "SELECT * FROM reserva WHERE cliente_id = ?";
        try (Connection conn = conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, clienteId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                lista.add("Reserva " + rs.getString("id") + " | Estado: " + rs.getString("estado") + " | Del " + rs.getDate("fecha_inicio") + " al " + rs.getDate("fecha_fin"));
            }
        } catch (SQLException e) { System.out.println("Error BD: " + e.getMessage()); }
        return lista;
    }

    @Override
    public List<String> consultarEstanciasActivas(String hotelId) {
        List<String> lista = new ArrayList<>();
        String sql = "SELECT e.id AS estancia_id, r.cliente_id, h.numero " +
                "FROM estancia e " +
                "JOIN reserva r ON e.reserva_id = r.id " +
                "JOIN habitacion h ON r.habitacion_id = h.id " +
                "WHERE h.hotel_id = ? AND e.activa = true";
        try (Connection conn = conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, hotelId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                lista.add("Estancia " + rs.getString("estancia_id") + " | Habitación " + rs.getString("numero") + " ocupada por el cliente " + rs.getString("cliente_id"));
            }
        } catch (SQLException e) { System.out.println("Error BD: " + e.getMessage()); }
        return lista;
    }
}
