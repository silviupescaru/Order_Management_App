package start;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import bll.StudentBLL;
import model.Student;

public class Start {
	protected static final Logger LOGGER = Logger.getLogger(Start.class.getName());

	public static void main(String[] args) throws SQLException {

		StudentBLL studentBll = new StudentBLL();

		Student student1 = null;

		try {
			student1 = studentBll.findStudentById(2);

		} catch (Exception ex) {
			LOGGER.log(Level.INFO, ex.getMessage());
		}

		// obtain field-value pairs for object through reflection
		ReflectionExample.retrieveProperties(student1);

	}

}
