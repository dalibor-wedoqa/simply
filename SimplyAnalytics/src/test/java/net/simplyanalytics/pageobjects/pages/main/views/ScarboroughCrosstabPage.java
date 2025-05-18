package net.simplyanalytics.pageobjects.pages.main.views;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import net.simplyanalytics.pageobjects.sections.toolbar.ScarboroughCrosstabToolbar;
import net.simplyanalytics.pageobjects.sections.view.ScarboroughCrosstabViewPanel;
import net.simplyanalytics.pageobjects.sections.viewchooser.ViewChooserSection;

public class ScarboroughCrosstabPage extends BaseViewPage {

  @FindBy(xpath = ".//div[contains(@class,'sa-project-view') and not(contains(@class,'sa-project-view-hidden'))]")
  private WebElement table;
  
  private final ScarboroughCrosstabToolbar scarboroughCrosstabToolbar;
  private final ScarboroughCrosstabViewPanel scarboroughCrosstabViewPanel;

  public ScarboroughCrosstabPage(WebDriver driver) {
    super(driver);
    scarboroughCrosstabToolbar 
      = new ScarboroughCrosstabToolbar(driver, new ViewChooserSection(driver).getTopViewType());
    scarboroughCrosstabViewPanel = new ScarboroughCrosstabViewPanel(driver);
  }
  
  @Override
  public void isLoaded() {
    waitForElementToAppear(table, "The Scarborough crosstab table is not loaded");
  }
  
  @Override
  public ScarboroughCrosstabToolbar getToolbar() {
    return scarboroughCrosstabToolbar;
  }
  
  @Override
  public ScarboroughCrosstabViewPanel getActiveView() {
    return scarboroughCrosstabViewPanel;
  }
  
}
