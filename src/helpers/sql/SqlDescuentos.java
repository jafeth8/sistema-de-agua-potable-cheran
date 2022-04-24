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
public class SqlDescuentos {
    ConexionBd cc= ConexionBd.obtenerInstancia();
    Connection cn= cc.conexion();
    public void registrarNuevoDescuento(String tipoDescuento,String descuento){
        String query="INSERT INTO descuentos (tipo_descuento,descuento,descuento_anual) VALUES(?,?,?*12)";
        PreparedStatement psDescuentos=null;
        
        try {
            psDescuentos=cn.prepareStatement(query);
            psDescuentos.setString(1,tipoDescuento);
            psDescuentos.setString(2,descuento);
            psDescuentos.setString(3,descuento);
            psDescuentos.executeUpdate();
            JOptionPane.showMessageDialog(null,"Nuevo descuento registrado","!!",JOptionPane.INFORMATION_MESSAGE );
        } catch (SQLException ex) {
            Logger.getLogger(SqlDescuentos.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.getMessage(),"error al registrar descuento nuevo", JOptionPane.ERROR_MESSAGE);
        }finally{
            try {
                if(psDescuentos!=null) psDescuentos.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public void actualizarRegistroDescuento(String tipoDescuento,String descuento){
        PreparedStatement pst=null;
        try {
            pst = cn.prepareStatement("UPDATE descuentos SET tipo_descuento=?, "
                    + "descuento=?, descuento_anual=?*12 WHERE tipo_descuento=?");
            pst.setString(1,tipoDescuento);
            pst.setString(2,descuento);
            pst.setString(3,descuento);
            pst.setString(4,tipoDescuento);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null,"Modificacion realizada","!!",JOptionPane.INFORMATION_MESSAGE );
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error al actualizar descuento", JOptionPane.ERROR_MESSAGE);
        }finally{
            try {
               if(pst!=null) pst.close();
            } catch (SQLException ex) {
               ex.printStackTrace();
            }
        }
    }
    
    public void actualizarEstadoDescuento(String tipoDescuento,String estado){
        PreparedStatement pst=null;
        try {
            pst = cn.prepareStatement("UPDATE descuentos SET estado=? "
                    + " WHERE tipo_descuento=?");
            pst.setString(1,estado);
            pst.setString(2,tipoDescuento);
            
  
            pst.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error al reactivar el descuento", JOptionPane.ERROR_MESSAGE);
        }finally{
            try {
               if(pst!=null) pst.close();
            } catch (SQLException ex) {
               ex.printStackTrace();
            }
        }
    }
}
