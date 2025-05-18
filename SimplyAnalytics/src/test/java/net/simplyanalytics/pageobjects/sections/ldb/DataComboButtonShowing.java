package net.simplyanalytics.pageobjects.sections.ldb;

import net.simplyanalytics.pageobjects.base.BasePage;
import net.simplyanalytics.pageobjects.sections.ldb.data.DataByMyImportedData;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;

public class DataComboButtonShowing extends BasePage {
  
  protected static final By ROOT = By
      .xpath("//div[contains(@class,'x-panel-body') and .//span[text()='Current Data & Locations']]");
  
  private final WebElement rootElement;
  
  @FindBy(xpath = "//a[contains(@id,'menuitem')]//span[text()='My Imported Data']")
  private WebElement myImportedDataItem;
  
  public DataComboButtonShowing(WebDriver driver) {
    super(driver, ROOT);
    rootElement = driver.findElement(ROOT);
  }
  
  @Override
  public void isLoaded() {
    
  }
  
  @Step("Click on a data to show")
  public DataByMyImportedData clickMyImportedData() {
    logger.debug("Click on the My Imported Data item");
    waitForElementToBeClickable(myImportedDataItem, "The User button is not clickable").click();
    return new DataByMyImportedData(driver);
  }
  
}
