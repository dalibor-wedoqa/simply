package net.simplyanalytics.pageobjects.sections.header.importpackage;

import java.util.UUID;

import net.simplyanalytics.pageobjects.base.BasePage;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;

public class ManageRenameMenuWindow extends BasePage{
  
  protected static final By rootLocator = By.xpath("//div[contains(@id,'rename-menu') and .//div[contains(@id,'body')]]");
  protected WebElement root;
  
  @FindBy(xpath = "//div[contains(@class,'sa-rename-menu-input')]//input[contains(@class,'sa-text-field-input')]")
  private WebElement renameInput;
  
  public ManageRenameMenuWindow(WebDriver driver) {
    super(driver, rootLocator);
    this.root = driver.findElement(rootLocator);
  }
  
  public void isLoaded() {
    //waitForElementToBeClickable(renameInput, "Input is not enabled");
  }
  
  @Step("Input the new name")
  public void renameDataset() {
    String uuid = UUID.randomUUID().toString();
    logger.debug("Rename dataset");
    waitForElementToStop(renameInput);
    renameInput.click();    
    renameInput.clear();    
    renameInput.sendKeys(uuid);  
    renameInput.sendKeys(Keys.ENTER);
  }
  
}
