/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemacheranaguapotable;

import helpers.jtables.MetodosTablas;
import helpers.sql.SqlDetallePagos;
import helpers.sql.SqlPagos;
import helpers.sql.SqlPagosYdetallePagos;
import helpers.sql.SqlUsuarios;
import helpers.sql.clases.MostrarPagos;
import impresiones.Imprimir;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import sistemacheranaguapotable.bd.ConexionBd;

/**
 *
 * @author jafeth888
 */
public class VentanaPagos extends javax.swing.JDialog {
    ConexionBd cc= ConexionBd.obtenerInstancia();
    Connection cn= cc.conexion();
    public static String idCliente;// variable estatica para mostrar los pagos en relacion al id 
    public static String periodo;  //variable estatica para mostrar exclusivamente los pagos de un año determinado
    public static int periodo1,periodo2;//variables para actulizar la tablaDetallePagos en la pestania de cobros
    
    /**
     * Creates new form VentanaPagos
     */
    public VentanaPagos(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        mostrarPagosCliente(idCliente,periodo);
        
        //System.out.print(idCliente);
    }
    
    public void mostrarPagosCliente(String idCliente,String periodo){
        System.out.println("periodo-----"+periodo);
        DefaultTableModel modelo= new DefaultTableModel();
 
        modelo.addColumn("Idpago");
        modelo.addColumn("Tipo Tarifa");
        modelo.addColumn("Tarifa x año");
        modelo.addColumn("Tipo descuento");
        modelo.addColumn("descuento x mes");
        modelo.addColumn("pago");
        modelo.addColumn("descuento anual");
        modelo.addColumn("Total a pagar");
        modelo.addColumn("Total Pagado");
        modelo.addColumn("Deuda");
        modelo.addColumn("periodo");
        String sql="";
        //table.setModel(modelo);
        jtablePagos.setModel(modelo);
  
        //SELECT id_pago,tipo_tarifa,precio_tarifa,tipo_descuento,descuento,tipo_pago,descuento_anual,total,total_pagado,deuda,periodo FROM `pagos` WHERE fk_id_cliente = 1 AND estado='en deuda'
        sql="SELECT id_pago,tipo_tarifa,precio_tarifa,tipo_descuento,descuento,tipo_pago,descuento_anual,"
                + "total,total_pagado,deuda,periodo FROM `pagos` WHERE fk_id_cliente ='"+idCliente+"' AND pagos.periodo='"+periodo+"' AND fk_id_estado_pago='2'";
        

        Object []datos = new Object [11];
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                datos[0]=rs.getString(1);
                datos[1]=rs.getString(2);
                datos[2]=rs.getString(3);
                datos[3]=rs.getString(4);
                datos[4]=rs.getString(5);
                datos[5]=rs.getString(6);
                datos[6]=rs.getString(7);
                datos[7]=rs.getString(8);
                datos[8]=rs.getString(9);
                datos[9]=rs.getString(10);
                datos[10]=rs.getInt(11);
                modelo.addRow(datos);
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
        String idPago=jtablePagos.getValueAt(0,0).toString();
        mostrarDetallePagos(idPago);
        mostrarMesesPagados(idPago);
        
        if(jtablePagos.getValueAt(0,5).toString().equals("Mensual")){
            botonCobrar.setText("cobrar solo: " +jtableDetallePagos.getValueAt(0,3));
            
            float tarifa=Float.parseFloat(jtablePagos.getValueAt(0,2).toString());
            jTextAreaInfo.setText("tarifa correpondiente por tipo de pago mensual:"
                + " "+tarifa/12);
            
        }
        if(jtablePagos.getValueAt(0,5).toString().equals("Anual")){
            String Tipodescuento=jtablePagos.getValueAt(0,3).toString();
            float descuento=Float.parseFloat(jtablePagos.getValueAt(0,4).toString());
            jTextAreaInfo.setText("Descuento de tipo "+Tipodescuento+" correpondiente por pago Anual:"
                + " "+descuento*10);
        }
        
    }
    TableColumn seleccionar;// no mover de este lugar xd
    public void mostrarDetallePagos(String idPago){
        DefaultTableModel modelo= new DefaultTableModel();
        MetodosTablas metodoTabla=new MetodosTablas();
        modelo.addColumn("Id Registro");
        modelo.addColumn("Id Pago");
        modelo.addColumn("Periodo");
        modelo.addColumn("Mes");
        modelo.addColumn("Importe");
        modelo.addColumn("selecionar");//columna que sera de tipo checkbox
        
        String sql="";
        //table.setModel(modelo);
        jtableDetallePagos.setModel(modelo);
        
        //añadimos columna de tipo checkbox----------------------------------------
        metodoTabla.addCheckBox(5,jtableDetallePagos);
        
  
        //SELECT id_pago,tipo_tarifa,precio_tarifa,tipo_descuento,descuento,tipo_pago,descuento_anual,total,total_pagado,deuda,periodo FROM `pagos` WHERE fk_id_cliente = 1 AND estado='en deuda'
        sql="SELECT id_registro,fk_id_pago,periodo,mes,importe"
                + " FROM detalle_pagos join pagos on pagos.id_pago=detalle_pagos.fk_id_pago "
                + " WHERE fk_id_pago ='"+idPago+"' AND detalle_pagos.fk_id_estado_pago='2'";
        

        Object []datos = new Object [5];
            try {
                Statement st = cn.createStatement();
                ResultSet rs = st.executeQuery(sql);
                while(rs.next()){
                    datos[0]=rs.getString(1);
                    datos[1]=rs.getString(2);
                    datos[2]=rs.getInt(3);
                    datos[3]=rs.getString(4);
                    datos[4]=rs.getString(5);
                    modelo.addRow(datos);
                }
                
            } catch (SQLException ex) {
                ex.printStackTrace();
                System.out.println(ex.getMessage());
            }
    }
    
     
    public void mostrarMesesPagados(String idPago){
        DefaultTableModel modelo= new DefaultTableModel();
        modelo.addColumn("Mes pagado");
        modelo.addColumn("Fecha de pago");
        modelo.addColumn("importe");
        
        
        String sql="";
        //table.setModel(modelo);
        jTableMesesPagados.setModel(modelo);
        
        
  
        //SELECT id_pago,tipo_tarifa,precio_tarifa,tipo_descuento,descuento,tipo_pago,descuento_anual,total,total_pagado,deuda,periodo FROM `pagos` WHERE fk_id_cliente = 1 AND estado='en deuda'
        sql="SELECT mes,fecha_pago,importe"
                + " FROM detalle_pagos join pagos on pagos.id_pago=detalle_pagos.fk_id_pago "
                + " WHERE fk_id_pago ='"+idPago+"' AND detalle_pagos.fk_id_estado_pago='1'";
        

        Object []datos = new Object [3];
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                datos[0]=rs.getString(1);
                datos[1]=rs.getString(2);
                datos[2]=rs.getInt(3);
                modelo.addRow(datos);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jtablePagos = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtableDetallePagos = new javax.swing.JTable();
        botonCobrar = new javax.swing.JButton();
        botonDestruirRegistro = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        label_idCliente = new javax.swing.JLabel();
        jlabelValueIdCliente = new javax.swing.JLabel();
        jlabelCliente = new javax.swing.JLabel();
        jLabelvalueCliente = new javax.swing.JLabel();
        jlabelDomicilio = new javax.swing.JLabel();
        jlabelValueDomicilio = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jlabelValueBarrio = new javax.swing.JLabel();
        botonCobrosSeleccionados = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableMesesPagados = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextAreaInfo = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jtablePagos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jtablePagos);

