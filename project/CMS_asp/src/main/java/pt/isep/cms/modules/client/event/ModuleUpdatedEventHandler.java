package pt.isep.cms.modules.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface ModuleUpdatedEventHandler extends EventHandler{
  void onModuleUpdated(ModuleUpdatedEvent event);
}
