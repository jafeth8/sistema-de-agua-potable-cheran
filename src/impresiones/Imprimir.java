/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package impresiones;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;

import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JOptionPane;

/**
 *
 * @author jafeth888
 */
public class Imprimir {
    private static final Font fontEtiqueta = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLDITALIC);
    private static final Font fontDatos = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL);
    private static final Font fontAviso = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL,BaseColor.RED);
    private static final String rutaHeader = "C:/sistema-agua-potable/img-comprobante/logo_michoacan.png";
    
    public void imprimirComprobante(String idPago,String idDetallePago,String NombreCompleto,
        String domicilio,String barrio,String tipoPago,String periodo,String tipoTarifa,float precioTarifaAnual,
        String tipoDescuento,float precioDescuentoMensual,String descuentoAnual,String mesDePago,String fechaPago,String importe){
           
           Document documento=new Document();
           try {
               //String ruta=System.getProperty("user.home");
               String rutaDocumento="C:/sistema-agua-potable/comprobantes/comprobante-"+idDetallePago+"-"+NombreCompleto+"-"+fechaPago+".pdf";
               PdfWriter.getInstance(documento, new FileOutputStream(rutaDocumento));
               documento.open();
               Image header=Image.getInstance(rutaHeader);
               header.scaleToFit(100,1000);//primer parametro para el largo y el segundo para le escala de visualizacion;
               header.setAlignment(Chunk.ALIGN_LEFT);
               
               Paragraph parrafoEspacio=new Paragraph();
               parrafoEspacio.add("\n\n");
               
               Paragraph parrafoTituloRecibo=new Paragraph();
               parrafoTituloRecibo.setAlignment(Chunk.ALIGN_CENTER);
               parrafoTituloRecibo.add("Recibo de pago, codigo: "+idPago+" \n\n");
               parrafoTituloRecibo.setFont(FontFactory.getFont(FontFactory.HELVETICA,10,Font.BOLD,BaseColor.BLUE));
               
               Paragraph parrafoNumeroFactura=new Paragraph();
               Chunk chunkEtiquetaNumeroFactura= new Chunk("Numero de factura:  ",fontEtiqueta);
               Chunk chunkNumeroFactura = new Chunk(idDetallePago+"  ", fontDatos);
               parrafoNumeroFactura.setAlignment(Chunk.ALIGN_LEFT);
               parrafoNumeroFactura.add(chunkEtiquetaNumeroFactura);
               parrafoNumeroFactura.add(chunkNumeroFactura);
               
               Paragraph parrafoDatosUser=new Paragraph();
               Chunk chunkEtiquetaNombre = new Chunk("Nombre: ",fontEtiqueta);
               Chunk chunkNombre = new Chunk(NombreCompleto+"  ", fontDatos);
               
               Chunk chunkEtiquetaDomicilio = new Chunk("Domicilio: ",fontEtiqueta);
               Chunk chunkDomicilio = new Chunk(domicilio+"  ",fontDatos);
               Chunk chunkEtiquetaBarrio = new Chunk("Barrio: ",fontEtiqueta);
               Chunk chunkBarrio = new Chunk(barrio+"  ",fontDatos);
               parrafoDatosUser.setAlignment(Chunk.ALIGN_LEFT);
               parrafoDatosUser.add(chunkEtiquetaNombre);
               parrafoDatosUser.add(chunkNombre);
               parrafoDatosUser.add(chunkEtiquetaDomicilio);
               parrafoDatosUser.add(chunkDomicilio);
               parrafoDatosUser.add(chunkEtiquetaBarrio);
               parrafoDatosUser.add(chunkBarrio);
               parrafoDatosUser.add("\n\n");
               
               
               Paragraph parrafoTipoPago=new Paragraph();
               Chunk chunkEtiquetaTipoPago = new Chunk("Tipo de pago: ",fontEtiqueta);
               Chunk chunkTipoPago = new Chunk(tipoPago+"  ", fontDatos);
               Chunk chunkAvisoDescuentoAnual = new Chunk("*el tipo de pago 'mensual' no aplica descuento anual*"+"  ", fontAviso);
               Chunk chunkEtiquetaPeriodo = new Chunk("Periodo: ",fontEtiqueta);
               Chunk chunkPeriodo= new Chunk(periodo+"  ", fontDatos);
               parrafoTipoPago.setAlignment(Chunk.ALIGN_LEFT);
               parrafoTipoPago.add(chunkEtiquetaTipoPago);
               parrafoTipoPago.add(chunkTipoPago);
               if(tipoPago.equals("Mensual")){
                   parrafoTipoPago.add(chunkAvisoDescuentoAnual);
               }
               parrafoTipoPago.add(chunkEtiquetaPeriodo);
               parrafoTipoPago.add(chunkPeriodo);
               parrafoTipoPago.add("\n\n");
               
               Paragraph parrafoTituloTarifa=new Paragraph();
               parrafoTituloTarifa.setAlignment(Chunk.ALIGN_CENTER);
               
               parrafoTituloTarifa.add("Tarifa "+tipoPago+" \n\n"); //para que sea tarifa mensual o tarifa anual
               parrafoTituloTarifa.setFont(FontFactory.getFont(FontFactory.HELVETICA,10,Font.BOLD,BaseColor.BLUE));
               
               /*----------TABLA TARIFA----------------------*/               
               PdfPTable tablaTarifa=new PdfPTable(2);
               tablaTarifa.setWidthPercentage(100);
               tablaTarifa.addCell("Descripcion");
               tablaTarifa.addCell("Importe");
               
               tablaTarifa.addCell(tipoTarifa);
               if(tipoPago.equals("Mensual")){
                  float tarifaMensual=precioTarifaAnual/12;
                  tablaTarifa.addCell(" "+tarifaMensual);
               }else{
                  tablaTarifa.addCell(" "+precioTarifaAnual);
               }
               
               /*----------FIN DE TABLA TARIFA-------------------*/
               
               Paragraph parrafoTituloDescuento=new Paragraph();
               parrafoTituloDescuento.setAlignment(Chunk.ALIGN_CENTER);
               parrafoTituloDescuento.add("Tipo descuento \n\n");
               parrafoTituloDescuento.setFont(FontFactory.getFont(FontFactory.HELVETICA,10,Font.BOLD,BaseColor.BLUE));
               
               /*----------TABLA DESCUENTOS----------------------*/               
               PdfPTable tablaDatosUser=new PdfPTable(2);
               tablaDatosUser.setWidthPercentage(100);
               tablaDatosUser.addCell("Descripcion");
               tablaDatosUser.addCell("Descuento");
               
               tablaDatosUser.addCell(tipoDescuento);
               if(tipoPago.equals("Mensual")){
                   tablaDatosUser.addCell(" "+precioDescuentoMensual);
               }else{
                   float precioDescuentoAnual=precioDescuentoMensual*10;
                   tablaDatosUser.addCell(""+precioDescuentoAnual);
               }
               
               tablaDatosUser.addCell("Descuento Anual");
               tablaDatosUser.addCell(descuentoAnual);
               
               
               /*----------FIN DE TABLA DESCUENTOS-------------------*/
               
               Paragraph parrafoFinal=new Paragraph();
               Chunk chunkEtiquetaMesdePago = new Chunk("Mes que se pagó:    ",fontEtiqueta);
               Chunk chunkMesPago = new Chunk(mesDePago+"     ", fontDatos);
               Chunk chunkEtiquetaFechaPago = new Chunk("Fecha de pago:    ",fontEtiqueta);
               Chunk chunkFechaPago = new Chunk(fechaPago+"    ",fontDatos);
               Chunk chunkEtiquetaImporteTotal = new Chunk("Importe total:   ",fontEtiqueta);
               Chunk chunkImporteTotal = new Chunk(importe+"    ",fontDatos);
               parrafoFinal.setAlignment(Chunk.ALIGN_LEFT);
               parrafoFinal.add(chunkEtiquetaMesdePago);
               parrafoFinal.add(chunkMesPago);
               parrafoFinal.add(chunkEtiquetaFechaPago);
               parrafoFinal.add(chunkFechaPago);
               parrafoFinal.add(chunkEtiquetaImporteTotal);
               parrafoFinal.add(chunkImporteTotal);
               parrafoFinal.add("\n\n");
               
               Paragraph parrafoTituloSello=new Paragraph();
               parrafoTituloSello.setAlignment(Chunk.ALIGN_CENTER);
               parrafoTituloSello.add("*SELLO* \n\n");
               parrafoTituloSello.setFont(FontFactory.getFont(FontFactory.HELVETICA,10,Font.BOLD,BaseColor.BLUE));
               
               Paragraph parrafoSello=new Paragraph();
               parrafoSello.setAlignment(Chunk.ALIGN_CENTER);
               parrafoSello.add("_____________________________________\n\n");
               parrafoSello.setFont(FontFactory.getFont(FontFactory.HELVETICA,10,Font.BOLD,BaseColor.BLUE));
               documento.add(header);
         
               documento.add(parrafoTituloRecibo);
               documento.add(parrafoNumeroFactura);
               documento.add(parrafoDatosUser);
               documento.add(parrafoTipoPago);
               
               documento.add(parrafoTituloTarifa);
               documento.add(tablaTarifa);
               documento.add(parrafoEspacio);
               documento.add(parrafoTituloDescuento);
               documento.add(tablaDatosUser);
               documento.add(parrafoEspacio);
               documento.add(parrafoFinal);
               documento.add(parrafoEspacio);
               documento.add(parrafoTituloSello);
               documento.add(parrafoEspacio);
               documento.add(parrafoEspacio);
               documento.add(parrafoEspacio);
               documento.add(parrafoSello);
              
               JOptionPane.showMessageDialog(null,"comprobante generado");
              
               File path = new File (rutaDocumento);
               Desktop.getDesktop().open(path);
           } catch (Exception e) {
               JOptionPane.showMessageDialog(null, "hubo un error,"+e.getMessage()+" intentelo nuevamente","Error en al generar comprobante",JOptionPane.ERROR_MESSAGE);
           }finally{
               documento.close();
           }
           
    }
}
