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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import sistemacheranaguapotable.bd.ConexionBd;

/**
 *
 * @author jafeth888
 */
public class FiltradoUsuarios {
    ConexionBd cc= ConexionBd.obtenerInstancia();
    Connection cn= cc.conexion();
    
    public void mostrarUsuario_al_Registrar(int idCliente, JTable tablaUsuarios){
        DefaultTableModel modelo= new DefaultTableModel();
        
        modelo.addColumn("id cliente");
        modelo.addColumn("No. cliente");
        modelo.addColumn("No contrato");
        modelo.addColumn("Nombre");
        modelo.addColumn("Ap paterno");
        modelo.addColumn("Ap materno");
        modelo.addColumn("Domicilio");
        modelo.addColumn("Telefono");
        modelo.addColumn("Barrio");
        modelo.addColumn("descuento");
        modelo.addColumn("tarifa");
        tablaUsuarios.setModel(modelo);
        
        String sql="SELECT id_cliente, numero_cliente, numero_contrato, nombre, apellido_paterno, apellido_materno, "
                + "domicilio, telefono, barrio, fk_tipo_descuento, fk_tipo_tarifa FROM clientes WHERE id_cliente="+idCliente+"";
        
        String []datos = new String [11];
        Statement st = null;
        ResultSet rs = null;
        try {
            st = cn.createStatement();
            rs = st.executeQuery(sql);
           
            while(rs.next()){
                datos[0]=rs.getString(1);
                datos[1]=rs.getString(2);
                datos[2]=rs.getString(3);
                datos[3]=rs.getString(4);
                datos[4]=rs.getString(5);
                datos[5]=rs.getString(6);
                datos[6]=rs.getString(7);
                datos[7]=rs.getString(8);
                datos[8]=rs.getString(9);
                datos[9]=rs.getString(10);
                datos[10]=rs.getString(11);
                modelo.addRow(datos);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(FiltradoUsuarios.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }finally{
            try {
                if(st!=null)st.close();
                if(rs!=null)rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(FiltradoUsuarios.class.getName()).log(Level.SEVERE, null, ex);
                ex.printStackTrace();
            }
        }
    }
    
    public void mostrarUsuarios(String nombre, String apellidoPaterno,String apellidoMaterno,
        String domicilio,String barrio, JTable tablaUsuarios){
        DefaultTableModel modelo= new DefaultTableModel();
        
        modelo.addColumn("Id");
        modelo.addColumn("No. cliente");
        modelo.addColumn("No. Contrato");
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellido paterno");
        modelo.addColumn("Apellido materno");
        modelo.addColumn("Domicilio");
        modelo.addColumn("Telefono");
        modelo.addColumn("Barrio");
        modelo.addColumn("descuento");
        modelo.addColumn("tarifa");

        tablaUsuarios.setModel(modelo);
        /*---------ESTABLECIMIENTO DE TAMAÑO DE COLUMNAS-------------*/
        TableColumn columnaId=tablaUsuarios.getColumn("Id");
        columnaId.setMinWidth(0);
        columnaId.setPreferredWidth(0);
        columnaId.setMaxWidth(0);
        TableColumn columnaDomicilio=tablaUsuarios.getColumn("Domicilio");
        columnaDomicilio.setMinWidth(300);
        columnaDomicilio.setPreferredWidth(400);
        columnaDomicilio.setMaxWidth(300);
        /*--------FIN DE ESTABLECIMIENTO DE TAMAÑO DE COLUMNAS---------*/
        String sql="";
        if(nombre.equals("") && apellidoPaterno.equals("") && apellidoMaterno.equals("") && domicilio.equals("") && barrio.equals(""))
        {
            sql="SELECT id_cliente, numero_cliente, numero_contrato, nombre, apellido_paterno, apellido_materno, "
                + "domicilio, telefono, barrio, fk_tipo_descuento, fk_tipo_tarifa FROM clientes WHERE fk_id_estado_cliente=1";
        }
        else{
            //SELECT * FROM clientes WHERE nombre LIKE '%R%'
            sql="SELECT id_cliente, numero_cliente, numero_contrato, nombre, apellido_paterno, apellido_materno, "
                + "domicilio, telefono, barrio, fk_tipo_descuento, fk_tipo_tarifa FROM clientes WHERE nombre LIKE '%"+nombre+"%' "
                + "AND apellido_paterno LIKE '%"+apellidoPaterno+"%' AND apellido_materno LIKE '%"+apellidoMaterno+"%' "
                + "AND domicilio LIKE '%"+domicilio+"%' AND barrio like '%"+barrio+"%' AND fk_id_estado_cliente=1";
        }
        
        String []datos = new String [11];
        
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                datos[0]=rs.getString(1);
                datos[1]=rs.getString(2);
                datos[2]=rs.getString(3);
                datos[3]=rs.getString(4);
                datos[4]=rs.getString(5);
                datos[5]=rs.getString(6);
                datos[6]=rs.getString(7);
                datos[7]=rs.getString(8);
                datos[8]=rs.getString(9);
                datos[9]=rs.getString(10);
                datos[10]=rs.getString(11);
                modelo.addRow(datos);
            }    
        } catch (SQLException ex) {
            Logger.getLogger(FiltradoUsuarios.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }
}
