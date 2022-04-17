/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
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
    public void cobrarImporteDetallePagos(String idRegistro,String pagoRealizado,String cambioEntregado,String fechaPago,int fkIdEstadoPago){
        PreparedStatement pst=null;
        try {
            pst = cn.prepareStatement("UPDATE detalle_pagos SET pago_realizado=?,"
                    + "cambio_entregado=?,fecha_pago=?, fk_id_estado_pago=? WHERE id_registro=?");
            pst.setString(1, pagoRealizado);
            pst.setString(2, cambioEntregado);
            pst.setString(3, fechaPago);
            pst.setInt(4,fkIdEstadoPago);
            pst.setString(5, idRegistro);
            pst.executeUpdate();
            cn.commit();
        } catch (SQLException e) {
            try {
                // TODO Auto-generated catch block
                cn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null,e.getMessage(),"error en rollback (cobrar importe)", JOptionPane.ERROR_MESSAGE);
            }
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,e.getMessage(),"error al cobrar importe", JOptionPane.ERROR_MESSAGE);
        }finally{
            try {
                if(pst!=null) pst.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    
}
