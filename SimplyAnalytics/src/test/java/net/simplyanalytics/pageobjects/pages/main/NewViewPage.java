package net.simplyanalytics.pageobjects.pages.main;

import net.simplyanalytics.pageobjects.sections.toolbar.BaseToolbar;
import net.simplyanalytics.pageobjects.sections.view.NewViewPanel;

import org.openqa.selenium.WebDriver;

public class NewViewPage extends MainPage {

  private final BaseToolbar toolbar;
  private final NewViewPanel newViewPanel;

  /**
   * . Constructor for a class
   */
  public NewViewPage(WebDriver driver) {
    super(driver);
    //no toolbar
    toolbar = null;//new BaseToolbar(driver);
    newViewPanel = new NewViewPanel(driver);
  }

  @Override
  public BaseToolbar getToolbar() {
    return toolbar;
  }

  @Override
  public NewViewPanel getActiveView() {
    return newViewPanel;
  }

}
