package pt.isep.cms.students;

import pt.isep.cms.students.shared.Student;
import pt.isep.cms.students.shared.StudentDetails;
import pt.isep.cms.students.server.StudentsServiceImpl;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import junit.framework.TestCase;

public class StudentsServiceImplJRETest extends TestCase {
	private StudentsServiceImpl studentsServiceImpl;
	private int firstTestStudentId;


	protected void setUp() {
		studentsServiceImpl = new StudentsServiceImpl();

	}


	// Test StudentsServiceImpl > addStudent
	public void testAddStudent() {
		String id = studentsServiceImpl.getNextIdString("students","id");
		System.out.println("Initial ID: "+ id);
		Student student = new Student(id, "KeepMe", "TestStudent", "Male", "1990-05-09", "4");
		try {
				TimeUnit.SECONDS.sleep(1);
				System.out.println("I AM SLEEPING");
		} catch (InterruptedException e){
			e.printStackTrace();
		}
		studentsServiceImpl.addStudent(student);

		Student studentTest = studentsServiceImpl.getStudent(student.getId());
		//

		System.out.println("STUDENT Test: "+ studentTest);
		System.out.println("STUDENT Test ID: "+ studentTest.id);
		assertEquals(student.id, studentTest.id);
		studentsServiceImpl.deleteStudent(id);
	}

	// Test StudentsServiceImpl > updateStudent
	public void testUpdateStudent() {
		String id = studentsServiceImpl.getNextIdString("students","id");
		System.out.println("Initial ID: "+ id);
		Student student = new Student(id, "firstNameUpdated", "lastName", "Gender", "1990-05-09", "1");
		studentsServiceImpl.addStudent(student);
		student = studentsServiceImpl.getStudent(student.getId());
		student.setFirstName("Update");
		student.setLastName("Update");
		student.setGender("Gender2");
		student.setBirthDate("1990-05-09");
		student.setAddToClass("2");
		studentsServiceImpl.updateStudent(student);

		Student studentTest = studentsServiceImpl.getStudent(student.getId());
		assertEquals(student.firstName, studentTest.firstName);
		assertEquals(student.lastName, studentTest.lastName);
		assertEquals(student.gender, studentTest.gender);
		//assertEquals(student.birthDate, studentTest.birthDate);
		assertEquals(student.addToClass, studentTest.addToClass);

		studentsServiceImpl.deleteStudent(id);
	}

	// Test StudentsServiceImpl > deleteStudent
	public void testDeleteStudent() {
		String id = studentsServiceImpl.getNextIdString("students","id");
		System.out.println("Initial ID: "+ id);
		Student student = new Student(id, "Delete", "Delete", "Gender", "1990-05-09", "1");
		studentsServiceImpl.addStudent(student);
		int arraySizeBefore = studentsServiceImpl.getStudentDetails().size();
		studentsServiceImpl.deleteStudent(student.getId());
		int arraySizeAfter = studentsServiceImpl.getStudentDetails().size();
		assertTrue(arraySizeAfter < arraySizeBefore);
	}

	// Test StudentsServiceImpl > getStudent
	public void testGetStudent() {
		String id = studentsServiceImpl.getNextIdString("students","id");
		System.out.println("Initial ID: "+ id);
		Student student = new Student(id, "unik", "special", "Gender", "1990-05-09", "2");
		studentsServiceImpl.addStudent(student);
		Student studentTest = studentsServiceImpl.getStudent(student.getId());
		assertEquals(student.id, studentTest.id);

		studentsServiceImpl.deleteStudent(id);
	}

	// Test StudentsServiceImpl > deleteStudents
	public void testDeleteStudents() {
		String id1 = studentsServiceImpl.getNextIdString("students","id");
		int temp = Integer.parseInt(id1);
		temp += 1;
		String id2 = Integer.toString(temp);
		System.out.println("Initial ID1: "+ id1);
		System.out.println("Initial ID2: "+ id2);
		Student student = new Student(id1, "DeletePlural", "DeletePlural", "Gender", "1990-05-09", "4");
		Student student2 = new Student(id2, "DeletePlural", "DeletePlural", "Gender", "1990-05-09", "4");
		studentsServiceImpl.addStudent(student);
		studentsServiceImpl.addStudent(student2);

		int arraySizeBefore = studentsServiceImpl.getStudentDetails().size();

		ArrayList<String> ids = new ArrayList<String>();
		ids.add(student.getId());
		ids.add(student2.getId());

		int arraySizeIds = ids.size();

		int arraySizeAfter = studentsServiceImpl.deleteStudents(ids).size();

		assertTrue((arraySizeBefore - arraySizeIds) == arraySizeAfter);

	}

	// Test StudentsServiceImpl > getStudentDetails
	public void testGetStudentDetails() {
		String id1 = studentsServiceImpl.getNextIdString("students","id");
		int temp = Integer.parseInt(id1);
		temp += 1;
		String id2 = Integer.toString(temp);
		System.out.println("Initial ID1: "+ id1);
		System.out.println("Initial ID2: "+ id2);
		Student student = new Student(id1, "Details", "Details", "Gender", "1990-05-09", "4");
		Student student2 = new Student(id2, "Details2", "Details2", "Gender", "1990-05-09", "2");
		studentsServiceImpl.addStudent(student);
		studentsServiceImpl.addStudent(student2);

		ArrayList<StudentDetails> studentDetails = studentsServiceImpl.getStudentDetails();
		System.out.println("STUDENT ID: "+ student.id);
		System.out.println("STUDENT2 ID: "+ student2.id);
		ArrayList<String> idsFromStudentDetails = new ArrayList<String>();
		for (StudentDetails cd : studentDetails) {
			idsFromStudentDetails.add(cd.getId());
		}
		System.out.println("STUDENTDETAILS: "+ studentDetails);
		//System.out.println("STUDENT2 ID: "+ student2.id);

	    assertTrue(idsFromStudentDetails.contains(student.getId()));
	    assertTrue(idsFromStudentDetails.contains(student2.getId()));
			studentsServiceImpl.deleteStudent(id1);
			studentsServiceImpl.deleteStudent(id2);
	}

}
