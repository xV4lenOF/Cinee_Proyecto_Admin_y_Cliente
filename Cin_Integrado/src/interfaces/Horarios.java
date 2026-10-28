package interfaces;

import CLASES.BaseDeDatos;
import CLASES.Pelicula;
import java.awt.Dimension;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * Pantalla de horarios de una película: muestra su información (poster,
 * título, sinopsis, duración, clasificación) y los días/horas disponibles
 * para verla. Al elegir una función, arma la Preventa (o la fila de espera
 * si es un estreno) y navega a la pantalla de Asientos.
 */
public class Horarios extends javax.swing.JPanel {
    private Pelicula peliculaActual;
    private String diaSeleccionado = "HOY"; 
    public Horarios(int idPelicula) {    //ACTUALICE EL CONSTRUCTOR PARA QUE DIFERENCIE SI ENTRO 
        initComponents();                //A UNA PELICULA DISPONIBLE O PROXIMAMENTE PARA LO QUE ES LA COLA DE PREVENTA
try {

    java.time.LocalDate hoy = java.time.LocalDate.now();    // Obtiene la fecha actual del sistema
    java.time.LocalDate mañana = hoy.plusDays(1);    // Calcula la fecha de mañana sumando 1 día
    java.time.LocalDate pasado = hoy.plusDays(2);    // Calcula la fecha de pasado mañana sumando 2 días

    
    // Crea un formateador de fechas en español para que devuelva la fheca y mes
    java.time.format.DateTimeFormatter formateador =
        java.time.format.DateTimeFormatter.ofPattern(
            "d MMMM",
            new java.util.Locale("es", "ES")
        );

    
    // Coloca la fecha de hoy en el botón y la convierte a mayúsculas
    btnHOY.setText(hoy.format(formateador).toUpperCase());
    
    // Coloca la fecha de mañana en el botón
    btnMañana.setText(mañana.format(formateador).toUpperCase());
    
    // Coloca la fecha de pasado mañana en el botón
    btnPasado.setText(pasado.format(formateador).toUpperCase());

} catch (Exception e) {
    // Si ocurre algún error, coloca textos por defecto
    btnHOY.setText("HOY");
    btnMañana.setText("MAÑANA");
    btnPasado.setText("SIGUIENTE");
}   
        
        
        peliculaActual = CLASES.BaseDeDatos.buscarPeliculaPorId(idPelicula);
        
        if (peliculaActual != null) {
            FOTO.setIcon(new javax.swing.ImageIcon(getClass().getResource(peliculaActual.getRutaImagen())));
            NombrePelicula.setText(peliculaActual.getTitulo());
            Duracion.setText(peliculaActual.getDuracion());
            Clasificacion.setText(peliculaActual.getClasificacion());
            sintaxix.setText(strToHtml(peliculaActual.getSinopsis()));
            
            // 1. Verificamos si la película pertenece a "Próximamente"
            boolean esPreventa = false;
            for (CLASES.Pelicula p : CLASES.BaseDeDatos.peliculasProximamente) {
                if (p != null && p.getId() == peliculaActual.getId()) {
                    esPreventa = true;
                    diaSeleccionado = "PREVENTA"; // Ajustamos el texto para la info
                    break;
                }
            }
            
            // 2. Evaluamos si tiene horarios disponibles para mostrar
            if (peliculaActual.getHorariosPorDia() != null && peliculaActual.getHorariosPorDia().length > 0) {
                lblAviso.setVisible(false);           
                jScrollPane1.setVisible(true);        
                
                if (esPreventa) {
                    // === ES PREVENTA: OCULTAMOS BOTONES ===
                    btnHOY.setVisible(false);              
                    btnMañana.setVisible(false);
                    btnPasado.setVisible(false);
                    
                    // Unimos todas las fechas en una sola LinkedList (Punto para el profe)
                    java.util.LinkedList<String> todosLosHorarios = new java.util.LinkedList<>();
                    for (String[] horariosDelDia : peliculaActual.getHorariosPorDia()) {
                        for (String hora : horariosDelDia) {
                            todosLosHorarios.add(hora); // Ejemplo: "20 Mayo - 06:30 PM"
                        }
                    }
                    // Pasamos la lista unificada al generador de botones
                    cargarHorariosDinamicos(todosLosHorarios.toArray(new String[0]));
                    
                } else {
                    // === ES CARTELERA NORMAL: MOSTRAMOS LOS BOTONES ===
                    btnHOY.setVisible(true);              
                    btnMañana.setVisible(true);
                    btnPasado.setVisible(true);
                    
                    // Cargamos por defecto el índice 0 (HOY)
                    cargarHorariosDinamicos(peliculaActual.getHorariosPorDia()[0]);
                }
                
            } else {
                // 3. NO TIENE HORARIOS (Ej: Películas que recién anunciaron)
                lblAviso.setVisible(true);            
                jScrollPane1.setVisible(false);       // Ocultamos el fondo vacío
                btnHOY.setVisible(false);             
                btnMañana.setVisible(false);
                btnPasado.setVisible(false);
            }
            
        } else {
            NombrePelicula.setText("Película no encontrada");
        } 
    }
    
