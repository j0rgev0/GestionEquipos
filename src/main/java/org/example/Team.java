package org.example;

/**
 * Representa un equipo de fútbol o cualquier otro deporte en el sistema.
 * La clase contiene información sobre el equipo, como su identificador, nombre, ciudad y estadio.
 *
 * @author Jorge
 * @version 1.0
 * @since 2025-02-05
 */
public class Team {

    private int id;
    private String name;
    private String city;
    private String stadium;

    /**
     * Constructor que inicializa un equipo con los valores proporcionados.
     *
     * @param id El identificador único del equipo.
     * @param name El nombre del equipo.
     * @param city La ciudad en la que se encuentra el equipo.
     * @param stadium El nombre del estadio del equipo.
     */
    public Team(int id, String name, String city, String stadium) {
        super();
        this.id = id;
        this.name = name;
        this.city = city;
        this.stadium = stadium;
    }

    /**
     * Constructor que inicializa un equipo sin el identificador, usado para crear nuevos equipos.
     *
     * @param name El nombre del equipo.
     * @param city La ciudad en la que se encuentra el equipo.
     * @param stadium El nombre del estadio del equipo.
     */
    public Team(String name, String city, String stadium) {
        super();
        this.name = name;
        this.city = city;
        this.stadium = stadium;
    }

    /**
     * Obtiene el identificador del equipo.
     *
     * @return El identificador del equipo.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el identificador del equipo.
     *
     * @param id El identificador a asignar al equipo.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del equipo.
     *
     * @return El nombre del equipo.
     */
    public String getName() {
        return name;
    }

    /**
     * Establece el nombre del equipo.
     *
     * @param name El nombre a asignar al equipo.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Obtiene la ciudad en la que se encuentra el equipo.
     *
     * @return La ciudad del equipo.
     */
    public String getCity() {
        return city;
    }

    /**
     * Establece la ciudad en la que se encuentra el equipo.
     *
     * @param city La ciudad a asignar al equipo.
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Obtiene el nombre del estadio del equipo.
     *
     * @return El estadio del equipo.
     */
    public String getStadium() {
        return stadium;
    }

    /**
     * Establece el nombre del estadio del equipo.
     *
     * @param stadium El estadio a asignar al equipo.
     */
    public void setStadium(String stadium) {
        this.stadium = stadium;
    }
}

