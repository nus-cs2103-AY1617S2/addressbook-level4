package seedu.address.commons.util;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.ColumnDocumentRenderer;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.AreaBreakType;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;

import guitests.TaskManagerGuiTest;
import seedu.address.commons.exceptions.IllegalDateTimeValueException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.testutil.TaskBuilder;
import seedu.address.testutil.TestTask;

/**
 * Test utility for exporting to pdf file
 */
public class PdfUtilTest extends TaskManagerGuiTest {

    private TestTask[] tasks;

    //Fonts
    PdfFont titleFont;
    PdfFont bodyFont;
    PdfFont headerFont;

    //Styles
    Style titleStyle;
    Style bodyStyle;
    Style headerStyle;

    //Font locations
    public static final String FONT_TITLE = "src/main/resources/fonts/YouMurdererBB-Regular.otf";
    public static final String FONT_HEADER = "src/main/resources/fonts/Roboto-Condensed.ttf";
    public static final String FONT_BODY = "src/main/resources/fonts/Roboto-Regular.ttf";

    @Before
    public void setup() throws IllegalValueException, IllegalDateTimeValueException, IOException {
        //Initialize dummy tasks
        tasks = Arrays.copyOf(this.td.getTypicalTasks(), this.td.getTypicalTasks().length + 6);
        tasks[tasks.length - 6] = (new TaskBuilder()).withTitle("Late Task").withDeadline("Yesterday").build();
        tasks[tasks.length - 5] = (new TaskBuilder()).withTitle("Complete Task 999")
                                    .withDeadline("11 November 2017 1520").build();
        tasks[tasks.length - 4] = (new TaskBuilder()).withTitle("Complete Task 888")
                                    .withDeadline("16 November 2017 1540").build();
        tasks[tasks.length - 3] = (new TaskBuilder()).withTitle("Complete Task 777")
                                    .withDeadline("23 November 2017 1720").build();
        tasks[tasks.length - 2] = (new TaskBuilder()).withTitle("Complete Task 666")
                                    .withDeadline("26 November 2017 1020").build();
        tasks[tasks.length - 1] = (new TaskBuilder()).withTitle("Complete Task 555")
                                    .withDeadline("23 November 2017 1220").build();

        //Initialize fonts
        titleFont = PdfFontFactory.createFont(FONT_TITLE, true);
        headerFont = PdfFontFactory.createFont(FONT_HEADER, true);
        bodyFont = PdfFontFactory.createFont(FONT_BODY, true);

        //Initialize styles
        titleStyle = new Style().setFont(titleFont).setFontSize(64).setTextAlignment(TextAlignment.CENTER);
        bodyStyle = new Style().setFont(bodyFont).setFontSize(12);
        headerStyle = new Style().setFont(bodyFont)
                                    .setFontSize(16)
                                    .setTextAlignment(TextAlignment.CENTER);
    }

    @Test
    public void testPDFExport() throws IOException {
        PdfWriter writer = new PdfWriter("test.pdf");
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);
        document.setTopMargin(0);
        document.add(createTitle("DoOrDie Task Manager"));

        //Export tasks with deadlines first
        document.add(createHeader("DEADLINES"));
        Date today = new Date();
        for (TestTask task : tasks) {
            //TODO: add isDeadlineTask && !isCompleted to ensure only outstanding deadlines are displayed
            if (task.getDeadline().get().getDateTime().after(today) && !task.getTitle().toString().contains("999")) {
                document.add(new Paragraph(task.getAsText()).setFontSize(10));
            }
        }

        //Export tasks with date ranges (includes bookings)
        //Group the tasks by dates
        int month = 10; //0 - 11
        LinkedList<TestTask>[] sortedTasks = new LinkedList[32]; //We use 1-31, ignore 0
        for (TestTask task : tasks) {
            //TODO: isRangeTask (has start/end datetime)
            if (month == task.getDeadline().get().getDateTime().getMonth()) {
                int date = task.getDeadline().get().getDateTime().getDate();
                if (sortedTasks[date] == null) {
                    sortedTasks[date] = new LinkedList<TestTask>();
                }
                sortedTasks[date].add(task);
            }
        }

        document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
        //Build header for schedule
        Paragraph scheduleHeader = createHeader("SCHEDULE").setMarginTop(15);
        document.add(scheduleHeader);
        Border rightBorder = new SolidBorder(Color.GRAY, 0.5f);

        //Break into 2 column format
        //Set column parameters
        float offSet = 36;
        float gutter = 23;
        float columnWidth = (PageSize.A4.getWidth() - offSet * 2) / 2 - gutter;
        float columnHeight = PageSize.A4.getHeight() - offSet * 2;

        //Define column areas
        Rectangle[] columns = {
            new Rectangle(offSet, offSet - 30, columnWidth, columnHeight),
            new Rectangle(offSet + columnWidth + gutter, offSet - 30, columnWidth, columnHeight)};
        document.setRenderer(new ColumnDocumentRenderer(document, columns));
        document.add(new AreaBreak(AreaBreakType.LAST_PAGE));

        float[] columnWidths = {1, 4};
        for (int i = 1; i < sortedTasks.length; i++) {
            LinkedList<TestTask> tasksOnDay = sortedTasks[i];
            if (tasksOnDay != null) {
                //New table for every day
                Table table = new Table(columnWidths);
                table.setWidthPercent(100);
                table.setHorizontalAlignment(HorizontalAlignment.CENTER);
                //Put date header on the left spanning all the rows
                Cell date = new Cell(tasksOnDay.size(), 1)
                                    .setTextAlignment(TextAlignment.RIGHT)
                                    .setBorder(Border.NO_BORDER)
                                    .setBorderRight(rightBorder)
                                    .setPaddingRight(10)
                                    .setWidth(50)
                                    .add(createBody(Integer.toString(i)));
                table.addCell(date);
                //Put each task on the row
                for (TestTask task : tasksOnDay) {
                    String taskTitle = task.getTitle().toString();
                    Cell taskTitleCell = new Cell(1, 2)
                            .setTextAlignment(TextAlignment.LEFT)
                            .setBorder(Border.NO_BORDER)
                            .setPaddingLeft(10);
                    do {
                        int maxLength = Math.min(taskTitle.length(), 24);
                        taskTitleCell.add(createBody(taskTitle.substring(0, maxLength)));
                        taskTitle = taskTitle.substring(maxLength);
                    } while (taskTitle.length() > 0);
                    table.addCell(taskTitleCell);
                }
                document.add(table);
                document.add(new Paragraph());
            } //end taskOnDay != null
        } //end sortedTask.length loop
        document.close();
    }

    /**
     * Returns a Paragraph object with body style
     */
    private Paragraph createBody(String text) {
        return new Paragraph(text).addStyle(bodyStyle);
    }

    /**
     * Returns a Paragraph object with title style
     */
    private Paragraph createTitle(String text) {
        return new Paragraph(text).addStyle(titleStyle);
    }

    /**
     * Returns a Paragraph object with header style
     */
    private Paragraph createHeader(String text) {
        return new Paragraph(text).addStyle(headerStyle);
    }
}
