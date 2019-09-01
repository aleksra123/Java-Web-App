package pt.isep.cms.modules.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class EditModuleEvent extends GwtEvent<EditModuleEventHandler>{
  public static Type<EditModuleEventHandler> TYPE = new Type<EditModuleEventHandler>();
  private final String id;
  
  public EditModuleEvent(String id) {
    this.id = id;
  }
  
  public String getId() { return id; }
  
  @Override
  public Type<EditModuleEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(EditModuleEventHandler handler) {
    handler.onEditModule(this);
  }
}
