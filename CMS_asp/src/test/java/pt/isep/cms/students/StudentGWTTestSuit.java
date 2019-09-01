package pt.isep.cms.students;

import com.google.gwt.junit.tools.GWTTestSuite;


import junit.framework.Test;
import junit.framework.TestSuite;
import pt.isep.cms.students.client.StudentGWTTest;
import pt.isep.cms.students.shared.Student;
import pt.isep.cms.students.shared.StudentDetails;
import pt.isep.cms.students.server.StudentsServiceImpl;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import junit.framework.TestCase;
import pt.isep.cms.students.server.StudentsServiceImpl;
import static org.junit.Assert.*;


public class StudentGWTTestSuit extends GWTTestSuite {

	private StudentsServiceImpl studentsServiceImpl;

	protected void setUp() {
		studentsServiceImpl = new StudentsServiceImpl();


	}

	public void testStudentEnrolledInOnlyOneClass(){ //checks if adding a two students with the same contact info gives different ID's which show that they are different persons
	//Melding til Lone; Tabellen kan ikke være tom når du kjører denne for at id'en skal bli ulik, men assertTrue er lik uansett.
			String id = studentsServiceImpl.getNextIdString("students","id");
			Student studentLone1 = new Student(id, "Lone", "Bekkeheien", "female", "1990-05-09", "1");
			Student studentLone2 = new Student(id+1, "Lone", "Bekkeheien", "female", "1990-05-09", "1");
			studentsServiceImpl.addStudent(studentLone1);
			studentsServiceImpl.addStudent(studentLone2);
			Student student1 = studentsServiceImpl.getStudent(studentLone1.id); //gets the last Lone student added
			Student student2 = studentsServiceImpl.getStudentByLone(); //gets the first Lone student added
			System.out.println("HALLLOOOO1: "+ student1.id);
			System.out.println("HALLOOOOO2: "+ student2.id);
			assertTrue(student1.id != student2.id);
			studentsServiceImpl.deleteStudent(student1.id);
			studentsServiceImpl.deleteStudent(student2.id);
	}

	//The following two functions tests if a module/class can max have 20 students
	public void testMaxStudents(){
			Thread t1 = new Thread(new MyClass ());
			t1.start();
		}

	public class MyClass implements Runnable {
		public void run(){
			boolean exceededMaxStud=false;
			try{
		   while(!Thread.currentThread().isInterrupted()){
					 int id = studentsServiceImpl.getMaxId("students","id");
			 			id+=1;
			 			int cap = 20;
			 			int number = 0;
						number++;
						System.out.println("NUMBER: "+ number);
						String s_number = Integer.toString(number);
						Student student = new Student(Integer.toString(id), "firstNameMAX"+s_number, "lastNameAdd", "Gender", "1990-05-09", "2");
						studentsServiceImpl.addStudent(student);
						id++;
						Thread.sleep(1); //fds

				}
			}	catch(InterruptedException e){
					System.out.println("solhatt:" + exceededMaxStud);
					e.printStackTrace();
					exceededMaxStud=true;
					System.out.println("EXCEEDED MAX STUDENTS:" + exceededMaxStud);
				}
				assertTrue(exceededMaxStud);
		 }
	 }


	 public void DeletingAfterTests(){

 	 try {
 			 TimeUnit.SECONDS.sleep(1);
 			 System.out.println("I AM SLEEPING");
 	 } catch (InterruptedException e){
 		 e.printStackTrace();
 	 }
 	 ArrayList<StudentDetails> studentDetails = studentsServiceImpl.getStudentDetails();

 	 for(int i=0; i<studentDetails.size(); i++){

 							if(studentDetails.get(i).getDisplayName()=="firstNameMAX"){

 								 studentsServiceImpl.deleteStudent(studentDetails.get(i).getId());
 					}


 			 }
 		 }

	  public static Test suite() {
		    TestSuite suite = new TestSuite("Test for the Contacts Application");
		    suite.addTestSuite(StudentGWTTest.class);

		    return suite;
		  }



		}
