package net.simplyanalytics.pageobjects.sections.ldb.data.bydataset;

import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import net.simplyanalytics.pageobjects.base.BasePage;
import io.qameta.allure.Step;

public class DatasetSearchImportedDataPanel extends BasePage {
  
  protected static final By ROOT_LOCATOR = By.cssSelector(".sa-imports-data-browse-window-result-wrap");
  protected WebElement root;

  @FindBy(css = ".sa-text-field-input")
  private WebElement dataSearchElement;

  @FindBy(css = ".sa-attribute-selected")
  private List<WebElement> defaultCheckedElements;

  @FindBy(css = ".sa-attribute")
  private List<WebElement> allDataElements;

  public DatasetSearchImportedDataPanel(WebDriver driver) {
    super(driver, ROOT_LOCATOR);
    root = driver.findElement(ROOT_LOCATOR);
  }

  @Override
  public void isLoaded() {
    // TODO
  }

  /**
   * Getting recent favorite menu item with sub menu.
   * 
   * @param dataVariableName data variable name
   * @return RecentFavoriteMenuItemWithSubMenu
   */
  @Step("Click on data variable row: {0}")
  public void clickDataVariableRow(String dataVariableName) {
    for(WebElement row : allDataElements) {
      if(row.findElement(By.cssSelector(".sa-attribute-name")).getText().trim().equals(dataVariableName)) {
        waitForElementToBeClickable(row.findElement(By.cssSelector(".sa-attribute-body")), "Data is not clickable").click();
        return;
      }
    }
    throw new AssertionError("Data variable is not found");
  }
  
  @Step("Click on random data")
  public String clickOnRandomData() {
    int randomDataIndex = new Random().nextInt(allDataElements.size());
    logger.debug("Click on random data, with index: " + randomDataIndex);
    allDataElements.get(randomDataIndex).findElement(By.cssSelector(".sa-attribute-body")).click();
    String randomData = allDataElements.get(randomDataIndex).findElement(By.cssSelector(".sa-attribute-body .sa-attribute-name")).getText().trim();
    logger.info("Random data: " + randomData);
    return randomData;
  }

}
