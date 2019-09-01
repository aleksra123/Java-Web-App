package pt.isep.cms.students.shared;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Student implements Serializable {
	public String id;
  public String firstName;
  public String lastName;
  public String gender;
  public String birthDate;
  public String addToClass;
  public String emailAddress; // DON'T DELETE this, I have no clue why but it needs to be here..




	public Student() {}

	public Student(String id, String firstName, String lastName,
			String gender, String birthDate, String addToClass) { /*, String addToClass*/
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.birthDate = birthDate;

		this.addToClass = addToClass;
	}

	public StudentDetails getLightWeightStudent() {
	  return new StudentDetails(id, getFullName());
	}

  public String getId() { return id; }
  public void setId(String id) { this.id = id; }
  public String getFirstName() { return firstName; }
  public void setFirstName(String firstName) { this.firstName = firstName; }
  public String getLastName() { return lastName; }
  public void setLastName(String lastName) { this.lastName = lastName; }
  public String getGender() { return gender; }
  public void setGender(String gender) { this.gender = gender; }
  public String getBirthDate() { return birthDate; }
  public void setBirthDate(String birthDate) { this.birthDate = birthDate; }



  //Will we use this when we are implementing it as a database?
  public String getAddToClass() { return addToClass; }
  public void setAddToClass(String AddToClass) { this.addToClass = AddToClass; }


  public String getFullName() { return firstName + " " + lastName; }
}
