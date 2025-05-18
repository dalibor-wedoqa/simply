package net.simplyanalytics.pageobjects.sections.toolbar.ranking;

//import net.simplyanalytics.enums.DataVariable;
//import net.simplyanalytics.pageobjects.base.BasePage;
//import net.simplyanalytics.pageobjects.pages.main.views.RankingPage;
//
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.FindBy;
//
//import io.qameta.allure.Step;

//public class DataVariableDropdown extends BasePage {
//
//	  private static final By rootElement = By
//	      .cssSelector(".x-panel:not([style*='visibility: hidden'])");
//
//	  private WebElement root;
//
//	  @FindBy(xpath = ".//span[normalize-space(.)='Location Name']")
//	  private WebElement locationNameBtn;
//
//	  public DataVariableDropdown(WebDriver driver) {
//	    super(driver, rootElement);
//
//	    root = driver.findElement(rootElement);
//	  }
//
//	  @Override
//	  public void isLoaded() {
//	    waitForElementToAppear(locationNameBtn, "Location name button is not present");
//	  }
//
//	  /**
//	   * Click on the Sort by Location Name button.
//	   * @return RankingPage
//	   */
//	  @Step("Click on the Sort by Location Name button")
//	  public RankingPage clickOnLocationName() {
//	    logger.debug("Click on the Sort by Location Name button");
//	    locationNameBtn.click();
//	    return new RankingPage(driver);
//	  }
//
//	  /**
//	   * Click on the Sort by button.
//	   * @param dataVariable data variable
//	   * @return RankingPage
//	   */
//	  @Step("Click on the data variable {0} button")
//	  public RankingPage clickSortByDatavariable(DataVariable dataVariable) {
//	    logger.debug("Click on the data variable " + dataVariable.getFullName() + " button");
//
//	    waitForElementToBeClickable(
//	        root.findElement(By.xpath(".//span[normalize-space(.)='" + dataVariable.getFullName() + "']")),
//	        "Data variable " + dataVariable.getFullName() + " element is not present").click();
//	    return new RankingPage(driver);
//	  }
//}
