package pt.isep.cms.students.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import pt.isep.cms.students.client.StudentsService;
import pt.isep.cms.students.client.StudentsServiceAsync;
import pt.isep.cms.students.client.presenter.StudentsPresenter;
import pt.isep.cms.students.client.view.StudentsView;
import pt.isep.cms.students.shared.Student;
import pt.isep.cms.students.shared.StudentDetails;

// Nao se pode usar o easymock com testes GWT pois este usar reflection e o GWT não consegue "transpile"....
//import static org.easymock.EasyMock.createStrictMock;

import java.util.ArrayList;

public class StudentGWTTest extends GWTTestCase {
	private StudentsPresenter studentsPresenter;
	private StudentsServiceAsync rpcService;
	private HandlerManager eventBus;
	private StudentsPresenter.Display mockDisplay;

	public String getModuleName() {
		return "pt.isep.cms.students.TestCMSJUnit";
	}

	public void gwtSetUp() {
		rpcService = GWT.create(StudentsService.class);
		mockDisplay = new StudentsView();
		studentsPresenter = new StudentsPresenter(rpcService, eventBus, mockDisplay);
	}

	public void testStudentSort() {
		ArrayList<StudentDetails> studentDetails = new ArrayList<StudentDetails>();
		studentDetails.add(new StudentDetails("0", "c_student"));
		studentDetails.add(new StudentDetails("1", "b_student"));
		studentDetails.add(new StudentDetails("2", "a_student"));
		studentsPresenter.setStudentDetails(studentDetails);
		studentsPresenter.sortStudentDetails();
		assertTrue(studentsPresenter.getStudentDetail(0).getDisplayName().equals("a_student"));
		assertTrue(studentsPresenter.getStudentDetail(1).getDisplayName().equals("b_student"));
		assertTrue(studentsPresenter.getStudentDetail(2).getDisplayName().equals("c_student"));
	}

	public void testStudentsService() {
		// Create the service that we will test.
		StudentsServiceAsync studentsService = GWT.create(StudentsService.class);
		ServiceDefTarget target = (ServiceDefTarget) studentsService;
		target.setServiceEntryPoint(GWT.getModuleBaseURL() + "students/studentsService");

		// Since RPC calls are asynchronous, we will need to wait for a response
		// after this test method returns. This line tells the test runner to wait
		// up to 10 seconds before timing out.
		delayTestFinish(10000);

		// fail("Ainda nao implementado");

		// Send a request to the server.
		studentsService.getStudent("2", new AsyncCallback<Student>() {
			public void onFailure(Throwable caught) {
				// The request resulted in an unexpected error.
				fail("Request failure: " + caught.getMessage());
			}

			public void onSuccess(Student result) {
				// Verify that the response is correct.
				assertTrue(result != null);

				// Now that we have received a response, we need to tell the test runner
				// that the test is complete. You must call finishTest() after an
				// asynchronous test finishes successfully, or the test will time out.
				finishTest();
			}
		});
	}
}
