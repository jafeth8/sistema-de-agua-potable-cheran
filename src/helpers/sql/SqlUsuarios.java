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
public class SqlUsuarios {
   ConexionBd cc= ConexionBd.obtenerInstancia();
   Connection cn= cc.conexion();
   
   public float obtenerPrecioTarifaDeUsuario(String idCliente){
       //SELECT tarifas.tarifa_anual FROM clientes JOIN tarifas ON clientes.fk_tipo_tarifa = tarifas.tipo_tarifa WHERE clientes.id_cliente=1
       String sql="SELECT tarifas.tarifa_anual FROM clientes JOIN tarifas ON clientes.fk_tipo_tarifa = tarifas.tipo_tarifa WHERE clientes.id_cliente='"+idCliente+"'";
       float precio=0;    	 
		    
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
            	
                precio=rs.getFloat(1);
              
            }
           
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
        	//Aqui no se cierra la conexion para permitir mas operaciones
	}
        
        return precio;
   }
   
   public float obtenerDescuentoDeUsuario(String idCliente){
       //SELECT descuentos.descuento FROM clientes JOIN descuentos ON clientes.fk_tipo_descuento = descuentos.tipo_descuento WHERE clientes.id_cliente='1'
       String sql="SELECT descuentos.descuento FROM clientes JOIN descuentos ON clientes.fk_tipo_descuento = descuentos.tipo_descuento WHERE clientes.id_cliente='"+idCliente+"'";
       float precio=0;    	 
		    
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
            	
                precio=rs.getFloat(1);
              
            }
           
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
        	//Aqui no se cierra la conexion para permitir mas operaciones
	}
        
        return precio;
   }
    /**
     *@return retorna el id del usuario generado del nuevo usario para mostrar informacion del usuario en la tabla de registros
     *@return retorna 0 si no se a pudo extraer el id
     **/
    public int registrarUsuario(String numCliente,String numContrato, String nombre,String aPaterno,String aMaterno,
            String domicilio,String telefono,String barrio,String descuento,String tarifa){
        //calculamos de una vez la tarifa mensual
        String query="INSERT INTO clientes (numero_cliente,numero_contrato,nombre,apellido_paterno,"
                + " apellido_materno, domicilio, telefono, barrio, fk_tipo_descuento, fk_tipo_tarifa)"
                + " VALUES(?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement psUsuarios=null;
        int idUsuario=0;
        ResultSet resultadoId=null;//para obtener el nuevo id generado al usuario
        try {
            psUsuarios=cn.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
            psUsuarios.setString(1,numCliente);
            psUsuarios.setString(2,numContrato);
            psUsuarios.setString(3,nombre);
            psUsuarios.setString(4,aPaterno);
            psUsuarios.setString(5,aMaterno);
            psUsuarios.setString(6,domicilio);
            psUsuarios.setString(7,telefono);
            psUsuarios.setString(8,barrio);
            psUsuarios.setString(9,descuento);
            psUsuarios.setString(10,tarifa);
            
            psUsuarios.executeUpdate();
            
            resultadoId=psUsuarios.getGeneratedKeys();
            
            if(resultadoId.next()){
                idUsuario=resultadoId.getInt(1);
            }
            
            JOptionPane.showMessageDialog(null,"Nuevo usuario registrado","!!",JOptionPane.INFORMATION_MESSAGE );
        } catch (SQLException ex) {
            Logger.getLogger(SqlDescuentos.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.getMessage(),"error al registrar nuevo usuario", JOptionPane.ERROR_MESSAGE);
        }finally{
            try {
                if(psUsuarios!=null) psUsuarios.close();
                if(resultadoId!=null) resultadoId.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return idUsuario;
    }
    
    public void modificarUsuario(String idCliente,String numCliente,String numContrato, String nombre,String aPaterno,String aMaterno,
        String domicilio,String telefono,String barrio,String descuento,String tarifa){
        
        PreparedStatement pst=null;
        try {
            pst = cn.prepareStatement("UPDATE clientes SET numero_cliente=? , numero_contrato=? , "
                    + "nombre=? , apellido_paterno=? , apellido_materno=? , domicilio=? , telefono=? , "
                    + "barrio=? , fk_tipo_descuento=? , fk_tipo_tarifa=? WHERE id_cliente=?");
            pst.setString(1,numCliente);
            pst.setString(2,numContrato);
            pst.setString(3,nombre);
            pst.setString(4,aPaterno);
            pst.setString(5,aMaterno);
            pst.setString(6,domicilio);
            pst.setString(7,telefono);
            pst.setString(8,barrio);
            pst.setString(9,descuento);
            pst.setString(10,tarifa);
            pst.setString(11,idCliente);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null,"Modificacion realizada","!!",JOptionPane.INFORMATION_MESSAGE );
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error al Modificar usuario", JOptionPane.ERROR_MESSAGE);
        }finally{
            try {
               if(pst!=null) pst.close();
            } catch (SQLException ex) {
               ex.printStackTrace();
            }
        }
    }
    
    
    public void actualizarEstadoRegistroUsuario(String idCliente,int fkIdEstadoCliente){
        PreparedStatement pst=null;
        try {
            pst = cn.prepareStatement("UPDATE clientes SET fk_id_estado_cliente=? "
                    + " WHERE id_cliente=?");
            pst.setInt(1,fkIdEstadoCliente);
            pst.setString(2,idCliente);

            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,e.getMessage(),"error al actualizar estado del pago", JOptionPane.ERROR_MESSAGE);
        }finally{
             try {
                if(pst!=null)pst.close();
             } catch (SQLException ex) {
                ex.printStackTrace();
             }
        }
    }
    
}
