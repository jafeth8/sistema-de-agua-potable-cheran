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
import sistemacheranaguapotable.bd.ConexionBd;

/**
 *
 * @author jafeth888
 */
public class MostrarPagos {
    ConexionBd cc= ConexionBd.obtenerInstancia();
    Connection cn= cc.conexion();
    public void MostrarUsuariosPagos(String nombre,JTable tablaPagos){
        DefaultTableModel modelo= new DefaultTableModel();

        modelo.addColumn("No. cliente");
        modelo.addColumn("No. Contrato");
        modelo.addColumn("Nombre");
        modelo.addColumn("Ap paterno");
        modelo.addColumn("Ap materno");
        modelo.addColumn("Domicilio       ");
        modelo.addColumn("Telefono");
        modelo.addColumn("Barrio");
        modelo.addColumn("descuento");
        modelo.addColumn("tarifa");
        
        String sql="";
        
        tablaPagos.setModel(modelo);
        if(nombre.equals(""))
        {
            sql="SELECT id_cliente,numero_cliente,numero_contrato,nombre,apellido_paterno,"
                    + "apellido_materno,domicilio,telefono,barrio,fk_tipo_descuento,"
                    + "fk_tipo_tarifa FROM clientes where fk_id_estado_cliente='1'";
        }
        else{
            
            sql="SELECT id_cliente,numero_cliente,numero_contrato,nombre,apellido_paterno,"
                    + "apellido_materno,domicilio,telefono,barrio,fk_tipo_descuento,"
                    + "fk_tipo_tarifa FROM clientes WHERE nombre LIKE '%"+nombre+"%' AND fk_id_estado_cliente='1'";
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
    
    public void mostrarDetallePagos(String idCliente,int periodo1, int periodo2,JTable tablaDetallePagos){
        DefaultTableModel modelo= new DefaultTableModel();
        
        modelo.addColumn("Id cliente");
        modelo.addColumn("Nombre");
        modelo.addColumn("Forma de pago");
        modelo.addColumn("Periodo");
        modelo.addColumn("Estado");

        String sql="";
        
        tablaDetallePagos.setModel(modelo);

        sql="SELECT id_cliente,nombre,tipo_pago,periodo,c_estado_pagos.descripcion "
            + "FROM clientes JOIN pagos ON clientes.id_cliente=pagos.fk_id_cliente "
            + "JOIN c_estado_pagos ON pagos.fk_id_estado_pago=c_estado_pagos.id_estado_pago "
            + "WHERE clientes.id_cliente='"+idCliente+"' AND periodo BETWEEN "+periodo1+" AND "+periodo2+"";

     
        
        String []datos = new String [5];
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            while(rs.next()){
                datos[0]=rs.getString(1);
                datos[1]=rs.getString(2);
                datos[2]=rs.getString(3);
                datos[3]=rs.getString(4);
                datos[4]=rs.getString(5);
                modelo.addRow(datos);
            }

            } catch (SQLException ex) {
                    ex.printStackTrace();
                System.out.println(ex.getMessage());
            }
    }
    
}
