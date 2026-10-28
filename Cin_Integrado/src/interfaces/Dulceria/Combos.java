package interfaces.dulceria;

public class Combos extends javax.swing.JPanel {
    private int cantCombo1 = 0;
    private int cantCombo2 = 0;
    private int cantNachos = 0;
    
    // CREAMOS LOS OBJETOS 
    CLASES.Combo comboUno = new CLASES.Combo("C01", "Palomitas + Cañonazo + Gaseosa", 27.50);
    CLASES.Combo comboDos = new CLASES.Combo("C02", "Palomitas Grandes + Piqueo + 2 Gaseosas", 29.00);
    CLASES.Combo comboTres = new CLASES.Combo("C03", "Nachos + Gaseosa", 15.00);
    
    public Combos() {
        initComponents();
        // Forzamos a que los labels inicien en 0 visualmente
        Cantidad1.setText("0");
        Cantidad2.setText("0");
        Cantidad3.setText("0");
        // Sincronizamos con lo que haya en el carrito
        sincronizarConCarrito();       
    }
// --- MÉTODO PARA SINCRONIZAR LOS DATOS DE LA SESION ACTUAL ---
private void sincronizarConCarrito() {
    // Recorremos la lista de la sesión
    for (CLASES.Detalle_Venta dv : CLASES.SesionActual.carritoDulceria) {
        String idEnCarrito = dv.getProducto().getProductoID();
        
        // Comparamos el ID del producto en el carrito con nuestros objetos locales
        if (idEnCarrito.equals(comboUno.getProductoID())) {
            this.cantCombo1 = dv.getCantidad();
            Cantidad1.setText(String.valueOf(this.cantCombo1));
        } else if (idEnCarrito.equals(comboDos.getProductoID())) {
            this.cantCombo2 = dv.getCantidad();
            Cantidad2.setText(String.valueOf(this.cantCombo2));
        } else if (idEnCarrito.equals(comboTres.getProductoID())) {
            this.cantNachos = dv.getCantidad();
            Cantidad3.setText(String.valueOf(this.cantNachos));
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
                if (label == Cantidad1) {
                    cantCombo1 = nuevoValor;
                    CLASES.SesionActual.actualizarProductoEnCarrito(comboUno, cantCombo1);
                } else if (label == Cantidad2) {
                    cantCombo2 = nuevoValor;
                    CLASES.SesionActual.actualizarProductoEnCarrito(comboDos, cantCombo2);
                } else if (label == Cantidad3) {
                    cantNachos = nuevoValor;
                    CLASES.SesionActual.actualizarProductoEnCarrito(comboTres, cantNachos);
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
        Cantidad1 = new javax.swing.JLabel();
        NombreProduct4 = new javax.swing.JLabel();
        PalomitasMedianas = new javax.swing.JPanel();
        imagenM = new javax.swing.JLabel();
        NombreProduct2 = new javax.swing.JLabel();
        masCantMed = new javax.swing.JButton();
        menosCantMed = new javax.swing.JButton();
        Cantidad2 = new javax.swing.JLabel();
        NombreProduct5 = new javax.swing.JLabel();
        PalomitasGrande = new javax.swing.JPanel();
        imagenG = new javax.swing.JLabel();
        NombreProduct3 = new javax.swing.JLabel();
        masCantGrand = new javax.swing.JButton();
        menosCantidadGrand = new javax.swing.JButton();
        Cantidad3 = new javax.swing.JLabel();
        NombreProduct6 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setMinimumSize(new java.awt.Dimension(1370, 529));
        setPreferredSize(new java.awt.Dimension(1370, 529));
        setLayout(new java.awt.BorderLayout());

        Palomitas.setBackground(new java.awt.Color(37, 94, 150));
        Palomitas.setMinimumSize(new java.awt.Dimension(1370, 529));
        Palomitas.setPreferredSize(new java.awt.Dimension(1370, 529));
        Palomitas.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        PalomitasPequeñas.setBackground(new java.awt.Color(17, 24, 39));
        PalomitasPequeñas.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(250, 204, 21), 3));
        PalomitasPequeñas.setPreferredSize(new java.awt.Dimension(300, 400));

        imagenP.setBackground(new java.awt.Color(153, 255, 51));
        imagenP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/comida/Combo1.png"))); // NOI18N

        NombreProduct1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        NombreProduct1.setForeground(new java.awt.Color(255, 255, 255));
        NombreProduct1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        NombreProduct1.setText("  Palomitas Grande +       ");

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

        Cantidad1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        Cantidad1.setForeground(new java.awt.Color(255, 255, 255));
        Cantidad1.setText("0");

        NombreProduct4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        NombreProduct4.setForeground(new java.awt.Color(255, 255, 255));
        NombreProduct4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        NombreProduct4.setText("  Cañonaso + Gaseosa S/27.50 ");

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
                            .addComponent(NombreProduct1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(NombreProduct4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(PalomitasPequeñasLayout.createSequentialGroup()
                        .addGap(72, 72, 72)
                        .addComponent(masCantPeq, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(Cantidad1)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(NombreProduct4)
                .addGap(18, 18, 18)
                .addGroup(PalomitasPequeñasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(menosCantPeq, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Cantidad1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(masCantPeq, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(33, 33, 33))
        );

        Palomitas.add(PalomitasPequeñas, new org.netbeans.lib.awtextra.AbsoluteConstraints(131, 29, 316, 490));

        PalomitasMedianas.setBackground(new java.awt.Color(17, 24, 39));
        PalomitasMedianas.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(250, 204, 21), 3));
        PalomitasMedianas.setPreferredSize(new java.awt.Dimension(300, 400));

        imagenM.setBackground(new java.awt.Color(153, 255, 51));
        imagenM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/comida/Combo2.png"))); // NOI18N

        NombreProduct2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        NombreProduct2.setForeground(new java.awt.Color(255, 255, 255));
        NombreProduct2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        NombreProduct2.setText("  Palomitas Grande + ");

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

        Cantidad2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        Cantidad2.setForeground(new java.awt.Color(255, 255, 255));
        Cantidad2.setText("0");

        NombreProduct5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        NombreProduct5.setForeground(new java.awt.Color(255, 255, 255));
        NombreProduct5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        NombreProduct5.setText("Piqueo + 2 Gaseosas S/29.00 ");

        javax.swing.GroupLayout PalomitasMedianasLayout = new javax.swing.GroupLayout(PalomitasMedianas);
        PalomitasMedianas.setLayout(PalomitasMedianasLayout);
        PalomitasMedianasLayout.setHorizontalGroup(
            PalomitasMedianasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PalomitasMedianasLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(PalomitasMedianasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(imagenM, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(NombreProduct2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(NombreProduct5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(14, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PalomitasMedianasLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(masCantMed, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(Cantidad2)
                .addGap(18, 18, 18)
                .addComponent(menosCantMed, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(71, 71, 71))
        );
        PalomitasMedianasLayout.setVerticalGroup(
            PalomitasMedianasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PalomitasMedianasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(imagenM, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(NombreProduct2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(NombreProduct5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addGroup(PalomitasMedianasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(menosCantMed, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Cantidad2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(masCantMed, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        Palomitas.add(PalomitasMedianas, new org.netbeans.lib.awtextra.AbsoluteConstraints(533, 29, 316, 490));

        PalomitasGrande.setBackground(new java.awt.Color(17, 24, 39));
        PalomitasGrande.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(250, 204, 21), 3));
        PalomitasGrande.setPreferredSize(new java.awt.Dimension(300, 400));

        imagenG.setBackground(new java.awt.Color(153, 255, 51));
        imagenG.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/comida/nacho.png"))); // NOI18N

        NombreProduct3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        NombreProduct3.setForeground(new java.awt.Color(255, 255, 255));
        NombreProduct3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        NombreProduct3.setText("Gaseosa Mediana S/15.00 ");

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

        Cantidad3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        Cantidad3.setForeground(new java.awt.Color(255, 255, 255));
        Cantidad3.setText("0");

        NombreProduct6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        NombreProduct6.setForeground(new java.awt.Color(255, 255, 255));
        NombreProduct6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        NombreProduct6.setText("Nachos +");

        javax.swing.GroupLayout PalomitasGrandeLayout = new javax.swing.GroupLayout(PalomitasGrande);
        PalomitasGrande.setLayout(PalomitasGrandeLayout);
        PalomitasGrandeLayout.setHorizontalGroup(
            PalomitasGrandeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PalomitasGrandeLayout.createSequentialGroup()
                .addGroup(PalomitasGrandeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PalomitasGrandeLayout.createSequentialGroup()
                        .addGap(76, 76, 76)
                        .addComponent(masCantGrand, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(Cantidad3)
                        .addGap(18, 18, 18)
                        .addComponent(menosCantidadGrand, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PalomitasGrandeLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(PalomitasGrandeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(imagenG, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(PalomitasGrandeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(NombreProduct3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
                                .addComponent(NombreProduct6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PalomitasGrandeLayout.setVerticalGroup(
            PalomitasGrandeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PalomitasGrandeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(imagenG, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(NombreProduct6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(NombreProduct3)
                .addGap(18, 18, 18)
                .addGroup(PalomitasGrandeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(menosCantidadGrand, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Cantidad3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(masCantGrand, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        Palomitas.add(PalomitasGrande, new org.netbeans.lib.awtextra.AbsoluteConstraints(934, 29, 316, 490));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fondo/FondoDulc.png"))); // NOI18N
        jLabel1.setMaximumSize(new java.awt.Dimension(1370, 529));
        jLabel1.setMinimumSize(new java.awt.Dimension(1370, 529));
        jLabel1.setPreferredSize(new java.awt.Dimension(1370, 529));
        Palomitas.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 540));

        add(Palomitas, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void masCantPeqActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_masCantPeqActionPerformed
        cambiarCantidad(Cantidad1, 1);
    }//GEN-LAST:event_masCantPeqActionPerformed

    private void menosCantPeqActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menosCantPeqActionPerformed
        cambiarCantidad(Cantidad1, -1);
    }//GEN-LAST:event_menosCantPeqActionPerformed

    private void masCantMedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_masCantMedActionPerformed
        cambiarCantidad(Cantidad2, 1);
    }//GEN-LAST:event_masCantMedActionPerformed

    private void menosCantMedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menosCantMedActionPerformed
        cambiarCantidad(Cantidad2, -1);
    }//GEN-LAST:event_menosCantMedActionPerformed

    private void masCantGrandActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_masCantGrandActionPerformed
        cambiarCantidad(Cantidad3, 1);
    }//GEN-LAST:event_masCantGrandActionPerformed

    private void menosCantidadGrandActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menosCantidadGrandActionPerformed
        cambiarCantidad(Cantidad3, -1);
    }//GEN-LAST:event_menosCantidadGrandActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Cantidad1;
    private javax.swing.JLabel Cantidad2;
    private javax.swing.JLabel Cantidad3;
    private javax.swing.JLabel NombreProduct1;
    private javax.swing.JLabel NombreProduct2;
    private javax.swing.JLabel NombreProduct3;
    private javax.swing.JLabel NombreProduct4;
    private javax.swing.JLabel NombreProduct5;
    private javax.swing.JLabel NombreProduct6;
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
    private javax.swing.JButton menosCantMed;
    private javax.swing.JButton menosCantPeq;
    private javax.swing.JButton menosCantidadGrand;
    // End of variables declaration//GEN-END:variables
}
