package net.simplyanalytics.pageobjects.pages.main.views;

import net.simplyanalytics.pageobjects.sections.toolbar.map.MapToolbar;
import net.simplyanalytics.pageobjects.sections.view.map.MapViewPanel;
import net.simplyanalytics.pageobjects.sections.viewchooser.ViewChooserSection;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MapPage extends BaseViewPage {

  private final MapToolbar mapToolbar;
  private final MapViewPanel mapViewPanel;

  /**
   * MapPage constructor.
   * 
   * @param driver WebDriver
   */
  public MapPage(WebDriver driver) {
    super(driver);
    mapToolbar = new MapToolbar(driver, new ViewChooserSection(driver).getTopViewType());
    mapViewPanel = new MapViewPanel(driver);
  }
  
  @Override
  public void isLoaded() {
    waitForLoadingElementToDisappear(driver, By.cssSelector(".sa-boxed-spinner"), 150);
  }

  @Override
  public MapToolbar getToolbar() {
    return mapToolbar;
  }

  @Override
  public MapViewPanel getActiveView() {
    return mapViewPanel;
  }

}
