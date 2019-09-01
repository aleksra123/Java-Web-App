package pt.isep.cms.students.client;

import com.google.gwt.user.client.rpc.RemoteService;


import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import pt.isep.cms.students.shared.Student;
import pt.isep.cms.students.shared.StudentDetails;

import java.util.ArrayList;

@RemoteServiceRelativePath("studentsService")
public interface StudentsService extends RemoteService {
	
  Student addStudent(Student student);
  Boolean deleteStudent(String id); 
  ArrayList<StudentDetails> deleteStudents(ArrayList<String> ids);
  ArrayList<StudentDetails> getStudentDetails();
  Student getStudent(String id);
  Student updateStudent(Student student);
}
