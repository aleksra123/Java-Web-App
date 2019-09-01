package pt.isep.cms.students.client.presenter;

import pt.isep.cms.students.client.StudentsServiceAsync;

import pt.isep.cms.students.client.event.AddStudentEvent;
import pt.isep.cms.students.client.event.EditStudentEvent;
import pt.isep.cms.students.shared.StudentDetails;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import java.util.ArrayList;
import java.util.List;

public class StudentsPresenter implements Presenter {

	private List<StudentDetails> studentDetails;

	public interface Display {
		HasClickHandlers getAddButton();

		HasClickHandlers getDeleteButton();

		HasClickHandlers getList();

		void setData(List<String> data);

		int getClickedRow(ClickEvent event);

		List<Integer> getSelectedRows();

		Widget asWidget();
	}

	private final StudentsServiceAsync rpcService;
	private final HandlerManager eventBus;
	private final Display display;

	public StudentsPresenter(StudentsServiceAsync rpcService, HandlerManager eventBus, Display view) {
		this.rpcService = rpcService;
		this.eventBus = eventBus;
		this.display = view;
	}

	public void bind() {
		display.getAddButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				eventBus.fireEvent(new AddStudentEvent());
			}
		});

		display.getDeleteButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				deleteSelectedStudents();
			}
		});

		display.getList().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				int selectedRow = display.getClickedRow(event);

				if (selectedRow >= 0) {
					String id = studentDetails.get(selectedRow).getId();
					eventBus.fireEvent(new EditStudentEvent(id));
				}
			}
		});
	}

	public void go(final HasWidgets container) {
		bind();
		container.clear();
		container.add(display.asWidget());

		fetchStudentDetails();
	}

	public void sortStudentDetails() {

		// Yes, we could use a more optimized method of sorting, but the
		// point is to create a test case that helps illustrate the higher
		// level concepts used when creating MVP-based applications.
		//
		for (int i = 0; i < studentDetails.size(); ++i) {
			for (int j = 0; j < studentDetails.size() - 1; ++j) {
				if (studentDetails.get(j).getDisplayName()
						.compareToIgnoreCase(studentDetails.get(j + 1).getDisplayName()) >= 0) {
					StudentDetails tmp = studentDetails.get(j);
					studentDetails.set(j, studentDetails.get(j + 1));
					studentDetails.set(j + 1, tmp);
				}
			}
		}
	}

	public void setStudentDetails(List<StudentDetails> studentDetails) {
		this.studentDetails = studentDetails;
	}

	public StudentDetails getStudentDetail(int index) {
		return studentDetails.get(index);
	}

	private void fetchStudentDetails() {
		rpcService.getStudentDetails(new AsyncCallback<ArrayList<StudentDetails>>() {
			public void onSuccess(ArrayList<StudentDetails> result) {
				studentDetails = result;
				sortStudentDetails();
				List<String> data = new ArrayList<String>();

				for (int i = 0; i < result.size(); ++i) {
					data.add(studentDetails.get(i).getDisplayName());
				}

				display.setData(data);
			}

			public void onFailure(Throwable caught) {
				caught.printStackTrace();
				Window.alert("Error fetching student details");
			}
		});
	}

	private void deleteSelectedStudents() {
		List<Integer> selectedRows = display.getSelectedRows();
		ArrayList<String> ids = new ArrayList<String>();

		for (int i = 0; i < selectedRows.size(); ++i) {
			ids.add(studentDetails.get(selectedRows.get(i)).getId());
		}

		rpcService.deleteStudents(ids, new AsyncCallback<ArrayList<StudentDetails>>() {
			public void onSuccess(ArrayList<StudentDetails> result) {
				studentDetails = result;
				sortStudentDetails();
				List<String> data = new ArrayList<String>();

				for (int i = 0; i < result.size(); ++i) {
					data.add(studentDetails.get(i).getDisplayName());
				}

				display.setData(data);

			}

			public void onFailure(Throwable caught) {
				Window.alert("Error deleting selected students");
			}
		});
	}
}
