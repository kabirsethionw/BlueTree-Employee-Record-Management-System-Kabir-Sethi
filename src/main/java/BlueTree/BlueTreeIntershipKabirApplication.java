package BlueTree;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;


@SpringBootApplication
public class BlueTreeIntershipKabirApplication implements CommandLineRunner {

	@Autowired
	private JdbcTemplate jdbcTemplate; 
	
	public static void main(String[] args) {	
		SpringApplication.run(BlueTreeIntershipKabirApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception{
//		String statment = "insert into employee(name, email, profile, status, joinedAt, leftAt) values(?,?,?,?,?,?)";
//		
//		int res = jdbcTemplate.update(statment, "Ricardo Rodhriques", "rodhriques9999@gmail.com", "SALESMEN", "WORKING", new Date(), null);
//		
//		System.out.println(res);
	}

}
