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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import sistemacheranaguapotable.bd.ConexionBd;

/**
 *
 * @author jafeth888
 */
public class SqlPagos {
    
    ConexionBd cc= ConexionBd.obtenerInstancia();
    Connection cn= cc.conexion();
       
    public float obtenerTotalPago(String idPago){
        
        String sql="SELECT total FROM pagos where id_pago='"+idPago+"'";
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
            JOptionPane.showMessageDialog(null,ex.getMessage(),"error en consulta (obtner total pago)", JOptionPane.ERROR_MESSAGE);
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
    
    public void actualizarDeudaRegistroPago(String idPago,float totalPagado, float deuda){
         PreparedStatement pst=null;
        try {
            pst = cn.prepareStatement("UPDATE pagos SET total_pagado=?, "
                    + "deuda=? WHERE id_pago=?");
            pst.setFloat(1,totalPagado);
            pst.setFloat(2,deuda);
            pst.setString(3,idPago);
  
            pst.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,e.getMessage(),"error al actualizar la deuda del pago", JOptionPane.ERROR_MESSAGE);
        }finally{
             try {
                if(pst!=null) pst.close();
             } catch (SQLException ex) {
                ex.printStackTrace();
             }
        }
    }
    
    /**
     *@param idPago :idpago
     *@param fkIdEstadoPago valores: 1 es 'pagado' 2 es 'en deuda'
     **/
    public void actualizarEstadoRegistroPago(String idPago,int fkIdEstadoPago){
         PreparedStatement pst=null;
        try {
            pst = cn.prepareStatement("UPDATE pagos SET fk_id_estado_pago=? "
                    + " WHERE id_pago=?");
            pst.setInt(1,fkIdEstadoPago);
            pst.setString(2,idPago);
            
  
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,e.getMessage(),"error al actualizar estado del pago", JOptionPane.ERROR_MESSAGE);
        }finally{
             try {
                if(pst!=null)pst.close();
             } catch (SQLException ex) {
                ex.printStackTrace();
             }
        }
    }
    
    public void eliminarPermanentementeRegistroPago(String idPago){
        PreparedStatement pst=null;
        
        try {
            pst=cn.prepareStatement("DELETE FROM pagos WHERE id_pago=?");
            pst.setString(1, idPago);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null,"se ha eliminado todo registro referente al pago");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,e.getMessage(),"error al eliminar todo registro del pago", JOptionPane.ERROR_MESSAGE);
        }
        
                
    }
    
    public boolean yaExistePagoConUsuarioPeriodo(String idCliente, String periodo) {
	String sql = "SELECT * FROM pagos WHERE fk_id_cliente='"+idCliente+"' AND periodo ='"+periodo+"'";

        
        Statement st =null;
        ResultSet rs =null;
        try {
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            if( rs.first() )        
                return true;        
            else
                return false; 

        } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null,ex.getMessage(),"error sql", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if(st!=null)st.close();
                if(rs!=null)st.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return true;
    }
    
}
