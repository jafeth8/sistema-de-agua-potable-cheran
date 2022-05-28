/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package importacion_datos;

import helpers.sql.SqlDetallePagos;
import helpers.sql.SqlPagoMinimo;
import helpers.sql.SqlPagosYdetallePagos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import static sistemacheranaguapotable.FacturaNueva.campoDescuento;
import static sistemacheranaguapotable.FacturaNueva.campoPrecioTarifa;
import sistemacheranaguapotable.bd.ConexionBd;

/**
 *
 * @author jafeth888
 */
public class ProcesarDatos extends javax.swing.JFrame {
    ConexionBd cc= ConexionBd.obtenerInstancia();
    Connection cn= cc.conexion();
    /**
     * Creates new form ProcesarDatos
     */
    public ProcesarDatos() {
        initComponents();
        mostrarDatosProcesados();
    }
    
    public void mostrarDatosProcesados(){
        DefaultTableModel modelo= new DefaultTableModel();
 
        modelo.addColumn("Numero cliente");
        modelo.addColumn("Nombre");
        modelo.addColumn("Ultimo año pagado");
        modelo.addColumn("ultimo mes pagado");
        modelo.addColumn("Año Actual");
        modelo.addColumn("fecha mes pagado");
        String sql="";
        //table.setModel(modelo);
        tablaDatos.setModel(modelo);
  
        //SELECT id_pago,tipo_tarifa,precio_tarifa,tipo_descuento,descuento,tipo_pago,descuento_anual,total,total_pagado,deuda,periodo FROM `pagos` WHERE fk_id_cliente = 1 AND estado='en deuda'
        sql="SELECT numero_cliente,nombre,YEAR(mes_pagado),MONTH(mes_pagado),YEAR(fecha_actual),mes_pagado FROM datos_procesados_importacion";
       
        Object []datos = new Object [6];
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

        botonProcesarDatos = new javax.swing.JButton();
        botonGenerarFacturas = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaDatos = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        botonProcesarDatos.setText("Procesar Datos");
        botonProcesarDatos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonProcesarDatosActionPerformed(evt);
            }
        });

        botonGenerarFacturas.setText("Generar Facturas");
        botonGenerarFacturas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonGenerarFacturasActionPerformed(evt);
            }
        });

        tablaDatos.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tablaDatos);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 575, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(botonProcesarDatos)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(botonGenerarFacturas)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonProcesarDatos)
                    .addComponent(botonGenerarFacturas))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonProcesarDatosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonProcesarDatosActionPerformed
        // TODO add your handling code here:
        Statement st = null;
        ResultSet rs = null;
        String sqlDatosImportacion="SELECT numero_cliente,nombre,fecha_pago,mes_pagado FROM datos_importacion";
        try {
            st = cn.createStatement();
            rs=st.executeQuery(sqlDatosImportacion);
            PreparedStatement ps;
            String sqlDatosProcesadosImportacion="INSERT INTO datos_procesados_importacion (numero_cliente,nombre,mes_pagado,fecha_actual) VALUES(?,?,?,?)";
            String numeroCliente,nombre,mes_pagado;
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            Date date;
            while(rs.next()){
                numeroCliente=rs.getString(1);
                nombre=rs.getString(2);
                mes_pagado=rs.getString(4);
                System.out.print("mes pago original--"+mes_pagado);
                
                date = formato.parse(mes_pagado);
                formato.applyPattern("yyyy/MM/dd");
                String nuevoFormato=formato.format(date);
                System.err.print("formato--- "+nuevoFormato);
                System.out.println();
                formato.applyPattern("dd/MM/yyyy");
                
                ps=cn.prepareStatement(sqlDatosProcesadosImportacion);
                ps.setString(1,numeroCliente);
                ps.setString(2,nombre);
                ps.setString(3,nuevoFormato);
                ps.setString(4,LocalDate.now().toString());
                ps.execute();    
            }
            JOptionPane.showMessageDialog(null,"Listo");
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ParseException ex) {
            ex.printStackTrace();
        } 
    }//GEN-LAST:event_botonProcesarDatosActionPerformed

    private void botonGenerarFacturasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonGenerarFacturasActionPerformed
       
        ClientesSql clienteSql;
        Facturas factura=new Facturas();
        SqlPagoMinimo instanciaPagoMinimo=new SqlPagoMinimo();
        HashMap<String, String> infoCliente;
        int filas=tablaDatos.getRowCount();
        for (int i = 0; i <filas; i++) {
            //-----todas las facturas se registraran de tipo mensual
            String numeroCliente=tablaDatos.getValueAt(i,0).toString();
            int lastYear=Integer.parseInt(tablaDatos.getValueAt(i,2).toString());
            int lastMonth=Integer.parseInt(tablaDatos.getValueAt(i,3).toString());
            int currentYear=Integer.parseInt(tablaDatos.getValueAt(i,4).toString());
            int numeroFacturas=currentYear-lastYear;
            String fechaUltimoPago=tablaDatos.getValueAt(i,5).toString();//solo se ocupa una vez en el metodo 'registrarPagoYdetalleTipoMensualInicial()'
            clienteSql=new ClientesSql();
            /*------------datos para generar factura----------------------------------*/
            infoCliente=clienteSql.obtenerDatosClientes(numeroCliente);
            String idCliente=infoCliente.get("idCliente");
            String tipoDescuento=infoCliente.get("tipoDescuento");
            float descuento=Float.parseFloat(infoCliente.get("descuentoMensual"));
            String tipoTarifa=infoCliente.get("tipoTarifa");
            float tarifa=Float.parseFloat(infoCliente.get("tarifaAnual"));
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
            int periodo=lastYear;
            /*---------fin de datos para generar factuta------------------------------*/
            String tipoPago="Mensual";

            
            for (int j = 0; j <=numeroFacturas; j++) {
                
                if(j==0){
                    factura.registrarPagoYdetalleTipoMensualInicial(idCliente, tipoTarifa,infoCliente.get("tarifaAnual"), 
                    tipoDescuento,infoCliente.get("descuentoMensual"), tipoPago, descuentoAnual, total,total,
                    String.valueOf(periodo), importeMes,fecha,lastMonth,fechaUltimoPago);//el parametro total se repite porque inicialmente el total a pagar es la deuda
                    periodo++;
                    continue;
                }
                
                factura.registrarPagoYdetalleTipoMensual(idCliente, tipoTarifa,infoCliente.get("tarifaAnual"), 
                tipoDescuento,infoCliente.get("descuentoMensual"), tipoPago, descuentoAnual, total,total,
                String.valueOf(periodo), importeMes,fecha);//el parametro total se repite porque inicialmente el total a pagar es la deuda
                periodo++;
            }
            clienteSql=null;
            
        }
        JOptionPane.showMessageDialog(null,"Listo!!");
    }//GEN-LAST:event_botonGenerarFacturasActionPerformed

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
            java.util.logging.Logger.getLogger(ProcesarDatos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ProcesarDatos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ProcesarDatos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ProcesarDatos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ProcesarDatos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonGenerarFacturas;
    private javax.swing.JButton botonProcesarDatos;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaDatos;
    // End of variables declaration//GEN-END:variables
}
