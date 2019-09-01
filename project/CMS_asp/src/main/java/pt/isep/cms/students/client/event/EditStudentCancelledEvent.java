package pt.isep.cms.students.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class EditStudentCancelledEvent extends GwtEvent<EditStudentCancelledEventHandler>{
  public static Type<EditStudentCancelledEventHandler> TYPE = new Type<EditStudentCancelledEventHandler>();
  
  @Override
  public Type<EditStudentCancelledEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(EditStudentCancelledEventHandler handler) {
    handler.onEditStudentCancelled(this);
  }
}
