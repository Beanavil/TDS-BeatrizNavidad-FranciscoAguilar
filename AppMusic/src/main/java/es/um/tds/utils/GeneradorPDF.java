package es.um.tds.utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import es.um.tds.modelo.Cancion;
import es.um.tds.modelo.ListaCanciones;
import es.um.tds.modelo.Usuario;

/**
 * Generador de pdf con la lista de canciones de un usuario.
 * 
 * @author Beatriz y Francisco
 */
public class GeneradorPDF {
	private static final String DIRECTORIO_PDF = "./pdf";
	
	/**
	 * Genera un PDF con las listas del usuario
	 * @param usuario Usuario que solicita el pdf
	 * @throws FileNotFoundException
	 * @throws DocumentException
	 */
	public void generarPDF(Usuario usuario) throws FileNotFoundException, DocumentException {
	  List<ListaCanciones> listas = usuario.getListas();
	  Document pdf = crearPDF();
	  pdf.open();

	  pdf.add(new Paragraph("Listas de canciones de " + usuario.getLogin() + ":", 
			  new Font(FontFamily.HELVETICA, 20, Font.ITALIC)));
	  for (ListaCanciones lista : listas) {
	    pdf.add(new Paragraph(lista.getNombre(), 
	    		new Font(FontFamily.HELVETICA, 18, Font.BOLD)));
	    pdf.add(Chunk.SPACETABBING);
	    pdf.add(tablaFromListaCanciones(lista));
	    pdf.add(Chunk.NEWLINE);
	  }

	  pdf.close();
	}

	
	/**
	 * Convierte una lista de canciones en una PdfTable
	 * @param lista Lista a  convertir
	 * @return PdfTable con los datos de las canciones en sus filas
	 */
	private static PdfPTable tablaFromListaCanciones(ListaCanciones lista) {
	  PdfPTable tabla = new PdfPTable(3);
	  tabla.addCell(new PdfPCell(new Paragraph("Título")));
	  tabla.addCell(new PdfPCell(new Paragraph("Intérprete")));
	  tabla.addCell(new PdfPCell(new Paragraph("Estilo")));
	  tabla.setHeaderRows(1);
	  for (Cancion cancion : lista.getCanciones()) {
		  tabla.addCell(new PdfPCell(new Paragraph(cancion.getTitulo())));
		  tabla.addCell(new PdfPCell(new Paragraph(cancion.getInterprete())));
		  tabla.addCell(new PdfPCell(new Paragraph(cancion.getEstilo().getNombre())));
	  }
	  return tabla;
	}

	
	/**
	 * Crea un documento pdf en el directorio establecido por defecto
	 * @return Documento pdf
	 * @throws FileNotFoundException
	 * @throws DocumentException
	 */
	private static Document crearPDF() throws FileNotFoundException, DocumentException {
	  FileOutputStream outputFile = new FileOutputStream(DIRECTORIO_PDF);
	  Document pdf = new Document();
	  PdfWriter.getInstance(pdf, outputFile);
	  return pdf;
	}
}
