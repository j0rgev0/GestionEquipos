package org.example;


public class Team {

    private int id;
    private String name;
    private String city;
    private String stadium;


    public Team(int id, String name, String city, String stadium) {
        super();
        this.id = id;
        this.name = name;
        this.city = city;
        this.stadium = stadium;
    }
    public Team( String name, String city, String stadium) {
        super();
        this.name = name;
        this.city = city;
        this.stadium = stadium;
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
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getStadium() {
        return stadium;
    }
    public void setStadium(String stadium) {
        this.stadium = stadium;
    }






}
