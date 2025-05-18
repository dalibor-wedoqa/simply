package net.simplyanalytics.pageobjects.sections.view.map;

import net.simplyanalytics.pageobjects.base.BasePage;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MinimapPanel extends BasePage {

  protected WebElement root;

  @FindBy(css = ".leaflet-zoom-hide")
  private WebElement map;

  @FindBy(css = ".sa-vector-map-collapsible-inset-map-collapse-button.x-border-box.x-component.x-component-default")
  private WebElement showHideButton;


  //NB my code
  @FindBy(xpath = "//*[@class='sa-simple-container sa-vector-map-collapsible-inset-map sa-simple-container-default x-border-box']")
  private WebElement getMinimapShownWithButton;

  @FindBy(xpath = "//*[@class='sa-simple-container sa-vector-map-collapsible-inset-map sa-simple-container-default x-border-box sa-vector-map-collapsible-inset-map-collapsed']")
  private WebElement getMinimapNotShownWithButton;

  @FindBy(xpath = "//body/div[@class='sa-project-view sa-project-view-default x-border-box']//button")
  private WebElement getMiniMapButton;


  protected MinimapPanel(WebDriver driver, WebElement root) {
    super(driver, root);
    this.root = root;
  }

  @Override
  protected void isLoaded() {

  }

  /**
   * Checking if mini map is displayed.
   * 
   * @return
   */
  public boolean isShowButtonPresent() {
    String title = showHideButton.getAttribute("class");
    switch (title) {
      case "[class=\"sa-simple-container sa-vector-map-collapsible-inset-map sa-simple-container-default x-border-box\"]":
        return false;
      case "[class=\"sa-simple-container sa-vector-map-collapsible-inset-map sa-simple-container-default x-border-box sa-vector-map-collapsible-inset-map-collapsed\"]":
        return true;
      default:
        throw new Error("The title is not one of the expected: " + title);
    }
  }

  /*
  //N.B. Added code with if
  */
  /*
//  public boolean isMinimapButtonPresent() {
//    try {
//      WebDriverWait wait = new WebDriverWait(driver, 10); // Wait up to 10 seconds
//      wait.until(ExpectedConditions.visibilityOf(getMinimapShownOrNotWithButton));
//      return true; // Minimap is visible
//    } catch (Exception e) {
//      return false; // Minimap is not visible or not found
//    }
//  }
//
//  public void clickShowHideMinimapButton() {
//    getShowHideButton.click();
//  }

  */
  //Here ends your code
  //New code
  public boolean isMinimapPresent() {
    if (getMinimapShownWithButton.isDisplayed()) {
      return true;
    } else {
      return false;
    }
  }

  public boolean isCollapsedMinimapPresent() {
    if (getMinimapNotShownWithButton.isDisplayed()) {
      return true;
    } else {
      return false;
    }
  }

  public void clickOnTheMiniMapButton() {
      getMiniMapButton.click();
  }
//End of the new code



  /**
   * Click on the show/hide minimap button.
   */
  public void clickShowHideButton() {
    if (isShowButtonPresent()) {
      allureStep("Click on the show minimap button");
      showHideButton.click();
      waitForElementToAppear(map, "The mini map is still missing");
    } else {
      allureStep("Click on the hide minimap button");
      showHideButton.click();
      waitForElementToDisappear(map);
    }
  }

  public boolean isMapPresent() {
    if (root.getSize().width == 144 && root.getSize().height == 144) {
      return true;
    } else {
      return false;
    }
  }

}
