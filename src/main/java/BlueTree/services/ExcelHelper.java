package BlueTree.services;
// Here
import java.io.InputStream;
import java.util.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import BlueTree.entities.Employee;
import BlueTree.entities.Employee.Dept;
import BlueTree.entities.Employee.Status;

public class ExcelHelper {
	public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	static String SHEET ;
	 
	public static boolean hasExcelFormat(MultipartFile file) {
		if(TYPE.equals(file.getContentType())) {
			SHEET = file.getName();
			return true;
		}
		else {
			return false;
		}	
	}
	
	public static List<Employee> excelToEmloyees(MultipartFile mf) {
		try {
			InputStream is = mf.getInputStream();
			Workbook wb = new XSSFWorkbook(is);
			Sheet sheet = wb.getSheetAt(0);
			Iterator<Row> rows = sheet.iterator();
			
			List<Employee> employees = new ArrayList<Employee>();
			
			int rowNum = 0;
			while(rows.hasNext()) {
				Row currentRow = rows.next();
				if(rowNum == 0) {
					rowNum++;
					continue;
				}
				
		        Iterator<Cell> rowCells = currentRow.iterator();
		        Employee emp = new Employee();
		        int cellIndex = 0;
		        
		        while(rowCells.hasNext()) {
		        	Cell currentCell = rowCells.next();
		        	
		        	switch(cellIndex) {
		        		case 0: 
		        			emp.setName(currentCell.getStringCellValue());
		        			break;
		        		case 1:
		        			emp.setEmail(currentCell.getStringCellValue());
		        			break;
		        		case 2:
		        			Date dob = currentCell.getDateCellValue();
		        			Calendar cal = Calendar.getInstance();
		        			cal.setTime(dob);
		        			emp.setDob(LocalDate.of(cal.get(cal.YEAR), cal.get(cal.MONTH), cal.get(cal.DAY_OF_MONTH )));
		        			break;
		        		case 3:
		        			emp.setAddress(currentCell.getStringCellValue());
		        			break;
		        		case 4: 
		        			long phone = (long)currentCell.getNumericCellValue();
		        			emp.setPhone(Long.toString(phone));
		        			break;
		        		case 5:
		        			emp.setDept(Dept.valueOf(currentCell.getStringCellValue()));
		        			break;
		        		case 6:
		        			emp.setStatus(Status.valueOf(currentCell.getStringCellValue()));
		        			break;			
		        		default:
		        			break;
		        	}
		        	cellIndex++;
		        }
		        employees.add(emp);
			}
			
			wb.close();
			return employees;
			
		}catch(Exception e) {
			throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
		}
	}
}
