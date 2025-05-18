package net.simplyanalytics.pageobjects.sections.ldb;

import net.simplyanalytics.enums.DataBrowseType;
import net.simplyanalytics.enums.Ldb;
import net.simplyanalytics.pageobjects.base.BasePage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.sections.ldb.businesses.BusinessesTab;
import net.simplyanalytics.pageobjects.sections.ldb.data.DataTab;
import net.simplyanalytics.pageobjects.sections.ldb.locations.BaseLocationsTab;
import net.simplyanalytics.pageobjects.sections.ldb.locations.LocationsTab;
import net.simplyanalytics.pageobjects.sections.ldb.locations.ScarboroughLocationsTab;
import net.simplyanalytics.pageobjects.sections.viewchooser.ViewChooserSection;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;

public class LdbSection extends BasePage {
  
  protected static final By ROOT = By.cssSelector(".sa-sidebar");
  
  private final WebElement rootElement;
  
  @FindBy(css = ".sa-sidebar-tabs > *:first-child + * >  .sa-sidebar-tab-btn")
  private WebElement dataElement;
  
  @FindBy(css = ".sa-sidebar-tabs > *:first-child >  .sa-sidebar-tab-btn")
  private WebElement locationElement;
  
  @FindBy(css = ".sa-sidebar-tabs > *:first-child + *  + * > .sa-sidebar-tab-btn")
  private WebElement businessesElement;
  
  @FindBy(css = ".sa-sidebar-mode-combo-button")
  private WebElement sidebarComboButton;
    
  public LdbSection(WebDriver driver) {
    super(driver, ROOT);
    rootElement = driver.findElement(ROOT);
  }
  
  @Override
  public void isLoaded() {
    
  }
  
  /**
   * Getting active ldb.
   * 
   * @return active ldb
   */
  public Ldb getActiveLdb() {
    WebElement activeLdbElement = rootElement
        .findElement(By.cssSelector(".sa-sidebar-tab-btn-selected"));
    String ldb = activeLdbElement.findElement(By.cssSelector(".sa-sidebar-tab-label")).getText();
    switch (ldb) {
      case "Locations":
        return Ldb.LOCATION;
      case "Data":
        return Ldb.DATA;
      case "Businesses":
        return Ldb.BUSINESS;
      default:
        throw new AssertionError("There is not LDB enum with name: " + ldb);
    }
  }
  
  private boolean isTabEnabled(WebElement tabElement) {
    return tabElement.isEnabled();
  }
  
  /**
   * Getting Location tab.
   * 
   * @return Location tab
   */
  public BaseLocationsTab getLocationsTab() {
    String activeView = new ViewChooserSection(driver).getActiveViewName();
    if (!getActiveLdb().equals(Ldb.LOCATION) ) {
      return clickLocations();
    } else {
    if(activeView.contains("Scarborough")) {
      clickLocationsVoid();
      return new ScarboroughLocationsTab(driver);
    }
    else
      return new LocationsTab(driver);
    }
  }
  
  public boolean isLocationTabEnabled() {
    return isTabEnabled(locationElement);
  }
  
  /**
   * Getting Data tab.
   * 
   * @return Data tab
   */
  public DataTab getDataTab() {
    if (!getActiveLdb().equals(Ldb.DATA)) {
      return clickData();
    } else {
      return new DataTab(driver);
    }
  }
  
  public boolean isDataTabEnabled() {
    return isTabEnabled(dataElement);
  }
  
  /**
   * Getting Businesses tab.
   * 
   * @return Businesses tab
   */
  public BusinessesTab getBusinessTab() {
    if (!getActiveLdb().equals(Ldb.BUSINESS)) {
      return clickBusinesses();
    } else {
      return new BusinessesTab(driver);
    }
  }
  
  public boolean isBusinessesTabEnabled() {
    return isTabEnabled(businessesElement);
  }
  
  /**
   * Click on the Data button.
   * 
   * @return DataTab
   */
  @Step("Click on the Data button")
  public DataTab clickData() {
    logger.debug("Click on the Data button");
    dataElement.click();
    return new DataTab(driver, DataBrowseType.CATEGORY);
  }
  
  /**
   * Click on the Combo button.
   * 
   * @return Combo button dropdown
   */
  @Step("Click on the combo button")
  public DataComboButtonShowing clickCombuButton() {
    logger.debug("Click on the combo button");
    waitForElementToBeClickable(sidebarComboButton, "The User button is not clickable").click();
    return new DataComboButtonShowing(driver);
  }  
  
  /**
   * Click on the Location button.
   * 
   * @return LocationsTab
   */
  @Step("Click on the Location button")
  public LocationsTab clickLocations() {
    logger.debug("Click on the Location button");
    waitForElementToBeClickable(locationElement, "The locations tab is not clickable").click();
    return new LocationsTab(driver);
  }
  
  /**
   * Click on the Location button.
   * 
   * @return LocationsTab
   */
  @Step("Click on the Location button")
  public void clickLocationsVoid() {
    logger.debug("Click on the Location button");
    waitForElementToBeClickable(locationElement, "The locations tab is not clickable").click();
  }
  
  /**
   * Click on the Businesses button.
   * 
   * @return BusinessesTab
   */
  @Step("Click on the Businesses button")
  public BusinessesTab clickBusinesses() {
    logger.debug("Click on the Businesses button");
    businessesElement.click();
    return new BusinessesTab(driver);
  }
  
  @Step("Select a location")
  public MapPage clickLocationsEnterSearchAndChooseALocation(String searchLetters,
      String location) {
    return ((LocationsTab) getLocationsTab()).chooseLocation(searchLetters, location);
  }
  
  @Step("Select random locations ({1} locations)")
  public void clickLocationsEnterSearchAndChooseARandomNumberLocation(String searchLetters,
      int num) {
    ((LocationsTab) getLocationsTab()).chooseRandomNumberLocations(searchLetters, num);
  }
  
  /**
   * Hover on Locations tab.
   * 
   * @return String
   */
  @Step("Hover locations tab")
  public String getLocationsTooltipContent() {
    logger.debug("Hover Location tab");
    Actions action = new Actions(driver);
    action.moveToElement(locationElement).build().perform();
    WebElement tooltip = driver.findElement(By.cssSelector(".sa-tooltip-content"));
    return tooltip.getAttribute("innerHTML");
  }

  
}
