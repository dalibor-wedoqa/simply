package net.simplyanalytics.pageobjects.sections.view.map;

import java.util.List;

import net.simplyanalytics.pageobjects.base.BasePage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;

public class LegendHeaderMenuPanel extends BasePage {
  
  protected static final By ROOT_LOCATOR = By.cssSelector(".sa-arrow-menu");
  
  @SuppressWarnings("ucd")
  protected WebElement root;
  
  @FindBy(css = ".sa-menu-item-checkbox")
  private List<WebElement> headerMenuCheckboxes;
  
  @FindBy(css = ".sa-menu-item-radio")
  private List<WebElement> themeRadioButton;
  
  protected LegendHeaderMenuPanel(WebDriver driver) {
    super(driver);
    root = driver.findElement(ROOT_LOCATOR);
  }
  
  @Override
  public void isLoaded() {
    waitForAllElementsToAppear(headerMenuCheckboxes, "Check boxes does not appeard!");
  }
  
  @Step("Click on the Show Map Center check box")
  public void clickOnShowMapCenterCheckBox() {
    waitForElementToAppear(headerMenuCheckboxes.get(0), "The checkbox does not appear").click();
  }
  
  @Step("Click on the Show Title check box")
  public void clickOnShowTitleCheckBox() {
    waitForElementToAppear(headerMenuCheckboxes.get(1), "The checkbox does not appear").click();
  }
  
  @Step("Click on the Show Businesses check box")
  public void clickOnShowBusinessesCheckBox() {
    waitForElementToAppear(headerMenuCheckboxes.get(2), "The checkbox does not appear").click();
  }
  
  @Step("Click on the Show Color Ranges check box")
  public void clickOnShowColorRangesCheckBox() {
    waitForElementToAppear(headerMenuCheckboxes.get(3), "The checkbox does not appear").click();
  }
  
  /**
   * Change to dark theme.
   */
  @Step("Click on the Dark Theme radio button")
  public void clickOnDarkTheme() {
    waitForElementToAppear(themeRadioButton.get(0), "The radio button does not appear").click();
    waitForAllElementsToAppearByLocator(By.cssSelector(".sa-legend-dark"), "The theme does not changed to dark theme");
  }
  
  /**
   * Change to light theme.
   */
  @Step("Click on the Light Theme radio button")
  public void clickOnLightTheme() {
    waitForElementToAppear(themeRadioButton.get(1), "The radio button does not appear").click();
    waitForAllElementsToAppearByLocator(By.cssSelector(".sa-legend-light"),
        "The theme does not changed to light theme");
  }
}
