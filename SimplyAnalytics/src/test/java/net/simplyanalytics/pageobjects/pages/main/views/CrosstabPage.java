package net.simplyanalytics.pageobjects.pages.main.views;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import net.simplyanalytics.pageobjects.sections.toolbar.crosstab.CrosstabToolbar;
import net.simplyanalytics.pageobjects.sections.view.CrosstabViewPanel;
import net.simplyanalytics.pageobjects.sections.viewchooser.ViewChooserSection;

public class CrosstabPage extends BaseViewPage {

  @FindBy(css = ".sa-crosstab-view-table-target")
  private WebElement table;
  
  private final CrosstabToolbar crosstabToolbar;
  private final CrosstabViewPanel crosstabViewPanel;
  
  public CrosstabPage(WebDriver driver) {
    super(driver);
    crosstabToolbar 
      = new CrosstabToolbar(driver, new ViewChooserSection(driver).getTopViewType());
    crosstabViewPanel = new CrosstabViewPanel(driver);
  }
  
  @Override
  public void isLoaded() {
    //waitForElementToAppear(table, "The crosstab table is not loaded");
  }
  
  @Override
  public CrosstabToolbar getToolbar() {
    return crosstabToolbar;
  }
  
  @Override
  public CrosstabViewPanel getActiveView() {
    return crosstabViewPanel;
  }
}
