package es.um.tds.controlador;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import es.um.tds.modelo.Cancion;
import es.um.tds.modelo.ListaCanciones;
import es.um.tds.modelo.Usuario;

public class GeneradorPDF {
	private static final String rutaPDF = "./";
	
	//TODO Mirar si Bea le quiere cambiar la fuente al PDF generado.
//	private static final Font TITLE_FONT = new Font(FontFamily.HELVETICA, 22, Font.BOLD);
//	private static final Font PLAYLIST_NAME_FONT = new Font(FontFamily.HELVETICA, 18, Font.UNDERLINE);
	
   /**
	* Genera un PDF con las listas del usuario
	*/
	public void generatePDF(Usuario usuario) throws FileNotFoundException, DocumentException {
	  List<ListaCanciones> listas = usuario.getListasCanciones();
	  Document pdf = crearGeneradorPDF();
	  pdf.open();

//	  pdf.add(
//	      new Paragraph("Hello " + usuario.getUsername() + ", these are your playlists", TITLE_FONT));
	  for (ListaCanciones lista : listas) {
	    pdf.add(new Paragraph(lista.getNombre()));
	    pdf.add(Chunk.SPACETABBING);
	    pdf.add(listaToTabla(lista));
	    pdf.add(Chunk.NEWLINE);
	  }

	  pdf.close();
	}

	private static PdfPTable listaToTabla(ListaCanciones lista) {
	  PdfPTable tabla = new PdfPTable(3);
	  tabla.addCell(new PdfPCell(new Paragraph("Título")));
	  tabla.addCell(new PdfPCell(new Paragraph("Intérprete")));
	  tabla.addCell(new PdfPCell(new Paragraph("Artista")));
	  tabla.setHeaderRows(1);
	  for (Cancion cancion : lista.getCanciones()) {
		  tabla.addCell(new PdfPCell(new Paragraph(cancion.getTitulo())));
		  tabla.addCell(new PdfPCell(new Paragraph(cancion.getInterprete())));
		  tabla.addCell(new PdfPCell(new Paragraph(cancion.getEstilo().getNombre())));
	  }
	  return tabla;
	}

	private static Document crearGeneradorPDF() throws FileNotFoundException, DocumentException {
	  FileOutputStream outputFile = new FileOutputStream(rutaPDF);
	  Document pdf = new Document();
	  PdfWriter.getInstance(pdf, outputFile);
	  return pdf;
	}
}
