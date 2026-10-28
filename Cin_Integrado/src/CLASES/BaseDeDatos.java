package CLASES;

import CLASES.Pelicula;
import java.util.LinkedList;
import java.util.List;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Collections;

/**
 * Actúa como una base de datos en memoria: guarda la lista de películas
 * disponibles y próximas, y permite buscarlas por ID.
 */
public class BaseDeDatos {

    // === Listas estáticas con todas las películas del sistema ===
    public static List<Pelicula> peliculasDisponibles = new LinkedList<>();
    public static List<Pelicula> peliculasProximamente = new LinkedList<>();

    // Registro de asientos ya vendidos
    public static HashMap<String, Set<String>> asientosOcupadosPorFuncion = new HashMap<>();

    // Arma la clave única de una función a partir de la película, la fecha y la hora
    public static String claveFuncion(int idPelicula, String fecha, String hora) {
        return idPelicula + "_" + fecha + "_" + hora;
    }

    // Marca como ocupados los asientos indicados
    // Se llama desde Pago.java justo cuando se confirma una compra.
    public static void marcarAsientosOcupados(String clave, List<String> codigosAsientos) {
        asientosOcupadosPorFuncion.computeIfAbsent(clave, k -> new HashSet<>()).addAll(codigosAsientos);
    }

    // Devuelve los códigos de asiento ya ocupados para esa función (vacío si no hay ninguno aún).
    // Se llama desde Asientos.java al armar la sala, para pintar como "Ocupado"
    // cualquier butaca que ya haya sido comprada antes.
    public static Set<String> obtenerAsientosOcupados(String clave) {
        return asientosOcupadosPorFuncion.getOrDefault(clave, Collections.emptySet());
    }

