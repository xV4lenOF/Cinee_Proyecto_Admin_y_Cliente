package interfaces;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.util.regex.Pattern;
import java.io.IOException;
import java.util.List;

/**
 * Pantalla de pago: último paso antes de confirmar la compra.
 * Arma el formulario dinámicamente en Java (sin diseñador de NetBeans) porque
 * los campos que se muestran cambian según lo que elige el usuario:
 * DNI/RUC/C.E. para sus datos, y Tarjeta/Yape para el método de pago.
 * Al presionar "PAGAR AHORA" valida todos los campos y, si todo está bien,
 * genera la Venta, el Ticket y el comprobante en PDF.
 */
public class Pago extends javax.swing.JPanel {

    // Esquema de colores para el modo oscuro
    private final Color COLOR_FONDO = new Color(5, 10, 26);
    private final Color COLOR_TARJETA = new Color(10, 17, 40);
    private final Color COLOR_COMPONENTE_BG = new Color(17, 24, 52);
    private final Color COLOR_TEXTO_PLACEHOLDER = new Color(100, 116, 139);
    private final Color COLOR_TEXTO_NORMAL = new Color(241, 245, 249);
    private final Color COLOR_AZUL_BOTON = new Color(13, 110, 253);
    private final Color COLOR_AZUL_LINEA = new Color(30, 64, 175);
    
    // Pantalla de Dulcería de la que venimos (respaldo si no hay historial de Navegador)
    private Dulceria panelDulceriaAnterior;

    // Componentes del formulario base (siempre visibles, sin importar lo que elija el usuario)
    private JComboBox<String> cbTipoDocumento;
    private JTextField txtNumDocumento, txtCorreo;
    private JRadioButton rbtnTarjeta, rbtnYape;
    private JButton btnBoleta, btnFactura, btnPagar;
    private ButtonGroup grupoPago;

    // Contenedores dinámicos: su contenido se reemplaza en tiempo real según
    // el tipo de documento o el método de pago que el usuario vaya eligiendo
    private JPanel panelDinamicoDatos;
    private JPanel panelDinamicoPago;

    // Campos alternables: Persona Natural (DNI / C.E.)
    private JTextField txtNombre, txtApellido;

    // Campos alternables: Persona Jurídica (RUC)
    private JTextField txtRuc, txtRazonSocial, txtDireccion, txtTelefono;

    // Campos alternables: Pasarela Tarjeta
    private JTextField txtNumTarjeta, txtVencimiento, txtCvv, txtNombreTarjeta;

    // Campos alternables: Pasarela Yape
    private JTextField txtIngresaYape, txtNumSeguridadYape;

