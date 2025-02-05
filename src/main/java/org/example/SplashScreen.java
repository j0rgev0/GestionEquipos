package org.example;

import com.formdev.flatlaf.FlatDarkLaf;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.*;

/**
 * Clase que representa la pantalla de inicio (splash screen) de la aplicación.
 * Esta clase extiende {@link JDialog} y muestra una ventana emergente con una barra de progreso que
 * se actualiza mientras se cargan los recursos de la aplicación. También muestra un logo y un mensaje
 * que indica que los recursos están siendo cargados.
 *
 *
 * @author Jorge
 * @version 1.0
 * @since 2025-02-05
 */
public class SplashScreen extends JDialog {

    /**
     * Barra de progreso que muestra el estado de carga.
     */
    private JProgressBar progressBar;

    /**
     * Constructor que configura la pantalla de inicio (splash screen).
     *
     *
     * Este constructor establece el look and feel oscuro, configura la apariencia de la pantalla
     * de inicio, define los componentes gráficos (como el logo, el mensaje y la barra de progreso),
     * y ajusta la ventana a un tamaño fijo.
     *
     */
    public SplashScreen() {

        try {
            // Establece el look and feel oscuro para la interfaz
            UIManager.setLookAndFeel(new FlatDarkLaf());
            UIManager.put("Button.arc", 30); // Establece un radio de curvatura para los botones

        } catch (Exception e) {
            e.printStackTrace(); // Si hay un error al establecer el look and feel, se imprime el error
        }

        // Configura la ventana de la pantalla de inicio
        setSize(400, 250); // Tamaño de la ventana
        setLocationRelativeTo(null); // Centra la ventana en la pantalla
        setLayout(new BorderLayout()); // Utiliza BorderLayout
        setUndecorated(true); // Quita los bordes y la barra de título

        // Panel principal para los componentes
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE); // Establece el color de fondo del panel

        // Logo en el centro de la pantalla
        JLabel logoJLabel = new JLabel(new ImageIcon("img/ball.gif"), JLabel.CENTER);
        logoJLabel.setForeground(new Color(0, 255, 0)); // Color verde para el logo

        // Mensaje debajo del logo
        JLabel messageLabel = new JLabel("Cargando recursos, por favor espere", JLabel.CENTER);
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 14)); // Fuente del mensaje
        messageLabel.setForeground(new Color(0, 255, 0)); // Color verde para el mensaje

        // Barra de progreso en la parte inferior
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true); // Muestra el porcentaje de progreso
        progressBar.setForeground(new Color(0, 255, 0)); // Color verde para la barra de progreso

        // Añadir componentes al panel
        mainPanel.add(logoJLabel, BorderLayout.CENTER);
        mainPanel.add(messageLabel, BorderLayout.SOUTH);

        // Añadir el panel principal y la barra de progreso a la ventana
        add(mainPanel, BorderLayout.CENTER);
        add(progressBar, BorderLayout.SOUTH);
    }

    /**
     * Actualiza el valor de la barra de progreso.
     *
     * @param value El nuevo valor de la barra de progreso, debe estar en el rango de 0 a 100.
     */
    public void updateProgress(int value) {
        progressBar.setValue(value); // Actualiza el valor de la barra de progreso
    }
}
