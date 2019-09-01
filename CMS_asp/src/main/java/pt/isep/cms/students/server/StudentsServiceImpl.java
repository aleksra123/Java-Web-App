package pt.isep.cms.students.server;

//
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;

import java.util.Date;

import pt.isep.cms.students.client.StudentsService;
import pt.isep.cms.students.shared.Student;
import pt.isep.cms.students.shared.StudentDetails;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;


@SuppressWarnings("serial")
public class StudentsServiceImpl extends RemoteServiceServlet implements
    StudentsService {

	private final HashMap<String, Student> students = new HashMap<String, Student>();

	//CONSTURCTOR
    public StudentsServiceImpl() {
	    initStudents();
	  }


	private String driver = "com.mysql.cj.jdbc.Driver";
	private String myUrl = "jdbc:mysql://127.0.0.1:3306/cmsDB?serverTimezone=UTC";//host.docker.internal // 127.0.0.1
	private String userName = "root";
	private String password = "12qwaszx"; //12qwaszx

	private java.sql.Connection con = null;
	Statement statement = null;

	private void initStudents() {
		// Establish a Connection to Database
		// TODO Auto-generated method stub
		try {
	        Class.forName(driver).newInstance();

	        con = DriverManager.getConnection(myUrl,userName,password);

	        String sqlQuerry = "SELECT id, firstname, lastname, gender, birthdate, modules_idmodules, className FROM students LEFT JOIN modules ON students.modules_idmodules = modules.idmodules";

	        if (con != null) {
	        	System.out.println("Connection Successful!");
	        	statement = con.createStatement();
	        	ResultSet rs = statement.executeQuery(sqlQuerry);

	        	// insert the database entries to display them //I think we need to clear the web-info first, talk to me
	        	while (rs.next()) {
	        		String className = rs.getString("modules_idmodules") + " " + rs.getString("className");
                    Student student = new Student(rs.getString("id"), rs.getString("firstname"), rs.getString("lastname"), rs.getString("gender"), rs.getString("birthdate"), className);
                    students.put(student.getId(), student);
	        	}
	            rs.close();
	            rs = null;
	        } else
                System.out.println("Error: No active Connection");
		} catch (Exception e) {
		        e.printStackTrace();
		        System.out.println("Error Trace in getConnection() : "  + e.getMessage());
		}
	}


  public Student getStudentByLone() {

          String sqlQuerry = "SELECT * FROM cms.students WHERE firstname = 'Lone'";
          //String sqlQuerry = "SELECT firstname FROM students WHERE firstname = Lone";
        //  String sqlQuerry = "SELECT " + Name + " AS firstname FROM cms.students WHERE firstname = " + "firstname" ;

          try{

            con = DriverManager.getConnection(myUrl,userName,password);
            statement = con.createStatement();
            ResultSet result = statement.executeQuery(sqlQuerry);
            while(result.next()){
              Student student = new Student(result.getString(1),result.getString(2),result.getString(3),
                                  result.getString(4),result.getString(5),result.getString(6));
              return student;
            }

          } catch (Exception e) {
                  e.printStackTrace();
                  System.out.println("Error Trace in getConnection() : "  + e.getMessage());

          }
          Student student = new Student();
          return student;
        }

    public Student addStudent(Student student) {

    	String insertSQL = "INSERT INTO students( firstname, lastname, gender, birthdate, modules_idmodules) VALUES( ?, ?, ?, ?, ?)";

    	try {

    		PreparedStatement preparedStatement = con.prepareStatement(insertSQL);
      //preparedStatement.setString(1, getMaxId("students","id"));

      //preparedStatement.setString(1, getNextIdString("students","id"));
			preparedStatement.setString(1, student.firstName);
			preparedStatement.setString(2, student.lastName);
			preparedStatement.setString(3, student.gender);

			// Two formats for parsing the European Format to SQL Database Dateformat
			DateFormat formatOld = new SimpleDateFormat("dd.MM.yyyy");
			DateFormat formatNew = new SimpleDateFormat("yyyy-MM-dd");
			java.sql.Date birthDate = null;
			try {
				Date parsedDate;
  				if (isThisDateValid(student.birthDate,"dd.MM.yyyy")) {
  					Date date = formatOld.parse(student.birthDate);
  	  				String formatedString = formatNew.format(date);
  	  				parsedDate = formatNew.parse(formatedString);
  				} else {
  					parsedDate = formatNew.parse(student.birthDate);
  				}

  				birthDate = new java.sql.Date(parsedDate.getTime()); // parsing Dateformat to SQL Dateformat

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			preparedStatement.setDate(4,birthDate);

			if(!isMaxStudentsInClass(student.addToClass.substring(0, 1))) {

				boolean stop = false;
				while(!stop) {
					List<String> aList = getTable("modules");
					String[] choices = aList.toArray(new String[aList.size()]);
					String input = (String) JOptionPane.showInputDialog(null, "Choose a different Class because this one is Full",
					        "Set a Class for the Student",JOptionPane.QUESTION_MESSAGE, null, // Use
					                                                                        // default
					                                                                        // icon
					        choices, // Array of choices
					        null); // Initial choice

					if(input!=null) {
						preparedStatement.setInt(5, Integer.parseInt(input.substring(0, 1)));
						if(isMaxStudentsInClass(input.substring(0, 1)))
							stop = true;
					}
				}

			} else {
				preparedStatement.setInt(5, Integer.parseInt(student.addToClass.substring(0, 1)));
			}
      //preparedStatement.setInt(6, Integer.parseInt(student.addToClass.substring(0, 1)));

			preparedStatement.executeUpdate(); // create SQL Update on DB
			preparedStatement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	if(con!= null) {
        System.out.println("Connection: Starting initStudents again.");
    		initStudents();
    		//students.put(getMaxId("students", "id").toString(), student); // put on Display

    	}
    	return student;

      }

      public Student updateStudent(Student student) {
    	String updateSQL = "UPDATE students SET firstname = ?, lastname = ?, gender = ?, birthdate = ?, modules_idmodules = ? WHERE id =" + student.id;

      	try {
      		PreparedStatement preparedStatement = con.prepareStatement(updateSQL);
        //preparedStatement.setString(1, student.id);
  			preparedStatement.setString(1, student.firstName);
  			preparedStatement.setString(2, student.lastName);
  			preparedStatement.setString(3, student.gender);

  			// Two formats for parsing the European Format to SQL Database Dateformat
  			DateFormat formatOld = new SimpleDateFormat("dd.MM.yyyy");
  			DateFormat formatNew = new SimpleDateFormat("yyyy-MM-dd");
  			java.sql.Date birthDate = null;
  			try {
  				Date parsedDate;
  				if (isThisDateValid(student.birthDate,"dd.MM.yyyy")) {
  					Date date = formatOld.parse(student.birthDate);
  	  				String formatedString = formatNew.format(date);
  	  				parsedDate = formatNew.parse(formatedString);
  				} else {
  					parsedDate = formatNew.parse(student.birthDate);
  				}

  				birthDate = new java.sql.Date(parsedDate.getTime()); // parsing Dateformat to SQL Dateformat

  			} catch (ParseException e) {
  				// TODO Auto-generated catch block
  				e.printStackTrace();
  			}
  			preparedStatement.setDate(4,birthDate);

  			// parse string to int for DB
  			if (isMaxStudentsInClass(student.addToClass.substring(0, 1))) {
				preparedStatement.setInt(5, Integer.parseInt(student.addToClass.substring(0, 1)));
			}else {
				preparedStatement.setString(5, "0");
			}

  			// int studentId = Integer.parseInt(student.id);
  			// preparedStatement.setInt(1, studentId); //do we really want to be able to update ID's ?

  			preparedStatement.executeUpdate(); // create SQL Update on DB
  			preparedStatement.close();
  		} catch (SQLException e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  		}
        initStudents();
      	//students.remove(student.getId());
        //students.put(student.getId(), student);
        return student;
      }

      public Boolean deleteStudent(String id) {

    	  String deleteSQL = "DELETE FROM students WHERE id = ?";

    	try {
    		PreparedStatement preparedStatement = con.prepareStatement(deleteSQL);
			// parse string to int for DB
			int studentId = Integer.parseInt(id);
			preparedStatement.setInt(1, studentId);
			preparedStatement.executeUpdate(); // create SQL  on DB
			preparedStatement.close();
      int auto_inc = getMaxId("students", "id") +1;
      String correctAI_SQL2 = "ALTER TABLE students AUTO_INCREMENT = " + auto_inc;
      PreparedStatement preparedStatement2 = con.prepareStatement(correctAI_SQL2);
      preparedStatement2.executeUpdate();
			preparedStatement2.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	students.remove(id);
        return true;
      }

      public ArrayList<StudentDetails> deleteStudents(ArrayList<String> ids) {

        for (int i = 0; i < ids.size(); ++i) {
          deleteStudent(ids.get(i));
        }

        return getStudentDetails();
      }

      public ArrayList<StudentDetails> getStudentDetails() {
        ArrayList<StudentDetails> studentDetails = new ArrayList<StudentDetails>();

        Iterator<String> it = students.keySet().iterator();
        while(it.hasNext()) {
          Student student = students.get(it.next());
          studentDetails.add(student.getLightWeightStudent());
        }

        return studentDetails;
      }

      public Student getStudent(String id) {
        String sqlQuerry = "SELECT * FROM students WHERE id = " + id;
        try{
          con = DriverManager.getConnection(myUrl,userName,password);
          statement = con.createStatement();
          ResultSet result = statement.executeQuery(sqlQuerry);
          while(result.next()){
            Student student = new Student(result.getString(1),result.getString(2),result.getString(3),
                                result.getString(4),result.getString(5),result.getString(6));
            return student;
          }

        } catch (Exception e) {
    		        e.printStackTrace();
    		        System.out.println("Error Trace in getConnection() : "  + e.getMessage());

    		}
        Student student = new Student();
        return student;
      }


/*
 * Created to solve the Problem
 * This functions help to execute the logic required <=20 students in one class
 */
      public Boolean isThisDateValid(String dateToValidate, String dateFormat){

  		if(dateToValidate == null){
  			return false;
  		}

  		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
  		sdf.setLenient(false);

  		try {
  			//if not valid, it will throw ParseException
  			Date date = sdf.parse(dateToValidate);
  		} catch (ParseException e) {
  			return false;
  		}

  		return true;
  	}

	   public Boolean isMaxStudentsInClass(String idModule) {

	      	String sqlQuerry = "SELECT modules_idmodules, COUNT(modules_idmodules) AS \"maxStudents\" FROM students WHERE modules_idmodules = " + idModule +" GROUP BY modules_idmodules ";

	      	try {

	      		con = DriverManager.getConnection(myUrl,userName,password);
	      		statement = con.createStatement();
	  			ResultSet rs = statement.executeQuery(sqlQuerry);

	  			if (con!=null) {
	  				while(rs.next()) {
	 	  				if (rs.getInt("maxStudents")>=20)
		  						return false;
	  				}

	  			}
	  			rs.close();
	  			rs = null;
	  		} catch (SQLException e) {
	  			// TODO Auto-generated catch block
	  			e.printStackTrace();
	  		}

	      	return true;

	      }


	   public Integer getMaxId(String tableName, String column) {

		   String sqlQuerry = "SELECT MAX(" + column + ") FROM " + tableName;

		   Integer result = null;

		   try {
				con = DriverManager.getConnection(myUrl,userName,password);
				statement = con.createStatement();
				ResultSet rs = statement.executeQuery(sqlQuerry);
				while(rs.next()) {
					if (con!=null) {
						result =  rs.getInt(1);
					}
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		   return result;
	   }

     public String getNextIdString(String tableName, String column) {
        int id = getMaxId(tableName, column);
        id += 1;
        return Integer.toString(id);
     }

	@SuppressWarnings("null")
	public List<String> getTable(String tableName) {

		   List<String> result = new ArrayList<String>() ;

		   String sqlQuerry = "SELECT * FROM " + tableName;
		   try {
			con = DriverManager.getConnection(myUrl,userName,password);
			statement = con.createStatement();
			ResultSet rs = statement.executeQuery(sqlQuerry);

			while(rs.next()) {
				String name = rs.getString(1) + " " + rs.getString(2);
				result.add(name);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   return result;

	   }

     public HashMap<String, Student> getStudents() {
       return students;
     }


}
