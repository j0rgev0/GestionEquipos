package org.example;

/**
 * Representa a un jugador de un equipo en el sistema.
 * La clase contiene información sobre el jugador, como su identificador, nombre, posición y el equipo al que pertenece.
 *
 *
 * @author Jorge
 * @version 1.0
 * @since 2025-02-05
 */

public class Player {
    private int id;
    private String name;
    private String pos;
    private int idTeam;

    /**
     * Constructor que inicializa un jugador con los valores proporcionados.
     *
     * @param id El identificador único del jugador.
     * @param name El nombre del jugador.
     * @param pos La posición del jugador en el campo.
     * @param idTeam El identificador del equipo al que pertenece el jugador.xxºxº
     */

    public Player(int id, String name, String pos, int idTeam) {
        this.idTeam = idTeam;
        this.pos = pos;
        this.name = name;
        this.id = id;
    }


    /**
     * Constructor que inicializa un jugador sin el identificador, usado para crear nuevos jugadores.
     *
     * @param name El nombre del jugador.
     * @param pos La posición del jugador en el campo.
     * @param idTeam El identificador del equipo al que pertenece el jugador.
     */
    public Player(String name, String pos, int idTeam) {
        super();
        this.name = name;
        this.pos = pos;
        this.idTeam = idTeam;
    }

    /**
     * Obtiene el identificador del jugador.
     *
     * @return El identificador del jugador.
     */
    public int getId() {
        return id;
    }
    /**
     * Establece el identificador del jugador.
     *
     * @param id El identificador a asignar al jugador.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del jugador.
     *
     * @return El nombre del jugador.
     */
    public String getName() {
        return name;
    }
    /**
     * Establece el nombre del jugador.
     *
     * @param name El nombre a asignar al jugador.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Obtiene la posición del jugador en el campo.
     *
     * @return La posición del jugador.
     */
    public String getPos() {
        return pos;
    }

    /**
     * Establece la posición del jugador en el campo.
     *
     * @param pos La posición a asignar al jugador.
     */
    public void setPos(String pos) {
        this.pos = pos;
    }

    /**
     * Obtiene el identificador del equipo al que pertenece el jugador.
     *
     * @return El identificador del equipo.
     */
    public int getIdTeam() {
        return idTeam;
    }

    /**
     * Establece el identificador del equipo al que pertenece el jugador.
     *
     * @param idTeam El identificador del equipo a asignar.
     */
    public void setIdTeam(int idTeam) {
        this.idTeam = idTeam;
    }




}