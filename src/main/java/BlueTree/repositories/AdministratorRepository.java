package BlueTree.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import BlueTree.entities.Administrator;

public interface AdministratorRepository extends CrudRepository<Administrator, String> {
	public Optional<Administrator> findById(Long id);
	public Optional<Administrator> findByUsername(String username);
	public Optional<Administrator> findByPassword(String password);
}
