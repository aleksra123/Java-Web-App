package pt.isep.cms.students.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface EditStudentEventHandler extends EventHandler {
  void onEditStudent(EditStudentEvent event);
}
