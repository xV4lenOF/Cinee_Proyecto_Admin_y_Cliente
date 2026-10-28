package CLASES;

/**
 * Representa una función de cine: una película específica, en una sala
 * específica, a una fecha y hora determinadas.
 */
public class Funcion {

    // === Atributos de la función ===
    private String funcionID;
    private String fecha;
    private String hora;

    // === Relaciones: una función tiene una película y una sala ===
    private Pelicula pelicula;
    private Sala sala;

    // === Constructor ===
    public Funcion(String funcionID, String fecha, String hora, Pelicula pelicula, Sala sala) {
        this.funcionID = funcionID;
        this.fecha = fecha;
        this.hora = hora;
        this.pelicula = pelicula;
        this.sala = sala;
    }

    // === Getters ===
    public String getFuncionID() { return funcionID; }
    public String getFecha() { return fecha; }
    public String getHora() { return hora; }
    public Pelicula getPelicula() { return pelicula; }
    public Sala getSala() { return sala; }
}