    // Recibe la pantalla de Dulcería anterior y con eso arma todo el formulario
    public Pago(Dulceria anterior) {
        this.panelDulceriaAnterior = anterior; 
        construirInterfazDinamica(); 
    }

// Arma toda la tarjeta de pago: documento, correo, método de pago y botones.
// Se construye por código (no con el editor visual de NetBeans) porque el
// contenido cambia según lo que el usuario vaya seleccionando.
private void construirInterfazDinamica() {
        setBackground(COLOR_FONDO);
        setLayout(new GridBagLayout()); // Centra la tarjeta en resoluciones altas (1600x900)
        setBorder(new EmptyBorder(20, 20, 20, 20));

        // ====================================================================
        // CONTENEDOR CENTRAL: TARJETA DE ANCHO CONTROLADO (650px)
        // ====================================================================
        JPanel tarjetaCentral = new JPanel(new GridBagLayout());
        tarjetaCentral.setBackground(COLOR_TARJETA);
        tarjetaCentral.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(COLOR_AZUL_LINEA, 1, true),
                new EmptyBorder(25, 35, 25, 35)
        ));
        tarjetaCentral.setPreferredSize(new Dimension(650, 720)); // Altura optimizada sin los botones
        tarjetaCentral.setMinimumSize(new Dimension(600, 660));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(6, 0, 6, 0);
        gbc.weightx = 1.0;

        // ====================================================================
        // SECCIÓN 1: COMPLETA TUS DATOS
        // ====================================================================
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        tarjetaCentral.add(crearEtiquetaSeccion("I  COMPLETA TUS DATOS"), gbc);

        // Fila selectora de documento
        gbc.gridy = 1;
        JPanel panelDocBase = new JPanel(new BorderLayout(10, 0));
        panelDocBase.setOpaque(false);

        cbTipoDocumento = new JComboBox<>(new String[]{"DNI", "RUC", "C.E."});
        cbTipoDocumento.setBackground(COLOR_AZUL_BOTON);
        cbTipoDocumento.setForeground(COLOR_TEXTO_NORMAL);
        cbTipoDocumento.setPreferredSize(new Dimension(95, 42));

        txtNumDocumento = crearTextFieldEstilizado("Número de documento");
        configurarSoloNumeros(txtNumDocumento, 12);

        panelDocBase.add(cbTipoDocumento, BorderLayout.WEST);
        panelDocBase.add(txtNumDocumento, BorderLayout.CENTER);
        tarjetaCentral.add(panelDocBase, gbc);

        // --- CONTENEDOR DINÁMICO DE SUB-FORMULARIOS (DNI / RUC) ---
        gbc.gridy = 2;
        panelDinamicoDatos = new JPanel(new GridBagLayout());
        panelDinamicoDatos.setOpaque(false);
        tarjetaCentral.add(panelDinamicoDatos, gbc);

        // Instanciación preventiva de variables de formulario
        txtNombre = crearTextFieldEstilizado("Nombre");
        txtApellido = crearTextFieldEstilizado("Apellido");
        txtRuc = crearTextFieldEstilizado("Número de RUC");
        configurarSoloNumeros(txtRuc, 11);
        txtRazonSocial = crearTextFieldEstilizado("Razón Social");
        txtDireccion = crearTextFieldEstilizado("Dirección");
        txtTelefono = crearTextFieldEstilizado("Teléfono");
        configurarSoloNumeros(txtTelefono, 9);

        // Listener único: Ahora todo el flujo de datos depende exclusivamente del ComboBox
        cbTipoDocumento.addActionListener(e -> {
            actualizarFormularioDatos(cbTipoDocumento.getSelectedItem().toString());
        });

        // Campo obligatorio: Correo
        gbc.gridy = 3;
        txtCorreo = crearTextFieldEstilizado("Correo electrónico");
        tarjetaCentral.add(txtCorreo, gbc);

        // ====================================================================
        // SECCIÓN 2: ¿CÓMO DESEA REALIZAR SU PAGO?
        // ====================================================================
        gbc.gridy = 4;
        tarjetaCentral.add(crearEtiquetaSeccion("I  ¿CÓMO DESEA REALIZAR SU PAGO?"), gbc);

        gbc.gridy = 5;
        rbtnTarjeta = new JRadioButton("Pago con tarjeta de débito/crédito", true);
        estilarRadioButton(rbtnTarjeta);
        tarjetaCentral.add(rbtnTarjeta, gbc);

        gbc.gridy = 6;
        rbtnYape = new JRadioButton("Pago con Yape en línea", false);
        estilarRadioButton(rbtnYape);
        tarjetaCentral.add(rbtnYape, gbc);

        grupoPago = new ButtonGroup();
        grupoPago.add(rbtnTarjeta);
        grupoPago.add(rbtnYape);

        // --- CONTENEDOR DINÁMICO DE PASARELAS (Tarjeta / Yape) ---
        gbc.gridy = 7;
        panelDinamicoPago = new JPanel(new GridBagLayout());
        panelDinamicoPago.setOpaque(false);
        tarjetaCentral.add(panelDinamicoPago, gbc);

        // Instanciación de inputs de Tarjeta
        txtNumTarjeta = crearTextFieldEstilizado("Número de tarjeta");
        configurarSoloNumeros(txtNumTarjeta, 16);
        txtVencimiento = crearTextFieldEstilizado("Vencimiento MM/AA");
        configurarSoloNumerosYFormatoFecha(txtVencimiento);
        txtCvv = crearTextFieldEstilizado("CVV");
        configurarSoloNumeros(txtCvv, 3);
        txtNombreTarjeta = crearTextFieldEstilizado("Nombre completo en tarjeta");

        // Instanciación de inputs de Yape
        txtIngresaYape = crearTextFieldEstilizado("Ingresa Yape (Número de celular)");
        configurarSoloNumeros(txtIngresaYape, 9);
        txtNumSeguridadYape = crearTextFieldEstilizado("Número de seguridad (Código de aprobación de 6 dígitos)");
        configurarSoloNumeros(txtNumSeguridadYape, 6);

        // Listeners para alternar la pasarela de pago activa
        rbtnTarjeta.addActionListener(e -> actualizarPasarelaPago(true));
        rbtnYape.addActionListener(e -> actualizarPasarelaPago(false));

        // Inicializar vistas por defecto (DNI y Tarjeta)
        actualizarFormularioDatos("DNI");
        actualizarPasarelaPago(true);
        // ====================================================================
        // ACCIÓN: BOTÓN RETROCESO + DISPARADOR DEL FLUJO DE VALIDACIONES (LADO A LADO)
        // ====================================================================
        gbc.gridy = 8;
        gbc.gridwidth = 1;
        gbc.weightx = 0.5;
        gbc.insets = new Insets(12, 0, 0, 4);
        JButton btnRetroceso = new JButton("← Volver");
        btnRetroceso.setBackground(COLOR_COMPONENTE_BG);
        btnRetroceso.setForeground(COLOR_TEXTO_NORMAL);
        btnRetroceso.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnRetroceso.setPreferredSize(new Dimension(0, 48));
        btnRetroceso.setFocusPainted(false);
        btnRetroceso.setBorder(BorderFactory.createEmptyBorder());
        btnRetroceso.addActionListener(e -> btnRetrocesoActionPerformed());
        gbc.gridx = 0;
        tarjetaCentral.add(btnRetroceso, gbc);

        gbc.insets = new Insets(12, 4, 0, 0);
        btnPagar = new JButton("PAGAR AHORA");
        btnPagar.setBackground(COLOR_AZUL_BOTON);
        btnPagar.setForeground(COLOR_TEXTO_NORMAL);
        btnPagar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnPagar.setPreferredSize(new Dimension(0, 48));
        btnPagar.setFocusPainted(false);
        btnPagar.setBorder(BorderFactory.createEmptyBorder());
        btnPagar.addActionListener(e -> ejecutarValidacionDePago());
        gbc.gridx = 1;
        tarjetaCentral.add(btnPagar, gbc);

        gbc.gridwidth = 2;
        gbc.weightx = 1.0;

        // Montamos la tarjeta estructurada dentro de la pantalla completa
        add(tarjetaCentral);
    }

    // ====================================================================
    // INTERCAMBIO EN TIEMPO DE EJECUCIÓN (REVALIDACIONES DE LAYOUT)
    // Se llama cada vez que el usuario cambia el ComboBox de tipo de
    // documento: vacía el panel dinámico de datos y vuelve a llenarlo con
    // los campos de RUC (empresa) o de DNI/C.E. (persona), según corresponda.
    // ====================================================================
    private void actualizarFormularioDatos(String tipoDoc) {
        panelDinamicoDatos.removeAll();
        GridBagConstraints subGbc = new GridBagConstraints();
        subGbc.fill = GridBagConstraints.HORIZONTAL;
        subGbc.weightx = 1.0;
        subGbc.insets = new Insets(4, 0, 4, 0);

        if (tipoDoc.equals("RUC")) {
            subGbc.gridx = 0; subGbc.gridy = 0;
            panelDinamicoDatos.add(txtRuc, subGbc);
            subGbc.gridy = 1;
            panelDinamicoDatos.add(txtRazonSocial, subGbc);
            subGbc.gridy = 2;
            panelDinamicoDatos.add(txtDireccion, subGbc);
            subGbc.gridy = 3;
            panelDinamicoDatos.add(txtTelefono, subGbc);
        } else {
            subGbc.gridy = 0; subGbc.gridx = 0; subGbc.weightx = 0.5;
            subGbc.insets = new Insets(4, 0, 4, 4);
            panelDinamicoDatos.add(txtNombre, subGbc);
            
            subGbc.gridx = 1;
            subGbc.insets = new Insets(4, 4, 4, 0);
            panelDinamicoDatos.add(txtApellido, subGbc);
        }
        panelDinamicoDatos.revalidate();
        panelDinamicoDatos.repaint();
    }