    // convierte el texto a html para los saltos de línea
    public String strToHtml(String texto) {
        return "<html>" + texto + "</html>";
    }
//############### ACTUALICE EL METODO EN LA PARTE DE ACCION DEL BOTON ###############
    // Método para generar las tarjetas dinámicamente
    private void cargarHorariosDinamicos(String[] horariosDeUnDia) {
        panelContenedorHorarios.removeAll();

        // Si la peli es "Próximamente" o el arreglo está vacío, no dibuja botones
        if (horariosDeUnDia == null || horariosDeUnDia.length == 0) {
            panelContenedorHorarios.revalidate();
            panelContenedorHorarios.repaint();
            return; 
        }

        // Recorremos el arreglo de horas del día seleccionado
        for (String hora : horariosDeUnDia) {
            
            javax.swing.JPanel tarjeta = new javax.swing.JPanel();
            tarjeta.setLayout(new java.awt.GridLayout(2, 1, 0, 10)); 
            tarjeta.setBackground(new java.awt.Color(0,9,39));

            javax.swing.border.Border linea = javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0,9,39), 1, true);
            javax.swing.border.Border padding = javax.swing.BorderFactory.createEmptyBorder(10, 15, 10, 15);
            tarjeta.setBorder(javax.swing.BorderFactory.createCompoundBorder(linea, padding));
            tarjeta.setPreferredSize(new java.awt.Dimension(140, 95)); 

            javax.swing.JLabel lblHora = new javax.swing.JLabel(hora, javax.swing.SwingConstants.CENTER);
            lblHora.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 16)); 
            lblHora.setForeground(new java.awt.Color(240, 240, 245)); 
            
            //CREACION BOTON 
            javax.swing.JButton btnComprar = new javax.swing.JButton("COMPRAR");
            btnComprar.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
            btnComprar.setForeground(java.awt.Color.WHITE); 
            btnComprar.setBackground(new java.awt.Color(204,0,0)); 
            btnComprar.setFocusPainted(false); 
            btnComprar.setBorderPainted(false); 
            btnComprar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
     
