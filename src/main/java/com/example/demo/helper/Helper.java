package com.example.demo.helper;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Employers;

@Service
public class Helper {
	
	public static String Type ="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	public String[] headers = {"id","fname","lname","depertment","active"};
	static String SHEET ="Employers";
	
	//check excel fole format
	public boolean checkisExcelFile(MultipartFile file)
	{
		if(!Type.equals(file.getContentType()))
		{
			return false;
		}
		return true;
	}
	
	//Read Excel file
	
	public List<Employers> employersExcel(InputStream is)
	{
		try {
			
			//Workbook workbook =new XSSFWorkbook(is); //creating workbook
			Workbook workbook = WorkbookFactory.create(is);
			
			Sheet sheet = workbook.getSheetAt(0); //create sheet
			
			
			Iterator<Row> rows =sheet.iterator();
			
			
			List<Employers> employers = new ArrayList<Employers>();
			
			int rowNumber = 0;
			
			while(rows.hasNext())
			{
				Row currentRow = rows.next();
				
				//skip headers
				if(rowNumber == 0)
				{
					rowNumber ++;
					continue;
				}
				
		    Iterator<Cell> cell = currentRow.iterator(); //cell iterator
		    
		    Employers employers1 = new Employers();
		    int cellIndex =0;
		    
		    while(cell.hasNext())
		    {
		    	  Cell currentCell =cell.next();
		    	  
		    	 switch(cellIndex)
		    	 {
		    	 case 0:
		    		 employers1.setId((long) currentCell.getNumericCellValue());
		    		 	break;
		    	 case 1:
		    		 employers1.setFname(currentCell.getStringCellValue());
		    		 break;
		    	 case 2:
		    		 employers1.setLname(currentCell.getStringCellValue());
		    		 break;
		    	 case 3:
		    		 employers1.setDepertment(currentCell.getStringCellValue());
		    		 break;	
		    	 case 4:
		    		 employers1.setActive(currentCell.getBooleanCellValue());
		    		 break;	
		    		 
		    	 default:
		    		 break;
		    	 }
		    	 
		    	 cellIndex ++;
		    }
		    
		    employers.add(employers1);
		    
		    
			}
			
			workbook.close();
			return employers;
			
		} catch (Exception e) {
			throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
		}
	}

}