// Se llama cada vez que el usuario cambia el radio button de método de pago:
// vacía el panel dinámico de pago y lo vuelve a llenar con los campos de
// Tarjeta (número, vencimiento, CVV, nombre) o de Yape (celular, código).
private void actualizarPasarelaPago(boolean mostrarTarjeta) {
        panelDinamicoPago.removeAll();
        GridBagConstraints subGbc = new GridBagConstraints();
        subGbc.fill = GridBagConstraints.HORIZONTAL;
        subGbc.weightx = 1.0;
        subGbc.insets = new Insets(4, 0, 4, 0);

        if (mostrarTarjeta) {
            subGbc.gridx = 0; subGbc.gridy = 0; subGbc.gridwidth = 2;
            panelDinamicoPago.add(txtNumTarjeta, subGbc);
            
            subGbc.gridy = 1; subGbc.gridwidth = 1; subGbc.weightx = 0.5;
            subGbc.insets = new Insets(4, 0, 4, 4);
            panelDinamicoPago.add(txtVencimiento, subGbc);
            
            subGbc.gridx = 1;
            subGbc.insets = new Insets(4, 4, 4, 0);
            panelDinamicoPago.add(txtCvv, subGbc);
            
            // CORREGIDO AQUÍ: Se cambió gbc.gridy por subGbc.gridy
            subGbc.gridx = 0; subGbc.gridy = 2; subGbc.gridwidth = 2; subGbc.weightx = 1.0;
            subGbc.insets = new Insets(4, 0, 4, 0);
            panelDinamicoPago.add(txtNombreTarjeta, subGbc);
        } else {
            subGbc.gridx = 0; subGbc.gridy = 0;
            panelDinamicoPago.add(txtIngresaYape, subGbc);
            subGbc.gridy = 1;
            panelDinamicoPago.add(txtNumSeguridadYape, subGbc);
        }
        panelDinamicoPago.revalidate();
        panelDinamicoPago.repaint();
    }

    // ====================================================================
    // LÓGICA DE CONTROL Y VALIDACIONES REQUERIDAS
    // Antes de dejar pasar la compra, revisamos TODOS los campos del
    // formulario: primero que ninguno esté vacío, y luego que el contenido
    // de cada uno tenga un formato válido (largo correcto, solo números
    // donde corresponde, solo letras donde corresponde, etc.).
    // Se corta en el primer error encontrado (return) y se le avisa al
    // usuario con una alerta específica de qué falta corregir.
    // ====================================================================
    private void ejecutarValidacionDePago() {

        // --- BLOQUE 1: Documento y correo (obligatorios para cualquier tipo de cliente) ---
        if (estaVacio(txtNumDocumento, "Número de documento") || estaVacio(txtCorreo, "Correo electrónico")) {
            mostrarAlerta("Por favor, complete la documentación base y el correo.");
            return;
        }

        // El número de documento debe tener el largo correcto según el tipo elegido en el combo
        String tipoDoc = cbTipoDocumento.getSelectedItem().toString();
        String numDoc = txtNumDocumento.getText().trim();
        if (tipoDoc.equals("DNI") && numDoc.length() != 8) {
            mostrarAlerta("El DNI debe tener exactamente 8 dígitos.");
            txtNumDocumento.requestFocus();
            return;
        }
        if (tipoDoc.equals("C.E.") && numDoc.length() < 9) {
            mostrarAlerta("El Carné de Extranjería debe tener al menos 9 dígitos.");
            txtNumDocumento.requestFocus();
            return;
        }

        // El correo debe cumplir un formato básico "algo@algo"
        String email = txtCorreo.getText().trim();
        String regexEmail = "^[A-Za-z0-9+_.-]+@(.+)$";
        if (!Pattern.compile(regexEmail).matcher(email).matches()) {
            mostrarAlerta("El correo electrónico ingresado no cuenta con un formato válido (ejemplo@dominio.com).");
            txtCorreo.requestFocus();
            return;
        }

        // --- BLOQUE 2: Datos personales o de empresa, según el tipo de documento elegido ---
        if (tipoDoc.equals("RUC")) {
            // Persona jurídica: se piden los 4 campos de la empresa
            if (estaVacio(txtRuc, "Número de RUC") || estaVacio(txtRazonSocial, "Razón Social") ||
                estaVacio(txtDireccion, "Dirección") || estaVacio(txtTelefono, "Teléfono")) {
                mostrarAlerta("Complete los 4 campos del RUC corporativo requeridos.");
                return;
            }
            if (txtRuc.getText().trim().length() < 11) {
                mostrarAlerta("El número de RUC debe constar de 11 dígitos.");
                txtRuc.requestFocus();
                return;
            }
            if (txtRazonSocial.getText().trim().length() < 3) {
                mostrarAlerta("La Razón Social debe tener al menos 3 caracteres.");
                txtRazonSocial.requestFocus();
                return;
            }
            if (txtDireccion.getText().trim().length() < 5) {
                mostrarAlerta("Ingrese una dirección válida (mínimo 5 caracteres).");
                txtDireccion.requestFocus();
                return;
            }
            if (txtTelefono.getText().trim().length() < 9) {
                mostrarAlerta("El número de teléfono debe tener 9 dígitos.");
                txtTelefono.requestFocus();
                return;
            }
        } else {
            // Persona natural: se piden nombre y apellido, y deben ser solo letras
            if (estaVacio(txtNombre, "Nombre") || estaVacio(txtApellido, "Apellido")) {
                mostrarAlerta("Por favor, complete sus nombres y apellidos.");
                return;
            }
            String regexSoloLetras = "^[A-Za-zÁÉÍÓÚáéíóúÑñ ]{2,}$";
            if (!Pattern.compile(regexSoloLetras).matcher(txtNombre.getText().trim()).matches()) {
                mostrarAlerta("El nombre solo debe contener letras (mínimo 2 caracteres).");
                txtNombre.requestFocus();
                return;
            }
            if (!Pattern.compile(regexSoloLetras).matcher(txtApellido.getText().trim()).matches()) {
                mostrarAlerta("El apellido solo debe contener letras (mínimo 2 caracteres).");
                txtApellido.requestFocus();
                return;
            }
        }

        // --- BLOQUE 3: Método de pago elegido (Tarjeta o Yape), con sus propios campos ---
        if (rbtnTarjeta.isSelected()) {
            // Pago con tarjeta: 4 campos obligatorios
            if (estaVacio(txtNumTarjeta, "Número de tarjeta") || estaVacio(txtVencimiento, "Vencimiento MM/AA") ||
                estaVacio(txtCvv, "CVV") || estaVacio(txtNombreTarjeta, "Nombre completo en tarjeta")) {
                mostrarAlerta("Por favor, llene todos los campos de su tarjeta bancaria.");
                return;
            }
            if (txtNumTarjeta.getText().trim().length() < 16) {
                mostrarAlerta("El número de tarjeta debe tener 16 dígitos.");
                txtNumTarjeta.requestFocus();
                return;
            }
            // El vencimiento debe venir completo como MM/AA (5 caracteres) y con un mes válido (01-12)
            String vencimiento = txtVencimiento.getText().trim();
            if (!Pattern.compile("^(0[1-9]|1[0-2])/\\d{2}$").matcher(vencimiento).matches()) {
                mostrarAlerta("La fecha de vencimiento debe tener el formato MM/AA con un mes válido (01-12).");
                txtVencimiento.requestFocus();
                return;
            }
            if (txtCvv.getText().trim().length() < 3) {
                mostrarAlerta("El CVV debe tener 3 dígitos.");
                txtCvv.requestFocus();
                return;
            }
            String regexSoloLetras = "^[A-Za-zÁÉÍÓÚáéíóúÑñ ]{2,}$";
            if (!Pattern.compile(regexSoloLetras).matcher(txtNombreTarjeta.getText().trim()).matches()) {
                mostrarAlerta("El nombre en la tarjeta solo debe contener letras (mínimo 2 caracteres).");
                txtNombreTarjeta.requestFocus();
                return;
            }
        } else {
            // Pago con Yape: 2 campos obligatorios
            if (estaVacio(txtIngresaYape, "Ingresa Yape (Número de celular)") ||
                estaVacio(txtNumSeguridadYape, "Número de seguridad (Código de aprobación de 6 dígitos)")) {
                mostrarAlerta("Complete las casillas de autenticación de Yape.");
                return;
            }
            if (txtIngresaYape.getText().trim().length() < 9) {
                mostrarAlerta("El número de celular de Yape debe tener 9 dígitos.");
                txtIngresaYape.requestFocus();
                return;
            }
            if (txtNumSeguridadYape.getText().trim().length() < 6) {
                mostrarAlerta("El código de aprobación de Yape debe tener 6 dígitos.");
                txtNumSeguridadYape.requestFocus();
                return;
            }
        }

        // Si llegamos hasta aquí, todos los campos son válidos: registramos la venta.
        procesarCompraFinal();
    }
