package pt.isep.cms.modules.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class EditModuleCancelledEvent extends GwtEvent<EditModuleCancelledEventHandler>{
  public static Type<EditModuleCancelledEventHandler> TYPE = new Type<EditModuleCancelledEventHandler>();
  
  @Override
  public Type<EditModuleCancelledEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(EditModuleCancelledEventHandler handler) {
    handler.onEditModuleCancelled(this);
  }
}
