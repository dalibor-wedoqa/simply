package net.simplyanalytics.pageobjects.sections.view.map;

import net.simplyanalytics.pageobjects.base.BasePage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

public class ControlsPanel extends BasePage {
	
  //@FindBy(css = ".sa-map-controls-button[data-action='pan']") -old locator
  @FindBy(xpath = "//a[contains(@class,'sa-vector-map-tool-buttons-button')]")
  private WebElement panButton;
  
  //@FindBy(css = ".sa-map-controls-button[data-action='zoom']") -old locator
  @FindBy(xpath = "//a[contains(@title,'Zoom in with bounding box')]")
  private WebElement boundingBoxButton;

  //@FindBy(css = ".sa-map-controls-button[data-action='info']") -old locator
  @FindBy(xpath = "//a[contains(@title,'Information')]")
  private WebElement infoButton;
  
  private MapViewPanel mapViewPanel;
  
  protected ControlsPanel(WebDriver driver, WebElement root, MapViewPanel mapViewPanel) {
    super(driver, root);
    this.mapViewPanel = mapViewPanel;
  }
  
  @Override
  protected void isLoaded() {
    waitForElementToAppear(panButton, "The pan button is missing");
    waitForElementToAppear(boundingBoxButton, "The zoom button is missing");
    waitForElementToAppear(infoButton, "The info button is missing");
  }

  public void zoomWithBoundingBox(int x, int y) {
    boundingBoxButton.click();
    Actions action = new Actions(driver);

    action.moveToElement(mapViewPanel.mapContainerZoom, 20, 30 ).clickAndHold().moveByOffset(x, y).release().perform();
    waitForLoadingToDisappear();
  }

  public MapViewPanel clickInfoControlButton() {
    logger.debug("Select Information control button");
    infoButton.click();
    return new MapViewPanel(driver);
  }
  
}
