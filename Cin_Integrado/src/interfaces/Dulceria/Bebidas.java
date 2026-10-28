package interfaces.dulceria;

import CLASES.Bebida;

public class Bebidas extends javax.swing.JPanel {
    private int cantGaseosaPeq = 0;
    private int cantGaseosaMed = 0;
    private int cantGaseosaGra = 0;
    
    // CREAMOS LOS OBJETOS 
    CLASES.Bebida bebidaPequeña = new CLASES.Bebida("B01", "Gaseosa Pequeña", 8.00, 100, true, "Pequeño", "Gaseosa");
    CLASES.Bebida bebidaMediana = new CLASES.Bebida("B02", "Gaseosa Mediana", 10.00, 100, true, "Mediano", "Gaseosa");
    CLASES.Bebida bebidaGrande = new CLASES.Bebida("B03", "Gaseosa Grande", 11.50, 100, true, "Grande", "Gaseosa");
    
    public Bebidas() {
        initComponents();
        // Forzamos a que los labels inicien en 0 visualmente
        CantidadPeq.setText("0");
        CantidadMed.setText("0");
        CantidadGrand.setText("0");
        // Sincronizamos con lo que haya en el carrito
        sincronizarConCarrito();
    }

// --- MÉTODO PARA SINCRONIZAR LOS DATOS DE LA SESION ACTUAL ---
    private void sincronizarConCarrito() {
        for (CLASES.Detalle_Venta dv : CLASES.SesionActual.carritoDulceria) {
            String idEnCarrito = dv.getProducto().getProductoID();

            if (idEnCarrito.equals(bebidaPequeña.getProductoID())) {
                this.cantGaseosaPeq = dv.getCantidad();
                CantidadPeq.setText(String.valueOf(this.cantGaseosaPeq));
            } else if (idEnCarrito.equals(bebidaMediana.getProductoID())) {
                this.cantGaseosaMed = dv.getCantidad();
                CantidadMed.setText(String.valueOf(this.cantGaseosaMed));
            } else if (idEnCarrito.equals(bebidaGrande.getProductoID())) {
                this.cantGaseosaGra = dv.getCantidad();
                CantidadGrand.setText(String.valueOf(this.cantGaseosaGra));
            }
        }
    }

// --- MÉTODO LÓGICO PARA CAMBIAR CANTIDAD Y ACTUALIZAR VARIABLES ---
    private void cambiarCantidad(javax.swing.JLabel label, int delta) {
        try {
            int valorActual = Integer.parseInt(label.getText());
            int nuevoValor = valorActual + delta;

            if (nuevoValor >= 0) {
                label.setText(String.valueOf(nuevoValor));

                // ENVIAMOS AL CARRITO Y ACTUALIZAMOS LA VARIABLE
                if (label == CantidadPeq) {
                    cantGaseosaPeq = nuevoValor;
                    CLASES.SesionActual.actualizarProductoEnCarrito(bebidaPequeña, cantGaseosaPeq);
                } else if (label == CantidadMed) {
                    cantGaseosaMed = nuevoValor;
                    CLASES.SesionActual.actualizarProductoEnCarrito(bebidaMediana, cantGaseosaMed);
                } else if (label == CantidadGrand) {
                    cantGaseosaGra = nuevoValor;
                    CLASES.SesionActual.actualizarProductoEnCarrito(bebidaGrande, cantGaseosaGra);
                }
                
                // LE DECIMOS A LA DULCERIA QUE ACTUALICE EL LABEL DEL TOTAL
                if (interfaces.Dulceria.instanciaActual != null) {
                    interfaces.Dulceria.instanciaActual.actualizarTotalVisual();
                }
            }
        } catch (NumberFormatException e) {
            label.setText("0");
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Gaseosa = new javax.swing.JPanel();
        GaseosaPequeña = new javax.swing.JPanel();
        imagenP = new javax.swing.JLabel();
        NombreProduct1 = new javax.swing.JLabel();
        masCantPeq = new javax.swing.JButton();
        menosCantPeq = new javax.swing.JButton();
        CantidadPeq = new javax.swing.JLabel();
        GaseosaMediana = new javax.swing.JPanel();
        imagenM = new javax.swing.JLabel();
        NombreProduct2 = new javax.swing.JLabel();
        masCantMed = new javax.swing.JButton();
        menosCantMed = new javax.swing.JButton();
        CantidadMed = new javax.swing.JLabel();
        GaseosaGrande = new javax.swing.JPanel();
        imagenG = new javax.swing.JLabel();
        NombreProduct3 = new javax.swing.JLabel();
        masCantGrand = new javax.swing.JButton();
        menosCantidadGrand = new javax.swing.JButton();
        CantidadGrand = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setMinimumSize(new java.awt.Dimension(1370, 529));
        setPreferredSize(new java.awt.Dimension(1370, 529));
        setLayout(new java.awt.BorderLayout());

        Gaseosa.setBackground(new java.awt.Color(37, 94, 150));
        Gaseosa.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        GaseosaPequeña.setBackground(new java.awt.Color(17, 24, 39));
        GaseosaPequeña.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(250, 204, 21), 3));
        GaseosaPequeña.setPreferredSize(new java.awt.Dimension(300, 400));

