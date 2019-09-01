package pt.isep.cms.modules.shared;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ModuleDetails implements Serializable {
  private String id;
  private String displayName;
  
  public ModuleDetails() {
    new ModuleDetails("0", "");
  }

  public ModuleDetails(String id, String displayName) {
    this.id = id;
    this.displayName = displayName;
  }
  
  public String getId() { return id; }
  public void setId(String id) { this.id = id; }
  public String getDisplayName() { return displayName; }
  public void setDisplayName(String displayName) { this.displayName = displayName; } 
}
