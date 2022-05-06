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
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import sistemacheranaguapotable.bd.ConexionBd;

/**
 *
 * @author jafeth888
 */
public class MostrarUsuarios {
    ConexionBd cc= ConexionBd.obtenerInstancia();
    Connection cn= cc.conexion();    
    
    public void mostrarUsuariosCobros(String nombre, String apellidoPaterno,String apellidoMaterno,
        String domicilio,String barrio,JTable tablaUsuarios){
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
            sql="SELECT * FROM clientes WHERE fk_id_estado_cliente=1";
        }
        else{
            //SELECT * FROM clientes WHERE nombre LIKE '%R%'
            sql="SELECT * FROM clientes WHERE nombre LIKE '%"+nombre+"%' "
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
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
    }
    
}
