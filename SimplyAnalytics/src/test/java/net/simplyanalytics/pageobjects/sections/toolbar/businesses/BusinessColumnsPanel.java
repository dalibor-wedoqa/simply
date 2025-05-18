package net.simplyanalytics.pageobjects.sections.toolbar.businesses;

import java.util.Map;
import java.util.stream.Collectors;

import net.simplyanalytics.pageobjects.base.BasePage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import io.qameta.allure.Step;

public class BusinessColumnsPanel extends BasePage {

  protected static final By rootLocator = By
      .cssSelector("span[id*='business-report-columns-menu']");

  protected WebElement root;

  public BusinessColumnsPanel(WebDriver driver) {
    super(driver, rootLocator);
    root = driver.findElement(rootLocator);
  }

  @Override
  public void isLoaded() {

  }

  /**
   * Click on the checkbox.
   * @param name checkbox name
   */
  @Step("Click on the {0} checkbox")
  public void clickCheckbox(String name) {
    logger.debug("Click on the " + name + " checkbox");
    waitForElementToBeClickable(
        root.findElement(By.xpath(".//span[text()=" + xpathSafe(name) + "]")),
        "Element is not clickable").click();
  }

  /**
   * Getting checkbox values.
   * @return checkbox values
   */
  public Map<String, Boolean> getCheckboxValues() {
    return root.findElements(By.cssSelector("div.x-menu-item")).stream()
        .collect(Collectors.toMap(x -> x.findElement(By.cssSelector("a span")).getText(),
            y -> y.getAttribute("class").contains("x-menu-item-checked")));
  }
}
