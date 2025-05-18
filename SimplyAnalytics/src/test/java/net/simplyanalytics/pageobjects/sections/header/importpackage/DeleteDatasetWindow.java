package net.simplyanalytics.pageobjects.sections.header.importpackage;

import net.simplyanalytics.pageobjects.base.BasePage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;

public class DeleteDatasetWindow extends BasePage {
   
  protected static final By rootLocator = By.cssSelector(".sa-delete-dataset-window");
  protected WebElement root;
  
  @FindBy(xpath = "//a[contains(@class,'sa-button') and .//span[text()='Yes, delete dataset']]")
  private WebElement confirmDelete;
  
  @FindBy(xpath = "//a[contains(@class,'sa-button') and .//span[text()='Keep dataset']]")
  private WebElement discardDelete;
  
  
  @FindBy(xpath = "//a[contains(@class,'sa-button') and .//span[text()='Deleting dataset…']]")
  private WebElement deletingDatasetButton;
  
  
  public DeleteDatasetWindow(WebDriver driver) {
    super(driver, rootLocator);
    this.root = driver.findElement(rootLocator);
  }
  
  public void isLoaded() {
    waitForElementToBeClickable(confirmDelete, "Confirm button is not clickable");
    waitForElementToBeClickable(discardDelete, "Discard button is not clickable");
  }
  
  @Step("Confirm delete dataset")
  public void clickConfirm() {
    logger.debug("Confirm delete dataset");
    confirmDelete.click();
    waitForElementToDisappear(deletingDatasetButton, "The delete window is still present");
    waitForElementToDisappear(root);
  }
  
  @Step("Discard delete dataset")
  public void clickDiscard() {
    logger.debug("Discard delete dataset");
    discardDelete.click();
  }
  
}
