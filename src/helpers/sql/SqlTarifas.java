/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import sistemacheranaguapotable.bd.ConexionBd;

/**
 *
 * @author jafeth888
 */
public class SqlTarifas {
    ConexionBd cc= ConexionBd.obtenerInstancia();
    Connection cn= cc.conexion();
    public void registrarNuevaTarifa(String tipoTarifa,String tarifaAnual){
        //calculamos de una vez la tarifa mensual
        String query="INSERT INTO tarifas (tipo_tarifa,tarifa_anual,tarifa_mensual)VALUES(?,?,?/12)";
        PreparedStatement psTarifas=null;
        
        try {
            psTarifas=cn.prepareStatement(query);
            psTarifas.setString(1,tipoTarifa);
            psTarifas.setString(2,tarifaAnual);
            psTarifas.setString(3,tarifaAnual);
            psTarifas.executeUpdate();
            JOptionPane.showMessageDialog(null,"Nueva Tarifa registrada","!!",JOptionPane.INFORMATION_MESSAGE );
        } catch (SQLException ex) {
            Logger.getLogger(SqlDescuentos.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.getMessage(),"error al registrar nueva Tarifa", JOptionPane.ERROR_MESSAGE);
        }finally{
            try {
                if(psTarifas!=null) psTarifas.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public void actualizarRegistroTarifa(String tipoTarifa,String oldTipoTarifa,String TarifaAnual){
        PreparedStatement pst=null;
        try {
            pst = cn.prepareStatement("UPDATE tarifas SET tipo_tarifa=?, "
                    + "tarifa_anual=?, tarifa_mensual=?/12 WHERE tipo_tarifa=?");
            pst.setString(1,tipoTarifa);
            pst.setString(2,TarifaAnual);
            pst.setString(3,TarifaAnual);//se actualiza la tarifa mensual obteniendolo de la tariaAnual / 12
            pst.setString(4,oldTipoTarifa);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null,"Modificacion realizada","!!",JOptionPane.INFORMATION_MESSAGE );
            
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error al actualizar tarifa", JOptionPane.ERROR_MESSAGE);
        }finally{
            try {
               if(pst!=null) pst.close();
            } catch (SQLException ex) {
               ex.printStackTrace();
            }
        }
    }
    public void actualizarEstadoTarifa(String tipoTarifa,String estado){
        PreparedStatement pst=null;
        try {
            pst = cn.prepareStatement("UPDATE tarifas SET estado=? "
                    + " WHERE tipo_tarifa=?");
            pst.setString(1,estado);
            pst.setString(2,tipoTarifa);
            
  
            pst.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error al reactivar la tarifa", JOptionPane.ERROR_MESSAGE);
        }finally{
            try {
               if(pst!=null) pst.close();
            } catch (SQLException ex) {
               ex.printStackTrace();
            }
        }
    }    
}
