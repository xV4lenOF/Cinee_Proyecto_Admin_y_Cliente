package pe.cinee.admin.ui;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DonutChartPanel extends JPanel {
    public record Item(String label, BigDecimal value) { }
    private List<Item> items = new ArrayList<>();
    private final Color[] colors = {Ui.RED, Ui.BLUE, Ui.GREEN, Ui.GOLD, new Color(167,139,250), new Color(251,146,60)};
    public DonutChartPanel(){setOpaque(false);setPreferredSize(new Dimension(320,230));}
    public void setItems(List<Item> values){items=values==null?new ArrayList<>():new ArrayList<>(values);repaint();}
    @Override protected void paintComponent(Graphics graphics){
        super.paintComponent(graphics); Graphics2D g=(Graphics2D)graphics.create();g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        if(items.isEmpty()){g.setColor(Ui.MUTED);String s="Sin datos para graficar";g.drawString(s,(getWidth()-g.getFontMetrics().stringWidth(s))/2,getHeight()/2);g.dispose();return;}
        double total=items.stream().map(Item::value).filter(v->v!=null).mapToDouble(BigDecimal::doubleValue).sum(); if(total<=0)total=1;
        int size=Math.min(160,Math.min(getHeight()-24,getWidth()/2));int x=12,y=(getHeight()-size)/2;int angle=0;
        for(int i=0;i<items.size();i++){int sweep=(int)Math.round(items.get(i).value().doubleValue()/total*360);g.setColor(colors[i%colors.length]);g.fillArc(x,y,size,size,angle,sweep);angle+=sweep;}
        g.setColor(Ui.CARD);g.fillOval(x+size/4,y+size/4,size/2,size/2);
        g.setColor(Ui.TEXT);g.setFont(new Font("Segoe UI",Font.BOLD,13));String center=items.size()+" medios";g.drawString(center,x+size/2-g.getFontMetrics().stringWidth(center)/2,y+size/2+4);
        int lx=x+size+18,ly=24;g.setFont(new Font("Segoe UI",Font.PLAIN,12));
        for(int i=0;i<items.size()&&i<7;i++){Item it=items.get(i);g.setColor(colors[i%colors.length]);g.fillRoundRect(lx,ly-9,9,9,3,3);g.setColor(Ui.TEXT);String name=it.label();if(name.length()>17)name=name.substring(0,16)+"…";g.drawString(name,lx+15,ly);g.setColor(Ui.MUTED);String val="S/ "+String.format(java.util.Locale.US,"%.2f",it.value().doubleValue());g.drawString(val,lx+15,ly+14);ly+=36;}
        g.dispose();
    }
}
