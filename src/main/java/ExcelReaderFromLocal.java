import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Iterator;

public class ExcelReaderFromLocal {
    public static final String SAMPLE_XLSX_FILE_PATH = "C:\\Proyectos\\Ejemplos\\excelPOI\\src\\main\\java\\sample-xlsx-file.xlsx";
    public static final String  SAMPLE_XLSX_FILE_PATH_ORIGINAL="C:\\Proyectos\\Ejemplos\\excelPOI\\src\\main\\java\\sample-xlsx-file - copia.xlsx";

    public static void main(String[] args) throws IOException, InvalidFormatException {
try {
    Files.copy(Paths.get(SAMPLE_XLSX_FILE_PATH_ORIGINAL),Paths.get(SAMPLE_XLSX_FILE_PATH), StandardCopyOption.REPLACE_EXISTING);

    // Creating a Workbook from an Excel file (.xls or .xlsx)
    Workbook workbook = WorkbookFactory.create(new File(SAMPLE_XLSX_FILE_PATH));

    // Retrieving the number of sheets in the Workbook
    System.out.println("Workbook has " + workbook.getNumberOfSheets() + " Sheets : ");

        /*
           =============================================================
           Iterating over all the sheets in the workbook (Multiple ways)
           =============================================================
        */

    // 1. You can obtain a sheetIterator and iterate over it
    Iterator<Sheet> sheetIterator = workbook.sheetIterator();
    System.out.println("Retrieving Sheets using Iterator");
    while (sheetIterator.hasNext()) {
        Sheet sheet = sheetIterator.next();
        System.out.println("=> " + sheet.getSheetName());
    }

    // 2. Or you can use a for-each loop
    System.out.println("Retrieving Sheets using for-each loop");
    for (Sheet sheet : workbook) {
        System.out.println("=> " + sheet.getSheetName());
    }

    // 3. Or you can use a Java 8 forEach with lambda
    System.out.println("Retrieving Sheets using Java 8 forEach with lambda");
    workbook.forEach(sheet -> {
        System.out.println("=> " + sheet.getSheetName());
    });

        /*
           ==================================================================
           Iterating over all the rows and columns in a Sheet (Multiple ways)
           ==================================================================
        */

    // Getting the Sheet at index zero
    Sheet sheet = workbook.getSheetAt(0);

    // Create a DataFormatter to format and get each cell's value as String
    DataFormatter dataFormatter = new DataFormatter();

    // 1. You can obtain a rowIterator and columnIterator and iterate over them
    System.out.println("\n\nIterating over Rows and Columns using Iterator\n");
    Iterator<Row> rowIterator = sheet.rowIterator();
    while (rowIterator.hasNext()) {
        Row row = rowIterator.next();

        // Now let's iterate over the columns of the current row
        Iterator<Cell> cellIterator = row.cellIterator();

        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            String cellValue = dataFormatter.formatCellValue(cell);
            System.out.print(cellValue + "\t");
        }
        System.out.println();
    }

    // 2. Or you can use a for-each loop to iterate over the rows and columns
    System.out.println("\n\nIterating over Rows and Columns using for-each loop\n");
    for (Row row : sheet) {
        for (Cell cell : row) {
            String cellValue = dataFormatter.formatCellValue(cell);
            System.out.print(cellValue + "\t");
            printCellValue(cell);
        }
        System.out.println();
    }

    // 3. Or you can use Java 8 forEach loop with lambda
    System.out.println("\n\nIterating over Rows and Columns using Java 8 forEach with lambda\n");
    sheet.forEach(row -> {
        row.forEach(cell -> {
            String cellValue = dataFormatter.formatCellValue(cell);
            System.out.print(cellValue + "\t");
        });
        System.out.println();
    });

    // Closing the workbook
    workbook.close();
}catch (Exception ex){
    System.out.println("ERROR"+ex.getMessage());
}
    }

    private static void printCellValue(Cell cell) {
        switch (cell.getCellTypeEnum()) {
            case BOOLEAN:
                System.out.print(cell.getBooleanCellValue());
                break;
            case STRING:
                System.out.print(cell.getRichStringCellValue().getString());
                break;
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    System.out.print(cell.getDateCellValue());
                } else {
                    System.out.print(cell.getNumericCellValue());
                }
                break;
            case FORMULA:
                //System.out.println("Hola"+cell.getCellType().toString());
                //System.out.print(cell.getCellFormula());
               System.out.print(cell.getNumericCellValue());
                break;
            case BLANK:
                System.out.print("");
                break;
            default:
                System.out.print("");
        }

        System.out.print("\t");
    }
}