package pt.isep.cms.modules.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface ModuleDeletedEventHandler extends EventHandler {
  void onModuleDeleted(ModuleDeletedEvent event);
}
