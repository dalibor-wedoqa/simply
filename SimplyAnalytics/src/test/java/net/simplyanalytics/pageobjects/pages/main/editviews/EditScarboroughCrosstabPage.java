package net.simplyanalytics.pageobjects.pages.main.editviews;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.main.views.BaseViewPage;
import net.simplyanalytics.pageobjects.pages.main.views.ScarboroughCrosstabPage;
import net.simplyanalytics.pageobjects.sections.view.editview.EditScarboroughCrosstabPanel;
import io.qameta.allure.Step;

public class EditScarboroughCrosstabPage extends BaseEditViewPage {

    @FindBy(css = ".sa-edit-header-done-btn")
    private WebElement doneButton;
    
    private final EditScarboroughCrosstabPanel editScarboroughCrosstabPanel;

    public EditScarboroughCrosstabPage(WebDriver driver) {
      super(driver, ViewType.SCARBOROUGH_CROSSTAB_TABLE);
      editScarboroughCrosstabPanel = new EditScarboroughCrosstabPanel(driver);
    }
    
    @Override
    public EditScarboroughCrosstabPanel getActiveView() {
      return editScarboroughCrosstabPanel;
    }
    
    /*@Step("Click on the Done button")
    @Override
    public BaseViewPage clickDone() {
      logger.info("Click Done");
      doneButton.click();
      waitForLoadingToDisappear();
      sleep(1000);
      return new ScarboroughCrosstabPage(driver);
    }*/

}
