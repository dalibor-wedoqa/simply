package net.simplyanalytics.pageobjects.panels.color;

import java.util.List;

import net.simplyanalytics.pageobjects.base.BasePage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;

public class ColorSelectionPanel extends BasePage {

  @FindBy(css = ".sa-close")
  private WebElement closeButton;

  @FindBy(css = "li[data-tab='paletteTab']")
  private WebElement paletteButton;

  @FindBy(css = "li[data-tab='rgbTab']")
  private WebElement rgbButton;

  @FindBy(css = "li[data-tab='hslTab']")
  private WebElement hslButton;
  
  @FindBy(className = "sa-color-window-item-inner")
  private List<WebElement> colors;

  protected static final By rootLocator = By.cssSelector(".sa-color-window");
  protected WebElement root;

  public ColorSelectionPanel(WebDriver driver) {
    super(driver, rootLocator);
    this.root = driver.findElement(rootLocator);
  }

  @Override
  protected void isLoaded() {
    waitForElementToAppear(closeButton, "The close button (x icon) is missing");
    waitForElementToAppear(paletteButton, "The palette button is missing");
    waitForElementToAppear(rgbButton, "The RGB button is missing");
    waitForElementToAppear(hslButton, "The HSL button is missing");
  }

  /**
   * Click on the close button (x icon).
   */
  @Step("Click on the close button (x icon)")
  public void clickCloseButton() {
    logger.debug("Click on the close button (x icon)");
    closeButton.click();
    waitForElementToDisappear(root);
  }

  /**
   * Click on the Palette button.
   * 
   * @return PaletteTab
   */
  @Step("Click on the Palette button")
  public PaletteTab clickPaletteButton() {
    logger.debug("Click on the Palette button");
    paletteButton.click();
    return new PaletteTab(driver);
  }

  /**
   * Click on the RGB button.
   * 
   * @return RgbTab
   */
  @Step("Click on the RGB button")
  public RgbTab clickRgbButton() {
    logger.debug("Click on the RGB button");
    rgbButton.click();
    return new RgbTab(driver);
  }

  /**
   * Click on the HSL button.
   * 
   * @return HslTab
   */
  @Step("Click on the HSL button")
  public HslTab clickHslButton() {
    logger.debug("Click on the HSL button");
    hslButton.click();
    return new HslTab(driver);
  }

  /**
   * Getting actual color tab.
   * @return actual color tab
   */
  public BaseColorTab getActualTab() {
    WebElement acticeTab = root
        .findElement(By.cssSelector("[id*='tab']:not([style*='display: none'])"));
    String id = acticeTab.getAttribute("id").replaceAll("-tab(.*)", "");
    switch (id) {
      case "palette": {
        return new PaletteTab(driver);
      }
      case "rgb": {
        return new RgbTab(driver);
      }
      case "hsl": {
        return new HslTab(driver);
      }
      default: {
        throw new Error("The tab id is not in expected set: " + id);
      }
    }
  }
  
  public void chooseColorByIndex(int i) {
    colors.get(i).click();
  }
  
  public String getColorByIndex(int i) {
    return colors.get(i).getCssValue("background-color");
  }

  public boolean isDisplayed() {
    return isPresent(root);
  }

}
