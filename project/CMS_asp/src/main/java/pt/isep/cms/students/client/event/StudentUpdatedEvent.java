package pt.isep.cms.students.client.event;

import com.google.gwt.event.shared.GwtEvent;


import pt.isep.cms.students.shared.Student;

public class StudentUpdatedEvent extends GwtEvent<StudentUpdatedEventHandler>{
  public static Type<StudentUpdatedEventHandler> TYPE = new Type<StudentUpdatedEventHandler>();
  private final Student updatedStudent;
  
  public StudentUpdatedEvent(Student updatedStudent) {
    this.updatedStudent = updatedStudent;
  }
  
  public Student getUpdatedStudent() { return updatedStudent; }
  

  @Override
  public Type<StudentUpdatedEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(StudentUpdatedEventHandler handler) {
    handler.onStudentUpdated(this);
  }
}
