package net.simplyanalytics.pageobjects.sections.view.base;

import net.simplyanalytics.pageobjects.sections.ldb.data.DataFolderPanel;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import net.simplyanalytics.pageobjects.base.BasePage;
import net.simplyanalytics.pageobjects.pages.main.views.BaseViewPage;
import net.simplyanalytics.pageobjects.windows.MetadataWindow;
import io.qameta.allure.Step;

public class CrosstabHeaderDropdown extends BasePage {

  
  private GenericTableViewPanel tableViewPanel;
  
  // Favorites
  @FindBy(xpath = ".//li[text()='Add to Favorites']")
  private WebElement addFavorites;
  
  @FindBy(xpath = ".//li[text()='Remove from Favorites']")
  private WebElement removeFavorites;
  
  // Data Variable
  @FindBy(xpath = ".//li[text()='View Metadata']")
  private WebElement viewMetadata;
  
  @FindBy(xpath = ".//li[text()='Remove from this Report']")
  private WebElement hideDataVariable;

  @FindBy(xpath = ".//li[text()='Open Data Folder']")
  private WebElement openDataFolder;

  @SuppressWarnings("ucd")
  protected WebElement root;
  
  /**
   * HeaderDropdown constructor.
   * 
   * @param tableViewPanel GenericTableViewPanel
   * @param driver         WebDriver
   * @param root           header dropdown WebElement
   */
  public CrosstabHeaderDropdown(GenericTableViewPanel tableViewPanel, WebDriver driver, WebElement root) {
    super(driver, root);
    this.tableViewPanel = tableViewPanel;
    this.root = root;
  }
  
  @Override
  protected void isLoaded() {
    // no need, if root is loaded, all the elements appears at once
  }
  
  // Favorites
  /**
   * Click on the Add to Favorites button.
   * 
   * @return tableViewPanel
   */
  @Step("Click on the Add to Favorites button")
  public BaseViewPage clickAddFavorites() {
    logger.debug("Click on the Add to Favorites button");
    waitForElementToStop(addFavorites);
    addFavorites.click();
    return this.tableViewPanel.viewType.getNewPage(driver);
  }
  
  /**
   * Click on the Remove from Favorites button.
   * 
   * @return tableViewPanel
   */
  @Step("Click on the Remove from Favorites button")
  public BaseViewPage clickRemoveFavorites() {
    logger.debug("Click on the Remove from Favorites button");
    removeFavorites.click();
    return this.tableViewPanel.viewType.getNewPage(driver);
  }

  // Data variable
  /**
   * Click on the View Metadata button.
   * 
   * @return MetadataWindow
   */
  @Step("Click on the View Metadata button")
  public MetadataWindow clickViewMetadata() {
    logger.debug("Click on the View Metadata button");
    viewMetadata.click();
    waitForLoadingToDisappear();
    return new MetadataWindow(driver);
  }
  
  public boolean isAddedToFavorites() {
    return isPresent(removeFavorites);
  } 
  
  /**
   * Click on the Hide Data Variable from this Report button.
   * 
   * @return tableViewPanel
   */
  @Step("Click on the Hide Data Variable from this Report button")
  public BaseViewPage clickHideDataVariable() {
    logger.debug("Click on the Hide Data Variable from this Report button");
    hideDataVariable.click();
    return this.tableViewPanel.viewType.getNewPage(driver);
  }

  /**
   * Click on the Open Data Folder button
   *
   * @return DataByCategoryDropwDown
   */
  @Step("Click on the Open Data Folder button")
  public DataFolderPanel clickOpenDataFolder() {
    logger.debug("Click on the Open Data Folder button");
    openDataFolder.click();
    waitForLoadingToDisappear();
    return new DataFolderPanel(driver);
  }

}
