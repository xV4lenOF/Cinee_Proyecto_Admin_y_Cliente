package pe.cinee.admin.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class AdminFrame extends JFrame {
    private final String adminUser;
    private final CardLayout cards=new CardLayout();
    private final JPanel content=new JPanel(cards);
    private final DashboardPanel dashboard=new DashboardPanel();
    private final SalesPanel sales=new SalesPanel();
    private final ReportsPanel reports=new ReportsPanel();
    private String active="dashboard";
    private final java.util.Map<String, JButton> navButtons = new java.util.LinkedHashMap<>();

    public AdminFrame(String adminUser){
        super("Cinee Admin · Panel de ventas");this.adminUser=adminUser;setDefaultCloseOperation(EXIT_ON_CLOSE);setSize(1440,900);setMinimumSize(new Dimension(1120,720));setLocationRelativeTo(null);getContentPane().setBackground(Ui.BG);setLayout(new BorderLayout());
        add(sidebar(),BorderLayout.WEST);JPanel main=new JPanel(new BorderLayout());main.setBackground(Ui.BG);main.add(topbar(),BorderLayout.NORTH);
        content.setBackground(Ui.BG);content.add(dashboard,"dashboard");content.add(sales,"sales");content.add(reports,"reports");main.add(content,BorderLayout.CENTER);add(main,BorderLayout.CENTER);showCard("dashboard");
    }
    private JPanel sidebar(){
        JPanel side=new JPanel(new BorderLayout(0,18));side.setBackground(Ui.SIDEBAR);side.setPreferredSize(new Dimension(235,0));side.setBorder(new EmptyBorder(25,16,18,16));
        JPanel brand=new JPanel();brand.setOpaque(false);brand.setLayout(new BoxLayout(brand,BoxLayout.Y_AXIS));brand.add(Ui.label("CINEE",30,true,Ui.TEXT));brand.add(Box.createVerticalStrut(3));brand.add(Ui.label("ADMIN CONSOLE",10,true,Ui.RED));side.add(brand,BorderLayout.NORTH);
        JPanel nav=new JPanel();nav.setOpaque(false);nav.setLayout(new BoxLayout(nav,BoxLayout.Y_AXIS));nav.add(Ui.label("MENÚ PRINCIPAL",10,true,Ui.MUTED));nav.add(Box.createVerticalStrut(12));
        nav.add(navButton("▦   Resumen general","dashboard"));nav.add(Box.createVerticalStrut(6));nav.add(navButton("▤   Registro de ventas","sales"));nav.add(Box.createVerticalStrut(6));nav.add(navButton("▥   Reportes y gráficas","reports"));side.add(nav,BorderLayout.CENTER);
        JPanel bottom=new JPanel();bottom.setOpaque(false);bottom.setLayout(new BoxLayout(bottom,BoxLayout.Y_AXIS));JSeparator sep=new JSeparator();sep.setForeground(Ui.BORDER);bottom.add(sep);bottom.add(Box.createVerticalStrut(14));bottom.add(Ui.label("SESIÓN ACTIVA",10,true,Ui.MUTED));bottom.add(Box.createVerticalStrut(6));bottom.add(Ui.label("●  "+adminUser,13,true,Ui.TEXT));bottom.add(Box.createVerticalStrut(12));JButton db=Ui.button("⚙  Conexión de BD",new Color(35,47,69));db.setAlignmentX(Component.LEFT_ALIGNMENT);db.setMaximumSize(new Dimension(Integer.MAX_VALUE,40));db.addActionListener(e->{new ConnectionDialog(this).setVisible(true);refreshAll();});bottom.add(db);bottom.add(Box.createVerticalStrut(7));JButton logout=Ui.button("↪  Cerrar sesión",new Color(67,35,50));logout.setAlignmentX(Component.LEFT_ALIGNMENT);logout.setMaximumSize(new Dimension(Integer.MAX_VALUE,40));logout.addActionListener(e->{dispose();SwingUtilities.invokeLater(()->new LoginFrame().setVisible(true));});bottom.add(logout);side.add(bottom,BorderLayout.SOUTH);return side;
    }
    private JButton navButton(String text,String key){JButton b=Ui.button(text,active.equals(key)?new Color(91,24,48):Ui.SIDEBAR);navButtons.put(key,b);b.setHorizontalAlignment(SwingConstants.LEFT);b.setAlignmentX(Component.LEFT_ALIGNMENT);b.setMaximumSize(new Dimension(Integer.MAX_VALUE,44));b.setBorder(new EmptyBorder(12,14,12,10));b.addActionListener(e->showCard(key));return b;}
    private JPanel topbar(){JPanel top=new JPanel(new BorderLayout());top.setBackground(Ui.BG);top.setBorder(new EmptyBorder(17,26,14,26));JPanel left=new JPanel();left.setOpaque(false);left.setLayout(new BoxLayout(left,BoxLayout.Y_AXIS));left.add(Ui.label("Panel administrador",14,true,Ui.TEXT));left.add(Box.createVerticalStrut(4));left.add(Ui.label("Control de transacciones · Ventas solamente",11,false,Ui.MUTED));top.add(left,BorderLayout.WEST);String d=LocalDate.now().format(DateTimeFormatter.ofPattern("EEEE, dd 'de' MMMM yyyy",java.util.Locale.forLanguageTag("es-PE")));JLabel date=Ui.label(d,12,false,Ui.MUTED);top.add(date,BorderLayout.EAST);return top;}
    private void showCard(String key){active=key;cards.show(content,key);for(var entry:navButtons.entrySet())entry.getValue().setBackground(entry.getKey().equals(key)?new Color(91,24,48):Ui.SIDEBAR);if("dashboard".equals(key))dashboard.refreshData();else if("sales".equals(key))sales.refreshData();else if("reports".equals(key))reports.refreshData();}
    private void refreshAll(){dashboard.refreshData();sales.refreshData();reports.refreshData();}
}
