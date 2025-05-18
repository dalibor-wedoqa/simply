package net.simplyanalytics.pageobjects.pages.login;

import java.util.ArrayList;
import java.util.List;

import net.simplyanalytics.pageobjects.base.BasePage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;

public class DialogPage extends BasePage {

  //protected static final By ROOT_LOCATOR = By.cssSelector("#content > .dialog-visible");
  protected static final By ROOT_LOCATOR = By.cssSelector("div.page-content > .dialog-visible");
  protected WebElement root;

  @FindBy(css = "h4")
  private WebElement title;

  @FindBy(css = "p")
  private List<WebElement> messages;
  
  @FindBy(css = "div.error")
  private WebElement errorMessage;

  @FindAll({
          @FindBy(css = "input.dialog-close"),
          @FindBy(css = "input.sign-in-button")
  })
  private WebElement okButton;

//  @FindBy(css = "input.sign-in-button")
//  private WebElement signInButton;

  public DialogPage(WebDriver driver) {
    super(driver, ROOT_LOCATOR, true);
    root = driver.findElement(ROOT_LOCATOR);
  }

  @Override
  public void isLoaded() {
    waitForElementToAppear(title, "The title should appear" + title);
  }

  public String getTitle() {
    return title.getText();
  }
  
  /**
   * Getting the messages from the Dialog page.
   * @return Messages from the Dialog page
   */
  public List<String> getMessages() {
    waitForAllElementsToAppear(messages, "messages should be visible");
    List<String> result = new ArrayList<>();
    messages.stream().forEach(element -> result.add(element.getText()));
    return result;
  }
  
  /**
   * Getting the error messages from the Dialog page.
   * @return Messages from the Dialog page
   */
  public String getErrorMessage() {
    waitForElementToAppear(errorMessage, "error message should be visible");
    return errorMessage.getText().trim();
  }
  
  /**
   * Click on the Ok to close the dialog.
   */
  @Step("Click on the Ok/Close/Cancel button to close the dialog")
  public void clickOk() {
    waitForElementToAppear(okButton, "The button should appear");
    logger.debug("Click on the ok button");
    okButton.click();
  }
}
