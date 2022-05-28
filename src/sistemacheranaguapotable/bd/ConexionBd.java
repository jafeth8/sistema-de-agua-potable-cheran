/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemacheranaguapotable.bd;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/**
 *
 * @author jafeth888
 */
public class ConexionBd {
    private static ConexionBd instancia;
	
	private ConexionBd() {
		
	}
	
	public static ConexionBd obtenerInstancia() {
		if(instancia==null) {
			return instancia=new ConexionBd();
		}else {
			return instancia;
		}
	}
	
    private Connection conectar=null;
    public Connection conexion(){
        try {
            //Class.forName("com.mysql.jdbc.Driver");
            conectar=DriverManager.getConnection("jdbc:mysql://localhost/"+Ruta.database+"",""+Ruta.Usuario+"",""+Ruta.Contrasenia+"");
            
            //System.out.println("sistemacheranaguapotable.bd.ConexionBd.conexion()");
            //conectar.setAutoCommit(false);
        } catch (Exception e) {
            System.err.print(e.getMessage());
            JOptionPane.showMessageDialog(null, e.getMessage(),"error", JOptionPane.ERROR_MESSAGE);
        }
        return conectar;
    }
}
