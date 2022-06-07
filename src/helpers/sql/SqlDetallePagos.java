/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers.sql;

import helpers.jtables.MetodosTablas;
import impresiones.Imprimir;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import sistemacheranaguapotable.VentanaPagos;
import sistemacheranaguapotable.bd.ConexionBd;

/**
 *
 * @author jafeth888
 */
public class SqlDetallePagos {
    ConexionBd cc= ConexionBd.obtenerInstancia();
    Connection cn= cc.conexion();
    
    public float obtenerSumatoriaImportesPagadosTablaDetallePagos(String idPago){
        String sql="SELECT SUM(importe) FROM `detalle_pagos` WHERE fk_id_pago='"+idPago+"' AND fk_id_estado_pago='1'";
        float total=0;    	 
	Statement st = null;
        ResultSet rs = null;	    
        try {
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            while(rs.next()){
            	
                total=rs.getFloat(1);
              
            }
           
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null,ex.getMessage(),"error en consultar sumatoriaImportes pagados detalle pagos", JOptionPane.ERROR_MESSAGE);
        }finally {
            try {
                if(st!=null)st.close();
                if(rs!=null)rs.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
	}
        
        return total;
    }
    /**
     *@param fkIdEstadoPago valores: 1 es 'pagado' 2 es 'en deuda'
     **/
    public boolean cobrarImporteDetallePagos(String idRegistro,String pagoRealizado,String cambioEntregado,String fechaPago,int fkIdEstadoPago,String idPago){
        PreparedStatement pst=null;
        Statement st = null;
        ResultSet rs = null;
        boolean success=true;//devolvemos valor booleano para determinar que demas tareas se sigan o no ejecutando
        try {
            /*desactivamos el autocommit para ejecutar las instrucciones de pago y detalle en un solo bloque
            por ACID en base de datos
            */
            cn.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error al desactivar auto-commit en registrar pago y detalle tipo Anual",JOptionPane.ERROR_MESSAGE);
        }
        try {
            pst = cn.prepareStatement("UPDATE detalle_pagos SET pago_realizado=?,"
                    + "cambio_entregado=?,fecha_pago=?, fk_id_estado_pago=? WHERE id_registro=?");
            pst.setString(1, pagoRealizado);
            pst.setString(2, cambioEntregado);
            pst.setString(3, fechaPago);
            pst.setInt(4,fkIdEstadoPago);
            pst.setString(5, idRegistro);
            pst.executeUpdate();
            
            float totalTablaPagos;//esta variable representa el total a pagar de una factura, aplicado los descuentos
            float sumatoriaImportesPagadosTablaDetallePagos;
            float deuda;
            String sqlTotalTablaPagos="SELECT total FROM pagos where id_pago='"+idPago+"'";
            st = cn.createStatement();
            rs = st.executeQuery(sqlTotalTablaPagos);
            if(rs.next()){	
                totalTablaPagos=rs.getFloat(1);
            }else{
                throw new SQLException("opereacion necesaria: no se pudo obtener el total del pago");
            }
            String sqlSumatoriaImportesPagados="SELECT SUM(importe) FROM `detalle_pagos` WHERE fk_id_pago='"+idPago+"' AND fk_id_estado_pago='1'";
            st = cn.createStatement();
            rs = st.executeQuery(sqlSumatoriaImportesPagados);
            if(rs.next()){
                sumatoriaImportesPagadosTablaDetallePagos=rs.getFloat(1);
            }else{
               throw new SQLException("operacion necesaria: no se pudo obtenero el total de los importes"); 
            }

            deuda=totalTablaPagos-sumatoriaImportesPagadosTablaDetallePagos;

            //ACTUALIZAMOS LA DEUDA DEL REGISTRO PAGO
            pst = cn.prepareStatement("UPDATE pagos SET total_pagado=?, "
            + "deuda=? WHERE id_pago=?");
            pst.setFloat(1,sumatoriaImportesPagadosTablaDetallePagos);
            pst.setFloat(2,deuda);
            pst.setString(3,idPago);
            pst.executeUpdate();

            if(deuda==0){
                pst = cn.prepareStatement("UPDATE pagos SET fk_id_estado_pago=? "
                + " WHERE id_pago=?");
                pst.setInt(1,fkIdEstadoPago);
                pst.setString(2,idPago);
                pst.executeUpdate();
                JOptionPane.showMessageDialog(null,"El cliente ha completado todos los pagos");
            }
            cn.commit();
            
        } catch (SQLException e) {
            success=false;
            try {
                cn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null,e.getMessage(),"Error Rollback al facturar pagos",JOptionPane.ERROR_MESSAGE);
            }
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,e.getMessage(),"error al cobrar importe", JOptionPane.ERROR_MESSAGE);
        }finally{
            
            try {
                cn.setAutoCommit(true);
                if(pst!=null) pst.close();
                if(st!=null)st.close();
                if(rs!=null)rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return success;
    }
    
    
    public void cobrarVariosImportesDetallePagos(String idPago,String fechaPago,int fkIdEstadoPago,
    JTable tablaDetallePagos,JTable tablaPagos){
        PreparedStatement pst=null;
        Statement st = null;
        ResultSet rs = null;
        MetodosTablas metodoTabla=new MetodosTablas();
        Imprimir imprimir=new Imprimir();
        
        /*VARIABLES FALTANTES PARA GENERAR EL RECIBO */
        String nombreCompleto=VentanaPagos.jLabelvalueCliente.getText();
        String domicilio=VentanaPagos.jlabelValueDomicilio.getText();
        String barrio=VentanaPagos.jlabelValueBarrio.getText();
        String tipoPago=tablaPagos.getValueAt(0,5).toString();
        String periodo=tablaPagos.getValueAt(0,10).toString();
        String tipoTarifa=tablaPagos.getValueAt(0,1).toString();
        float precioTarifaAnual=Float.parseFloat(tablaPagos.getValueAt(0, 2).toString());
        String tipoDescuento=tablaPagos.getValueAt(0,3).toString();
        float precioDescuentoMensual=Float.parseFloat(tablaPagos.getValueAt(0,4).toString());
        String descuentoAnual=tablaPagos.getValueAt(0,6).toString();
        String idregistroInicial=tablaDetallePagos.getValueAt(0,0).toString();
        
        String mesInicial=tablaDetallePagos.getValueAt(0,3).toString();
        String mesIterador="";
        float importeTotal=0;
        String idRegistroIterador="";
        
        try {
            /*desactivamos el autocommit para ejecutar las instrucciones de pago y detalle en un solo bloque
            por ACID en base de datos
            */
            cn.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error al desactivar auto-commit en registrar pago y detalle tipo Anual",JOptionPane.ERROR_MESSAGE);
        }
        
        try {
            for (int i = 0; i<tablaDetallePagos.getRowCount(); i++) {
                if(metodoTabla.IsSelected(i, 5,tablaDetallePagos)){
                    System.out.println("helpers.sql.SqlDetallePagos.cobrarVariosImportesDetallePagos()");
                    idRegistroIterador=tablaDetallePagos.getValueAt(i,0).toString();

                    String cadenaImporte=tablaDetallePagos.getValueAt(i,4).toString();

                    float cambio;
                    float importe=0;
                    mesIterador=tablaDetallePagos.getValueAt(i,3).toString();

                    importe=Float.parseFloat(cadenaImporte);
                    importeTotal+=importe;
                    cambio=0;

                    pst = cn.prepareStatement("UPDATE detalle_pagos SET pago_realizado=?,"
                    + "cambio_entregado=?,fecha_pago=?, fk_id_estado_pago=? WHERE id_registro=?");
                    pst.setString(1,String.valueOf(importe));
                    pst.setString(2, String.valueOf(cambio));
                    pst.setString(3, fechaPago);
                    pst.setInt(4,fkIdEstadoPago);
                    pst.setString(5, idRegistroIterador);
                    pst.executeUpdate();

                    float totalTablaPagos;//esta variable representa el total a pagar de una factura, aplicado los descuentos
                    float sumatoriaImportesPagadosTablaDetallePagos;
                    float deuda;
                    String sqlTotalPagos="SELECT total FROM pagos where id_pago='"+idPago+"'";
                    st = cn.createStatement();
                    rs = st.executeQuery(sqlTotalPagos);
                    if(rs.next()){
                         totalTablaPagos=rs.getFloat(1);
                    }else{
                        throw new SQLException("operacion necesaria: no se pudo obtener el total del pago");
                    }
                    String sqlSumatoriaImportesPagados="SELECT SUM(importe) FROM `detalle_pagos` WHERE fk_id_pago='"+idPago+"' AND fk_id_estado_pago='1'";
                    st = cn.createStatement();
                    rs = st.executeQuery(sqlSumatoriaImportesPagados);
                    if(rs.next()){
                        sumatoriaImportesPagadosTablaDetallePagos=rs.getFloat(1);
                    }else{
                       throw new SQLException("operacion necesaria: no se pudo obtenero el total de los importes"); 
                    }
                    
                    deuda=totalTablaPagos-sumatoriaImportesPagadosTablaDetallePagos;
                    
                    //ACTUALIZAMOS LA DEUDA DEL REGISTRO PAGO
                    pst = cn.prepareStatement("UPDATE pagos SET total_pagado=?, "
                    + "deuda=? WHERE id_pago=?");
                    pst.setFloat(1,sumatoriaImportesPagadosTablaDetallePagos);
                    pst.setFloat(2,deuda);
                    pst.setString(3,idPago);
                    pst.executeUpdate();
                    
                    if(deuda==0){
                        pst = cn.prepareStatement("UPDATE pagos SET fk_id_estado_pago=? "
                        + " WHERE id_pago=?");
                        pst.setInt(1,fkIdEstadoPago);
                        pst.setString(2,idPago);
                        pst.executeUpdate();
                        JOptionPane.showMessageDialog(null,"El cliente ha completado todos los pagos");
                    }  
                }
            }
            cn.commit();
            imprimir.imprimirComprobante(idPago,idregistroInicial+" - "+idRegistroIterador,nombreCompleto,domicilio,barrio,tipoPago,periodo,tipoTarifa,precioTarifaAnual, 
            tipoDescuento,precioDescuentoMensual, descuentoAnual,mesInicial+" - "+mesIterador, fechaPago,String.valueOf(importeTotal));
        
        }catch (SQLException e) {
             try {
                cn.rollback();
                
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null,e.getMessage(),"----",JOptionPane.ERROR_MESSAGE);
            }
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,e.getMessage(),"error al cobrar importe", JOptionPane.ERROR_MESSAGE);
        }finally{
            try {
                cn.setAutoCommit(true);
                if(pst!=null)pst.close();
                if(st!=null)st.close();
                if(rs!=null)rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(SqlDetallePagos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    } 

}
