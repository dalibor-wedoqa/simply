package net.simplyanalytics.pageobjects.sections.header.importpackage;

import net.simplyanalytics.pageobjects.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;

public class ReviewAndImportView extends BasePage {
  
  protected static final By rootLocator = By
      .xpath("//div[.//div[contains(@class,'step-indicator-step-active') and .//div[text()='Review & Import']]]");
  protected WebElement root;
  
  @FindBy(xpath = ".//a[.//span[normalize-space(.)='Back']]")
  private WebElement backButton;
  
  @FindBy(xpath = ".//a[.//span[normalize-space(.)='Begin Import']]")
  private WebElement beginImportButton;
  
  @FindBy(xpath = ".//a[.//span[normalize-space(.)='Cancel']]")
  private WebElement cancelButton;
  
  @FindBy(xpath = "//div[contains(@class,'sa-import-review-step-file-item') and .//span[contains(@class,'sa-import-review-step-geographic-unit')]]")
  private WebElement nameOfImportedFile;
  
  @FindBy(xpath = "//td[contains(@class,'sa-import-review-step-label') and text()='Display Name:']")
  private WebElement displayedNameOfImport;
  
  @FindBy(css = ".sa-prompt-window-ok-btn")
  private WebElement okSuccesfulImportButton;
  private WebElement okButton;
  
  public ReviewAndImportView(WebDriver driver) {
    super(driver, rootLocator);
    this.root = driver.findElement(rootLocator);
  }
  
  public void isLoaded() {
    
  }
  
  @Step("Click on Cancel")
  public void clickCancel() {
    logger.debug("Click on the Cancel button");
    cancelButton.click();
  }
  
  public boolean isElementPresent(By locatorKey) {
	    try {
	        driver.findElement(locatorKey);
	        return true;
	    } catch (org.openqa.selenium.NoSuchElementException e) {
	        return false;
	    }
	}
  @Step("Click on Begin Import")
  public SuccessfulDialog clickBeginImport() {
    logger.debug("Click on the Begin Import button");
    waitForElementToBeClickable(beginImportButton, "Begin import button is not clickable").click();
    waitForElementToAppearByLocatorWithCustomTime(By.cssSelector(".sa-import-status-window-body"), "Import progress window", 60);
    waitForElementToDisappear(By.cssSelector(".sa-import-status-window-body"));

    
    if(isElementPresent(By.cssSelector(".sa-import-request-error-window-svg-wrap"))) { 
    	logger.info("Import time out - expected");
    	return new SuccessfulDialog(driver);
    }
    else {
    	waitForElementToAppearByLocatorWithCustomTime(By.cssSelector(".sa-import-success-window"), "Import is not succesful", 60);
        logger.info("Import success");
        return new SuccessfulDialog(driver);
    }
  }

  @Step("Click on Begin Import")
  public LocationNotFoundWindow clickBeginImportLocationError() {
    logger.debug("Click on the Begin Import button");
    beginImportButton.click();
    return new LocationNotFoundWindow(driver);
  }

  @Step("Click on Back")
  public IdentifyDataView clickBack() {
    logger.debug("Click on the Back button");
    backButton.click();
    return new IdentifyDataView(driver);
  }
}
