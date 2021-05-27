package BlueTree.services;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import BlueTree.entities.Employee;
import BlueTree.entities.Employee.Dept;
import BlueTree.entities.Employee.Status;
import BlueTree.repositories.EmployeeRepository;


@Service
public class ExcelDataServiceImpli implements ExcelDataService {

	public String EXCEL_FILE_PATH =  "EmployeeExcel.xlsx";

	@Autowired
	EmployeeRepository repo;

	Workbook workbook;

	public List<Employee> getExcelDataAsList() {

		List<String> list = new ArrayList<String>();

		// Create a DataFormatter to format and get each cell's value as String
		DataFormatter dataFormatter = new DataFormatter();

		// Create the Workbook
		try {
			workbook = WorkbookFactory.create(new File(EXCEL_FILE_PATH));
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Retrieving the number of sheets in the Workbook
		System.out.println("-------Workbook has '" + workbook.getNumberOfSheets() + "' Sheets-----");

		// Getting the Sheet at index zero
		Sheet sheet = workbook.getSheetAt(0);

		// Getting number of columns in the Sheet
		int noOfColumns = sheet.getRow(0).getLastCellNum();
		System.out.println("-------Sheet has '"+noOfColumns+"' columns------");

		// Using for-each loop to iterate over the rows and columns
		for (Row row : sheet) {
			for (Cell cell : row) {
				String cellValue = dataFormatter.formatCellValue(cell);
				list.add(cellValue);
			}
		}

		// filling excel data and creating list as List<Invoice>
		List<Employee> invList = createList(list, noOfColumns);

		// Closing the workbook
		try {
			workbook.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return invList;
	}

	private List<Employee> createList(List<String> excelData, int noOfColumns) {

		ArrayList<Employee> invList = new ArrayList<Employee>();

		int i = noOfColumns;
		do {
			Employee emp = new Employee();

			emp.setName(excelData.get(i));
			emp.setEmail(excelData.get(i + 1));
			emp.setDob(LocalDate.of(Integer.parseInt(excelData.get(i + 2).split("-")[0]),Integer.parseInt(excelData.get(i + 2).split("-")[1]),Integer.parseInt(excelData.get(i + 2).split("-")[2])));
			emp.setAddress(excelData.get(i + 3));
			emp.setPhone(excelData.get(i+4));
			emp.setDept(Dept.valueOf(excelData.get(i+5)));
			emp.setStatus(Status.valueOf(excelData.get(i+6)));
			emp.setUpdatedAt(null);

			invList.add(emp);
			i = i + (noOfColumns);

		} while (i < excelData.size());
		return invList;
	}

	@Override
	public int saveExcelData(List<Employee> empSave) {
		repo.saveAll(empSave).forEach(empSave::add);
		return empSave.size();
	}
}