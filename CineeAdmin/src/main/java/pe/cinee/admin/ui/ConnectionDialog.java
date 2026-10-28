package pe.cinee.admin.ui;

import pe.cinee.admin.Database;
import pe.cinee.admin.DatabaseConfig;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.Connection;
import java.util.Arrays;

public final class ConnectionDialog extends JDialog {
    private final JTextField url = new JTextField(42);
    private final JTextField user = new JTextField(22);
    private final JPasswordField password = new JPasswordField(22);
    private final JLabel status = Ui.label("Las credenciales se guardan en tu perfil local de Windows.", 12, false, Ui.MUTED);

    public ConnectionDialog(Window owner) {
        super(owner, "Conexión con MySQL", ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); getContentPane().setBackground(Ui.BG);
        JPanel body = new JPanel(new GridBagLayout()); body.setBackground(Ui.CARD); body.setBorder(new EmptyBorder(22,24,18,24));
        GridBagConstraints gc = new GridBagConstraints(); gc.insets=new Insets(7,5,7,5);gc.fill=GridBagConstraints.HORIZONTAL;gc.anchor=GridBagConstraints.WEST;
        addField(body,gc,0,"URL JDBC",url); addField(body,gc,1,"Usuario de MySQL",user); addField(body,gc,2,"Contraseña de MySQL",password);
        gc.gridx=0;gc.gridy=3;gc.gridwidth=2;body.add(status,gc);
        JPanel buttons=new JPanel(new FlowLayout(FlowLayout.RIGHT,8,0));buttons.setOpaque(false);
        JButton test=Ui.button("Probar conexión",Ui.CARD_ALT),save=Ui.button("Guardar",Ui.RED),cancel=Ui.button("Cerrar",new Color(58,69,89));
        buttons.add(test);buttons.add(save);buttons.add(cancel);
        test.addActionListener(e->testConnection(false)); save.addActionListener(e->testConnection(true)); cancel.addActionListener(e->dispose());
        setLayout(new BorderLayout(0,0));add(body,BorderLayout.CENTER);add(buttons,BorderLayout.SOUTH);
        try { DatabaseConfig c=DatabaseConfig.load();url.setText(c.getUrl());user.setText(c.getUsername());password.setText(c.getPassword()); }
        catch(Exception ex){url.setText("jdbc:mysql://localhost:3306/cinee_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=America/Lima&characterEncoding=UTF-8");user.setText("cinee_app");}
        for(JTextField f:new JTextField[]{url,user,password}){f.setBackground(new Color(10,17,30));f.setForeground(Ui.TEXT);f.setCaretColor(Ui.TEXT);f.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Ui.BORDER),new EmptyBorder(9,10,9,10)));}
        pack();setMinimumSize(new Dimension(640,280));setLocationRelativeTo(owner);
    }
    private void addField(JPanel body,GridBagConstraints gc,int row,String label,JComponent input){gc.gridwidth=1;gc.gridx=0;gc.gridy=row;body.add(Ui.label(label,12,true,Ui.MUTED),gc);gc.gridx=1;body.add(input,gc);}
    private void testConnection(boolean save) {
        char[] pass=password.getPassword();DatabaseConfig cfg=new DatabaseConfig(url.getText(),user.getText(),new String(pass));Arrays.fill(pass,'\0');
        try(Connection ignored=Database.connect(cfg)){
            if(save){cfg.save();status.setForeground(Ui.GREEN);status.setText("Conexión correcta. Configuración guardada en " + DatabaseConfig.CONFIG_PATH);JOptionPane.showMessageDialog(this,"Conexión correcta y configuración guardada.","MySQL",JOptionPane.INFORMATION_MESSAGE);}
            else {status.setForeground(Ui.GREEN);status.setText("Conexión correcta. No se guardaron cambios.");}
        }catch(Exception ex){status.setForeground(new Color(252,165,165));status.setText("No se pudo conectar: " + shorten(ex.getMessage(),82));}
    }
    private static String shorten(String s,int max){if(s==null)return "Error desconocido";return s.length()<=max?s:s.substring(0,max-1)+"…";}
}
