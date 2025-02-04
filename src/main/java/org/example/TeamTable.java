package org.example;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;


public class TeamTable extends JPanel {

    PlayerDAO playerDAO;
    TeamDAO teamDAO;

    DefaultTableModel tableModel = new DefaultTableModel();
    JTable table = new JTable(tableModel);

    DefaultTableModel playerTableModel = new DefaultTableModel();
    JTable playerTable = new JTable(playerTableModel);

    public TeamTable (TeamDAO teamDAO,PlayerDAO playerDAO) {
        this.playerDAO = playerDAO;
        this.teamDAO = teamDAO;

        this.setLayout(new BorderLayout());
        JPanel panelNorth = new JPanel();
        JPanel panelSouth = new JPanel();
        JPanel panelEast = new JPanel();
        JPanel panelWest = new JPanel();

        JPanel panelCenter = new JPanel(new GridLayout(2, 2));
        JPanel panelTeam = new JPanel();
        JPanel panelPlayer = new JPanel();

        JPanel info = new JPanel();


        String[] filters = {
                "Name",
                "City",
                "Stadium"
        };


        JComboBox<String> seachCombo = new JComboBox<>(filters);
        seachCombo.setToolTipText("Filter by");
        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");



        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (searchField.getText().toString().trim().isEmpty()){
                    completeTeamTable();

                }else {
                    if(seachCombo.getSelectedItem().toString().equals("Name")){
                        String f = "nombre";
                        completeFilter(f,searchField.getText().toString().trim());
                    }
                    if(seachCombo.getSelectedItem().toString().equals("City")){
                        String f = "ciudad";
                        completeFilter(f,searchField.getText().toString().trim());
                    }
                    if(seachCombo.getSelectedItem().toString().equals("Stadium")){
                        String f = "estadio";
                        completeFilter(f,searchField.getText().toString().trim());
                    }

                }


            }
        });


        panelNorth.add(seachCombo);
        panelNorth.add(searchField);
        panelNorth.add(searchButton);





        tableModel.addColumn("ID");
        tableModel.addColumn("Name");
        tableModel.addColumn("Ciudad");
        tableModel.addColumn("estadio");

        JScrollPane scrollPaneTeam = new JScrollPane(table);
        panelTeam.add(scrollPaneTeam);

        playerTableModel.addColumn("ID");
        playerTableModel.addColumn("Name");
        playerTableModel.addColumn("Position");
        playerTableModel.addColumn("ID_team");

        JScrollPane scrollPanePlayer = new JScrollPane(playerTable);
        panelPlayer.add(scrollPanePlayer);

        panelCenter.add(scrollPaneTeam);
        panelCenter.add(scrollPanePlayer);
        panelCenter.add(info);


        completeTeamTable();

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int teamId = (int) tableModel.getValueAt(selectedRow, 0);
                    completePlayerTable(teamId);
                }
            }
        });

        Dimension buttonSize = new Dimension(200, 50);
        Color buttonColor = new Color(0,255,0);

        JButton addButton = new JButton("Add Team");
        addButton.setPreferredSize(buttonSize);
        addButton.setBackground(buttonColor);
        addButton.setForeground(Color.BLACK);
        addButton.setFont(new Font("Arial", Font.BOLD, 16));
        addButton.setFocusPainted(false);

        JButton editButton = new JButton("Edit Team");
        editButton.setPreferredSize(buttonSize);
        editButton.setBackground(buttonColor);
        editButton.setForeground(Color.BLACK);
        editButton.setFont(new Font("Arial", Font.BOLD, 16));
        editButton.setFocusPainted(false);

        JButton deleteButton = new JButton("Delete Team");
        deleteButton.setPreferredSize(buttonSize);
        deleteButton.setBackground(buttonColor);
        deleteButton.setForeground(Color.BLACK);
        deleteButton.setFont(new Font("Arial", Font.BOLD, 16));
        deleteButton.setFocusPainted(false);


        addButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                addTeam();
                completeTeamTable();

            }
        });
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editTeam();
                completeTeamTable();
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteTeam();
                completeTeamTable();
            }
        });


        panelSouth.add(addButton);
        panelSouth.add(editButton);
        panelSouth.add(deleteButton);


        add(panelNorth,BorderLayout.NORTH);
        add(panelCenter, BorderLayout.CENTER);
        add(panelSouth,BorderLayout.SOUTH);

        panelEast.add(new JLabel("                            "));
        panelWest.add(new JLabel("                            "));
        add(panelEast,BorderLayout.EAST);
        add(panelWest,BorderLayout.WEST);
    }

    public void completeFilter(String f, String n){
        tableModel.setRowCount(0);
        for(Team t : teamDAO.searchPlayer(f,n) ) {

            int id = t.getId();
            String name = t.getName();
            String city = t.getCity();
            String stadium = t.getStadium();
            tableModel.addRow(new Object[] {id,name,city,stadium});

        }
    }

    public void completeTeamTable(){

        tableModel.setRowCount(0);

        for(Team t : teamDAO.TeamList()){
            int id = t.getId();
            String name = t.getName();
            String city = t.getCity();
            String stadium = t.getStadium();
            tableModel.addRow(new Object[] {id,name,city,stadium});

        }
    }

    public void completePlayerTable(int teamId) {
        playerTableModel.setRowCount(0);
        for (Player p : playerDAO.getPlayersByTeam(teamId)) {
            playerTableModel.addRow(new Object[]{p.getId(), p.getName(), p.getPos(), p.getIdTeam()});
        }
    }

    public void deleteTeam(){

        int selectedRow = table.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "No player selected", "Error", JOptionPane.WARNING_MESSAGE);
        }else {

            int id = (int) tableModel.getValueAt(selectedRow, 0);

            teamDAO.deleteTeam(id);
        }
    }

    public void editTeam(){

        int selectedRow = table.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "No Team selected", "Error", JOptionPane.WARNING_MESSAGE);


        }else {

            int idTeam = (int) tableModel.getValueAt(selectedRow,0);
            String nameTeam = (String) tableModel.getValueAt(selectedRow,1);
            String cityTeam = (String) tableModel.getValueAt(selectedRow,2);
            String stadiumTeam = (String) tableModel.getValueAt(selectedRow,3);



            JPanel panel = new JPanel(new BorderLayout());

            JPanel namePanel = new JPanel();
            JLabel nameJLabel = new JLabel("Name:");
            JTextField name = new JTextField(10);
            name.setText(nameTeam);
            namePanel.add(nameJLabel);
            namePanel.add(name);

            JPanel cityPanel = new JPanel();
            JTextField city = new JTextField(10);
            city.setText(cityTeam);
            city.setToolTipText("Team city");

            cityPanel.add(new JLabel("City:"));
            cityPanel.add(city);


            JPanel stadiumPanel = new JPanel();
            JTextField stadium = new JTextField(10);
            stadium.setText(stadiumTeam);
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

                    String nameEdit = name.getText().trim();
                    String cityEdit =  city.getText().trim();
                    String stadiumEdit =  stadium.getText().trim();

                    teamDAO.updateTeam(new Team(idTeam,nameEdit,cityEdit,stadiumEdit));



                }


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