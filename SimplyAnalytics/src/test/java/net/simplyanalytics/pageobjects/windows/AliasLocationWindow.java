package net.simplyanalytics.pageobjects.windows;

import net.simplyanalytics.pageobjects.base.BasePage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;

public class AliasLocationWindow extends BasePage {
  
  private static By ROOT = By
      .cssSelector("div.sa-location-alias-window[id*='location-alias-window']");
  private WebElement root;
  
  @FindBy(css = "input")
  private WebElement alias;
  
  @FindBy(xpath = ".//a[.//span[normalize-space(.)='Save']]")
  private WebElement save;
  
  @FindBy(xpath = ".//a[.//span[normalize-space(.)='Cancel']]")
  private WebElement cancel;
  
  @FindBy(css = ".sa-close")
  private WebElement closeIcon;
  
  public AliasLocationWindow(WebDriver driver) {
    super(driver, ROOT);
    this.root = driver.findElement(ROOT);
  }
  
  @Override
  protected void isLoaded() {
    waitForElementToAppear(alias, "The alias name input didn't appear");
    waitForElementToAppear(save, "The save button didn't appear");
    waitForElementToAppear(cancel, "The cancel button didn't appear");
  }
  
  /**
   * Enter alias name.
   * @param name alias name
   */
  @Step("Enter alias name: {0}")
  public void enterAliasName(String name) {
    logger.debug("Enter alias name: " + name);
    alias.click();
    alias.clear();
    alias.sendKeys(name);
  }
  
  /**
   * Click on the Cancel button.
   */
  @Step("Click on the Cancel button")
  public void clickCancel() {
    logger.debug("Click on the Cancel button");
    cancel.click();
    waitForElementToDisappear(root);
  }
  
  /**
   * Click on the Save button.
   */
  @Step("Click on the Save button")
  public void clickSave() {
    logger.debug("Click on the Save button");
    save.click();
    waitForElementToDisappear(root, 20);
    // wait for header names to update
    sleep(4000);
  }
  
  /**
   * Click on the close button (X).
   */
  @Step("Click on the close button (X)")
  public void clickClose() {
    logger.debug("Click on the close button (X)");
    closeIcon.click();
//    waitForElementToDisappear(root);
  }
  
  public boolean isDisplayed() {
    return isPresent(root);
  }
}
