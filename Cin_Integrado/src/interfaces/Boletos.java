/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package interfaces;

/**
 *
 * @author USER
 */
/**
 * Pantalla donde se elige el tipo de entrada (Adulto, Niño, Adulto mayor)
 * para cada butaca seleccionada en Asientos, y se calcula el subtotal de
 * las entradas. Al continuar, navega a Dulcería para agregar snacks/bebidas.
 */
public class Boletos extends javax.swing.JPanel {
    private javax.swing.JPanel panelAnterior;
    private CLASES.Pelicula peliculaElegida;
    private String horaElegida;
    private String fechaElegida;
    private final int cantAsientos;
    public static Boletos instanciaActual; 

    private int totalClicks = 0;
    private int contadorAdultos = 0;
    private int contadorNinos = 0;
    private int contadorMayor = 0;
    public Boletos(javax.swing.JPanel anterior,CLASES.Pelicula pelicula, String hora, String fecha,int asientos) {
        initComponents();
        this.panelAnterior = anterior;
        instanciaActual = this;     // Esto guarda la pantalla actual en la memoria
        this.peliculaElegida = pelicula;
        this.horaElegida = hora;
        this.fechaElegida = fecha;
        this.cantAsientos=asientos;
 
        cantAdultos.setText("0");
        cantInfantil.setText("0");
        PrecTotalAdult.setText("S/0.00");
        PrecTotalInfantil.setText("S/0.00");
        
        
        btnMenosAdulto.setEnabled(false); // Inicia apagado porque no se puede restar a 0 boletos

            // 1. Llenamos los datos visuales en el panel izquierdo (panelInfo)
            if (peliculaElegida != null) {
                // Se coloca la imagen
                lblFotoPeli.setIcon(new javax.swing.ImageIcon(getClass().getResource(peliculaElegida.getRutaImagen())));
                // Se coloca el titulo
                lblTituloPeli.setText(peliculaElegida.getTitulo());
                //Se coloca la Duracion y Clasificacion
                lblDuracionCategoria.setText(peliculaElegida.getDuracion() + " " + peliculaElegida.getClasificacion());
                // Se coloca la Fecha y Hora
                lblDatosHoraFecha.setText("Fecha: " + fecha + " - Hora: " + hora);
                // Se coloca la Sala
                lblSalaPeli.setText("SALA: 01");
            }

    }
    private void actualizarSumaTotalGlobal() {
        // Calcular el subtotal de cada categoría en base a los contadores actuales
        double totalAdultos = contadorAdultos * 13.00;
        double totalNinos = contadorNinos * 9.00;
        double totalMayor = contadorMayor * 13.00;
        
        // Sumar todo
        double sumaGeneral = totalAdultos + totalNinos + totalMayor;
        
        // Mostrar el resultado formateado con dos decimales en el JTextField
        lblSumaTotal.setText(String.format("S/%.2f", sumaGeneral));
    }
// Método para que cualquier otra pantalla pueda obtener el texto del total
    public String obtenerTextoSumaTotal() {
        return lblSumaTotal.getText(); // Retorna "TOTAL: S/XX.XX"
    }

    // === GETTERS para que Pago.java pueda armar el comprobante final ===
    public int getContadorAdultos() { return contadorAdultos; }
    public int getContadorNinos() { return contadorNinos; }
    public int getContadorMayor() { return contadorMayor; }

