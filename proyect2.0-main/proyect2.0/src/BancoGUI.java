import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class BancoGUI {
    private Banco banco;
    private JFrame frame;
    private JTextField txtNumeroCuenta;
    private JTextField txtTipoCuenta;
    private JTextField txtSaldo;
    private JTextArea txtAreaResultados;

    public BancoGUI() {
        banco = new Banco();
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Sistema Bancario");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500);
        frame.setLayout(new FlowLayout());

        // Campos para agregar cuenta
        txtNumeroCuenta = new JTextField(10);
        txtTipoCuenta = new JTextField(10);
        txtSaldo = new JTextField(10);
        JButton btnAgregar = new JButton("Agregar Cuenta");

        // Botones de búsqueda y mostrar
        JButton btnBuscarNumero = new JButton("Buscar por Número");
        JButton btnBuscarSaldo = new JButton("Buscar por Saldo");
        JButton btnBuscarTipo = new JButton("Buscar por Tipo");
        JButton btnMostrarTodas = new JButton("Mostrar Todas las Cuentas");

        // Área de texto para resultados
        txtAreaResultados = new JTextArea(10, 30);
        txtAreaResultados.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txtAreaResultados);

        // Agregar componentes al frame
        frame.add(new JLabel("Número de Cuenta:"));
        frame.add(txtNumeroCuenta);
        frame.add(new JLabel("Tipo de Cuenta:"));
        frame.add(txtTipoCuenta);
        frame.add(new JLabel("Saldo:"));
        frame.add(txtSaldo);
        frame.add(btnAgregar);
        frame.add(btnBuscarNumero);
        frame.add(btnBuscarSaldo);
        frame.add(btnBuscarTipo);
        frame.add(btnMostrarTodas);  // Agregar botón para mostrar todas las cuentas
        frame.add(scrollPane);

        // Agregar acción para agregar cuenta
        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String numeroCuenta = txtNumeroCuenta.getText();
                String tipoCuenta = txtTipoCuenta.getText();
                double saldo;

                try {
                    saldo = Double.parseDouble(txtSaldo.getText());
                    CuentaBancaria cuenta = new CuentaBancaria(numeroCuenta, tipoCuenta, saldo);
                    banco.agregarCuenta(cuenta);
                    txtAreaResultados.setText("Cuenta agregada: " + cuenta.toString());
                    txtNumeroCuenta.setText("");
                    txtTipoCuenta.setText("");
                    txtSaldo.setText("");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Saldo inválido. Ingrese un número válido.");
                }
            }
        });

        // Agregar acción para buscar por número
        btnBuscarNumero.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String numeroCuenta = JOptionPane.showInputDialog(frame, "Ingrese el número de cuenta:");
                CuentaBancaria cuenta = banco.buscarPorNumeroCuenta(numeroCuenta);
                if (cuenta != null) {
                    txtAreaResultados.setText("Cuenta encontrada: " + cuenta.toString());
                } else {
                    txtAreaResultados.setText("No se encontró ninguna cuenta con ese número.");
                }
            }
        });

        // Agregar acción para buscar por saldo
        btnBuscarSaldo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputSaldo = JOptionPane.showInputDialog(frame, "Ingrese el saldo:");
                try {
                    double saldo = Double.parseDouble(inputSaldo);
                    List<CuentaBancaria> cuentas = banco.buscarPorSaldo(saldo);
                    if (!cuentas.isEmpty()) {
                        StringBuilder resultados = new StringBuilder("Cuentas con saldo " + saldo + ":\n");
                        for (CuentaBancaria c : cuentas) {
                            resultados.append(c.toString()).append("\n");
                        }
                        txtAreaResultados.setText(resultados.toString());
                    } else {
                        txtAreaResultados.setText("No se encontraron cuentas con ese saldo.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Saldo inválido. Ingrese un número válido.");
                }
            }
        });

        // Agregar acción para buscar por tipo
        btnBuscarTipo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tipoCuenta = JOptionPane.showInputDialog(frame, "Ingrese el tipo de cuenta (Ahorro o Corriente):");
                List<CuentaBancaria> cuentas = banco.buscarPorTipoCuenta(tipoCuenta);
                if (!cuentas.isEmpty()) {
                    StringBuilder resultados = new StringBuilder("Cuentas de tipo " + tipoCuenta + ":\n");
                    for (CuentaBancaria c : cuentas) {
                        resultados.append(c.toString()).append("\n");
                    }
                    txtAreaResultados.setText(resultados.toString());
                } else {
                    txtAreaResultados.setText("No se encontraron cuentas de ese tipo.");
                }
            }
        });

        // Agregar acción para mostrar todas las cuentas
        btnMostrarTodas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<CuentaBancaria> cuentas = banco.getCuentas();  // Obtener todas las cuentas
                if (!cuentas.isEmpty()) {
                    StringBuilder resultados = new StringBuilder("Todas las cuentas:\n");
                    for (CuentaBancaria cuenta : cuentas) {
                        resultados.append(cuenta.toString()).append("\n");
                    }
                    txtAreaResultados.setText(resultados.toString());
                } else {
                    txtAreaResultados.setText("No hay cuentas registradas.");
                }
            }
        });

        // Hacer visible el frame
        frame.setVisible(true);
    }
}
