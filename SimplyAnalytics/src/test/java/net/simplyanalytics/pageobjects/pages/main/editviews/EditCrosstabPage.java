package net.simplyanalytics.pageobjects.pages.main.editviews;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.main.views.BaseViewPage;
import net.simplyanalytics.pageobjects.pages.main.views.CrosstabPage;
import net.simplyanalytics.pageobjects.sections.view.editview.EditCrosstabPanel;
import io.qameta.allure.Step;

public class EditCrosstabPage extends BaseEditViewPage {

  
  @FindBy(css = ".sa-edit-header-done-btn")
  private WebElement doneButton;
  
  private final EditCrosstabPanel editCrosstabPanel;

  public EditCrosstabPage(WebDriver driver) {
    super(driver, ViewType.CROSSTAB_TABLE);
    editCrosstabPanel = new EditCrosstabPanel(driver);
  }
  
  @Override
  public EditCrosstabPanel getActiveView() {
    return editCrosstabPanel;
  }
  
  /*@Step("Click on the Done button")
  @Override
  public BaseViewPage clickDone() {
    logger.info("Click Done");
    doneButton.click();
    waitForLoadingToDisappear();
    sleep(1000);
    return new CrosstabPage(driver);
  }*/
  
}
