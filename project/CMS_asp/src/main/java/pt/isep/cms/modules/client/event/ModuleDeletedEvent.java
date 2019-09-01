package pt.isep.cms.modules.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class ModuleDeletedEvent extends GwtEvent<ModuleDeletedEventHandler>{
  public static Type<ModuleDeletedEventHandler> TYPE = new Type<ModuleDeletedEventHandler>();
  
  @Override
  public Type<ModuleDeletedEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(ModuleDeletedEventHandler handler) {
    handler.onModuleDeleted(this);
  }
}
