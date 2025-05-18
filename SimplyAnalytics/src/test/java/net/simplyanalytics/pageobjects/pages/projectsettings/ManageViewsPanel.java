package net.simplyanalytics.pageobjects.pages.projectsettings;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.sections.view.base.ViewPanel;
import net.simplyanalytics.pageobjects.sections.view.manageproject.ViewDropdown;
import io.qameta.allure.Step;

public class ManageViewsPanel extends ViewPanel {

  private static final By ROOT = By
      .cssSelector(".sa-project-view:not(.sa-project-view-hidden) .sa-manage-project-view-list");

  private WebElement rootElement;
  
  @FindBy(css = ".sa-manage-project-view-list-item:not([style*='display: none;']) "
      + ".sa-manage-project-view-list-item-name")
  private List<WebElement> views;
  
  public ManageViewsPanel(WebDriver driver) {
    super(driver);
    rootElement = driver.findElement(ROOT);
  }
  
  @Override
  public void isLoaded() {

  }
  
  /**
   * Getting all views.
   * @return list of all views
   */
  public List<String> getAllViews() {
    List<String> result = new ArrayList<>();
    views.forEach(element -> result.add(element.getText()));
    return result;
  }

  /**
   * Getting view icon.
   * @param viewName view name
   * @return view icon
   */
  public ViewType getViewIcon(String viewName) {
    WebElement icon = getViewRow(viewName)
        .findElement(By.cssSelector(".sa-manage-project-view-list-item-icon use"));
    String iconName = icon.getAttribute("href");
    switch (iconName) {
      case "#sa-icon-comparison-report":
        return ViewType.COMPARISON_REPORT;
      case "#sa-icon-map":
        return ViewType.MAP;
      case "#sa-icon-ranking-report":
        return ViewType.RANKING;
      case "#sa-icon-ring-study":
        return ViewType.RING_STUDY;
      case "#sa-icon-business-report":
        return ViewType.BUSINESSES;
      case "#sa-icon-related-data-report":
        return ViewType.RELATED_DATA;
      case "#sa-icon-quick-report":
        return ViewType.QUICK_REPORT;
      case "#sa-icon-time-series-report":
        return ViewType.TIME_SERIES;
      case "#sa-icon-bar-chart":
        return ViewType.BAR_CHART;
      case "#sa-icon-histogram":
          return ViewType.HISTOGRAM;
      case "#sa-icon-crosstab":
        if(viewName.contains("Simmons"))
          return ViewType.CROSSTAB_TABLE;
        else
          return ViewType.SCARBOROUGH_CROSSTAB_TABLE;
      case "#sa-icon-scatter-plot":
        return ViewType.SCATTER_PLOT;
      default:
        throw new AssertionError("This icon is unknown: " + iconName);
    }
  }

  /**
   * Open view menu for view with name.
   * @param viewName view name
   * @return ViewDropdown
   */
  @Step("Open view menu for view with name \"{0}\"")
  public ViewDropdown clickViewMenu(String viewName) {
    logger.debug("Open view menu for view with name: " + viewName);
    getViewRow(viewName).findElement(By.xpath(".//a[contains(@class, 'sa-manage-project-list-item-menu-button')]")).click();
    return new ViewDropdown(driver);
  }

  /**
   * Click on the up arrow for view.
   * @param viewName view name
   */
  @Step("Click on the up arrow for view {0}")
  public void clickUpArrow(String viewName) {
    logger.debug("Click on the up arrow for view: " + viewName);
    getViewRow(viewName).findElement(By.cssSelector(".sa-manage-project-view-list-item-move-up"))
        .click();
    waitForElementToStop(getViewRow(viewName));
  }

  /**
   * Click on the down arrow for view.
   * @param viewName view name
   */
  @Step("Click on the down arrow for view {0}")
  public void clickDownArrow(String viewName) {
    logger.debug("Click on the down arrow for view: " + viewName);
    getViewRow(viewName).findElement(By.cssSelector(".sa-manage-project-view-list-item-move-down"))
        .click();
    waitForElementToStop(getViewRow(viewName));
  }

  /**
   * . When the distance is positive it will push it down, when it is negative it
   * will pull it up
   * 
   * 
   * @param viewName the name of the view
   * @param distance distance
   */
  public void dragAndDropRow(String viewName, int distance) {
    String log;
    if (distance > 0) {
      log = "Move the view row with name " + viewName + " with drag and drop down " + distance
          + " rows ";
    } else {
      log = "Move the view row with name " + viewName + " with drag and drop up " + -distance
          + " rows";
    }
    allureStep(log);
    logger.debug(log);
    WebElement row = getViewRow(viewName);
    Actions actions = new Actions(driver);
    actions.dragAndDropBy(row, 0, distance * row.getSize().getHeight()).build().perform();
    waitForElementToStop(row);
    while (!row.getAttribute("style").isEmpty()) {
      logger.trace(row.getAttribute("style"));
      sleep(100);
    }
  }

  private WebElement getViewRow(String viewName) {
    return driver.findElement(
            By.xpath("//div[contains(@class, 'sa-manage-project-view-list-item')]/span[text()='" + viewName + "']/.."));
  }
  
  /**
   * Delete all views.
   */
  @Step("Delete all views")
  public void deleteAllViews() {
    logger.debug("Delete all views");
    getAllViews().forEach(
        viewName -> clickViewMenu(viewName).clickDelete());
  }

}
