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
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import sistemacheranaguapotable.bd.ConexionBd;

/**
 *
 * @author jafeth888
 */
public class InfoRecibo {
    ConexionBd cc= ConexionBd.obtenerInstancia();
    Connection cn= cc.conexion();
    
    public HashMap<String,String> datosRecibo(String idDetallePago){
        HashMap<String,String> datosRecibo = new HashMap<String, String>();
        datosRecibo.put("nombre","Romeo Jafet");
        datosRecibo.put("edad","26");
        String sql="SELECT detalle_pagos.fk_id_pago, detalle_pagos.id_registro, clientes.nombre, clientes.apellido_paterno,clientes.apellido_materno,clientes.domicilio,clientes.barrio,"
            + " pagos.tipo_pago,pagos.periodo,pagos.tipo_tarifa,pagos.precio_tarifa,pagos.tipo_descuento,pagos.descuento,pagos.descuento_anual,detalle_pagos.mes,detalle_pagos.fecha_pago,detalle_pagos.importe "
            + "FROM detalle_pagos JOIN pagos ON fk_id_pago=id_pago JOIN clientes ON pagos.fk_id_cliente=clientes.id_cliente WHERE detalle_pagos.id_registro="+idDetallePago+"";
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                datosRecibo.put("idPago",rs.getString(1));
                datosRecibo.put("idDetallePago",rs.getString(2));
                datosRecibo.put("nombreCompleto",rs.getString(3)+" "+rs.getString(4)+" "+rs.getString(5));
                datosRecibo.put("domicilio",rs.getString(6));
                datosRecibo.put("barrio",rs.getString(7));
                datosRecibo.put("tipoPago",rs.getString(8));
                datosRecibo.put("periodo",rs.getString(9));
                datosRecibo.put("tipoTarifa",rs.getString(10));
                datosRecibo.put("precioTarifa",rs.getString(11));
                datosRecibo.put("tipoDescuento",rs.getString(12));
                datosRecibo.put("precioDescuento",rs.getString(13));
                datosRecibo.put("descuentoAnual",rs.getString(14));
                datosRecibo.put("mesDePago",rs.getString(15));
                datosRecibo.put("fechaPago",rs.getString(16));
                datosRecibo.put("importe",rs.getString(17));
                
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Hubo un error al obtner info del recibo: "+ ex.getMessage() +"intentelo de nuevo","Error al obtener datos", JOptionPane.ERROR_MESSAGE);
            datosRecibo.put("error","si");
        }
        return datosRecibo;
    }
    
    
}
