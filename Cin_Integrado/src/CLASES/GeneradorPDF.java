package CLASES;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.awt.image.BufferedImage; // Representa la imagen ya cargada en memoria, píxel por píxel
import java.io.InputStream;          // Para leer el archivo qr.png como flujo de bytes
import java.util.zip.Deflater;       // Comprime los píxeles antes de guardarlos en el PDF
import javax.imageio.ImageIO;        // Decodifica el archivo PNG y lo convierte en BufferedImage

/**
 * Genera comprobantes de venta en formato PDF SIN usar ninguna librería externa
 * Construye el archivo PDF "a mano"
 */
public class GeneradorPDF {

    private final List<String> lineas = new ArrayList<>();
    private byte[] pixelesImagenRGB; // Guarda el color (R,G,B) de cada pixel del QR
    private int imgAnchoPx, imgAltoPx; // Ancho y alto de la imagen original, en píxeles
    private double imgAnchoPt, imgAltoPt; // Ancho y alto final dentro del PDF, en puntos
    private int indiceImagen = -1; // Después de qué línea de texto se debe dibujar la imagen
    public void agregarLinea(String texto) {
        lineas.add(texto == null ? "" : texto);
    }

    public void agregarLineaVacia() {
        lineas.add("");
    }
    
// Carga el qr.png y guarda sus píxeles para poder incrustarlos en el PDF
public void agregarImagenQR(InputStream inputStream, double anchoMM, double altoMM) throws IOException {
    BufferedImage img = ImageIO.read(inputStream); // Decodifica el PNG
    if (img == null) {
        throw new IOException("No se pudo leer la imagen del QR");
    }
    imgAnchoPx = img.getWidth();
    imgAltoPx = img.getHeight();

    pixelesImagenRGB = new byte[imgAnchoPx * imgAltoPx * 3];
    int idx = 0;
    // Recorre cada pixel y guarda su Rojo, Verde y Azul por separado
    for (int y = 0; y < imgAltoPx; y++) {
        for (int x = 0; x < imgAnchoPx; x++) {
            int rgb = img.getRGB(x, y);
            pixelesImagenRGB[idx++] = (byte) ((rgb >> 16) & 0xFF); // Rojo
            pixelesImagenRGB[idx++] = (byte) ((rgb >> 8) & 0xFF);  // Verde
            pixelesImagenRGB[idx++] = (byte) (rgb & 0xFF);         // Azul
        }
    }

    // Convierte milímetros a puntos PDF (1 mm = 72/25.4 puntos)
    imgAnchoPt = anchoMM * 72.0 / 25.4;
    imgAltoPt = altoMM * 72.0 / 25.4;
    indiceImagen = lineas.size(); // Se dibuja después de la última línea de texto agregada hasta ahora
}

// Comprime los bytes de la imagen (obligatorio: el PDF no guarda imágenes sin comprimir)
private byte[] comprimir(byte[] datos) throws IOException {
    Deflater deflater = new Deflater();
    deflater.setInput(datos);
    deflater.finish();
    ByteArrayOutputStream salida = new ByteArrayOutputStream();
    byte[] buf = new byte[4096];
    while (!deflater.finished()) {
        int n = deflater.deflate(buf);
        salida.write(buf, 0, n);
    }
    deflater.end();
    return salida.toByteArray();
}
    /**
     * Genera el archivo dentro de Escritorio/venta/<nombreBase>_<fechaHora>.pdf
     * - Si la carpeta "venta" ya existe, NO se vuelve a crear, solo se agrega el PDF adentro.
     * - Cada venta genera un archivo con nombre distinto (incluye fecha y hora), así que
     *   nunca se sobrescribe un comprobante anterior.
     *
     * @return la ruta completa del archivo PDF generado.
     */
    public String generarArchivo(String nombreBase) throws IOException {
        File carpetaVenta = obtenerCarpetaVenta();

        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String nombreArchivo = nombreBase + "_" + timestamp + ".pdf";
        File archivoPdf = new File(carpetaVenta, nombreArchivo);

        try (FileOutputStream fos = new FileOutputStream(archivoPdf)) {
            fos.write(construirPdfBytes());
        }

        return archivoPdf.getAbsolutePath();
    }
/**
     * Genera un número correlativo persistente (ej: V-00001, V-00002, ...).
     * Guarda el último número usado en un archivo contador dentro de Escritorio/venta/,
     * así el conteo sigue avanzando aunque cierres y vuelvas a abrir el programa.
     */
    public String generarCorrelativo(String prefijo) throws IOException {
        File carpetaVenta = obtenerCarpetaVenta();
        File archivoContador = new File(carpetaVenta, "contador_" + prefijo + ".txt");

        int siguienteNumero = 1;
        if (archivoContador.exists()) {
            String contenido = new String(
                    java.nio.file.Files.readAllBytes(archivoContador.toPath()),
                    StandardCharsets.UTF_8).trim();
            try {
                siguienteNumero = Integer.parseInt(contenido) + 1;
            } catch (NumberFormatException e) {
                siguienteNumero = 1; // si el archivo estaba corrupto, reinicia en 1
            }
        }

        // Guarda el nuevo valor para la próxima vez
        java.nio.file.Files.write(archivoContador.toPath(),
                String.valueOf(siguienteNumero).getBytes(StandardCharsets.UTF_8));

        return prefijo + "-" + String.format("%05d", siguienteNumero);
    }
    // Ubica (o crea, solo si no existe) la carpeta "venta" dentro del Escritorio del usuario
    private File obtenerCarpetaVenta() {
        String home = System.getProperty("user.home");
        File escritorio = new File(home, "Desktop");

        // Algunas instalaciones de Windows en español llaman a la carpeta "Escritorio"
        if (!escritorio.exists()) {
            File alterno = new File(home, "Escritorio");
            if (alterno.exists()) {
                escritorio = alterno;
            }
        }

        File carpetaVenta = new File(escritorio, "venta");
        if (!carpetaVenta.exists()) {
            carpetaVenta.mkdirs(); // Solo la crea la primera vez
        }
        return carpetaVenta;
    }

