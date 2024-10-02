import javax.swing.*;
public class Main {
    public static void main(String[] args) {
        // Ejecutar la interfaz gráfica en el hilo de despacho de eventos de Swing
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                BancoGUI bancoGUI = new BancoGUI(); // Crear la instancia de la interfaz
            }
        });
    }
}

