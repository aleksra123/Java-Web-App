package pt.isep.cms.students;

import com.google.gwt.event.shared.HandlerManager;

import pt.isep.cms.students.client.StudentsServiceAsync;
import pt.isep.cms.students.client.presenter.StudentsPresenter;
import pt.isep.cms.students.shared.StudentDetails;

import java.util.ArrayList;
import junit.framework.TestCase;

import static org.easymock.EasyMock.createStrictMock;

public class ExampleJRETest extends TestCase {
	private StudentsPresenter studentsPresenter;
	private StudentsServiceAsync mockRpcService;
	private HandlerManager eventBus;
	private StudentsPresenter.Display mockDisplay;

	protected void setUp() {
		mockRpcService = createStrictMock(StudentsServiceAsync.class);
		eventBus = new HandlerManager(null);
		mockDisplay = createStrictMock(StudentsPresenter.Display.class);
		studentsPresenter = new StudentsPresenter(mockRpcService, eventBus, mockDisplay);
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
}
