/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package importacion_datos;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import sistemacheranaguapotable.bd.ConexionBd;

/**
 *
 * @author jafeth888
 */
public class ClientesSql {
    ConexionBd cc= ConexionBd.obtenerInstancia();
    Connection cn= cc.conexion();
    
    public int obtenerIdUsuario(String numeroCliente){
    String sql="SELECT id_cliente FROM clientes WHERE numero_cliente='"+numeroCliente+"'";
    int id=0;    	 
    Statement st =null;
    ResultSet rs =null;
     try {
         st = cn.createStatement();
         rs = st.executeQuery(sql);
         while(rs.next()){

             id=rs.getInt(1);

        }

     } catch (SQLException ex) {
         ex.printStackTrace();
     }finally {
        try {
            if(st!=null)st.close();
            if(rs!=null)rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
     }

     return id;
   }
    
    public HashMap<String,String> obtenerDatosClientes(String numeroCliente){
        HashMap<String, String> datosCliente = new HashMap<String, String>();
        String sql="SELECT id_cliente,fk_tipo_tarifa,tarifas.tarifa_anual,fk_tipo_descuento,descuentos.descuento FROM clientes JOIN tarifas ON fk_tipo_tarifa=tarifas.tipo_tarifa "
            + "JOIN descuentos ON fk_tipo_descuento=descuentos.tipo_descuento WHERE numero_cliente='"+numeroCliente+"'";  	 
	Statement st = null;
        ResultSet rs = null;	    
        try {
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            while(rs.next()){
                datosCliente.put("idCliente",rs.getString(1));
            	
                datosCliente.put("tipoTarifa",rs.getString(2));
            	
                datosCliente.put("tarifaAnual",rs.getString(3));
            	
                datosCliente.put("tipoDescuento",rs.getString(4));
            	
                datosCliente.put("descuentoMensual",rs.getString(5));                
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null,ex.getMessage(),"error en obtner datos ", JOptionPane.ERROR_MESSAGE);
        }finally {
            try {
                if(st!=null)st.close();
                if(rs!=null)rs.close();
                
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
	}
        
        return datosCliente;
    } 
}
