package pe.cinee.admin.ui;

import pe.cinee.admin.model.DashboardData;
import pe.cinee.admin.repository.SalesRepository;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public final class ReportsPanel extends JPanel {
    private final SalesRepository repo=new SalesRepository();
    private final JComboBox<String> period=DateRanges.createCombo("Este mes");
    private final JLabel total=Ui.label("S/ 0.00",23,true,Ui.TEXT), sales=Ui.label("0",23,true,Ui.TEXT), average=Ui.label("S/ 0.00",23,true,Ui.TEXT);
    private final BarChartPanel daily=new BarChartPanel(false,Ui.GREEN);
    private final DonutChartPanel methods=new DonutChartPanel();
    private final BarChartPanel movies=new BarChartPanel(true,Ui.RED);
    private final JLabel note=Ui.label("Selecciona un período para actualizar las gráficas.",12,false,Ui.MUTED);
    public ReportsPanel(){
        setLayout(new BorderLayout(0,16));setBackground(Ui.BG);setBorder(new EmptyBorder(24,25,24,25));
        JPanel head=new JPanel(new BorderLayout());head.setOpaque(false);JPanel title=new JPanel();title.setOpaque(false);title.setLayout(new BoxLayout(title,BoxLayout.Y_AXIS));title.add(Ui.label("Reportes y analítica",26,true,Ui.TEXT));title.add(Box.createVerticalStrut(5));title.add(Ui.label("Ingresos, medios de pago y películas con mejor rendimiento.",12,false,Ui.MUTED));head.add(title,BorderLayout.WEST);JPanel controls=new JPanel(new FlowLayout(FlowLayout.RIGHT,8,0));controls.setOpaque(false);controls.add(Ui.label("Período",12,false,Ui.MUTED));controls.add(period);JButton reload=Ui.button("Generar reporte",Ui.RED);controls.add(reload);head.add(controls,BorderLayout.EAST);add(head,BorderLayout.NORTH);
        JPanel body=new JPanel(new BorderLayout(0,14));body.setOpaque(false);JPanel metrics=new JPanel(new GridLayout(1,3,14,0));metrics.setOpaque(false);metrics.add(metric("INGRESO DEL PERÍODO",total,Ui.GREEN));metrics.add(metric("CANTIDAD DE VENTAS",sales,Ui.BLUE));metrics.add(metric("VENTA PROMEDIO",average,Ui.GOLD));
        JPanel graphs=new JPanel(new GridBagLayout());graphs.setOpaque(false);GridBagConstraints gc=new GridBagConstraints();gc.fill=GridBagConstraints.BOTH;gc.insets=new Insets(0,0,14,14);gc.weightx=1;gc.weighty=.53;gc.gridx=0;gc.gridy=0;graphs.add(Ui.cardLayout("Tendencia diaria","Importe de ventas por día",daily),gc);gc.gridx=1;gc.insets=new Insets(0,0,14,0);graphs.add(Ui.cardLayout("Medios de pago","Participación sobre los ingresos",methods),gc);gc.gridx=0;gc.gridy=1;gc.gridwidth=2;gc.weighty=.47;gc.insets=new Insets(0,0,0,0);graphs.add(Ui.cardLayout("Ranking de películas","Ingresos atribuidos a las compras con función",movies),gc);
        body.add(metrics,BorderLayout.NORTH);body.add(graphs,BorderLayout.CENTER);JPanel south=new JPanel(new BorderLayout());south.setOpaque(false);south.add(note,BorderLayout.WEST);body.add(south,BorderLayout.SOUTH);add(body,BorderLayout.CENTER);reload.addActionListener(e->refreshData());period.addActionListener(e->refreshData());refreshData();
    }
    private JPanel metric(String t,JLabel val,Color accent){JPanel p=new JPanel(new BorderLayout(0,12));p.setBackground(Ui.CARD);p.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Ui.BORDER),new EmptyBorder(15,16,15,16)));p.add(Ui.label(t,10,true,Ui.MUTED),BorderLayout.NORTH);p.add(val,BorderLayout.CENTER);JPanel line=new JPanel();line.setBackground(accent);line.setPreferredSize(new Dimension(4,3));p.add(line,BorderLayout.SOUTH);return p;}
    public void refreshData(){DateRanges.Range r=DateRanges.resolve((String)period.getSelectedItem());try{DashboardData d=repo.getDashboard(r.from(),r.to());total.setText(Ui.moneyText(d.revenue()));sales.setText(String.format(java.util.Locale.US,"%,d",d.saleCount()));average.setText(Ui.moneyText(d.averageSale()));List<BarChartPanel.Item> days=new ArrayList<>();DateTimeFormatter fmt=DateTimeFormatter.ofPattern("dd/MM");List<DashboardData.DailyRevenue> dayData=d.dailyRevenue();if(dayData.size()>20)dayData=dayData.subList(dayData.size()-20,dayData.size());for(var x:dayData)days.add(new BarChartPanel.Item(x.date().format(fmt),x.revenue()));daily.setItems(days);List<DonutChartPanel.Item> m=new ArrayList<>();for(var x:d.paymentTotals())m.add(new DonutChartPanel.Item(x.name(),x.value()));methods.setItems(m);List<BarChartPanel.Item> films=new ArrayList<>();for(var x:d.topMovies())films.add(new BarChartPanel.Item(x.name(),x.value()));movies.setItems(films);note.setForeground(Ui.GREEN);note.setText("Reporte generado · "+period.getSelectedItem()+" · "+d.saleCount()+" ventas incluidas");}catch(Exception ex){note.setForeground(new Color(252,165,165));note.setText("No se pudo generar el reporte. Comprueba la conexión y ejecuta el esquema SQL.");}revalidate();repaint();}
}
