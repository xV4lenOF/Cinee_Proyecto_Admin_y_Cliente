package interfaces.dulceria;

import CLASES.Detalle_Venta;
import CLASES.SesionActual;
import java.awt.Color;

public class DetalleCarrito extends javax.swing.JDialog {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(DetalleCarrito.class.getName());

    public DetalleCarrito(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(parent); // Para que salga bien centrada
        
        cargarCarritoVisual(); // Llamamos al método que dibuja la lista
    }
    
//################## Este metodo permite cargar el carrito a esta ventana Detalle ##################
    private void cargarCarritoVisual() {
        panelContenedorLista.removeAll();
        panelContenedorLista.setLayout(new javax.swing.BoxLayout(panelContenedorLista, javax.swing.BoxLayout.Y_AXIS));
        // FONDO DEL SCROLL (Azul Dulcería)
        panelContenedorLista.setBackground(new java.awt.Color(17,24,39)); 

        // =========================================================
        // CONDICIONAL: ¿EL CARRITO ESTÁ VACÍO?
        // =========================================================
        if (SesionActual.carritoDulceria.isEmpty()) {
            
            // Creamos el mensaje de para cuando no hay nada en el carrito
            String mensajeVacio = "<html><div style='text-align: center; margin-top: 50px;'>Aún no tienes snacks para tu función.<br><br><b style='color:#facc15;'>🎬 ¡Pásate por los combos y arma tu pedido!</b></div></html>";
            
            javax.swing.JLabel lblVacio = new javax.swing.JLabel(mensajeVacio);
            lblVacio.setForeground(java.awt.Color.WHITE);
            lblVacio.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 18));
            
