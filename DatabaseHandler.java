import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import EasyXLS.*;
import java.time.LocalTime;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;

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
    private ExcelTable encSpreadsheet;
    private String file = "EffortLoggerDatabase.xlsx";
    private int columnCount; 
    /*Create the spreadsheet and name the sheets, call initSheet to initialize the spreadsheet*/
    public void Create()
    {
        File file1 = new File(file);
        if(file1.exists()){
            Update();
        }
        else
        {                
            workbook = new ExcelDocument(3);
            workbook.easy_getSheetAt(0).setSheetName("Project Data");
            workbook.easy_getSheetAt(1).setSheetName("List Information");
            workbook.easy_getSheetAt(2).setSheetName("Encryption Validator");
            workbook.easy_getSheetAt(2).setSheetProtected(true);
            workbook.easy_WriteXLSXFile("EffortLoggerDatabase.xlsx");
            spreadsheet = ((ExcelWorksheet)workbook.easy_getSheet("Project Data")).easy_getExcelTable();    
            spreadsheetList = ((ExcelWorksheet)workbook.easy_getSheet("List Information")).easy_getExcelTable(); 
            encSpreadsheet = ((ExcelWorksheet)workbook.easy_getSheet("Encryption Validator")).easy_getExcelTable();
            closeSheet();
            initSheet();
        }
    } 
    /*Is called when the program first creates a spreadsheet to set certain values and cell widths*/
    public void initSheet()
    {
            Update();
            int rowCount = spreadsheet.RowCount();             
            columnCount = 0;
            
            encSpreadsheet.easy_getCell(0,0).setValue("0");
           
           
            spreadsheet.easy_getCell(rowCount,columnCount).setFontSize(24);
            String temp = "Effort Logger:";
            spreadsheet.easy_getCell(rowCount,columnCount).setValue(temp.toString());   
            temp = "Number:";            
            spreadsheet.easy_getCell(rowCount+1,columnCount).setValue(temp.toString());
            temp = "Project:";
            spreadsheet.easy_getCell(rowCount+1,columnCount+1).setValue(temp.toString());
            temp = "Date:";
            spreadsheet.easy_getCell(rowCount+1,columnCount+2).setValue(temp.toString()); 
            temp = "Start:";
            spreadsheet.easy_getCell(rowCount+1,columnCount+3).setValue(temp.toString()); 
            temp = "Stop:";
            spreadsheet.easy_getCell(rowCount+1,columnCount+4).setValue(temp.toString()); 
            temp = "Time:";
            spreadsheet.easy_getCell(rowCount+1,columnCount+5).setValue(temp.toString()); 
            temp = "Life Cycle Step:";
            spreadsheet.easy_getCell(rowCount+1,columnCount+6).setValue(temp.toString());
            temp = "Effort Category:";
            spreadsheet.easy_getCell(rowCount+1,columnCount+7).setValue(temp.toString());
            temp = "Deliverable:";
            spreadsheet.easy_getCell(rowCount+1,columnCount+8).setValue(temp.toString());
            temp = "Other:";
            spreadsheet.easy_getCell(rowCount+1,columnCount+9).setValue(temp.toString());
            spreadsheet.setColumnWidth(1,80);
            spreadsheet.setColumnWidth(2,80);
            spreadsheet.setColumnWidth(3,80);
            spreadsheet.setColumnWidth(4,120);
            spreadsheet.setColumnWidth(5,120);
            spreadsheet.setColumnWidth(6,120);
            spreadsheet.setColumnWidth(7,120);
            spreadsheet.setColumnWidth(8,120);
            spreadsheetList.easy_getCell(rowCount,columnCount).setFontSize(24);
            temp = "Effort Logger List:";
            spreadsheetList.easy_getCell(rowCount,columnCount).setValue(temp.toString()); 
            temp = "Projects:";
            spreadsheetList.easy_getCell(rowCount+1,columnCount).setValue(temp.toString()); 
            temp = "Life Cycle Steps:";
            spreadsheetList.easy_getCell(rowCount+1,columnCount+1).setValue(temp.toString()); 
            temp = "Corresponding Project:";
            spreadsheetList.easy_getCell(rowCount+1,columnCount+2).setValue(temp.toString()); 
            temp = "Effort Categories:";
            spreadsheetList.easy_getCell(rowCount+1,columnCount+3).setValue(temp.toString()); 
            temp = "Plan Categories:";
            spreadsheetList.easy_getCell(rowCount+1,columnCount+4).setValue(temp.toString()); 
            temp = "Corresponding Effort:";
            spreadsheetList.easy_getCell(rowCount+1,columnCount+5).setValue(temp.toString());
            spreadsheetList.setColumnWidth(1,120);
            spreadsheetList.setColumnWidth(2,120);    
            spreadsheetList.setColumnWidth(3,120); 
            spreadsheetList.setColumnWidth(4,120); 
            spreadsheetList.setColumnWidth(5,120);
            closeSheet();        
    }
    /*Checks for an existing spreadsheet and pulls the sheets from it*/
    public void Update()
    {
        try {            
            FileInputStream inputStream = new FileInputStream(new File(file));
            workbook = new ExcelDocument(0);
            workbook.easy_LoadXLSXFile(file);
            spreadsheet = ((ExcelWorksheet)workbook.easy_getSheet("Project Data")).easy_getExcelTable();
            spreadsheetList = ((ExcelWorksheet)workbook.easy_getSheet("List Information")).easy_getExcelTable();  
            encSpreadsheet = ((ExcelWorksheet)workbook.easy_getSheet("Encryption Validator")).easy_getExcelTable();
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
        spreadsheet.easy_getCell(rowCount,columnCount+1).setValue(projectType);
        spreadsheet.easy_getCell(rowCount,columnCount+2).setValue(LocalDate.now()+"");    
        spreadsheet.easy_getCell(rowCount,columnCount+3).setValue(startTime.withNano(0).toString()); 
        spreadsheet.easy_getCell(rowCount,columnCount+4).setValue(stopTime.withNano(0).toString()); 
        spreadsheet.easy_getCell(rowCount,columnCount+5).setValue(Long.toString((Duration.between(startTime, stopTime).toMinutes()))); 
        spreadsheet.easy_getCell(rowCount,columnCount+6).setValue(lifecycleStep); 
        spreadsheet.easy_getCell(rowCount,columnCount+7).setValue(effortCategory); 
        spreadsheet.easy_getCell(rowCount,columnCount+8).setValue(deliverable);
        spreadsheet.easy_getCell(rowCount,columnCount+9).setValue(other);
        closeSheet();
    }
    public void addData(String projectType, String lifecycleStep, String effortCategory, LocalTime startTime, LocalTime stopTime, String deliverable)
    {
        Update();
        int rowCount = spreadsheet.RowCount();  
        columnCount = 0;
        int entryNumber = rowCount - 1;
        spreadsheet.easy_getCell(rowCount,columnCount).setValue(entryNumber + "");
        spreadsheet.easy_getCell(rowCount,columnCount+1).setValue(projectType);
        spreadsheet.easy_getCell(rowCount,columnCount+2).setValue(LocalDate.now()+"");    
        spreadsheet.easy_getCell(rowCount,columnCount+3).setValue(startTime.withNano(0).toString()); 
        spreadsheet.easy_getCell(rowCount,columnCount+4).setValue(stopTime.withNano(0).toString()); 
        spreadsheet.easy_getCell(rowCount,columnCount+5).setValue(Long.toString((Duration.between(startTime, stopTime).toMinutes()))); 
        spreadsheet.easy_getCell(rowCount,columnCount+6).setValue(lifecycleStep); 
        spreadsheet.easy_getCell(rowCount,columnCount+7).setValue(effortCategory); 
        spreadsheet.easy_getCell(rowCount,columnCount+8).setValue(deliverable);        
        closeSheet();
    } 
    /*All findlist and addlist methods add and search entries for the the choiceboxes*/
    /*Add a project name to the database list*/
    public void addListProject(String projectType)
    {
        Update();
        int i = 1;        
        ExcelRow row = spreadsheetList.easy_getRowAt(i); 
        while(!row.easy_getCell(0).getValue().isEmpty())
        {             
            if(projectType.equals(row.easy_getCell(0).getValue()))
            {
                break;
            }
            i++; 
            try
                  {
                    row = spreadsheetList.easy_getRowAt(i);  
                  }
                  catch(IndexOutOfBoundsException e)
                  {
                    spreadsheetList.easy_getCell(i,0).setValue(projectType + "");
                     break; 
                  }
                  if(row.easy_getCell(0).getValue().isEmpty())
            {
                    spreadsheetList.easy_getCell(i,0).setValue(projectType + "");
                    break; 
            }
        }              
        closeSheet();
    }  
    
    public void removeListProject(String projectType)
    {        
        int i = 1;        
        ExcelRow row = spreadsheetList.easy_getRowAt(i); 
        while(!row.easy_getCell(0).getValue().isEmpty())
        {             
            if(projectType.equals(row.easy_getCell(0).getValue()))
            {                
                row.easy_removeCellAt(0);                
                row.easy_getCell(5).setValue(row.easy_getCell(4).getValue());
                row.easy_getCell(4).setValue(row.easy_getCell(3).getValue());
                row.easy_getCell(3).setValue(row.easy_getCell(2).getValue());
                row.easy_getCell(2).setValue(row.easy_getCell(1).getValue());
                row.easy_getCell(1).setValue(row.easy_getCell(0).getValue());                                                            
                row.easy_getCell(0).setValue("");
                
                while((row.easy_getCell(0).getValue().isEmpty() || row.easy_getCell(0).getValue() == null) && !spreadsheetList.easy_getRowAt(i+1).easy_getCell(0).getValue().isEmpty())
                {                             
                    String temp = spreadsheetList.easy_getRowAt(i+1).easy_getCell(0).getValue();
                    spreadsheetList.easy_getCell(i,0).setValue(temp + "");
                    spreadsheetList.easy_getRowAt(i+1).easy_getCell(0).setValue("");
                    i++;
                    try
                  {
                    row = spreadsheetList.easy_getRowAt(i);  
                  }
                  catch(IndexOutOfBoundsException e)
                  {
                    
                     break; 
                  }  
                }
                
                break;
                
            }
            i++; 
            try
                  {
                    row = spreadsheetList.easy_getRowAt(i);  
                  }
                  catch(IndexOutOfBoundsException e)
                  {
                    
                     break; 
                  }                  
        }    
        i = 1;         
        row = spreadsheetList.easy_getRowAt(i); 
        while(!row.easy_getCell(1).getValue().isEmpty())
        {
                if(row.easy_getCell(2).getValue().equals(projectType))
                {
                    row.easy_getCell(1).setValue("");
                    row.easy_getCell(2).setValue("");
                    int j=i;
                    ExcelRow rowU = spreadsheetList.easy_getRowAt(j); 
                    while((rowU.easy_getCell(1).getValue().isEmpty() || rowU.easy_getCell(1).getValue() == null) && !spreadsheetList.easy_getRowAt(j+1).easy_getCell(1).getValue().isEmpty())
                    {
                    String tempN = spreadsheetList.easy_getRowAt(j+1).easy_getCell(1).getValue();
                    spreadsheetList.easy_getCell(j,1).setValue(tempN + "");
                    spreadsheetList.easy_getRowAt(j+1).easy_getCell(1).setValue("");
                    tempN = spreadsheetList.easy_getRowAt(j+1).easy_getCell(2).getValue();
                    spreadsheetList.easy_getCell(j,2).setValue(tempN + "");
                    spreadsheetList.easy_getRowAt(j+1).easy_getCell(2).setValue("");
                    j++;
                    try
                      {
                    rowU = spreadsheetList.easy_getRowAt(j);  
                      }
                      catch(IndexOutOfBoundsException e)
                      { 
                    
                     break; 
                      } 
                    }
                    i=0;
                }
                i++;
                try
                  {
                    row = spreadsheetList.easy_getRowAt(i);  
                  }
                  catch(IndexOutOfBoundsException e)
                  {
                    
                     break; 
                  } 
        }
        
    }
    
    /*Create a arraylist of the project names*/
    public ArrayList<String> findListProject()
    {
       Update();
        int i = 2;
        ExcelRow row = spreadsheetList.easy_getRowAt(i); 
        ArrayList<String> temp = new ArrayList<>();
        while(!row.easy_getCell(0).getValue().isEmpty())
        {
            temp.add(row.easy_getCell(0).getValue());
            i++;
            try{
            row = spreadsheetList.easy_getRowAt(i);
        }
        catch(IndexOutOfBoundsException e)
        {                               
            break;
        }
        }       
       closeSheet();
       return temp;
    }
    /*Add a LifeCycle Step with an associated project to the database*/
    public void addListLC(String projectName, String lifecycleStep)
    {
        Update();
        int i = 2;
        int j = 2;
        ExcelRow row = spreadsheetList.easy_getRowAt(i); 
        while(!row.easy_getCell(0).getValue().isEmpty())
        {                                    
            if(projectName.equals(row.easy_getCell(0).getValue()))
            {
                row = spreadsheetList.easy_getRowAt(j);
                while(!row.easy_getCell(1).getValue().isEmpty())
                { 
                    if(lifecycleStep.equals(row.easy_getCell(1).getValue()))
                    {
                    break;
                    }
                  j++;  
                  try
                  {
                    row = spreadsheetList.easy_getRowAt(j);  
                  }
                  catch(IndexOutOfBoundsException e)
                  {
                     break; 
                  }
                }
                if(row.easy_getCell(1).getValue().isEmpty())
                    {
                        spreadsheetList.easy_getCell(j,1).setValue(lifecycleStep + "");
                        spreadsheetList.easy_getCell(j,2).setValue(projectName + "");
                    }
                break;                
            }
            i++; 
            row = spreadsheetList.easy_getRowAt(i);
        }              
        closeSheet();
    }   
    /*Create an arraylist of all of the life cycle steps that are associated with a project*/
    public ArrayList<String> findListLC(String projectName)
    {
        Update();
        int i = 2;
        ExcelRow row = spreadsheetList.easy_getRowAt(i); 
        ArrayList<String> temp = new ArrayList<>();
        while(!row.easy_getCell(1).getValue().isEmpty())
        {
            if(row.easy_getCell(2).getValue().equals(projectName))
            {
               temp.add(row.easy_getCell(1).getValue()); 
            }             
            i++;
            try{
                row = spreadsheetList.easy_getRowAt(i);
            }
            catch(IndexOutOfBoundsException e)
            {                               
                break;
            }
        }       
       closeSheet();
       return temp;
    }
    /*Adds an effort category to the database*/
    public void addListEffort(String effortCategory)
    {
        Update();
        int i = 1;        
        ExcelRow row = spreadsheetList.easy_getRowAt(i); 
        while(!row.easy_getCell(3).getValue().isEmpty())
        {            
            if(effortCategory.equals(row.easy_getCell(3).getValue()))
            {
                break;
            }
            i++; 
            try
                  {
                    row = spreadsheetList.easy_getRowAt(i);  
                  }
                  catch(IndexOutOfBoundsException e)
                  {
                    spreadsheetList.easy_getCell(i,3).setValue(effortCategory + "");
                     break; 
                  }
            if(row.easy_getCell(3).getValue().isEmpty())
            {
                    spreadsheetList.easy_getCell(i,3).setValue(effortCategory + "");
                    break; 
            }  
        }              
        closeSheet();
    }  
    /*Create an arraylist of the effort categories*/
    public ArrayList<String> findListEffort()
    {
        Update();
        int i = 2;
        ExcelRow row = spreadsheetList.easy_getRowAt(i); 
        ArrayList<String> temp = new ArrayList<>();
        while(!row.easy_getCell(3).getValue().isEmpty())
        {
            temp.add(row.easy_getCell(3).getValue());
            i++;
            try{
            row = spreadsheetList.easy_getRowAt(i);
        }
        catch(IndexOutOfBoundsException e)
        {                               
            break;
        }
        }       
       closeSheet();
       return temp;
    }
    /*Add a plan with an associated effort category to the database*/
    public void addListPlan(String effort, String plans)
    {
        Update();
        int i = 2;
        int j = 2;
        ExcelRow row = spreadsheetList.easy_getRowAt(i); 
        while(!row.easy_getCell(3).getValue().isEmpty())
        {                                    
            if(effort.equals(row.easy_getCell(3).getValue()))
            {
                row = spreadsheetList.easy_getRowAt(j);
                while(!row.easy_getCell(4).getValue().isEmpty())
                { 
                  if(plans.equals(row.easy_getCell(4).getValue()))
                    {
                    break;
                    }
                  j++;  
                  try
                  {
                    row = spreadsheetList.easy_getRowAt(j);  
                  }
                  catch(IndexOutOfBoundsException e)
                  {
                     break; 
                  }
                }
                if(row.easy_getCell(4).getValue().isEmpty())
                    {
                        spreadsheetList.easy_getCell(j,4).setValue(plans + "");
                        spreadsheetList.easy_getCell(j,5).setValue(effort + "");
                    }
                break;                
            }
            i++; 
            row = spreadsheetList.easy_getRowAt(i);
        }              
        closeSheet();
    }   
    /*Create an array list of plan steps associated with an effort category*/
    public ArrayList<String> findListPlan(String effort)
    {
        Update();
        int i = 2;
        ExcelRow row = spreadsheetList.easy_getRowAt(i); 
        ArrayList<String> temp = new ArrayList<>();
        while(!row.easy_getCell(4).getValue().isEmpty())
        {
            if(row.easy_getCell(5).getValue().equals(effort))
            {
               temp.add(row.easy_getCell(4).getValue()); 
            }             
            i++;
            try{
                row = spreadsheetList.easy_getRowAt(i);
            }
            catch(IndexOutOfBoundsException e)
            {                               
                break;
            }
        }       
       closeSheet();
       return temp;
    }
    
    public ArrayList<String> printWholeList()
    {
        Update();
        int i = 2;
        String index = "";
        ArrayList<String> temp = new ArrayList<>();
        ExcelRow row = new ExcelRow();
        try{
        row = spreadsheet.easy_getRowAt(i); 
        }
        catch(IndexOutOfBoundsException e)
        {
            closeSheet();
            return temp;
        }
        
        while(!row.easy_getCell(0).getValue().isEmpty())
        {
            index = row.easy_getCell(0).getValue() + "; " + row.easy_getCell(1).getValue() + "; "
                + row.easy_getCell(2).getValue() + "; "  + row.easy_getCell(3).getValue() + "; "  + row.easy_getCell(4).getValue()
                + "; "  + row.easy_getCell(5).getValue() + "; " + row.easy_getCell(6).getValue() + "; " + row.easy_getCell(7).getValue() + "; " + row.easy_getCell(8).getValue() + "; "
                + row.easy_getCell(9).getValue() + "; ";
            temp.add(index);
            i++;
            try{
                row = spreadsheet.easy_getRowAt(i);
            }
            catch(IndexOutOfBoundsException e)
            {                               
                break;
            }
        }       
       closeSheet();
       return temp;
    }
    
    /*Removes an index from the database*/
    public void Remove(String index)
    {
        Update();
        ExcelRow rowCheck = new ExcelRow();
        System.out.println(index + "");
        int rowCount = spreadsheet.RowCount();
        for (int i = 2; i < rowCount; i++) {
            ExcelRow row = spreadsheet.easy_getRowAt(i);            
            if(row.easy_getCell(0).getValue().equals(index))
            {                
                rowCheck = spreadsheet.easy_getRowAt(i);
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
        int Multiple = 0;
        for (int i = 2; i < rowCount; i++) {
            ExcelRow row = spreadsheet.easy_getRowAt(i);            
            if(row.easy_getCell(1).getValue().equals(rowCheck.easy_getCell(1).getValue()))
            {                
                Multiple++;                            
            }            
        }
        if(Multiple == 0)
        {
           removeListProject(rowCheck.easy_getCell(1).getValue());           
        }
        closeSheet();
    }
    public void encryptSheet()
    {
        Update();   
        if(encSpreadsheet.easy_getCell(0,0).getValue().equals("0"))
        {
        Encryption cryption = new Encryption();
        //sets all of the values of the excel sheet to their encrypted values
        ExcelRow row;
        int rowCount = spreadsheet.RowCount();       
        for(int i = 2; i < rowCount;i++)
        {
            row = spreadsheet.easy_getRowAt(i);
            row.easy_getCell(0).setValue(cryption.encryptData(row.easy_getCell(0).getValue(), "eecc"));
            row.easy_getCell(1).setValue(cryption.encryptData(row.easy_getCell(1).getValue(), "eecc"));
            row.easy_getCell(2).setValue(cryption.encryptData(row.easy_getCell(2).getValue(), "eecc"));
            row.easy_getCell(3).setValue(cryption.encryptData(row.easy_getCell(3).getValue(), "eecc"));
            row.easy_getCell(4).setValue(cryption.encryptData(row.easy_getCell(4).getValue(), "eecc"));
            row.easy_getCell(5).setValue(cryption.encryptData(row.easy_getCell(5).getValue(), "eecc"));
            row.easy_getCell(6).setValue(cryption.encryptData(row.easy_getCell(6).getValue(), "eecc"));
            row.easy_getCell(7).setValue(cryption.encryptData(row.easy_getCell(7).getValue(), "eecc"));
            row.easy_getCell(8).setValue(cryption.encryptData(row.easy_getCell(8).getValue(), "eecc"));
        }
    }
        encSpreadsheet.easy_getCell(0,0).setValue("1");
        closeSheet();    
    }
    public void decryptSheet()
    {
        Update();    
        if(encSpreadsheet.easy_getCell(0,0).getValue().equals("1"))
        {
        Encryption cryption = new Encryption();
        //sets all of the values of the excel sheet to their decrypted value
        ExcelRow row;
        int rowCount = spreadsheet.RowCount();       
        for(int i = 2; i < rowCount;i++)
        {
            row = spreadsheet.easy_getRowAt(i);
            row.easy_getCell(0).setValue(cryption.decryptData(row.easy_getCell(0).getValue(), "eecc"));
            row.easy_getCell(1).setValue(cryption.decryptData(row.easy_getCell(1).getValue(), "eecc"));
            row.easy_getCell(2).setValue(cryption.decryptData(row.easy_getCell(2).getValue(), "eecc"));
            row.easy_getCell(3).setValue(cryption.decryptData(row.easy_getCell(3).getValue(), "eecc"));
            row.easy_getCell(4).setValue(cryption.decryptData(row.easy_getCell(4).getValue(), "eecc"));
            row.easy_getCell(5).setValue(cryption.decryptData(row.easy_getCell(5).getValue(), "eecc"));
            row.easy_getCell(6).setValue(cryption.decryptData(row.easy_getCell(6).getValue(), "eecc"));
            row.easy_getCell(7).setValue(cryption.decryptData(row.easy_getCell(7).getValue(), "eecc"));
            row.easy_getCell(8).setValue(cryption.decryptData(row.easy_getCell(8).getValue(), "eecc"));
        }
    }
        encSpreadsheet.easy_getCell(0,0).setValue("0");
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

    /* public void editSheet(String Project, String Step, String effortCategory, String Deliverable)
    {
        int rowCount = spreadsheet.RowCount();
        int index = findindexSheet(Project, Step, effortCategory, Deliverable);
        if (index == 0)
        {
            
        }
    } */
    /*Prints all of the data in the spreadsheet to stdout*/
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
    /*Returns a string with all of the information related to an index*/
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
    /*Finds the index of data when given information about it*/
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
    public int isEnc()
    {
        Update();
        if(encSpreadsheet.easy_getCell(0,0).getValue().equals("1"))
        {
            closeSheet();
            return 1;
        }
        closeSheet();
        return 0;
    }
}
