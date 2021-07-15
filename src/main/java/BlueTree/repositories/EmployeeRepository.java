package BlueTree.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import BlueTree.entities.Employee;

public interface EmployeeRepository extends CrudRepository<Employee, String>{
	public Optional<Employee> findById(Long id);
	public Optional<List<Employee>> findByName(String name);
	//public List<Employee> saveAll(List<Employee> emp);
}
