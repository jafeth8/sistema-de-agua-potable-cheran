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
public class SqlRespaldo {
    ConexionBd cc= ConexionBd.obtenerInstancia();
    Connection cn= cc.conexion(); 
    public boolean registro_ruta_respaldo() {
        Statement st =null;
        ResultSet resultadosConsulta =null;
        try {
            String sql = "SELECT * FROM ruta_respaldo";
            st = cn.createStatement();
            resultadosConsulta = st.executeQuery(sql);
            if (resultadosConsulta.next())
                return true;
            else
                return false;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"..."
            + "error: "+e.getMessage(),"Error al verificar existencia de ruta respaldo", JOptionPane.WARNING_MESSAGE);
            return false;
        }finally{
            try {
                if(st!=null)st.close();
                if(resultadosConsulta!=null)resultadosConsulta.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                 JOptionPane.showMessageDialog(null,""
                    + "error: "+ex.getMessage(),"Se intento cerrar la conexion al verificar existencia de ruta respaldo", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
    
    public void registrarRutaRespaldo(String ruta) {
        PreparedStatement pst=null;
        try {
            pst = cn.prepareStatement("INSERT INTO ruta_respaldo" + "(id,ruta) VALUES (?,?)");
            pst.setString(1,"1");
            pst.setString(2, ruta);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null,"Ruta registrada!");
        } catch (Exception e) {
            // TODO: handle exception
            JOptionPane.showMessageDialog(null,"Intentelo nuevamente"
                + "error: "+e.getMessage(),"Error al registrar la ruta de respaldo", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if(pst!=null)pst.close();
            } catch (SQLException ex) { 
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null,""
                + "error: "+ex.getMessage(),"Se intento cerrar la conexion al registrar ruta respaldo", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
    public void actualizar_RutaRespaldo(String ruta) {

        PreparedStatement pst=null;
        try {
            pst = cn.prepareStatement("UPDATE ruta_respaldo SET ruta=? WHERE id='1'");
            pst.setString(1, ruta);
            pst.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Intentelo nuevamente"
            + "error: "+e.getMessage(),"Error al actualizar la ruta de respaldo", JOptionPane.ERROR_MESSAGE);
        }finally{
            try {
                if(pst!=null)pst.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null,""
                + "error: "+ex.getMessage(),"Se intento cerrar la conexion al actualizar ruta respaldo", JOptionPane.INFORMATION_MESSAGE);
            }
        }

    }
    
    public String obtenerRutaRespaldo() {

        String sql = "SELECT ruta FROM ruta_respaldo where id='1'";

        String ruta = "";
        Statement st =null;
        ResultSet rs =null;        
        try {
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                ruta = rs.getString(1);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null,"error: "+ex.getMessage(),"Error al obtener la ruta de respaldo",JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if(st!=null)st.close();
                if(rs!=null)rs.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null,"error: "+ex.getMessage(),"Error al cerrar la conexion de obtencion de ruta de respaldo",JOptionPane.INFORMATION_MESSAGE);
            }
        }
        return ruta;
    }

}
