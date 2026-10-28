package pe.cinee.admin.ui;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class BarChartPanel extends JPanel {
    public record Item(String label, BigDecimal value) { }
    private List<Item> items = new ArrayList<>();
    private final boolean horizontal;
    private final Color barColor;
    private String emptyText = "Todavía no hay ventas para este período";

    public BarChartPanel(boolean horizontal, Color barColor) {
        this.horizontal = horizontal; this.barColor = barColor; setOpaque(false); setPreferredSize(new Dimension(360, 230));
    }
    public void setItems(List<Item> values) { items = values == null ? new ArrayList<>() : new ArrayList<>(values); repaint(); }
    public void setEmptyText(String text) { emptyText = text; }

    @Override protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g = (Graphics2D) graphics.create(); g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int w = getWidth(), h = getHeight(); g.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        if (items.isEmpty()) { g.setColor(Ui.MUTED); drawCentered(g, emptyText, w, h); g.dispose(); return; }
        BigDecimal max = items.stream().map(Item::value).filter(v -> v != null).max(BigDecimal::compareTo).orElse(BigDecimal.ONE);
        if (max.signum() <= 0) max = BigDecimal.ONE;
        DecimalFormat df = new DecimalFormat("#,##0.##");
        if (horizontal) {
            int left = Math.min(160, Math.max(90, w / 3)), right = 78, top = 7;
            int rowH = Math.max(25, (h - 12) / items.size());
            for (int i=0; i<items.size(); i++) {
                Item it = items.get(i); int y=top+i*rowH;
                g.setColor(Ui.MUTED); String name = it.label(); if (name.length()>20) name=name.substring(0,19)+"…";
                g.drawString(name, 0, y+14);
                int barW = Math.max(0, w-left-right);
                g.setColor(Ui.BORDER); g.fillRoundRect(left,y+3,barW,12,8,8);
                int fill = (int)Math.round(barW * it.value().doubleValue()/max.doubleValue());
                g.setColor(barColor); g.fillRoundRect(left,y+3,Math.max(3,fill),12,8,8);
                g.setColor(Ui.TEXT); g.drawString(df.format(it.value()), left+barW+9, y+14);
            }
        } else {
            int left=38,right=10,top=10,bottom=43; int plotW=Math.max(20,w-left-right), plotH=Math.max(20,h-top-bottom);
            g.setColor(Ui.BORDER);
            for(int j=0;j<4;j++){ int y=top+(plotH*j)/3; g.drawLine(left,y,w-right,y); }
            int slot=plotW/items.size(), bw=Math.max(8,(int)(slot*.52));
            for(int i=0;i<items.size();i++){
                Item it=items.get(i); int bh=(int)Math.round(plotH*it.value().doubleValue()/max.doubleValue());
                int x=left+i*slot+(slot-bw)/2, y=top+plotH-bh;
                g.setColor(barColor); g.fillRoundRect(x,y,bw,Math.max(2,bh),8,8);
                g.setColor(Ui.TEXT); String val=df.format(it.value()); g.drawString(val,x+ bw/2 - g.getFontMetrics().stringWidth(val)/2, Math.max(top+10,y-5));
                g.setColor(Ui.MUTED); String lab=it.label(); if(lab.length()>10)lab=lab.substring(0,9)+"…";
                g.drawString(lab,x+bw/2-g.getFontMetrics().stringWidth(lab)/2,h-20);
            }
        }
        g.dispose();
    }
    private static void drawCentered(Graphics2D g,String s,int w,int h){ g.drawString(s, Math.max(0,(w-g.getFontMetrics().stringWidth(s))/2), h/2); }
}
