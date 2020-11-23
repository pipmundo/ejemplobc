
import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;


import java.io.IOException;

public class PdfIText {

    public static void main(String[] args) {

        try {
            writePDF();
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }

    }

    private static void writePDF() throws IOException {

        PdfDocument pdf = new PdfDocument(new PdfWriter("C:\\Proyectos\\Ejemplos\\excelPOI\\src\\main\\java\\resultado.pdf"));
        PdfFont code = PdfFontFactory.createFont(FontConstants.COURIER);
        Style style = new Style()
                .setFont(code)
                .setFontSize(12)
                .setFontColor(Color.RED)
                .setBackgroundColor(Color.LIGHT_GRAY);
        try (Document document = new Document(pdf)) {
            document.add(
                    new Paragraph()
                            .add("In this example, named ")
                            .add(new Text("HelloWorldStyles").addStyle(style))
                            .add(", we experiment with some text in ")
                            .add(new Text("code style").addStyle(style))
                            .add("."));


        Table table = new Table(3);
        Cell cell = new Cell(1, 3)
                .setTextAlignment(TextAlignment.CENTER)
                .add("Cell with colspan 3");
        table.addCell(cell);
        cell = new Cell(2, 1)
                .add("Cell with rowspan 2")
                .setVerticalAlignment(VerticalAlignment.MIDDLE);
        table.addCell(cell);

        ImageData imageData = ImageDataFactory.create("C:\\Proyectos\\Ejemplos\\excelPOI\\src\\main\\java\\imagen.jpg");
        Image pdfImg = new Image(imageData);

        table.addCell(pdfImg);
        table.addCell(new Cell().add("Cell 1.2"));
        table.addCell(new Cell()
                .add("Cell 2.1")
                .setBackgroundColor(Color.LIGHT_GRAY)
                .setMargin(5));
        table.addCell(new Cell()
                .add("Cell 1.2")
                .setBackgroundColor(Color.LIGHT_GRAY)
                .setPadding(5));
        document.add(table);
        }
    }


}