package net.simplyanalytics.pageobjects.sections.toolbar.map;

import java.util.ArrayList;
import java.util.List;

import net.simplyanalytics.enums.DataVariable;
import net.simplyanalytics.pageobjects.base.BasePage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;

public class DataVariableComboElements extends BasePage {

  protected static final By rootLocator = By
      .cssSelector(".sa-menu-button-menu:not([style*='hidden'])");   
  protected WebElement root;

  @FindBy(xpath = ".//span[contains(normalize-space(text()), 'None')]")
  private WebElement noneButton;
  
  @FindBy(tagName = "span")
  private List<WebElement> allElements;

  public DataVariableComboElements(WebDriver driver) {
    super(driver, rootLocator);
    root = driver.findElement(rootLocator);
  }

  @Override
  public void isLoaded() {
    waitForElementToAppear(noneButton, "The none button should appear");
  }

  /**
   * Click on data variable.
   * @param dataVariable data variable
   */
  @Step("Click on data variable \"{0}\"")
  public void clickDataVariable(DataVariable dataVariable) {
    logger.debug("Click on data variable: " + dataVariable.getFullName());
    WebElement button = root.findElement(
        By.xpath(".//span[normalize-space(text()) = \"" + dataVariable.getFullName() + "\"]"));
    waitForElementToAppear(button, "The dropdown button is not present");
    button.click();
    waitForElementToDisappear(root);
  }

  /**
   * Click on None button.
   */
  @Step("Click on None button")
  public void clickNoneButton() {
    logger.debug("Click on the None button");
    noneButton.click();
    waitForElementToDisappear(root);
  }
  
  public int getNumberOfElements() {
    return allElements.size();
  }
  
  public List<String> getElementsName(){
    List<String> elements = new ArrayList<String>();
    for(WebElement webElement : allElements)
      elements.add(webElement.getText().trim());
    getCurrentAllElements();
    return elements;
  }
  
  private void getCurrentAllElements() {
    allElements = root.findElements(By.cssSelector("span"));
  }
  
  public boolean isVariablePresent(DataVariable variable) {
    for (WebElement actualVariable : allElements) {
      if(actualVariable.getText().contains(variable.getName())) {
        return true;
      }
    }
    return false;
  }
}
