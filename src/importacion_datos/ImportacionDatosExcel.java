
package importacion_datos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import sistemacheranaguapotable.bd.ConexionBd;

/**
 *
 * @author jafeth888
 */
public class ImportacionDatosExcel {
    ConexionBd cc= ConexionBd.obtenerInstancia();
    Connection cn= cc.conexion();
    /**
        @deprecated funcion para fines didacticos
    **/
    public void cargar(){
        PreparedStatement ps;
        JFileChooser file=new JFileChooser();
	file.showSaveDialog(null);
        File archivo =file.getSelectedFile();
        if(archivo==null){
            JOptionPane.showMessageDialog(null,"operacion cancelada");
            return;
        }
        
        try {
            FileInputStream fileInput = new FileInputStream(archivo);
            XSSFWorkbook wb = new XSSFWorkbook(fileInput);
            XSSFSheet sheet = wb.getSheetAt(0);
            int numFilas = sheet.getLastRowNum();
            
            for (int a = 1; a <= numFilas; a++) {
                Row fila = sheet.getRow(a);

                ps = cn.prepareStatement("INSERT INTO importacion_datos (numero_cliente,nombre,fecha_pago,mes_pagado) VALUES(?,?,?,?)");
                ps.setString(1, fila.getCell(0).getStringCellValue());
                ps.setString(2, fila.getCell(1).getStringCellValue());
                ps.setString(3, ""+fila.getCell(2).getDateCellValue());
                ps.setString(4, ""+fila.getCell(3).getDateCellValue());
                ps.execute();
            }
            
            JOptionPane.showMessageDialog(null,"datos cargados!");
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null,"error"+ex.getMessage(),"Probable error al ubicar archivo excel",JOptionPane.ERROR_MESSAGE);
            
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null,"error"+ex.getMessage(),"Error al cargar archivo excel",JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
           ex.printStackTrace();
           JOptionPane.showMessageDialog(null,"error"+ex.getMessage(),"Error al registrar datos en la BD",JOptionPane.ERROR_MESSAGE);
        }
        
        
    }
}
