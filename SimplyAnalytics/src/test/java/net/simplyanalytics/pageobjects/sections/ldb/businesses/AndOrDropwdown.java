package net.simplyanalytics.pageobjects.sections.ldb.businesses;

import net.simplyanalytics.pageobjects.base.BasePage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import io.qameta.allure.Step;

public class AndOrDropwdown extends BasePage {

  protected WebElement root;

  public AndOrDropwdown(WebDriver driver, WebElement root) {
    super(driver, root);
    this.root = root;
  }

  @Override
  protected void isLoaded() {
    waitForElementToAppearByLocator(By.cssSelector(".sa-divider-combo-button-button span"),
        "And/Or dropdown is not present");
  }

  /**
   * Click on the And button.
   */
  @Step("Click on the And button")
  public void clickAnd() {
    logger.debug("Click on the And button");
    root.findElement(By.xpath(".//span[text()='and']")).click();
    waitForElementToDisappear(root);
  }

  /**
   * Click on the Or button.
   */
  @Step("Click on the Or button")
  public void clickOr() {
    logger.debug("Click on the Or button");
    root.findElement(By.xpath(".//span[text()='or']")).click();
    waitForElementToDisappear(root);
  }

}