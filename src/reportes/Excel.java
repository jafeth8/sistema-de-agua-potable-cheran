
package reportes;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


/**
 *
 * @author jafeth888
 */
public class Excel {
    public void crearExcel(){
        //**version vieja .xls
        //Workbook book=new HSSFWorkbook();//archivo en excel
        //**
        Workbook book=new XSSFWorkbook();//archivo en excel
        Sheet sheet=book.createSheet("reportes");//pestaña
        
        Row row = sheet.createRow(0);
        row.createCell(0).setCellValue("value celda");
        row.createCell(1).setCellValue(888);
        row.createCell(2).setCellValue(true);
        
        Cell celda=row.createCell(3);//lo mismo que las anterioes pero se puede agregar mas atributos a una celda
        celda.setCellFormula(String.format("1+1",""));
        
        Row row2=sheet.createRow(1);
        row2.createCell(0).setCellValue(8);
        row2.createCell(1).setCellValue(12);
        
        Cell celdaSumaAB=row2.createCell(2);
        celdaSumaAB.setCellFormula(String.format("A%d+B%d",2,2));
        
         FileOutputStream fileOut=null;
        try {
            //fileOut=new FileOutputStream("reporte.xls");
            fileOut=new FileOutputStream("reporte.xlsx");
            book.write(fileOut);
            System.out.println("reportes.Excel.crearExcel()");
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }finally{
            try {
                fileOut.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    
    public void crearReporte(JTable tablaReportes){
        Workbook book =new XSSFWorkbook();
        Sheet sheet=book.createSheet("reporte");
        /*---------titulo-----------------------------------------*/
        CellStyle tituloEstilo = book.createCellStyle();
        tituloEstilo.setAlignment(HorizontalAlignment.CENTER);
        tituloEstilo.setVerticalAlignment(VerticalAlignment.CENTER);
        
        Font fuenteTitulo=book.createFont();
        fuenteTitulo.setFontName("Arial");
        fuenteTitulo.setBold(true);
        fuenteTitulo.setFontHeightInPoints((short)16);
        
        tituloEstilo.setFont(fuenteTitulo);
        
        Row filaTitulo=sheet.createRow(0);
        Cell celdaTitulo= filaTitulo.createCell(0);
        celdaTitulo.setCellStyle(tituloEstilo);
        celdaTitulo.setCellValue("Reporte Agua Potable");
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 4));//primeros 2 parametros rango de filas a ocupar, 3 y 4 parametros: rango de columnas a utilizar
        /*-------Fin de titulo------------------------------------------*/
        /*--------------Encabezado---------------------------------------*/
        CellStyle headerStyle = book.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);
        headerStyle.setBorderBottom(BorderStyle.THIN);
         
        Font font = book.createFont();
        font.setFontName("Arial");
        font.setBold(true);
        font.setColor(IndexedColors.WHITE.getIndex());
        font.setFontHeightInPoints((short) 12);
        headerStyle.setFont(font);
        
        Row filaEncabezados = sheet.createRow(3);
        
        int numeroColumnas=tablaReportes.getColumnCount();
        
        for (int i = 0; i <numeroColumnas; i++) {
            Cell celdaEnzabezado = filaEncabezados.createCell(i);
            celdaEnzabezado.setCellStyle(headerStyle);
            celdaEnzabezado.setCellValue(tablaReportes.getColumnName(i));
            
        }
        /*-+----------------------Fin de encabezado------------------------------------*/
        /*-------Insercion de filas y datos----------------------------------------------*/
        int filaExcel=4;//iniciamos despues del encabezado
        int contador=0;
        int numfilas=tablaReportes.getRowCount();
        while(contador<numfilas){
            Row filaDatos = sheet.createRow(filaExcel);
            for (int i = 0; i <numeroColumnas; i++) {
                Cell CeldaDatos = filaDatos.createCell(i);
                //if(tablaReportes.getColumnName(i).equals("importe pagado")){
                    
                //}
                CeldaDatos.setCellValue(tablaReportes.getValueAt(contador,i).toString());
                
            }
            filaExcel++;
            contador++;
        }
        
        for (int i = 0; i <numeroColumnas; i++) {//establecesmos que las columnas se ajusten a su contenido
            sheet.autoSizeColumn(i);
        }
        
        /*-------Fin de insercion de filas y datos-----------------------------------------*/
        
        
        
        FileOutputStream fileOut=null;
        try {
            fileOut = new FileOutputStream("reporteDeImportes.xlsx");
            book.write(fileOut);
            JOptionPane.showMessageDialog(null,"documento creado");
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }finally{
            try {
                if(fileOut!=null)fileOut.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        
                
    }
}
