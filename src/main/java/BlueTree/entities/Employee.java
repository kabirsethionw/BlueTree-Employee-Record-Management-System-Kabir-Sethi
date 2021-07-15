package BlueTree.entities;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;


@Data
@Entity
@NoArgsConstructor(access=AccessLevel.PUBLIC, force=true)
@AllArgsConstructor
@Getter @Setter 
public class Employee {
	
	public static enum Dept{
		SALES,
		IT,
		QA,
		MANAGEMENT
	}
	
	public static enum Status{
		WORKING,
		NOT_WORKING
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotNull
	@Size(min=2, max=30, message="Name field must be atleast 2 characters long and atmost 30 characters long.")
	private String name;
	
	@Email(message = "Invalid email.")
	@NotNull
	private String email;
	

	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	@DateTimeFormat(pattern="yyyy-MM-dd") //2079-08-30
	@JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
	private LocalDate dob;
	
	private String address;
	
	@NotNull
	@Pattern(regexp="(^$|[0-9]{10})", message="Invalid phone no.") //10 digits regular expression
	private String phone; 
	
	@NotNull
	@Enumerated(EnumType.STRING) //Stored as string in db
	private Dept dept;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private Status status;
	
	@NotNull
	private Date updatedAt = new Date();

	
	public Employee(String name, String email, Dept dept, Status status,String address,String phone, String dob){
		this.name = name;
		this.email = email;
		this.dept = dept;
		this.status = status;
		this.updatedAt = new Date();
		this.address = address;
		this.phone = phone;
//		String[] dateArgs = dob.split("-");
		this.dob = LocalDate.parse(dob, DateTimeFormatter.ofPattern("yyyy-MM-dd"));//LocalDate.of(Integer.parseInt(dateArgs[0]), Integer.parseInt(dateArgs[1]), Integer.parseInt(dateArgs[2]));
	}
//	
//	public Employee(String name, String email, String profile, String status){
//		this.name = name;
//		this.email = email;
//		this.profile = Profile.valueOf(profile);
//		this.status = Status.valueOf(status);
//		this.joinedAt = new Date();
//		this.leftAt = null;
//	}
	
}
