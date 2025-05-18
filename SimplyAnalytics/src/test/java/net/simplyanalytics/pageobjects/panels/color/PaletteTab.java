package net.simplyanalytics.pageobjects.panels.color;

import java.util.List;

import net.simplyanalytics.utils.ColorUtils;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;

public class PaletteTab extends BaseColorTab {
  
  @FindBy(className = "sa-color-window-item-inner")
  private List<WebElement> colors;

  protected static final By rootLocator = By.cssSelector("[id*='palette-tab']");

  protected PaletteTab(WebDriver driver) {
    super(driver, rootLocator);
  }

  @Override
  protected void isLoaded() {

  }

  /**
   * Click on the color with hex code.
   * 
   * @param hexcode hex code
   */
  @Step("Click on the color with hex code: {0}")
  public void clickOnColorButton(String hexcode) {
    logger.debug("Click on the color with hex code: " + hexcode);
    try {
      root.findElement(By.cssSelector("[id*='color-palette'] [style*='#" + hexcode + "']")).click();
      waitForElementToDisappear(root);
    } catch (NoSuchElementException e) {
      throw new Error("The color button with hexcode not found: " + hexcode);
    }
  }
  
  public void chooseColorByIndex(int i) {
    colors.get(i).click();
  }
  
  public String getColorByIndex(int i) {
    return ColorUtils.getBackgroundHexColor(colors.get(i));
  }

  
}
