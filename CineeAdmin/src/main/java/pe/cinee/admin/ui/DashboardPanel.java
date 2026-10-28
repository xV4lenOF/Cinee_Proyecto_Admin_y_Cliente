package pe.cinee.admin.ui;

import pe.cinee.admin.model.DashboardData;
import pe.cinee.admin.repository.SalesRepository;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public final class DashboardPanel extends JPanel {
    private final SalesRepository repo = new SalesRepository();
    private final JComboBox<String> period = DateRanges.createCombo("Últimos 7 días");
    private final JLabel revenueValue = Ui.label("S/ 0.00",25,true,Ui.TEXT);
    private final JLabel salesValue = Ui.label("0",25,true,Ui.TEXT);
    private final JLabel averageValue = Ui.label("S/ 0.00",25,true,Ui.TEXT);
    private final JLabel customersValue = Ui.label("0",25,true,Ui.TEXT);
    private final JLabel subtitle = Ui.label("Ventas y movimientos registrados en la base de datos",12,false,Ui.MUTED);
    private final BarChartPanel dailyChart = new BarChartPanel(false,Ui.RED);
    private final DonutChartPanel paymentChart = new DonutChartPanel();
    private final BarChartPanel moviesChart = new BarChartPanel(true,Ui.BLUE);

    public DashboardPanel(){
        setLayout(new BorderLayout(0,18));setBackground(Ui.BG);setBorder(new EmptyBorder(24,25,24,25));
        JPanel heading=new JPanel(new BorderLayout(10,5));heading.setOpaque(false);
        JPanel texts=new JPanel();texts.setOpaque(false);texts.setLayout(new BoxLayout(texts,BoxLayout.Y_AXIS));texts.add(Ui.label("Resumen general",26,true,Ui.TEXT));texts.add(Box.createVerticalStrut(5));texts.add(subtitle);
        JPanel action=new JPanel(new FlowLayout(FlowLayout.RIGHT,8,0));action.setOpaque(false);action.add(Ui.label("Período",12,false,Ui.MUTED));action.add(period);JButton refresh=Ui.button("↻ Actualizar",Ui.CARD_ALT);action.add(refresh);refresh.addActionListener(e->refreshData());period.addActionListener(e->refreshData());
        heading.add(texts,BorderLayout.WEST);heading.add(action,BorderLayout.EAST);add(heading,BorderLayout.NORTH);

        JPanel body=new JPanel(new BorderLayout(0,16));body.setOpaque(false);
        JPanel metrics=new JPanel(new GridLayout(1,4,14,0));metrics.setOpaque(false);
        metrics.add(metric("INGRESOS TOTALES",revenueValue,"Suma de ventas no anuladas",Ui.RED));metrics.add(metric("VENTAS",salesValue,"Transacciones registradas",Ui.BLUE));metrics.add(metric("PROMEDIO POR VENTA",averageValue,"Ticket promedio",Ui.GREEN));metrics.add(metric("CLIENTES",customersValue,"Clientes con compras",Ui.GOLD));
        JPanel charts=new JPanel(new GridBagLayout());charts.setOpaque(false);GridBagConstraints gc=new GridBagConstraints();gc.insets=new Insets(0,0,14,14);gc.fill=GridBagConstraints.BOTH;gc.weightx=1;gc.weighty=.52;
        gc.gridx=0;gc.gridy=0;charts.add(Ui.cardLayout("Ingresos por día","Monto vendido por día",dailyChart),gc);gc.gridx=1;gc.insets=new Insets(0,0,14,0);charts.add(Ui.cardLayout("Medios de pago","Distribución del importe por método",paymentChart),gc);
        gc.gridx=0;gc.gridy=1;gc.gridwidth=2;gc.weighty=.48;gc.insets=new Insets(0,0,0,0);charts.add(Ui.cardLayout("Películas con más ingresos","Ordenadas por importe de ventas",moviesChart),gc);
        body.add(metrics,BorderLayout.NORTH);body.add(charts,BorderLayout.CENTER);add(body,BorderLayout.CENTER);
        refreshData();
    }
    private JPanel metric(String title,JLabel value,String note,Color accent){
        JPanel p=new JPanel(new BorderLayout(0,12));p.setBackground(Ui.CARD);p.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Ui.BORDER),new EmptyBorder(15,16,14,16)));
        JPanel top=new JPanel(new BorderLayout());top.setOpaque(false);top.add(Ui.label(title,10,true,Ui.MUTED),BorderLayout.WEST);JPanel dot=new JPanel();dot.setBackground(accent);dot.setPreferredSize(new Dimension(9,9));top.add(dot,BorderLayout.EAST);
        p.add(top,BorderLayout.NORTH);p.add(value,BorderLayout.CENTER);p.add(Ui.label(note,11,false,Ui.MUTED),BorderLayout.SOUTH);return p;
    }
    public void refreshData(){
        DateRanges.Range r=DateRanges.resolve((String)period.getSelectedItem());
        try{
            DashboardData d=repo.getDashboard(r.from(),r.to());revenueValue.setText(Ui.moneyText(d.revenue()));salesValue.setText(String.format(java.util.Locale.US,"%,d",d.saleCount()));averageValue.setText(Ui.moneyText(d.averageSale()));customersValue.setText(String.format(java.util.Locale.US,"%,d",d.customerCount()));
            List<BarChartPanel.Item> days=new ArrayList<>();List<DashboardData.DailyRevenue> src=d.dailyRevenue();if(src.size()>14)src=src.subList(src.size()-14,src.size());DateTimeFormatter fmt=DateTimeFormatter.ofPattern("dd/MM");for(var item:src)days.add(new BarChartPanel.Item(item.date().format(fmt),item.revenue()));dailyChart.setItems(days);
            List<DonutChartPanel.Item> pay=new ArrayList<>();for(var item:d.paymentTotals())pay.add(new DonutChartPanel.Item(item.name(),item.value()));paymentChart.setItems(pay);
            List<BarChartPanel.Item> movies=new ArrayList<>();for(var item:d.topMovies())movies.add(new BarChartPanel.Item(item.name(),item.value()));moviesChart.setItems(movies);
            subtitle.setText("Datos de la base de datos · período: "+(String)period.getSelectedItem());
        }catch(Exception ex){subtitle.setText("No se pudieron cargar las ventas. Revisa MySQL y el esquema cinee_db.");}
        revalidate();repaint();
    }
}
