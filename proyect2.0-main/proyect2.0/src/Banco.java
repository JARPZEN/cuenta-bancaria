import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Banco {
    private List<CuentaBancaria> cuentas;
    private static final String ARCHIVO_CUENTAS = "cuentas_bancarias.txt";

    public Banco() {
        this.cuentas = new ArrayList<>();
        cargarCuentasDesdeArchivo(); // Cargar cuentas al iniciar
    }

    // Método para cargar cuentas desde un archivo al iniciar
    private void cargarCuentasDesdeArchivo() {
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_CUENTAS))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 3) {
                    String numeroCuenta = datos[0];
                    String tipoCuenta = datos[1];
                    double saldo = Double.parseDouble(datos[2]);
                    CuentaBancaria cuenta = new CuentaBancaria(numeroCuenta, tipoCuenta, saldo);
                    cuentas.add(cuenta);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado. No se han cargado cuentas.");
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }

    // Método para agregar cuentas
    public void agregarCuenta(CuentaBancaria cuenta) {
        cuentas.add(cuenta);
        guardarCuentaEnArchivo(cuenta);
    }

    // Método para guardar una cuenta bancaria en un archivo de texto
    private void guardarCuentaEnArchivo(CuentaBancaria cuenta) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO_CUENTAS, true))) {
            writer.write(cuenta.getNumeroCuenta() + "," + cuenta.getTipoCuenta() + "," + cuenta.getSaldo());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo: " + e.getMessage());
        }
    }

    // Método para buscar por número de cuenta
    public CuentaBancaria buscarPorNumeroCuenta(String numeroCuenta) {
        for (CuentaBancaria cuenta : cuentas) {
            if (cuenta.getNumeroCuenta().equals(numeroCuenta)) {
                return cuenta;
            }
        }
        return null; // Si no se encuentra la cuenta
    }

    // Método para buscar por saldo
    public List<CuentaBancaria> buscarPorSaldo(double saldo) {
        List<CuentaBancaria> cuentasConSaldo = new ArrayList<>();
        for (CuentaBancaria cuenta : cuentas) {
            if (cuenta.getSaldo() == saldo) {
                cuentasConSaldo.add(cuenta);
            }
        }
        return cuentasConSaldo;
    }

    // Método para buscar por tipo de cuenta (Ahorro o Corriente)
    public List<CuentaBancaria> buscarPorTipoCuenta(String tipoCuenta) {
        List<CuentaBancaria> cuentasPorTipo = new ArrayList<>();
        for (CuentaBancaria cuenta : cuentas) {
            if (cuenta.getTipoCuenta().equalsIgnoreCase(tipoCuenta)) {
                cuentasPorTipo.add(cuenta);
            }
        }
        return cuentasPorTipo;
    }
    public List<CuentaBancaria> getCuentas() {
        return cuentas;
    }

}
