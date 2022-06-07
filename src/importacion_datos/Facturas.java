/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package importacion_datos;

import helpers.sql.SqlDetallePagos;
import helpers.sql.SqlPagos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import sistemacheranaguapotable.bd.ConexionBd;

/**
 *
 * @author jafeth888
 */
public class Facturas {
    ConexionBd cc= ConexionBd.obtenerInstancia();
    Connection cn= cc.conexion();
    SqlDetallePagos detallePago=new SqlDetallePagos();
    SqlPagos pago = new SqlPagos();
    public void registrarPagoYdetalleTipoMensualInicial(String fkIdCliente,String tipoTarifa,String precioTarifa,
        String tipoDescuento,String descuentoAplicado,String tipoPago,float descuentoAnual,float total,float deuda,String periodo,float importeMes,String fecha,int mesesPagados,String fechaUltimoPago){




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
         ResultSet llave=null;
         for (int i = 0; i <meses.length; i++) {
             int idRegistro=0;
             psDetallePagos = cn.prepareStatement("INSERT INTO detalle_pagos"
             + "(fk_id_pago,mes,importe,fk_id_estado_pago) VALUES (?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
             psDetallePagos.setInt(1,idPago);
             psDetallePagos.setString(2,meses[i]);
             psDetallePagos.setFloat(3,importeMes);//importe mes viene como parametro de la funcion
             psDetallePagos.setInt(4,2);
             psDetallePagos.executeUpdate();
             llave=psDetallePagos.getGeneratedKeys();
             if(llave.next()){
                idRegistro=llave.getInt(1);
             }
             
             if(i<mesesPagados){
                detallePago.cobrarImporteDetallePagos(String.valueOf(idRegistro),String.valueOf(importeMes),"0",fechaUltimoPago,1,String.valueOf(idPago));
             }
             
             
             
         }

         
         System.out.println("NUEVA FACTURA GENERADA CON EXITO-- " + idPago);
     } catch (SQLException e) {


         e.printStackTrace();
         JOptionPane.showMessageDialog(null,e.getMessage(),"Error al registrar pago",JOptionPane.ERROR_MESSAGE);
     }finally{
         try {
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

         
        System.out.println("NUEVA FACTURA GENERADA CON EXITO-- " + idPago);
     } catch (SQLException e) {

         e.printStackTrace();
         JOptionPane.showMessageDialog(null,e.getMessage(),"Error al registrar pago",JOptionPane.ERROR_MESSAGE);
     }finally{
         try {
             
             if(psPagos!=null)psPagos.close();
             if(psDetallePagos!=null)psDetallePagos.close();
             if(resultado!=null)resultado.close();
         } catch (SQLException ex) {
             ex.printStackTrace();
         }
    }    
   }
}
