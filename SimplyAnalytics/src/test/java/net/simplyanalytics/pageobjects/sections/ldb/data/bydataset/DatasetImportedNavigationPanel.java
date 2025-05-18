package net.simplyanalytics.pageobjects.sections.ldb.data.bydataset;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import net.simplyanalytics.pageobjects.base.BasePage;
import io.qameta.allure.Step;

public class DatasetImportedNavigationPanel extends BasePage {

  protected static final By ROOT_LOCATOR = By.cssSelector(".sa-datasets-list");
  protected WebElement root;
  
  @FindBy(css = ".sa-datasets-list-item")
  List<WebElement> datasetItems;
  
  public DatasetImportedNavigationPanel(WebDriver driver) {
    super(driver, ROOT_LOCATOR);
    root = driver.findElement(ROOT_LOCATOR);
  }

  @Override
  public void isLoaded() {

  }
  
  @Step("Click on the dataset: {0}")
  public void clickOnDataset(String datasetName) {
    logger.info("Click on dataset: " + datasetName);
    for(WebElement datasetElement : datasetItems) {
      if(datasetElement.findElement(By.cssSelector("div")).getText().trim().equals(datasetName))
        datasetElement.click();
    }
  }
  
}
