package com.jcaa.usersmanagement.infrastructure.adapter.in.gui;

import com.jcaa.usersmanagement.application.usecases.GestionConsultasUseCase;
import com.jcaa.usersmanagement.infrastructure.adapter.out.MySQLHotelAdapter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class VentanaHotelAdapter extends JFrame {

    private final GestionConsultasUseCase casosDeUso;

    // Componentes de la interfaz
    private JComboBox<String> comboConsultas;
    private JTextField txtParametro;
    private JLabel lblParametro;
    private JTable tablaResultados;
    private DefaultTableModel modeloTabla;
    private JButton btnConsultar;

    public VentanaHotelAdapter() {
        // 1. Inicializar el Backend
        MySQLHotelAdapter adaptadorDatos = new MySQLHotelAdapter();
        this.casosDeUso = new GestionConsultasUseCase(adaptadorDatos);

        // 2. Configurar la Ventana
        setTitle("SISTEMA HOTELERO SAFARI'S - VISTA DE TABLAS BD");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // 3. Crear Componentes Visuales
        inicializarComponentes();

        // 4. Configurar Eventos
        configurarEventos();
    }

    private void inicializarComponentes() {
        // Panel Superior: Selección de consulta y parámetro
        JPanel panelSuperior = new JPanel(new GridLayout(3, 2, 5, 5));
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panelSuperior.add(new JLabel("Seleccione la consulta:"));
        String[] opciones = {
                "Listar todos los hoteles de la cadena",
                "Consultar habitaciones disponibles por Hotel",
                "Ver reservas activas de un Cliente",
                "Mostrar historial de reservas de un Cliente",
                "Consultar estancias activas por Hotel"
        };
        comboConsultas = new JComboBox<>(opciones);
        panelSuperior.add(comboConsultas);

        lblParametro = new JLabel("ID requerido:");
        lblParametro.setVisible(false);

        txtParametro = new JTextField();
        txtParametro.setVisible(false);
        panelSuperior.add(lblParametro);
        panelSuperior.add(txtParametro);

        btnConsultar = new JButton("Visualizar Tabla Completa");
        panelSuperior.add(new JLabel(""));
        panelSuperior.add(btnConsultar);

        add(panelSuperior, BorderLayout.NORTH);

        // Panel Central: TABLA
        modeloTabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer la tabla de solo lectura
            }
        };
        tablaResultados = new JTable(modeloTabla);
        tablaResultados.setFillsViewportHeight(true);
        tablaResultados.setRowHeight(26); // Altura cómoda para leer datos
        tablaResultados.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));

        JScrollPane scrollPane = new JScrollPane(tablaResultados);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Registros de la Base de Datos"));

        add(scrollPane, BorderLayout.CENTER);
    }

    private void configurarEventos() {
        // Evento para mostrar/ocultar campos de texto según la consulta
        comboConsultas.addActionListener(e -> {
            int index = comboConsultas.getSelectedIndex();
            if (index == 0) {
                lblParametro.setVisible(false);
                txtParametro.setVisible(false);
            } else {
                lblParametro.setVisible(true);
                txtParametro.setVisible(true);
                if (index == 1 || index == 5) {
                    lblParametro.setText("Ingrese el ID del Hotel (ej: H-001):");
                } else {
                    lblParametro.setText("Ingrese el ID del Cliente (ej: C-123):");
                }
            }
            revalidate();
            repaint();
        });

        // Evento del botón Consultar
        btnConsultar.addActionListener(e -> {
            int index = comboConsultas.getSelectedIndex();
            String parametro = txtParametro.getText().trim();

            if (index > 0 && parametro.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor ingrese el ID requerido.", "Falta Información", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Limpiar por completo la tabla (columnas y filas anteriores)
            modeloTabla.setRowCount(0);
            modeloTabla.setColumnCount(0);

            // Interceptamos temporalmente la salida de texto
            PrintStream consolaOriginal = System.out;
            ByteArrayOutputStream domadorDeConsola = new ByteArrayOutputStream();
            System.setOut(new PrintStream(domadorDeConsola));

            try {
                switch (index) {
                    case 0 -> casosDeUso.consultaHoteles();
                    case 1 -> casosDeUso.consultaHabitaciones(parametro);
                    case 2 -> casosDeUso.consultaReservasActivas(parametro);
                    case 3 -> casosDeUso.consultaHistorialReservas(parametro);
                    case 4 -> casosDeUso.consultaEstanciasActivas(parametro);
                }

                System.setOut(consolaOriginal); // Restaurar consola inmediatamente

                String salidaConsola = domadorDeConsola.toString();
                if (!salidaConsola.trim().isEmpty()) {
                    procesarEInyectarDatosDinamicos(salidaConsola, index);
                } else {
                    JOptionPane.showMessageDialog(this, "No se encontraron datos en las tablas para esta consulta.", "Tabla Vacía", JOptionPane.INFORMATION_MESSAGE);
                }

            } catch (Exception ex) {
                System.setOut(consolaOriginal);
                JOptionPane.showMessageDialog(this, "Error al consultar la BD: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    // Procesa las líneas de texto, descubre cuántos datos trae y crea las columnas exactas sobre la marcha
    private void procesarEInyectarDatosDinamicos(String datosConsola, int tipoConsulta) {
        String[] lineas = datosConsola.split("\n");
        List<String[]> filasValidas = new ArrayList<>();
        int maxColumnas = 0;

        // 1. Analizar el texto y limpiar los datos de la BD
        for (String linea : lineas) {
            linea = linea.trim();

            // Ignoramos títulos decorativos de la consola
            if (linea.isEmpty() || linea.startsWith("-") || linea.startsWith("=")) {
                continue;
            }

            // Separamos por cualquiera de los separadores comunes que use tu BD o tus toString: '|', '-', o ';'
            String[] partesRaw = linea.split("\\||;");
            List<String> celdasLimpias = new ArrayList<>();

            for (String parte : partesRaw) {
                String limpia = parte.trim();
                if (!limpia.isEmpty()) {
                    celdasLimpias.add(limpia);
                }
            }

            if (!celdasLimpias.isEmpty()) {
                String[] filaArray = celdasLimpias.toArray(new String[0]);
                filasValidas.add(filaArray);
                // Registramos cuál es la fila que más datos trajo para saber cuántas columnas crear
                if (filaArray.length > maxColumnas) {
                    maxColumnas = filaArray.length;
                }
            }
        }

        // 2. Crear los encabezados de las columnas de forma adaptativa
        if (maxColumnas == 0) return;

        String[] encabezados = obtenerEncabezadosPorDefecto(tipoConsulta, maxColumnas);
        for (String encabezado : encabezados) {
            modeloTabla.addColumn(encabezado);
        }

        // 3. Inyectar todas las filas y todos sus datos en la cuadrícula
        for (String[] fila : filasValidas) {
            // Si una fila tiene menos elementos por alguna razón, la nivelamos para que no falle
            if (fila.length < maxColumnas) {
                String[] filaNivelada = new String[maxColumnas];
                System.arraycopy(fila, 0, filaNivelada, 0, fila.length);
                for (int i = fila.length; i < maxColumnas; i++) {
                    filaNivelada[i] = ""; // Rellenar vacíos
                }
                modeloTabla.addRow(filaNivelada);
            } else {
                modeloTabla.addRow(fila);
            }
        }
    }

    // Genera nombres lógicos para las columnas; si faltan nombres, numera las columnas automáticamente para asegurar que TODO se visualice
    private String[] obtenerEncabezadosPorDefecto(int tipoConsulta, int totalColumnas) {
        String[] titulos;

        switch (tipoConsulta) {
            case 0 -> titulos = new String[]{"ID Hotel", "Nombre", "Categoría", "Dirección", "Teléfono", "Director"};
            case 1 -> titulos = new String[]{"ID Habitación", "Número", "Tipo", "Precio", "Hotel ID"};
            case 2, 3 -> titulos = new String[]{"ID Reserva", "ID Cliente", "ID Habitación", "Fecha Inicio", "Fecha Fin", "Estado"};
            case 4 -> titulos = new String[]{"ID Estancia", "ID Reserva", "Habitación", "ID Cliente", "Fecha Entrada"};
            default -> titulos = new String[]{};
        }

        // Si la tabla real de la BD tiene más columnas de las que estimamos, creamos nombres automáticos "Dato X" para no ocultar nada
        String[] encabezadosFinales = new String[totalColumnas];
        for (int i = 0; i < totalColumnas; i++) {
            if (i < titulos.length) {
                encabezadosFinales[i] = titulos[i];
            } else {
                encabezadosFinales[i] = "Dato Adicional (" + (i + 1) + ")";
            }
        }
        return encabezadosFinales;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VentanaHotelAdapter().setVisible(true);
        });
    }
}