private void btnRetrocesoActionPerformed() {
        java.awt.Window ventana = javax.swing.SwingUtilities.getWindowAncestor(this);
        if (ventana instanceof javax.swing.JFrame) {
            javax.swing.JFrame framePrincipal = (javax.swing.JFrame) ventana;

            // Recuperamos del historial la pantalla de Dulcería desde la que llegamos,
            // con el carrito que ya tenía armado.
            if (CLASES.Navegador.hayHistorial()) {
                CLASES.Navegador.regresar(framePrincipal);
            } else if (panelDulceriaAnterior != null) {
                // Respaldo por si no hay historial guardado
                framePrincipal.setContentPane(panelDulceriaAnterior);
                framePrincipal.revalidate();
                framePrincipal.repaint();
            }
        }
    }

    // ====================================================================
    // AQUÍ SE CONECTAN LAS CLASES DE NEGOCIO (CLASES/*) CON LA INTERFAZ
    // Solo se llega aquí si ejecutarValidacionDePago() ya confirmó que todos
    // los campos son válidos. Este método arma, en orden, el Cliente, el
    // método de pago, la Venta con sus productos, el Ticket y el PDF final;
    // y al terminar reinicia la sesión y regresa a la Cartelera.
    // ====================================================================
    private void procesarCompraFinal() {
        try {
            // 1. Armamos el Cliente con los datos del formulario
            String documento = txtNumDocumento.getText().trim();
            String nombreCompleto;
            if (cbTipoDocumento.getSelectedItem().toString().equals("RUC")) {
                nombreCompleto = txtRazonSocial.getText().trim();
            } else {
                nombreCompleto = txtNombre.getText().trim() + " " + txtApellido.getText().trim();
            }
            String correo = txtCorreo.getText().trim();
            String telefono = (txtTelefono != null && !estaVacio(txtTelefono, "Teléfono"))
                    ? txtTelefono.getText().trim() : "";

            CLASES.Cliente cliente = new CLASES.Cliente(documento, nombreCompleto, correo, telefono);

            // 2. Guardamos el cliente en la sesión global y lo enlazamos a la preventa (asientos/función)
            CLASES.SesionActual.clienteLogueado = cliente;
            if (CLASES.SesionActual.preventaEnCurso != null) {
                CLASES.SesionActual.preventaEnCurso.setCliente(cliente);
                CLASES.SesionActual.preventaEnCurso.setEstado("Confirmada");
            }

            // 3. Elegimos el método de pago según el radio button seleccionado y lo procesamos
            CLASES.MetodoPago metodoPago;
            if (rbtnTarjeta.isSelected()) {
                metodoPago = new CLASES.Tarjeta(txtNumTarjeta.getText().trim(), "Tarjeta");
            } else {
                metodoPago = new CLASES.Yape();
            }

            // 4. Creamos la Venta y le sumamos: entradas (Boletos) + dulcería (SesionActual)
           CLASES.GeneradorPDF numerador = new CLASES.GeneradorPDF();
            String ventaID = numerador.generarCorrelativo("V");
            CLASES.Venta venta = new CLASES.Venta(ventaID, CLASES.SesionActual.preventaEnCurso);

            if (interfaces.Boletos.instanciaActual != null) {
                List<CLASES.Detalle_Venta> detallesEntradas = interfaces.Boletos.instanciaActual.obtenerDetallesEntradas();
                for (CLASES.Detalle_Venta dv : detallesEntradas) {
                    venta.agregarDetalle(dv);
                }
            }
            for (CLASES.Detalle_Venta dv : CLASES.SesionActual.carritoDulceria) {
                venta.agregarDetalle(dv);
            }

            // 5. Procesamos el cobro (imprime en consola cuánto se cobró y por qué medio)
            metodoPago.procesarPago(venta.getTotal());

            // 6. Generamos el ticket y persistimos toda la compra en la base compartida.
            // La escritura usa una transacción; si falla, no confirmamos las butacas en memoria.
            String ticketID = "T-" + ventaID.substring(2);
            String codigoQR = "QR-" + ventaID;
            CLASES.Ticket ticket = new CLASES.Ticket(ticketID, codigoQR, venta);
            String metodoPagoTexto = rbtnTarjeta.isSelected() ? "Tarjeta" : "Yape";
            CLASES.PersistenciaVentasMySQL.guardarVenta(venta, cliente,
                    cbTipoDocumento.getSelectedItem().toString(), metodoPagoTexto, ticket);

            // 7. Bloqueamos las butacas recién compradas también en la sesión actual.
            CLASES.Preventa preventaConfirmada = CLASES.SesionActual.preventaEnCurso;
            if (preventaConfirmada != null && preventaConfirmada.getFuncion() != null
                    && preventaConfirmada.getFuncion().getPelicula() != null) {
                CLASES.Funcion funcionConfirmada = preventaConfirmada.getFuncion();
                String claveFuncion = CLASES.BaseDeDatos.claveFuncion(
                        funcionConfirmada.getPelicula().getId(),
                        funcionConfirmada.getFecha(),
                        funcionConfirmada.getHora());

                List<String> codigosAsientos = new java.util.ArrayList<>();
                for (CLASES.Asiento a : preventaConfirmada.getAsientos()) {
                    codigosAsientos.add(a.getNumero());
                }
                CLASES.BaseDeDatos.marcarAsientosOcupados(claveFuncion, codigosAsientos);
            }

            // 8. Generamos el comprobante en PDF dentro de Escritorio/venta/
            String rutaPdf = generarComprobantePdf(cliente, venta, ticket);

            JOptionPane.showMessageDialog(this,
                    "¡Compra procesada correctamente!\n\nSe generó tu comprobante en:\n" + rutaPdf,
                    "Transacción Exitosa", JOptionPane.INFORMATION_MESSAGE);

            // 9. Limpiamos la sesión para dejar todo listo para una compra nueva
            CLASES.SesionActual.reiniciarSesion();

            // 10. Volvemos al inicio (Cartelera)
            java.awt.Window ventana = javax.swing.SwingUtilities.getWindowAncestor(this);
            if (ventana != null) {
                ventana.dispose();
            }
            java.awt.EventQueue.invokeLater(() -> new interfaces.Cartelera().setVisible(true));

        } catch (java.sql.SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "No se pudo guardar la compra en la base de datos compartida.\n\n"
                    + "Verifica que MySQL esté encendido, que hayas importado sql/cinee_schema.sql y que la conexión esté configurada.\n\n"
                    + ex.getMessage(),
                    "Error de base de datos", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this,
                    "La compra se guardó, pero no se pudo generar el comprobante PDF:\n" + ex.getMessage(),
                    "Error al generar comprobante", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Arma línea por línea el texto del comprobante (datos del cliente, función,
    // butacas, detalle de productos y total), le agrega el código QR como imagen,
    // y delega a CLASES.GeneradorPDF la escritura del archivo PDF final en disco.
    private String generarComprobantePdf(CLASES.Cliente cliente, CLASES.Venta venta,
            CLASES.Ticket ticket) throws IOException {

        CLASES.GeneradorPDF pdf = new CLASES.GeneradorPDF();
        pdf.agregarLinea("CINE - COMPROBANTE DE VENTA");
        pdf.agregarLinea("Fecha: " + new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(new java.util.Date()));
        pdf.agregarLineaVacia();

        pdf.agregarLinea("Cliente: " + cliente.getNombre());
        pdf.agregarLinea("Documento: " + cbTipoDocumento.getSelectedItem().toString() + " " + txtNumDocumento.getText().trim());
        pdf.agregarLinea("Correo: " + cliente.getEmail());
        pdf.agregarLineaVacia();

        CLASES.Preventa preventa = CLASES.SesionActual.preventaEnCurso;
        if (preventa != null && preventa.getFuncion() != null) {
            CLASES.Funcion funcion = preventa.getFuncion();
            pdf.agregarLinea("Pelicula: " + (funcion.getPelicula() != null ? funcion.getPelicula().getTitulo() : "-"));
            pdf.agregarLinea("Funcion: " + funcion.getFecha() + " - " + funcion.getHora()
                    + (funcion.getSala() != null ? " | Sala " + funcion.getSala().getNumero() : ""));

            StringBuilder butacas = new StringBuilder();
            for (CLASES.Asiento a : preventa.getAsientos()) {
                if (butacas.length() > 0) butacas.append(", ");
                butacas.append(a.getNumero());
            }
            pdf.agregarLinea("Butacas: " + (butacas.length() > 0 ? butacas.toString() : "-"));
            pdf.agregarLineaVacia();
        }

        pdf.agregarLinea("--- DETALLE DE LA COMPRA ---");
        for (CLASES.Detalle_Venta dv : venta.getDetalles()) {
            pdf.agregarLinea(String.format("%-30s x%-3d S/%.2f",
                    dv.getProducto().getNombre(), dv.getCantidad(), dv.getSubTotal()));
        }
        pdf.agregarLineaVacia();

        pdf.agregarLinea(String.format("TOTAL A PAGAR: S/%.2f", venta.getTotal()));
        pdf.agregarLinea("Metodo de pago: " + (rbtnTarjeta.isSelected() ? "Tarjeta" : "Yape"));
        pdf.agregarLineaVacia();
        pdf.agregarLinea("N° Venta: " + venta.getVentaID());
        pdf.agregarLinea("N° Ticket: " + ticket.getTicketID() + "  |  QR: " + ticket.getCodigoQR());
        // Busca tu archivo qr.png dentro del proyecto y lo agrega al PDF, tamaño 80mm x 80mm
try (java.io.InputStream isQr = getClass().getResourceAsStream("/imagenes/fondo/qr.png")) {
    if (isQr != null) {
        pdf.agregarImagenQR(isQr, 80, 80);
    }
}
        pdf.agregarLinea("Gracias por su compra. ¡Disfrute la funcion!");

        return pdf.generarArchivo("Venta_" + venta.getVentaID());
    }

    // Considera "vacío" tanto un campo realmente sin texto como uno que
    // todavía muestra el placeholder gris (el usuario nunca escribió nada)
    private boolean estaVacio(JTextField tf, String placeholder) {
        return tf.getText().trim().isEmpty() || tf.getText().equals(placeholder);
    }

    // Muestra una ventana de advertencia con el mensaje de qué falta corregir
    private void mostrarAlerta(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Falta Información", JOptionPane.WARNING_MESSAGE);
    }

    // ====================================================================
    // RESTRICTOR DE TECLAS (SOLO NÚMEROS)
    // Bloquea cualquier tecla que no sea un dígito, y deja de aceptar
    // caracteres una vez que el campo llega a su límite máximo de largo.
    // ====================================================================
    private void configurarSoloNumeros(JTextField tf, int limiteMax) {
        tf.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) || tf.getText().length() >= limiteMax) {
                    e.consume();
                }
            }
        });
    }

    // Igual que configurarSoloNumeros, pero además inserta automáticamente
    // el "/" después de los primeros 2 dígitos para formar MM/AA, y corta
    // la escritura una vez que el campo llega a 5 caracteres (ej. "12/27")
    private void configurarSoloNumerosYFormatoFecha(JTextField tf) {
        tf.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) e.consume();
                if (Character.isDigit(c) && tf.getText().length() == 2) {
                    tf.setText(tf.getText() + "/");
                }
                if (tf.getText().length() >= 5) e.consume();
            }
        });
    }

    // Crea el rótulo en negrita que encabeza cada sección del formulario
    // (ej. "COMPLETA TUS DATOS", "¿CÓMO DESEA REALIZAR SU PAGO?")
    private JLabel crearEtiquetaSeccion(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Segoe UI", Font.BOLD, 13));
        label.setForeground(COLOR_TEXTO_NORMAL);
        label.setBorder(new EmptyBorder(10, 0, 4, 0));
        return label;
    }

    // Crea un campo de texto con el estilo oscuro de la pantalla, y le agrega
    // el efecto de "placeholder": el texto de ejemplo desaparece al hacer foco
    // y vuelve a aparecer si el usuario lo deja vacío
    private JTextField crearTextFieldEstilizado(String placeholder) {
        JTextField tf = new JTextField(placeholder);
        tf.setBackground(COLOR_COMPONENTE_BG);
        tf.setForeground(COLOR_TEXTO_PLACEHOLDER);
        tf.setCaretColor(COLOR_TEXTO_NORMAL);
        tf.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tf.setPreferredSize(new Dimension(0, 42));
        tf.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(COLOR_AZUL_LINEA, 1, true),
                new EmptyBorder(0, 12, 0, 12)
        ));
        
        tf.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent evt) {
                if (tf.getText().equals(placeholder)) {
                    tf.setText("");
                    tf.setForeground(COLOR_TEXTO_NORMAL);
                }
            }
            @Override
            public void focusLost(FocusEvent evt) {
                if (tf.getText().isEmpty()) {
                    tf.setText(placeholder);
                    tf.setForeground(COLOR_TEXTO_PLACEHOLDER);
                }
            }
        });
        return tf;
    }

    // Aplica el mismo estilo oscuro a los radio buttons de método de pago
    private void estilarRadioButton(JRadioButton rb) {
        rb.setBackground(COLOR_TARJETA); 
        rb.setForeground(COLOR_TEXTO_NORMAL);
        rb.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        rb.setFocusPainted(false);
        rb.setPreferredSize(new Dimension(0, 42));
        rb.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(COLOR_AZUL_LINEA, 1, true),
                new EmptyBorder(0, 12, 0, 12)
        ));
        rb.setBorderPainted(true);
    }
}

