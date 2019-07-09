package writer;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import bugFeatures.Bug;
import bugFeatures.Card;
import jxl.CellView;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;


public class WriteExcel {

    private WritableCellFormat timesBoldUnderline;
    private WritableCellFormat times;
    public String inputFile;
    public WritableSheet excelSheet;
    public WritableWorkbook workbook;
    
    public WriteExcel(String inputFile) throws IOException, WriteException {
    	this.inputFile = inputFile;
    	File file = new File(inputFile);
        WorkbookSettings wbSettings = new WorkbookSettings();
        
        if (file.exists()) {
        	file.delete();
        }

        wbSettings.setLocale(new Locale("en", "EN"));

        workbook = Workbook.createWorkbook(file, wbSettings);
        workbook.createSheet("Report", 0);
        excelSheet = workbook.getSheet(0);
    	
    }
    
    public void closeExcel ()  throws IOException, WriteException {
    	workbook.write();
    	workbook.close();
        System.out.println("Please check the result file under" +this.inputFile);
    }

public void setOutputFile(String inputFile) {
    this.inputFile = inputFile;
    } 

    public Integer write(Bug theBug, int line) throws IOException, WriteException {
        
        createLabel(excelSheet);
        return createContent(excelSheet, theBug, line);

        

    }

    private void createLabel(WritableSheet sheet)
            throws WriteException {
        // Lets create a times font
        WritableFont times10pt = new WritableFont(WritableFont.ARIAL, 10);
        // Define the cell format
        times = new WritableCellFormat(times10pt);
        // Lets automatically wrap the cells
        times.setWrap(false);

        // create create a bold font with unterlines
        WritableFont times10ptBoldUnderline = new WritableFont(
                WritableFont.TIMES, 10, WritableFont.BOLD, false,
                UnderlineStyle.SINGLE);
        timesBoldUnderline = new WritableCellFormat(times10ptBoldUnderline);
        // Lets automatically wrap the cells
        timesBoldUnderline.setWrap(true);

        CellView cv = new CellView();
        cv.setFormat(times);
        //cv.setFormat(timesBoldUnderline);
        cv.setAutosize(true);

        // Write a few headers
        //addCaption(sheet, 0, 0, "Header 1");
        //addCaption(sheet, 1, 0, "This is another header");


    }

