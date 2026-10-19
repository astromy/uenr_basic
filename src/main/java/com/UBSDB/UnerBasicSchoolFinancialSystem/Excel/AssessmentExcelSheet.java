package com.UBSDB.UnerBasicSchoolFinancialSystem.Excel;

import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.Student;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Student.StudentService;
import com.UBSDB.UnerBasicSchoolFinancialSystem.TKS.TeachrKlassSubject;
import com.UBSDB.UnerBasicSchoolFinancialSystem.Term.Term;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AssessmentExcelSheet {

    private List<TeachrKlassSubject> TKS;

    @Autowired
    private StudentService SS;

    public List<TeachrKlassSubject> getTKS() {
        return TKS;
    }

    public void setTKS(List<TeachrKlassSubject> TKS) {
        this.TKS = TKS;
    }

    public StudentService getSS() {
        return SS;
    }

    public void setSS(StudentService SS) {
        this.SS = SS;
    }

    /**
     * Creates the header row.
     */
    private void writeHeaderLine(XSSFSheet sheet, XSSFWorkbook workbook) {

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();

        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);

        style.setFont(font);

        createCell(row, 0, "Student Id", style);
        createCell(row, 1, "Sur Name", style);
        createCell(row, 2, "First Class Test(15)", style);
        createCell(row, 3, "Second Class Test(15)", style);
        createCell(row, 4, "Class Work(15)", style);
        createCell(row, 5, "Project Work(15)", style);
        createCell(row, 6, "Exam Score(100)", style);

        // Set column widths
        sheet.setColumnWidth(0, 20 * 256);
        sheet.setColumnWidth(1, 35 * 256);
        sheet.setColumnWidth(2, 22 * 256);
        sheet.setColumnWidth(3, 23 * 256);
        sheet.setColumnWidth(4, 20 * 256);
        sheet.setColumnWidth(5, 22 * 256);
        sheet.setColumnWidth(6, 20 * 256);
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
     * Adds numeric validation to an Excel column.
     *
     * Example:
     * First Test = 0 to 15
     * Exam = 0 to 100
     */
    private void addScoreValidation(
            XSSFSheet sheet,
            int column,
            int min,
            int max,
            int firstRow,
            int lastRow) {

        DataValidationHelper validationHelper =
                sheet.getDataValidationHelper();

        DataValidationConstraint constraint =
                validationHelper.createNumericConstraint(
                        DataValidationConstraint.ValidationType.INTEGER,
                        DataValidationConstraint.OperatorType.BETWEEN,
                        String.valueOf(min),
                        String.valueOf(max)
                );

        CellRangeAddressList addressList =
                new CellRangeAddressList(
                        firstRow,
                        lastRow,
                        column,
                        column
                );

        DataValidation validation =
                validationHelper.createValidation(
                        constraint,
                        addressList
                );

        validation.setShowErrorBox(true);
        validation.setShowPromptBox(true);

        validation.createErrorBox(
                "Invalid Score",
                "Please enter a score between "
                        + min
                        + " and "
                        + max
                        + "."
        );

        validation.createPromptBox(
                "Score",
                "Enter a score between "
                        + min
                        + " and "
                        + max
                        + "."
        );

        sheet.addValidationData(validation);
    }

    /**
     * Export assessment Excel file.
     */
    public void export(
            HttpServletResponse response,
            String ClassName,
            Term t) throws IOException {

        XSSFWorkbook workbook = new XSSFWorkbook();

        try {

            if (TKS == null || TKS.isEmpty()) {
                return;
            }

            for (TeachrKlassSubject c : TKS) {

                String text =
                        c.getKlas().getClassName()
                                + "_"
                                + c.getSubjects().getSubjectName()
                                + "_"
                                + c.getTeacher().getStaffId()
                                + "_"
                                + t.getTermName();

                /*
                 * Excel sheet names cannot exceed 31 characters.
                 */
                if (text.length() > 31) {
                    text = text.substring(0, 31);
                }

                /*
                 * Remove characters that Excel does not allow
                 * in sheet names.
                 */
                text = text.replace("/", "_")
                        .replace("\\", "_")
                        .replace("?", "_")
                        .replace("*", "_")
                        .replace("[", "_")
                        .replace("]", "_")
                        .replace(":", "_");

                XSSFSheet sheet =
                        workbook.createSheet(text);

                writeHeaderLine(sheet, workbook);

                writeDataLines(
                        c,
                        sheet,
                        workbook
                );
            }

            ServletOutputStream outputStream =
                    response.getOutputStream();

            workbook.write(outputStream);

            outputStream.flush();

        } finally {

            workbook.close();
        }
    }

    /**
     * Writes students into the Excel sheet.
     */
    private void writeDataLines(
            TeachrKlassSubject TKS,
            XSSFSheet sheet,
            XSSFWorkbook workbook) {

        int rowCount = 1;

        List<Student> students =
                SS.GetStudents_In_A_Particular_Class(
                        TKS.getKlas()
                );

        CellStyle style =
                workbook.createCellStyle();

        XSSFFont font =
                workbook.createFont();

        font.setFontHeight(14);

        style.setFont(font);

        /*
         * If there are no students, create one empty row.
         */
        if (students == null || students.isEmpty()) {

            students.add(
                    new Student(
                            "0",
                            "",
                            "",
                            ""
                    )
            );
        }

        /*
         * Sort students by surname.
         */
        List<Student> sortedStudents =
                students.stream()
                        .sorted(
                                Comparator.comparing(
                                        Student::getSurname,
                                        Comparator.nullsFirst(
                                                String::compareToIgnoreCase
                                        )
                                )
                        )
                        .collect(Collectors.toList());

        for (Student student : sortedStudents) {

            String firstName =
                    student.getFirstName() == null
                            ? ""
                            : student.getFirstName();

            String lastName =
                    student.getLastName() == null
                            ? ""
                            : student.getLastName();

            String surname =
                    student.getSurname() == null
                            ? ""
                            : student.getSurname();

            String fullName =
                    firstName
                            + " "
                            + lastName
                            + " "
                            + surname;

            Row row =
                    sheet.createRow(rowCount++);

            int columnCount = 0;

            /*
             * Column 0
             * Student ID
             */
            createCell(
                    row,
                    columnCount++,
                    student.getStudentId(),
                    style
            );

            /*
             * Column 1
             * Student Name
             */
            createCell(
                    row,
                    columnCount++,
                    fullName.trim(),
                    style
            );

            /*
             * Column 2
             * First Class Test
             * Minimum = 0
             * Maximum = 15
             */
            createCell(
                    row,
                    columnCount++,
                    0,
                    style
            );

            /*
             * Column 3
             * Second Class Test
             * Minimum = 0
             * Maximum = 15
             */
            createCell(
                    row,
                    columnCount++,
                    0,
                    style
            );

            /*
             * Column 4
             * Class Work
             * Minimum = 0
             * Maximum = 15
             */
            createCell(
                    row,
                    columnCount++,
                    0,
                    style
            );

            /*
             * Column 5
             * Project Work
             * Minimum = 0
             * Maximum = 15
             */
            createCell(
                    row,
                    columnCount++,
                    0,
                    style
            );

            /*
             * Column 6
             * Exam
             * Minimum = 0
             * Maximum = 100
             */
            createCell(
                    row,
                    columnCount++,
                    0,
                    style
            );
        }

        /*
         * Last student row.
         *
         * Row 0 = Header
         * Row 1 = First student
         */
        int lastRow = rowCount - 1;

        /*
         * FIRST TEST
         * Column C
         * 0 - 15
         */
        addScoreValidation(
                sheet,
                2,
                0,
                15,
                1,
                lastRow
        );

        /*
         * SECOND TEST
         * Column D
         * 0 - 15
         */
        addScoreValidation(
                sheet,
                3,
                0,
                15,
                1,
                lastRow
        );

        /*
         * CLASS WORK
         * Column E
         * 0 - 15
         */
        addScoreValidation(
                sheet,
                4,
                0,
                15,
                1,
                lastRow
        );

        /*
         * PROJECT WORK
         * Column F
         * 0 - 15
         */
        addScoreValidation(
                sheet,
                5,
                0,
                15,
                1,
                lastRow
        );

        /*
         * EXAM
         * Column G
         * 0 - 100
         */
        addScoreValidation(
                sheet,
                6,
                0,
                100,
                1,
                lastRow
        );
    }
}