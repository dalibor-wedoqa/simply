package net.simplyanalytics.pageobjects.pages.main.views;

import net.simplyanalytics.pageobjects.sections.toolbar.businesses.BusinessesToolbar;
import net.simplyanalytics.pageobjects.sections.view.BusinessesViewPanel;
import net.simplyanalytics.pageobjects.sections.viewchooser.ViewChooserSection;

import org.openqa.selenium.WebDriver;

public class BusinessesPage extends BaseViewPage {

  private final BusinessesViewPanel businessesViewPanel;
  private final BusinessesToolbar businessesToolbar;

  /**
   * BusinessesPage constructor.
   * 
   * @param driver WebDriver
   */
  public BusinessesPage(WebDriver driver) {
    super(driver);
    businessesViewPanel = new BusinessesViewPanel(driver);
    businessesToolbar = new BusinessesToolbar(driver, new ViewChooserSection(driver).getTopViewType());
  }

  @Override
  public BusinessesToolbar getToolbar() {
    return businessesToolbar;
  }

  @Override
  public BusinessesViewPanel getActiveView() {
    return businessesViewPanel;
  }

}
