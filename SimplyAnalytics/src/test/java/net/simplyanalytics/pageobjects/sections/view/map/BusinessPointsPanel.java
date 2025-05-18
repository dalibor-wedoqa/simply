package net.simplyanalytics.pageobjects.sections.view.map;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import net.simplyanalytics.pageobjects.base.BasePage;
import io.qameta.allure.Step;

public class BusinessPointsPanel extends BasePage {
  
  private static final By ROOT_LOCATOR = By.cssSelector(".sa-map-business-list-window");
  
  @FindBy(css = ".sa-businesses-list-header-business-count")
  private WebElement businessesNumber;
  
  @FindBy(css = ".sa-map-business-list-info-legal-name")
  private WebElement businessesName;
  
  @FindBy(css = ".sa-map-business-list-info-collapse")
  private WebElement backLink;
  
  @FindBy(css = ".sa-paging-toolbar-next-button")
  private WebElement nextButton;
  
  @FindBy(css = ".sa-paging-toolbar-previous-button")
  private WebElement previousButton;
  
  @FindBy(css = ".sa-paging-toolbar-last-button")
  private WebElement lastButton;
  
  @FindBy(css = ".sa-paging-toolbar-first-button")
  private WebElement firstButton;
  
  @FindBy(css = ".sa-map-business-list-row")
  private List<WebElement> businessRow;
  
  public BusinessPointsPanel(WebDriver driver) {
    super(driver, ROOT_LOCATOR);
  }
  
  @Override
  protected void isLoaded() {
    waitForElementToAppear(businessesNumber, "Number of businessesis not present");
    waitForElementToAppear(businessesName, "Businessesis name is not present");
    waitForElementToAppear(nextButton, "Next button is not present");
    waitForElementToAppear(previousButton, "Previous button is not present");
    waitForElementToAppear(firstButton, "First button is not present");
    waitForElementToAppear(lastButton, "Last button is not present");
  }
  
  public int getBusinessesCount() {
    return Integer.parseInt(businessesNumber.getText().trim().split(" ")[0]);
  }
  
  public String getBusinessName() {
    if(isPresent(driver.findElement(By.cssSelector(".sa-map-business-list-other-name")))) {
      return businessesName.getText().trim() + " " + driver.findElement(By.cssSelector(".sa-map-business-list-other-name")).getText().trim();
    }
    return businessesName.getText().trim();
  }
  
  @Step("Click on the Back button")
  public void clickBack() {
    waitForElementToBeClickable(backLink, "Back link is not clickable").click();
  }
  
  @Step("Click on the Next button")
  public void clickNextButton() {
    nextButton.click();
  }
  
  public boolean isNextButtonEnabled() {
    return !nextButton.getAttribute("class").contains("x-item-disabled");
  }
  
  @Step("Click on the Previous button")
  public void clickPreviousButton() {
    previousButton.click();
  }
  
  public boolean isPreviousButtonEnabled() {
    return !previousButton.getAttribute("class").contains("x-item-disabled");
  }
  
  @Step("Click on the Last button")
  public void clickLastButton() {
    lastButton.click();
  }
  
  public boolean isLastButtonEnabled() {
    return !lastButton.getAttribute("class").contains("x-item-disabled");
  }
  
  @Step("Click on the First button")
  public void clickFirstButton() {
    firstButton.click();
  }
  
  public boolean isFirstButtonEnabled() {
    return !firstButton.getAttribute("class").contains("x-item-disabled");
  }
  
  public List<String> getAllBusinessesRow() {
    List<String> businesses = new ArrayList<String>();
    businesses.addAll(businessRow.stream().map(businessElement -> 
    businessElement.findElement(By.cssSelector(".sa-map-business-list-name-td")).getText().trim())
      .collect(Collectors.toList()));
    while(isNextButtonEnabled()) {
      if(isNextButtonEnabled())
        clickNextButton();
      businesses.addAll(businessRow.stream().map(businessElement -> 
        businessElement.findElement(By.cssSelector(".sa-map-business-list-name-td")).getText().trim())
          .collect(Collectors.toList()));
    }
    return businesses;
  }
}
