package net.simplyanalytics.pageobjects.sections.view.map;

import net.simplyanalytics.pageobjects.base.BasePage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

public class ZoomSliderPanel extends BasePage {

  private static final int sectionCount = 13;
  
  @FindBy(xpath = "//div[contains(@title,'Zoom in')]")
  private WebElement zoomInButton;
  
  @FindBy(xpath = "//div[contains(@title,'Zoom out')]")
  private WebElement zoomOutButton;
  
  @FindBy(xpath = "//div[contains(@class,'x-slider-thumb')]")
  private WebElement sliderThumb;
  
  protected WebElement root;
  
  protected ZoomSliderPanel(WebDriver driver, WebElement root) {
    super(driver, root);
    this.root = root;
  }
  
  @Override
  protected void isLoaded() {
    waitForElementToAppear(zoomInButton, "Zoom in button missing");
    waitForElementToAppear(zoomOutButton, "Zoom out button missing");
    waitForElementToAppear(sliderThumb, "Slider button button missing");
  }
  
  public void clickZoomInButton() {
    logger.debug("Click on the zoom in button in the zoom slider");   
    root.findElement(By.xpath("//div[contains(@class,'sa-vector-map-zoom-slider-zoom-in')]")).click();
    waitForLoadingToDisappear();
  }

  public void clickZoomOutButton() {
    logger.debug("Click on the zoom out button in the zoom slider");
    root.findElement(By.xpath("//div[contains(@class,'sa-vector-map-zoom-slider-zoom-out')]")).click();  
    waitForLoadingToDisappear();
  }

  public void moveZoomLevelWithSlider(int i) {
    int section = root.findElement(By.xpath("//div[contains(@class,'sa-vector-map-zoom-slider-wrap-outer')]")).getSize()
        .getHeight() / sectionCount;

    Actions action = new Actions(driver);
    action.dragAndDropBy(root.findElement(By.xpath("//div[contains(@class,'x-slider-thumb')]")), 0, i * section).build().perform();
    waitForLoadingToDisappear();
  }
  
}