// ################################ CAMBIE EL METODO DE PULSACION DE BOTON ################################       
            //ACCION PULSACION DEL BOTON COMPRAR
            btnComprar.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    java.awt.Window ventana = javax.swing.SwingUtilities.getWindowAncestor(btnComprar);
                    if (ventana instanceof javax.swing.JFrame) {
                        javax.swing.JFrame framePrincipal = (javax.swing.JFrame) ventana;
                        
                        // 1. Iniciamos la sesión global
                        CLASES.SesionActual.reiniciarSesion(); 
                        CLASES.SesionActual.peliculaSeleccionada = peliculaActual;
                        
                        CLASES.Sala salaAsignada = new CLASES.Sala("S01", 1, 100);
                        CLASES.Funcion funcionElegida = new CLASES.Funcion("F001", diaSeleccionado, hora, peliculaActual, salaAsignada);
                        CLASES.SesionActual.preventaEnCurso.setFuncion(funcionElegida);

                        // 2. Comprobamos si la peli es de "Próximamente" (Preventa)
                        boolean esPreventa = false;
                        for (CLASES.Pelicula p : CLASES.BaseDeDatos.peliculasProximamente) {
                            if (p != null && p.getId() == peliculaActual.getId()) {
                                esPreventa = true;
                                break;
                            }
                        }

                        // 3. Lógica según el tipo de película
                        if (esPreventa) {
                            // Como es una película en preventa, simulamos una fila de espera
                            // virtual: el primero que entra es el primero en comprar (FIFO),
                            // por eso usamos una Cola en vez de una simple lista.
                            java.util.Queue<String> colaEspera = new java.util.LinkedList<>();
                            colaEspera.offer("Invitado_592");
                            colaEspera.offer("Invitado_104");
                            colaEspera.offer("Invitado_888");
                            colaEspera.offer("Tú (Cliente Actual)");

                            // 1. INSTANCIAMOS TU NUEVA PANTALLA (Reemplaza el JDialog temporal)
                            interfaces.ColaVirtual dialogoCola = new interfaces.ColaVirtual(framePrincipal, true);
                            
                            // 2. Le pasamos los datos de la peli usando el método que creamos
                            dialogoCola.configurarDatos(peliculaActual.getTitulo(), diaSeleccionado + " - " + hora + " | Sala 01");
                            dialogoCola.actualizarTurno(colaEspera.size() - 1);

                             //Para Evitar salida de ultimo momento en la cola usamos un arreglo de un solo elemento 
                             //Para poder acceder al timer desde adentro y afuera
                            final javax.swing.Timer[] timerCierreRef = {null};
                            
                            // 3. El Temporizador que saca gente de la cola
                            javax.swing.Timer timer = new javax.swing.Timer(3000, new java.awt.event.ActionListener() {
                                @Override
                                public void actionPerformed(java.awt.event.ActionEvent e) {
                                    colaEspera.poll(); // ELIMINA AL PRIMERO DE LA COLA
                                    
                                    if (colaEspera.size() > 1) {
                                        dialogoCola.actualizarTurno(colaEspera.size() - 1);
                                    } else {
                                        // ¡ES TU TURNO!
                                        ((javax.swing.Timer)e.getSource()).stop(); 
                                        dialogoCola.mostrarEsTuTurno();
                                        
                                        //Este es el temporizador que mantiene la pantalla cuando sale el mensaje de ES TU TURNO
                                        //Guardamos el segundo timer en nuestra referencia
                                        timerCierreRef[0] = new javax.swing.Timer(3000, evtCierre -> {
                                            dialogoCola.dispose();
                                            
                                            Asientos nuevoPanel = new Asientos(peliculaActual, hora, diaSeleccionado);
                                            nuevoPanel.setSize(framePrincipal.getSize()); 
                                            CLASES.Navegador.irA(framePrincipal, nuevoPanel);
                                        });
                                        timerCierreRef[0].setRepeats(false); 
                                        timerCierreRef[0].start();
                                    }
                                }
                            });
                            
                            // Permite parar el temporizador si salimos de la ventana
                            dialogoCola.addWindowListener(new java.awt.event.WindowAdapter() {
                                @Override
                                public void windowClosed(java.awt.event.WindowEvent e) {
                                    // Usamos windowClosed en vez de windowClosing ya que no existe la barra nativa sino un lbl que simula la X
                                    timer.stop(); // ¡Esto mata al contador de fondo!
                                    
                                    // Si el segundo timer ya había arrancado, ¡también lo paramos!
                                    if (timerCierreRef[0] != null) {
                                        timerCierreRef[0].stop();
                                    }
                                }
                            });

                            timer.start();
                            dialogoCola.setLocationRelativeTo(framePrincipal); 
                            dialogoCola.setVisible(true); // Muestra tu nueva pantalla bloqueando el fondo
                            
                        } else {
                            // === ES CARTELERA NORMAL: PASA DIRECTO SIN COLA ===
                            Asientos nuevoPanel = new Asientos(peliculaActual, hora, diaSeleccionado);
                            nuevoPanel.setSize(framePrincipal.getSize()); 
                            CLASES.Navegador.irA(framePrincipal, nuevoPanel);
                        }
                    }
                }
            });
