package pt.isep.cms.students.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class AddStudentEvent extends GwtEvent<AddStudentEventHandler> {
  public static Type<AddStudentEventHandler> TYPE = new Type<AddStudentEventHandler>();
  
  @Override
  public Type<AddStudentEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(AddStudentEventHandler handler) {
    handler.onAddStudent(this);
  }
}
