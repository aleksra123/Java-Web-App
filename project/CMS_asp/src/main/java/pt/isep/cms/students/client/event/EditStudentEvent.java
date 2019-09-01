package pt.isep.cms.students.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class EditStudentEvent extends GwtEvent<EditStudentEventHandler>{
  public static Type<EditStudentEventHandler> TYPE = new Type<EditStudentEventHandler>();
  private final String id;
  
  public EditStudentEvent(String id) {
    this.id = id;
  }
  
  public String getId() { return id; }
  
  @Override
  public Type<EditStudentEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(EditStudentEventHandler handler) {
    handler.onEditStudent(this);
  }
}
