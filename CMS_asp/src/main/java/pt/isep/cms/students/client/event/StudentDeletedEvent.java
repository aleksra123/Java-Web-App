package pt.isep.cms.students.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class StudentDeletedEvent extends GwtEvent<StudentDeletedEventHandler>{
  public static Type<StudentDeletedEventHandler> TYPE = new Type<StudentDeletedEventHandler>();
  
  @Override
  public Type<StudentDeletedEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(StudentDeletedEventHandler handler) {
    handler.onStudentDeleted(this);
  }
}
