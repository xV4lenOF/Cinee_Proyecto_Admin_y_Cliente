package interfaces;
import CLASES.SesionActual;
import interfaces.dulceria.Palomitas;
import interfaces.dulceria.Bebidas;
import interfaces.dulceria.Combos;
import interfaces.dulceria.DetalleCarrito;
import java.awt.BorderLayout;

/**
 * Pantalla de dulcería: agrupa las sub-pantallas de Palomitas, Bebidas y
 * Combos, y muestra el carrito de compra (SesionActual.carritoDulceria) con
 * el total acumulado. Al continuar, navega a Pago para finalizar la compra.
 */
public class Dulceria extends javax.swing.JPanel {
private Boletos panelAnterior;
    public static Dulceria instanciaActual;

    public Dulceria(Boletos anterior) {
        initComponents();
        this.panelAnterior = anterior;
        instanciaActual = this;     // Esto guarda la pantalla actual en la memoria
        actualizarTotalVisual();    // Forzamos la actualización del total visual apenas carga
        if (this.panelAnterior != null) {
            // Guarda el texto "TOTAL: S/XX.XX" en tu JTextField de Dulcería
            precio.setText(this.panelAnterior.obtenerTextoSumaTotal());
        }
    }

    public Dulceria() {

    }
    
//######### Este método lo llamarán los botones "+" y "-" para actualizar el número visual ########
public void actualizarTotalVisual() {
        double sumaDulceria = SesionActual.calcularTotalDulceria();
        double sumaBoletos = 0.0;

        if (this.panelAnterior != null) {
            // Recibe "TOTAL: S/13.00", le quita "TOTAL: " y "S/" para quedarnos solo con el número
            String textoLimpio = this.panelAnterior.obtenerTextoSumaTotal()
                    .replace("TOTAL: ", "")
                    .replace("S/", "")
                    .trim();
            try {
                sumaBoletos = Double.parseDouble(textoLimpio);
            } catch (NumberFormatException e) {
                sumaBoletos = 0.0; // por si el texto viniera con otro formato inesperado
            }
        }

        double totalGeneral = sumaDulceria + sumaBoletos;
        precio.setText(String.format("S/%.2f", totalGeneral));
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        contenido = new javax.swing.JPanel();
        lblImagen = new javax.swing.JLabel();
        BtnCombos = new javax.swing.JButton();
        BtnPalomitas = new javax.swing.JButton();
        BtnGaseosas = new javax.swing.JButton();
        precio = new javax.swing.JLabel();
        total = new javax.swing.JLabel();
        BtnVolver = new javax.swing.JButton();
        BtnSiguiente = new javax.swing.JButton();
        BtnCarrito = new javax.swing.JButton();

        setBackground(new java.awt.Color(0, 9, 39));
        setPreferredSize(new java.awt.Dimension(1370, 760));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        contenido.setBackground(new java.awt.Color(102, 255, 102));

        lblImagen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fondo/llama.png"))); // NOI18N

        javax.swing.GroupLayout contenidoLayout = new javax.swing.GroupLayout(contenido);
        contenido.setLayout(contenidoLayout);
        contenidoLayout.setHorizontalGroup(
            contenidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contenidoLayout.createSequentialGroup()
                .addComponent(lblImagen)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        contenidoLayout.setVerticalGroup(
            contenidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblImagen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        add(contenido, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 59, 1370, -1));

        BtnCombos.setBackground(new java.awt.Color(0, 9, 80));
        BtnCombos.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        BtnCombos.setForeground(new java.awt.Color(255, 255, 255));
        BtnCombos.setText("COMBOS");
        BtnCombos.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        BtnCombos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCombosActionPerformed(evt);
            }
        });
        add(BtnCombos, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 10, 337, 35));

        BtnPalomitas.setBackground(new java.awt.Color(0, 9, 80));
        BtnPalomitas.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        BtnPalomitas.setForeground(new java.awt.Color(255, 255, 255));
        BtnPalomitas.setText("PALOMITAS");
        BtnPalomitas.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        BtnPalomitas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPalomitasActionPerformed(evt);
            }
        });
        add(BtnPalomitas, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 10, 334, 35));

        BtnGaseosas.setBackground(new java.awt.Color(0, 9, 80));
        BtnGaseosas.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        BtnGaseosas.setForeground(new java.awt.Color(255, 255, 255));
        BtnGaseosas.setText("BEBIDAS");
        BtnGaseosas.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        BtnGaseosas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnGaseosasActionPerformed(evt);
            }
        });
        add(BtnGaseosas, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 10, 334, 35));

        precio.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        precio.setForeground(new java.awt.Color(255, 255, 255));
        precio.setText("S/0.00");
        add(precio, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 620, 365, -1));

        total.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        total.setForeground(new java.awt.Color(255, 255, 255));
        total.setText("TOTAL:");
        add(total, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 620, -1, -1));

        BtnVolver.setBackground(new java.awt.Color(0, 9, 80));
        BtnVolver.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        BtnVolver.setForeground(new java.awt.Color(255, 255, 255));
        BtnVolver.setText("VOLVER");
        BtnVolver.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(96, 98, 99), 1, true));
        BtnVolver.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        BtnVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnVolverActionPerformed(evt);
            }
        });
        add(BtnVolver, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 620, 130, 50));

        BtnSiguiente.setBackground(new java.awt.Color(0, 9, 80));
        BtnSiguiente.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        BtnSiguiente.setForeground(new java.awt.Color(255, 255, 255));
        BtnSiguiente.setText("SIGUIENTE");
        BtnSiguiente.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(96, 98, 99), 1, true));
        BtnSiguiente.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        BtnSiguiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSiguienteActionPerformed(evt);
            }
        });
        add(BtnSiguiente, new org.netbeans.lib.awtextra.AbsoluteConstraints(1150, 620, 190, 50));

        BtnCarrito.setBackground(new java.awt.Color(0, 9, 80));
        BtnCarrito.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fondo/Carrito.png"))); // NOI18N
        BtnCarrito.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        BtnCarrito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCarritoActionPerformed(evt);
            }
        });
        add(BtnCarrito, new org.netbeans.lib.awtextra.AbsoluteConstraints(1320, 10, 40, 40));
    }// </editor-fold>//GEN-END:initComponents

    private void BtnCombosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCombosActionPerformed
        Combos combos = new Combos();
        contenido.removeAll();
        contenido.setLayout(new BorderLayout());        
        contenido.add(combos, java.awt.BorderLayout.CENTER);
        contenido.revalidate();
        contenido.repaint();
    }//GEN-LAST:event_BtnCombosActionPerformed

    private void BtnPalomitasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPalomitasActionPerformed
        Palomitas palomitas = new Palomitas();
        contenido.removeAll();
        contenido.setLayout(new BorderLayout());
        contenido.add(palomitas, BorderLayout.CENTER);
        contenido.revalidate();
        contenido.repaint();
    }//GEN-LAST:event_BtnPalomitasActionPerformed

    private void BtnGaseosasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnGaseosasActionPerformed
        Bebidas bebidas = new Bebidas ();
        contenido.removeAll();
        contenido.setLayout(new BorderLayout());        
        contenido.add(bebidas, java.awt.BorderLayout.CENTER);
        contenido.revalidate();
        contenido.repaint();
    }//GEN-LAST:event_BtnGaseosasActionPerformed

    private void BtnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnVolverActionPerformed
        java.awt.Window ventana = javax.swing.SwingUtilities.getWindowAncestor(this);
        if (ventana instanceof javax.swing.JFrame) {
            javax.swing.JFrame framePrincipal = (javax.swing.JFrame) ventana;

            // Recuperamos del historial la pantalla de Boletos desde la que llegamos,
            // con las entradas que ya tenía seleccionadas.
            if (CLASES.Navegador.hayHistorial()) {
                CLASES.Navegador.regresar(framePrincipal);
            } else if (panelAnterior != null) {
                // Respaldo por si no hay historial guardado
                framePrincipal.setContentPane(panelAnterior);
                framePrincipal.revalidate();
                framePrincipal.repaint();
            }
        }
    }//GEN-LAST:event_BtnVolverActionPerformed

    private void BtnSiguienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSiguienteActionPerformed
