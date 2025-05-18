package net.simplyanalytics.pageobjects.sections.ldb.data;

import java.util.List;

import net.simplyanalytics.pageobjects.sections.ldb.data.byimport.BaseImportPanel;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;

public class DataByMyImportedData extends DataBaseBrowsePanel {
  
  protected static final By ROOT_LOCATOR = By.cssSelector(".sa-imports-list");
  protected WebElement root;

  @FindBy(css = ".sa-imports-list-item")
  private List<WebElement> importedListItems;
    
  public DataByMyImportedData(WebDriver driver) {
    super(driver, ROOT_LOCATOR);
    root = driver.findElement(ROOT_LOCATOR);
  }

  @Override
  public void isLoaded() {
    
  }
  
  @Step("Click on an imported data folder")
  public BaseImportPanel clickFirstItem() {
    logger.debug("Click on the First item");
    waitForElementToBeClickable(importedListItems.get(0), "The User button is not clickable").click();
    return new BaseImportPanel(driver);
  }
  
  public String getNameOfFirstElement() {
    return importedListItems.get(0).getText();
  }
  
  public int getNumberOfListItems() {
    return importedListItems.size();
  }
  
}
