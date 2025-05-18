package net.simplyanalytics.pageobjects.sections.ldb.businesses;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import net.simplyanalytics.pageobjects.base.BasePage;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;

public class BusinessesCategoriesPanel extends BasePage {
  
  protected static final List<String> blackList = Arrays.asList("4529");
  
  protected static final By ROOT_LOCATOR = By
      .cssSelector(".sa-business-code-browser:not([style*='visibility: hidden;'])");
  
  @SuppressWarnings("ucd")
  protected WebElement root;
  
  @FindBy(css = ".sa-toggle-switch")
  private WebElement toggleSwitchElement;
  
  @FindBy(css = ".sa-business-code-browser-row")
  private List<WebElement> allBusinessesElementsList;
  
  @FindBy(css = ".sa-close")
  private WebElement close;
  
  public BusinessesCategoriesPanel(WebDriver driver) {
    super(driver, ROOT_LOCATOR);
    root = driver.findElement(ROOT_LOCATOR);
  }
  
  @Override
  public void isLoaded() {
  }
  
  private String getToggleSwitchCheckedName() {
    
    if (toggleSwitchElement.getAttribute("class").contains("sa-toggle-switch-checked")) {
      return "NAICS";
    }
    return "SIC";
  }
  
  /**
   * Click on a business category.
   * 
   * @return businees
   */
  @Step("Click on a business category")
  public String clickRandomBusiness() {
	  
	JavascriptExecutor js = (JavascriptExecutor) driver; 
    
    logger.debug("Click on a business category until no sub option left");
    String organization = getToggleSwitchCheckedName();
    boolean contains = true;
    
    int randomCategory = new Random().nextInt(allBusinessesElementsList.size());
    String code = allBusinessesElementsList.get(randomCategory)
        .findElement(By.cssSelector(".sa-business-code-browser-td-code")).getText();
    
    while (contains) {
      if (blackList.contains(code)) {
        randomCategory = new Random().nextInt(allBusinessesElementsList.size());
        code = allBusinessesElementsList.get(randomCategory)
            .findElement(By.cssSelector(".sa-business-code-browser-td-code")).getText();
        contains = true;
      } else {
        contains = false;
      }
    }
    
    String name = allBusinessesElementsList.get(randomCategory)
        .findElement(By.cssSelector(".sa-business-code-browser-td-name")).getText(); 
    
    
    js.executeScript("arguments[0].scrollIntoView(true);", allBusinessesElementsList.get(randomCategory));
    js.executeScript("arguments[0].click();", allBusinessesElementsList.get(randomCategory));
    
    //waitForElementToBeClickable(allBusinessesElementsList.get(randomCategory), "Random business element is not clickable").click();
    
    if (!isPresent(ROOT_LOCATOR)) {
      return organization + " = " + code + ", " + name;
    } else {
      logger.warn("off");
      String businees = new BusinessesCategoriesPanel(driver).clickRandomBusiness();
      logger.warn("on");
      logger.trace("business category: " + businees);
      return businees;
    }
  }
  
  /**
   * Click on the close button.
   */
  public void clickCloseBusinessesCategoriesPanel() {
    logger.debug("Click on the close button");
    close.click();
    waitForElementToDisappear(root, "The businesses categories panel should disappear");
  }
  
  public boolean isDisplayed() {
    return isPresent(root);
  }
}
