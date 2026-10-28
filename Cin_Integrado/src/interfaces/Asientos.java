package interfaces;

/**
 * Pantalla de selección de butacas para la función elegida en Horarios.
 * Dibuja la sala completa (filas A-M, 16 columnas) y permite marcar/desmarcar
 * asientos hasta completar la cantidad indicada. Al confirmar, navega a
 * Boletos para elegir el tipo de entrada de cada butaca seleccionada.
 */
public class Asientos extends javax.swing.JPanel {
    int cantAsientos = 0;
    // Guardamos cada asiento con su código como clave (ej. "M05") para poder
    // encontrarlo y cambiar su estado directamente, sin recorrer toda la sala
    // butaca por butaca cada vez que el usuario hace clic en una.
    private java.util.HashMap<String, CLASES.Asiento> mapaAsientos = new java.util.HashMap<>();
    
    // Lista de apoyo para los nombres visuales
    private java.util.ArrayList<String> nombresSeleccionados = new java.util.ArrayList<>();
    
    private CLASES.Pelicula peliculaElegida;
    private String horaElegida;
    private String fechaElegida;
        public Asientos(CLASES.Pelicula p, String hora, String fecha) {
                initComponents();
                this.peliculaElegida = p;
                this.horaElegida = hora;
                this.fechaElegida = fecha;

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
            // 1. Inicializamos el HashMap con todos los asientos de la sala
            inicializarMapaAsientos();

            // 2. Dibujamos los botones
            generarAsientos();  
        }
        
// Recorre toda la sala (filas M..A, columnas 1..16) y va registrando cada
    // asiento en el mapa con su código como clave, para poder ubicarlo rápido después.
    private void inicializarMapaAsientos() {
        // Buscamos qué butacas ya fueron compradas antes para esta misma función
        // (misma película + misma fecha + misma hora), para bloquearlas.
        String clave = CLASES.BaseDeDatos.claveFuncion(peliculaElegida.getId(), fechaElegida, horaElegida);
        java.util.Set<String> ocupados = new java.util.HashSet<>(CLASES.BaseDeDatos.obtenerAsientosOcupados(clave));
        // También consultamos las ventas persistidas para no permitir comprar
        // una butaca que ya fue vendida desde otra ejecución del programa.
        try {
            ocupados.addAll(CLASES.PersistenciaVentasMySQL.obtenerAsientosVendidos(
                    peliculaElegida.getId(), fechaElegida, horaElegida, "S01"));
        } catch (java.sql.SQLException ex) {
            System.err.println("No se pudo consultar el estado de asientos en MySQL: " + ex.getMessage());
        }

        char letra = 'M';
        for (int i = 0; i < 13; i++) {
            for (int j = 1; j <= 16; j++) {
                String id = letra + String.format("%02d", j);
                // Si esa butaca ya fue vendida en una compra anterior, nace "Ocupado"; si no, "Libre"
                String estadoInicial = ocupados.contains(id) ? "Ocupado" : "Libre";
                mapaAsientos.put(id, new CLASES.Asiento(id, id, estadoInicial));
            }
            letra--;
        }
    }
    
private void generarAsientos() {

    int filas = 13;
    int columnasTotaleslogicas = 18;

    contenedorAsientos.setLayout(new java.awt.GridLayout(filas, columnasTotaleslogicas, 5, 5));

    char letraFila = 'M';

    for (int i = 0; i < filas; i++) {

        int numeroAsiento = 1; // contador real

        for (int j = 1; j <= columnasTotaleslogicas; j++) {

            // PASILLOS
            if (j == 3 || j == 16) {

                javax.swing.JLabel huecoPasillo = new javax.swing.JLabel("");
                huecoPasillo.setOpaque(false);
                contenedorAsientos.add(huecoPasillo);

                continue;
            }

            // Nombre del asiento
            // Ejemplo: M01
            String nombreAsiento = letraFila + String.format("%02d", numeroAsiento);

            BotonAsientoRedondo btnAsiento = new BotonAsientoRedondo(nombreAsiento);

            // Si esta butaca ya está "Ocupado" (comprada en una compra anterior de
            // esta misma función), la bloqueamos: no se puede seleccionar ni hacer clic.
            CLASES.Asiento butacaInicial = mapaAsientos.get(nombreAsiento);
            if (butacaInicial != null && "Ocupado".equals(butacaInicial.getEstado())) {
                btnAsiento.marcarComoOcupado();
                btnAsiento.setEnabled(false);
            }

            btnAsiento.addActionListener(new java.awt.event.ActionListener() {

                public void actionPerformed(java.awt.event.ActionEvent evt) {

                    // === BÚSQUEDA INSTANTÁNEA EN EL HASHMAP ===
                    CLASES.Asiento butaca = mapaAsientos.get(btnAsiento.getName());

                    // SI EL ASIENTO SE SELECCIONA
                    if (btnAsiento.isSelected()) {

                        butaca.setEstado("Seleccionado");

                        nombresSeleccionados.add(butaca.getNumero());

                        // Guardamos el objeto en la preventa
                        CLASES.SesionActual.preventaEnCurso.agregarAsiento(butaca);

                        // AUMENTA LA CANTIDAD DE ASIENTOS
                        cantAsientos++;

                    } else {

                        // SI EL ASIENTO SE DESELECCIONA
                        butaca.setEstado("Libre");

                        nombresSeleccionados.remove(butaca.getNumero());

                        // Quitamos el asiento de la preventa
                        CLASES.SesionActual.preventaEnCurso.getAsientos().remove(butaca);

                        // DISMINUYE LA CANTIDAD DE ASIENTOS
                        cantAsientos--;
                    }

                    // Muestra la cantidad actual en consola
                    System.out.println("Cantidad de asientos: " + cantAsientos);

                    actualizarTextoButacas();
                }
            });

            contenedorAsientos.add(btnAsiento);

            numeroAsiento++; // solo aumenta cuando sí hay asiento
        }

        letraFila--;
    }

    contenedorAsientos.revalidate();
    contenedorAsientos.repaint();
}

