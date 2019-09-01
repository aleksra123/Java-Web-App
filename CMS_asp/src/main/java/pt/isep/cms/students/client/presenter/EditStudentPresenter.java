package pt.isep.cms.students.client.presenter;

import com.google.gwt.event.dom.client.ClickEvent;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.Window;
import pt.isep.cms.students.client.StudentsServiceAsync;
import pt.isep.cms.students.client.event.StudentUpdatedEvent;
import pt.isep.cms.students.client.event.EditStudentCancelledEvent;
import pt.isep.cms.students.shared.Student;

public class EditStudentPresenter implements Presenter {
	public interface Display {
		HasClickHandlers getSaveButton();

		HasClickHandlers getCancelButton();

		HasValue<String> getFirstName();

		HasValue<String> getLastName();

		HasValue<String> getGender();

		HasValue<String> getBirthDate();

		HasValue<String> getAddToClass();

		void show();

		void hide();
	}

	private Student student;
	private final StudentsServiceAsync rpcService;
	private final HandlerManager eventBus;
	private final Display display;

	public EditStudentPresenter(StudentsServiceAsync rpcService, HandlerManager eventBus, Display display) {
		this.rpcService = rpcService;
		this.eventBus = eventBus;
		this.student = new Student();
		this.display = display;
		bind();
	}

	public EditStudentPresenter(StudentsServiceAsync rpcService, HandlerManager eventBus, Display display, String id) {
		this.rpcService = rpcService;
		this.eventBus = eventBus;
		this.display = display;
		bind();

		rpcService.getStudent(id, new AsyncCallback<Student>() {
			public void onSuccess(Student result) {
				student = result;
				EditStudentPresenter.this.display.getFirstName().setValue(student.getFirstName());
				EditStudentPresenter.this.display.getLastName().setValue(student.getLastName());
				EditStudentPresenter.this.display.getGender().setValue(student.getGender());
				EditStudentPresenter.this.display.getBirthDate().setValue(student.getBirthDate());

				EditStudentPresenter.this.display.getAddToClass().setValue(student.getAddToClass());
			}

			public void onFailure(Throwable caught) {
				Window.alert("Error retrieving student");
			}
		});

	}

	public void bind() {
		this.display.getSaveButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				doSave();
				display.hide();
			}
		});

		this.display.getCancelButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				display.hide();
				eventBus.fireEvent(new EditStudentCancelledEvent());
			}
		});
	}

	public void go(final HasWidgets container) {
		display.show();
	}

	private void doSave() {
		student.setFirstName(display.getFirstName().getValue());
		student.setLastName(display.getLastName().getValue());
		student.setGender(display.getGender().getValue());
		student.setBirthDate(display.getBirthDate().getValue());

		student.setAddToClass(display.getAddToClass().getValue());
		// HER MÅ DU LAGRA RESTEN


		if (student.getId() == null) {
			// Adding new student
			rpcService.addStudent(student, new AsyncCallback<Student>() {
				public void onSuccess(Student result) {
					eventBus.fireEvent(new StudentUpdatedEvent(result));
				}

				public void onFailure(Throwable caught) {
					Window.alert("Error adding student");
				}
			});
		} else {
			// updating existing student
			rpcService.updateStudent(student, new AsyncCallback<Student>() {
				public void onSuccess(Student result) {
					eventBus.fireEvent(new StudentUpdatedEvent(result));
				}

				public void onFailure(Throwable caught) {
					Window.alert("Error updating student");
				}
			});
		}
	}

}
