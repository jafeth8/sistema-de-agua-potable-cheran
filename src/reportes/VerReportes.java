/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportes;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import sistemacheranaguapotable.bd.ConexionBd;

/**
 *
 * @author jafeth888
 */
public class VerReportes extends javax.swing.JFrame {
    ConexionBd cc= ConexionBd.obtenerInstancia();
    Connection cn= cc.conexion();
    String fechaInicio,fechaFinal;
    String rezagados="";
    /**
     * Creates new form VerReportes
     */
    public VerReportes() {
        initComponents();
    }
    
    public void mostrarReportes(ArrayList<String> camposSql,String campoBase,String sql ){
        ArrayList<String> campos=camposSql;
        //System.out.println("tamanio "+campos.size());
        campos.add(campoBase);
        DefaultTableModel modelo=new DefaultTableModel();
        for (int i = 0; i < campos.size(); i++) {
            if(campos.get(i).equals("mes")){
                modelo.addColumn("mes que se pago");
            }else if(campos.get(i).equals("importe")){
                modelo.addColumn("importe pagado");
            }
            else{
                modelo.addColumn(campos.get(i));
            }
            
        }
        //modelo.addColumn(campoBase);
        tablaReportes.setModel(modelo);
        
        String []datos = new String [campos.size()];
        
        Statement st=null;
        ResultSet rs=null;
        try {
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            while(rs.next()){
                for (int i = 0; i <campos.size(); i++) {
                    /*la condicional es para evitar que el campo periodo de un formato del tipo: "yyyy-mm-dd
                    el objetivo es mostro solo: "yyyy" (año), con la sentencia rs.getInt se logra tal objetivo
                    "*/
                    if(campos.get(i).equals("periodo")){
                        datos[i]=""+rs.getInt(i+1);
                    }else{
                       datos[i]=rs.getString(i+1);//rs.getStrig no es bueno obteniendo campos del tipo "year" de una DB siempre devuelve un valor de "yyyy-mm-dd" y deberia ser solo "yyyy" (año) 
                    }
                }
                modelo.addRow(datos);
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(rootPane,"por favor intente nuevamente: "+ex.getMessage(),"Error al consultar reportes",JOptionPane.ERROR_MESSAGE);
        }finally{
            try {
                if(st!=null)st.close();
                if(rs!=null)rs.close();
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }
    
    public void mostrarTotalImporte(String fechaInicio,String fechaFinal,String filtroRezagos){
        //el parametro filtroRezagos: es para realizar bien la sumatoria de importes
        Statement st=null;
        ResultSet rs=null;
        String sql="SELECT SUM(importe) FROM detalle_pagos JOIN pagos ON fk_id_pago=pagos.id_pago WHERE fecha_pago BETWEEN '"+fechaInicio+"' AND '"+fechaFinal+"' "+filtroRezagos+"";
        try {
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            if(rs.next()){
                jLabelTotal.setText(rs.getString(1));
            }
            jTextArea1.setText(sql);
        } catch (SQLException ex) {
            ex.printStackTrace();
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        dateChooserInicioFecha = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        dateChooserFechaFinal = new com.toedter.calendar.JDateChooser();
        jPanel1 = new javax.swing.JPanel();
        jCheckBoxNombre = new javax.swing.JCheckBox();
        jCheckBoxDomicilio = new javax.swing.JCheckBox();
        jCheckBoxPeriodo = new javax.swing.JCheckBox();
        jCheckBoxTipoPago = new javax.swing.JCheckBox();
        jCheckBoxFechaPago = new javax.swing.JCheckBox();
        jCheckBoxApellidoPaterno = new javax.swing.JCheckBox();
        jCheckBoxApellidoMaterno = new javax.swing.JCheckBox();
        jCheckBoxBarrio = new javax.swing.JCheckBox();
        jCheckBoxMesDePago = new javax.swing.JCheckBox();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaReportes = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        textArea = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        jLabelTotal = new javax.swing.JLabel();
        jYearChooserPeriodoActual = new com.toedter.calendar.JYearChooser();
        jLabel4 = new javax.swing.JLabel();
        jRadioButtonRezagos = new javax.swing.JRadioButton();
        jRadioButtonNormal = new javax.swing.JRadioButton();
        jRadioButtonRezagosAndNormal = new javax.swing.JRadioButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Corte de caja");

        jLabel1.setText("de:");

        jLabel2.setText("hasta:");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos a mostrar", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        jPanel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jCheckBoxNombre.setText("nombre");

        jCheckBoxDomicilio.setText("domicilio");

        jCheckBoxPeriodo.setText("periodo");

        jCheckBoxTipoPago.setText("Tipo de pago ");

        jCheckBoxFechaPago.setText("fecha del pago");

        jCheckBoxApellidoPaterno.setText("Apellido paterno");

        jCheckBoxApellidoMaterno.setText("Apellido materno");

        jCheckBoxBarrio.setText("barrio");

        jCheckBoxMesDePago.setText("Mes que se pago");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCheckBoxNombre)
                    .addComponent(jCheckBoxPeriodo))
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCheckBoxMesDePago)
                    .addComponent(jCheckBoxApellidoPaterno))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jCheckBoxApellidoMaterno)
                        .addGap(18, 18, 18)
                        .addComponent(jCheckBoxDomicilio)
                        .addGap(18, 18, 18)
                        .addComponent(jCheckBoxBarrio))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jCheckBoxTipoPago, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jCheckBoxFechaPago)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBoxNombre)
                    .addComponent(jCheckBoxApellidoPaterno)
                    .addComponent(jCheckBoxApellidoMaterno)
                    .addComponent(jCheckBoxDomicilio)
                    .addComponent(jCheckBoxBarrio))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBoxPeriodo)
                    .addComponent(jCheckBoxTipoPago)
                    .addComponent(jCheckBoxFechaPago)
                    .addComponent(jCheckBoxMesDePago))
                .addGap(0, 61, Short.MAX_VALUE))
        );

        jButton1.setText("mostrar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        tablaReportes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tablaReportes);

        textArea.setColumns(20);
        textArea.setRows(5);
        jScrollPane2.setViewportView(textArea);

        jLabel3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel3.setText("total:");

        jLabelTotal.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabelTotal.setText("no value");

        jYearChooserPeriodoActual.setEnabled(false);

        jLabel4.setText("periodo actual:");

        buttonGroup1.add(jRadioButtonRezagos);
        jRadioButtonRezagos.setText("rezagos");

        buttonGroup1.add(jRadioButtonNormal);
        jRadioButtonNormal.setText("normal");

        buttonGroup1.add(jRadioButtonRezagosAndNormal);
        jRadioButtonRezagosAndNormal.setSelected(true);
        jRadioButtonRezagosAndNormal.setText("rezagos y normal");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane3.setViewportView(jTextArea1);

        jButton2.setText("crear excel");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane3)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dateChooserInicioFecha, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dateChooserFechaFinal, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                        .addGap(729, 729, 729))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(jYearChooserPeriodoActual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jRadioButtonRezagos)
                        .addGap(18, 18, 18)
                        .addComponent(jRadioButtonNormal)
                        .addGap(18, 18, 18)
                        .addComponent(jRadioButtonRezagosAndNormal)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jYearChooserPeriodoActual, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(dateChooserFechaFinal, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(dateChooserInicioFecha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jRadioButtonRezagos)
                    .addComponent(jRadioButtonNormal)
                    .addComponent(jRadioButtonRezagosAndNormal))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabelTotal)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        
        ArrayList<String> camposSql = new ArrayList<String>();
        if(jCheckBoxNombre.isSelected()){
           camposSql.add("nombre");
        }
        if(jCheckBoxApellidoPaterno.isSelected()) camposSql.add("apellido_paterno");
        if(jCheckBoxApellidoMaterno.isSelected()) camposSql.add("apellido_materno");
        if(jCheckBoxDomicilio.isSelected()) camposSql.add("domicilio"); 
        if(jCheckBoxBarrio.isSelected()) camposSql.add("barrio");
        if(jCheckBoxPeriodo.isSelected()) camposSql.add("periodo");
        if(jCheckBoxMesDePago.isSelected()) camposSql.add("mes");
        if(jCheckBoxTipoPago.isSelected()) camposSql.add("tipo_pago");
        if(jCheckBoxFechaPago.isSelected()) camposSql.add("fecha_pago");
        
        String campoSqlBase="importe";
        String campos="";
        String sql="";
        
        for (int i = 0; i < camposSql.size(); i++) {
            campos+=camposSql.get(i)+",";
        }
        
        
        /*-----------bloque para: mostrar rezagados, mormal o normal y rezagados---------------*/
        String filtroRezagos;
        int periodoActual=jYearChooserPeriodoActual.getYear();
        if(jRadioButtonRezagos.isSelected()){
            filtroRezagos=" AND periodo<"+periodoActual+"";
            rezagados="rezagados";
        }else if(jRadioButtonNormal.isSelected()){
           filtroRezagos=" AND periodo="+periodoActual+"";
           rezagados="normal";
        }else if(jRadioButtonRezagosAndNormal.isSelected()){
            filtroRezagos="";
            rezagados="normal y rezagados";
        }
        else{
            filtroRezagos="";
        }
        /*------------Fin de bloque para: mostrar rezagados, mormal o normal y rezagados-----------*/
        try {
            SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
            fechaInicio=formatoFecha.format(dateChooserInicioFecha.getDate());
            fechaFinal=formatoFecha.format(dateChooserFechaFinal.getDate());
            
            sql="SELECT "+campos+""+campoSqlBase+" FROM detalle_pagos JOIN pagos ON fk_id_pago=pagos.id_pago "
                + "JOIN clientes ON pagos.fk_id_cliente=clientes.id_cliente WHERE fecha_pago BETWEEN '"+fechaInicio+"' AND '"+fechaFinal+"' "+filtroRezagos+"";
            mostrarReportes(camposSql,campoSqlBase,sql);
            mostrarTotalImporte(fechaInicio, fechaFinal,filtroRezagos);//el parametro filtroRezagos: es para realizar correctamente la sumatoria de importes
            
        } catch (NullPointerException vacio) {
            System.err.println(vacio.getMessage());
            JOptionPane.showMessageDialog(rootPane,"La fecha no es valida","Fecha invalida",JOptionPane.ERROR_MESSAGE);
        }catch(Exception e){
            e.printStackTrace();
        }

        
        textArea.setText(sql);
        //mostrarReportes(camposSql,campoSqlBase);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        Excel excel=new Excel();
        //excel.crearReporte();
        int numeCol=tablaReportes.getColumnCount();
        int filas=tablaReportes.getRowCount();
        //JOptionPane.showMessageDialog(rootPane,"filas: "+filas);
        if(filas>0){
            excel.crearReporte(tablaReportes,jLabelTotal.getText(),fechaInicio,fechaFinal,rezagados);
        }else{
            JOptionPane.showMessageDialog(rootPane,"no hay datos que reportar");
        }
        /*
        for (int i = 0; i <numeCol; i++) {
            System.out.println(tablaReportes.getColumnName(i));
        }*/
        
        
    }//GEN-LAST:event_jButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(VerReportes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VerReportes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VerReportes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VerReportes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VerReportes().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private com.toedter.calendar.JDateChooser dateChooserFechaFinal;
    private com.toedter.calendar.JDateChooser dateChooserInicioFecha;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JCheckBox jCheckBoxApellidoMaterno;
    private javax.swing.JCheckBox jCheckBoxApellidoPaterno;
    private javax.swing.JCheckBox jCheckBoxBarrio;
    private javax.swing.JCheckBox jCheckBoxDomicilio;
    private javax.swing.JCheckBox jCheckBoxFechaPago;
    private javax.swing.JCheckBox jCheckBoxMesDePago;
    private javax.swing.JCheckBox jCheckBoxNombre;
    private javax.swing.JCheckBox jCheckBoxPeriodo;
    private javax.swing.JCheckBox jCheckBoxTipoPago;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabelTotal;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JRadioButton jRadioButtonNormal;
    private javax.swing.JRadioButton jRadioButtonRezagos;
    private javax.swing.JRadioButton jRadioButtonRezagosAndNormal;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea jTextArea1;
    private com.toedter.calendar.JYearChooser jYearChooserPeriodoActual;
    private javax.swing.JTable tablaReportes;
    private javax.swing.JTextArea textArea;
    // End of variables declaration//GEN-END:variables
}
