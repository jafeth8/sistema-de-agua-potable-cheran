/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemacheranaguapotable;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import sistemacheranaguapotable.bd.ConexionBd;

/**
 *
 * @author jafeth888
 */
public class ConsultaPago extends javax.swing.JFrame {
    ConexionBd cc= ConexionBd.obtenerInstancia();
    Connection cn= cc.conexion();
    /**
     * Creates new form ConsultaPago
     */
    public ConsultaPago() {
        initComponents();
    }
    public void mostrarPago(String idPago){
        DefaultTableModel modelo= new DefaultTableModel();
        modelo.addColumn("Tipo tarifa");
        modelo.addColumn("Precio tarifa");
        modelo.addColumn("Tipo descuento");
        modelo.addColumn("descuento");
        modelo.addColumn("Tipo pago");
        modelo.addColumn("Descuento anual");
        modelo.addColumn("Total a pagar");
        modelo.addColumn("Total pagado");
        modelo.addColumn("deuda");
        modelo.addColumn("ciclo");
        modelo.addColumn("estado del pago");
        
        tablaPago.setModel(modelo);
        
        String sql="";
        sql="SELECT pagos.id_pago,clientes.nombre,clientes.apellido_paterno,clientes.apellido_materno,pagos.tipo_tarifa,"
            + "pagos.precio_tarifa,pagos.tipo_descuento,pagos.descuento,pagos.tipo_pago,pagos.descuento_anual,pagos.total,pagos.total_pagado,pagos.deuda,pagos.periodo,c_estado_pagos.descripcion "
            + "FROM pagos JOIN clientes ON pagos.fk_id_cliente=clientes.id_cliente JOIN c_estado_pagos ON pagos.fk_id_estado_pago=c_estado_pagos.id_estado_pago WHERE pagos.id_pago='"+idPago+"'";
        String []datos = new String [11];
        
        Statement st = null;
        ResultSet rs = null;
        
        try {
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            
            if(rs.next()){
                labelValueIdPago.setText(rs.getString(1));
                labelValueCliente.setText(rs.getString(2)+" "+rs.getString(3)+" "+rs.getString(4));
                datos[0]=rs.getString(5);
                datos[1]=rs.getString(6);
                datos[2]=rs.getString(7);
                datos[3]=rs.getString(8);
                datos[4]=rs.getString(9);
                datos[5]=rs.getString(10);
                datos[6]=rs.getString(11);
                datos[7]=rs.getString(12);
                datos[8]=rs.getString(13);
                datos[9]=rs.getString(14);
                datos[10]=rs.getString(15);
                modelo.addRow(datos);
                mostrarDetallePago(rs.getString(1));
            }else{
                JOptionPane.showMessageDialog(rootPane,"no existe pago con el codigo: "+idPago);
                tablaPago.setModel(modelo);
                tablaDetallePago.setModel(new DefaultTableModel());
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }finally{
            try {
                if(st!=null)st.close();
                if(rs!=null)rs.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public void mostrarDetallePago(String idPago){
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Numero de factura");
        modelo.addColumn("mes");
        modelo.addColumn("Importe");
        modelo.addColumn("Fecha de pago");
        modelo.addColumn("Estado");
        
        tablaDetallePago.setModel(modelo);
        
        String sql="";
        sql="SELECT id_registro,mes,importe,fecha_pago,c_estado_pagos.descripcion FROM detalle_pagos JOIN c_estado_pagos "
                + "ON detalle_pagos.fk_id_estado_pago=c_estado_pagos.id_estado_pago WHERE fk_id_pago='"+idPago+"'";
        
        Statement st=null;
        ResultSet rs=null;
        
        String [] datos=new String [5];
        
        try {
            st=cn.createStatement();
            rs=st.executeQuery(sql);
            while(rs.next()){
                datos[0]=rs.getString(1);
                datos[1]=rs.getString(2);
                datos[2]=rs.getString(3);
                datos[3]=rs.getString(4);
                datos[4]=rs.getString(5);
                modelo.addRow(datos);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showConfirmDialog(null,"error: "+ex.getMessage(),"No se pudo consultar el detalle del pago, intentelo nuevamente.",JOptionPane.WARNING_MESSAGE);
        }finally{
            try {
                if(st!=null)st.close();
                if(rs!=null)rs.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextFieldCodigoPago = new javax.swing.JTextField();
        botonConsultar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaPago = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaDetallePago = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        labelValueIdPago = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        labelValueCliente = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(51, 51, 255), new java.awt.Color(51, 51, 255), new java.awt.Color(51, 51, 255), new java.awt.Color(51, 51, 255)));

        jLabel1.setText("Codigo del pago");

        botonConsultar.setText("Consultar");
        botonConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonConsultarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextFieldCodigoPago))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(botonConsultar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldCodigoPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonConsultar))
                .addContainerGap(40, Short.MAX_VALUE))
        );

        tablaPago.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tablaPago);

        tablaDetallePago.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tablaDetallePago);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("id pago:");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Cliente:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addComponent(jScrollPane2)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelValueIdPago, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(labelValueCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 456, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(labelValueIdPago, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(labelValueCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(46, 46, 46)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonConsultarActionPerformed
        // TODO add your handling code here:
        mostrarPago(jTextFieldCodigoPago.getText());
        
    }//GEN-LAST:event_botonConsultarActionPerformed

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
            java.util.logging.Logger.getLogger(ConsultaPago.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ConsultaPago.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ConsultaPago.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ConsultaPago.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ConsultaPago().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonConsultar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextFieldCodigoPago;
    private javax.swing.JLabel labelValueCliente;
    private javax.swing.JLabel labelValueIdPago;
    private javax.swing.JTable tablaDetallePago;
    private javax.swing.JTable tablaPago;
    // End of variables declaration//GEN-END:variables
}
