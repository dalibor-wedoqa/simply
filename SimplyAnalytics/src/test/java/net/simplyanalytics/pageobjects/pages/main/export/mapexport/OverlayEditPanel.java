package net.simplyanalytics.pageobjects.pages.main.export.mapexport;

import net.simplyanalytics.pageobjects.base.BasePage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;

public class OverlayEditPanel extends BasePage {

  protected WebElement root;

  @FindBy(xpath = ".//tbody[.//label[text()='Width:']]//input")
  private WebElement widthInputField;

  @FindBy(xpath = ".//tbody[.//label[text()='Height:']]//input")
  private WebElement heightInputField;

  @FindBy(xpath = ".//tbody[.//label[text()='Left:']]//input")
  private WebElement leftInputField;

  @FindBy(xpath = ".//tbody[.//label[text()='Top:']]//input")
  private WebElement topInputField;

  public OverlayEditPanel(WebDriver driver) {
    super(driver, LayoutWindow.OVERLAY_EDIT_PANEL);
    this.root = driver.findElement(LayoutWindow.OVERLAY_EDIT_PANEL);
  }

  @Override
  protected void isLoaded() {
    waitForElementToAppear(widthInputField, "The width input field is missing");
    waitForElementToAppear(heightInputField, "The height input field is missing");
    waitForElementToAppear(leftInputField, "The left input field is missing");
    waitForElementToAppear(topInputField, "The top input field is missing");
  }

  /**
   * Enter width.
   * 
   * @param width width
   */
  @Step("Enter width: {0}")
  public void enterWidth(int width) {
    logger.debug("Enter width: " + width);
    widthInputField.click();
    widthInputField.clear();
    widthInputField.sendKeys("" + width);
    root.click();
  }

  public int getWidth() {
    return Integer.parseInt(widthInputField.getAttribute("value"));
  }

  /**
   * Enter height.
   * 
   * @param height height
   */
  @Step("Enter height: {0}")
  public void enterHeight(int height) {
    logger.debug("Enter height: " + height);
    heightInputField.click();
    heightInputField.clear();
    heightInputField.sendKeys("" + height);
    root.click();
  }

  public int getHeight() {
    return Integer.parseInt(heightInputField.getAttribute("value"));
  }

  /**
   * Enter left.
   * 
   * @param left left
   */
  @Step("Enter left: {0}")
  public void enterLeft(int left) {
    logger.debug("Enter left: " + left);
    leftInputField.click();
    leftInputField.clear();
    leftInputField.sendKeys("" + left);
    root.click();
  }

  public int getLeft() {
    return Integer.parseInt(leftInputField.getAttribute("value"));
  }

  /**
   * Enter top.
   * 
   * @param top top
   */
  @Step("Enter top: {0}")
  public void enterTop(int top) {
    logger.debug("Enter top: " + top);
    topInputField.click();
    topInputField.clear();
    topInputField.sendKeys("" + top);
    root.click();
  }

  public int getTop() {
    return Integer.parseInt(topInputField.getAttribute("value"));
  }
}