package net.simplyanalytics.pageobjects.windows;

import net.simplyanalytics.pageobjects.base.BasePage;
import io.qameta.allure.Step;

import org.openqa.selenium.*;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;

public class IntroToSimplyAnalyticsWindow extends BasePage {
  
  protected static final By rootElement = By.xpath(".//div[@id='player']");
  
  @SuppressWarnings("ucd")
  protected WebElement root;
  
  @FindBy(css = ".vp-progress")
  private WebElement loadedProgressBarElement;
  
  @FindBy(xpath = "//button[@data-play-button]")
  private WebElement playButton;

  @FindBy(xpath = "(//div[@role='slider'])[1]")
  private WebElement loadedProgressBarElementCheck;
  
  public IntroToSimplyAnalyticsWindow(WebDriver driver) {
    super(driver, rootElement);
    this.root = driver.findElement(rootElement);
  }
  
  @Override
  public void isLoaded() {
    mouseOverProgressBar();
    waitForElementToAppear(loadedProgressBarElement, "Progress bar is not loaded");
  }
  
  /**
   * Set mouse over progress bar.
   */
  private void mouseOverProgressBar() {
    logger.debug("Move to Progress Bar element");
    Actions builder = new Actions(driver);
    builder.moveToElement(loadedProgressBarElement).build().perform();
  }


  public void clickPlayButton() {
    // Locate the play button using the provided XPath
    WebElement playButton = driver.findElement(By.xpath("//button[@data-play-button]"));
    System.out.println("Located play button: " + playButton);

    // Method 1: Direct click
    try {
      playButton.click();
      System.out.println("Method 1: Direct click succeeded.");

    } catch (Exception e) {
      System.out.println("Method 1: Direct click failed: " + e.getMessage());
    }

    // Method 2: JavaScript simple click
    try {
      ((JavascriptExecutor) driver).executeScript("arguments[0].click();", playButton);
      System.out.println("Method 2: JavaScript click succeeded.");
      return;
    } catch (Exception e) {
      System.out.println("Method 2: JavaScript click failed: " + e.getMessage());
    }

    // Method 3: JavaScript dispatchEvent using MouseEvent
    try {
      String script = "var event = new MouseEvent('click', {view: window, bubbles: true, cancelable: true});" +
              "arguments[0].dispatchEvent(event);";
      ((JavascriptExecutor) driver).executeScript(script, playButton);
      System.out.println("Method 3: JavaScript dispatchEvent with MouseEvent succeeded.");
      return;
    } catch (Exception e) {
      System.out.println("Method 3: JavaScript dispatchEvent with MouseEvent failed: " + e.getMessage());
    }

    // Method 4: Using Actions class to move and click
    try {
      Actions actions = new Actions(driver);
      actions.moveToElement(playButton).click().perform();
      System.out.println("Method 4: Actions class click succeeded.");
      return;
    } catch (Exception e) {
      System.out.println("Method 4: Actions class click failed: " + e.getMessage());
    }

    // Method 5: Scroll into view then JavaScript click
    try {
      ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", playButton);
      ((JavascriptExecutor) driver).executeScript("arguments[0].click();", playButton);
      System.out.println("Method 5: Scroll into view then JS click succeeded.");
      return;
    } catch (Exception e) {
      System.out.println("Method 5: Scroll into view then JS click failed: " + e.getMessage());
    }

    // Method 6: Wait until element is clickable then click
    try {
      WebDriverWait wait = new WebDriverWait(driver, 10);
      WebElement clickableElement = wait.until(ExpectedConditions.elementToBeClickable(playButton));
      clickableElement.click();
      System.out.println("Method 6: Wait until clickable then direct click succeeded.");
      return;
    } catch (Exception e) {
      System.out.println("Method 6: Wait until clickable then direct click failed: " + e.getMessage());
    }

    // Method 7: Move to element with Actions then JavaScript click
    try {
      Actions actions = new Actions(driver);
      actions.moveToElement(playButton).perform();
      ((JavascriptExecutor) driver).executeScript("arguments[0].click();", playButton);
      System.out.println("Method 7: Actions move then JS click succeeded.");
      return;
    } catch (Exception e) {
      System.out.println("Method 7: Actions move then JS click failed: " + e.getMessage());
    }

    // Method 8: Click via coordinates using Actions (offset click)
    try {
      // Get the element's location and add a small offset
      Point location = playButton.getLocation();
      Actions actions = new Actions(driver);
      actions.moveByOffset(location.getX() + 5, location.getY() + 5).click().perform();
      System.out.println("Method 8: Click via coordinates using Actions succeeded.");
      return;
    } catch (Exception e) {
      System.out.println("Method 8: Click via coordinates using Actions failed: " + e.getMessage());
    }

    // Method 10: Retry loop with multiple attempts and a brief pause between them
    try {
      boolean clicked = false;
      for (int i = 0; i < 3; i++) {
        try {
          playButton.click();
          System.out.println("Method 10: Retry loop click succeeded on attempt " + (i + 1));
          clicked = true;
          break;
        } catch (Exception ex) {
          System.out.println("Method 10: Attempt " + (i + 1) + " failed: " + ex.getMessage());
          Thread.sleep(500);
        }
      }
      if (clicked) return;
    } catch (Exception e) {
      System.out.println("Method 10: Retry loop failed: " + e.getMessage());
    }

    System.out.println("All click methods failed.");
  }



  
  public String getPlayerProgressBarValue() {
    return loadedProgressBarElementCheck.getAttribute("aria-valuenow");
  }
  
  /**
   * Click on Close.
   */
  public void clickClose() {
    driver.switchTo().defaultContent();
    WebElement closeButton = waitForElementToAppearByLocator(
        By.cssSelector(".sa-video-window .sa-close"), 
        "Close button is not loaded");
    closeButton.click();
  }
}
