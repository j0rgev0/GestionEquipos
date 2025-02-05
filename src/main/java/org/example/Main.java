package org.example;

/**
 * Clase principal que gestiona la pantalla de inicio (splash screen) y la transición a la pantalla principal.
 *
 * <p>
 * Esta clase inicia el proceso mostrando una pantalla de carga con una barra de progreso. La barra
 * de progreso se actualiza mientras se simula una tarea de inicialización. Una vez que la barra de progreso
 * llega al 100%, la pantalla de carga se cierra y se abre la pantalla principal de la aplicación.
 * </p>
 *
 * @author Jorge
 * @version 1.0
 * @since 2025-02-05
 */
public class Main {

    /**
     * Método principal que inicia el proceso de la aplicación.
     *
     * Este metodo muestra una pantalla de carga, actualiza el progreso de la barra de forma continua,
     * y una vez finalizada, cierra la pantalla de carga y muestra la pantalla principal de la aplicación.
     *
     *
     * @param args Argumentos de la línea de comandos (no utilizados en este caso).
     */
    public static void main(String[] args) {

        // Crear e inicializar la pantalla de carga
        SplashScreen splashScreen = new SplashScreen();
        splashScreen.setVisible(true);

        // Simula una tarea de carga actualizando la barra de progreso
        for (int i = 0; i <= 100; i++) {
            try {
                Thread.sleep(20);  // Pausa para simular tiempo de carga
                splashScreen.updateProgress(i);  // Actualiza el progreso de la barra
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Una vez que el progreso llega a 100%, se cierra la pantalla de carga
        splashScreen.dispose();

        // Muestra la pantalla principal de la aplicación
        new HomeScreen().setVisible(true);
    }
}
