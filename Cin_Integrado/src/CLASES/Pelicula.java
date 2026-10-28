package CLASES;

/**
 * Representa una película de la cartelera (disponible o próximamente),
 * junto con sus horarios disponibles por día.
 */
public class Pelicula {

    // === Atributos de la película ===
    private int id;
    private String titulo;
    private String sinopsis;
    private String duracion;
    private String clasificacion;
    private String rutaImagen;
    private String[][] horariosPorDia;

    // === Constructor ===
    public Pelicula(int id, String titulo, String sinopsis, String duracion, String clasificacion, String rutaImagen, String[][] horariosPorDia) {
        this.id = id;
        this.titulo = titulo;
        this.sinopsis = sinopsis;
        this.duracion = duracion;
        this.clasificacion = clasificacion;
        this.rutaImagen = rutaImagen;
        this.horariosPorDia = horariosPorDia;
    }

    // === Getters ===
    public int getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getSinopsis() { return sinopsis; }
    public String getDuracion() { return duracion; }
    public String getClasificacion() { return clasificacion; }
    public String getRutaImagen() { return rutaImagen; }
    public String[][] getHorariosPorDia() { return horariosPorDia; }
}