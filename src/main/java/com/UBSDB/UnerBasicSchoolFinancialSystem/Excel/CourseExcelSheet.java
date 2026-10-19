package com.UBSDB.UnerBasicSchoolFinancialSystem.Excel;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Course.Course;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Course.CourseService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class CourseExcelSheet {

    @Autowired
    private CourseService courseService;

    /**
     * Creates the header row for the course Excel template.
     */
    private void writeHeaderLine(XSSFSheet sheet, XSSFWorkbook workbook) {

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();

        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);

        style.setFont(font);

        createCell(row, 0, "Course Name", style);
        createCell(row, 1, "Description", style);
        createCell(row, 2, "Notes", style);
        createCell(row, 3, "Subject Name", style);
        createCell(row, 4, "Term Week", style);
        createCell(row, 5, "Class", style);

        // Set column widths
        sheet.setColumnWidth(0, 25 * 256);
        sheet.setColumnWidth(1, 40 * 256);
        sheet.setColumnWidth(2, 40 * 256);
        sheet.setColumnWidth(3, 25 * 256);
        sheet.setColumnWidth(4, 30 * 256);
        sheet.setColumnWidth(5, 30 * 256);
    }

    /**
     * Creates a cell.
     */
    private void createCell(
            Row row,
            int columnCount,
            Object value,
            CellStyle style) {

        Cell cell = row.createCell(columnCount);

        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);

        } else if (value instanceof Double) {
            cell.setCellValue((Double) value);

        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);

        } else {
            cell.setCellValue(String.valueOf(value));
        }

        cell.setCellStyle(style);
    }

    /**
     * Export course Excel template.
     */
    public void export(HttpServletResponse response) throws IOException {

        XSSFWorkbook workbook = new XSSFWorkbook();

        try {

            XSSFSheet sheet = workbook.createSheet("Courses");

            writeHeaderLine(sheet, workbook);

            // Add example rows
            Row row1 = sheet.createRow(1);
            createCell(row1, 0, "Mathematics 101", null);
            createCell(row1, 1, "Introduction to Algebra", null);
            createCell(row1, 2, "Core subject for Grade 7", null);
            createCell(row1, 3, "Mathematics", null);
            createCell(row1, 4, "First Term - Week 1", null);

            Row row2 = sheet.createRow(2);
            createCell(row2, 0, "English 101", null);
            createCell(row2, 1, "Grammar and Composition", null);
            createCell(row2, 2, "", null);
            createCell(row2, 3, "English", null);
            createCell(row2, 4, "First Term - Week 1", null);
            createCell(row2, 5, "Basic1", null);

            ServletOutputStream outputStream =
                    response.getOutputStream();

            workbook.write(outputStream);

            outputStream.flush();

        } finally {

            workbook.close();
        }
    }
}
