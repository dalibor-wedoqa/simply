package net.simplyanalytics.pageobjects.windows;

import java.util.ArrayList;
import java.util.List;

import net.simplyanalytics.enums.DataVariable;
import net.simplyanalytics.pageobjects.base.BasePage;
import net.simplyanalytics.pageobjects.pages.main.NewViewPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;

public class NewProjectVariablesWindow extends BasePage {

  protected static final By rootLocator = By.cssSelector(".sa-new-project-variables-window");

  @FindBy(css = ".sa-button-with-button-el")
  private WebElement createProjectButton;

  @FindBy(css = ".sa-new-project-variables-window-skip-variables-link")
  private WebElement createProjectWithoutSeedVars;

  @FindBy(css = "input[type='checkbox'][checked]")
  private List<WebElement> selectedDateVariables;

  //NB Start Added Locator for population variable
  @FindBy(xpath = "//label[contains(text(), 'Total Population')]")
  private WebElement getLabelForPopulation;

  @FindBy(xpath = "//label[contains(text(), 'Total Population')]/preceding-sibling::input")
  private WebElement getCheckBoxForPopulation;

  //NB End
  private WebElement root;

  public NewProjectVariablesWindow(WebDriver driver) {
    super(driver, rootLocator);
    root = driver.findElement(rootLocator);
  }

  @Override
  public void isLoaded() {
    waitForElementToAppear(createProjectButton, "Create Project button is missing");
    waitForElementToAppear(createProjectWithoutSeedVars,
        "Create Project without seed variables link is missing");
  }
  //NB Added code under
  //Add Population Count step
  @Step("Click on the #Total Population Checkbox")
  public NewProjectVariablesWindow clickCheckBoxButtonForPopulation() {
    logger.debug("Click on the #Total Population Check Box");
//    waitForElementToAppear(getCheckBoxForPopulation, "Check Box button is not visible");
//    sleep(1000);
//    getCheckBoxForPopulation.click();
    getLabelForPopulation.click();
    return new NewProjectVariablesWindow(driver);
  }
  //NB Code end
  @Step("Click on the Create project button")
  private void clickCreateProjectButtonVoid() {
    logger.debug("Click on the Create project button");
//    waitForElementToAppear(createProjectButton, "Create project button is not visible");
    createProjectButton.click();
  }

  public MapPage clickCreateProjectButton() {
    clickCreateProjectButtonVoid();
    MapPage mapPage = new MapPage(driver);
    return new MapPage(driver);
  }

  @Step("Click on the Create project button with out seed variables")
  private void clickCreateProjectWithoutVariablesVoid() {
    logger.debug("Click on the Create project button");
    waitForElementToAppear(createProjectWithoutSeedVars, "Create project button is not visible");
    createProjectWithoutSeedVars.click();
  }

  public MapPage clickCreateProjectWithOutVariables() {
    clickCreateProjectWithoutVariablesVoid();
    return new MapPage(driver);
  }


  /**
   * Click on the Create Project Without Seed Variables button.
   * @return ManageProjectPage
   */
  @Step("Click on the Create Project Without Seed Variables button")
  public NewViewPage clickCreateProjectWithoutSeedVariables() {
    logger.debug("Click on the Create Project Without Seed Variables button");
    createProjectWithoutSeedVars.click();
    return new NewViewPage(driver);
  }

  public NewViewPage clickCreateProjectButtonEmptyLocation() {
    clickCreateProjectButtonVoid();
    return new NewViewPage(driver);
  }

  /**
   * Getting selected data variables.
   * @return list of selected data variables
   */
  public List<DataVariable> getSelectedDataVariables() {
    List<DataVariable> result = new ArrayList<>();
    List<WebElement> checkedCheckboxes = driver.findElements(By.cssSelector("input[type='checkbox']:checked"));
    System.out.println("Number of checked checkboxes found: " + checkedCheckboxes.size());
    checkedCheckboxes.stream().forEach(webElement -> result.add(
        DataVariable.getByFullName(webElement.findElement(By.xpath("..")).getText() + ", " + DataVariable.getSeedVariablesYear())));
    return result;
  }

  /**
   * Click on checkbox.
   * @param dataVariableName data variable name
   */
  @Step("Click on the {0} checkbox")
  public void clickDataVariable(String dataVariableName) {
    logger.debug("Click on the " + xpathSafe(dataVariableName) + " checkbox");
    root.findElement(
                    By.xpath("//input[@type='checkbox' and following-sibling::text()[contains(., "+xpathSafe(dataVariableName)+")]]"))
            .click();;
  }
}
