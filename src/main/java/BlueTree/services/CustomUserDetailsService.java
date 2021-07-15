package BlueTree.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import BlueTree.entities.Administrator;
import BlueTree.repositories.AdministratorRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService  {

	@Autowired
	private AdministratorRepository repo;
	
	public CustomUserDetailsService() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Administrator admin = repo.findByUsername(username).get();
		return new org.springframework.security.core.userdetails.User(admin.getUsername(), admin.getPassword(), new ArrayList<>());
	}

}
