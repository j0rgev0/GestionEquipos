package org.example;

public class Player {
    private int id;
    private String name;
    private String pos;
    private int idTeam;

    public Player(int id, String name, String pos, int idTeam) {
        this.idTeam = idTeam;
        this.pos = pos;
        this.name = name;
        this.id = id;
    }

    public Player(String name, String pos, int idTeam) {
        super();
        this.name = name;
        this.pos = pos;
        this.idTeam = idTeam;
    }


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPos() {
        return pos;
    }
    public void setPos(String pos) {
        this.pos = pos;
    }
    public int getIdTeam() {
        return idTeam;
    }
    public void setIdTeam(int idTeam) {
        this.idTeam = idTeam;
    }




}