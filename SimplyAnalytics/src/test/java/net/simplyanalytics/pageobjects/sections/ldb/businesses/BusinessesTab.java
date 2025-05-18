package net.simplyanalytics.pageobjects.sections.ldb.businesses;

import net.simplyanalytics.pageobjects.base.BasePage;
import net.simplyanalytics.pageobjects.sections.ldb.TabWithRecentMenuInterface;
import net.simplyanalytics.pageobjects.sections.ldb.search.RecentFavoriteMenu;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;

public class BusinessesTab extends BasePage implements TabWithRecentMenuInterface {
  
  public static final By ROOT_LOCATOR = By.cssSelector("[id*='businesses-sidebar']");
  @SuppressWarnings("ucd")
  protected WebElement root;
  
  @FindBy(xpath = ".//div[text()='Browse business categories']")
  private WebElement browseBusinessCategories;
  
  @FindBy(xpath = ".//div[text()='Use advanced search']")
  private WebElement businessUseAdvanceSearch;
  
  @FindBy(css = ".sa-sidebar-list")
  private WebElement businesseTabContainer;
  
  @FindBy(css = "button[title='Recent Business Searches']")
  private WebElement recentSearches;
  
  @FindBy(css = ".sa-text-field-input")
  private WebElement searchField;
  
  public BusinessesTab(WebDriver driver) {
    super(driver, ROOT_LOCATOR);
    root = driver.findElement(ROOT_LOCATOR);
  }
  
  @Override
  public void isLoaded() {
    waitForElementToAppear(businesseTabContainer, "Businesses tab container is not loaded");
  }
  
  @Override
  public boolean isRecentPresent() {
    return recentSearches.isDisplayed();
  }
  
  @Override
  @Step("Click on the Recent Business Searches button")
  public RecentFavoriteMenu clickRecent() {
    logger.debug("Click on the Recent Business Searches button");
    waitForElementToAppear(recentSearches, "Recent Business Searches button is not loaded").click();
    return new RecentFavoriteMenu(driver);
  }
  
  /**
   * Enter business in search.
   * 
   * @param business business
   */
  @Step("Enter business in search")
  public void enterBusinessSearch(String business) {
    logger.debug("Enter business {0} in search");
    searchField.click();
    searchField.clear();
    searchField.sendKeys(business + Keys.ENTER);
  }
  
  /**
   * Click on the 'Use advanced search' button.
   * 
   * @return AdvancedBusinessSearchWindow
   */
  @Step("Click on the 'Use advanced search' button")
  public AdvancedBusinessSearchWindow clickUseAdvancedSearch() {
    logger.debug("Click on the 'Use advanced search' button");
    waitForElementToBeClickable(businessUseAdvanceSearch, "Use Advanced Search button is not clickable at a point")
        .click();
    return new AdvancedBusinessSearchWindow(driver);
  }
  
  /**
   * Click on the 'Browse business categories'.
   * 
   * @return BusinessesCategoriesPanel
   */
  @Step("Click on the 'Browse business categories'")
  public BusinessesCategoriesPanel clickBusinessCategories() {
    logger.debug("Click on the 'Browse business categories'");
    browseBusinessCategories.click();
    return new BusinessesCategoriesPanel(driver);
  }
  
  @Step("Select a random business")
  public String addRandomBusinesses() {
    // logger.debug("Select a random business");
    return clickBusinessCategories().clickRandomBusiness();
  }
}
