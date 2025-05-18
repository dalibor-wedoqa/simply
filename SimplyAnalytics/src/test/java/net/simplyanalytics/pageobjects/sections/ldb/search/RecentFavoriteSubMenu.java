package net.simplyanalytics.pageobjects.sections.ldb.search;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import net.simplyanalytics.pageobjects.base.BasePage;
import net.simplyanalytics.pageobjects.sections.ldb.locations.CreateOrEditCombinationLocation;
import net.simplyanalytics.pageobjects.sections.ldb.locations.CreateRadiusLocation;
import net.simplyanalytics.pageobjects.sections.ldb.locations.DeleteAlert;
import net.simplyanalytics.pageobjects.windows.AliasLocationWindow;
import net.simplyanalytics.pageobjects.windows.MetadataWindow;
import io.qameta.allure.Step;

public class RecentFavoriteSubMenu extends BasePage {
    
	protected static final By SUB_MENU_ROOT = By
		    .xpath(".//div[contains(@class, 'x-panel')][not(contains(@class, 'body'))]"
		        + "[not(contains(@style, 'visibility: hidden;'))]");
	
	protected static final By COMBINATION_LOCATION_MENU_ROOT = By
		      .cssSelector(".sa-menu-compact:not([class*='sa-arrow'])");
	  
    @FindBy(xpath = "descendant::span[text()='Add to Favorites']")
    private WebElement addToFavorites;
    
    @FindBy(xpath = "descendant::span[text()='Remove from Favorites']")
    private WebElement removeFromFavorites;
    
    @FindBy(xpath = "descendant::span[text()='Remove from Recent List']")
    private WebElement removeFromRecentList;
    
    @FindBy(xpath = "descendant::span[text()='Add Alias Location Name']")
    private WebElement addAliasLocationName;
    
    @FindBy(xpath = "descendant::span[text()='Edit Alias Location Name']")
    private WebElement editAliasLocationName;
    
    @FindBy(xpath = "descendant::span[text()='View Metadata']")
    private WebElement viewMetadata;
    
    @FindBy(xpath = "descendant::span[text()='Add to Combination Location']")
    private WebElement addToCombinationLocation;
    
    @FindBy(xpath = "descendant::span[text()='Edit Combination Location']")
    private WebElement editCombinationLocation;
    
    @FindBy(xpath = "descendant::span[text()='Create Radius Location']")
    private WebElement createRadiusLocation;
    
    @FindBy(xpath = "descendant::span[text()='Delete']")
    private WebElement delete;
    
    @SuppressWarnings("ucd")
    protected WebElement root;
    
    public RecentFavoriteSubMenu(WebDriver driver) {
      super(driver, SUB_MENU_ROOT);
      this.root = driver.findElement(SUB_MENU_ROOT);
    }
    
    @Override
    protected void isLoaded() {
      // no element that should appear in every case
    }
    
    @Step("Click on the Add to Favorites button")
    public void clickAddToFavorites() {
      logger.debug("Click to the Add to Favorites button");
      List<WebElement> contents = root.findElements(By.cssSelector(".x-menu-item-link"));
      if (contents.get(0).getText().equals("Add to Favorites")) {
        addToFavorites.click();
      }
      /*
       * try { addToFavorites.click(); } catch (Exception e) {
       * logger.debug("Variable is already added to favorites"); }
       */
    }
    
    public boolean isInFavorites() {
      return isPresent(removeFromFavorites);
    }
    
    @Step("Click on the Remove from Favorites button")
    public void clickRemoveFromFavorites() {
      logger.debug("Click on the Remove from Favorites button");
      removeFromFavorites.click();
    }
    
    @Step("Click on the Remove from Recent List button")
    public void clickRemoveFromRecentList() {
      logger.debug("Click on the Remove from Recent List button");
      removeFromRecentList.click();
    }
    
    /**
     * Click on the Add Alias Location Name button.
     * 
     * @return AliasLocationWindow
     */
    @Step("Click on the Add Alias Location Name button")
    public AliasLocationWindow clickAddAliasLocation() {
      logger.debug("Click on the Add Alias Location Name button");
      addAliasLocationName.click();
      return new AliasLocationWindow(driver);
    }
    
