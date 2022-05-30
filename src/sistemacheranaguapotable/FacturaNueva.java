/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemacheranaguapotable;

import helpers.sql.SqlDetallePagos;
import helpers.sql.SqlPagoMinimo;
import helpers.sql.SqlPagos;
import helpers.sql.SqlPagosYdetallePagos;
import helpers.sql.SqlUsuarios;
import helpers.sql.clases.MostrarPagos;
import java.time.LocalDate;
import javax.swing.JOptionPane;
import static sistemacheranaguapotable.VentanaPagos.idCliente;
import static sistemacheranaguapotable.VentanaPagos.periodo1;
import static sistemacheranaguapotable.VentanaPagos.periodo2;


/**
 *
 * @author jafeth888
 */
public class FacturaNueva extends javax.swing.JDialog {
    public static int periodo1,periodo2;
    /**
     * Creates new form FacturaNueva
     */
    public FacturaNueva(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        campoIdCliente = new javax.swing.JTextField();
        jlabelNombreCliente = new javax.swing.JLabel();
        campoNombreCliente = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        campoTipoTarifa = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        campoPrecioTarifa = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        campoTipoDescuento = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        campoDescuento = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jYearChooser1 = new com.toedter.calendar.JYearChooser();
        jComboBoxTipoPago = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        buttonGenerarFactura = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Id del cliente");

        campoIdCliente.setEditable(false);
        campoIdCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoIdClienteActionPerformed(evt);
            }
        });

        jlabelNombreCliente.setText("Nombre");

        campoNombreCliente.setEditable(false);

        jLabel2.setText("Tipo tarifa");

        campoTipoTarifa.setEditable(false);

        jLabel3.setText("Precio tarifa");

        campoPrecioTarifa.setEditable(false);

        jLabel4.setText("Tipo descuento");

        campoTipoDescuento.setEditable(false);

        jLabel5.setText("Descuento");

        campoDescuento.setEditable(false);

        jLabel6.setText("Periodo");

        jComboBoxTipoPago.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Anual", "Mensual" }));

        jLabel7.setText("tipo pago");

        buttonGenerarFactura.setText("Generar nueva factura");
        buttonGenerarFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonGenerarFacturaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(buttonGenerarFactura, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jYearChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE)
                            .addComponent(campoIdCliente, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(campoNombreCliente)
                            .addComponent(jlabelNombreCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBoxTipoPago, 0, 96, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(campoTipoTarifa, javax.swing.GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(campoPrecioTarifa, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
                    .addComponent(campoTipoDescuento))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(campoDescuento, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(47, 47, 47))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jlabelNombreCliente)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campoIdCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(campoNombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(campoTipoTarifa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(campoPrecioTarifa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(campoTipoDescuento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(campoDescuento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jYearChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxTipoPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addComponent(buttonGenerarFactura)
                .addContainerGap(121, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void campoIdClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoIdClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoIdClienteActionPerformed

    private void buttonGenerarFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonGenerarFacturaActionPerformed
        // TODO add your handling code here:
        
        SqlPagos pago= new SqlPagos();
        SqlPagosYdetallePagos pagoYdetalle =new SqlPagosYdetallePagos();
        SqlPagoMinimo instanciaPagoMinimo=new SqlPagoMinimo();
        String item=jComboBoxTipoPago.getSelectedItem().toString();
        float tarifa=Float.parseFloat(campoPrecioTarifa.getText());
        float descuento=Float.parseFloat(campoDescuento.getText());

        if(item.equals("Anual")){
            if(pago.yaExistePagoConUsuarioPeriodo(campoIdCliente.getText(),String.valueOf(jYearChooser1.getYear()))){
                JOptionPane.showMessageDialog(rootPane,"el cliente:" +campoNombreCliente.getText()+" ya tiene una factura con el periodo: "+jYearChooser1.getYear());
                return;
            }
            String fkIdCliente=campoIdCliente.getText(),tipoTarifa=campoTipoTarifa.getText();
            String precioTarifa=campoPrecioTarifa.getText(),tipoDescuento=campoTipoDescuento.getText();
            String descuentoAplicado=campoDescuento.getText(),tipoPago=jComboBoxTipoPago.getSelectedItem().toString();
            String periodo=String.valueOf(jYearChooser1.getYear());
            
            float descuentoAnual=(tarifa/12)*2;
            float descuentoFinal=descuento*10;//se descuentan a los demas meses ya que el pago sera anual enero-diciembre
            float total=tarifa-descuentoFinal-descuentoAnual;
            float pagoMinimo=instanciaPagoMinimo.obtenerPagoMinimo();
            if(total<=0){
                System.err.println("total-- "+total);
                total=pagoMinimo*10;
            }

            /*----------------------------------------REGISTRAR PAGO Y DETALLE PAGO: TIPO DE PAGO ANUAL-------------------------------------------------*/
            String fecha =LocalDate.now().toString();//registramos la fecha, para futuras busquedas por fecha al pago
            pagoYdetalle.registrarPagoYdetalleTipoAnual(fkIdCliente, tipoTarifa, precioTarifa, 
                tipoDescuento, descuentoAplicado, tipoPago, descuentoAnual, total,total, periodo,fecha);//el parametro total se repite porque inicialmente el total a pagar es la deuda
            /*----------------------------------------FIN DE REGISTRAR PAGO Y DETALLE PAGO: TIPO DE PAGO ANUAL-------------------------------------------------*/
            //actulizamos la tabla de detalle pagos en la pestania cobros 
            MostrarPagos instancia=new MostrarPagos();
            instancia.mostrarDetallePagos(campoIdCliente.getText(),periodo1,periodo2, 
                MainJFrame.tablaDetallePagos);
            ////actulizamos el jlabel deDeudaTotal del cliente en la pestania de cobros
            SqlUsuarios instanciaSqlUsuarios=new SqlUsuarios();
            MainJFrame.jLabelValueDeudaTotal.setText(""+instanciaSqlUsuarios.obtenerDeudaTotalCliente(campoIdCliente.getText(), periodo1, periodo2));
        }else if(item.equals("Mensual")){
            
            if(pago.yaExistePagoConUsuarioPeriodo(campoIdCliente.getText(),String.valueOf(jYearChooser1.getYear()))){
                JOptionPane.showMessageDialog(rootPane,"el cliente:" +campoNombreCliente.getText()+" ya tiene una factura con el periodo: "+jYearChooser1.getYear());
                return;
            }
            
            String idCliente=campoIdCliente.getText(),tipoTarifa=campoTipoTarifa.getText();
            String precioTarifa=campoPrecioTarifa.getText(),tipoDescuento=campoTipoDescuento.getText();
            String descuentoAplicado=campoDescuento.getText(),tipoPago=jComboBoxTipoPago.getSelectedItem().toString();
            String periodo=String.valueOf(jYearChooser1.getYear());
            
            float descuentoAnual=0;//ya que estara pagarando por mes por lo tanto no aplica descuento anual
            float descuentoFinal=descuento*12;//se hace descuento a los 12 meses ya que no hay un descuentoAnual(bimestral)
            float total=tarifa-descuentoFinal-descuentoAnual;
            float pagoMinimo=instanciaPagoMinimo.obtenerPagoMinimo();
            if(total<=0){
                total=pagoMinimo*12;
            }
            
            float importeMes=(tarifa/12)-descuento;
            if(importeMes<=0){
                importeMes=pagoMinimo;
            }
            String fecha =LocalDate.now().toString();//registramos la fecha, para futuras busquedas por fecha al pago    
            pagoYdetalle.registrarPagoYdetalleTipoMensual(idCliente, tipoTarifa, precioTarifa, 
                tipoDescuento, descuentoAplicado, tipoPago, descuentoAnual, total,total, periodo, importeMes,fecha);//el parametro total se repite porque inicialmente el total a pagar es la deuda
            
            //actulizamos la tabla de detalle pagos en la pestania cobros al eliminar el registro del pago
            MostrarPagos instancia=new MostrarPagos();
            instancia.mostrarDetallePagos(campoIdCliente.getText(),periodo1,periodo2, 
                MainJFrame.tablaDetallePagos);
            //actulizamos el jlabel deDeudaTotal del cliente en la pestania de cobros
            SqlUsuarios instanciaSqlUsuarios=new SqlUsuarios();
            MainJFrame.jLabelValueDeudaTotal.setText(""+instanciaSqlUsuarios.obtenerDeudaTotalCliente(campoIdCliente.getText(), periodo1, periodo2));
            
        }
        dispose();
    }//GEN-LAST:event_buttonGenerarFacturaActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FacturaNueva.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FacturaNueva.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FacturaNueva.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FacturaNueva.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FacturaNueva dialog = new FacturaNueva(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonGenerarFactura;
    public static javax.swing.JTextField campoDescuento;
    public static javax.swing.JTextField campoIdCliente;
    public static javax.swing.JTextField campoNombreCliente;
    public static javax.swing.JTextField campoPrecioTarifa;
    public static javax.swing.JTextField campoTipoDescuento;
    public static javax.swing.JTextField campoTipoTarifa;
    private javax.swing.JComboBox<String> jComboBoxTipoPago;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private com.toedter.calendar.JYearChooser jYearChooser1;
    private javax.swing.JLabel jlabelNombreCliente;
    // End of variables declaration//GEN-END:variables
}
