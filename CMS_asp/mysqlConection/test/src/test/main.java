package test;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;

public class main {
	public static void main (String[] args) {
		
		MysqlConnect mysqlConnect = new MysqlConnect();
		
		String sql = "SELECT * FROM `contacts`";
		try {
		    PreparedStatement statement = mysqlConnect.connect().prepareStatement(sql);
		   
		    ResultSet rs = statement.executeQuery();
		    
		    int rows = getRows(rs);
		    
		    int columns = statement.getMetaData().getColumnCount();

		    
		    String[] neue = new String[rows];
		    /*
		    System.out.println(rs.);
		    */
		    StringBuilder message = new StringBuilder();
		   
		    
		    int count = 0;
		    while (rs.next()) {
		        for (int i = 1; i <= columns; i++) {
		    	
	
		       
		            
		        	message.append(rs.getString(i) + " ");
		        }
		        message.append("\n");
		    }
		    
		    //*/
		    System.out.println(message);
		    

		    //System.out.println(neue.toString());  // print table contents
		} catch (SQLException e) {
		    e.printStackTrace();
		} finally {
		    mysqlConnect.disconnect();
		}
	}
	
	public static int getRows(ResultSet res){
	    int totalRows = 0;
	    try {
	        res.last();
	        totalRows = res.getRow();
	        res.beforeFirst();
	    } 
	    catch(Exception ex)  {
	        return 0;
	    }
	    return totalRows ;    
	}
}