// Buscamos la ventana Frame principal donde se aloja este panel
        java.awt.Window ventana = javax.swing.SwingUtilities.getWindowAncestor(this);
        if (ventana instanceof javax.swing.JFrame) {
            javax.swing.JFrame framePrincipal = (javax.swing.JFrame) ventana;

            // Creamos la pantalla de pago pasándole "this" (este panel Dulceria actual)
            Pago nuevoPanelPago = new Pago(this);

            // Ajustamos el tamaño y cambiamos el contenedor para mostrar el Pago
            nuevoPanelPago.setSize(framePrincipal.getSize());
            CLASES.Navegador.irA(framePrincipal, nuevoPanelPago);
        }
    }//GEN-LAST:event_BtnSiguienteActionPerformed

    private void BtnCarritoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCarritoActionPerformed
        java.awt.Window ventana = javax.swing.SwingUtilities.getWindowAncestor(this);
        DetalleCarrito popup = new DetalleCarrito((javax.swing.JFrame) ventana, true);
        
        // El programa se pausa en esta línea hasta que cierres el modal (con botón o X)
        popup.setVisible(true); 

        // =========================================================
        // AL CERRARSE EL MODAL, DETECTAMOS DÓNDE ESTABAS Y REFRESCAMOS
        // =========================================================
        if (contenido.getComponentCount() > 0) {
            java.awt.Component panelActivo = contenido.getComponent(0);
            
            // Si el panel que está ahorita en pantalla es el de Combos...
            if (panelActivo instanceof interfaces.dulceria.Combos) {
                BtnCombosActionPerformed(null);
            } 
            // Si es el de Palomitas... (Ajusta el paquete si Palomitas está en otra carpeta)
            else if (panelActivo instanceof Palomitas) { 
                BtnPalomitasActionPerformed(null);
            } 
            // Si es el de Bebidas...
            else if (panelActivo instanceof interfaces.dulceria.Bebidas) {
                BtnGaseosasActionPerformed(null);
            }
        }
    }//GEN-LAST:event_BtnCarritoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnCarrito;
    private javax.swing.JButton BtnCombos;
    private javax.swing.JButton BtnGaseosas;
    private javax.swing.JButton BtnPalomitas;
    private javax.swing.JButton BtnSiguiente;
    private javax.swing.JButton BtnVolver;
    private javax.swing.JPanel contenido;
    private javax.swing.JLabel lblImagen;
    private javax.swing.JLabel precio;
    private javax.swing.JLabel total;
    // End of variables declaration//GEN-END:variables
}
