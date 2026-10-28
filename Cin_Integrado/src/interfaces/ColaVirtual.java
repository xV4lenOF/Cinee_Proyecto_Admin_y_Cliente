package interfaces;

import java.awt.Color;

public class ColaVirtual extends javax.swing.JDialog {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ColaVirtual.class.getName());

    public ColaVirtual(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fondoColaVirtual = new javax.swing.JPanel();
        lblEstasColaVirtual = new javax.swing.JLabel();
        lblAdvertencia = new javax.swing.JLabel();
        pnlFondoDatosPeli = new javax.swing.JPanel();
        lblPelicula = new javax.swing.JLabel();
        lblFecha = new javax.swing.JLabel();
        lblPersonalDelanteTI = new javax.swing.JLabel();
        lblContador = new javax.swing.JLabel();
        BarraProgreso = new javax.swing.JProgressBar();
        pnlFondoInfo = new javax.swing.JPanel();
        lblInfo = new javax.swing.JLabel();
        fondoBtnSalir = new javax.swing.JPanel();
        txtBtnSalir = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        fondoColaVirtual.setBackground(new java.awt.Color(5, 20, 64));

        lblEstasColaVirtual.setFont(new java.awt.Font("Segoe UI", 1, 26)); // NOI18N
        lblEstasColaVirtual.setForeground(new java.awt.Color(255, 255, 255));
        lblEstasColaVirtual.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblEstasColaVirtual.setText("ESTÁS EN LA COLA VIRTUAL");

        lblAdvertencia.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblAdvertencia.setForeground(new java.awt.Color(250, 204, 21));
        lblAdvertencia.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAdvertencia.setText("¡ADVERTENCIA!  No cierres esta ventana, podrías perder tu lugar");

        pnlFondoDatosPeli.setBackground(new java.awt.Color(2, 13, 49));
        pnlFondoDatosPeli.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(52, 58, 71), 1, true));

        lblPelicula.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblPelicula.setForeground(new java.awt.Color(209, 213, 219));
        lblPelicula.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPelicula.setText("Pelicula: Titulo Peli");

        lblFecha.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblFecha.setForeground(new java.awt.Color(209, 213, 219));
        lblFecha.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblFecha.setText("Fecha: 00 Mes - 00:00 PM | Sala 01");

        javax.swing.GroupLayout pnlFondoDatosPeliLayout = new javax.swing.GroupLayout(pnlFondoDatosPeli);
        pnlFondoDatosPeli.setLayout(pnlFondoDatosPeliLayout);
        pnlFondoDatosPeliLayout.setHorizontalGroup(
            pnlFondoDatosPeliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFondoDatosPeliLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlFondoDatosPeliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblPelicula, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblFecha, javax.swing.GroupLayout.DEFAULT_SIZE, 622, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlFondoDatosPeliLayout.setVerticalGroup(
            pnlFondoDatosPeliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFondoDatosPeliLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(lblPelicula)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblFecha)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        lblPersonalDelanteTI.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblPersonalDelanteTI.setForeground(new java.awt.Color(143, 161, 204));
        lblPersonalDelanteTI.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPersonalDelanteTI.setText("PERSONAS DELANTE DE TI");

        lblContador.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        lblContador.setForeground(new java.awt.Color(255, 255, 255));
        lblContador.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblContador.setText("000");

        pnlFondoInfo.setBackground(new java.awt.Color(3, 14, 51));

        lblInfo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblInfo.setText("<html><div style='text-align: center; font-family: Segoe UI, sans-serif; color: #94a3b8; font-size: 11px; font-weight: bold;'>Debido a la alta demanda de este estreno, el tiempo de espera puede ser ligeramente mayor.<br>Ten paciencia y mantén tu lugar en la fila.<br><br>Aún hay butacas disponibles. Recuerda que la disponibilidad de los mejores asientos<br>depende del stock al momento de que sea tu turno.<br><br><span style='color: #cbd5e1;'><b>💡 Tip:</b></span> Ten lista tu tarjeta de crédito o débito para realizar el pago más rápido cuando ingreses.</div></html>\n");

        javax.swing.GroupLayout pnlFondoInfoLayout = new javax.swing.GroupLayout(pnlFondoInfo);
        pnlFondoInfo.setLayout(pnlFondoInfoLayout);
        pnlFondoInfoLayout.setHorizontalGroup(
            pnlFondoInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblInfo)
        );
        pnlFondoInfoLayout.setVerticalGroup(
            pnlFondoInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblInfo, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
        );

        fondoBtnSalir.setBackground(new java.awt.Color(5, 20, 64));

        txtBtnSalir.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtBtnSalir.setForeground(new java.awt.Color(255, 255, 255));
        txtBtnSalir.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtBtnSalir.setText("X");
        txtBtnSalir.setToolTipText("");
        txtBtnSalir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        txtBtnSalir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtBtnSalirMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txtBtnSalirMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                txtBtnSalirMouseExited(evt);
            }
        });

        javax.swing.GroupLayout fondoBtnSalirLayout = new javax.swing.GroupLayout(fondoBtnSalir);
        fondoBtnSalir.setLayout(fondoBtnSalirLayout);
        fondoBtnSalirLayout.setHorizontalGroup(
            fondoBtnSalirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtBtnSalir, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
        );
        fondoBtnSalirLayout.setVerticalGroup(
            fondoBtnSalirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtBtnSalir, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout fondoColaVirtualLayout = new javax.swing.GroupLayout(fondoColaVirtual);
        fondoColaVirtual.setLayout(fondoColaVirtualLayout);
        fondoColaVirtualLayout.setHorizontalGroup(
            fondoColaVirtualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fondoColaVirtualLayout.createSequentialGroup()
                .addContainerGap(42, Short.MAX_VALUE)
                .addGroup(fondoColaVirtualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lblEstasColaVirtual, javax.swing.GroupLayout.PREFERRED_SIZE, 427, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblAdvertencia, javax.swing.GroupLayout.PREFERRED_SIZE, 554, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlFondoDatosPeli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPersonalDelanteTI, javax.swing.GroupLayout.PREFERRED_SIZE, 482, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblContador, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BarraProgreso, javax.swing.GroupLayout.PREFERRED_SIZE, 723, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(42, Short.MAX_VALUE))
            .addComponent(pnlFondoInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, fondoColaVirtualLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(fondoBtnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        fondoColaVirtualLayout.setVerticalGroup(
            fondoColaVirtualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fondoColaVirtualLayout.createSequentialGroup()
                .addComponent(fondoBtnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(lblEstasColaVirtual)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblAdvertencia)
                .addGap(18, 18, 18)
                .addComponent(pnlFondoDatosPeli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(lblPersonalDelanteTI)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblContador)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(BarraProgreso, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pnlFondoInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(fondoColaVirtual, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(fondoColaVirtual, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtBtnSalirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtBtnSalirMouseClicked
        this.dispose();
    }//GEN-LAST:event_txtBtnSalirMouseClicked

    private void txtBtnSalirMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtBtnSalirMouseEntered
        //En este tipo de eventos se cambia el color del fondo y texto del boton cuando pase o salga el cursor del boton.
        fondoBtnSalir.setBackground(new Color(250,0,0)); //Color fondo (JPanel)
        txtBtnSalir.setForeground(new Color(255,255,255)); //Crlor letras (JLabel)
    }//GEN-LAST:event_txtBtnSalirMouseEntered

    private void txtBtnSalirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtBtnSalirMouseExited
        fondoBtnSalir.setBackground(new Color(5,20,64));
        txtBtnSalir.setForeground(new Color(255,255,255));
    }//GEN-LAST:event_txtBtnSalirMouseExited
    
    // ========================================================
    // MÉTODOS PARA CONTROLAR LA COLA DESDE AFUERA
    // ========================================================
    public void configurarDatos(String tituloPeli, String fechaHora) {
        lblPelicula.setText("Película: " + tituloPeli);
        lblFecha.setText("Fecha: " + fechaHora);
        
        // --- TRUCO PARA DOMINAR LA BARRA DE CARGA ---
        // 1. Le quitamos el estilo rebelde de Nimbus
        BarraProgreso.setUI(new javax.swing.plaf.basic.BasicProgressBarUI()); 
        // 2. Le ponemos el Azul Eléctrico (Cyan)
        BarraProgreso.setForeground(new java.awt.Color(0, 180, 216)); 
        // 3. El fondo de la barrita
        BarraProgreso.setBackground(new java.awt.Color(5, 20, 64)); 
        
        // Animación infinita
        BarraProgreso.setIndeterminate(true); 
    }

    public void actualizarTurno(int faltantes) {
        lblContador.setText(String.valueOf(faltantes));
    }

    public void mostrarEsTuTurno() {
        lblContador.setText("¡ES TU TURNO!");
        lblContador.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 36)); 
        lblContador.setForeground(new java.awt.Color(74, 222, 128)); // Verde
        
        // --- EFECTO FINAL DE LA BARRA ---
        BarraProgreso.setIndeterminate(false); // Detenemos la animación
        BarraProgreso.setValue(100); // Llenamos la barra al máximo
        // Cambiamos el color a Verde (#4ade80)
        BarraProgreso.setForeground(new java.awt.Color(74, 222, 128)); 
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                ColaVirtual dialog = new ColaVirtual(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar BarraProgreso;
    private javax.swing.JPanel fondoBtnSalir;
    private javax.swing.JPanel fondoColaVirtual;
    private javax.swing.JLabel lblAdvertencia;
    private javax.swing.JLabel lblContador;
    private javax.swing.JLabel lblEstasColaVirtual;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblInfo;
    private javax.swing.JLabel lblPelicula;
    private javax.swing.JLabel lblPersonalDelanteTI;
    private javax.swing.JPanel pnlFondoDatosPeli;
    private javax.swing.JPanel pnlFondoInfo;
    private javax.swing.JLabel txtBtnSalir;
    // End of variables declaration//GEN-END:variables
}
