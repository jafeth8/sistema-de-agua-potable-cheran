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
import javax.swing.JOptionPane;
import sistemacheranaguapotable.bd.ConexionBd;

/**
 *
 * @author jafeth888
 */
public class SqlDetallePagos {
    ConexionBd cc= ConexionBd.obtenerInstancia();
    Connection cn= cc.conexion();
    
    /**
    *@param fk_idPago para relacionar el registro detalle con el pago
    *@param mes mes que se esta pagando si el tipoPago establecido en el metodo 'registrarPago' es de tipo anual se establece enero-diciembre
    *@param pagoCorrespondiente pago que le corresponde pagar por cada mes
    *@param pagado valores: 'si' o 'no'
    **/
    public void registrarDetallePago(String fk_idPago,String mes,String pagoCorrespondiente,
       String pagado){
       try {	
            PreparedStatement pst = cn.prepareStatement("INSERT INTO detalle_pagos"
                    + "(fk_id_pago,mes,importe,pagado) VALUES (?,?,?,?)");
            pst.setString(1, fk_idPago);
            pst.setString(2, mes);
            pst.setString(3, pagoCorrespondiente);
            pst.setString(4, pagado);
            
            pst.executeUpdate();
            
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Verifique que los campos introducidos sean validos","Error!!",JOptionPane.ERROR_MESSAGE);
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);

        }
   }
    
    public float obtenerSumatoriaImportesPagadosTablaDetallePagos(String idPago){
        //SELECT SUM(pago_realizado) FROM `detalle_pagos` WHERE fk_id_pago='11'
        String sql="SELECT SUM(importe) FROM `detalle_pagos` WHERE fk_id_pago='"+idPago+"' AND pagado='si'";
        float total=0;    	 
		    
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
            	
                total=rs.getFloat(1);
              
            }
           
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null,ex.getMessage(),"error sql", JOptionPane.ERROR_MESSAGE);
        }finally {
        	//Aqui no se cierra la conexion para permitir mas operaciones
	}
        
        return total;
    }
    
    public void cobrarImporteDetallePagos(String idRegistro,String pagoRealizado,String cambioEntregado,String fechaPago,String pagado){
        PreparedStatement pst;
        try {
            pst = cn.prepareStatement("UPDATE detalle_pagos SET pago_realizado=?,"
                    + "cambio_entregado=?,fecha_pago=?, pagado=? WHERE id_registro=?");
            pst.setString(1, pagoRealizado);
            pst.setString(2, cambioEntregado);
            pst.setString(3, fechaPago);
            pst.setString(4, pagado);
            pst.setString(5, idRegistro);
            pst.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,e.getMessage(),"error sql", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
}
