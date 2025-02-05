package org.example;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import com.formdev.flatlaf.FlatDarkLaf;

/**
 * Clase que representa la pantalla principal de la aplicación de gestión de equipos de fútbol.
 * Esta clase extiende {@link JFrame} y proporciona la interfaz gráfica para la gestión de jugadores y equipos.
 * Incluye opciones para agregar jugadores y equipos, listar jugadores y equipos, y generar reportes en PDF o Excel.
 *
 *
 * @author Jorge
 * @version 1.0
 * @since 2025-02-05
 */

public class HomeScreen extends JFrame {

    // Objetos DAO para interactuar con los datos de jugadores y equipos
    PlayerDAO playerDAO = new PlayerDAO();
    TeamDAO teamDAO = new TeamDAO();

    /**
     * Constructor que configura la pantalla principal de la aplicación.
     * Este constructor configura la ventana principal, los menús, y los botones para interactuar con los jugadores
     * y equipos, incluyendo las acciones para agregar, listar y generar reportes.
     *
     */

    public HomeScreen() {

        try {
            // Establece el look and feel oscuro para la interfaz
            UIManager.setLookAndFeel(new FlatDarkLaf());
            UIManager.put("Button.arc", 30);// Establece un radio de curvatura para los botones

        } catch (Exception e) {
            e.printStackTrace();// Si hay un error al establecer el look and feel, se imprime el error
        }

        // Configura la ventana principal
        this.setTitle("Gestión Equipo de Fútbol");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);

        // Menú de la aplicación
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu menu = new JMenu("Menú");
        menu.setMnemonic('M');
        menu.setToolTipText("Opciones disponibles en la aplicación");


        // Menú "Home"
        JMenu home = new JMenu("Home");
        home.setMnemonic('H');
        home.setToolTipText("Go home");

        menuBar.add(home);
        menuBar.add(menu);

        // Menú "List" para listar jugadores y equipos
        JMenu list = new JMenu("List");
        list.setMnemonic('L');
        list.setToolTipText("Go home");

        // Opciones dentro del menú "List"
        JMenuItem listPlayer = new JMenuItem("List Player");
        listPlayer.setMnemonic('L');
        listPlayer.setToolTipText("Show Player list");


        JMenuItem listTeam = new JMenuItem("List Team");
        listTeam.setMnemonic('E');
        listTeam.setToolTipText("Show Player list");

        // Menú "Print" para generar reportes
        JMenu print = new JMenu("Print");
        print.setMnemonic('p');
        print.setToolTipText("Print");

        JMenuItem generatePdf = new JMenuItem("PDF");
        generatePdf.setMnemonic('D');
        generatePdf.setToolTipText("Print PDF");

        JMenuItem generateExcel = new JMenuItem("Excel");
        generateExcel.setMnemonic('X');
        generatePdf.setToolTipText("Print Excel");

        // Añadir los menús a la barra de menús
        menu.add(list);
        list.add(listPlayer);
        list.add(listTeam);
        menu.add(print);
        print.add(generatePdf);
        print.add(generateExcel);

        // Panel de contenido
        JPanel contentPanel = new JPanel(new GridBagLayout());
        setContentPane(contentPanel);

        // Configuración de los botones
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        Dimension buttonSize = new Dimension(200, 50);
        Color buttonColor = new Color(0,255,0);

        JButton addButton = new JButton("Add Player");
        addButton.setPreferredSize(buttonSize);
        addButton.setBackground(buttonColor);
        addButton.setForeground(Color.BLACK);
        addButton.setFont(new Font("Arial", Font.BOLD, 16));
        addButton.setFocusPainted(false);

        JButton addTeam = new JButton("Add Team");
        addTeam.setPreferredSize(buttonSize);
        addTeam.setBackground(buttonColor);
        addTeam.setForeground(Color.BLACK);
        addTeam.setFont(new Font("Arial", Font.BOLD, 16));
        addTeam.setFocusPainted(false);



