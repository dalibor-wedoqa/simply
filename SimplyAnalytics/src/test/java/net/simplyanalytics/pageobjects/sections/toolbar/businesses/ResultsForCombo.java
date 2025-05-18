package net.simplyanalytics.pageobjects.sections.toolbar.businesses;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import net.simplyanalytics.pageobjects.base.BasePage;
import net.simplyanalytics.pageobjects.pages.main.views.BusinessesPage;
import io.qameta.allure.Step;

public class ResultsForCombo extends BasePage {

  static By root = By.xpath("//div[contains(@class, 'sa-combo-button-menu')]"
      + "[not(contains(@style, 'visibility: hidden;'))]");

  private WebElement rootElement;
  
  @FindBy(css = "span.x-menu-item-text")
  private List<WebElement> businessList;
  
  public ResultsForCombo(WebDriver driver) {
    super(driver, root);
  }

  @Override
  public void isLoaded() {
    rootElement = waitForElementToAppear(driver.findElement(root),
        "Results for Combo is not loaded");
    waitForAllElementsToAppear(businessList, "businesses are missing");
  }

  /**
   * Click on business witn name.
   * @param businessesName businesses name
   * @return BusinessesPage
   */
  @Step("Click on business with name {0}")
  public BusinessesPage clickOnABusiness(String businessesName) {
    logger.debug("Click on business with name: " + businessesName);
    waitForElementToAppear(
        rootElement.findElement(By.xpath(".//span[normalize-space(.)= " + xpathSafe(businessesName) + "]")),
        "There is no business with the given Name").click();
    waitForElementToDisappear(By.cssSelector(".sa-grid-loading"));
    waitForLoadingToDisappear(rootElement, 200);
    return new BusinessesPage(driver);
  }
  
  public List<String> getBusinesses() {
    return businessList.stream()
        .map(element -> element.getText())
        .collect(Collectors.toList());
  }
  
}
