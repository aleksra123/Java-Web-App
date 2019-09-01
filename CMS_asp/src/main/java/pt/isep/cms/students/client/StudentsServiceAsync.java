package pt.isep.cms.students.client;

import com.google.gwt.user.client.rpc.AsyncCallback;


import pt.isep.cms.students.shared.Student;
import pt.isep.cms.students.shared.StudentDetails;

import java.util.ArrayList;

public interface StudentsServiceAsync {

  public void addStudent(Student student, AsyncCallback<Student> callback);
  public void deleteStudent(String id, AsyncCallback<Boolean> callback);
  public void deleteStudents(ArrayList<String> ids, AsyncCallback<ArrayList<StudentDetails>> callback);
  public void getStudentDetails(AsyncCallback<ArrayList<StudentDetails>> callback);
  public void getStudent(String id, AsyncCallback<Student> callback);
  public void updateStudent(Student student, AsyncCallback<Student> callback);
}