    // MÉTODO PARA ACTUALIZAR EL TEXTO
    private void actualizarTextoButacas() {
        // String.join une todos los elementos de la lista separados por comas
        lblButacasSeleccionadas.setText("Butacas: " + String.join(", ", nombresSeleccionados));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelSala = new javax.swing.JPanel();
        fondoLblPantalla = new javax.swing.JPanel();
        lblPantalla = new javax.swing.JLabel();
        btnSiguiente = new javax.swing.JButton();
        fondoLblButacasSeleccionadas = new javax.swing.JPanel();
        lblButacasSeleccionadas = new javax.swing.JLabel();
        contenedorAsientos = new javax.swing.JPanel();
        A = new javax.swing.JLabel();
        B = new javax.swing.JLabel();
        I = new javax.swing.JLabel();
        K = new javax.swing.JLabel();
        N18 = new javax.swing.JLabel();
        N17 = new javax.swing.JLabel();
        N1 = new javax.swing.JLabel();
        N2 = new javax.swing.JLabel();
        N5 = new javax.swing.JLabel();
        N9 = new javax.swing.JLabel();
        N12 = new javax.swing.JLabel();
        N13 = new javax.swing.JLabel();
        N14 = new javax.swing.JLabel();
        N15 = new javax.swing.JLabel();
        N16 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        N6 = new javax.swing.JLabel();
        N8 = new javax.swing.JLabel();
        N7 = new javax.swing.JLabel();
        N11 = new javax.swing.JLabel();
        N10 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        C = new javax.swing.JLabel();
        D = new javax.swing.JLabel();
        M = new javax.swing.JLabel();
        L = new javax.swing.JLabel();
        E = new javax.swing.JLabel();
        F = new javax.swing.JLabel();
        G = new javax.swing.JLabel();
        H = new javax.swing.JLabel();
        J = new javax.swing.JLabel();
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

        setLayout(new java.awt.BorderLayout());

        panelSala.setBackground(new java.awt.Color(0, 0, 23));
        panelSala.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        fondoLblPantalla.setBackground(new java.awt.Color(219, 219, 219));
        fondoLblPantalla.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblPantalla.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblPantalla.setForeground(new java.awt.Color(0, 0, 51));
        lblPantalla.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPantalla.setText("PANTALLA");
        fondoLblPantalla.add(lblPantalla, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 616, 54));

