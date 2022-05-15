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
public class SqlPagosYdetallePagos {
    ConexionBd cc= ConexionBd.obtenerInstancia();
    Connection cn= cc.conexion();
   
    public void registrarPagoYdetalleTipoAnual(String fkIdCliente,String tipoTarifa,String precioTarifa,
        String tipoDescuento,String descuentoAplicado,String tipoPago,float descuentoAnual,float total,float deuda,String periodo,String fecha){
        
        try {
            /*desactivamos el autocommit para ejecutar las instrucciones de pago y detalle en un solo bloque
            por ACID en base de datos
            */
            cn.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error al desactivar auto-commit en registrar pago y detalle tipo Anual",JOptionPane.ERROR_MESSAGE);
        }
        int idPago=0;
        String queryTablaPagos="INSERT INTO pagos"
                    + "(fk_id_cliente,tipo_tarifa,precio_tarifa,tipo_descuento,descuento,tipo_pago,"
                    + "descuento_anual,total,deuda,periodo,fk_id_estado_pago,fecha_registro) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement psPagos=null,psDetallePagos=null;
        ResultSet resultado=null;
        try {
            psPagos = cn.prepareStatement(queryTablaPagos,Statement.RETURN_GENERATED_KEYS);
            psPagos.setString(1, fkIdCliente);
            psPagos.setString(2, tipoTarifa);
            psPagos.setString(3, precioTarifa);
            psPagos.setString(4, tipoDescuento);
            psPagos.setString(5, descuentoAplicado);
            psPagos.setString(6, tipoPago);
            psPagos.setFloat(7, descuentoAnual);
            psPagos.setFloat(8, total);
            psPagos.setFloat(9,deuda);
            psPagos.setString(10, periodo);
            psPagos.setInt(11,2);
            psPagos.setString(12, fecha);

            psPagos.executeUpdate();

            resultado=psPagos.getGeneratedKeys();

            if(resultado.next()){
                idPago=resultado.getInt(1);
            }

            /*----------A CONTINUACION REGISTRAMOS EL DETALLE DEL PAGO: TIPO DE PAGO ANUAL----------------*/
            psDetallePagos = cn.prepareStatement("INSERT INTO detalle_pagos"
                + "(fk_id_pago,mes,importe,fk_id_estado_pago) VALUES (?,?,?,?)");
            psDetallePagos.setInt(1,idPago);
            psDetallePagos.setString(2,"enero-diciembre");
            psDetallePagos.setFloat(3,total);
            psDetallePagos.setInt(4,2);

            psDetallePagos.executeUpdate();
            cn.commit();
            JOptionPane.showMessageDialog(null, "NUEVA FACTURA GENERADA CON EXITO-- " + idPago);
        } catch (SQLException e) {
            try {
                cn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null,e.getMessage(),"Error Rollback al facturar pagos",JOptionPane.ERROR_MESSAGE);
            }

            e.printStackTrace();
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error al registrar pago",JOptionPane.ERROR_MESSAGE);

        }finally{
            try {
                cn.setAutoCommit(true);
                if(psPagos!=null)psPagos.close();
                if(psDetallePagos!=null)psDetallePagos.close();
                if(resultado!=null)resultado.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
   }
   
   public void registrarPagoYdetalleTipoMensual(String fkIdCliente,String tipoTarifa,String precioTarifa,
        String tipoDescuento,String descuentoAplicado,String tipoPago,float descuentoAnual,float total,float deuda,String periodo,float importeMes,String fecha){
       
        try {
            /*desactivamos el autocommit para ejecutar las instrucciones de pago y detalle en un solo bloque
            por cuestiones ACID en base de datos
            */
            cn.setAutoCommit(false);
            
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error al desactivar auto-commit en registrar pago y detalle tipo Mensual",JOptionPane.ERROR_MESSAGE);
        }
       
       
        String [] meses = {"enero", "febrero", "marzo", "abril", "mayo", "junio", "julio","agosto",
        "septiempre","octubre","noviembre","diciembre"};
        
        int idPago=0;
        String queryTablaPagos="INSERT INTO pagos"
                    + "(fk_id_cliente,tipo_tarifa,precio_tarifa,tipo_descuento,descuento,tipo_pago,"
                    + "descuento_anual,total,deuda,periodo,fk_id_estado_pago,fecha_registro) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement psPagos=null,psDetallePagos=null;
        ResultSet resultado=null;
        
        try {
            psPagos = cn.prepareStatement(queryTablaPagos,Statement.RETURN_GENERATED_KEYS);
            psPagos.setString(1, fkIdCliente);
            psPagos.setString(2, tipoTarifa);
            psPagos.setString(3, precioTarifa);
            psPagos.setString(4, tipoDescuento);
            psPagos.setString(5, descuentoAplicado);
            psPagos.setString(6, tipoPago);
            psPagos.setFloat(7, descuentoAnual);
            psPagos.setFloat(8, total);
            psPagos.setFloat(9, deuda);
            psPagos.setString(10, periodo);
            psPagos.setInt(11,2);
            psPagos.setString(12, fecha);

            psPagos.executeUpdate();

            resultado=psPagos.getGeneratedKeys();

            if(resultado.next()){
                idPago=resultado.getInt(1);
            }
            
            /*----------A CONTINUACION REGISTRAMOS EL DETALLE DEL PAGO: TIPO DE PAGO MENSUAL----------------*/
            for (int i = 0; i <meses.length; i++) {
                psDetallePagos = cn.prepareStatement("INSERT INTO detalle_pagos"
                + "(fk_id_pago,mes,importe,fk_id_estado_pago) VALUES (?,?,?,?)");
                psDetallePagos.setInt(1,idPago);
                psDetallePagos.setString(2,meses[i]);
                psDetallePagos.setFloat(3,importeMes);//importe mes viene como parametro de la funcion
                psDetallePagos.setInt(4,2);
                psDetallePagos.executeUpdate();
            }
            
            cn.commit();
            JOptionPane.showMessageDialog(null, "NUEVA FACTURA GENERADA CON EXITO-- " + idPago);
        } catch (SQLException e) {
            try {
                cn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null,e.getMessage(),"Error Rollback al facturar pagos",JOptionPane.ERROR_MESSAGE);
            }

            e.printStackTrace();
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error al registrar pago",JOptionPane.ERROR_MESSAGE);
        }finally{
            try {
                cn.setAutoCommit(true);
                if(psPagos!=null)psPagos.close();
                if(psDetallePagos!=null)psDetallePagos.close();
                if(resultado!=null)resultado.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }    
   }

   
}