    // === Convierte las entradas elegidas en Detalle_Venta usando la clase Entrada ===
    // Así las entradas se pueden sumar dentro de la misma Venta que los productos de dulcería.
    public java.util.List<CLASES.Detalle_Venta> obtenerDetallesEntradas() {
        java.util.List<CLASES.Detalle_Venta> detalles = new java.util.ArrayList<>();

        if (contadorAdultos > 0) {
            CLASES.Entrada entradaAdulto = new CLASES.Entrada("E-AD", "Entrada Adulto", 13.00, "Adulto");
            detalles.add(new CLASES.Detalle_Venta("D-AD", entradaAdulto, contadorAdultos));
        }
        if (contadorNinos > 0) {
            CLASES.Entrada entradaNino = new CLASES.Entrada("E-NI", "Entrada Niño", 9.00, "Niño");
            detalles.add(new CLASES.Detalle_Venta("D-NI", entradaNino, contadorNinos));
        }
        if (contadorMayor > 0) {
            CLASES.Entrada entradaMayor = new CLASES.Entrada("E-AM", "Entrada Adulto Mayor", 13.00, "Adulto Mayor");
            detalles.add(new CLASES.Detalle_Venta("D-AM", entradaMayor, contadorMayor));
        }
        return detalles;
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        panelSala = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        PrecTotalAdultoMayor = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        cantInfantil = new javax.swing.JLabel();
        cantAdultoMayor = new javax.swing.JLabel();
        cantAdultos = new javax.swing.JLabel();
        btnMasAdultoMayor = new javax.swing.JButton();
        btnMenosAdultoMayor = new javax.swing.JButton();
        btnMasAdulto = new javax.swing.JButton();
        btnMenosAdulto = new javax.swing.JButton();
        btnMenosInfantil = new javax.swing.JButton();
        btnMasInfantil = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        PrecTotalAdult = new javax.swing.JLabel();
        PrecTotalInfantil = new javax.swing.JLabel();
        fondoLblButacasSeleccionadas = new javax.swing.JPanel();
        lblSumaTotal = new javax.swing.JLabel();
        TOTAL = new javax.swing.JLabel();
        btnSiguiente = new javax.swing.JButton();
        fondo = new javax.swing.JLabel();
        panelInfo = new javax.swing.JPanel();
        lblFotoPeli = new javax.swing.JLabel();
        lblTituloPeli = new javax.swing.JLabel();
        lblDuracionCategoria = new javax.swing.JLabel();
        lblDatosHoraFecha = new javax.swing.JLabel();
        lblSalaPeli = new javax.swing.JLabel();
        btnVolver = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        jPanel1.setLayout(new java.awt.BorderLayout());

        panelSala.setBackground(new java.awt.Color(0, 0, 23));
        panelSala.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(0, 9, 86));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Segoe UI Black", 0, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Entradas");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(48, 20, -1, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI Black", 0, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("ADULTO");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(48, 112, -1, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI Black", 0, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("ADULTOMAYOR(+60)");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(48, 391, -1, -1));

        jLabel6.setFont(new java.awt.Font("Segoe UI Black", 0, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("INFANTIL(2-8años)");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(48, 267, -1, -1));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("S/13.00");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(48, 441, 170, -1));

        PrecTotalAdultoMayor.setBackground(new java.awt.Color(47, 107, 255));
        PrecTotalAdultoMayor.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        PrecTotalAdultoMayor.setForeground(new java.awt.Color(47, 107, 255));
        PrecTotalAdultoMayor.setText("S/0.00");
        jPanel2.add(PrecTotalAdultoMayor, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 435, 90, -1));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("S/9.00");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(48, 317, 170, -1));

        cantInfantil.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        cantInfantil.setForeground(new java.awt.Color(255, 255, 255));
        cantInfantil.setText("0");
        jPanel2.add(cantInfantil, new org.netbeans.lib.awtextra.AbsoluteConstraints(745, 300, -1, 40));

        cantAdultoMayor.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        cantAdultoMayor.setForeground(new java.awt.Color(255, 255, 255));
        cantAdultoMayor.setText("0");
        jPanel2.add(cantAdultoMayor, new org.netbeans.lib.awtextra.AbsoluteConstraints(745, 430, -1, 40));

        cantAdultos.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        cantAdultos.setForeground(new java.awt.Color(255, 255, 255));
        cantAdultos.setText("0");
        jPanel2.add(cantAdultos, new org.netbeans.lib.awtextra.AbsoluteConstraints(745, 150, -1, 60));

        btnMasAdultoMayor.setBackground(new java.awt.Color(37, 99, 255));
        btnMasAdultoMayor.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        btnMasAdultoMayor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fondo/icons8-más-32.png"))); // NOI18N
        btnMasAdultoMayor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMasAdultoMayorActionPerformed(evt);
            }
        });
        jPanel2.add(btnMasAdultoMayor, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 435, 30, 30));

        btnMenosAdultoMayor.setBackground(new java.awt.Color(37, 99, 255));
        btnMenosAdultoMayor.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        btnMenosAdultoMayor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fondo/menos.png"))); // NOI18N
        btnMenosAdultoMayor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMenosAdultoMayorActionPerformed(evt);
            }
        });
        jPanel2.add(btnMenosAdultoMayor, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 435, 30, 30));

        btnMasAdulto.setBackground(new java.awt.Color(37, 99, 255));
        btnMasAdulto.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        btnMasAdulto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fondo/icons8-más-32.png"))); // NOI18N
        btnMasAdulto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMasAdultoActionPerformed(evt);
            }
        });
        jPanel2.add(btnMasAdulto, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 165, 30, 30));

        btnMenosAdulto.setBackground(new java.awt.Color(37, 99, 255));
        btnMenosAdulto.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        btnMenosAdulto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fondo/menos.png"))); // NOI18N
        btnMenosAdulto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMenosAdultoActionPerformed(evt);
            }
        });
        jPanel2.add(btnMenosAdulto, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 165, 30, 30));

        btnMenosInfantil.setBackground(new java.awt.Color(37, 99, 255));
        btnMenosInfantil.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        btnMenosInfantil.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fondo/menos.png"))); // NOI18N
        btnMenosInfantil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMenosInfantilActionPerformed(evt);
            }
        });
        jPanel2.add(btnMenosInfantil, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 305, 30, 30));

        btnMasInfantil.setBackground(new java.awt.Color(37, 99, 255));
        btnMasInfantil.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        btnMasInfantil.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fondo/icons8-más-32.png"))); // NOI18N
        btnMasInfantil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMasInfantilActionPerformed(evt);
            }
        });
        jPanel2.add(btnMasInfantil, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 305, 30, 30));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("S/13.00");
        jPanel2.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 170, 170, -1));

        PrecTotalAdult.setBackground(new java.awt.Color(47, 107, 255));
        PrecTotalAdult.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        PrecTotalAdult.setForeground(new java.awt.Color(47, 107, 255));
        PrecTotalAdult.setText("0");
        jPanel2.add(PrecTotalAdult, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 165, 90, -1));

        PrecTotalInfantil.setBackground(new java.awt.Color(47, 107, 255));
        PrecTotalInfantil.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        PrecTotalInfantil.setForeground(new java.awt.Color(47, 107, 255));
        PrecTotalInfantil.setText("0");
        jPanel2.add(PrecTotalInfantil, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 305, 90, -1));

        panelSala.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 960, 530));

        fondoLblButacasSeleccionadas.setBackground(new java.awt.Color(0, 9, 39));

        lblSumaTotal.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblSumaTotal.setForeground(new java.awt.Color(255, 255, 255));
        lblSumaTotal.setText("0.00");

        TOTAL.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        TOTAL.setForeground(new java.awt.Color(255, 255, 255));
        TOTAL.setText("TOTAL: ");

        javax.swing.GroupLayout fondoLblButacasSeleccionadasLayout = new javax.swing.GroupLayout(fondoLblButacasSeleccionadas);
        fondoLblButacasSeleccionadas.setLayout(fondoLblButacasSeleccionadasLayout);
        fondoLblButacasSeleccionadasLayout.setHorizontalGroup(
            fondoLblButacasSeleccionadasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, fondoLblButacasSeleccionadasLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(TOTAL)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblSumaTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 888, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        fondoLblButacasSeleccionadasLayout.setVerticalGroup(
            fondoLblButacasSeleccionadasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, fondoLblButacasSeleccionadasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(fondoLblButacasSeleccionadasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSumaTotal, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
                    .addComponent(TOTAL, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)))
        );

        panelSala.add(fondoLblButacasSeleccionadas, new org.netbeans.lib.awtextra.AbsoluteConstraints(161, 609, 616, 60));

        btnSiguiente.setBackground(new java.awt.Color(0, 9, 80));
        btnSiguiente.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        btnSiguiente.setForeground(new java.awt.Color(255, 255, 255));
        btnSiguiente.setText("SIGUIENTE");
        btnSiguiente.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnSiguiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSiguienteActionPerformed(evt);
            }
        });
        panelSala.add(btnSiguiente, new org.netbeans.lib.awtextra.AbsoluteConstraints(845, 629, 163, 40));

        fondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fondo/fondoasientos.png"))); // NOI18N
        panelSala.add(fondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1030, -1));

        jPanel1.add(panelSala, java.awt.BorderLayout.CENTER);

        panelInfo.setBackground(new java.awt.Color(0, 9, 39));

        lblFotoPeli.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblFotoPeli.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblFotoPeli.setText("PORTADA PELICULA");
        lblFotoPeli.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 0)));

        lblTituloPeli.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTituloPeli.setForeground(new java.awt.Color(255, 255, 255));
        lblTituloPeli.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTituloPeli.setText("TITULO");

        lblDuracionCategoria.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblDuracionCategoria.setForeground(new java.awt.Color(255, 255, 255));
        lblDuracionCategoria.setText("DURACION Y CATEGORIA");

        lblDatosHoraFecha.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblDatosHoraFecha.setForeground(new java.awt.Color(255, 255, 255));
        lblDatosHoraFecha.setText("FECHA Y HORA ELEGIDA ");

        lblSalaPeli.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblSalaPeli.setForeground(new java.awt.Color(255, 255, 255));
        lblSalaPeli.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblSalaPeli.setText("SALA PELI");

        btnVolver.setBackground(new java.awt.Color(0, 9, 80));
        btnVolver.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        btnVolver.setForeground(new java.awt.Color(255, 255, 255));
        btnVolver.setText("VOLVER");
        btnVolver.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverActionPerformed(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fondo/puerta.png"))); // NOI18N

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fondo/reloh.png"))); // NOI18N

        javax.swing.GroupLayout panelInfoLayout = new javax.swing.GroupLayout(panelInfo);
        panelInfo.setLayout(panelInfoLayout);
        panelInfoLayout.setHorizontalGroup(
            panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInfoLayout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(lblFotoPeli, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(50, Short.MAX_VALUE))
            .addGroup(panelInfoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnVolver, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(lblTituloPeli, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblDuracionCategoria, javax.swing.GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE))
                    .addGroup(panelInfoLayout.createSequentialGroup()
                        .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblDatosHoraFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblSalaPeli, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelInfoLayout.setVerticalGroup(
            panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInfoLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(lblFotoPeli, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(lblTituloPeli, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblDuracionCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblDatosHoraFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelInfoLayout.createSequentialGroup()
                        .addComponent(lblSalaPeli, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnVolver, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35))
                    .addGroup(panelInfoLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        jPanel1.add(panelInfo, java.awt.BorderLayout.WEST);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1330, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1330, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 702, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 702, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        java.awt.Window ventana = javax.swing.SwingUtilities.getWindowAncestor(this);
        if (ventana instanceof javax.swing.JFrame) {
            javax.swing.JFrame framePrincipal = (javax.swing.JFrame) ventana;

            // Recuperamos del historial la pantalla de Asientos desde la que llegamos,
            // con los asientos que ya tenía marcados.
            if (CLASES.Navegador.hayHistorial()) {
                CLASES.Navegador.regresar(framePrincipal);
            } else if (panelAnterior != null) {
                // Respaldo por si no hay historial guardado
                framePrincipal.setContentPane(panelAnterior);
                framePrincipal.revalidate();
                framePrincipal.repaint();
            }
        }
    }//GEN-LAST:event_btnVolverActionPerformed

    private void btnSiguienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSiguienteActionPerformed
// Calcular el total de entradas seleccionadas actualmente
        int totalEntradasSeleccionadas = contadorAdultos + contadorNinos + contadorMayor;
        
        // VALIDACIÓN: Si no coincide con la cantidad de asientos elegidos
        if (totalEntradasSeleccionadas != cantAsientos) {
            javax.swing.JOptionPane.showMessageDialog(
                this, 
                "Por favor, ingrese la cantidad correcta de las entradas.", 
                "Entradas Incompletas", 
                javax.swing.JOptionPane.WARNING_MESSAGE
            );
            return; // Detiene la ejecución del método para que no avance de pantalla
        }

        // SI PASA LA VALIDACIÓN, CONTINÚA CON EL CAMBIO DE PANTALLA:
        java.awt.Window ventana = javax.swing.SwingUtilities.getWindowAncestor(this);
        if (ventana instanceof javax.swing.JFrame) {
            javax.swing.JFrame framePrincipal = (javax.swing.JFrame) ventana;

            // Pasamos "this" (este panel de asientos con sus datos) al siguiente panel
            Dulceria nuevoPanel = new Dulceria(this);

            nuevoPanel.setSize(framePrincipal.getSize());
            CLASES.Navegador.irA(framePrincipal, nuevoPanel);
        }
    }//GEN-LAST:event_btnSiguienteActionPerformed

    private void btnMasAdultoMayorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMasAdultoMayorActionPerformed
// Validación global: Suma de los tres tipos de boleto
        if ((contadorAdultos + contadorNinos + contadorMayor) < cantAsientos) {
            contadorMayor++; // Sumar 1 boleto de adulto mayor
            
            // Actualizar el JTextField de la cantidad
            cantAdultoMayor.setText(String.valueOf(contadorMayor));
            
            // Calcular el precio total (S/13.00 cada uno)
            double totalMayor = contadorMayor * 13.00;
            PrecTotalAdultoMayor.setText(String.format("S/%.2f", totalMayor));
            
            // Activar el botón menos de adulto mayor
            btnMenosAdultoMayor.setEnabled(true);
        }
        
        // Si se llegó al límite de asientos totales, bloquea los TRES botones MÁS inmediatamente
        if ((contadorAdultos + contadorNinos + contadorMayor) == cantAsientos) {
            btnMasAdulto.setEnabled(false);
            btnMasInfantil.setEnabled(false);
            btnMasAdultoMayor.setEnabled(false);
        }
        actualizarSumaTotalGlobal();
    }//GEN-LAST:event_btnMasAdultoMayorActionPerformed

    private void btnMenosAdultoMayorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenosAdultoMayorActionPerformed
// Verificar que no baje de cero
        if (contadorMayor > 0) {
            contadorMayor--; // Restar 1 boleto de adulto mayor
            
            // Actualizar el JTextField de la cantidad
            cantAdultoMayor.setText(String.valueOf(contadorMayor));
            
            // Calcular el nuevo precio total
            double totalMayor = contadorMayor * 13.00;
            
            if (contadorMayor == 0) {
                PrecTotalAdultoMayor.setText("S/0.00");
            } else {
                PrecTotalAdultoMayor.setText(String.format("S/%.2f", totalMayor));
            }
            
            // Como liberamos un asiento, reactivamos los TRES botones MÁS
            btnMasAdulto.setEnabled(true);
            btnMasInfantil.setEnabled(true);
            btnMasAdultoMayor.setEnabled(true);
        }
        
        // Si volvió a cero, desactiva este botón menos de adulto mayor
        if (contadorMayor == 0) {
            btnMenosAdultoMayor.setEnabled(false);
            actualizarSumaTotalGlobal();
        }
    }//GEN-LAST:event_btnMenosAdultoMayorActionPerformed

    private void btnMasAdultoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMasAdultoActionPerformed
// Validación global: Suma de los tres tipos de boleto
        if ((contadorAdultos + contadorNinos + contadorMayor) < cantAsientos) {
            contadorAdultos++; // Sumar 1 boleto
            
            // Actualizar el JTextField de la cantidad
            cantAdultos.setText(String.valueOf(contadorAdultos));
            
            // Calcular el precio total (13.00 por cada boleto)
            double total = contadorAdultos * 13.00;
            PrecTotalAdult.setText(String.format("S/%.2f", total));
            
            // Activar el botón menos
            btnMenosAdulto.setEnabled(true);
        }
        
        // Si se llegó al límite de asientos totales, bloquea los TRES botones MÁS inmediatamente
        if ((contadorAdultos + contadorNinos + contadorMayor) == cantAsientos) {
            btnMasAdulto.setEnabled(false);
            btnMasInfantil.setEnabled(false);
            btnMasAdultoMayor.setEnabled(false);
        }
        actualizarSumaTotalGlobal();
    }//GEN-LAST:event_btnMasAdultoActionPerformed

    private void btnMenosAdultoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenosAdultoActionPerformed
// Verificar que no baje de cero
        if (contadorAdultos > 0) {
            contadorAdultos--; // Restar 1 boleto
            
            // Actualizar el JTextField de la cantidad
            cantAdultos.setText(String.valueOf(contadorAdultos));
            
            // Calcular el nuevo precio total
            double total = contadorAdultos * 13.00;
            
            if (contadorAdultos == 0) {
                PrecTotalAdult.setText("S/0.00"); 
            } else {
                PrecTotalAdult.setText(String.format("S/%.2f", total));
            }
            
            // Como liberamos un asiento, reactivamos los TRES botones MÁS
            btnMasAdulto.setEnabled(true);
            btnMasInfantil.setEnabled(true);
            btnMasAdultoMayor.setEnabled(true);
        }
        
        // Si el contador llegó a 0, se deshabilita este botón menos
        if (contadorAdultos == 0) {
            btnMenosAdulto.setEnabled(false); 
        }
        actualizarSumaTotalGlobal();
    }//GEN-LAST:event_btnMenosAdultoActionPerformed

    private void btnMenosInfantilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenosInfantilActionPerformed
