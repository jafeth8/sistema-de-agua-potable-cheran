/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import sistemacheranaguapotable.bd.ConexionBd;

/**
 *
 * @author jafeth888
 */
public class SqlUsuarios {
   ConexionBd cc= ConexionBd.obtenerInstancia();
   Connection cn= cc.conexion();
   
   public float obtenerPrecioTarifaDeUsuario(String idCliente){
       //SELECT tarifas.tarifa_anual FROM clientes JOIN tarifas ON clientes.fk_tipo_tarifa = tarifas.tipo_tarifa WHERE clientes.id_cliente=1
       String sql="SELECT tarifas.tarifa_anual FROM clientes JOIN tarifas ON clientes.fk_tipo_tarifa = tarifas.tipo_tarifa WHERE clientes.id_cliente='"+idCliente+"'";
       float precio=0;    	 
		    
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
            	
                precio=rs.getFloat(1);
              
            }
           
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
        	//Aqui no se cierra la conexion para permitir mas operaciones
	}
        
        return precio;
   }
   
   public float obtenerDescuentoDeUsuario(String idCliente){
       //SELECT descuentos.descuento FROM clientes JOIN descuentos ON clientes.fk_tipo_descuento = descuentos.tipo_descuento WHERE clientes.id_cliente='1'
       String sql="SELECT descuentos.descuento FROM clientes JOIN descuentos ON clientes.fk_tipo_descuento = descuentos.tipo_descuento WHERE clientes.id_cliente='"+idCliente+"'";
       float precio=0;    	 
		    
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
            	
                precio=rs.getFloat(1);
              
            }
           
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
        	//Aqui no se cierra la conexion para permitir mas operaciones
	}
        
        return precio;
   }
}
