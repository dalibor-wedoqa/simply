package net.simplyanalytics.pageobjects.pages.main.editviews;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.sections.view.editview.BaseEditViewPanel;

public class StartBySelectingScarboroughPage extends BaseEditViewPage {
  
  @FindBy(css = ".sa-project-view:not([class*=\"sa-project-view-hidden\"]) .sa-scarborough-crosstab-edit-select-location-message-text")
  private WebElement startBySelctionText;
  
  public StartBySelectingScarboroughPage(WebDriver driver) {
    super(driver, ViewType.SCARBOROUGH_CROSSTAB_TABLE);
  }

  @Override
  public void isLoaded() {
    waitForElementToAppear(startBySelctionText, "Start by selecting a location text");
  }

  @Override
  public BaseEditViewPanel getActiveView() {
    // TODO Auto-generated method stub
    return null;
  }
  
  
  
}