        JButton listPlayerButton = new JButton("Players List");
        listPlayerButton.setPreferredSize(buttonSize);
        listPlayerButton.setBackground(buttonColor);
        listPlayerButton.setForeground(Color.BLACK);
        listPlayerButton.setFont(new Font("Arial", Font.BOLD, 16));
        listPlayerButton.setFocusPainted(false);


        JButton listTeamButtton = new JButton("Teams List");
        listTeamButtton.setPreferredSize(buttonSize);
        listTeamButtton.setBackground(buttonColor);
        listTeamButtton.setForeground(Color.BLACK);
        listTeamButtton.setFont(new Font("Arial", Font.BOLD, 16));
        listTeamButtton.setFocusPainted(false);

        // Añadir los botones al panel
        contentPanel.add(addButton, gbc);
        gbc.gridy++;
        contentPanel.add(addTeam, gbc);
        gbc.gridy++;
        contentPanel.add(listPlayerButton, gbc);
        gbc.gridy++;
        contentPanel.add(listTeamButtton, gbc);


        // Configuración de los eventos de los menús y botones
        home.addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                setContentPane(contentPanel);
                revalidate();
                repaint();
            }
            @Override
            public void menuDeselected(MenuEvent e) {

            }
            @Override
            public void menuCanceled(MenuEvent e) {

            }
        });

        // Acción de agregar jugador
        addTeam.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addTeam();
            }
        });

        // Acción de agregar equipo
        addButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                addPlayer();

            }
        });
        // Acción para listar equipos
        listTeamButtton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setContentPane(new TeamTable(teamDAO,playerDAO));
                revalidate();
                repaint();

            }
        });
        // Acción para generar reporte en PDF
        generatePdf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PDFGenerator.generateTeamsReport(teamDAO.TeamList(),playerDAO);
            }
        });

        // Acción para generar reporte en Excel
        generateExcel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ExcelGenerator.generateTeamsReport(teamDAO.TeamList(),playerDAO);

            }
        });
        // Acción para listar equipos
        listTeam.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setContentPane(new TeamTable(teamDAO,playerDAO));
                revalidate();
                repaint();
            }
        });
        // Acción para listar jugadores
        listPlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setContentPane(new PlayerTable(playerDAO));
                revalidate();
                repaint();

            }
        });
        // Acción para listar jugadores
        listPlayer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setContentPane(new PlayerTable(playerDAO));
                revalidate();
                repaint();
            }
        });

        // Establecer ícono de la ventana
        ImageIcon icon = new ImageIcon("img/ball-icon.png");

        // Mostrar la ventana
        this.setVisible(true);
        this.setIconImage(icon.getImage());
    }

    /**
     * Abre un cuadro de diálogo que permite al usuario agregar un nuevo jugador.
     *
     * <p>
     * Este metodo crea un panel con campos para ingresar el nombre del jugador, seleccionar su posición y
     * asignar un equipo. Al hacer clic en "OK", se validan los campos y, si están completos,
     * se crea un nuevo objeto de tipo {@link Player} y se guarda en la base de datos.
     * </p>
     *
     * <p>
     * El cuadro de diálogo se presenta con:
     * <ul>
     *   <li>Un campo de texto para el nombre del jugador.</li>
     *   <li>Un combo de selección para la posición del jugador, con opciones como "Goalkeeper", "Defender", "Midfielder" y "Forward".</li>
     *   <li>Un combo de selección para asignar el jugador a un equipo existente, con una lista de equipos disponibles.</li>
     * </ul>
     * </p>
     *
     * <p>
     * Si alguno de los campos está vacío, se muestra un mensaje de advertencia y no se crea el jugador.
     * </p>
     */
    public void addPlayer() {

        JPanel panel = new JPanel(new BorderLayout());


        String[] positions = {
                "",
                "Goalkeeper",
                "Defender",
                "Midfielder",
                "Forward",
        };



        JPanel namePanel = new JPanel();

        JLabel nameJLabel = new JLabel("Name:");
        JTextField name = new JTextField(10);
        namePanel.add(nameJLabel);
        namePanel.add(name);

        JPanel posPanel = new JPanel();
        JComboBox<String> posCombo = new JComboBox<>(positions);
        posCombo.setToolTipText("Select player position: ");

        posPanel.add(new JLabel("Position:"));
        posPanel.add(posCombo);


        JPanel teamPanel = new JPanel();
        JComboBox<String> teamCombo = new JComboBox<>();
        teamCombo.addItem("");
        for(Team t :  teamDAO.TeamList()){

            teamCombo.addItem(t.getName());
        }


        teamCombo.setToolTipText("Select player team: ");
        teamPanel.add(new JLabel("Team:"));
        teamPanel.add(teamCombo);


        panel.add(namePanel,BorderLayout.NORTH);
        panel.add(posPanel, BorderLayout.CENTER);
        panel.add(teamPanel,BorderLayout.SOUTH);

        int result = JOptionPane.showConfirmDialog(this, panel, "Add Player", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if(result == JOptionPane.OK_OPTION){

            if (name.getText().trim().isEmpty() && posCombo.getSelectedItem().toString().trim().isEmpty() && teamCombo.getSelectedItem().toString().trim().isEmpty() ){

                JOptionPane.showMessageDialog(this,"Complete the empty fields", "Error",JOptionPane.WARNING_MESSAGE);

            }else {

                String namePlayer = name.getText().trim();
                String posPlayer = posCombo.getSelectedItem().toString().trim();
                int idTeam = 1;

                for(Team t : teamDAO.TeamList()){
                    if (t.getName().trim().equals(teamCombo.getSelectedItem().toString().trim())){
                        idTeam = t.getId();
                    }
                }

                playerDAO.createPlayer(new Player(namePlayer,posPlayer,idTeam));



            }


        }
    }

    /**
     * Abre un cuadro de diálogo que permite al usuario agregar un nuevo equipo.
     *
     * <p>
     * Este metodo crea un panel con campos para ingresar el nombre del equipo, la ciudad y el estadio.
     * Al hacer clic en "OK", se validan los campos y, si están completos, se crea un nuevo objeto de tipo {@link Team}
     * y se guarda en la base de datos.
     * </p>
     *
     * <p>
     * El cuadro de diálogo se presenta con:
     * <ul>
     *   <li>Un campo de texto para el nombre del equipo.</li>
     *   <li>Un campo de texto para la ciudad del equipo.</li>
     *   <li>Un campo de texto para el estadio del equipo.</li>
     * </ul>
     * </p>
     *
     * <p>
     * Si alguno de los campos está vacío, se muestra un mensaje de advertencia y no se crea el equipo.
     * </p>
     */

    public void addTeam() {

        JPanel panel = new JPanel(new BorderLayout());



        JPanel namePanel = new JPanel();

        JLabel nameJLabel = new JLabel("Name:");
        JTextField name = new JTextField(10);
        namePanel.add(nameJLabel);
        namePanel.add(name);

        JPanel cityPanel = new JPanel();
        JTextField city = new JTextField(10);
        city.setToolTipText("Team city");

        cityPanel.add(new JLabel("City:"));
        cityPanel.add(city);


        JPanel stadiumPanel = new JPanel();
        JTextField stadium = new JTextField(10);
        stadium.setToolTipText("Team stadium");

        stadiumPanel.add(new JLabel("Stadium:"));
        stadiumPanel.add(stadium);




        panel.add(namePanel,BorderLayout.NORTH);
        panel.add(stadiumPanel, BorderLayout.CENTER);
        panel.add(cityPanel,BorderLayout.SOUTH);

        int result = JOptionPane.showConfirmDialog(this, panel, "Add Team", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);


        if(result == JOptionPane.OK_OPTION){

            if (name.getText().trim().isEmpty() && city.getText().trim().isEmpty() && stadium.getText().trim().isEmpty()){

                JOptionPane.showMessageDialog(this,"Complete the empty fields", "Error",JOptionPane.WARNING_MESSAGE);

            }else {

                String nameTeam = name.getText().trim();
                String cityTeam =  city.getText().trim();
                String stadiumTeam =  stadium.getText().trim();

                teamDAO.createTeam(new Team(nameTeam,cityTeam,stadiumTeam));



            }


        }
    }


}