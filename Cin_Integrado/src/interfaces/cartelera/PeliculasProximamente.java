package interfaces.cartelera;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import CLASES.BaseDeDatos;
import CLASES.Pelicula;
import interfaces.Horarios;
/**
 * Panel que muestra en pósters las películas próximas a estrenar
 * (CLASES.BaseDeDatos.peliculasProximamente). Al hacer clic en un póster,
 * navega a la pantalla de Horarios, donde se simula la fila de espera
 * virtual (preventa) para ese estreno.
 */
public class PeliculasProximamente extends javax.swing.JPanel {

    public PeliculasProximamente() {
        initComponents();
        lblVisual.setVisible(false);
        lblVisual1.setVisible(false);
        lblVisual2.setVisible(false);
        lblVisual3.setVisible(false);
        lblVisual4.setVisible(false);
        
        cargarPeliculasProximamente();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        estrenos = new javax.swing.JLabel();
        lblVisual = new javax.swing.JLabel();
        lblVisual1 = new javax.swing.JLabel();
        lblVisual2 = new javax.swing.JLabel();
        lblVisual3 = new javax.swing.JLabel();
        lblVisual4 = new javax.swing.JLabel();
        fondo1 = new javax.swing.JLabel();

        setLayout(new java.awt.BorderLayout());

        jPanel2.setBackground(new java.awt.Color(102, 255, 51));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        estrenos.setBackground(new java.awt.Color(250, 204, 21));
        estrenos.setFont(new java.awt.Font("Tahoma", 1, 50)); // NOI18N
        estrenos.setForeground(new java.awt.Color(250, 204, 21));
        estrenos.setText("Proximos estrenos ");
        estrenos.setToolTipText("240");
        estrenos.setIconTextGap(240);
        jPanel2.add(estrenos, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, -1, -1));

        lblVisual.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 0), 1, true));
        jPanel2.add(lblVisual, new org.netbeans.lib.awtextra.AbsoluteConstraints(1090, 130, 230, 320));

        lblVisual1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 0), 1, true));
        jPanel2.add(lblVisual1, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 130, 230, 320));

        lblVisual2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 0), 1, true));
        jPanel2.add(lblVisual2, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 130, 230, 320));

        lblVisual3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 0), 1, true));
        jPanel2.add(lblVisual3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 130, 230, 320));

        lblVisual4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 0), 1, true));
        jPanel2.add(lblVisual4, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 130, 230, 320));

        fondo1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fondo/fondo .png"))); // NOI18N
        jPanel2.add(fondo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        add(jPanel2, java.awt.BorderLayout.NORTH);
    }// </editor-fold>//GEN-END:initComponents

    private void cargarPeliculasProximamente() {
        // Misma posición inicial que usaste en tu diseño
        int posicionX = 16; 
        int posicionY = 125; 

        // OJO AQUÍ: Recorremos el arreglo de PROXIMAMENTE
        for (Pelicula p : BaseDeDatos.peliculasProximamente) {
            // Verificamos que la posición en el arreglo no esté vacía
            if (p != null) {
                // Creamos el JLabel para el poster
                javax.swing.JLabel lblPoster = new javax.swing.JLabel();
                
                // Le ponemos su imagen jalada de la Base de Datos
                lblPoster.setIcon(new javax.swing.ImageIcon(getClass().getResource(p.getRutaImagen())));
                lblPoster.setBorder(javax.swing.BorderFactory.createMatteBorder(5, 5, 5, 5, new java.awt.Color(250, 204, 21)));
                lblPoster.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

                // Acción de clic para abrir el panel HORARIOS con su ID
                lblPoster.addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        Horarios interfaz2 = new Horarios(p.getId());
                        javax.swing.JFrame frame = (javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(lblPoster);
                        CLASES.Navegador.irA(frame, interfaz2);
                    }
                });

                //
                jPanel2.add(lblPoster, new org.netbeans.lib.awtextra.AbsoluteConstraints(posicionX, posicionY, -1, -1), 0);
                // Sumamos los píxeles para que la siguiente salga a la derecha
                posicionX += 268; 
            }
        }
        
        // Refrescamos jPanel2
        jPanel2.revalidate();
        jPanel2.repaint();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel estrenos;
    private javax.swing.JLabel fondo1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblVisual;
    private javax.swing.JLabel lblVisual1;
    private javax.swing.JLabel lblVisual2;
    private javax.swing.JLabel lblVisual3;
    private javax.swing.JLabel lblVisual4;
    // End of variables declaration//GEN-END:variables
}
