package pe.cinee.admin.ui;

import pe.cinee.admin.model.DetailLine;
import pe.cinee.admin.model.SaleDetail;
import pe.cinee.admin.model.SaleRecord;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;

public final class SaleDetailDialog extends JDialog {
    public SaleDetailDialog(Window owner, SaleDetail detail){
        super(owner,"Detalle de compra · "+detail.sale().id(),ModalityType.APPLICATION_MODAL);SaleRecord s=detail.sale();setDefaultCloseOperation(DISPOSE_ON_CLOSE);setSize(850,650);setMinimumSize(new Dimension(760,570));setLocationRelativeTo(owner);getContentPane().setBackground(Ui.BG);setLayout(new BorderLayout(0,0));
        JPanel root=new JPanel(new BorderLayout(0,14));root.setBackground(Ui.BG);root.setBorder(new EmptyBorder(20,22,18,22));
        JPanel top=new JPanel(new BorderLayout());top.setOpaque(false);JPanel tit=new JPanel();tit.setOpaque(false);tit.setLayout(new BoxLayout(tit,BoxLayout.Y_AXIS));tit.add(Ui.label("Detalle de compra",24,true,Ui.TEXT));tit.add(Box.createVerticalStrut(5));tit.add(Ui.label("Código: "+s.id()+"    ·    "+(s.date()==null?"Fecha no registrada":s.date().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))),12,false,Ui.MUTED));top.add(tit,BorderLayout.WEST);top.add(Ui.money(s.total(),27,true,Ui.GREEN),BorderLayout.EAST);root.add(top,BorderLayout.NORTH);
        JPanel content=new JPanel(new GridLayout(2,1,0,12));content.setOpaque(false);
        JPanel customer=new JPanel(new GridLayout(0,2,12,7));customer.setBackground(Ui.CARD);customer.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Ui.BORDER),new EmptyBorder(14,15,14,15)));
        info(customer,"CLIENTE",s.customer());info(customer,"DOCUMENTO",s.document());info(customer,"CORREO",emptyDash(s.email()));info(customer,"TELÉFONO",emptyDash(s.phone()));info(customer,"PELÍCULA",s.movie());info(customer,"FECHA DE FUNCIÓN",s.functionDate());info(customer,"HORARIO",s.functionTime());info(customer,"CÓDIGO DE SALA",s.roomCode()+("—".equals(s.roomNumber())?"":" · Sala "+s.roomNumber()));info(customer,"CÓDIGOS DE ASIENTO",s.seats());info(customer,"MÉTODO DE PAGO",s.payment());info(customer,"ESTADO",s.state());info(customer,"TICKET / QR",s.ticketCode()+" / "+s.qrCode());
        JPanel itemsPanel=new JPanel(new BorderLayout(0,8));itemsPanel.setOpaque(false);itemsPanel.add(Ui.label("Productos y entradas de la compra",15,true,Ui.TEXT),BorderLayout.NORTH);DefaultTableModel tm=new DefaultTableModel(new Object[]{"Código","Producto","Categoría","Cantidad","Precio unitario","Subtotal"},0){@Override public boolean isCellEditable(int r,int c){return false;}};for(DetailLine line:detail.lines())tm.addRow(new Object[]{line.productCode(),line.productName(),line.category(),line.quantity(),Ui.moneyText(line.unitPrice()),Ui.moneyText(line.subtotal())});JTable table=new JTable(tm);Ui.styleTable(table);itemsPanel.add(Ui.scroll(table),BorderLayout.CENTER);
        content.add(customer);content.add(itemsPanel);root.add(content,BorderLayout.CENTER);
        JPanel bottom=new JPanel(new BorderLayout());bottom.setOpaque(false);bottom.add(Ui.label("Los datos mostrados se leen de la venta guardada; no se modifica la compra desde este panel.",11,false,Ui.MUTED),BorderLayout.WEST);JButton close=Ui.button("Cerrar",Ui.RED);close.addActionListener(e->dispose());bottom.add(close,BorderLayout.EAST);root.add(bottom,BorderLayout.SOUTH);add(root);
    }
    private static void info(JPanel p,String k,String v){JPanel cell=new JPanel(new BorderLayout(0,2));cell.setOpaque(false);cell.add(Ui.label(k,9,true,Ui.MUTED),BorderLayout.NORTH);cell.add(Ui.label(emptyDash(v),12,true,Ui.TEXT),BorderLayout.CENTER);p.add(cell);}
    private static String emptyDash(String s){return s==null||s.isBlank()?"—":s;}
}
