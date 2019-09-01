package pt.isep.cms.modules.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import pt.isep.cms.modules.client.ModulesService;
import pt.isep.cms.modules.shared.Module;
import pt.isep.cms.modules.shared.ModuleDetails;

@SuppressWarnings("serial")
public class ModulesServiceImpl extends RemoteServiceServlet implements
    ModulesService {

  private final HashMap<String, Module> modules = new HashMap<String, Module>();

  public ModulesServiceImpl() {
    initModules();
  }

  private java.sql.Connection con = null;
  Statement statement = null;

  private void initModules() {

	  // TODO: Create a real UID for each module
	  try {
	        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();

	        con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/cmsDB?serverTimezone=UTC","root","12qwaszx"); 

	        String sqlQuerry = "SELECT idmodules ,classname, description FROM modules";
	        if (con != null) {
	        	System.out.println("Connection Successful!");
	        	statement = con.createStatement();
	        	ResultSet rs = statement.executeQuery(sqlQuerry);

	        	final String str = "no one";
	        	// insert the database entries to display them
	        	while (rs.next()) {
	              Module module = new Module(rs.getString(1), rs.getString(2), rs.getString(3), str);
	              modules.put(module.getId(), module);
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

  public Module addModule(Module module) {

	String insertSQL = "INSERT INTO modules(classname, description) VALUES (?, ?)";

  	try {
  		PreparedStatement preparedStatement = con.prepareStatement(insertSQL);
			preparedStatement.setString(1, module.Name);
			preparedStatement.setString(2, module.Description);
			preparedStatement.executeUpdate(); // create SQL Update on DB
			preparedStatement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

  	if(con!= null)
  		modules.put(module.getId(), module); // put on Display

  	return module;
  }

  public Module updateModule(Module module) {

	  String updateSQL = "UPDATE modules SET classname = ?, description = ? WHERE idmodules = ?";

	  try {
    		PreparedStatement preparedStatement = con.prepareStatement(updateSQL);
			preparedStatement.setString(1, module.Name);
			preparedStatement.setString(2, module.Description);

			// parse string to int for DB
			int moduleId = Integer.parseInt(module.getId());
			preparedStatement.setInt(3, moduleId);

			preparedStatement.executeUpdate(); // create SQL Update on DB
			preparedStatement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      modules.remove(module.getId());
      modules.put(module.getId(), module);
      return module;
  }

  public Boolean deleteModule(String id) {

	String deleteSQL = "DELETE FROM modules WHERE idmodules = ?";

  	try {
  		PreparedStatement preparedStatement = con.prepareStatement(deleteSQL);
			// parse string to int for DB
			int moduleId = Integer.parseInt(id);
			preparedStatement.setInt(1, moduleId);

			preparedStatement.executeUpdate(); // create SQL  on DB
			preparedStatement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      modules.remove(id);
      return true;
  }

  public ArrayList<ModuleDetails> deleteModules(ArrayList<String> ids) {

    for (int i = 0; i < ids.size(); ++i) {
      deleteModule(ids.get(i));
    }

    return getModuleDetails();
  }

  public ArrayList<ModuleDetails> getModuleDetails() {
    ArrayList<ModuleDetails> moduleDetails = new ArrayList<ModuleDetails>();

    Iterator<String> it = modules.keySet().iterator();
    while(it.hasNext()) {
      Module module = modules.get(it.next());
      moduleDetails.add(module.getLightWeightModule());
    }

    return moduleDetails;
  }

  public Module getModule(String id) {
    return modules.get(id);
  }
}