    private Integer createContent(WritableSheet sheet, Bug theBug, int line) throws WriteException,
            RowsExceededException {
        
    	//abbLabel(sheet, theBug.theCase.);
    	DateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy hh:mm:ss");
    	String strDate;
    	
		addLabel(sheet,0,line, (theBug.theCase.caseNumber == null) ? "N/A" : theBug.theCase.caseNumber);
		addLabel(sheet,1,line, ((theBug.theCase.customerName == null) ? "N/A" : theBug.theCase.customerName));
		addNumber(sheet,2,line, ((theBug.theCase.scorePriority == null) ? 0 : new Integer(theBug.theCase.scorePriority)));
		addLabel(sheet,3,line, ((theBug.theCase.functionalDomain == null) ? "N/A" : theBug.theCase.functionalDomain));
		addLabel(sheet,4,line, ((theBug.theCase.status == null) ? "N/A" : theBug.theCase.status));
		// theCard
		// cardList.add(theCard);
		addLabel(sheet,5,line, ((theBug.theCase.module == null) ? "N/A" : theBug.theCase.module));
		addLabel(sheet,6,line, ((theBug.theCase.priority == null) ? "N/A" : theBug.theCase.priority));
		
		strDate = (theBug.theCase.EDD == null) ? "N/A" : dateFormat.format(theBug.theCase.EDD);
		addLabel(sheet,7,line, ((strDate == null) ? "N/A" : strDate));
		
		//CellView cell = sheet.getColumnView(c);
		//cell.setAutosize(true);
		if (theBug.theCase.cardList.size() >0) {

			for (Card theCard : theBug.theCase.cardList) {
				if (theCard.casenumber != null) { 
					line ++;
				
				addLabel(sheet,0,line, (theCard.numerofiche == null) ? "N/A" : theCard.numerofiche);
				addNumber(sheet,1,line, (theCard.dbid == null) ? 0 : new Integer(theCard.dbid));
				addLabel(sheet,2,line, (theCard.titre == null) ? "N/A" : theCard.titre);                                                
				addLabel(sheet,3,line, (theCard.status == null) ? "N/A" : theCard.status);                                                 
				addLabel(sheet,4,line, (theCard.casenumber == null) ? "N/A" : theCard.casenumber);                                         
				strDate = (theCard.datePlanningLatest == null) ? "N/A" : dateFormat.format(theCard.datePlanningLatest);
				addLabel(sheet,5,line, strDate);                      
				strDate = (theCard.datePlanningEarliest == null) ? "N/A" : dateFormat.format(theCard.datePlanningEarliest);
				addLabel(sheet,6,line, strDate); 
				                         
				addLabel(sheet,7,line, (theCard.label == null) ? "N/A" : theCard.label);                                                
				addLabel(sheet,8,line, (theCard.id == null) ? "N/A" : theCard.id);                                                   
				addLabel(sheet,9,line, (theCard.rfeId == null) ? "N/A" : theCard.rfeId);                                                
				addLabel(sheet,10,line, (theCard.typeReference == null) ? "N/A" : theCard.typeReference);                                        
				addLabel(sheet,11,line, (theCard.client == null) ? "N/A" : theCard.client);                                                 
				addLabel(sheet,12,line, (theCard.domainFunctional == null) ? "N/A" : theCard.domainFunctional);                                                 
				addLabel(sheet,13,line, (theCard.loginName == null) ? "N/A" : theCard.loginName);                                          
				addLabel(sheet,14,line, (theCard.productName == null) ? "N/A" : theCard.productName);                                         
				addNumber(sheet,15,line, (theCard.retrofitCard == null) ? 0 : new Integer(theCard.retrofitCard));                                    
				strDate = (theCard.openingDate == null) ? "N/A" : dateFormat.format(theCard.openingDate);
				addLabel(sheet,16,line, strDate); 
				
				strDate = (theCard.ECD == null) ? "N/A" : dateFormat.format(theCard.ECD);
				addLabel(sheet,17,line, strDate); 
				                             
				addLabel(sheet,18,line, (theCard.severity == null) ? "N/A" : theCard.severity);                  
				addNumber(sheet,19,line, (theCard.age == null) ? 0 : new Integer(theCard.age));       
				addLabel(sheet,20,line, (theCard.subProduct == null) ? "N/A" : theCard.subProduct);
				addLabel(sheet,21,line, (theCard.internalState == null) ? "N/A" : theCard.internalState);                                        
				addNumber(sheet,22,line, (theCard.regression == null) ? 0 : new Integer(theCard.regression));                                          
				addLabel(sheet,23,line, (theCard.regressionCause == null) ? "N/A" : theCard.regressionCause);
				//line ++;
				}
			}
		}
		
		for (int c = 0; c < 8; c++) {
			  CellView cell = sheet.getColumnView(c);
			  cell.setAutosize(true);
			  sheet.setColumnView(c, cell);
			 }
		
    	
//    	// Write a few number
//        for (int i = 1; i < 10; i++) {
//            // First column
//            addNumber(sheet, 0, i, i + 10);
//            // Second column
//            addNumber(sheet, 1, i, i * i);
//        }
//        // Lets calculate the sum of it
//        StringBuffer buf = new StringBuffer();
//        buf.append("SUM(A2:A10)");
//        Formula f = new Formula(0, 10, buf.toString());
//        sheet.addCell(f);
//        buf = new StringBuffer();
//        buf.append("SUM(B2:B10)");
//        f = new Formula(1, 10, buf.toString());
//        sheet.addCell(f);
		return line;
        
    }

    private void addCaption(WritableSheet sheet, int column, int row, String s)
            throws RowsExceededException, WriteException {
        Label label;
        label = new Label(column, row, s, timesBoldUnderline);
        sheet.addCell(label);
    }

    private void addNumber(WritableSheet sheet, int column, int row,
            Integer integer) throws WriteException, RowsExceededException {
        Number number;
        number = new Number(column, row, integer, times);
        sheet.addCell(number);
    }

    private void addLabel(WritableSheet sheet, int column, int row, String s)
            throws WriteException, RowsExceededException {
        Label label;
        label = new Label(column, row, s, times);
        sheet.addCell(label);
    }

   
}