    // ================== CONSTRUCCIÓN MANUAL DEL PDF ==================

    private byte[] construirPdfBytes() throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        List<Integer> offsets = new ArrayList<>(); // Posición (en bytes) de cada objeto, para la tabla xref

        escribir(buffer, "%PDF-1.4\n");

        // Objeto 1: Catálogo
        offsets.add(buffer.size());
        escribir(buffer, "1 0 obj\n<< /Type /Catalog /Pages 2 0 R >>\nendobj\n");

        // Objeto 2: Árbol de páginas
        offsets.add(buffer.size());
        escribir(buffer, "2 0 obj\n<< /Type /Pages /Kids [3 0 R] /Count 1 >>\nendobj\n");

        // Objeto 3: Página (800 x 800 puntos, tamaño personalizado)
        boolean hayImagen = (pixelesImagenRGB != null);
        String recursos = hayImagen
                ? "/Resources << /Font << /F1 5 0 R >> /XObject << /ImgQR 6 0 R >> >> "
                : "/Resources << /Font << /F1 5 0 R >> >> ";
        offsets.add(buffer.size());
        escribir(buffer, "3 0 obj\n<< /Type /Page /Parent 2 0 R " + recursos
                + "/MediaBox [0 0 800 800] /Contents 4 0 R >>\nendobj\n");
        
        // Objeto 4: Contenido de texto de la página
        String contenido = construirContenidoTexto();
        byte[] contenidoBytes = contenido.getBytes(StandardCharsets.ISO_8859_1);
        offsets.add(buffer.size());
        escribir(buffer, "4 0 obj\n<< /Length " + contenidoBytes.length + " >>\nstream\n");
        buffer.write(contenidoBytes);
        escribir(buffer, "\nendstream\nendobj\n");

        // Objeto 5: Fuente estándar Helvetica con acentos en español (WinAnsiEncoding)
        offsets.add(buffer.size());
        escribir(buffer, "5 0 obj\n<< /Type /Font /Subtype /Type1 /BaseFont /Helvetica "
                + "/Encoding /WinAnsiEncoding >>\nendobj\n");
        
        // Objeto 6: Imagen QR (solo si se cargó una)
        if (hayImagen) {
            byte[] comprimido = comprimir(pixelesImagenRGB);
            offsets.add(buffer.size());
            escribir(buffer, "6 0 obj\n<< /Type /XObject /Subtype /Image /Width " + imgAnchoPx
                    + " /Height " + imgAltoPx + " /ColorSpace /DeviceRGB /BitsPerComponent 8 "
                    + "/Filter /FlateDecode /Length " + comprimido.length + " >>\nstream\n");
            buffer.write(comprimido);
            escribir(buffer, "\nendstream\nendobj\n");
        }

        // Tabla xref
        int posXref = buffer.size();
        int totalObjetos = offsets.size() + 1; // +1 por el objeto libre 0
        escribir(buffer, "xref\n0 " + totalObjetos + "\n");
        escribir(buffer, "0000000000 65535 f \n");
        for (int off : offsets) {
            escribir(buffer, String.format("%010d 00000 n \n", off));
        }

        // Trailer
        escribir(buffer, "trailer\n<< /Size " + totalObjetos + " /Root 1 0 R >>\n"
                + "startxref\n" + posXref + "\n%%EOF");

        return buffer.toByteArray();
    }

    private void escribir(ByteArrayOutputStream buffer, String texto) throws IOException {
        buffer.write(texto.getBytes(StandardCharsets.ISO_8859_1));
    }

// Convierte las líneas guardadas en operadores de texto del PDF (e inserta la imagen si hay una)
    private String construirContenidoTexto() {
        StringBuilder sb = new StringBuilder();
        double x = 50;
        double y = 750; // un poco más abajo, porque la página ahora es 800x800 en vez de 595x842
        double leading = 14;

        sb.append("BT\n/F1 11 Tf\n");
        sb.append(x).append(" ").append(y).append(" Td\n");

        for (int i = 0; i < lineas.size(); i++) {
            if (i > 0) {
                sb.append("0 -").append(leading).append(" Td\n");
                y -= leading;
            }
            sb.append("(").append(escaparTexto(lineas.get(i))).append(") Tj\n");

            if (indiceImagen == i + 1) {
                sb.append("ET\n");
                double yImagen = y - 8 - imgAltoPt;
                sb.append("q\n");
                sb.append(imgAnchoPt).append(" 0 0 ").append(imgAltoPt).append(" ")
                        .append(x).append(" ").append(yImagen).append(" cm\n");
                sb.append("/ImgQR Do\n");
                sb.append("Q\n");
                y = yImagen - 10;
                sb.append("BT\n/F1 11 Tf\n");
                sb.append(x).append(" ").append(y).append(" Td\n");
            }
        }
        sb.append("ET");
        return sb.toString();
    }

    // Escapa paréntesis y backslashes, obligatorio dentro de un string de PDF
    private String escaparTexto(String texto) {
        return texto.replace("\\", "\\\\").replace("(", "\\(").replace(")", "\\)");
    }
}
