package net.simplyanalytics.pageobjects.sections.view.map;

import net.simplyanalytics.enums.LocationType;
import net.simplyanalytics.pageobjects.base.BasePage;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import io.qameta.allure.Step;

public class LegendMapCenterPanel extends BasePage {
  
  protected static final By ROOT_LOCATOR = By.cssSelector(".sa-arrow-menu");
  
  @SuppressWarnings("ucd")
  protected WebElement root;

  protected LegendMapCenterPanel(WebDriver driver) {
    super(driver);
    root = driver.findElement(ROOT_LOCATOR);
  }
  
  @Override
  public void isLoaded() {
  }
  
  @Step("\"Click on the location type by index: {0}")
  public void selectLocationType(LocationType locationType) {
    logger.debug("Click on the location type: " + locationType.getSingularName());
    waitForElementToBeClickable(driver.findElement(By.xpath("//a[contains(@class, 'x-menu-item-link')]//span[text()='" + locationType.getSingularName() + "']")), locationType.getSingularName() + " is not present").click();
  }
  
  public List<LocationType> getLocationTypeList() {
    return driver.findElements(By.cssSelector(".x-menu-item")).stream()
      .filter(locationType -> !locationType.getText().trim().equals("Same as Map"))
      .map(locationType -> LocationType.getBySingularName(locationType.getText().trim())).collect(Collectors.toList());
  }

}
