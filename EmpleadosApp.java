import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class EmpleadosApp extends JFrame {
    // Declaraciones de variables
    private ArrayList<Empleado> empleados;
    private JTable table;
    private DefaultTableModel tableModel;

    private JTextField nombreField;
    private JTextField apellidoField;
    private JTextField cedulaField;
    private JTextField hijosField;
    private JTextField horasField;
    private JTextField fechaAltaField;
    private JTextField fechaBajaField;
    private JTextField antiguedadField;

    private JRadioButton empleadoPorHora;
    private JRadioButton empleadoTemporal;
    private JRadioButton empleadoPermanente;
    private ButtonGroup empleadoGroup;

    // Declaracion de app principal
    public EmpleadosApp() {
        empleados = new ArrayList<>();
        setTitle("Gestión de Empleados");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Crear tabla
        String[] columnNames = {"Nombre", "Apellido", "Cédula", "Hijos", "Tipo", "Sueldo", "Fecha alta", "Fecha Baja"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        // Crear formulario
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(0, 2));

        formPanel.add(new JLabel("Nombre:"));
        nombreField = new JTextField();
        formPanel.add(nombreField);

        formPanel.add(new JLabel("Apellido:"));
        apellidoField = new JTextField();
        formPanel.add(apellidoField);

        formPanel.add(new JLabel("Cédula:"));
        cedulaField = new JTextField();
        formPanel.add(cedulaField);

        formPanel.add(new JLabel("Cantidad de Hijos:"));
        hijosField = new JTextField();
        formPanel.add(hijosField);

        empleadoGroup = new ButtonGroup();

        empleadoPorHora = new JRadioButton("Empleado por Hora");
        empleadoPorHora.addActionListener(e -> toggleFields(true, false, false));
        empleadoGroup.add(empleadoPorHora);
        formPanel.add(empleadoPorHora);
        horasField = new JTextField();
        formPanel.add(horasField);
        
        empleadoTemporal = new JRadioButton("Empleado Temporal");
        empleadoTemporal.addActionListener(e -> toggleFields(false, true, false));
        empleadoGroup.add(empleadoTemporal);
        formPanel.add(empleadoTemporal);
        fechaAltaField = new JTextField();
        formPanel.add(fechaAltaField);
        formPanel.add(new JLabel("Fecha de Alta:"));
        fechaBajaField = new JTextField();
        formPanel.add(fechaBajaField);
        formPanel.add(new JLabel("Fecha de Baja:"));
        formPanel.add(new JLabel(""));
        formPanel.add(new JLabel(""));

        empleadoPermanente = new JRadioButton("Empleado Permanente");
        empleadoPermanente.addActionListener(e -> toggleFields(false, false, true));
        empleadoGroup.add(empleadoPermanente);
        formPanel.add(empleadoPermanente);
        formPanel.add(new JLabel("Antigüedad:"));
        antiguedadField = new JTextField();
        formPanel.add(antiguedadField);

        panel.add(formPanel, BorderLayout.NORTH);

        // Crear botones
        JPanel buttonPanel = new JPanel();

        JButton saveButton = new JButton("Guardar");
        saveButton.addActionListener(e -> onSave());
        buttonPanel.add(saveButton);

        JButton clearButton = new JButton("Limpiar");
        clearButton.addActionListener(e -> onClear());
        buttonPanel.add(clearButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);
    }

    private void toggleFields(boolean porHora, boolean temporal, boolean permanente) {
        horasField.setEnabled(porHora);
        fechaAltaField.setEnabled(temporal);
        fechaBajaField.setEnabled(temporal);
        antiguedadField.setEnabled(permanente);
    }

    private void onSave() {
        int response = JOptionPane.showConfirmDialog(this, "¿Desea guardar la información?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            String nombre = nombreField.getText();
            String apellido = apellidoField.getText();
            String cedula = cedulaField.getText();
            int cantidadHijos = Integer.parseInt(hijosField.getText());
            String fechaAlta = fechaAltaField.getText();
            String fechaBaja = fechaBajaField.getText();

            Empleado empleado = null;
            if (empleadoPorHora.isSelected()) {
                int horasTrabajadas = Integer.parseInt(horasField.getText());
                empleado = new EmpleadoPorHora(nombre, apellido, cedula, cantidadHijos, horasTrabajadas);
            } else if (empleadoTemporal.isSelected()) {
                empleado = new EmpleadoTemporal(nombre, apellido, cedula, cantidadHijos, fechaAlta, fechaBaja);
            } else if (empleadoPermanente.isSelected()) {
                int antiguedad = Integer.parseInt(antiguedadField.getText());
                empleado = new EmpleadoPermanente(nombre, apellido, cedula, cantidadHijos, antiguedad);
            }

            empleados.add(empleado);

            tableModel.addRow(new Object[]{
                nombre, apellido, cedula, cantidadHijos,
                empleado.getClass().getSimpleName(), empleado.calcularSueldo(), fechaAlta, fechaBaja
            });

            clearFields();

            JOptionPane.showMessageDialog(this, "Datos almacenados correctamente.");
        }
    }

    private void onClear() {
        int response = JOptionPane.showConfirmDialog(this, "¿Está seguro que desea limpiar todos los parámetros?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            clearFields();
        }
    }

    private void clearFields() {
        nombreField.setText("");
        apellidoField.setText("");
        cedulaField.setText("");
        hijosField.setText("");
        horasField.setText("");
        fechaAltaField.setText("");
        fechaBajaField.setText("");
        antiguedadField.setText("");
        empleadoGroup.clearSelection();
        toggleFields(false, false, false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EmpleadosApp app = new EmpleadosApp();
            app.setVisible(true);
        });
    }
}
