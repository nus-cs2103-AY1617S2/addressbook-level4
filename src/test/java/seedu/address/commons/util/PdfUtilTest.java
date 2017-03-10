package seedu.address.commons.util;

import java.io.FileNotFoundException;

import org.junit.Test;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

/**
 * Test util for exporting to pdf
 */
public class PdfUtilTest {

    @Test
    public void testPDFExport() throws FileNotFoundException {
        PdfWriter writer = new PdfWriter("test.pdf");
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);
        document.add(new Paragraph("Hello World!"));
        document.close();
    }
}
