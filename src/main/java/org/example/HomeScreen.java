package org.example;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import com.formdev.flatlaf.FlatDarkLaf;

public class HomeScreen extends JFrame {

    PlayerDAO playerDAO = new PlayerDAO();
    TeamDAO teamDAO = new TeamDAO();

    public HomeScreen() {

        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
            UIManager.put("Button.arc", 30);

        } catch (Exception e) {
            e.printStackTrace();
        }


        this.setTitle("Gestión Equipo de Fútbol");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);


        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu menu = new JMenu("Menú");
        menu.setMnemonic('M');
        menu.setToolTipText("Opciones disponibles en la aplicación");



        JMenu home = new JMenu("Home");
        home.setMnemonic('H');
        home.setToolTipText("Go home");

        menuBar.add(home);
        menuBar.add(menu);

        JMenu list = new JMenu("List");
        list.setMnemonic('L');
        list.setToolTipText("Go home");

        JMenuItem listPlayer = new JMenuItem("List Player");
        listPlayer.setMnemonic('L');
        listPlayer.setToolTipText("Show Player list");

        JMenuItem listTeam = new JMenuItem("List Team");
        listTeam.setMnemonic('E');
        listTeam.setToolTipText("Show Player list");

        JMenu print = new JMenu("Print");
        print.setMnemonic('p');
        print.setToolTipText("Print");

        JMenuItem generatePdf = new JMenuItem("PDF");
        generatePdf.setMnemonic('D');
        generatePdf.setToolTipText("Print PDF");

        JMenuItem generateExcel = new JMenuItem("Excel");
        generateExcel.setMnemonic('X');
        generatePdf.setToolTipText("Print Excel");


        menu.add(list);
        list.add(listPlayer);
        list.add(listTeam);
        menu.add(print);
        print.add(generatePdf);
        print.add(generateExcel);


        JPanel contentPanel = new JPanel(new GridBagLayout());
        setContentPane(contentPanel);

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

        contentPanel.add(addButton, gbc);

        gbc.gridy++;
        contentPanel.add(addTeam, gbc);

        gbc.gridy++;
        contentPanel.add(listPlayerButton, gbc);

        gbc.gridy++;
        contentPanel.add(listTeamButtton, gbc);



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


        addTeam.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addTeam();
            }
        });

        addButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                addPlayer();

            }
        });

        listTeamButtton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setContentPane(new TeamTable(teamDAO,playerDAO));
                revalidate();
                repaint();

            }
        });
        generatePdf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PDFGenerator.generateTeamsReport(teamDAO.TeamList(),playerDAO);
            }
        });
        generateExcel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ExcelGenerator.generateTeamsReport(teamDAO.TeamList(),playerDAO);

            }
        });
        listTeam.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setContentPane(new TeamTable(teamDAO,playerDAO));
                revalidate();
                repaint();
            }
        });

        listPlayerButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                setContentPane(new PlayerTable(playerDAO));
                revalidate();
                repaint();

            }
        });

        listPlayer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setContentPane(new PlayerTable(playerDAO));
                revalidate();
                repaint();
            }
        });
        ImageIcon icon = new ImageIcon("img/ball-icon.png");

        this.setVisible(true);
        this.setIconImage(icon.getImage());
    }

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