            // Trucos para centrar el label en el medio del panel azul
            lblVacio.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT); 
            lblVacio.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            
            // Metemos el mensaje al panel
            panelContenedorLista.add(lblVacio);
            
        } else {
            // =========================================================
            // SI HAY PRODUCTOS, DIBUJAMOS LA LISTA NORMALMENTE
            // =========================================================
            for (Detalle_Venta dv : SesionActual.carritoDulceria) {

                // FILA DEL PRODUCTO
                javax.swing.JPanel fila = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 15, 15));
                fila.setBackground(new java.awt.Color(17, 24, 39)); 
                fila.setMaximumSize(new java.awt.Dimension(500, 85)); 
                fila.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(50, 60, 80))); 

                // 1. NOMBRE DEL PRODUCTO
                javax.swing.JLabel lblNombre = new javax.swing.JLabel("<html><body style='width: 170px'>" + dv.getProducto().getNombre() + "</body></html>");
                lblNombre.setForeground(java.awt.Color.WHITE);    
                lblNombre.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
                // Le bajamos un poquito el ancho al nombre (a 200) para darle espacio al precio
                lblNombre.setPreferredSize(new java.awt.Dimension(200, 50)); 

                // 2. ETIQUETA DE CANTIDAD
                javax.swing.JLabel lblCantidad = new javax.swing.JLabel(String.valueOf(dv.getCantidad()), javax.swing.SwingConstants.CENTER);
                lblCantidad.setForeground(new java.awt.Color(255, 255, 255));
                lblCantidad.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 18));
                lblCantidad.setPreferredSize(new java.awt.Dimension(30, 20)); 

                // 3. BOTONES
                javax.swing.JButton btnMenos = new javax.swing.JButton("-");
                javax.swing.JButton btnMas = new javax.swing.JButton("+");
                java.awt.Color colorBotones = new java.awt.Color(0, 9, 80);
                btnMenos.setBackground(colorBotones); btnMenos.setForeground(java.awt.Color.WHITE);
                btnMas.setBackground(colorBotones); btnMas.setForeground(java.awt.Color.WHITE);
                btnMenos.setFocusPainted(false); btnMas.setFocusPainted(false); 
                // CURSOR DE MANO
                btnMenos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
                btnMas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
                
                // ACCIONES DE LOS BOTONES
                // BOTON MENOS
                btnMenos.addActionListener(e -> {
                    int cant = Integer.parseInt(lblCantidad.getText());
                    if (cant > 0) {
                        cant--; // Reducimos 1

                        // 1. Actualizamos la memoria global sí o sí
                        SesionActual.actualizarProductoEnCarrito(dv.getProducto(), cant);

                        // =======================================================
                        // SOLUCIÓN AL BUG: Actualizamos el total AQUÍ ARRIBA, 
                        // antes de que los return corten la ejecución.
                        // =======================================================
                        if (interfaces.Dulceria.instanciaActual != null) {
                            interfaces.Dulceria.instanciaActual.actualizarTotalVisual();
                        }

                        // 2. LA MAGIA: ¿Llegó a cero?
                        if (cant == 0) {
                            // Desaparecemos toda la fila de la vista animadamente
                            panelContenedorLista.remove(fila);
                            
                            // Si borramos el último producto, recargamos la vista para que salga el mensaje
                            if (SesionActual.carritoDulceria.isEmpty()) {
                                cargarCarritoVisual();
                                return; // Ahora sí, cortamos el código tranquilos
                            }
                            
                            // Refrescamos el panel para que las otras filas suban y ocupen el hueco
                            panelContenedorLista.revalidate();
                            panelContenedorLista.repaint();
                        } else {
                            // Si aún queda 1 o más, solo actualizamos el numerito amarillo
                            lblCantidad.setText(String.valueOf(cant));
                        }
                    }
                });
                //BOTON MAS
                btnMas.addActionListener(e -> {
                    int cant = Integer.parseInt(lblCantidad.getText());
                    cant++;
                    lblCantidad.setText(String.valueOf(cant));
                    SesionActual.actualizarProductoEnCarrito(dv.getProducto(), cant);
                    if (interfaces.Dulceria.instanciaActual != null) {
                        interfaces.Dulceria.instanciaActual.actualizarTotalVisual();
                    }
                });

                // 4. NUEVO: ETIQUETA DEL PRECIO UNITARIO
                double precioUnidad = dv.getProducto().getPrecio();
                // Formateamos el precio para que siempre tenga 2 decimales
                javax.swing.JLabel lblPrecioUnitario = new javax.swing.JLabel(String.format("S/ %.2f", precioUnidad));
                lblPrecioUnitario.setForeground(new java.awt.Color(255, 255, 255)); // Un gris clarito para que no opaque a la cantidad
                lblPrecioUnitario.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 15));
                lblPrecioUnitario.setPreferredSize(new java.awt.Dimension(80, 20)); // Ancho fijo
                lblPrecioUnitario.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT); // Alineado a la derecha

                // EL ORDEN PERFECTO EN LA FILA
                fila.add(lblNombre);
                fila.add(btnMas);                   
                fila.add(lblCantidad);      
                fila.add(btnMenos);         
                fila.add(lblPrecioUnitario);

                panelContenedorLista.add(fila);
            }
        }
        
        panelContenedorLista.revalidate();
        panelContenedorLista.repaint();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        scrollContenedorLista = new javax.swing.JScrollPane();
        panelContenedorLista = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        fondoBtnSalir = new javax.swing.JPanel();
        txtBtnSalir = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        BtnSeguirComprando = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(0, 9, 39));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(96, 98, 99)));

        scrollContenedorLista.setBackground(new java.awt.Color(17, 24, 39));

        panelContenedorLista.setBackground(new java.awt.Color(17, 24, 39));
        panelContenedorLista.setLayout(new javax.swing.BoxLayout(panelContenedorLista, javax.swing.BoxLayout.LINE_AXIS));
        scrollContenedorLista.setViewportView(panelContenedorLista);

        jPanel2.setBackground(new java.awt.Color(0, 9, 39));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(96, 98, 99)));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Detalle Dulceria ");

        fondoBtnSalir.setBackground(new java.awt.Color(0, 9, 39));

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

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fondo/combo.png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(fondoBtnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(fondoBtnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        BtnSeguirComprando.setBackground(new java.awt.Color(0, 9, 80));
        BtnSeguirComprando.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        BtnSeguirComprando.setForeground(new java.awt.Color(255, 255, 255));
        BtnSeguirComprando.setText("Seguir Comprando");
        BtnSeguirComprando.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BtnSeguirComprando.addActionListener(this::BtnSeguirComprandoActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollContenedorLista)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(427, Short.MAX_VALUE)
                .addComponent(BtnSeguirComprando)
                .addGap(14, 14, 14))
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollContenedorLista, javax.swing.GroupLayout.PREFERRED_SIZE, 395, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(BtnSeguirComprando, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                .addGap(13, 13, 13))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setBounds(0, 0, 600, 500);
    }// </editor-fold>//GEN-END:initComponents

    private void txtBtnSalirMouseClicked(java.awt.event.MouseEvent evt) {                                         
        this.dispose();
    }                                        

    private void txtBtnSalirMouseEntered(java.awt.event.MouseEvent evt) {                                         
        //En este tipo de eventos se cambia el color del fondo y texto del boton cuando pase o salga el cursor del boton.
        fondoBtnSalir.setBackground(new Color(250,0,0)); //Color fondo (JPanel)
        txtBtnSalir.setForeground(new Color(250,250,250)); //Crlor letras (JLabel)
    }                                        

    private void txtBtnSalirMouseExited(java.awt.event.MouseEvent evt) {                                        
        fondoBtnSalir.setBackground(new Color(0,9,39));
        txtBtnSalir.setForeground(new Color(250,250,250));
    }                                       

    private void BtnSeguirComprandoActionPerformed(java.awt.event.ActionEvent evt) {                                                   
        this.dispose();
    }                                                  

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
                DetalleCarrito dialog = new DetalleCarrito(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton BtnSeguirComprando;
    private javax.swing.JPanel fondoBtnSalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel panelContenedorLista;
    private javax.swing.JScrollPane scrollContenedorLista;
    private javax.swing.JLabel txtBtnSalir;
    // End of variables declaration//GEN-END:variables
}
