package pt.isep.cms.modules.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class AddModuleEvent extends GwtEvent<AddModuleEventHandler> {
  public static Type<AddModuleEventHandler> TYPE = new Type<AddModuleEventHandler>();
  
  @Override
  public Type<AddModuleEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(AddModuleEventHandler handler) {
    handler.onAddModule(this);
  }
}
