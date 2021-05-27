package BlueTree.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import BlueTree.entities.Employee;
import BlueTree.entities.Employee.Dept;
import BlueTree.entities.Employee.Status;

public class EmployeeRowMapper implements RowMapper<Employee> {

	public EmployeeRowMapper()  {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		Employee emp = new Employee(); 
		emp.setName(rs.getString("name"));
		emp.setEmail(rs.getString("email"));
		emp.setPhone(rs.getString("phone"));
		Status st = Status.valueOf(rs.getString("status"));
		emp.setStatus(st);
		Dept de = Dept.valueOf(rs.getString("dept"));
		emp.setDept(de);
		emp.setAddress(rs.getString("address"));
		emp.setUpdatedAt(null);
		return null;
	}

}
