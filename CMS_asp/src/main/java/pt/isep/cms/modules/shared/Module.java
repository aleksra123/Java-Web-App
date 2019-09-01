package pt.isep.cms.modules.shared;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Module implements Serializable {
	public String id;
	public String Name;
	public String Description;
	public String Enrolled_Students; //Enrolled Students



	public Module() {}

	public Module(String id, String Name, String Description, String Enrolled_Students) {
	this.id = id;
    this.Name = Name;
    this.Description = Description;
    this.Enrolled_Students = Enrolled_Students;




	}


	public ModuleDetails getLightWeightModule() {
	  return new ModuleDetails(id, Name);
	}

  public String getId() { return id; }
  public void setId(String id) { this.id = id; }
  public String getName() { return Name; }
  public void setName(String Name) { this.Name = Name; }
  public String getDescription() { return Description; }
  public void setDescription(String Description) { this.Description = Description; }
  public String getEnrolled_Students() { return Enrolled_Students; }
  public void setEnrolled_Students(String Enrolled_Students) { this.Enrolled_Students = Enrolled_Students; }

}
