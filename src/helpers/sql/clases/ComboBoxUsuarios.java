/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers.sql.clases;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import sistemacheranaguapotable.bd.ConexionBd;

/**
 *
 * @author jafeth888
 */
public class ComboBoxUsuarios {
    ConexionBd cc= ConexionBd.obtenerInstancia();
    Connection cn= cc.conexion();    
    public void mostrarDescuentos(JComboBox comboBoxdescuentos) {
        String sql = "";
    
        sql = "SELECT tipo_descuento FROM descuentos WHERE estado='activo'";
        
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            comboBoxdescuentos.addItem("");
            while (rs.next()) {
                comboBoxdescuentos.addItem(rs.getString(1));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

    }
    
    public void mostrarTarifas(JComboBox comboBoxTarifas) {
        String sql = "";
    
        sql = "SELECT tipo_tarifa FROM tarifas WHERE estado='activo'";
        
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            comboBoxTarifas.addItem("");
            while (rs.next()) {
                comboBoxTarifas.addItem(rs.getString(1));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
}
