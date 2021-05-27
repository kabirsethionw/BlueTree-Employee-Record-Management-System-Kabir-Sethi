package converters;

import java.time.LocalDate;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class LocalDateCoverter implements AttributeConverter<LocalDate, String> {

	public LocalDateCoverter() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String convertToDatabaseColumn(LocalDate attribute) {
		// TODO Auto-generated method stub
		int[] dateArray = new int[3]; 
		dateArray[0] =  attribute.getYear(); 
	    dateArray[01] = attribute.getMonth().getValue();
		dateArray[0] = attribute.getYear();
		return attribute.toString();
	}

	@Override
	public LocalDate convertToEntityAttribute(String dbData) {
		// TODO Auto-generated method stub
		String[] data = dbData.split("-");
		return LocalDate.of(Integer.parseInt(data[0], 10), Integer.parseInt(data[1], 10), Integer.parseInt(data[2], 10));
	}

}
