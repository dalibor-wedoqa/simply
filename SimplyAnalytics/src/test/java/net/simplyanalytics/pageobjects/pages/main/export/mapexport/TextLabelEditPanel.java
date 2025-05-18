package net.simplyanalytics.pageobjects.pages.main.export.mapexport;

import net.simplyanalytics.pageobjects.base.BasePage;
import net.simplyanalytics.pageobjects.panels.color.ColorSelectionPanel;
import net.simplyanalytics.utils.ColorUtils;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;

public class TextLabelEditPanel extends BasePage {
  protected WebElement root;

  @FindBy(xpath = ".//table[contains(@class,'sa-label-edit-window-label-field')]//input")
  private WebElement labelInputField;

  @FindBy(xpath = ".//a[contains(@class,'sa-remove-button')]")
  private WebElement deleteButton;

  @FindBy(xpath = ".//a[contains(@class, 'sa-label-edit-window-typeface-combo')]/span[contains(@class, 'sa-button-text')]/..")
  private WebElement fontComboBox;

  @FindBy(xpath = ".//a[contains(@class, 'sa-label-edit-window-font-size-combo')]")
  private WebElement fontSizeComboBox;

  @FindBy(css = "a:nth-child(2) .sa-color-chooser-inner")
  private WebElement textColorChooser;

  @FindBy(css = "a:nth-child(3) .sa-color-chooser-inner")
  private WebElement backgroundColorChooser;

  @FindBy(css = ".sa-label-edit-window-bold-btn")
  private WebElement boldButton;

  @FindBy(css = ".sa-label-edit-window-italic-btn")
  private WebElement italicButton;

  @FindBy(xpath = ".//tbody[.//label[text()='Left:']]//input")
  private WebElement leftInputField;

  @FindBy(xpath = ".//tbody[.//label[text()='Top:']]//input")
  private WebElement topInputField;

  protected TextLabelEditPanel(WebDriver driver) {
    super(driver, LayoutWindow.LABEL_EDIT_PANEL);
    this.root = driver.findElement(LayoutWindow.LABEL_EDIT_PANEL);

  }

  @Override
  protected void isLoaded() {
    waitForElementToAppear(labelInputField, "The width input field is missing");
    waitForElementToAppear(deleteButton, "The delete button is missing");

    waitForElementToAppear(fontComboBox, "The typeface combo box is missing");

    waitForElementToBeClickable(fontSizeComboBox, "The font size combo box is missing");
    waitForElementToAppear(textColorChooser, "The text color chooser is missing");
    waitForElementToAppear(backgroundColorChooser, "The background color chooser is missing");
    waitForElementToAppear(boldButton, "The bold button is missing");
    waitForElementToAppear(italicButton, "The background color chooser is missing");

    waitForElementToAppear(leftInputField, "The left input field is missing");

    waitForElementToAppear(topInputField, "The top input field is missing");
  }

  /**
   * Replacing text label with some text.
   * 
   * @param text replace text
   */
  @Step("Replacing text label with the following text : {0}")
  public void enterText(String text) {
    logger.debug("Replacing text label with the following text: " + text);
    labelInputField.click();
    StringBuilder toSend = new StringBuilder();
    for (int i = 0; i < getTextLabel().length(); i++) {
      toSend.append(Keys.BACK_SPACE);
    }
    toSend.append(text);
    labelInputField.sendKeys(toSend.toString());
  }

  public String getTextLabel() {
    return labelInputField.getAttribute("value");
  }

  /**
   * Click on the delete button (x icon).
   */
  @Step("Click on the delete button (x icon)")
  public void clickDeleteButton() {
    logger.debug("Click on the delete button (x icon)");
    deleteButton.click();
    waitForElementToDisappear(root);
  }

  /**
   * Select font.
   * 
   * @param font selected font
   */
  @Step("Select the following font: {0}")
  public void selectFont(Font font) {
    logger.debug("Select the following font: " + font);
    sleep(500);
    fontComboBox.click();
    waitForElementToAppearByLocator(
        By.xpath("//div[contains(@class, 'sa-menu-button-menu') and not(contains(@style,'hidden'))]"
            + "//div[contains(@class, 'x-box-item') and contains(@id,'menucheckitem')"
            + " and normalize-space(.)=" + xpathSafe(font.getFontName()) + "]"),
        "Font dropdown not loaded").click();
  }

  public Font getSelectedFont() {
    String fontName = fontComboBox.getText();
    return Font.getFontByName(fontName);
  }

  /**
   * Select font size.
   * 
   * @param fontSize selected font size
   */
  @Step("Select the following font size: {0}")
  public void selectFontSize(Integer fontSize) {
    logger.debug("Select the following font szie: " + fontSize);
    sleep(500);
    fontSizeComboBox.click();
    waitForElementToAppearByLocator(
        By.xpath("//div[contains(@class, 'sa-menu-button-menu') and not(contains(@style,'hidden'))]"
            + "//div[contains(@class, 'x-box-item') and"
            + " contains(@id,'menucheckitem') and normalize-space(.)=" + fontSize + "]"),
        "Font size dropdown not loaded").click();
  }

  public int getFontSize() {
    String fontSize = fontSizeComboBox.getText();
    return Integer.parseInt(fontSize);
  }

  /**
   * Click on the text color button.
   * 
   * @return ColorSelectionPanel
   */
  @Step("Click on the text color button")
  public ColorSelectionPanel clickTextColorButton() {
    logger.debug("Click on the text color button");
    sleep(500);
    textColorChooser.click();
    return new ColorSelectionPanel(driver);
  }

  /**
   * Getting color Hex code.
   * 
   * @return color Hex code
   */
  public String getTextColorHexCode() {
    return ColorUtils.getBackgroundHexColor(textColorChooser);
  }

  /**
   * Click on the background color button.
   * 
   * @return ColorSelectionPanel
   */
  @Step("Click on the background color button")
  public ColorSelectionPanel clickBackgroundColorButton() {
    logger.debug("Click on the background color button");
    sleep(500);
    backgroundColorChooser.click();
    return new ColorSelectionPanel(driver);
  }

  /**
   * Getting background color Hex code.
   * 
   * @return background color Hex code
   */
  public String getBackgroundColorHexCode() {
    String style = backgroundColorChooser.getAttribute("style");
    if (style.equals("background-color: transparent;")) {
      return "Transparent";
    }
    return ColorUtils.getBackgroundHexColor(backgroundColorChooser);
  }

  public boolean isBoldButtonPressed() {
    return boldButton.getAttribute("class").contains("sa-button-pressed");
  }

  /**
   * Click on the bold button.
   */
  @Step("Click on the bold button")
  public void clickBoldButton() {
    logger.debug("Click on the bold button");
    boldButton.click();
    waitForElementToStop(root);
  }

  public boolean isItalicButtonPressed() {
    return italicButton.getAttribute("class").contains("sa-button-pressed");
  }

  /**
   * Click on the italic button.
   */
  @Step("Click on the italic button")
  public void clickItalicButton() {
    logger.debug("Click on the italic button");
    italicButton.click();
    waitForElementToStop(root);
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
    waitForElementToStop(root);
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
    waitForElementToStop(root);
  }

  public int getTop() {
    return Integer.parseInt(topInputField.getAttribute("value"));
  }
}