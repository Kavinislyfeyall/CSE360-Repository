import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import EasyXLS.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.time.LocalTime;
import java.time.Duration;
import java.time.LocalDate;
/**
 * Initializes the excel sheet along with adding, removing, editting, and exporting data from the sheet
 *
 * @author Michael Harper
 */
public class DatabaseHandler
{
    private ExcelDocument workbook;
    private ExcelTable spreadsheet;
    private ExcelTable spreadsheetList;
    private String file = "EffortLoggerDatabase.xlsx";
    private int columnCount;    
    public void Create()
    {
        File file1 = new File(file);
        if(file1.exists()){
            Update();
        }
        else
        {                
            workbook = new ExcelDocument(2);
            workbook.easy_getSheetAt(0).setSheetName("Project Data");
            workbook.easy_getSheetAt(1).setSheetName("List Information");
            workbook.easy_WriteXLSXFile("EffortLoggerDatabase.xlsx");
            spreadsheet = ((ExcelWorksheet)workbook.easy_getSheet("Project Data")).easy_getExcelTable();    
            spreadsheetList = ((ExcelWorksheet)workbook.easy_getSheet("List Information")).easy_getExcelTable(); 
            closeSheet();
            initSheet();
        }
    } 
    public void initSheet()
    {
            Update();
            int rowCount = spreadsheet.RowCount();             
            columnCount = 0;
           
           
            spreadsheet.easy_getCell(rowCount,columnCount).setFontSize(24);
            String temp = "Effort Logger:";
            spreadsheet.easy_getCell(rowCount,columnCount).setValue(temp.toString());   
            temp = "Number:";            
            spreadsheet.easy_getCell(rowCount+1,columnCount).setValue(temp.toString());
            temp = "Date:";
            spreadsheet.easy_getCell(rowCount+1,columnCount+1).setValue(temp.toString()); 
            temp = "Start:";
            spreadsheet.easy_getCell(rowCount+1,columnCount+2).setValue(temp.toString()); 
            temp = "Stop:";
            spreadsheet.easy_getCell(rowCount+1,columnCount+3).setValue(temp.toString()); 
            temp = "Time:";
            spreadsheet.easy_getCell(rowCount+1,columnCount+4).setValue(temp.toString()); 
            temp = "Life Cycle Step:";
            spreadsheet.easy_getCell(rowCount+1,columnCount+5).setValue(temp.toString());
            temp = "Effort Category:";
            spreadsheet.easy_getCell(rowCount+1,columnCount+6).setValue(temp.toString());
            temp = "Deliverable:";
            spreadsheet.easy_getCell(rowCount+1,columnCount+7).setValue(temp.toString());
            temp = "Other:";
            spreadsheet.easy_getCell(rowCount+1,columnCount+8).setValue(temp.toString());
            spreadsheet.setColumnWidth(1,80);
            spreadsheet.setColumnWidth(2,80);
            spreadsheet.setColumnWidth(3,80);
            spreadsheet.setColumnWidth(5,120);
            spreadsheet.setColumnWidth(6,120);
            spreadsheet.setColumnWidth(7,120);
            spreadsheetList.easy_getCell(rowCount,columnCount).setFontSize(24);
            temp = "Effort Logger List:";
            spreadsheetList.easy_getCell(rowCount,columnCount).setValue(temp.toString()); 
            temp = "Projects:";
            spreadsheetList.easy_getCell(rowCount+1,columnCount).setValue(temp.toString()); 
            temp = "Life Cycle Steps:";
            spreadsheetList.easy_getCell(rowCount+1,columnCount+1).setValue(temp.toString()); 
            temp = "Effort Categories:";
            spreadsheetList.easy_getCell(rowCount+1,columnCount+2).setValue(temp.toString()); 
            temp = "Plan Categories:";
            spreadsheetList.easy_getCell(rowCount+1,columnCount+3).setValue(temp.toString()); 
            spreadsheetList.setColumnWidth(1,120);
            spreadsheetList.setColumnWidth(2,120);            
            closeSheet();        
    }
    /*Checks for an existing spreadsheet and pulls data from it*/
    public void Update()
    {
        try {            
            FileInputStream inputStream = new FileInputStream(new File(file));
            workbook = new ExcelDocument(0);
            workbook.easy_LoadXLSXFile(file);
            spreadsheet = ((ExcelWorksheet)workbook.easy_getSheet("Project Data")).easy_getExcelTable();
            spreadsheetList = ((ExcelWorksheet)workbook.easy_getSheet("List Information")).easy_getExcelTable();       
            inputStream.close();     
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*Adds items to a spreadsheet*/
    public void addData(String projectType, String lifecycleStep, String effortCategory, LocalTime startTime, LocalTime stopTime, String deliverable, String other)
    {
        Update();
        int rowCount = spreadsheet.RowCount();  
        columnCount = 0;
        int entryNumber = rowCount - 1;
        spreadsheet.easy_getCell(rowCount,columnCount).setValue(entryNumber + "");
        spreadsheet.easy_getCell(rowCount,columnCount+1).setValue(LocalDate.now()+"");    
        spreadsheet.easy_getCell(rowCount,columnCount+2).setValue(startTime.withNano(0).toString()); 
        spreadsheet.easy_getCell(rowCount,columnCount+3).setValue(stopTime.withNano(0).toString()); 
        spreadsheet.easy_getCell(rowCount,columnCount+4).setValue(Long.toString((Duration.between(startTime, stopTime).toMinutes()))); 
        spreadsheet.easy_getCell(rowCount,columnCount+5).setValue(lifecycleStep); 
        spreadsheet.easy_getCell(rowCount,columnCount+6).setValue(effortCategory); 
        spreadsheet.easy_getCell(rowCount,columnCount+7).setValue(deliverable);
        spreadsheet.easy_getCell(rowCount,columnCount+8).setValue(other);
        closeSheet();
    }
    public void addData(String projectType, String lifecycleStep, String effortCategory, LocalTime startTime, LocalTime stopTime, String deliverable)
    {
        Update();
        int rowCount = spreadsheet.RowCount();  
        columnCount = 0;
        int entryNumber = rowCount - 1;
        spreadsheet.easy_getCell(rowCount,columnCount).setValue(entryNumber + "");
        spreadsheet.easy_getCell(rowCount,columnCount+1).setValue(LocalDate.now()+"");    
        spreadsheet.easy_getCell(rowCount,columnCount+2).setValue(startTime.withNano(0).toString()); 
        spreadsheet.easy_getCell(rowCount,columnCount+3).setValue(stopTime.withNano(0).toString()); 
        spreadsheet.easy_getCell(rowCount,columnCount+4).setValue(Long.toString((Duration.between(startTime, stopTime).toMinutes()))); 
        spreadsheet.easy_getCell(rowCount,columnCount+5).setValue(lifecycleStep); 
        spreadsheet.easy_getCell(rowCount,columnCount+6).setValue(effortCategory); 
        spreadsheet.easy_getCell(rowCount,columnCount+7).setValue(deliverable);        
        closeSheet();
    } 
    /*Add a new entry to the choiceboxes*/
    public void addListProject(String projectType)
    {
        Update();
        int rowCount = spreadsheetList.easy_getRowAt(0).Count()-2;                 
        spreadsheet.easy_getCell(rowCount,columnCount).setValue(projectType + "");        
        closeSheet();
    }  
    public void addListLC(String lifecycleStep)
    {
        Update();
        int rowCount = spreadsheetList.easy_getRowAt(0).Count()-2;                 
        spreadsheet.easy_getCell(rowCount,columnCount).setValue(lifecycleStep + "");        
        closeSheet();
    }   
    public void addListEffort(String projectType, String lifecycleStep, String effortCategory, LocalTime startTime, LocalTime stopTime, String deliverable)
    {
        Update();
        int rowCount = spreadsheet.RowCount();  
        columnCount = 0;
        int entryNumber = rowCount - 1;
        spreadsheet.easy_getCell(rowCount,columnCount).setValue(entryNumber + "");
        spreadsheet.easy_getCell(rowCount,columnCount+1).setValue(LocalDate.now()+"");    
        spreadsheet.easy_getCell(rowCount,columnCount+2).setValue(startTime.withNano(0).toString()); 
        spreadsheet.easy_getCell(rowCount,columnCount+3).setValue(stopTime.withNano(0).toString()); 
        spreadsheet.easy_getCell(rowCount,columnCount+4).setValue(Long.toString((Duration.between(startTime, stopTime).toMinutes()))); 
        spreadsheet.easy_getCell(rowCount,columnCount+5).setValue(lifecycleStep); 
        spreadsheet.easy_getCell(rowCount,columnCount+6).setValue(effortCategory); 
        spreadsheet.easy_getCell(rowCount,columnCount+7).setValue(deliverable);        
        closeSheet();
    }   
    public void addListPlan(String projectType, String lifecycleStep, String effortCategory, LocalTime startTime, LocalTime stopTime, String deliverable)
    {
        Update();
        int rowCount = spreadsheet.RowCount();  
        columnCount = 0;
        int entryNumber = rowCount - 1;
        spreadsheet.easy_getCell(rowCount,columnCount).setValue(entryNumber + "");
        spreadsheet.easy_getCell(rowCount,columnCount+1).setValue(LocalDate.now()+"");    
        spreadsheet.easy_getCell(rowCount,columnCount+2).setValue(startTime.withNano(0).toString()); 
        spreadsheet.easy_getCell(rowCount,columnCount+3).setValue(stopTime.withNano(0).toString()); 
        spreadsheet.easy_getCell(rowCount,columnCount+4).setValue(Long.toString((Duration.between(startTime, stopTime).toMinutes()))); 
        spreadsheet.easy_getCell(rowCount,columnCount+5).setValue(lifecycleStep); 
        spreadsheet.easy_getCell(rowCount,columnCount+6).setValue(effortCategory); 
        spreadsheet.easy_getCell(rowCount,columnCount+7).setValue(deliverable);        
        closeSheet();
    }   
    /*Removes items from a spreadsheet*/
    public void Remove(String index)
    {
        Update();
        System.out.println(index + "");
        int rowCount = spreadsheet.RowCount();
        for (int i = 2; i < rowCount; i++) {
            ExcelRow row = spreadsheet.easy_getRowAt(i);            
            if(row.easy_getCell(0).getValue().equals(index))
            {
                System.out.println("Remove " + row.easy_getCell(0).getValue());
                spreadsheet.easy_removeRow(i);
                rowCount--;                
                for(int j = i; j < rowCount; j++)
                {                    
                    ExcelRow rowU = spreadsheet.easy_getRowAt(j);
                    int temp = Integer.parseInt(rowU.easy_getCell(0).getValue())-1;
                    spreadsheet.easy_getCell(j,0).setValue(temp + "");
                }                
            }
        }
        
        closeSheet();
    }

    /*Writes to and then closes the spreadsheet*/
    public void closeSheet()
    {
        try{
            FileOutputStream outputStream = new FileOutputStream("EffortLoggerDatabase.xlsx");
            workbook.easy_WriteXLSXFile("EffortLoggerDatabase.xlsx");
            workbook.Dispose();
            outputStream.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void editSheet(String Project, String Step, String effortCategory, String Deliverable)
    {
        int rowCount = spreadsheet.RowCount();
        int index = findindexSheet(Project, Step, effortCategory, Deliverable);
        if (index == 0)
        {
            
        }
    }   
    
    public void printSheet()
    {
        Update();    
        //as of now prints out the values for every row
        ExcelRow row;
        int rowCount = spreadsheet.RowCount();       
        for(int i = 2; i < rowCount;i++)
        {
            row = spreadsheet.easy_getRowAt(i);
            System.out.println(row.easy_getCell(0).getValue() + ";created " + row.easy_getCell(1).getValue() + ";"
                + row.easy_getCell(2).getValue() + ";"  + row.easy_getCell(3).getValue() + ";"  + row.easy_getCell(4).getValue()
                + ";"  + row.easy_getCell(5).getValue() + ";" + row.easy_getCell(6).getValue() + ";");
        }
        closeSheet();    
    }
    public String printindexSheet(int index)
    {
       Update();    
        //Returns row of specific index
        int rowCount = spreadsheet.RowCount();
        ExcelRow row;                    
        row = spreadsheet.easy_getRowAt(index);
        for (int i = 2; i < rowCount; i++) {
            row = spreadsheet.easy_getRowAt(i);            
            if(row.easy_getCell(0).getValue().equals(index))
                {
                closeSheet();
                return row.easy_getCell(0).getValue() + ";created " + row.easy_getCell(1).getValue() + ";"
                + row.easy_getCell(2).getValue() + ";"  + row.easy_getCell(3).getValue() + ";"  + row.easy_getCell(4).getValue()
                + ";"  + row.easy_getCell(5).getValue() + ";" + row.easy_getCell(6).getValue() + ";";                 
                }
            }
        return "";
    }
    public int findindexSheet(String Project, String Step, String effortCategory, String Deliverable)
    {
       Update();    
        //Returns row of specific index
        ExcelRow row;         
        int rowCount = spreadsheet.RowCount();
        for(int i = 2; i < rowCount;i++)
        {
            row = spreadsheet.easy_getRowAt(i);
            if(Project.equals(row.easy_getCell(5)) && Step.equals(row.easy_getCell(6)) && effortCategory.equals(row.easy_getCell(7)) && Deliverable.equals(row.easy_getCell(8)))
            {
                return i;
            }
        }       
        closeSheet();  
        return 0;                        
    }
}
