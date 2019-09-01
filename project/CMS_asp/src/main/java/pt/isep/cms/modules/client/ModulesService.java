package pt.isep.cms.modules.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import pt.isep.cms.modules.shared.Module;
import pt.isep.cms.modules.shared.ModuleDetails;

import java.util.ArrayList;

@RemoteServiceRelativePath("modulesService")
public interface ModulesService extends RemoteService {
	
  Module addModule(Module module);
  Boolean deleteModule(String id); 
  ArrayList<ModuleDetails> deleteModules(ArrayList<String> ids);
  ArrayList<ModuleDetails> getModuleDetails();
  Module getModule(String id);
  Module updateModule(Module module);
}
