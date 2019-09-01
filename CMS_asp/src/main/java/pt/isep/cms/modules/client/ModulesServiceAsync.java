package pt.isep.cms.modules.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

import pt.isep.cms.modules.shared.Module;
import pt.isep.cms.modules.shared.ModuleDetails;

import java.util.ArrayList;

public interface ModulesServiceAsync {

  public void addModule(Module module, AsyncCallback<Module> callback);
  public void deleteModule(String id, AsyncCallback<Boolean> callback);
  public void deleteModules(ArrayList<String> ids, AsyncCallback<ArrayList<ModuleDetails>> callback);
  public void getModuleDetails(AsyncCallback<ArrayList<ModuleDetails>> callback);
  public void getModule(String id, AsyncCallback<Module> callback);
  public void updateModule(Module module, AsyncCallback<Module> callback);
}

