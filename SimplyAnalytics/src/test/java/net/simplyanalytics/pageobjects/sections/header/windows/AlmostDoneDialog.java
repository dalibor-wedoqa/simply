package net.simplyanalytics.pageobjects.sections.header.windows;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import net.simplyanalytics.pageobjects.base.BasePage;
import io.qameta.allure.Step;

public class AlmostDoneDialog extends BasePage {

  protected static final By rootLocator = By.cssSelector(".sa-alert-default");
  protected WebElement root;

  @FindBy(xpath = "//span[@class='sa-button-text']/..")
  private WebElement okButton;

  @FindBy(css= "use")
  private WebElement closeIcon;

  @FindBy(css = ".sa-alert-body")
  private WebElement content;

  public AlmostDoneDialog(WebDriver driver) {
    super(driver, rootLocator);
    root = driver.findElement(rootLocator);
  }

  @Override
  protected void isLoaded() {
    waitForElementToAppear(closeIcon, "The close icon is missing");
    waitForElementToAppear(content, "The content of the message is missing");
    waitForElementToAppear(okButton, "The ok button is missing");
  }

  public List<String> getProjects() {
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

  /**
   * Click on the close button (x icon).
   */
  @Step("Click on the close button (x icon)")
  public void clickClose() {
    logger.debug("Click on the close button (x icon)");
    closeIcon.click();
    waitForElementToDisappear(root);
  }

  public boolean isDisplayed() {
    return isPresent(root);
  }
}
