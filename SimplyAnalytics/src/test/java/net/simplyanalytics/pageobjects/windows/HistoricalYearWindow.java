package net.simplyanalytics.pageobjects.windows;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import net.simplyanalytics.enums.HistoricalYearEnum;
import net.simplyanalytics.pageobjects.base.BasePage;
import net.simplyanalytics.pageobjects.pages.main.NewViewPage;

public class HistoricalYearWindow extends BasePage {

  protected static final By ROOT_ELEMENT = By.cssSelector(".sa-census-release-window");
  protected WebElement root;

  @FindBy(css = ".sa-release-selector-body .sa-release-selector-item")
  private List<WebElement> yearItem;
  
  @FindBy(xpath = ".//a[.//span[normalize-space(.)='OK']]")
  private WebElement okButton;
  
  @FindBy(xpath = ".//a[.//span[normalize-space(.)='Cancel']]")
  private WebElement cancelButton;

  public HistoricalYearWindow(WebDriver driver) {
    super(driver, ROOT_ELEMENT);
    root = driver.findElement(ROOT_ELEMENT);
    waitForElementToStop(root);
  }

  @Override
  protected void isLoaded() {
    waitForElementToAppear(okButton, "OK button should appear");
    waitForElementToAppear(cancelButton, "Cancel button should appear");
  }
  
  public void selectYear(HistoricalYearEnum yearEnum) {
    List<WebElement> yearList = driver.findElements(By.className("sa-release-selector-item-radio"));
    for (int i=0; i<yearList.size(); i++ ){
      WebElement yearOption = yearList.get(i);
      logger.debug("Available data years: " + yearOption.getText());
      logger.debug("Select year: " + yearEnum.getName());
      if (yearOption.getText().trim().equals(yearEnum.getName())){
        waitForElementToBeClickable(yearOption, "Year element is not clickable").click();
      }
    }
//    List<WebElement> yearList = yearItem.stream().filter(yearElement ->
//      yearElement.findElement(By.className("sa-release-selector-radio-radio")).getText().trim().contains(yearEnum.getName()))
//      .collect(Collectors.toList());
//    yearList.forEach(element -> System.out.println("Year List: "+element.getText()));
//     waitForElementToBeClickable(yearList.get(0), "Year element is not clickable").click();
  }
  
  public NewViewPage clickOKButton() {
    logger.debug("Click OK button");
    waitForElementToBeClickable(okButton, "OK button is not clickable").click();
    return new NewViewPage(driver);
  }
  
  public void clickCancelButton() {
    logger.debug("Click Cancel button");
    waitForElementToBeClickable(cancelButton, "Cancel button is not clickable").click();
  }
  
}
