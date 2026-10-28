package interfaces.dulceria;

public class Palomitas extends javax.swing.JPanel {
    // Variables para guardar la cantidad numérica
    private int CantPalomitasPequeña = 0;
    private int CantPalomitasMediana = 0;
    private int CantPalomitasGrande = 0;
    
    // CREAMOS LOS OBJETOS  
    CLASES.Snack palomitaPequeña = new CLASES.Snack("S01", "Palomitas Pequeña", 12.00, 100, true, "Salado", "Pequeño");
    CLASES.Snack palomitaMediana = new CLASES.Snack("S02", "Palomitas Mediana", 13.00, 100, true, "Salado", "Mediano");
    CLASES.Snack palomitaGrande = new CLASES.Snack("S03", "Palomitas Grande", 15.00, 100, true, "Salado", "Grande");   
    
    public Palomitas() {
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

            if (idEnCarrito.equals(palomitaPequeña.getProductoID())) {
                this.CantPalomitasPequeña = dv.getCantidad();
                CantidadPeq.setText(String.valueOf(this.CantPalomitasPequeña));
            } else if (idEnCarrito.equals(palomitaMediana.getProductoID())) {
                this.CantPalomitasMediana = dv.getCantidad();
                CantidadMed.setText(String.valueOf(this.CantPalomitasMediana));
            } else if (idEnCarrito.equals(palomitaGrande.getProductoID())) {
                this.CantPalomitasGrande = dv.getCantidad();
                CantidadGrand.setText(String.valueOf(this.CantPalomitasGrande));
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
                    CantPalomitasPequeña = nuevoValor;
                    CLASES.SesionActual.actualizarProductoEnCarrito(palomitaPequeña, CantPalomitasPequeña);
                } else if (label == CantidadMed) {
                    CantPalomitasMediana = nuevoValor;
                    CLASES.SesionActual.actualizarProductoEnCarrito(palomitaMediana, CantPalomitasMediana);
                } else if (label == CantidadGrand) {
                    CantPalomitasGrande = nuevoValor;
                    CLASES.SesionActual.actualizarProductoEnCarrito(palomitaGrande, CantPalomitasGrande);
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

        Palomitas = new javax.swing.JPanel();
        PalomitasPequeñas = new javax.swing.JPanel();
        imagenP = new javax.swing.JLabel();
        NombreProduct1 = new javax.swing.JLabel();
        masCantPeq = new javax.swing.JButton();
        menosCantPeq = new javax.swing.JButton();
        CantidadPeq = new javax.swing.JLabel();
        PalomitasMedianas = new javax.swing.JPanel();
        imagenM = new javax.swing.JLabel();
        NombreProduct2 = new javax.swing.JLabel();
        CantidadMed = new javax.swing.JLabel();
        menosCantMed = new javax.swing.JButton();
        masCantMed = new javax.swing.JButton();
        PalomitasGrande = new javax.swing.JPanel();
        imagenG = new javax.swing.JLabel();
        NombreProduct3 = new javax.swing.JLabel();
        CantidadGrand = new javax.swing.JLabel();
        masCantGrand = new javax.swing.JButton();
        menosCantGrand = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(1370, 529));
        setLayout(new java.awt.BorderLayout());

        Palomitas.setBackground(new java.awt.Color(37, 94, 150));
        Palomitas.setPreferredSize(new java.awt.Dimension(1370, 640));
        Palomitas.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        PalomitasPequeñas.setBackground(new java.awt.Color(17, 24, 39));
        PalomitasPequeñas.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(250, 204, 21), 3));
        PalomitasPequeñas.setPreferredSize(new java.awt.Dimension(300, 400));

        imagenP.setBackground(new java.awt.Color(153, 255, 51));
        imagenP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/comida/palomitaschicas.png"))); // NOI18N

        NombreProduct1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        NombreProduct1.setForeground(new java.awt.Color(255, 255, 255));
        NombreProduct1.setText("  Palomitas Pequeña       S/12.00 ");

        masCantPeq.setBackground(new java.awt.Color(0, 9, 80));
        masCantPeq.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        masCantPeq.setForeground(new java.awt.Color(255, 255, 255));
        masCantPeq.setText("+");
        masCantPeq.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        masCantPeq.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
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

        javax.swing.GroupLayout PalomitasPequeñasLayout = new javax.swing.GroupLayout(PalomitasPequeñas);
        PalomitasPequeñas.setLayout(PalomitasPequeñasLayout);
        PalomitasPequeñasLayout.setHorizontalGroup(
            PalomitasPequeñasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PalomitasPequeñasLayout.createSequentialGroup()
                .addGroup(PalomitasPequeñasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PalomitasPequeñasLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(PalomitasPequeñasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(imagenP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(NombreProduct1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(PalomitasPequeñasLayout.createSequentialGroup()
                        .addGap(72, 72, 72)
                        .addComponent(masCantPeq, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(CantidadPeq)
                        .addGap(18, 18, 18)
                        .addComponent(menosCantPeq, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        PalomitasPequeñasLayout.setVerticalGroup(
            PalomitasPequeñasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PalomitasPequeñasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(imagenP, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(NombreProduct1)
                .addGap(18, 18, 18)
                .addGroup(PalomitasPequeñasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(menosCantPeq, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(PalomitasPequeñasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(CantidadPeq)
                        .addComponent(masCantPeq, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        Palomitas.add(PalomitasPequeñas, new org.netbeans.lib.awtextra.AbsoluteConstraints(131, 29, 316, 453));

        PalomitasMedianas.setBackground(new java.awt.Color(17, 24, 39));
        PalomitasMedianas.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(250, 204, 21), 3));
        PalomitasMedianas.setPreferredSize(new java.awt.Dimension(300, 400));

        imagenM.setBackground(new java.awt.Color(153, 255, 51));
        imagenM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/comida/palomitasmedianas.png"))); // NOI18N

        NombreProduct2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        NombreProduct2.setForeground(new java.awt.Color(255, 255, 255));
        NombreProduct2.setText("  Palomitas Mediana       S/13.00 ");

        CantidadMed.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        CantidadMed.setForeground(new java.awt.Color(255, 255, 255));
        CantidadMed.setText("0");

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

        masCantMed.setBackground(new java.awt.Color(0, 9, 80));
        masCantMed.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        masCantMed.setForeground(new java.awt.Color(255, 255, 255));
        masCantMed.setText("+");
        masCantMed.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        masCantMed.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        masCantMed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                masCantMedActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PalomitasMedianasLayout = new javax.swing.GroupLayout(PalomitasMedianas);
        PalomitasMedianas.setLayout(PalomitasMedianasLayout);
        PalomitasMedianasLayout.setHorizontalGroup(
            PalomitasMedianasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PalomitasMedianasLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(PalomitasMedianasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(imagenM, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(NombreProduct2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(14, Short.MAX_VALUE))
            .addGroup(PalomitasMedianasLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(masCantMed, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(CantidadMed)
                .addGap(18, 18, 18)
                .addComponent(menosCantMed, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(70, 70, 70))
        );
        PalomitasMedianasLayout.setVerticalGroup(
            PalomitasMedianasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PalomitasMedianasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(imagenM, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(NombreProduct2)
                .addGap(18, 18, 18)
                .addGroup(PalomitasMedianasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CantidadMed)
                    .addComponent(menosCantMed, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(masCantMed, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        Palomitas.add(PalomitasMedianas, new org.netbeans.lib.awtextra.AbsoluteConstraints(533, 29, 316, 453));

        PalomitasGrande.setBackground(new java.awt.Color(17, 24, 39));
        PalomitasGrande.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(250, 204, 21), 3));
        PalomitasGrande.setPreferredSize(new java.awt.Dimension(300, 400));

        imagenG.setBackground(new java.awt.Color(153, 255, 51));
        imagenG.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/comida/palomitasgrandes.png"))); // NOI18N

        NombreProduct3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        NombreProduct3.setForeground(new java.awt.Color(255, 255, 255));
        NombreProduct3.setText("  Palomitas Grande       S/15.00 ");

        CantidadGrand.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        CantidadGrand.setForeground(new java.awt.Color(255, 255, 255));
        CantidadGrand.setText("0");

        masCantGrand.setBackground(new java.awt.Color(0, 9, 80));
        masCantGrand.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        masCantGrand.setForeground(new java.awt.Color(255, 255, 255));
        masCantGrand.setText("+");
        masCantGrand.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        masCantGrand.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        masCantGrand.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                masCantGrandActionPerformed(evt);
            }
        });

        menosCantGrand.setBackground(new java.awt.Color(0, 9, 80));
        menosCantGrand.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        menosCantGrand.setForeground(new java.awt.Color(255, 255, 255));
        menosCantGrand.setText("-");
        menosCantGrand.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menosCantGrand.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menosCantGrandActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PalomitasGrandeLayout = new javax.swing.GroupLayout(PalomitasGrande);
        PalomitasGrande.setLayout(PalomitasGrandeLayout);
        PalomitasGrandeLayout.setHorizontalGroup(
            PalomitasGrandeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PalomitasGrandeLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(PalomitasGrandeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(imagenG, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(NombreProduct3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(14, Short.MAX_VALUE))
            .addGroup(PalomitasGrandeLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(masCantGrand, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(CantidadGrand)
                .addGap(18, 18, 18)
                .addComponent(menosCantGrand, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(70, 70, 70))
        );
        PalomitasGrandeLayout.setVerticalGroup(
            PalomitasGrandeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PalomitasGrandeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(imagenG, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(NombreProduct3)
                .addGap(18, 18, 18)
                .addGroup(PalomitasGrandeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(PalomitasGrandeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(CantidadGrand)
                        .addComponent(masCantGrand, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PalomitasGrandeLayout.createSequentialGroup()
                        .addComponent(menosCantGrand, javax.swing.GroupLayout.PREFERRED_SIZE, 25, Short.MAX_VALUE)
                        .addGap(4, 4, 4)))
                .addContainerGap(8, Short.MAX_VALUE))
        );

        Palomitas.add(PalomitasGrande, new org.netbeans.lib.awtextra.AbsoluteConstraints(934, 29, 316, 453));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fondo/FondoDulc.png"))); // NOI18N
        Palomitas.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 540));

        add(Palomitas, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void masCantPeqActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_masCantPeqActionPerformed
    cambiarCantidad(CantidadPeq, 1);
    }//GEN-LAST:event_masCantPeqActionPerformed

    private void menosCantPeqActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menosCantPeqActionPerformed
    cambiarCantidad(CantidadPeq, -1);
    }//GEN-LAST:event_menosCantPeqActionPerformed

    private void menosCantMedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menosCantMedActionPerformed
    cambiarCantidad(CantidadMed, -1);
    }//GEN-LAST:event_menosCantMedActionPerformed

    private void masCantMedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_masCantMedActionPerformed
    cambiarCantidad(CantidadMed, 1);
    }//GEN-LAST:event_masCantMedActionPerformed

    private void masCantGrandActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_masCantGrandActionPerformed
    cambiarCantidad(CantidadGrand, 1);
    }//GEN-LAST:event_masCantGrandActionPerformed

    private void menosCantGrandActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menosCantGrandActionPerformed
    cambiarCantidad(CantidadGrand, -1);
    }//GEN-LAST:event_menosCantGrandActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel CantidadGrand;
    private javax.swing.JLabel CantidadMed;
    private javax.swing.JLabel CantidadPeq;
    private javax.swing.JLabel NombreProduct1;
    private javax.swing.JLabel NombreProduct2;
    private javax.swing.JLabel NombreProduct3;
    private javax.swing.JPanel Palomitas;
    private javax.swing.JPanel PalomitasGrande;
    private javax.swing.JPanel PalomitasMedianas;
    private javax.swing.JPanel PalomitasPequeñas;
    private javax.swing.JLabel imagenG;
    private javax.swing.JLabel imagenM;
    private javax.swing.JLabel imagenP;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JButton masCantGrand;
    private javax.swing.JButton masCantMed;
    private javax.swing.JButton masCantPeq;
    private javax.swing.JButton menosCantGrand;
    private javax.swing.JButton menosCantMed;
    private javax.swing.JButton menosCantPeq;
    // End of variables declaration//GEN-END:variables
}