        panelSala.add(fondoLblPantalla, new org.netbeans.lib.awtextra.AbsoluteConstraints(161, 43, -1, -1));

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

        fondoLblButacasSeleccionadas.setBackground(new java.awt.Color(0, 9, 39));

        lblButacasSeleccionadas.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblButacasSeleccionadas.setForeground(new java.awt.Color(255, 255, 255));
        lblButacasSeleccionadas.setText("Butacas: ");

        javax.swing.GroupLayout fondoLblButacasSeleccionadasLayout = new javax.swing.GroupLayout(fondoLblButacasSeleccionadas);
        fondoLblButacasSeleccionadas.setLayout(fondoLblButacasSeleccionadasLayout);
        fondoLblButacasSeleccionadasLayout.setHorizontalGroup(
            fondoLblButacasSeleccionadasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fondoLblButacasSeleccionadasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblButacasSeleccionadas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        fondoLblButacasSeleccionadasLayout.setVerticalGroup(
            fondoLblButacasSeleccionadasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, fondoLblButacasSeleccionadasLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lblButacasSeleccionadas, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        panelSala.add(fondoLblButacasSeleccionadas, new org.netbeans.lib.awtextra.AbsoluteConstraints(161, 629, 616, -1));

        contenedorAsientos.setBackground(new java.awt.Color(234, 234, 234));

        javax.swing.GroupLayout contenedorAsientosLayout = new javax.swing.GroupLayout(contenedorAsientos);
        contenedorAsientos.setLayout(contenedorAsientosLayout);
        contenedorAsientosLayout.setHorizontalGroup(
            contenedorAsientosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 616, Short.MAX_VALUE)
        );
        contenedorAsientosLayout.setVerticalGroup(
            contenedorAsientosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 460, Short.MAX_VALUE)
        );

        panelSala.add(contenedorAsientos, new org.netbeans.lib.awtextra.AbsoluteConstraints(161, 131, 616, 460));

        A.setFont(new java.awt.Font("Segoe UI", 1, 19)); // NOI18N
        A.setForeground(new java.awt.Color(255, 255, 255));
        A.setText("A");
        panelSala.add(A, new org.netbeans.lib.awtextra.AbsoluteConstraints(789, 559, -1, 20));

        B.setFont(new java.awt.Font("Segoe UI", 1, 19)); // NOI18N
        B.setForeground(new java.awt.Color(255, 255, 255));
        B.setText("B");
        panelSala.add(B, new org.netbeans.lib.awtextra.AbsoluteConstraints(789, 521, -1, -1));

        I.setFont(new java.awt.Font("Segoe UI", 1, 19)); // NOI18N
        I.setForeground(new java.awt.Color(255, 255, 255));
        I.setText("I");
        panelSala.add(I, new org.netbeans.lib.awtextra.AbsoluteConstraints(789, 269, -1, 40));

        K.setFont(new java.awt.Font("Segoe UI", 1, 19)); // NOI18N
        K.setForeground(new java.awt.Color(255, 255, 255));
        K.setText("K");
        panelSala.add(K, new org.netbeans.lib.awtextra.AbsoluteConstraints(789, 211, -1, -1));

        N18.setFont(new java.awt.Font("Segoe UI", 1, 19)); // NOI18N
        N18.setForeground(new java.awt.Color(255, 255, 255));
        N18.setText("16");
        panelSala.add(N18, new org.netbeans.lib.awtextra.AbsoluteConstraints(749, 597, -1, 20));

        N17.setFont(new java.awt.Font("Segoe UI", 1, 19)); // NOI18N
        N17.setForeground(new java.awt.Color(255, 255, 255));
        N17.setText("15");
        panelSala.add(N17, new org.netbeans.lib.awtextra.AbsoluteConstraints(715, 597, -1, 20));

