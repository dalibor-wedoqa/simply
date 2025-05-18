package net.simplyanalytics.pageobjects.windows;

import net.simplyanalytics.pageobjects.base.BasePage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;

public class MetadataWindow extends BasePage {

  protected static final By ROOT_ELEMENT = By.cssSelector(".sa-metadata-window");
  protected WebElement root;

  @FindBy(xpath = "//div[contains(@class,'sa-metadata-window')]//button[contains(@class,'sa-close')]")
  private WebElement closeIcon;
  
  @FindBy(xpath = ".//div[contains(@class,'sa-metadata-window-header')]/..")
  private WebElement header;

  public MetadataWindow(WebDriver driver) {
    super(driver, ROOT_ELEMENT);
    root = driver.findElement(ROOT_ELEMENT);
    waitForElementToStop(root);
  }

  @Override
  protected void isLoaded() {
    waitForElementToAppear(closeIcon, "Close icon should be present");
    waitForElementToAppear(header, "Header should be present");
  }

  /**
   * Click on the close button (X).
   */
  @Step("Click on the close button (X)")
  public void clickClose() {
    logger.debug("Click on the close button (X)");
    waitForElementToBeClickable(closeIcon, "Close button is not clickable").click();
    waitForElementToDisappear(root);
    sleep(500);
  }

  /**
   * Getting metadata value.
   * @param metadata metadata
   * @return metadata value
   */
  public String getMetadataValue(String metadata) {
    return root.findElement(By.xpath(".//td[text()=" + xpathSafe(metadata) + "]"))
        .findElement(By.xpath("parent::tr")).findElement(By.cssSelector("td:nth-child(2)"))
        .getText().trim();
  }
  
  /**
   * Click on data sheet with name.
   * @param sheetName sheet name
   * @return DataSheetWindow
   */
  @Step("Click on data sheet with name: {0}")
  public DataSheetWindow clickDataSheetByName(String sheetName) {
    logger.debug("Click on data sheet with name: " + sheetName);
    root.findElement(By.cssSelector("img[data-title='" + sheetName + "']")).click();
    return new DataSheetWindow(driver);
  }

  public boolean isDisplayed() {
    return isPresent(root);
  }
  
  public MetadataWindow moveWindow(int offsetXPoz, int offsetYPoz) {
    logger.debug("Move Metadata window");
    Actions actions = new Actions(driver);
    actions.dragAndDropBy(header, offsetXPoz, offsetYPoz).build().perform();
    waitForElementToStop(root);
    return new MetadataWindow(driver);
  }
}
