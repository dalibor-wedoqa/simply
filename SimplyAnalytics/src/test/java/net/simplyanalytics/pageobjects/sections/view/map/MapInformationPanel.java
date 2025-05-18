package net.simplyanalytics.pageobjects.sections.view.map;

import java.util.List;
import java.util.Random;

import net.simplyanalytics.pageobjects.base.BasePage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.windows.AliasLocationWindow;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MapInformationPanel extends BasePage {

  private static final By ROOT_LOCATOR = By.cssSelector(".sa-location-info");
  private static final By root_alternate = By.cssSelector(".sa-location-info-alternate-locations-submenu");

  @FindBy(xpath = ".//div[contains(@class,'sa-location-info-location-name')]//div")
  private List<WebElement> locationName;
  
  @FindBy(css = ".sa-location-info-location-data-value")
  private WebElement locationValue;

  @FindBy(css = ".sa-location-info .x-menu-item-text")
  private List<WebElement> buttons;
  
  @FindBy(css = "button[id*='close-button']")
  private WebElement closeButton;
  
  /**
   * 
   * @param driver      WebDriver
   * @param rootElement Information Panel
   */
  public MapInformationPanel(WebDriver driver) {
    super(driver, ROOT_LOCATOR);
  }
  
  @Override
  protected void isLoaded() {
    waitForLoadingToDisappear();
    waitForElementToAppear(locationName.get(1), "Location name is missing");
    waitForElementToAppear(locationValue, "Location value is missing");
    waitForElementToAppear(buttons.get(0), "Button is missing");    
  }
  
  public MapPage clickOnCloseButton() {
	    logger.debug("Click on Close button");
	    waitForElementToBeClickable(closeButton, "Close Button is not clickable").click();
	    return new MapPage(driver);
	  } 
  
  public String getLocationName() {
    return locationName.get(1).getText();
  }
  
  public MapPage clickAddLocationToProject() {
    logger.debug("Click on Add Location To Project button");
    waitForElementToBeClickable(buttons.get(0), "Button is not clickable").click();
    return new MapPage(driver);
  }  
  
  public void clickAddLocationToFavotires() {
    logger.debug("Click on Add To Favorites button");
    waitForElementToBeClickable(buttons.get(1), "Button is not clickable").click();
    waitForElementToDisappear(ROOT_LOCATOR);
  }
  
  public AliasLocationWindow clickAddAliasLocationName() {
    logger.debug("Click on Add Alias Location Name button");
    waitForElementToBeClickable(buttons.get(2), "Button is not clickable").click();
    return new AliasLocationWindow(driver);
  } 

  public AlternateLocationPanel clickAlternateLocations() {
    logger.debug("Click on Alternate Locations button");
    waitForElementToBeClickable(buttons.get(3), "Button is not clickable").click();
    return new AlternateLocationPanel(driver);
  } 
  
  public class AlternateLocationPanel extends BasePage {
    
    @FindBy(xpath = ".//span[contains(@class,'sa-location-info-alternate-location-geographic-unit')]/..")
    private List<WebElement> geographicUnits;
    
    /**
     * 
     * @param driver      WebDriver
     * @param rootElement Information Panel
     */
    public AlternateLocationPanel(WebDriver driver) {
      super(driver, root_alternate);
    }
    
    @Override
    protected void isLoaded() {  
    }
    
    public MapInformationPanel clickRandomGeographicUnit() {
      logger.debug("Click on random geographic unit");  
      int index = new Random().nextInt(geographicUnits.size() - 1);
      while(!geographicUnits.get(index).isDisplayed()) {
        index = new Random().nextInt(geographicUnits.size() - 1);
      }
      geographicUnits.get(index).click();
      return new MapInformationPanel(driver);      
    }
    
  }
}