        imagenP.setBackground(new java.awt.Color(153, 255, 51));
        imagenP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/comida/CocaCola.png"))); // NOI18N

        NombreProduct1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        NombreProduct1.setForeground(new java.awt.Color(255, 255, 255));
        NombreProduct1.setText("    Gaseosa Pequeña       S/8.00 ");

        masCantPeq.setBackground(new java.awt.Color(0, 9, 80));
        masCantPeq.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        masCantPeq.setForeground(new java.awt.Color(255, 255, 255));
        masCantPeq.setText("+");
        masCantPeq.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        masCantPeq.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                masCantPeqActionPerformed(evt);
            }
        });

        menosCantPeq.setBackground(new java.awt.Color(0, 9, 80));
        menosCantPeq.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        menosCantPeq.setForeground(new java.awt.Color(255, 255, 255));
        menosCantPeq.setText("-");
        menosCantPeq.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menosCantPeq.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menosCantPeqActionPerformed(evt);
            }
        });

        CantidadPeq.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        CantidadPeq.setForeground(new java.awt.Color(255, 255, 255));
        CantidadPeq.setText("0");

        javax.swing.GroupLayout GaseosaPequeñaLayout = new javax.swing.GroupLayout(GaseosaPequeña);
        GaseosaPequeña.setLayout(GaseosaPequeñaLayout);
        GaseosaPequeñaLayout.setHorizontalGroup(
            GaseosaPequeñaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GaseosaPequeñaLayout.createSequentialGroup()
                .addGroup(GaseosaPequeñaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(GaseosaPequeñaLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(GaseosaPequeñaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(imagenP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(NombreProduct1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(GaseosaPequeñaLayout.createSequentialGroup()
                        .addGap(72, 72, 72)
                        .addComponent(masCantPeq, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(CantidadPeq)
                        .addGap(18, 18, 18)
                        .addComponent(menosCantPeq, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        GaseosaPequeñaLayout.setVerticalGroup(
            GaseosaPequeñaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GaseosaPequeñaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(imagenP, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(NombreProduct1)
                .addGap(18, 18, 18)
                .addGroup(GaseosaPequeñaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                    .addComponent(CantidadPeq, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(masCantPeq, javax.swing.GroupLayout.PREFERRED_SIZE, 25, Short.MAX_VALUE)
                    .addComponent(menosCantPeq, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        Gaseosa.add(GaseosaPequeña, new org.netbeans.lib.awtextra.AbsoluteConstraints(131, 29, 316, 453));

        GaseosaMediana.setBackground(new java.awt.Color(17, 24, 39));
        GaseosaMediana.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(250, 204, 21), 3));
        GaseosaMediana.setPreferredSize(new java.awt.Dimension(300, 400));

        imagenM.setBackground(new java.awt.Color(153, 255, 51));
        imagenM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/comida/CocaCola.png"))); // NOI18N

        NombreProduct2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        NombreProduct2.setForeground(new java.awt.Color(255, 255, 255));
        NombreProduct2.setText("   Gaseosa Mediana       S/10.00 ");

        masCantMed.setBackground(new java.awt.Color(0, 9, 80));
        masCantMed.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        masCantMed.setForeground(new java.awt.Color(255, 255, 255));
        masCantMed.setText("+");
        masCantMed.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        masCantMed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                masCantMedActionPerformed(evt);
            }
        });

        menosCantMed.setBackground(new java.awt.Color(0, 9, 80));
        menosCantMed.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        menosCantMed.setForeground(new java.awt.Color(255, 255, 255));
        menosCantMed.setText("-");
        menosCantMed.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menosCantMed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menosCantMedActionPerformed(evt);
            }
        });

        CantidadMed.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        CantidadMed.setForeground(new java.awt.Color(255, 255, 255));
        CantidadMed.setText("0");

        javax.swing.GroupLayout GaseosaMedianaLayout = new javax.swing.GroupLayout(GaseosaMediana);
        GaseosaMediana.setLayout(GaseosaMedianaLayout);
        GaseosaMedianaLayout.setHorizontalGroup(
            GaseosaMedianaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GaseosaMedianaLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(GaseosaMedianaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(imagenM, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(NombreProduct2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(14, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, GaseosaMedianaLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(masCantMed, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(CantidadMed)
                .addGap(18, 18, 18)
                .addComponent(menosCantMed, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(70, 70, 70))
        );
        GaseosaMedianaLayout.setVerticalGroup(
            GaseosaMedianaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GaseosaMedianaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(imagenM, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(NombreProduct2)
                .addGap(18, 18, 18)
                .addGroup(GaseosaMedianaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                    .addComponent(CantidadMed, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(masCantMed, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(menosCantMed, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        Gaseosa.add(GaseosaMediana, new org.netbeans.lib.awtextra.AbsoluteConstraints(533, 29, 316, 453));

        GaseosaGrande.setBackground(new java.awt.Color(17, 24, 39));
        GaseosaGrande.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(250, 204, 21), 3));
        GaseosaGrande.setPreferredSize(new java.awt.Dimension(300, 400));

        imagenG.setBackground(new java.awt.Color(153, 255, 51));
        imagenG.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/comida/CocaCola.png"))); // NOI18N

        NombreProduct3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        NombreProduct3.setForeground(new java.awt.Color(255, 255, 255));
        NombreProduct3.setText("    Gaseosa Grande       S/11.50 ");

        masCantGrand.setBackground(new java.awt.Color(0, 9, 80));
        masCantGrand.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        masCantGrand.setForeground(new java.awt.Color(255, 255, 255));
        masCantGrand.setText("+");
        masCantGrand.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        masCantGrand.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                masCantGrandActionPerformed(evt);
            }
        });

        menosCantidadGrand.setBackground(new java.awt.Color(0, 9, 80));
        menosCantidadGrand.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        menosCantidadGrand.setForeground(new java.awt.Color(255, 255, 255));
        menosCantidadGrand.setText("-");
        menosCantidadGrand.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menosCantidadGrand.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menosCantidadGrandActionPerformed(evt);
            }
        });

        CantidadGrand.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        CantidadGrand.setForeground(new java.awt.Color(255, 255, 255));
        CantidadGrand.setText("0");

        javax.swing.GroupLayout GaseosaGrandeLayout = new javax.swing.GroupLayout(GaseosaGrande);
        GaseosaGrande.setLayout(GaseosaGrandeLayout);
        GaseosaGrandeLayout.setHorizontalGroup(
            GaseosaGrandeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GaseosaGrandeLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(GaseosaGrandeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(imagenG, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(NombreProduct3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(14, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, GaseosaGrandeLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(masCantGrand, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(CantidadGrand)
                .addGap(18, 18, 18)
                .addComponent(menosCantidadGrand, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(71, 71, 71))
        );
        GaseosaGrandeLayout.setVerticalGroup(
            GaseosaGrandeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GaseosaGrandeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(imagenG, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(NombreProduct3)
                .addGap(18, 18, 18)
                .addGroup(GaseosaGrandeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(menosCantidadGrand, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CantidadGrand, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(masCantGrand, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        Gaseosa.add(GaseosaGrande, new org.netbeans.lib.awtextra.AbsoluteConstraints(934, 29, 316, 453));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fondo/FondoDulc.png"))); // NOI18N
        Gaseosa.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 540));

        add(Gaseosa, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void masCantPeqActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_masCantPeqActionPerformed
    cambiarCantidad(CantidadPeq, 1);
    }//GEN-LAST:event_masCantPeqActionPerformed

    private void menosCantPeqActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menosCantPeqActionPerformed
    cambiarCantidad(CantidadPeq, -1);
    }//GEN-LAST:event_menosCantPeqActionPerformed

    private void masCantMedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_masCantMedActionPerformed
    cambiarCantidad(CantidadMed, 1);
    }//GEN-LAST:event_masCantMedActionPerformed

    private void menosCantMedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menosCantMedActionPerformed
    cambiarCantidad(CantidadMed, -1);
    }//GEN-LAST:event_menosCantMedActionPerformed

    private void masCantGrandActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_masCantGrandActionPerformed
    cambiarCantidad(CantidadGrand, 1);
    }//GEN-LAST:event_masCantGrandActionPerformed

    private void menosCantidadGrandActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menosCantidadGrandActionPerformed
    cambiarCantidad(CantidadGrand, -1);
    }//GEN-LAST:event_menosCantidadGrandActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel CantidadGrand;
    private javax.swing.JLabel CantidadMed;
    private javax.swing.JLabel CantidadPeq;
    private javax.swing.JPanel Gaseosa;
    private javax.swing.JPanel GaseosaGrande;
    private javax.swing.JPanel GaseosaMediana;
    private javax.swing.JPanel GaseosaPequeña;
    private javax.swing.JLabel NombreProduct1;
    private javax.swing.JLabel NombreProduct2;
    private javax.swing.JLabel NombreProduct3;
    private javax.swing.JLabel imagenG;
    private javax.swing.JLabel imagenM;
    private javax.swing.JLabel imagenP;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JButton masCantGrand;
    private javax.swing.JButton masCantMed;
    private javax.swing.JButton masCantPeq;
    private javax.swing.JButton menosCantMed;
    private javax.swing.JButton menosCantPeq;
    private javax.swing.JButton menosCantidadGrand;
    // End of variables declaration//GEN-END:variables
}