    static {
        // ==========================================
        // 1. PELÍCULAS DISPONIBLES 
        // ==========================================

        Pelicula p1 = new Pelicula(
            1, 
            "SCREAM 7",             //Titulo
            "Años después de intentar dejar atrás su pasado, Sidney Prescott vive una vida tranquila con su familia." //Sinopsis
            + "Pero todo cambia cuando un nuevo asesino bajo la máscara de Ghostface aparece, poniendo como"
            + "objetivo a su hija. Obligada a enfrentar nuevamente sus peores miedos, Sidney deberá luchar para"
            + "proteger a su familia y acabar con la nueva ola de terror.",                                          
            "1 hr 54 min",          //Duraciön      
            "| +16 | R",            //Clasificaciön
            "/imagenes/cartelera/Scream7.jpg",  //Ruta de la Imagen
            new String[][] {
                {"02:10 PM", "04:25 PM", "07:00 PM"}, // Índice 0 -> HOY
                {"01:00 PM", "03:30 PM", "06:00 PM"}, // Índice 1 -> MAÑANA
                {"08:00 PM", "10:30 PM"}              // Índice 2 -> JUEVES
            }
        );

        Pelicula p2 = new Pelicula(
            2, 
            "Las locuras del Emperador", 
            "El arrogante emperador Kuzco vive rodeado de lujos y solo piensa en sí mismo, pero su vida da un giro inesperado cuando su ambiciosa consejera lo transforma en una llama. "
            + "Obligado a abandonar su comodidad, Kuzco se ve forzado a unir fuerzas con un humilde campesino llamado Pacha para poder regresar al palacio. "
            + "En el camino, aprenderá importantes lecciones sobre la amistad, la humildad y el verdadero significado de ser un buen gobernante.",
            "1 hr 18 min", 
            "| +6 | APT", 
            "/imagenes/cartelera/LasLocurasDelEmperador.jpg",
            new String[][] {
                {"10:00 AM", "12:30 PM", "03:00 PM", "05:15 PM"}, // Índice 0 -> HOY
                {"11:00 AM", "01:00 PM", "04:00 PM", "06:30 PM"}, // Índice 1 -> MAÑANA
                {"02:00 PM", "05:00 PM"}                          // Índice 2 -> JUEVES
            }
        );

        Pelicula p3 = new Pelicula(
            3, 
            "Super Mario Galaxy La Pelicula", 
            "Mario, un valiente héroe del Reino Champiñón, se embarca en una nueva aventura cuando una misteriosa amenaza surge desde las galaxias. "
            + "Acompañado de sus amigos, deberá viajar por distintos planetas enfrentando peligros y desafiando las fuerzas de Bowser, quien busca dominar el universo. "
            + "En su travesía, Mario descubrirá el verdadero poder de la amistad y el valor necesario para proteger a todos los mundos.",
            "2 hr 4 min", 
            "| +7 | APT", 
            "/imagenes/cartelera/Mario.jpg",
            new String[][] {
                {"11:00 AM", "01:45 PM", "04:30 PM", "06:20 PM"}, // Índice 0 -> HOY
                {"10:30 AM", "02:00 PM", "05:00 PM", "07:30 PM"}, // Índice 1 -> MAÑANA
                {"12:00 PM", "03:00 PM", "06:00 PM"}              // Índice 2 -> JUEVES
            }
        );

        Pelicula p4 = new Pelicula(
            4, 
            "Que paso ayer partIII", 
            "Tras los impactantes sucesos del día anterior, los protagonistas intentan entender lo ocurrido mientras enfrentan las consecuencias de sus decisiones. "
            + "Las tensiones aumentan y nuevos secretos salen a la luz, complicando aún más la situación y poniendo en riesgo todo lo que creían seguro. "
            + "En esta tercera parte, deberán tomar decisiones cruciales que definirán su destino y el rumbo de la historia.",
            "1 hr 30 min ", 
            "| +18 | B", 
            "/imagenes/cartelera/QuepasoAyer.jpg",
            new String[][] {
                {"08:00 PM", "10:30 PM", "11:50 PM"}, // Índice 0 -> HOY
                {"07:30 PM", "09:45 PM", "11:00 PM"}, // Índice 1 -> MAÑANA
                {"09:00 PM", "11:15 PM"}              // Índice 2 -> JUEVES
            }
        );

        Pelicula p5 = new Pelicula(
            5, 
            "BOULEVARD", 
            "Nolan Mack, un hombre atrapado en una vida monótona, comienza a cuestionar su rutina y su identidad tras años de conformidad. "
            + "Todo cambia cuando entabla una inesperada relación que lo lleva a enfrentar sus propios deseos y emociones reprimidas. "
            + "A medida que se adentra en este nuevo camino, deberá decidir entre seguir viviendo en la comodidad de lo conocido o arriesgarlo todo por descubrir quién es realmente.",
            "1 hr 54 min ", 
            "| +14 | C ", 
            "/imagenes/cartelera/BOULEVARD.jpg",
            new String[][] {
                {"03:15 PM", "05:45 PM", "08:15 PM"}, // Índice 0 -> HOY
                {"04:00 PM", "06:30 PM", "09:00 PM"}, // Índice 1 -> MAÑANA
                {"05:30 PM", "08:45 PM"}              // Índice 2 -> JUEVES
            }
        );
        
        // Agregamos a la LinkedList
        peliculasDisponibles.add(p1);
        peliculasDisponibles.add(p2);
        peliculasDisponibles.add(p3);
        peliculasDisponibles.add(p4);
        peliculasDisponibles.add(p5);
        
        // ==========================================
        // 2. PELÍCULAS PRÓXIMAMENTE 
        // ==========================================

        Pelicula p6 = new Pelicula(
            6, 
            "El Bufón 2", 
            "Tras los caóticos eventos anteriores, Arthur Fleck se encuentra aún más hundido en la oscuridad mientras su identidad como el Bufón comienza a influir en toda la ciudad. "
            + "A medida que su figura se convierte en un símbolo para los marginados, nuevos conflictos surgen y el caos se expande sin control. "
            + "Entre la locura y la realidad, Arthur deberá enfrentar las consecuencias de sus actos mientras su legado crece de forma impredecible.",
            "1 hr 26 min ", 
            "| +14 | B", 
            "/imagenes/cartelera/El Bufon2.png",
            new String[][] {
                {"20 Mayo - 06:30 PM", "20 Mayo - 09:15 PM"}, // Fecha 1
                {"21 Mayo - 04:00 PM", "21 Mayo - 07:30 PM"}, // Fecha 2
                {"22 Mayo - 08:00 PM"}                          // Fecha 3
            }
        );

        Pelicula p7 = new Pelicula(
            7, 
            "El Gato", 
            "Don Ares de la Dagracia intenta dejar atrás su vida como el legendario Gato para dedicarse por completo a su familia. "
            + "Sin embargo, cuando una peligrosa conspiración amenaza la libertad de su pueblo de tambo real, se ve obligado a volver a usar la máscara. "
            + "Dividido entre su deber como héroe y su responsabilidad como esposo y padre, Ares deberá encontrar la forma de proteger a su pueblo "
            + "sin perder a quienes más ama.",
            "2 hr 4 min ", 
            "| +18 | R ", 
            "/imagenes/cartelera/El Gato.png",
            new String[][] {
                {"25 Mayo - 10:00 AM", "25 Mayo - 01:30 PM"}, 
                {"26 Mayo - 03:00 PM", "26 Mayo - 06:45 PM"}
            }
        );

        Pelicula p8 = new Pelicula(
            8, 
            "El hombre que araña", 
            "En una ciudad hecha mierda, llena de quilombos y tipos turbios, vive Peter Parker, un salame que no destacaba en nada… "
            + "hasta que una araña mutante lo pica y lo deja con poderes rarísimos y con ganas de shota. "
            + "Ahora el flaco trepa paredes, tira telarañas y se manda cagadas a nivel profesional. "
            + "Así nace El Hombre que Araña, un héroe en calzas que intenta salvar la ciudad, pero entre villanos desquiciados y decisiones más boludas que inteligentes, "
            + "termina metido en problemas cada vez peores. "
            + "Una parodia cargada de acción, humor sin filtro y situations tan absurdas que no sabés si reír o preguntar qué mierda está pasando sapeeeeeeeeeeeeeeee.",
            "1 hr 30 min ", 
            "| +18 | R ", 
            "/imagenes/cartelera/El hombre que araña.png",
            new String[][] {
                {"28 Mayo - 08:00 PM", "28 Mayo - 11:30 PM"}, 
                {"29 Mayo - 11:59 PM"} // Función de medianoche
            }
        );

        Pelicula p9 = new Pelicula(
            9, 
            "Que paso ayer partIV", 
            "En una ciudad iluminada por neones y llena de caos nocturno, una brillante estrella y un ornitorrinco de peluche despiertan sin recordar absolutamente nada de lo ocurrido la noche anterior. "
            + "Confundidos y rodeados de pistas absurdas, descubren que estuvieron envueltos en una serie de eventos descontrolados que los llevó desde persecuciones dentro de un casino hasta decisiones completamente inesperadas. "
            + "Mientras intentan reconstruir lo sucedido, se enfrentan a personajes extraños y situaciones cada vez más ridículas, descubriendo poco a poco que incluso llegaron a casarse… sin tener la menor idea de cómo o por qué. "
            + "En esta aventura llena de acción, humor y misterio, deberán unir las piezas de una noche inolvidable que, irónicamente, ninguno de los dos puede recordar.",
            "1 hr 30 min ", 
            "| +14 | B", 
            "/imagenes/cartelera/Que paso ayer.png",
            new String[][] {
                {"02 Junio - 03:30 PM", "02 Junio - 05:45 PM"}, 
                {"03 Junio - 07:00 PM", "03 Junio - 10:15 PM"}
            }
        );

        Pelicula p10 = new Pelicula(
            10, 
            "Capitan Peru: el primer inka", 
            "Antes del Tahuantinsuyo, surge Capitán Perú, un guerrero que une a los pueblos andinos para defender su tierra. "
            + "Con el tiempo, regresa para enfrentar a invasores extranjeros y luchar por el destino de su civilización, convirtiéndose en símbolo de resistencia y honor.",
            "2 hr 4 min ", 
            "| +14 | APT", 
            "/imagenes/cartelera/Capitan.png",
            new String[][] {
                {"12 Junio - 11:00 AM", "12 Junio - 02:30 PM"}, 
                {"13 Junio - 04:00 PM", "13 Junio - 08:30 PM"}
            }
        );
        
        // Agregamos a la LinkedList
        peliculasProximamente.add(p6);
        peliculasProximamente.add(p7);
        peliculasProximamente.add(p8);
        peliculasProximamente.add(p9);
        peliculasProximamente.add(p10);

    }

    // === Búsqueda de una película por su ID en ambas listas ===
    public static Pelicula buscarPeliculaPorId(int idBuscado) {
        // Buscamos primero entre las disponibles
        for (Pelicula p : peliculasDisponibles) {
            if (p.getId() == idBuscado) {
                return p;
            }
        }
        // Si no está, buscamos entre las próximas a estrenar
        for (Pelicula p : peliculasProximamente) {
            if (p.getId() == idBuscado) {
                return p;
            }
        }
        return null; // No existe una película con ese ID
    }
}