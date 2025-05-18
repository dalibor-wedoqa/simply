package net.simplyanalytics.pageobjects.pages.projectsettings;

import org.openqa.selenium.WebDriver;

import net.simplyanalytics.pageobjects.pages.main.MainPage;
import net.simplyanalytics.pageobjects.sections.toolbar.BaseToolbar;
import net.simplyanalytics.pageobjects.sections.view.base.ViewPanel;

public class ProjectSettingsPage extends MainPage {
  
  private final BaseToolbar toolbar;
  private final ProjectSettingsHeader projectSettingHeader;
  
  public ProjectSettingsPage(WebDriver driver) {
    super(driver);
    //toolbar = new BaseToolbar(driver);
    toolbar = null;
    projectSettingHeader = new ProjectSettingsHeader(driver);
  }
  
  @Override
  public void isLoaded() {
    
  }
  
  public ProjectSettingsHeader getProjectSettingsHeader() {
    System.out.println("Test");
    logger.debug("Fetching Project Settings Header");
    return projectSettingHeader;
  }
  
  public BaseToolbar getToolbar() {
    return toolbar;
  }
  
  public ViewPanel getActiveView() {
    if(getProjectSettingsHeader().isManageViewsSelected()) {
      return new ManageViewsPanel(driver);
    }
    else if(getProjectSettingsHeader().isRemoveLDBSelected()) {
      return new RemoveLDBPanel(driver);
    }
    else
      return new GeneralSettingsPanel(driver);
  }
  
}