    /**
     * Click on the Edit Alias Location Name button.
     * 
     * @return AliasLocationWindow
     */
    @Step("Click on the Edit Alias Location Name button")
    public AliasLocationWindow clickEditAliasLocation() {
      logger.debug("Click on the Edit Alias Location Name button");
      editAliasLocationName.click();
      return new AliasLocationWindow(driver);
    }
    
    /**
     * Click on the Add to Combination Location button.
     * 
     * @return CombinationLocationSubMenu
     */
    @Step("Click on the Add to Combination Location button")
    public CombinationLocationSubMenu clickAddToCombinationLocation() {
      logger.debug("Click on the Add to Combination Location button");
      addToCombinationLocation.click();
      return new CombinationLocationSubMenu(driver);
    }
    
    public boolean isAddToCombinationLocationPresent() {
      return isPresent(addToCombinationLocation);
    }
    
    /**
     * Click on the Edit Combination Location button.
     * 
     * @return CreateOrEditCombinationLocation
     */
    @Step("Click on the Edit Combination Location button")
    public CreateOrEditCombinationLocation clickEditCombinationLocation() {
      logger.debug("Click on the Edit Combination Location button");
      editCombinationLocation.click();
      return new CreateOrEditCombinationLocation(driver);
    }
    
    /**
     * Click on the Create Radius Location button.
     * 
     * @return CreateRadiusLocation
     */
    @Step("Click on the Create Radius Location button")
    public CreateRadiusLocation clickCreateRadiusLocation() {
      logger.debug("Click on the Create Radius Location button");
      createRadiusLocation.click();
      return new CreateRadiusLocation(driver);
    }
    
    public boolean isCreateRadiusLocationPresent() {
      return isPresent(createRadiusLocation);
    }
    
    /**
     * Click on the View Metadata button.
     * 
     * @return MetadataWindow
     */
    @Step("Click on the View Metadata button")
    public MetadataWindow clickViewMetadata() {
      logger.debug("Click on the View Metadata button");
      viewMetadata.click();
      return new MetadataWindow(driver);
    }
    
    @Step("Click on the Delete button")
    private void clickDeleteVoid() {
      logger.debug("Click on the Delete button");
      delete.click();
    }
    
    public DeleteAlert clickDeleteWithAlert() {
      clickDeleteVoid();
      waitForElementToAppearByLocator(By.cssSelector(".sa-prompt-window-default"), "");
      return new DeleteAlert(driver);
    }
    
    public RecentFavoriteMenu clickDeleteWithoutAlert() {
      clickDeleteVoid();
      return new RecentFavoriteMenu(driver);
    }
    
    public boolean isDeletePresent() {
      return isPresent(delete);
    }
    
    public class CombinationLocationSubMenu extends BasePage {
        
        protected WebElement root;
        
        @FindBy(xpath = ".//a[span[.='Create New Combination']]")
        private WebElement createNewCombination;
        
        @FindBy(xpath = ".//div[preceding-sibling::div[contains(@class, 'x-menu-item-separator')]]")
        private List<WebElement> combinationLocations;
        
        public CombinationLocationSubMenu(WebDriver driver) {
          super(driver, COMBINATION_LOCATION_MENU_ROOT);
          root = driver.findElement(COMBINATION_LOCATION_MENU_ROOT);
        }
        
        @Override
        protected void isLoaded() {
          waitForElementToAppear(createNewCombination, "The create combination location is missing");
        }
        
        /**
         * Click on the Create New Combination button.
         * 
         * @return CreateOrEditCombinationLocation
         */
        @Step("Click on the Create New Combination button")
        public CreateOrEditCombinationLocation clickCreateNewCombinationButton() {
          logger.debug("Click on the Create New Combination button");
          createNewCombination.click();
          waitForElementToDisappear(root);
          return new CreateOrEditCombinationLocation(driver);
        }
        
        public List<String> getCustomLocations() {
          return combinationLocations.stream().map(element -> element.getText()).collect(Collectors.toList());
        }
        
        /**
         * Click on custom location.
         * 
         * @param customLocationName custom location name
         */
        @Step("Click on custom location: {0}")
        public void clickOnCustomLocation(String customLocationName) {
          logger.debug("Click on custom location: " + customLocationName);
          combinationLocations.stream().filter(element -> element.getText().equals(customLocationName)).findFirst().get()
              .click();
        }
        
      }
  }