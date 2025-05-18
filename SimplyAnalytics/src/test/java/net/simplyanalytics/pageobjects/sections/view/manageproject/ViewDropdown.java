package net.simplyanalytics.pageobjects.sections.view.manageproject;

import net.simplyanalytics.pageobjects.base.BasePage;
import net.simplyanalytics.pageobjects.pages.projectsettings.ManageViewsPanel;
import net.simplyanalytics.pageobjects.sections.viewchooser.RenameViewPanel;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;

public class ViewDropdown extends BasePage {
  
  @FindBy(xpath = ".//span[text()='Move to top']")
  private WebElement moveToTopButton;
  
  @FindBy(xpath = ".//span[text()='Rename']")
  private WebElement renameButton;
  
  @FindBy(xpath = ".//span[text()='Delete']")
  private WebElement deleteButton;
  
  protected static By root = By.cssSelector(".sa-manage-project-view-list-menu");
  
  public ViewDropdown(WebDriver driver) {
    super(driver, root);
  }
  
  @Override
  public void isLoaded() {
    waitForElementToAppear(moveToTopButton, "Move to top button not appeared");
    waitForElementToAppear(renameButton, "Rename button not appeared");
    waitForElementToAppear(deleteButton, "Delete button not appeared");
  }
  
  /**
   * Click on the Move to top button.
   * @return ManageProjectPage
   */
  @Step("Click on the Move to top button")
  public ManageViewsPanel clickMoveToTop() {
    logger.debug("Click on the Move to top button");
    moveToTopButton.click();
    return new ManageViewsPanel(driver);
  }
  
  /**
   * Click on the Rename button.
   * @return RenameViewPanel
   */
  @Step("Click on the Rename button")
  public RenameViewPanel clickRename() {
    logger.debug("Click on the Rename button");
    renameButton.click();
    return new RenameViewPanel(driver);
  }
  
  /**
   * Click on the Delete button.
   * @return ManageProjectPage
   */
  @Step("Click on the Delete button")
  public ManageViewsPanel clickDelete() {
    logger.debug("Click on the Delete button");
    deleteButton.click();
    return new ManageViewsPanel(driver);
  }
  
  public boolean isMoveToTopDisabled() {
    return moveToTopButton.findElement(By.xpath("./ancestor::div[contains(@class, 'x-menu-item')]"))
        .getAttribute("class").contains("x-menu-item-disabled");
  }
}
