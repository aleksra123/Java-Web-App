package pt.isep.cms.modules.client.event;

import com.google.gwt.event.shared.GwtEvent;
import pt.isep.cms.modules.shared.Module;

public class ModuleUpdatedEvent extends GwtEvent<ModuleUpdatedEventHandler>{
  public static Type<ModuleUpdatedEventHandler> TYPE = new Type<ModuleUpdatedEventHandler>();
  private final Module updatedModule;
  
  public ModuleUpdatedEvent(Module updatedModule) {
    this.updatedModule = updatedModule;
  }
  
  public Module getUpdatedModule() { return updatedModule; }
  

  @Override
  public Type<ModuleUpdatedEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(ModuleUpdatedEventHandler handler) {
    handler.onModuleUpdated(this);
  }
}
