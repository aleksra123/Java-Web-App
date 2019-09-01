package pt.isep.cms.contacts.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import pt.isep.cms.contacts.client.ContactsService;
import pt.isep.cms.contacts.shared.Contact;
import pt.isep.cms.contacts.shared.ContactDetails;

@SuppressWarnings("serial")
public class ContactsServiceImpl extends RemoteServiceServlet implements
    ContactsService {

	private final HashMap<String, Contact> contacts = new HashMap<String, Contact>();

	//CONSTURCTOR
    public ContactsServiceImpl() {
	    initContacts();
	  }

	private java.sql.Connection con = null;
	Statement statement = null;

	private void initContacts() {
		// Establish a Connection to Database
		// TODO Auto-generated method stub
		try {
	        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();

	        con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/cmsDB?serverTimezone=UTC","root","12qwaszx"); 

	        String sqlQuerry = "SELECT idcontacts ,firstname, lastname, email FROM contacts";
	        if (con != null) {
	        	System.out.println("Connection Successful!");
	        	statement = con.createStatement();
	        	ResultSet rs = statement.executeQuery(sqlQuerry);

	        	// insert the database entries to display them
	        	while (rs.next()) {
                    Contact contact = new Contact(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
                    contacts.put(contact.getId(), contact);
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


    public Contact addContact(Contact contact) {
    	String insertSQL = "INSERT INTO contacts(firstname, lastname, email) VALUES (?, ?, ?)";

    	try {
    		PreparedStatement preparedStatement = con.prepareStatement(insertSQL);
			preparedStatement.setString(1, contact.firstName);
			preparedStatement.setString(2, contact.lastName);
			preparedStatement.setString(3, contact.emailAddress);
			preparedStatement.executeUpdate(); // create SQL Update on DB
			preparedStatement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	if(con!= null)
    		contacts.put(contact.getId(), contact); // put on Display

    	return contact;

      }

      public Contact updateContact(Contact contact) {
    	String updateSQL = "UPDATE contacts SET firstname = ?, lastname = ?, email = ? WHERE idcontacts = ?";

      	try {
      		PreparedStatement preparedStatement = con.prepareStatement(updateSQL);
  			preparedStatement.setString(1, contact.firstName);
  			preparedStatement.setString(2, contact.lastName);
  			preparedStatement.setString(3, contact.emailAddress);

  			// parse string to int for DB
  			int contactId = Integer.parseInt(contact.getId());
  			preparedStatement.setInt(4, contactId);

  			preparedStatement.executeUpdate(); // create SQL Update on DB
  			preparedStatement.close();
  		} catch (SQLException e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  		}
        contacts.remove(contact.getId());
        contacts.put(contact.getId(), contact);
        return contact;
      }

      public Boolean deleteContact(String id) {

    	String deleteSQL = "DELETE FROM contacts WHERE id = ?";

    	try {
    		PreparedStatement preparedStatement = con.prepareStatement(deleteSQL);
			// parse string to int for DB
			int contactId = Integer.parseInt(id);
			preparedStatement.setInt(1, contactId);

			preparedStatement.executeUpdate(); // create SQL  on DB
			preparedStatement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        contacts.remove(id);
        return true;
      }

      public ArrayList<ContactDetails> deleteContacts(ArrayList<String> ids) {

        for (int i = 0; i < ids.size(); ++i) {
          deleteContact(ids.get(i));
        }

        return getContactDetails();
      }

      public ArrayList<ContactDetails> getContactDetails() {
        ArrayList<ContactDetails> contactDetails = new ArrayList<ContactDetails>();

        Iterator<String> it = contacts.keySet().iterator();
        while(it.hasNext()) {
          Contact contact = contacts.get(it.next());
          contactDetails.add(contact.getLightWeightContact());
        }

        return contactDetails;
      }

      public Contact getContact(String id) {
        return contacts.get(id);
      }


}