        N1.setFont(new java.awt.Font("Segoe UI", 1, 19)); // NOI18N
        N1.setForeground(new java.awt.Color(255, 255, 255));
        N1.setText("1");
        panelSala.add(N1, new org.netbeans.lib.awtextra.AbsoluteConstraints(173, 597, -1, 20));

        N2.setFont(new java.awt.Font("Segoe UI", 1, 19)); // NOI18N
        N2.setForeground(new java.awt.Color(255, 255, 255));
        N2.setText(" 2");
        panelSala.add(N2, new org.netbeans.lib.awtextra.AbsoluteConstraints(202, 597, 22, 20));

        N5.setFont(new java.awt.Font("Segoe UI", 1, 19)); // NOI18N
        N5.setForeground(new java.awt.Color(255, 255, 255));
        N5.setText("3");
        panelSala.add(N5, new org.netbeans.lib.awtextra.AbsoluteConstraints(273, 597, -1, 20));

        N9.setFont(new java.awt.Font("Segoe UI", 1, 19)); // NOI18N
        N9.setForeground(new java.awt.Color(255, 255, 255));
        N9.setText("7");
        panelSala.add(N9, new org.netbeans.lib.awtextra.AbsoluteConstraints(412, 597, -1, 20));

        N12.setFont(new java.awt.Font("Segoe UI", 1, 19)); // NOI18N
        N12.setForeground(new java.awt.Color(255, 255, 255));
        N12.setText("10");
        panelSala.add(N12, new org.netbeans.lib.awtextra.AbsoluteConstraints(509, 597, -1, 20));

        N13.setFont(new java.awt.Font("Segoe UI", 1, 19)); // NOI18N
        N13.setForeground(new java.awt.Color(255, 255, 255));
        N13.setText("11");
        panelSala.add(N13, new org.netbeans.lib.awtextra.AbsoluteConstraints(543, 597, -1, 20));

        N14.setFont(new java.awt.Font("Segoe UI", 1, 19)); // NOI18N
        N14.setForeground(new java.awt.Color(255, 255, 255));
        N14.setText("12");
        panelSala.add(N14, new org.netbeans.lib.awtextra.AbsoluteConstraints(577, 597, -1, 20));

        N15.setFont(new java.awt.Font("Segoe UI", 1, 19)); // NOI18N
        N15.setForeground(new java.awt.Color(255, 255, 255));
        N15.setText("13");
        panelSala.add(N15, new org.netbeans.lib.awtextra.AbsoluteConstraints(611, 597, -1, 20));

        N16.setFont(new java.awt.Font("Segoe UI", 1, 19)); // NOI18N
        N16.setForeground(new java.awt.Color(255, 255, 255));
        N16.setText("14");
        panelSala.add(N16, new org.netbeans.lib.awtextra.AbsoluteConstraints(645, 597, -1, 20));

        jPanel2.setBackground(new java.awt.Color(0, 0, 51,200));
        jPanel2.setForeground(new java.awt.Color(0, 0, 51));

        N6.setFont(new java.awt.Font("Segoe UI", 1, 19)); // NOI18N
        N6.setForeground(new java.awt.Color(255, 255, 255));
        N6.setText("4 ");

        N8.setFont(new java.awt.Font("Segoe UI", 1, 19)); // NOI18N
        N8.setForeground(new java.awt.Color(255, 255, 255));
        N8.setText("6");

        N7.setFont(new java.awt.Font("Segoe UI", 1, 19)); // NOI18N
        N7.setForeground(new java.awt.Color(255, 255, 255));
        N7.setText("5");

        N11.setFont(new java.awt.Font("Segoe UI", 1, 19)); // NOI18N
        N11.setForeground(new java.awt.Color(255, 255, 255));
        N11.setText(" 9");

