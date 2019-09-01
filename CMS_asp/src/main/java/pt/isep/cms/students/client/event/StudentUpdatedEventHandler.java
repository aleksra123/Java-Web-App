package pt.isep.cms.students.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface StudentUpdatedEventHandler extends EventHandler{
  void onStudentUpdated(StudentUpdatedEvent event);
}
