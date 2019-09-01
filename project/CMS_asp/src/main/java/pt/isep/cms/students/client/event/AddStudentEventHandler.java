package pt.isep.cms.students.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface AddStudentEventHandler extends EventHandler {
  void onAddStudent(AddStudentEvent event);
}
