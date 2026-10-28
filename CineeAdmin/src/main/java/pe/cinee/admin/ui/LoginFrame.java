package pe.cinee.admin.ui;

import pe.cinee.admin.AdminRepository;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Arrays;

public final class LoginFrame extends JFrame {
    private final AdminRepository admins = new AdminRepository();
    private final JTextField username = new JTextField();
    private final JPasswordField password = new JPasswordField();
    private final JLabel status = Ui.label("Conectando con la base de datos…",12,false,Ui.MUTED);
    private final JButton setup = Ui.button("Crear primer administrador",Ui.CARD_ALT);

    public LoginFrame(){
        super("Cinee · Acceso administrador");setDefaultCloseOperation(EXIT_ON_CLOSE);setSize(950,610);setMinimumSize(new Dimension(780,540));setLocationRelativeTo(null);
        JPanel root=new JPanel(new GridLayout(1,2));root.setBackground(Ui.BG);
        JPanel brand=new JPanel(new BorderLayout());brand.setBackground(Ui.SIDEBAR);brand.setBorder(new EmptyBorder(50,48,42,38));
        JPanel brandTop=new JPanel();brandTop.setOpaque(false);brandTop.setLayout(new BoxLayout(brandTop,BoxLayout.Y_AXIS));
        JLabel logo=Ui.label("CINEE",42,true,Ui.TEXT);JLabel dash=Ui.label("ADMIN CONSOLE",13,true,Ui.RED);
        brandTop.add(logo);brandTop.add(Box.createVerticalStrut(5));brandTop.add(dash);brandTop.add(Box.createVerticalStrut(35));
        brandTop.add(Ui.label("Tu cine, visto desde los datos.",22,true,Ui.TEXT));brandTop.add(Box.createVerticalStrut(14));
        brandTop.add(Ui.label("Control de ventas, compras detalladas y",14,false,Ui.MUTED));brandTop.add(Box.createVerticalStrut(4));brandTop.add(Ui.label("reportes desde un solo panel.",14,false,Ui.MUTED));
        brand.add(brandTop,BorderLayout.NORTH);
        JPanel art=new JPanel(){protected void paintComponent(Graphics gg){super.paintComponent(gg);Graphics2D g=(Graphics2D)gg.create();g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);int w=getWidth(),h=getHeight();g.setColor(new Color(31,44,69));for(int i=0;i<7;i++){int aw=46+i*18;g.fillRoundRect(60+i*20,h/2-75-aw/2,aw,aw,12,12);}g.setColor(Ui.RED);g.fillRoundRect(90,h/2-18,125,36,18,18);g.setColor(Ui.TEXT);g.setFont(new Font("Segoe UI",Font.BOLD,13));g.drawString("VENTAS  ·  ANÁLISIS  ·  CONTROL",55,h/2+65);g.dispose();}};art.setOpaque(false);brand.add(art,BorderLayout.CENTER);
        JPanel formWrap=new JPanel(new GridBagLayout());formWrap.setBackground(Ui.BG);
        JPanel form=new JPanel();form.setBackground(Ui.BG);form.setLayout(new BoxLayout(form,BoxLayout.Y_AXIS));form.setBorder(new EmptyBorder(28,35,28,35));form.setPreferredSize(new Dimension(385,410));
        form.add(Ui.label("Bienvenido",28,true,Ui.TEXT));form.add(Box.createVerticalStrut(8));form.add(Ui.label("Ingresa con tu cuenta de administrador.",13,false,Ui.MUTED));form.add(Box.createVerticalStrut(28));
        form.add(Ui.label("USUARIO",11,true,Ui.MUTED));form.add(Box.createVerticalStrut(7));styleInput(username);form.add(username);form.add(Box.createVerticalStrut(16));
        form.add(Ui.label("CONTRASEÑA",11,true,Ui.MUTED));form.add(Box.createVerticalStrut(7));styleInput(password);form.add(password);form.add(Box.createVerticalStrut(20));
        JButton login=Ui.button("Iniciar sesión  →",Ui.RED);login.setAlignmentX(Component.LEFT_ALIGNMENT);login.setMaximumSize(new Dimension(Integer.MAX_VALUE,44));login.addActionListener(e->login());form.add(login);
        form.add(Box.createVerticalStrut(10));setup.setAlignmentX(Component.LEFT_ALIGNMENT);setup.setMaximumSize(new Dimension(Integer.MAX_VALUE,42));setup.addActionListener(e->createAdmin());form.add(setup);
        form.add(Box.createVerticalStrut(15));status.setAlignmentX(Component.LEFT_ALIGNMENT);form.add(status);form.add(Box.createVerticalStrut(12));
        JButton config=Ui.button("Configurar base de datos",new Color(39,50,72));config.setAlignmentX(Component.LEFT_ALIGNMENT);config.addActionListener(e->{new ConnectionDialog(this).setVisible(true);refreshState();});form.add(config);
        formWrap.add(form);root.add(brand);root.add(formWrap);setContentPane(root);
        getRootPane().setDefaultButton(login);refreshState();
    }
    private static void styleInput(JTextField f){f.setFont(new Font("Segoe UI",Font.PLAIN,14));f.setForeground(Ui.TEXT);f.setBackground(Ui.CARD);f.setCaretColor(Ui.TEXT);f.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Ui.BORDER),new EmptyBorder(11,12,11,12)));f.setMaximumSize(new Dimension(Integer.MAX_VALUE,44));f.setAlignmentX(Component.LEFT_ALIGNMENT);}
    private void refreshState(){try{boolean has=admins.hasAdministrators();setup.setVisible(!has);status.setForeground(Ui.GREEN);status.setText(has?"Base de datos conectada · acceso protegido":"Primer uso: crea la cuenta administradora");}catch(Exception ex){setup.setVisible(false);status.setForeground(new Color(252,165,165));status.setText("No se pudo conectar. Revisa la configuración de MySQL.");}}
    private void login(){char[] p=password.getPassword();try{if(admins.authenticate(username.getText().trim(),p)){Arrays.fill(p,'\0');dispose();SwingUtilities.invokeLater(()->new AdminFrame(username.getText().trim()).setVisible(true));}else{status.setForeground(new Color(252,165,165));status.setText("Usuario o contraseña incorrectos.");password.setText("");}}catch(Exception ex){status.setForeground(new Color(252,165,165));status.setText("Error de acceso: "+(ex.getMessage()==null?"revisa MySQL":ex.getMessage()));}finally{Arrays.fill(p,'\0');}}
    private void createAdmin(){JTextField user=new JTextField();JPasswordField p1=new JPasswordField();JPasswordField p2=new JPasswordField();JPanel panel=new JPanel(new GridLayout(0,1,5,5));panel.add(new JLabel("Usuario (mínimo 3 caracteres):"));panel.add(user);panel.add(new JLabel("Contraseña (mínimo 10 caracteres):"));panel.add(p1);panel.add(new JLabel("Confirmar contraseña:"));panel.add(p2);int result=JOptionPane.showConfirmDialog(this,panel,"Crear primer administrador",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);if(result!=JOptionPane.OK_OPTION)return;char[] a=p1.getPassword(),b=p2.getPassword();try{if(!Arrays.equals(a,b)){JOptionPane.showMessageDialog(this,"Las contraseñas no coinciden.","Validación",JOptionPane.WARNING_MESSAGE);return;}admins.createFirstAdministrator(user.getText().trim(),a);JOptionPane.showMessageDialog(this,"Administrador creado. Ahora inicia sesión.","Cinee",JOptionPane.INFORMATION_MESSAGE);username.setText(user.getText().trim());password.setText("");refreshState();}catch(Exception ex){JOptionPane.showMessageDialog(this,ex.getMessage(),"No se pudo crear la cuenta",JOptionPane.ERROR_MESSAGE);}finally{Arrays.fill(a,'\0');Arrays.fill(b,'\0');}}
}