        N10.setFont(new java.awt.Font("Segoe UI", 1, 19)); // NOI18N
        N10.setForeground(new java.awt.Color(255, 255, 255));
        N10.setText("8");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(145, 145, 145)
                .addComponent(N6)
                .addGap(18, 18, 18)
                .addComponent(N7)
                .addGap(26, 26, 26)
                .addComponent(N8)
                .addGap(59, 59, 59)
                .addComponent(N10)
                .addGap(18, 18, 18)
                .addComponent(N11, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(281, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 34, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(N6)
                    .addComponent(N8, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(N7, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(N11, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(N10, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        panelSala.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(161, 560, 616, 60));

        jPanel1.setBackground(new java.awt.Color(0, 0, 51,200));
        jPanel1.setForeground(new java.awt.Color(0, 0, 51));

        C.setFont(new java.awt.Font("Segoe UI", 1, 19)); // NOI18N
        C.setForeground(new java.awt.Color(255, 255, 255));
        C.setText(" C");

        D.setFont(new java.awt.Font("Segoe UI", 1, 19)); // NOI18N
        D.setForeground(new java.awt.Color(255, 255, 255));
        D.setText(" D");

        M.setFont(new java.awt.Font("Segoe UI", 1, 19)); // NOI18N
        M.setForeground(new java.awt.Color(255, 255, 255));
        M.setText("M");

        L.setFont(new java.awt.Font("Segoe UI", 1, 19)); // NOI18N
        L.setForeground(new java.awt.Color(255, 255, 255));
        L.setText("L");

        E.setFont(new java.awt.Font("Segoe UI", 1, 19)); // NOI18N
        E.setForeground(new java.awt.Color(255, 255, 255));
        E.setText("E");

        F.setFont(new java.awt.Font("Segoe UI", 1, 19)); // NOI18N
        F.setForeground(new java.awt.Color(255, 255, 255));
        F.setText("F");

        G.setFont(new java.awt.Font("Segoe UI", 1, 19)); // NOI18N
        G.setForeground(new java.awt.Color(255, 255, 255));
        G.setText("G");

        H.setFont(new java.awt.Font("Segoe UI", 1, 19)); // NOI18N
        H.setForeground(new java.awt.Color(255, 255, 255));
        H.setText(" H");

        J.setFont(new java.awt.Font("Segoe UI", 1, 19)); // NOI18N
        J.setForeground(new java.awt.Color(255, 255, 255));
        J.setText("  J");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(C, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(D, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(L)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(E)
                                .addComponent(M))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(G)
                                    .addComponent(F, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(H)
                            .addComponent(J, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(M)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(L, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 64, Short.MAX_VALUE)
                .addComponent(J)
                .addGap(39, 39, 39)
                .addComponent(H)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(G, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(F, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(E, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(D)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(C, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(106, 106, 106))
        );

        panelSala.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(778, 130, 40, 490));

        fondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fondo/fondoasientos.png"))); // NOI18N
        panelSala.add(fondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1030, -1));

        add(panelSala, java.awt.BorderLayout.CENTER);

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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 100, Short.MAX_VALUE)
                        .addComponent(btnVolver, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35))
                    .addGroup(panelInfoLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        add(panelInfo, java.awt.BorderLayout.WEST);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSiguienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSiguienteActionPerformed
        //MENSAJE QUE DARA SI EL USUARIO NO SELECCIONE UN ASIENTO
        if (nombresSeleccionados.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Por favor, selecciona al menos una butaca.");
            return;
        }

        java.awt.Window ventana = javax.swing.SwingUtilities.getWindowAncestor(this);
        if (ventana instanceof javax.swing.JFrame) {
            javax.swing.JFrame framePrincipal = (javax.swing.JFrame) ventana;

            // Pasamos "this" (este panel de asientos con sus datos) al siguiente panel
Boletos nuevoPanel = new Boletos(this,
        peliculaElegida,
        horaElegida,
        fechaElegida,
        cantAsientos
);

            nuevoPanel.setSize(framePrincipal.getSize());
            CLASES.Navegador.irA(framePrincipal, nuevoPanel);
        }
    }//GEN-LAST:event_btnSiguienteActionPerformed

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        // Buscamos la ventana principal
        java.awt.Window ventana = javax.swing.SwingUtilities.getWindowAncestor(this);
        if (ventana instanceof javax.swing.JFrame) {
            javax.swing.JFrame framePrincipal = (javax.swing.JFrame) ventana;

            // Recuperamos del historial la pantalla de Horarios desde la que llegamos,
            // con el día y la película que ya tenía seleccionados.
            if (CLASES.Navegador.hayHistorial()) {
                CLASES.Navegador.regresar(framePrincipal);
            } else {
                // Respaldo por si no hay historial guardado
                Horarios panelAnterior = new Horarios(peliculaElegida.getId());
                panelAnterior.setSize(framePrincipal.getSize());
                framePrincipal.setContentPane(panelAnterior);
                framePrincipal.revalidate();
                framePrincipal.repaint();
            }
        }
    }//GEN-LAST:event_btnVolverActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel A;
    private javax.swing.JLabel B;
    private javax.swing.JLabel C;
    private javax.swing.JLabel D;
    private javax.swing.JLabel E;
    private javax.swing.JLabel F;
    private javax.swing.JLabel G;
    private javax.swing.JLabel H;
    private javax.swing.JLabel I;
    private javax.swing.JLabel J;
    private javax.swing.JLabel K;
    private javax.swing.JLabel L;
    private javax.swing.JLabel M;
    private javax.swing.JLabel N1;
    private javax.swing.JLabel N10;
    private javax.swing.JLabel N11;
    private javax.swing.JLabel N12;
    private javax.swing.JLabel N13;
    private javax.swing.JLabel N14;
    private javax.swing.JLabel N15;
    private javax.swing.JLabel N16;
    private javax.swing.JLabel N17;
    private javax.swing.JLabel N18;
    private javax.swing.JLabel N2;
    private javax.swing.JLabel N5;
    private javax.swing.JLabel N6;
    private javax.swing.JLabel N7;
    private javax.swing.JLabel N8;
    private javax.swing.JLabel N9;
    private javax.swing.JButton btnSiguiente;
    private javax.swing.JButton btnVolver;
    private javax.swing.JPanel contenedorAsientos;
    private javax.swing.JLabel fondo;
    private javax.swing.JPanel fondoLblButacasSeleccionadas;
    private javax.swing.JPanel fondoLblPantalla;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblButacasSeleccionadas;
    private javax.swing.JLabel lblDatosHoraFecha;
    private javax.swing.JLabel lblDuracionCategoria;
    private javax.swing.JLabel lblFotoPeli;
    private javax.swing.JLabel lblPantalla;
    private javax.swing.JLabel lblSalaPeli;
    private javax.swing.JLabel lblTituloPeli;
    private javax.swing.JPanel panelInfo;
    private javax.swing.JPanel panelSala;
    // End of variables declaration//GEN-END:variables
}

    // Metodo para generar Asientos
class BotonAsientoRedondo extends javax.swing.JToggleButton {

    // Indica si esta butaca ya fue vendida antes (no se puede seleccionar)
    private boolean ocupado = false;

    public void marcarComoOcupado() {
        this.ocupado = true;
    }
    
    public BotonAsientoRedondo(String nombre) {
        super();
        setName(nombre);
        setToolTipText(nombre);
        setContentAreaFilled(false); 
        setBorderPainted(false); 
        setFocusPainted(false); 
        setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }

    @Override
    protected void paintComponent(java.awt.Graphics g) {
        java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
        g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);

        if (ocupado) {
            // Relleno gris oscuro: butaca ya vendida, no se puede tocar
            g2.setColor(new java.awt.Color(120, 120, 120));
            g2.fillOval(2, 2, getWidth() - 5, getHeight() - 5);
        } else if (isSelected()) {
            // Relleno Verde al seleccionar
            g2.setColor(new java.awt.Color(0, 200, 83)); 
            g2.fillOval(2, 2, getWidth() - 5, getHeight() - 5);
        } else {
            // Blanco con borde gris si está libre
            g2.setColor(java.awt.Color.WHITE); 
            g2.fillOval(2, 2, getWidth() - 5, getHeight() - 5);
            g2.setColor(java.awt.Color.LIGHT_GRAY); 
            g2.drawOval(2, 2, getWidth() - 5, getHeight() - 5);
        }
        g2.dispose();
        super.paintComponent(g);
    }
}
