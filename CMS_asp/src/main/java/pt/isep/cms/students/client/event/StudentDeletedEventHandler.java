package pt.isep.cms.students.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface StudentDeletedEventHandler extends EventHandler {
  void onStudentDeleted(StudentDeletedEvent event);
}
