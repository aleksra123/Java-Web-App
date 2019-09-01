package pt.isep.cms.students.client;

import pt.isep.cms.client.ShowcaseConstants;




import pt.isep.cms.students.client.event.AddStudentEvent;
import pt.isep.cms.students.client.event.AddStudentEventHandler;
import pt.isep.cms.students.client.event.StudentUpdatedEvent;
import pt.isep.cms.students.client.event.StudentUpdatedEventHandler;
import pt.isep.cms.students.client.event.EditStudentEvent;
import pt.isep.cms.students.client.event.EditStudentEventHandler;
import pt.isep.cms.students.client.event.EditStudentCancelledEvent;
import pt.isep.cms.students.client.event.EditStudentCancelledEventHandler;
import pt.isep.cms.students.client.presenter.StudentsPresenter;
import pt.isep.cms.students.client.presenter.EditStudentPresenter;
import pt.isep.cms.students.client.presenter.Presenter;
import pt.isep.cms.students.client.view.StudentsView;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.i18n.client.Constants;
import com.google.gwt.user.client.ui.HasWidgets;

import pt.isep.cms.students.client.view.StudentsDialog;

public class StudentsController implements Presenter { // (ATB) No history at this level, ValueChangeHandler<String> {
	private final HandlerManager eventBus;
	private final StudentsServiceAsync rpcService;
	private HasWidgets container;

	public static interface CwConstants extends Constants {
	}

	/**
	 * An instance of the constants.
	 */
	private final CwConstants constants;
	private final ShowcaseConstants globalConstants;

	public StudentsController(StudentsServiceAsync rpcService, HandlerManager eventBus, ShowcaseConstants constants) {
		this.eventBus = eventBus;
		this.rpcService = rpcService;
		this.constants = constants;
		this.globalConstants = constants;

		bind();
	}

	private void bind() {
		// (ATB) No History at this level
		// History.addValueChangeHandler(this);

		eventBus.addHandler(AddStudentEvent.TYPE, new AddStudentEventHandler() {
			public void onAddStudent(AddStudentEvent event) {
				doAddNewStudent();
			}
		});

		eventBus.addHandler(EditStudentEvent.TYPE, new EditStudentEventHandler() {
			public void onEditStudent(EditStudentEvent event) {
				doEditStudent(event.getId());
			}
		});

		eventBus.addHandler(EditStudentCancelledEvent.TYPE, new EditStudentCancelledEventHandler() {
			public void onEditStudentCancelled(EditStudentCancelledEvent event) {
				doEditStudentCancelled();
			}
		});
		
		eventBus.addHandler(StudentUpdatedEvent.TYPE, new StudentUpdatedEventHandler() {
			public void onStudentUpdated(StudentUpdatedEvent event) {
				doStudentUpdated();
			}
		});
	}

	private void doAddNewStudent() {
		// Lets use the presenter to display a dialog...
		Presenter presenter = new EditStudentPresenter(rpcService, eventBus, new StudentsDialog(globalConstants, StudentsDialog.Type.ADD));
		presenter.go(container);

	}

	private void doEditStudent(String id) {
		Presenter presenter = new EditStudentPresenter(rpcService, eventBus, new StudentsDialog(globalConstants, StudentsDialog.Type.UPDATE), id);
		presenter.go(container);
	}

	private void doEditStudentCancelled() {
		// Nothing to update...
	}

	private void doStudentUpdated() {
		// (ATB) Update the list of Student s...
		Presenter presenter = new StudentsPresenter(rpcService, eventBus, new StudentsView());
		presenter.go(container);
	}

	public void go(final HasWidgets container) {
		this.container = container;

		Presenter presenter = new StudentsPresenter(rpcService, eventBus, new StudentsView());
		presenter.go(container);
	}

}
