package net.simplyanalytics.pageobjects.sections.header.importpackage;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import net.simplyanalytics.pageobjects.base.BasePage;
import io.qameta.allure.Step;

public class ErrorWindow extends BasePage {

  protected static final By rootLocator = By.cssSelector(".sa-import-file-step-error-window");
  protected WebElement root;

  @FindBy(xpath = ".//a[.//span[normalize-space(.)='OK']]")
  private WebElement okButton;

  @FindBy(css = ".sa-import-file-step-error-window-error-list ")
  private WebElement content;

  public ErrorWindow(WebDriver driver) {
    super(driver, rootLocator);
    root = driver.findElement(rootLocator);
  }

  @Override
  protected void isLoaded() {
    waitForElementToAppear(content, "The content of the message is missing");
    waitForElementToAppear(okButton, "The ok button is missing");
  }

  public List<String> getErrors() {
    return Arrays.asList(content.getText().split(System.lineSeparator()));
  }

  /**
   * Click on the OK button.
   */
  @Step("Click on the OK button")
  public void clickOkButton() {
    logger.debug("Click on the OK button");
    okButton.click();
    waitForElementToDisappear(root);
  }

  public boolean isDisplayed() {
    return isPresent(root);
  }

}
