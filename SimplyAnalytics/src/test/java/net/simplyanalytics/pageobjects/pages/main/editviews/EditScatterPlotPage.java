package net.simplyanalytics.pageobjects.pages.main.editviews;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.main.views.BaseViewPage;
import net.simplyanalytics.pageobjects.pages.main.views.ScatterPlotPage;
import net.simplyanalytics.pageobjects.sections.view.editview.EditScatterPlotPanel;
import io.qameta.allure.Step;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EditScatterPlotPage  extends BaseEditViewPage {
  
  @FindBy(css = ".sa-edit-header-done-btn")
  private WebElement doneButton;

  //NB added code for closing the menu window
  @FindBy(css = ".sa-sidebar-collapse-btn-inner")
  private WebElement getCloseMenuButton;

  private final EditScatterPlotPanel editScatterPlotPanel;
  
  public EditScatterPlotPage(WebDriver driver) {
    super(driver, ViewType.SCATTER_PLOT);
    editScatterPlotPanel = new EditScatterPlotPanel(driver);
  }

  @Override
  public EditScatterPlotPanel getActiveView() {
    return editScatterPlotPanel;
  }

  public static void changePlotData(WebDriver driver) {
    try {
      driver.findElements(By.xpath("//span[contains(@class, 'sa-button-text') and text()='View Actions']/..")).get(1).click();
      System.out.println("Clicked 'View Actions' button.");

      driver.findElement(By.xpath("//span[contains(@class, 'x-menu-item-text') and text()='Edit View']")).click();
      System.out.println("Clicked 'Edit View' menu item.");

      driver.findElements(By.xpath("//div[contains(@class, 'sa-check-button sa-check-button-default x-border-box')]")).get(0).click();
      System.out.println("Clicked the check button.");

      driver.findElements(By.xpath("//span[contains(@class, 'sa-button-text') and text()='Done']/..")).get(1).click();
      System.out.println("Clicked parent of 'Done' button.");

    } catch (Exception e) {
      System.out.println("Error occurred while clicking parent elements: " + e.getMessage());
    }
  }

  //NB added code for closing the menu window
  public void clickCloseMenuButton(){
    WebDriverWait wait = new WebDriverWait(driver, 1); // Adjust timeout as needed
    try {
      wait.until(ExpectedConditions.visibilityOf(getCloseMenuButton));
      if (getCloseMenuButton.isDisplayed() && getCloseMenuButton.isEnabled()) {
        getCloseMenuButton.click();
        logger.debug("Clicked on the Close Menu button on panel.");
      } else {
        logger.debug("The Close Menu button is not displayed or enabled.");
      }
    } catch (Exception e) {
      logger.error("Failed to click on the Close Menu button on panel.", e);
    }
  }
  
  // The button is now the same for all views
  /*@Step("Click on the Done button")
  @Override
  public BaseViewPage clickDone() {
    logger.debug("Click Done");
    doneButton.click();
    waitForLoadingToDisappear();
    sleep(1000);
    return new ScatterPlotPage(driver);
  }*/

}
