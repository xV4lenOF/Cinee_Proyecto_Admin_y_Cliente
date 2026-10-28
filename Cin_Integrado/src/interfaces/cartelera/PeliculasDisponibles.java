package interfaces.cartelera;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import CLASES.BaseDeDatos;
import CLASES.Pelicula;
import interfaces.Horarios;
import interfaces.Horarios;
/**
 * Panel que muestra en pósters todas las películas actualmente en cartelera
 * (CLASES.BaseDeDatos.peliculasDisponibles). Al hacer clic en un póster,
 * navega a la pantalla de Horarios de esa película.
 */
public class PeliculasDisponibles extends javax.swing.JPanel {

    public PeliculasDisponibles() {
        initComponents();
        lblVisual.setVisible(false);
        lblVisual1.setVisible(false);
        lblVisual2.setVisible(false);
        lblVisual3.setVisible(false);
        lblVisual4.setVisible(false);
        
        
        cargarPeliculasDinamicas(); // ¡Aquí jala todo de la BD!
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        Peliculas = new javax.swing.JLabel();
        lblVisual3 = new javax.swing.JLabel();
        lblVisual2 = new javax.swing.JLabel();
        lblVisual1 = new javax.swing.JLabel();
        lblVisual4 = new javax.swing.JLabel();
        lblVisual = new javax.swing.JLabel();
        fondo = new javax.swing.JLabel();

        setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(102, 255, 51));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Peliculas.setBackground(new java.awt.Color(250, 204, 21));
        Peliculas.setFont(new java.awt.Font("Tahoma", 1, 50)); // NOI18N
        Peliculas.setForeground(new java.awt.Color(250, 204, 21));
        Peliculas.setText("Nuevos Lanzamientos ");
        Peliculas.setToolTipText("240");
        Peliculas.setIconTextGap(240);
        jPanel1.add(Peliculas, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, -1, -1));

        lblVisual3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 0), 1, true));
        jPanel1.add(lblVisual3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 130, 230, 320));

        lblVisual2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 0), 1, true));
        jPanel1.add(lblVisual2, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 130, 230, 320));

        lblVisual1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 0), 1, true));
        jPanel1.add(lblVisual1, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 130, 230, 320));

        lblVisual4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 0), 1, true));
        jPanel1.add(lblVisual4, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 130, 230, 320));

        lblVisual.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 0), 1, true));
        jPanel1.add(lblVisual, new org.netbeans.lib.awtextra.AbsoluteConstraints(1090, 130, 230, 320));

        fondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fondo/fondo .png"))); // NOI18N
        jPanel1.add(fondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        add(jPanel1, java.awt.BorderLayout.NORTH);
    }// </editor-fold>//GEN-END:initComponents
    
    private void cargarPeliculasDinamicas() {
        // La posición X inicial (la primera peli a la izquierda)
        int posicionX = 16; 
        int posicionY = 125; // La altura a la que están todas

        // Recorremos el arreglo de películas disponibles
        for (Pelicula p : BaseDeDatos.peliculasDisponibles) {
            // Verificamos que la posición en el arreglo no esté vacía
            if (p != null) {
                // 1. Creamos la "cartilla" de la película (un JLabel)
                javax.swing.JLabel lblPoster = new javax.swing.JLabel();
                
                // 2. Le ponemos su imagen jalada de la Base de Datos
                lblPoster.setIcon(new javax.swing.ImageIcon(getClass().getResource(p.getRutaImagen())));
                
                // 3. Le ponemos tu borde amarillo
                lblPoster.setBorder(javax.swing.BorderFactory.createMatteBorder(5, 5, 5, 5, new java.awt.Color(250, 204, 21)));
                lblPoster.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

                // 4. LE DAMOS LA ACCIÓN DE CLIC (Aquí le pasamos el ID automático)
                lblPoster.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        // Creamos el panel HORARIOS pasándole el ID de esta película
                        Horarios interfaz2 = new Horarios(p.getId());
                        javax.swing.JFrame frame = (javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(lblPoster);
                        CLASES.Navegador.irA(frame, interfaz2);
                    }
                });

                // 5. Agregamos el poster al jPanel1 usando las coordenadas
                jPanel1.add(lblPoster, new org.netbeans.lib.awtextra.AbsoluteConstraints(posicionX, posicionY, -1, -1), 0);                
                // 6. Sumamos 268 píxeles a la X para que la siguiente película aparezca a la derecha
                posicionX += 268; 
            }
        }
        
        // Refrescamos el panel para que se dibujen las imágenes
        jPanel1.revalidate();
        jPanel1.repaint();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Peliculas;
    private javax.swing.JLabel fondo;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblVisual;
    private javax.swing.JLabel lblVisual1;
    private javax.swing.JLabel lblVisual2;
    private javax.swing.JLabel lblVisual3;
    private javax.swing.JLabel lblVisual4;
    // End of variables declaration//GEN-END:variables
}
