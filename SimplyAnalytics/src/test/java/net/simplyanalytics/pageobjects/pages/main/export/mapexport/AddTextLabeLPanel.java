package net.simplyanalytics.pageobjects.pages.main.export.mapexport;

import net.simplyanalytics.pageobjects.base.BasePage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;

public class AddTextLabeLPanel extends BasePage {

  protected WebElement root;

  protected AddTextLabeLPanel(WebDriver driver) {
    super(driver, LayoutWindow.ADD_TEXT_LABEL_PANEL);
    this.root = driver.findElement(LayoutWindow.ADD_TEXT_LABEL_PANEL);
  }

  @FindBy(xpath = ".//table[contains(@class,'sa-export-add-text-label-window-field-input')]"
      + "//input")
  private WebElement labelInputField;

  @FindBy(xpath = ".//a[.//span[normalize-space(.)='OK']]")
  private WebElement okButton;

  @FindBy(xpath = ".//a[.//span[normalize-space(.)='Cancel']]")
  private WebElement cancelButton;

  @FindBy(xpath = ".//div[contains(@class,'sa-prompt-window-header')]"
      + "//button[contains(@class,'sa-close')]")
  private WebElement closeButton;

  @Override
  protected void isLoaded() {
    waitForElementToAppear(labelInputField, "The label input field is missing");
    waitForElementToAppear(okButton, "The OK button is missing");
    waitForElementToAppear(cancelButton, "The Cancel button is missing");
    waitForElementToAppear(closeButton, "The close button is missing");
  }

  /**
   * Enter text label.
   * 
   * @param textLabel text label
   */
  @Step("Enter text label: {0}")
  public void enterTextLabel(String textLabel) {
    logger.debug("Enter text label: " + textLabel);
    labelInputField.click();
    labelInputField.clear();
    labelInputField.sendKeys("" + textLabel);
    root.click();
  }

  public String getTextLabel() {
    return labelInputField.getAttribute("value");
  }

  /**
   * Click on the close button (x icon).
   */
  @Step("Click on the close button (x icon)")
  public void clickClose() {
    logger.debug("Click on the close button (x icon)");
    closeButton.click();
    waitForElementToDisappear(root);
  }

  /**
   * Click on the Cancel button.
   */
  @Step("Click on the Cancel button")
  public void clickCancel() {
    logger.debug("Click on the Cancel button");
    cancelButton.click();
    waitForElementToDisappear(root);
  }

  /**
   * Click on the OK button.
   */
  @Step("Click on the OK button")
  public void clickOk() {
    logger.debug("Click on the OK button");
    okButton.click();
    waitForElementToDisappear(root);
  }

  public boolean isDisplayed() {
    return isPresent(root);
  }

}