package BlueTree.exception;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ConstrainValidationExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value= {javax.validation.ConstraintViolationException.class})
	public ResponseEntity<Object> handleConflict(Exception e, WebRequest wr){
		System.out.println("Caught: " + e.getClass().getName());
		ConstraintViolationException cve = (ConstraintViolationException)e;
		System.out.println("message"+cve.getMessage().split("interpolatedMessage='")[1].split("'")[0]);
		String message = cve.getMessage().split("propertyPath=")[1].split(",")[0]+": "+cve.getMessage().split("interpolatedMessage='")[1].split("'")[0];
		return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
	}


}