        jtableDetallePagos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id registro", "Id pago", "Mes", "importe"
            }
        ));
        jScrollPane2.setViewportView(jtableDetallePagos);

        botonCobrar.setText("Cobrar");
        botonCobrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCobrarActionPerformed(evt);
            }
        });

        botonDestruirRegistro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/advertencia.png"))); // NOI18N
        botonDestruirRegistro.setText("Destruir Registro");
        botonDestruirRegistro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonDestruirRegistroActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(102, 204, 255), new java.awt.Color(153, 204, 255)), "Datos del cliente", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        label_idCliente.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        label_idCliente.setText("Codigo cliente:");
        label_idCliente.setToolTipText("");

        jlabelValueIdCliente.setText("codigo cliente");

        jlabelCliente.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jlabelCliente.setText("Cliente:");
        jlabelCliente.setToolTipText("");

        jLabelvalueCliente.setText("no value");

        jlabelDomicilio.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jlabelDomicilio.setText("Domicilio:");

        jlabelValueDomicilio.setText("Francisco I madero #584 col. lazaro cardenas");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Barrio");

        jlabelValueBarrio.setText("1");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(label_idCliente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jlabelValueIdCliente))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jlabelCliente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelvalueCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jlabelDomicilio)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jlabelValueDomicilio, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jlabelValueBarrio, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(395, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label_idCliente)
                    .addComponent(jlabelValueIdCliente))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlabelCliente)
                    .addComponent(jLabelvalueCliente)
                    .addComponent(jlabelDomicilio)
                    .addComponent(jlabelValueDomicilio)
                    .addComponent(jLabel1)
                    .addComponent(jlabelValueBarrio))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jlabelValueIdCliente.getAccessibleContext().setAccessibleName("codigoPago");
        jlabelValueIdCliente.getAccessibleContext().setAccessibleDescription("");

        botonCobrosSeleccionados.setText("Cobrar seleccionados");
        botonCobrosSeleccionados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCobrosSeleccionadosActionPerformed(evt);
            }
        });

        jButton1.setText("Seleccionar todo");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTableMesesPagados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane3.setViewportView(jTableMesesPagados);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/publicalo.png"))); // NOI18N

        jScrollPane4.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane4.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jTextAreaInfo.setEditable(false);
        jTextAreaInfo.setBackground(new java.awt.Color(255, 204, 204));
        jTextAreaInfo.setColumns(20);
        jTextAreaInfo.setRows(5);
        jScrollPane4.setViewportView(jTextAreaInfo);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(botonDestruirRegistro, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(botonCobrosSeleccionados, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(botonCobrar, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane3)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 591, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonDestruirRegistro)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonCobrosSeleccionados, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonCobrar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonCobrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCobrarActionPerformed
        // TODO add your handling code here:
        
        int filasDetallePagos=jtableDetallePagos.getRowCount();
        if(filasDetallePagos>0){ 
           SqlPagos pago = new SqlPagos();
           SqlDetallePagos detallePago=new SqlDetallePagos();
           String idRegistro=jtableDetallePagos.getValueAt(0,0).toString();
           String idPago=jtableDetallePagos.getValueAt(0,1).toString();
           String cadenaImporte=jtableDetallePagos.getValueAt(0,4).toString();
           String cadenaPagoRecibido;
           String fechaPago=LocalDate.now().toString();
           
           float cambio;
           float pagoRecibido;
           float importe;
           /*VARIABLES FALTANTES PARA GENERAR EL RECIBO */
           String nombreCompleto=jLabelvalueCliente.getText();
           String domicilio=jlabelValueDomicilio.getText();
           String barrio=jlabelValueBarrio.getText();
           String tipoPago=jtablePagos.getValueAt(0,5).toString();
           String periodo=jtablePagos.getValueAt(0,10).toString();
           String tipoTarifa=jtablePagos.getValueAt(0,1).toString();
           float precioTarifaAnual=Float.parseFloat(jtablePagos.getValueAt(0, 2).toString());
           String tipoDescuento=jtablePagos.getValueAt(0,3).toString();
           float precioDescuentoMensual=Float.parseFloat(jtablePagos.getValueAt(0,4).toString());
           String descuentoAnual=jtablePagos.getValueAt(0,6).toString();
           
           String mesDepago=jtableDetallePagos.getValueAt(0,3).toString();
           /*
           //codigo para mostrar el cambio del pago
           try{
                cadenaPagoRecibido=JOptionPane.showInputDialog("pago recibido");
                pagoRecibido=Float.parseFloat(cadenaPagoRecibido);
           }catch(Exception e){

               //System.out.print(e.getMessage());
               System.err.println("Ventana cerrada!!");
               return;
           }
           */
           importe=Float.parseFloat(cadenaImporte);
           

           cambio=0;
           detallePago.cobrarImporteDetallePagos(idRegistro,String.valueOf(importe),String.valueOf(cambio), 
                fechaPago,1);
           
           JOptionPane.showMessageDialog(rootPane,"se ha cobrado el mes: "+mesDepago);
           
           float totalTablaPagos;//esta variable representa el total a pagar de una factura, aplicado los descuentos
           float sumatoriaImportesPagadosTablaDetallePagos;
           float deuda;
           
           totalTablaPagos=pago.obtenerTotalPago(idPago);
           sumatoriaImportesPagadosTablaDetallePagos=detallePago.obtenerSumatoriaImportesPagadosTablaDetallePagos(idPago);
           deuda=totalTablaPagos-sumatoriaImportesPagadosTablaDetallePagos;
           pago.actualizarDeudaRegistroPago(idPago,sumatoriaImportesPagadosTablaDetallePagos, deuda);
           
           if(deuda==0){
             pago.actualizarEstadoRegistroPago(idPago,1);
             JOptionPane.showMessageDialog(null,"El cliente ha completado todos los pagos");
           }
           
           /*
           int opcion=JOptionPane.showConfirmDialog(rootPane,"pago registrado correctamente, desea imprimir Comprobante");
           if(opcion==0){
               
           }*/
           Imprimir imprimir=new Imprimir();
           imprimir.imprimirComprobante(idPago, idRegistro,nombreCompleto,domicilio,barrio,tipoPago,periodo,tipoTarifa,precioTarifaAnual, 
               tipoDescuento,precioDescuentoMensual, descuentoAnual,mesDepago, fechaPago, cadenaImporte);
           
           
           //ya no mostramos informacion en las tablas de esta ventana porque la cerramos inmediatamente al generar el recibo
           //mostrarPagosCliente(idCliente,periodo);
           //mostrarDetallePagos(idPago);
           
           System.out.println("id pago-- "+idPago);
           System.out.println(sumatoriaImportesPagadosTablaDetallePagos);
           System.out.println(deuda);
           this.dispose();
        }
    }//GEN-LAST:event_botonCobrarActionPerformed

    private void botonDestruirRegistroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonDestruirRegistroActionPerformed
        // TODO add your handling code here:
        //aceptar=0 cancelar=2
        int opcion=JOptionPane.showConfirmDialog(rootPane,"Todo registro referente a este pago sera eliminado permanentemente y no podra recuperarse"
                + " esta seguro de realizar esta accion? ","BORRADO PERMANENTE",JOptionPane.OK_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE);
        //System.err.println("opcionElegida-----"+opcion);
        if(opcion==0){
            String frase="";
            String fraseConfirmacion="estoy seguro de eliminar registro";
            while(!fraseConfirmacion.equals(frase) && frase!=null){
                //frase==null cuando se cierra o cancela la ventana del inputDialog
                frase=JOptionPane.showInputDialog(rootPane,"escriba la siguiente frase: 'estoy seguro de eliminar registro'", 
                    "CONFIRMAR ELIMINACION",JOptionPane.INFORMATION_MESSAGE);
                if(frase==null)JOptionPane.showMessageDialog(rootPane,"operacion cancelada","eliminacion cancelada",JOptionPane.INFORMATION_MESSAGE);
                
                if(frase.equals(fraseConfirmacion)){
                    SqlPagos instanciaSqlPagos= new SqlPagos();
                    String idPago= jtablePagos.getValueAt(0,0).toString();
                    instanciaSqlPagos.eliminarPermanentementeRegistroPago(idPago);
                    
                    //actulizamos la tabla de detalle pagos en la pestania cobros al eliminar el registro del pago
                    MostrarPagos instancia=new MostrarPagos();
                    instancia.mostrarDetallePagos(jlabelValueIdCliente.getText(),periodo1,periodo2, 
                        MainJFrame.tablaDetallePagos);
                    ////actulizamos el jlabel deDeudaTotal del cliente en la pestania de cobros
                    SqlUsuarios instanciaSqlUsuarios=new SqlUsuarios();
                    MainJFrame.jLabelValueDeudaTotal.setText(""+instanciaSqlUsuarios.obtenerDeudaTotalCliente(idCliente, periodo1, periodo2));
                    this.dispose();
                }else{
                    JOptionPane.showMessageDialog(rootPane,"la frase no coincide");
                }
                
            }
            
        }else{
            JOptionPane.showMessageDialog(rootPane,"operacion cancelada","eliminacion cancelada",JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_botonDestruirRegistroActionPerformed

    private void botonCobrosSeleccionadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCobrosSeleccionadosActionPerformed
        /*
        if(jtableDetallePagos.getRowCount()==12){
            
            SqlPagos instanciaSqlPagos= new SqlPagos();
            String idPago= jtablePagos.getValueAt(0,0).toString();
            instanciaSqlPagos.eliminarPermanentementeRegistroPago(idPago);
            SqlPagosYdetallePagos pagos_Y_Detalle= new SqlPagosYdetallePagos();
            
            
            //registrarPagoYdetalleTipoAnual(String fkIdCliente,String tipoTarifa,String precioTarifa,
            //String tipoDescuento,String descuentoAplicado,String tipoPago,float descuentoAnual,float total,float deuda,String periodo,String fecha){
            
           String nombreCompleto=jLabelvalueCliente.getText();
           String domicilio=jlabelValueDomicilio.getText();
           String barrio=jlabelValueBarrio.getText();
           String tipoPago=jtablePagos.getValueAt(0,5).toString();
           String periodo=jtablePagos.getValueAt(0,10).toString();
           String tipoTarifa=jtablePagos.getValueAt(0,1).toString();
           float precioTarifaAnual=Float.parseFloat(jtablePagos.getValueAt(0, 2).toString());
           String tipoDescuento=jtablePagos.getValueAt(0,3).toString();
           float precioDescuentoMensual=Float.parseFloat(jtablePagos.getValueAt(0,4).toString());
           String descuentoAnual=jtablePagos.getValueAt(0,6).toString();
           
            pagos_Y_Detalle.registrarPagoYdetalleTipoAnual(idCliente,tipoTarifa,String.valueOf(precioTarifaAnual), 
                tipoDescuento, periodo, idPago, LEFT_ALIGNMENT, TOP_ALIGNMENT, TOP_ALIGNMENT, periodo, idPago);
            
        }*/
        /*---------VALIDACION DE ORDEN DE CHECKBOX------------*/
                int filas=jtableDetallePagos.getRowCount();
        int iteradorSeleccion=0;
        int iteradorFilas=0;
        for (int i = 0; i <filas; i++) {
            Object celdaSeleccion=jtableDetallePagos.getValueAt(i,5);
            if(celdaSeleccion!=null){//por defecto la celda se iniciliza como null 
                boolean seleccion=(boolean)celdaSeleccion;//hasta que se interactua con el checkBox la celda regresa un valor de true o false
                if(seleccion){
                    System.err.println("iteradorF---"+iteradorFilas+" iteradorSeleccion--"+iteradorSeleccion);
                    if(iteradorFilas!=iteradorSeleccion){
                        JOptionPane.showMessageDialog(rootPane,"orden incorrecto");
                        return;
                    } 
                   iteradorSeleccion++;
                }
            }
            iteradorFilas++;
        }
        
        if(iteradorSeleccion==0){
            JOptionPane.showMessageDialog(rootPane,"no selecciono ningun registro");
            return;
        }
        /*---------FIN DE VALIDACION DE CHECKBOX-----------------------------------------------*/
        SqlPagos pago = new SqlPagos();
        SqlDetallePagos detallePago=new SqlDetallePagos();
        MetodosTablas metodoTabla=new MetodosTablas();
        Imprimir imprimir=new Imprimir();
        
        
        String idPago=jtablePagos.getValueAt(0,0).toString();
        
        //String cadenaImporte=jtableDetallePagos.getValueAt(0,4).toString();
        //String cadenaPagoRecibido;
        //String fechaPago=LocalDate.now().toString();
        /*VARIABLES FALTANTES PARA GENERAR EL RECIBO */
        String nombreCompleto=jLabelvalueCliente.getText();
        String domicilio=jlabelValueDomicilio.getText();
        String barrio=jlabelValueBarrio.getText();
        String tipoPago=jtablePagos.getValueAt(0,5).toString();
        String periodo=jtablePagos.getValueAt(0,10).toString();
        String tipoTarifa=jtablePagos.getValueAt(0,1).toString();
        float precioTarifaAnual=Float.parseFloat(jtablePagos.getValueAt(0, 2).toString());
        String tipoDescuento=jtablePagos.getValueAt(0,3).toString();
        float precioDescuentoMensual=Float.parseFloat(jtablePagos.getValueAt(0,4).toString());
        String descuentoAnual=jtablePagos.getValueAt(0,6).toString();
        String fechaPago=LocalDate.now().toString();
        String idregistroInicial=jtableDetallePagos.getValueAt(0,0).toString();
        String idRegistroIterador="";
        
        String mesInicial=jtableDetallePagos.getValueAt(0,3).toString();
        String mesIterador="";
        float importeTotal=0;
        
        
        for (int i = 0; i <jtableDetallePagos.getRowCount(); i++) {
            idRegistroIterador=jtableDetallePagos.getValueAt(i,0).toString();
        }
        
        for (int i = 0; i <jtableDetallePagos.getRowCount(); i++) {
            
            if(metodoTabla.IsSelected(i, 5, jtableDetallePagos)){
                idRegistroIterador=jtableDetallePagos.getValueAt(i,0).toString();
                
                String cadenaImporte=jtableDetallePagos.getValueAt(i,4).toString();
                
                float cambio;
                float importe=0;
                mesIterador=jtableDetallePagos.getValueAt(i,3).toString();
                
                importe=Float.parseFloat(cadenaImporte);
                importeTotal+=importe;
                cambio=0;
                
                
                detallePago.cobrarImporteDetallePagos(idRegistroIterador,String.valueOf(importe),String.valueOf(cambio), 
                fechaPago,1);
                
                float totalTablaPagos;//esta variable representa el total a pagar de una factura, aplicado los descuentos
                float sumatoriaImportesPagadosTablaDetallePagos;
                float deuda;
           
                totalTablaPagos=pago.obtenerTotalPago(idPago);
                sumatoriaImportesPagadosTablaDetallePagos=detallePago.obtenerSumatoriaImportesPagadosTablaDetallePagos(idPago);
                deuda=totalTablaPagos-sumatoriaImportesPagadosTablaDetallePagos;
                pago.actualizarDeudaRegistroPago(idPago,sumatoriaImportesPagadosTablaDetallePagos, deuda);
                
                if(deuda==0){
                    pago.actualizarEstadoRegistroPago(idPago,1);
                    JOptionPane.showMessageDialog(null,"El cliente ha completado todos los pagos");
                }
                
            }
            
        }
        imprimir.imprimirComprobante(idPago,idregistroInicial+" - "+idRegistroIterador,nombreCompleto,domicilio,barrio,tipoPago,periodo,tipoTarifa,precioTarifaAnual, 
        tipoDescuento,precioDescuentoMensual, descuentoAnual,mesInicial+" - "+mesIterador, fechaPago,String.valueOf(importeTotal));
        this.dispose();

    }//GEN-LAST:event_botonCobrosSeleccionadosActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        for (int i = 0; i <jtableDetallePagos.getRowCount(); i++) {
            jtableDetallePagos.setValueAt(true, i, 5);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(VentanaPagos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaPagos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaPagos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaPagos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                VentanaPagos dialog = new VentanaPagos(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton botonCobrar;
    private javax.swing.JButton botonCobrosSeleccionados;
    private javax.swing.JButton botonDestruirRegistro;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    public static javax.swing.JLabel jLabelvalueCliente;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTableMesesPagados;
    private javax.swing.JTextArea jTextAreaInfo;
    private javax.swing.JLabel jlabelCliente;
    private javax.swing.JLabel jlabelDomicilio;
    public static javax.swing.JLabel jlabelValueBarrio;
    public static javax.swing.JLabel jlabelValueDomicilio;
    public static javax.swing.JLabel jlabelValueIdCliente;
    private javax.swing.JTable jtableDetallePagos;
    private javax.swing.JTable jtablePagos;
    private javax.swing.JLabel label_idCliente;
    // End of variables declaration//GEN-END:variables
}
