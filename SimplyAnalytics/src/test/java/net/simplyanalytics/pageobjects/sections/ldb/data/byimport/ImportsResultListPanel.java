package net.simplyanalytics.pageobjects.sections.ldb.data.byimport;

import java.util.List;
import java.util.Random;

import net.simplyanalytics.pageobjects.base.BasePage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;

public class ImportsResultListPanel extends BasePage {
  
  protected static final By ROOT_LOCATOR = By.cssSelector(".sa-imports-data-browse-window-result-list");
  protected WebElement root;
  
  @FindBy(css = ".sa-attribute")
  private List<WebElement> listOfDatas;
  
  public ImportsResultListPanel(WebDriver driver) {
    super(driver, ROOT_LOCATOR);
    root = driver.findElement(ROOT_LOCATOR);
  }
  
  @Override
  public void isLoaded() {
    
  }
  
  @Step("Click on a random result")
  public String clickRandomData() {
    int index = new Random().nextInt(listOfDatas.size() - 1);
    waitForElementToBeClickable(listOfDatas.get(index), "Data is not clickable");
    logger.debug("Click on random  data variable");
    listOfDatas.get(index).click();
    return listOfDatas.get(index).getText();
  }
  
}
