package BlueTree.services;


import java.util.List;


import BlueTree.entities.Employee;

public interface ExcelDataService {

	List<Employee> getExcelDataAsList();
	
	int saveExcelData(List<Employee> employees);
}