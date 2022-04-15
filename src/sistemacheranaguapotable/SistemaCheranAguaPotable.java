/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemacheranaguapotable;

import java.sql.Connection;
import sistemacheranaguapotable.bd.ConexionBd;

/**
 *
 * @author jafeth888
 */
public class SistemaCheranAguaPotable {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
           ConexionBd cc= ConexionBd.obtenerInstancia();
           Connection cn= cc.conexion();
    }
    
}
