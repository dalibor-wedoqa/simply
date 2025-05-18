package net.simplyanalytics.pageobjects.sections.view.histogram;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import net.simplyanalytics.pageobjects.base.BasePage;
import net.simplyanalytics.pageobjects.panels.color.ColorSelectionPanel;
import io.qameta.allure.Step;

public class EditLegendPanel extends BasePage {
  
  protected static final By ROOT_LOCATOR = By.cssSelector(".sa-chart-legend");
  protected WebElement viewRoot;
  protected WebElement root;
  
  @FindBy(css = ".sa-chart-legend-content .sa-chart-legend-edit-labelled-item:nth-child(1) input")
  private List<WebElement> titleElement;
  
  @FindBy(css = ".sa-chart-legend-content .sa-chart-legend-edit-labelled-item:nth-child(3) .sa-button-arrow span")
  private WebElement xAxis;
  
  @FindBy(css = ".sa-chart-legend-content .sa-chart-legend-edit-labelled-item:nth-child(4) input")
  private WebElement barWidth;
  
  @FindBy(css = ".sa-color-button")
  private WebElement colorBarCombo;
  
  @FindBy(css = ".sa-color-button .sa-color-button-swatch")
  private WebElement actualColor;
  
  @FindBy(xpath = ".//a[contains(@class, 'sa-chart-legend-mode-button')][//span[contains(normalize-space(.), 'Done')]]")
  private WebElement doneButton;
  
  /**
   * EditLegendPanel constructor.
   * 
   * @param driver      WebDriver
   * @param rootElement Legend Panel
   */
  public EditLegendPanel(WebDriver driver, WebElement rootElement) {
    super(driver, rootElement.findElement(ROOT_LOCATOR));
    viewRoot = rootElement;
    root = rootElement.findElement(ROOT_LOCATOR);
  }
  
  @Override
  public void isLoaded() {
	  waitForElementToAppear(doneButton, "Done Button is not present");
  }
  
  public int getLegendWidth() {
    return root.getSize().getWidth();
  }
  
  /**
   * Clicking on Done button.
   * 
   * @return Legend Panel
   */
  @Step("Click on the Done button")
  public LegendPanel clickDone() {
    logger.debug("Click on the Done button");
    waitForElementToBeClickable(doneButton, "Done button is not clickable").click();
    sleep(1000);
    waitForElementToAppearByLocator(By.cssSelector(".sa-chart-legend:not(.sa-legend-edit-mode)"),"Legend panel was not edited succesfuly");
	 //TODO stabilize this method
    waitForLoadingToDisappear();
    return new LegendPanel(driver, viewRoot);
  }
  
  @Step("Click on color bar combbox")
  public ColorSelectionPanel clickColorBarCmb() {
	colorBarCombo.click();
    return new ColorSelectionPanel(driver);
  }
  
  public String getActualBarColor() {
	 return colorBarCombo.findElement(By.cssSelector(".sa-color-button .sa-color-button-swatch"))
	        .getCssValue("background-color");
  }
 
}
