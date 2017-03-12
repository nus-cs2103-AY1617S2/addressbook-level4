package seedu.address.commons.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;

import guitests.TaskManagerGuiTest;
import seedu.address.commons.exceptions.IllegalDateTimeValueException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Title;
import seedu.address.testutil.TestTask;

/**
 * Test utility for exporting to pdf file
 */
public class PdfUtilTest extends TaskManagerGuiTest {

    private TestTask[] tasks;

    //Fonts
    PdfFont bodyFont;
    PdfFont headerFont;

    //Styles
    Style bodyStyle;
    Style headerStyle;

    //Font locations
    public static final String FONT_HEADER = "src/main/resources/fonts/Roboto-Condensed.ttf";
    public static final String FONT_BODY = "src/main/resources/fonts/Roboto-Regular.ttf";

    @Before
    public void setup() throws IllegalValueException, IllegalDateTimeValueException, IOException {
        //Initialize dummy tasks
        tasks = Arrays.copyOf(this.td.getTypicalTasks(), this.td.getTypicalTasks().length + 1);
        TestTask expiredTask = new TestTask();
        expiredTask.setTitle(new Title("Late Task"));
        expiredTask.setDeadline(new Deadline("Yesterday"));
        tasks[tasks.length - 1] = expiredTask;

        //Initialize fonts
        headerFont = PdfFontFactory.createFont(FONT_HEADER, true);
        bodyFont = PdfFontFactory.createFont(FONT_BODY, true);

        //Initialize styles
        bodyStyle = new Style().setFont(bodyFont).setFontSize(12);
        headerStyle = new Style().setFont(bodyFont)
                                    .setFontSize(16)
                                    .setTextAlignment(TextAlignment.CENTER);
    }

    @Test
    public void testPDFExport() throws FileNotFoundException {
        PdfWriter writer = new PdfWriter("test.pdf");
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);
        document.add(createHeader("DoOrDie Task Manager"));

        //Export tasks with deadlines first
        document.add(createHeader("Deadlines"));
        Date today = new Date();
        for (TestTask task : tasks) {
            //TODO: add isDeadlineTask && !isCompleted to ensure only outstanding deadlines are displayed
            if (task.getDeadline().getDateTime().after(today)) {
                document.add(new Paragraph(task.toString()));
            }
        }

        /*
        //Export tasks with date ranges (includes bookings)
        document.add(createHeader("Schedule"));
        for (TestTask task : tasks) {
            //TODO: && isRangeTask (has start/end datetime)
            if (task.getDeadline().getDateTime().after(today)) {
                document.add(new Paragraph(task.toString()));
            }
        }
        */

        document.close();
    }

    /**
     * Returns a Paragraph object with header styles
     */
    private Paragraph createHeader(String text) {
        return new Paragraph(text).addStyle(headerStyle);

    }
}
