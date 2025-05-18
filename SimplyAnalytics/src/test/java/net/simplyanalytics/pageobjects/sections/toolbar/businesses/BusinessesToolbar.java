package net.simplyanalytics.pageobjects.sections.toolbar.businesses;

import java.util.List;
import java.util.Random;

import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.main.export.ExportWindow;
import net.simplyanalytics.pageobjects.pages.main.views.BusinessesPage;
import net.simplyanalytics.pageobjects.sections.toolbar.BaseViewToolbar;
import net.simplyanalytics.pageobjects.sections.toolbar.DropdownToolbar;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;

public class BusinessesToolbar extends BaseViewToolbar {
  
  @FindBy(xpath = ".//div[contains(text(),'for')]/following-sibling::a[1]")
  private WebElement resultsForComboElement;
  
  @FindBy(xpath = "/html/body/div[6]/div[3]/a[2]")
  private WebElement activeLocation;
  
  @FindBy(xpath = ".//div[contains(text(),'for') and contains(@class,'sa-pvt-text')]")
  private WebElement numberOfResults;
  
  public BusinessesToolbar(WebDriver driver, ViewType nextViewType) {
    super(driver, ViewType.BUSINESSES, nextViewType);
  }
  
  @Override
  public void isLoaded() {
    super.isLoaded();
    waitForElementToAppear(resultsForComboElement, "Results for Combo is not loaded");
  }
  
  /**
   * Click on the active business to open business list menu.
   * 
   * @return ResultsForCombo
   */
  @Step("Click on the active business to open business list menu")
  private ResultsForCombo openBussinesListMenu() {
    logger.debug("Click on tha active business to open business list menu");
    resultsForComboElement.click();
    waitForElementToStop(driver.findElement(By.cssSelector(".sa-combo-button-menu")));
    return new ResultsForCombo(driver);
  }
  
  public BusinessesPage changeToRandomBusiness() {
    Random rand = new Random();
    ResultsForCombo resultsForCombo = openBussinesListMenu();
    List<String> businesses = resultsForCombo.getBusinesses();
    int n = rand.nextInt(businesses.size());
    logger.debug("Select business with name: " + businesses.get(n));
    return resultsForCombo.clickOnABusiness(businesses.get(n));
  }
  
  @Step("Select business with name {0}")
  public BusinessesPage changeBusiness(String businessName) {
    logger.debug("Select business with name: " + businessName);
    return openBussinesListMenu().clickOnABusiness(businessName);
  }
  
  // TODO
  private DropdownToolbar openLocationMenu() {
    logger.debug("Open location list");
    activeLocation.click();
    return new DropdownToolbar(driver);
  }
  
  // TODO
  public void changeLocation(Location locationName) {
    logger.debug("Select business with name: " + locationName);
    openLocationMenu().clickonLocation(locationName);
  }
  
  @Override
  public ExportWindow clickExport() {
    int maxIteration = 10;
    int numberOfIterarion = 0;
    while(getNumberOfResults()==0) {
      changeToRandomBusiness();
      numberOfIterarion++;
      if(numberOfIterarion > maxIteration) {
        throw new Error("Number of results is always '0' !");
      }
    }
    return (ExportWindow) clickExportButton();
  }
  

  public ExportWindow clickExportForEmptyTable() {
    return (ExportWindow) clickExportButton();
  }
  
  public Location getActiveLocation() {
    return Location.getByName(activeLocation.getText());
  }
  
  public String getActiveBusiness() {
    return resultsForComboElement.getText();
  }
  
  @Override
  public BusinessViewActionMenu clickViewActions() {
    return (BusinessViewActionMenu) super.clickViewActions();
  }
  
  public boolean isOutOfLimit() {
    while (numberOfResults.getText().split(" ")[0].equals("Loading")) {
    }
    if (Integer.parseInt(numberOfResults.getText().split(" ")[0]) > 2500) {
      return true;
    }
    return false;
  }
  
  public int getNumberOfResults() {
    return Integer.parseInt(numberOfResults.getText().split(" ")[0]);
  }
}
