package org.example;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;


public class PlayerTable extends JPanel {

    PlayerDAO playerDAO;
    TeamDAO teamDAO = new TeamDAO();

    DefaultTableModel tableModel = new DefaultTableModel();
    JTable table = new JTable(tableModel);

    public PlayerTable(PlayerDAO playerDAO) {

        this.playerDAO = playerDAO;

        this.setLayout(new BorderLayout());
        JPanel panelNorth = new JPanel();
        JPanel panelCenter = new JPanel();
        JPanel panelSouth = new JPanel();


        String[] filters = {
                "Name",
                "Position",
        };


        JComboBox<String> seachCombo = new JComboBox<>(filters);
        seachCombo.setToolTipText("Filter by");
        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        
        
        
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (searchField.getText().toString().trim().isEmpty()){
                    completePlayerTable();

                }else {
                    if(seachCombo.getSelectedItem().toString().equals("Name")){
                        String f = "nombre";
                        completeFilter(f,searchField.getText().toString().trim());
                    }
                    if(seachCombo.getSelectedItem().toString().equals("Position")){
                        String f = "posicion";
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
        tableModel.addColumn("Position");
        tableModel.addColumn("ID_team");



        JScrollPane scrollPane = new JScrollPane(table);
        panelCenter.add(scrollPane);

        completePlayerTable();

        Dimension buttonSize = new Dimension(200, 50);
        Color buttonColor = new Color(0,255,0);

        JButton addButton = new JButton("Add Player");
        addButton.setPreferredSize(buttonSize);
        addButton.setBackground(buttonColor);
        addButton.setForeground(Color.BLACK);
        addButton.setFont(new Font("Arial", Font.BOLD, 16));
        addButton.setFocusPainted(false);

        JButton editButton = new JButton("Edit Player");
        editButton.setPreferredSize(buttonSize);
        editButton.setBackground(buttonColor);
        editButton.setForeground(Color.BLACK);
        editButton.setFont(new Font("Arial", Font.BOLD, 16));
        editButton.setFocusPainted(false);

        JButton deleteButton = new JButton("Delete Player");
        deleteButton.setPreferredSize(buttonSize);
        deleteButton.setBackground(buttonColor);
        deleteButton.setForeground(Color.BLACK);
        deleteButton.setFont(new Font("Arial", Font.BOLD, 16));
        deleteButton.setFocusPainted(false);


        addButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                addPlayer();
                completePlayerTable();

            }
        });
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editPlayer();
                completePlayerTable();
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deletePlayer();
                completePlayerTable();
            }
        });

        panelSouth.add(addButton);
        panelSouth.add(editButton);
        panelSouth.add(deleteButton);

        add(panelNorth,BorderLayout.NORTH);
        add(panelCenter, BorderLayout.CENTER);
        add(panelSouth,BorderLayout.SOUTH);

    }

    public void deletePlayer(){

        int selectedRow = table.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "No player selected", "Error", JOptionPane.WARNING_MESSAGE);
        }else {

            int id = (int) tableModel.getValueAt(selectedRow, 0);

            playerDAO.deletePlayer(id);
        }
    }

    public void editPlayer() {

        int selectedRow = table.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "No player selected", "Error", JOptionPane.WARNING_MESSAGE);
        }else {

            int idEdit = (int) tableModel.getValueAt(selectedRow, 0);
            String nameEdit = (String) tableModel.getValueAt(selectedRow, 1);
            String positionEdit = (String) tableModel.getValueAt(selectedRow, 2);
            int idTeamEdit = (int) tableModel.getValueAt(selectedRow, 3);

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
            name.setText(nameEdit);
            namePanel.add(nameJLabel);
            namePanel.add(name);

            JPanel posPanel = new JPanel();
            JComboBox<String> posCombo = new JComboBox<>(positions);
            posCombo.setToolTipText("Select player position: ");
            posCombo.setSelectedItem(positionEdit);

            posPanel.add(new JLabel("Position:"));
            posPanel.add(posCombo);


            JPanel teamPanel = new JPanel();
            JComboBox<String> teamCombo = new JComboBox<>();
            teamCombo.addItem("");

            for(Team t :  teamDAO.TeamList()){
                teamCombo.addItem(t.getName());
            }

            for(Team t :  teamDAO.TeamList()){
                if(t.getId() == idTeamEdit){
                    teamCombo.setSelectedItem(t.getName());
                }
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
                    int idTeam = idTeamEdit;

                    for(Team t : teamDAO.TeamList()){
                        if (t.getName().trim().equals(teamCombo.getSelectedItem().toString().trim())){
                            idTeam = t.getId();
                        }
                    }

                    playerDAO.updatePlayer(new Player(idEdit,namePlayer,posPlayer,idTeam));



                }


            }




        }
    }

    public void completeFilter(String f, String n){
        tableModel.setRowCount(0);
        for(Player p : playerDAO.searchPlayer(f,n) ) {

            int id = p.getId();
            String name = p.getName();
            String pos = p.getPos();
            int idteam = p.getIdTeam();
            tableModel.addRow(new Object[] {id,name,pos,idteam});

        }
    }

    public void completePlayerTable(){

        tableModel.setRowCount(0);
        for(Player p : playerDAO.PlayerList() ) {

            int id = p.getId();
            String name = p.getName();
            String pos = p.getPos();
            int idteam = p.getIdTeam();
            tableModel.addRow(new Object[] {id,name,pos,idteam});

        }

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

}