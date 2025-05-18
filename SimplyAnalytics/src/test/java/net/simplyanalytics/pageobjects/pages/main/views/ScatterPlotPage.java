package net.simplyanalytics.pageobjects.pages.main.views;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.sections.toolbar.scatterplot.ScatterPlotToolbar;
import net.simplyanalytics.pageobjects.sections.view.ScatterPlotViewPanel;
import net.simplyanalytics.pageobjects.sections.viewchooser.ViewChooserSection;

public class ScatterPlotPage extends BaseViewPage {

  @FindBy(css = ".sa-scatter-plot-view-wrap")
  private WebElement table;
  
  private final ScatterPlotToolbar scatterPlotToolbar;
  private final ScatterPlotViewPanel scatterPlotViewPanel;
  
  public ScatterPlotPage(WebDriver driver) {
    super(driver);
    scatterPlotToolbar 
      = new ScatterPlotToolbar(driver, new ViewChooserSection(driver).getTopViewType());
    scatterPlotViewPanel = new ScatterPlotViewPanel(driver, ViewType.SCATTER_PLOT);
  }
  
  @Override
  public void isLoaded() {
    waitForElementToAppear(table, "The scatter plot is not loaded");
  }
  
  @Override
  public ScatterPlotToolbar getToolbar() {
    return scatterPlotToolbar;
  }
  
  @Override
  public ScatterPlotViewPanel getActiveView() {
    return scatterPlotViewPanel;
  }
}