// Verificar que no baje de cero
        if (contadorNinos > 0) {
            contadorNinos--; // Restar 1 boleto infantil
            
            // Actualizar el JTextField de la cantidad
            cantInfantil.setText(String.valueOf(contadorNinos));
            
            // Calcular el nuevo precio total de niños
            double totalNinos = contadorNinos * 9.00;
            
            if (contadorNinos == 0) {
                PrecTotalInfantil.setText("S/0.00");
            } else {
                PrecTotalInfantil.setText(String.format("S/%.2f", totalNinos));
            }
            
            // Como liberamos un asiento, reactivamos los TRES botones MÁS
            btnMasAdulto.setEnabled(true);
            btnMasInfantil.setEnabled(true);
            btnMasAdultoMayor.setEnabled(true);
        }
        
        // Si volvió a cero, apaga este botón menos infantil
        if (contadorNinos == 0) {
            btnMenosInfantil.setEnabled(false);
        }
        actualizarSumaTotalGlobal();
    }//GEN-LAST:event_btnMenosInfantilActionPerformed

    private void btnMasInfantilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMasInfantilActionPerformed
// Validación global: Suma de los tres tipos de boleto
        if ((contadorAdultos + contadorNinos + contadorMayor) < cantAsientos) {
            contadorNinos++; // Sumar 1 boleto infantil
            
            // Actualizar el JTextField de la cantidad de niños
            cantInfantil.setText(String.valueOf(contadorNinos));
            
            // Calcular el precio total de niños (9.00 por cada uno)
            double totalNinos = contadorNinos * 9.00;
            PrecTotalInfantil.setText(String.format("S/%.2f", totalNinos));
            
            // Activar el botón menos infantil
            btnMenosInfantil.setEnabled(true);
        }
        
        // Si se llegó al límite de asientos totales, bloquea los TRES botones MÁS inmediatamente
        if ((contadorAdultos + contadorNinos + contadorMayor) == cantAsientos) {
            btnMasAdulto.setEnabled(false);
            btnMasInfantil.setEnabled(false);
            btnMasAdultoMayor.setEnabled(false);
        }
        actualizarSumaTotalGlobal();
    }//GEN-LAST:event_btnMasInfantilActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel PrecTotalAdult;
    private javax.swing.JLabel PrecTotalAdultoMayor;
    private javax.swing.JLabel PrecTotalInfantil;
    private javax.swing.JLabel TOTAL;
    private javax.swing.JButton btnMasAdulto;
    private javax.swing.JButton btnMasAdultoMayor;
    private javax.swing.JButton btnMasInfantil;
    private javax.swing.JButton btnMenosAdulto;
    private javax.swing.JButton btnMenosAdultoMayor;
    private javax.swing.JButton btnMenosInfantil;
    private javax.swing.JButton btnSiguiente;
    private javax.swing.JButton btnVolver;
    private javax.swing.JLabel cantAdultoMayor;
    private javax.swing.JLabel cantAdultos;
    private javax.swing.JLabel cantInfantil;
    private javax.swing.JLabel fondo;
    private javax.swing.JPanel fondoLblButacasSeleccionadas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblDatosHoraFecha;
    private javax.swing.JLabel lblDuracionCategoria;
    private javax.swing.JLabel lblFotoPeli;
    private javax.swing.JLabel lblSalaPeli;
    private javax.swing.JLabel lblSumaTotal;
    private javax.swing.JLabel lblTituloPeli;
    private javax.swing.JPanel panelInfo;
    private javax.swing.JPanel panelSala;
    // End of variables declaration//GEN-END:variables
}
