package net.simplyanalytics.pageobjects.pages.main.export.mapexport;

import net.simplyanalytics.pageobjects.base.BasePage;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import io.qameta.allure.Step;

public class TextLabel extends BasePage {

  protected WebElement root;

  protected TextLabel(WebDriver driver, WebElement root) {
    super(driver, root);
    this.root = root;
  }

  @Override
  protected void isLoaded() {

  }

  public Dimension getSize() {
    return root.getSize();
  }

  public Point getLocation() {
    return root.getLocation();
  }

  /**
   * Click on the Text Label.
   * 
   * @return TextLabelEditPanel
   */
  @Step("Click on the Text Label")
  public TextLabelEditPanel clickTextLabelImage() {
    logger.debug("Click on the Text Label Image");
    root.click();
    return new TextLabelEditPanel(driver);
  }

  /**
   * Move text label with the following vector.
   * 
   * @param x point on x-axis
   * @param y point on y-axis
   */
  @Step("Move text label with the following vector: ({0},{1})")
  public void moveTextLabelImage(int x, int y) {
    logger.debug("Move text label with the following vector: (" + x + "," + y + ")");
    Actions actions = new Actions(driver);
    actions.dragAndDropBy(root, x, y).build().perform();
  }

}
