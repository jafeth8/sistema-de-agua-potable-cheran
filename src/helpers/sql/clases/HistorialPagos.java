
package helpers.sql.clases;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import sistemacheranaguapotable.bd.ConexionBd;

/**
 *
 * @author jafeth888
 */
public class HistorialPagos {
    ConexionBd cc= ConexionBd.obtenerInstancia();
    Connection cn= cc.conexion();
    
    public void mostrarHistorialPagos(String fecha, JTable tablaHistorialRecibos ){
        DefaultTableModel modelo= new DefaultTableModel();
        modelo.addColumn("Id pago");
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellido paterno");
        modelo.addColumn("Apellido materno");
        modelo.addColumn("mes que se pago");
        modelo.addColumn("importe");
        modelo.addColumn("fecha de pago");
        tablaHistorialRecibos.setModel(modelo);
        
        String sql="SELECT detalle_pagos.fk_id_pago, clientes.nombre, clientes.apellido_paterno, clientes.apellido_materno, "
            + "detalle_pagos.mes,detalle_pagos.importe,detalle_pagos.fecha_pago FROM detalle_pagos JOIN pagos "
            + "ON fk_id_pago=id_pago JOIN clientes ON pagos.fk_id_cliente=clientes.id_cliente "
            + "WHERE detalle_pagos.fecha_pago='"+fecha+"' AND detalle_pagos.fk_id_estado_pago=1";
        
        Statement st=null;
        ResultSet rs=null;
        Object datos []= new Object[7];
        try {
            st=cn.createStatement();
            rs=st.executeQuery(sql);
            while(rs.next()){
                datos[0]=rs.getString(1);
                datos[1]=rs.getString(2);
                datos[2]=rs.getString(3);
                datos[3]=rs.getString(4);
                datos[4]=rs.getString(5);
                datos[5]=rs.getString(6);
                datos[6]=rs.getString(7);
                modelo.addRow(datos);
            }
        } catch (SQLException ex) {
            Logger.getLogger(HistorialPagos.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.getMessage(),"!!",JOptionPane.WARNING_MESSAGE);
        }finally{
            try {
                if(st!=null) st.close();
                if(rs!=null) rs.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(),"!!",JOptionPane.WARNING_MESSAGE);
            }
        }
        

    }
}
