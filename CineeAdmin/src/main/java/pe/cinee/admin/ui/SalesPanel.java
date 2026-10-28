package pe.cinee.admin.ui;

import pe.cinee.admin.model.SaleDetail;
import pe.cinee.admin.model.SaleRecord;
import pe.cinee.admin.repository.SalesRepository;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.io.BufferedWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public final class SalesPanel extends JPanel {
    private final SalesRepository repo=new SalesRepository();
    private final JComboBox<String> period=DateRanges.createCombo("Todo");
    private final JTextField search=new JTextField(22);
    private final DefaultTableModel model=new DefaultTableModel(new Object[]{"Código venta","Fecha de compra","Cliente","Película / compra","Función","Sala","Asientos","Pago","Total","Estado"},0){@Override public boolean isCellEditable(int r,int c){return false;}};
    private final JTable table=new JTable(model);
    private List<SaleRecord> current=new ArrayList<>();
    private final JLabel countLabel=Ui.label("0 ventas",12,false,Ui.MUTED);
    private final JLabel statusLabel=Ui.label("",12,false,Ui.MUTED);
    private static final DateTimeFormatter DATE_TIME=DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public SalesPanel(){
        setLayout(new BorderLayout(0,15));setBackground(Ui.BG);setBorder(new EmptyBorder(24,25,24,25));
        JPanel heading=new JPanel(new BorderLayout());heading.setOpaque(false);JPanel titleBox=new JPanel();titleBox.setOpaque(false);titleBox.setLayout(new BoxLayout(titleBox,BoxLayout.Y_AXIS));titleBox.add(Ui.label("Registro de ventas",26,true,Ui.TEXT));titleBox.add(Box.createVerticalStrut(5));titleBox.add(Ui.label("Consulta cada compra, sus asientos, función y productos.",12,false,Ui.MUTED));heading.add(titleBox,BorderLayout.WEST);heading.add(Ui.label("SOLO LECTURA · VENTAS",10,true,Ui.RED),BorderLayout.EAST);add(heading,BorderLayout.NORTH);
        JPanel body=new JPanel(new BorderLayout(0,12));body.setOpaque(false);
        JPanel toolbar=new JPanel(new FlowLayout(FlowLayout.LEFT,8,0));toolbar.setOpaque(false);toolbar.add(Ui.label("Período:",12,false,Ui.MUTED));toolbar.add(period);styleSearch(search);toolbar.add(search);JButton searchBtn=Ui.button("Buscar",Ui.CARD_ALT);JButton detail=Ui.button("Ver compra",Ui.RED);JButton export=Ui.button("Exportar CSV",new Color(38,104,106));JButton refresh=Ui.button("Actualizar",Ui.CARD_ALT);toolbar.add(searchBtn);toolbar.add(detail);toolbar.add(export);toolbar.add(refresh);body.add(toolbar,BorderLayout.NORTH);
        Ui.styleTable(table);table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);table.setRowSorter(new TableRowSorter<>(model));
        int[] widths={105,135,150,180,130,65,125,90,95,90};for(int i=0;i<widths.length;i++)table.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);
        body.add(Ui.scroll(table),BorderLayout.CENTER);JPanel footer=new JPanel(new BorderLayout());footer.setOpaque(false);footer.add(countLabel,BorderLayout.WEST);footer.add(statusLabel,BorderLayout.EAST);body.add(footer,BorderLayout.SOUTH);add(body,BorderLayout.CENTER);
        searchBtn.addActionListener(e->refreshData());refresh.addActionListener(e->refreshData());period.addActionListener(e->refreshData());search.addActionListener(e->refreshData());detail.addActionListener(e->openSelected());export.addActionListener(e->exportCsv());table.addMouseListener(new java.awt.event.MouseAdapter(){@Override public void mouseClicked(java.awt.event.MouseEvent e){if(e.getClickCount()==2)openSelected();}});
        refreshData();
    }
    private static void styleSearch(JTextField f){f.setFont(new Font("Segoe UI",Font.PLAIN,12));f.setForeground(Ui.TEXT);f.setBackground(Ui.CARD);f.setCaretColor(Ui.TEXT);f.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Ui.BORDER),new EmptyBorder(8,10,8,10)));}
    public void refreshData(){DateRanges.Range r=DateRanges.resolve((String)period.getSelectedItem());try{current=repo.getSales(r.from(),r.to(),search.getText());model.setRowCount(0);for(SaleRecord s:current){model.addRow(new Object[]{s.id(),s.date()==null?"—":s.date().format(DATE_TIME),s.customer(),s.movie(),formatFunction(s),s.roomCode(),s.seats(),s.payment(),Ui.moneyText(s.total()),s.state()});}countLabel.setText(String.format(java.util.Locale.US,"%,d compras encontradas",current.size()));statusLabel.setForeground(Ui.GREEN);statusLabel.setText("Datos actualizados");}catch(Exception ex){statusLabel.setForeground(new Color(252,165,165));statusLabel.setText("Error de conexión o esquema: "+shorten(ex.getMessage(),58));} }
    private static String formatFunction(SaleRecord s){if("—".equals(s.functionDate())&&"—".equals(s.functionTime()))return "Sin función";return s.functionDate()+" · "+s.functionTime();}
    private void openSelected(){int row=table.getSelectedRow();if(row<0){JOptionPane.showMessageDialog(this,"Selecciona una venta para ver su detalle.","Ventas",JOptionPane.INFORMATION_MESSAGE);return;}int modelRow=table.convertRowIndexToModel(row);String id=String.valueOf(model.getValueAt(modelRow,0));try{SaleDetail d=repo.getSaleDetail(id);if(d==null)JOptionPane.showMessageDialog(this,"No se encontró la compra.","Ventas",JOptionPane.WARNING_MESSAGE);else new SaleDetailDialog(SwingUtilities.getWindowAncestor(this),d).setVisible(true);}catch(Exception ex){JOptionPane.showMessageDialog(this,"No se pudo leer el detalle: "+ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);}}
    private void exportCsv(){if(current.isEmpty()){JOptionPane.showMessageDialog(this,"No hay ventas para exportar.","Exportar",JOptionPane.INFORMATION_MESSAGE);return;}JFileChooser chooser=new JFileChooser();chooser.setDialogTitle("Guardar reporte de ventas");chooser.setSelectedFile(new java.io.File("reporte_ventas_cinee.csv"));if(chooser.showSaveDialog(this)!=JFileChooser.APPROVE_OPTION)return;try(BufferedWriter w=Files.newBufferedWriter(chooser.getSelectedFile().toPath(),StandardCharsets.UTF_8)){w.write("\uFEFF");w.write("Codigo venta,Fecha compra,Cliente,Documento,Correo,Telefono,Pelicula,Fecha funcion,Hora funcion,Codigo sala,Numero sala,Asientos,Metodo pago,Total,Estado,Ticket,QR\n");for(SaleRecord s:current){String[] fields={s.id(),s.date()==null?"":s.date().format(DATE_TIME),s.customer(),s.document(),s.email(),s.phone(),s.movie(),s.functionDate(),s.functionTime(),s.roomCode(),s.roomNumber(),s.seats(),s.payment(),String.format(java.util.Locale.US,"%.2f",s.total()),s.state(),s.ticketCode(),s.qrCode()};for(int i=0;i<fields.length;i++){if(i>0)w.write(',');w.write(csv(fields[i]));}w.write('\n');}JOptionPane.showMessageDialog(this,"Reporte exportado correctamente.\n"+chooser.getSelectedFile(),"Exportación completada",JOptionPane.INFORMATION_MESSAGE);}catch(Exception ex){JOptionPane.showMessageDialog(this,"No se pudo exportar: "+ex.getMessage(),"Error de exportación",JOptionPane.ERROR_MESSAGE);}}
    private static String csv(String s){if(s==null)return "\"\"";return "\""+s.replace("\"","\"\"")+"\"";}
    private static String shorten(String s,int max){if(s==null)return "sin detalle";return s.length()<=max?s:s.substring(0,max-1)+"…";}
}
