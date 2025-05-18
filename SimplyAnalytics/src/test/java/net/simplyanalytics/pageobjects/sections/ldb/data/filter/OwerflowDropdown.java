package net.simplyanalytics.pageobjects.sections.ldb.data.filter;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import net.simplyanalytics.pageobjects.base.BasePage;

public class OwerflowDropdown extends BasePage {

  @FindBy(css = "span.sa-data-search-condition")
  protected List<WebElement> allFilterElements;

  @FindBy(css = "span.sa-data-search-condition-remove-wrap")
  protected List<WebElement> allRemoveWrap;

  public OwerflowDropdown(WebDriver driver) {
    super(driver);
  }

  @Override
  protected void isLoaded() {
    waitForElementToAppear(
        driver.findElement(By.cssSelector(".sa-data-search-conditions-overflow-conditions-wrap")),
        "The dropdown is missing");
    waitForElementToBeVisible(By.cssSelector(".sa-data-search-conditions-overflow-conditions-wrap"),
        "Dropdown missing");
  }

  public int getSizeOverflow() {
    return allFilterElements.size();
  }

  public List<WebElement> getAllFilterElements() {
    return allFilterElements;
  }

  public List<WebElement> getAllRemoveWrap() {
    return allRemoveWrap;
  }

}
