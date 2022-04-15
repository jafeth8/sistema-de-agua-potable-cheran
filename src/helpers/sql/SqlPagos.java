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
public class SqlPagos {
   ConexionBd cc= ConexionBd.obtenerInstancia();
   Connection cn= cc.conexion();
   
   /**
    *@param fk_idCliente para relacionar el pago con el cliente
    *@param tipoTarifa tipo de tarifa que sera aplicado
    *@param precioTarifa precio de la tarifa que sera aplicada
    *@param tipoDescuento tipo de descuento que sera aplicado
    *@param descuento precio de descuento que sera aplicado
    *@param tipoPago tipo de pago: si el usuario estaria pagando mensual o anual
    *@param descuentoAnual aplica descuento si el tipoPago es anual, si es mensual el descuento es de 0
    *@param total total a pagar aplicando descuentos
    *@param perido año de factura 
    *@param estado valores: 'en deuda' o 'pagado'
    * 
   **/
   public void registrarPago(String fk_idCliente,String tipoTarifa,String precioTarifa,String tipoDescuento,String descuento,
           String tipoPago,String descuentoAnual,String total,String periodo,String estado){
       try {	
            PreparedStatement pst = cn.prepareStatement("INSERT INTO pagos"
                    + "(fk_id_cliente,tipo_tarifa,precio_tarifa,tipo_descuento,descuento,tipo_pago,"
                    + "descuento_anual,total,periodo,estado) VALUES (?,?,?,?,?,?,?,?,?,?)");
            pst.setString(1, fk_idCliente);
            pst.setString(2, tipoTarifa);
            pst.setString(3, precioTarifa);
            pst.setString(4, tipoDescuento);
            pst.setString(5, descuento);
            pst.setString(6, tipoPago);
            pst.setString(7, descuentoAnual);
            pst.setString(8, total);
            pst.setString(9, periodo);
            pst.setString(10, estado);
            
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "FACTURA GENERADA CON EXITO");
            
        } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Verifique que los campos introducidos sean validos","Error!!",JOptionPane.ERROR_MESSAGE);
                JOptionPane.showMessageDialog(null,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
                
        }
   }

   
    public int obtenerUltimoRegistroPago(){
       String sql="SELECT MAX(id_pago) FROM pagos";
       int id=0;    	 
		    
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
            	
                id=rs.getInt(1);
              
            }
           
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null,ex.getMessage(),"error sql", JOptionPane.ERROR_MESSAGE);
        }finally {
        	//Aqui no se cierra la conexion para permitir mas operaciones
	}
        
        return id;
    }
    
    public float obtenerTotalPago(String idPago){
        
        String sql="SELECT total FROM pagos where id_pago='"+idPago+"'";
        float total=0;    	 
		    
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
            	
                total=rs.getFloat(1);
              
            }
           
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null,ex.getMessage(),"error sql", JOptionPane.ERROR_MESSAGE);
        }finally {
        	//Aqui no se cierra la conexion para permitir mas operaciones
	}
        
        return total;
    }
    
    public void actualizarDeudaRegistroPago(String idPago,float totalPagado, float deuda){
         PreparedStatement pst;
        try {
            pst = cn.prepareStatement("UPDATE pagos SET total_pagado=?, "
                    + "deuda=? WHERE id_pago=?");
            pst.setFloat(1,totalPagado);
            pst.setFloat(2,deuda);
            pst.setString(3,idPago);
  
            pst.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,e.getMessage(),"error sql", JOptionPane.ERROR_MESSAGE);
        }
    }
    
     public void actualizarEstadoRegistroPago(String idPago,String estado){
         PreparedStatement pst;
        try {
            pst = cn.prepareStatement("UPDATE pagos SET estado=? "
                    + " WHERE id_pago=?");
            pst.setString(1,estado);
            pst.setString(2,idPago);
            
  
            pst.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,e.getMessage(),"error sql", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public boolean yaExistePagoConUsuarioPeriodo(String idCliente, String periodo) {
	String sql = "SELECT * FROM pagos WHERE fk_id_cliente='"+idCliente+"' AND periodo ='"+periodo+"'";

        String categoria ="";

        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if( rs.first() )        
                return true;        
            else
                return false; 

        } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null,ex.getMessage(),"error sql", JOptionPane.ERROR_MESSAGE);
        } finally {
            
        }
        return true;
    }
    
}
