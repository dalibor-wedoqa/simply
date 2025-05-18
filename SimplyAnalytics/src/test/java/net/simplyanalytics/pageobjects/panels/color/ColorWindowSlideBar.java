package net.simplyanalytics.pageobjects.panels.color;

import net.simplyanalytics.pageobjects.base.BasePage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;

public class ColorWindowSlideBar extends BasePage {

  @FindBy(css = ".sa-color-window-slider-arrow")
  protected WebElement arrow;

  protected WebElement root;

  public ColorWindowSlideBar(WebDriver driver, WebElement root) {
    super(driver, root);
    this.root = root;
  }

  @Override
  protected void isLoaded() {
    waitForElementToAppear(arrow, "The arrow is missing");
  }

  /**
   * Getting actual slide bar value.
   * @return actual slide bar value
   */
  public double getActualValue() {
    int length = root.getSize().getWidth() - 2; // 1 pixel margin
    double arrowLeft = Double.parseDouble(
        arrow.getAttribute("style").replace("left: ", "").replace("px", "").replace(";", ""));
    int arrowWidth = arrow.getSize().getWidth();
    double arrowMiddle = arrowLeft + (arrowWidth / 2);
    return arrowMiddle / length;
  }

  /**
   * Click on slideBar on some value calculated from the left side.
   * @param value value
   */
  @Step("Click on slideBar on the {0}% value calculated from the left side")
  public void clickOnValue(double value) {
    logger.debug(
        String.format("Click on slideBar on the %.2f value calculated from the left side", value));
    Actions actions = new Actions(driver);
    int x = (int) ((root.getSize().getWidth() - 2) * value) - root.getSize().getWidth() / 2;
    actions.moveToElement(root, x, 0).click().build().perform();
  }
}
