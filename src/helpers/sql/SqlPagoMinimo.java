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
public class SqlPagoMinimo {
   ConexionBd cc= ConexionBd.obtenerInstancia();
   Connection cn= cc.conexion(); 
   public float obtenerPagoMinimo(){
       
        String sql="SELECT pago_minimo FROM c_pago_minimo";
        float pagoMinimo=0;    	 
	Statement st =null;
        ResultSet rs =null;	    
        try {
             st = cn.createStatement();
             rs = st.executeQuery(sql);
            while(rs.next()){
            	
                pagoMinimo=rs.getFloat(1);
              
            }
           
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
            try {
                if(rs!=null)rs.close();
                if(st!=null)st.close();
            } catch (SQLException ex) {
                Logger.getLogger(SqlPagoMinimo.class.getName()).log(Level.SEVERE, null, ex);
                ex.printStackTrace();
            }
	}
        
        return pagoMinimo;
   }
   
   public void actualizarPagoMinimo(String pagoMinimo){
        PreparedStatement pst=null;
        try {
            pst = cn.prepareStatement("UPDATE c_pago_minimo SET pago_minimo=?");
            pst.setString(1,pagoMinimo);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null,"valor Actualizado");
            
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error en actualizar pago minimo", JOptionPane.ERROR_MESSAGE);
        }finally{
            try {
               if(pst!=null) pst.close();
            } catch (SQLException ex) {
               ex.printStackTrace();
            }
        }
    }
}