//################################################################
            btnComprar.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    btnComprar.setBackground(new java.awt.Color(153,0,0)); 
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    btnComprar.setBackground(new java.awt.Color(204,0,0)); 
                }
            });

            tarjeta.add(lblHora);
            tarjeta.add(btnComprar);
            panelContenedorHorarios.add(tarjeta);
        }

        panelContenedorHorarios.revalidate();
        panelContenedorHorarios.repaint();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel9 = new javax.swing.JPanel();
        btnPasado = new javax.swing.JButton();
        btnMañana = new javax.swing.JButton();
        btnHOY = new javax.swing.JButton();
        Clasificacion = new javax.swing.JLabel();
        Duracion = new javax.swing.JLabel();
        FOTO = new javax.swing.JLabel();
        sintaxix = new javax.swing.JLabel();
        NombrePelicula = new javax.swing.JLabel();
        btnVolver = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        lblAviso = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        fondoPanelContenedorHorarios = new javax.swing.JPanel();
        panelContenedorHorarios = new javax.swing.JPanel();

        setBackground(new java.awt.Color(12, 30, 48));
        setAutoscrolls(true);
        setMaximumSize(new java.awt.Dimension(1366, 760));
        setMinimumSize(new java.awt.Dimension(1366, 760));
        setLayout(new java.awt.BorderLayout());

        jPanel9.setBackground(new java.awt.Color(0, 9, 39));
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnPasado.setBackground(new java.awt.Color(0, 9, 80));
        btnPasado.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        btnPasado.setForeground(new java.awt.Color(255, 255, 255));
        btnPasado.setText("JUEVES");
        btnPasado.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnPasado.addActionListener(this::btnPasadoActionPerformed);
        jPanel9.add(btnPasado, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 400, 150, -1));

        btnMañana.setBackground(new java.awt.Color(0, 9, 80));
        btnMañana.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        btnMañana.setForeground(new java.awt.Color(255, 255, 255));
        btnMañana.setText("MAÑANA");
        btnMañana.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnMañana.addActionListener(this::btnMañanaActionPerformed);
        jPanel9.add(btnMañana, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 400, 170, -1));

        btnHOY.setBackground(new java.awt.Color(0, 9, 80));
        btnHOY.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        btnHOY.setForeground(new java.awt.Color(255, 255, 255));
        btnHOY.setText("HOY");
        btnHOY.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnHOY.addActionListener(this::btnHOYActionPerformed);
        jPanel9.add(btnHOY, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 400, 170, -1));

        Clasificacion.setFont(new java.awt.Font("Segoe UI", 0, 30)); // NOI18N
        Clasificacion.setForeground(new java.awt.Color(255, 255, 255));
        Clasificacion.setText("clasificacion");
        jPanel9.add(Clasificacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 80, 850, 50));

        Duracion.setFont(new java.awt.Font("Segoe UI", 0, 30)); // NOI18N
        Duracion.setForeground(new java.awt.Color(255, 255, 255));
        Duracion.setText("hora");
        Duracion.setToolTipText("");
        jPanel9.add(Duracion, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 80, 230, 50));

        FOTO.setBackground(new java.awt.Color(250, 204, 21));
        FOTO.setToolTipText("");
        FOTO.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(250, 204, 21)));
        jPanel9.add(FOTO, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 240, 360));

        sintaxix.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        sintaxix.setForeground(new java.awt.Color(255, 255, 255));
        sintaxix.setText("descripcion");
        sintaxix.setToolTipText("");
        jPanel9.add(sintaxix, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 130, 1050, 240));

        NombrePelicula.setFont(new java.awt.Font("Segoe UI", 0, 48)); // NOI18N
        NombrePelicula.setForeground(new java.awt.Color(255, 255, 255));
        NombrePelicula.setText("pelicula");
        NombrePelicula.setToolTipText("");
        jPanel9.add(NombrePelicula, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 10, 870, -1));

        btnVolver.setBackground(new java.awt.Color(0, 9, 80));
        btnVolver.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        btnVolver.setForeground(new java.awt.Color(255, 255, 255));
        btnVolver.setText("VOLVER");
        btnVolver.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnVolver.addActionListener(this::btnVolverActionPerformed);
        jPanel9.add(btnVolver, new org.netbeans.lib.awtextra.AbsoluteConstraints(1230, 20, -1, -1));

        jPanel10.setBackground(new java.awt.Color(0, 9, 39));
        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel9.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 440, 1370, 30));

        lblAviso.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lblAviso.setForeground(new java.awt.Color(255, 255, 255));
        lblAviso.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAviso.setText("ESTRENO PRÓXIMAMENTE - HORARIOS NO DISPONIBLES");
        jPanel9.add(lblAviso, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 390, 630, 50));

        add(jPanel9, java.awt.BorderLayout.NORTH);

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        fondoPanelContenedorHorarios.setBackground(new java.awt.Color(0, 0, 23));
        fondoPanelContenedorHorarios.setLayout(new java.awt.BorderLayout());

        panelContenedorHorarios.setBackground(new java.awt.Color(0, 0, 23));
        panelContenedorHorarios.setBorder(javax.swing.BorderFactory.createEmptyBorder(50, 50, 50, 50));
        panelContenedorHorarios.setLayout(new java.awt.GridLayout(0, 5, 50, 50));
        fondoPanelContenedorHorarios.add(panelContenedorHorarios, java.awt.BorderLayout.NORTH);

        jScrollPane1.setViewportView(fondoPanelContenedorHorarios);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        // Recuperamos del historial la pantalla de Cartelera desde la que llegamos,
        // en vez de crear una Cartelera nueva y perder en qué estado estaba.
        javax.swing.JFrame framePrincipal = (javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this);
        if (CLASES.Navegador.hayHistorial()) {
            CLASES.Navegador.regresar(framePrincipal);
        } else {
            // Respaldo por si no hay historial guardado (por ejemplo, se entró directo a esta pantalla)
            Cartelera cartelera = new Cartelera();
            cartelera.setVisible(true);
            framePrincipal.dispose();
        }
    }//GEN-LAST:event_btnVolverActionPerformed

    private void btnPasadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPasadoActionPerformed
        diaSeleccionado = btnPasado.getText();
        if (peliculaActual != null && peliculaActual.getHorariosPorDia().length > 2) {
            cargarHorariosDinamicos(peliculaActual.getHorariosPorDia()[2]);
        }
    }//GEN-LAST:event_btnPasadoActionPerformed

    private void btnHOYActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHOYActionPerformed
        
         diaSeleccionado = btnHOY.getText();
        if (peliculaActual != null && peliculaActual.getHorariosPorDia().length > 0) {
            cargarHorariosDinamicos(peliculaActual.getHorariosPorDia()[0]);
        }
    }//GEN-LAST:event_btnHOYActionPerformed

    private void btnMañanaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMañanaActionPerformed
        diaSeleccionado = btnMañana.getText(); 
        if (peliculaActual != null && peliculaActual.getHorariosPorDia().length > 1) {
            cargarHorariosDinamicos(peliculaActual.getHorariosPorDia()[1]);
        }
    }//GEN-LAST:event_btnMañanaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Clasificacion;
    private javax.swing.JLabel Duracion;
    private javax.swing.JLabel FOTO;
    private javax.swing.JLabel NombrePelicula;
    private javax.swing.JButton btnHOY;
    private javax.swing.JButton btnMañana;
    private javax.swing.JButton btnPasado;
    private javax.swing.JButton btnVolver;
    private javax.swing.JPanel fondoPanelContenedorHorarios;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAviso;
    private javax.swing.JPanel panelContenedorHorarios;
    private javax.swing.JLabel sintaxix;
    // End of variables declaration//GEN-END:variables
}
