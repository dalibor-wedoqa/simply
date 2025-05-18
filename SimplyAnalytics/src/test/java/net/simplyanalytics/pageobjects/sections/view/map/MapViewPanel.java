package net.simplyanalytics.pageobjects.sections.view.map;

import net.simplyanalytics.pageobjects.sections.view.base.ViewPanel;
import net.simplyanalytics.tests.view.map.MapBusinessPointsTests;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

public class MapViewPanel extends ViewPanel {

  @FindBy(css = ".sa-simple-container:not(.sa-project-view-hidden)")
  private WebElement mapContainer;

  @FindBy(xpath = "//a[contains(@title,'Zoom in with bounding box')]")
  protected WebElement mapContainerZoom;

  @FindBy(css = ".sa-legend")
  private WebElement legend;

  @FindBy(css = ".leaflet-layer[style*='z-index: 1;'] img.leaflet-tile:nth-child(1)")
  private WebElement tile;

  @FindBy(css = ".sa-vector-map-zoom-slider-button")
  private WebElement mapZoomSlider;

  //@FindBy(css = ".sa-map-controls")-old locator
  @FindBy(css = ".sa-vector-map-tool-buttons-button")
  private WebElement controls;

  @FindBy(css = ".maplibregl-ctrl-scale")
  private WebElement mapScale;

  @FindBy(xpath = "(//canvas[@class= 'maplibregl-canvas mapboxgl-canvas'])[2]")
  private WebElement minimap;

  //@FindBy(css = ".sa-map-click-info") -old locator
  @FindBy(css = ".sa-vector-map-view")
  private WebElement mapWithInfoChecked;
  
  @FindBy(css = ".leaflet-image-layer")
  private List<WebElement> highlightedLocations;
  
  @FindBy(css = ".sa-map-point")
  private List<WebElement> mapPoints;

  private LegendPanel legendPanel;
  private ZoomSliderPanel zoomSliderPanel;
  private ControlsPanel controlsPanel;
  private ScalePanel scalePanel;
  private MinimapPanel minimapPanel;

  public MapViewPanel(WebDriver driver) {
    super(driver);
  }

  @Override
  public void isLoaded() {
    waitForLoadingToDisappear();
    waitForElementToAppear(mapContainer, "The map not appeared");
  }

  /**
   * Getting legend.
   * 
   * @return legendPanel
   */
  public LegendPanel getLegend() {
    if (legendPanel == null) {
      legendPanel = new LegendPanel(driver, root);
    }
    return legendPanel;
  }

  /**
   * Getting the zoom slider.
   * 
   * @return zoom slider
   */
  public ZoomSliderPanel getZoomSlider() {
    if (zoomSliderPanel == null) {
      zoomSliderPanel = new ZoomSliderPanel(driver, mapZoomSlider);
    }
    return zoomSliderPanel;
  }

  /**
   * Getting controls panelel.
   * 
   * @return controls panel
   */
  public ControlsPanel getControlsPanel() {
    if (controlsPanel == null) {
      controlsPanel = new ControlsPanel(driver, controls, this);
    }
    return controlsPanel;
  }

  /**
   * Getting scale panel.
   * 
   * @return scale panel
   */
  public ScalePanel getScalePanel() {
    if (scalePanel == null) {
      scalePanel = new ScalePanel(driver, mapScale);
    }
    return scalePanel;
  }

  /**
   * Getting minimap panel.
   * 
   * @return minimap panel
   */
  public MinimapPanel getMinimapPanel() {
    if (minimapPanel == null) {
      minimapPanel = new MinimapPanel(driver, minimap);
    }
    return minimapPanel;
  }

  public boolean isMapLabelsPresent() {
    return isChildPresent(mapContainer,
        By.cssSelector(".leaflet-overlay-pane img[style*='z-index: 7;']"));
  }

  public boolean isHighlighLocationsPresent() {
    return isChildPresent(mapContainer,
        By.cssSelector(".leaflet-overlay-pane img[style*='z-index: 4;']"));
  }

  public boolean isFilterLayerPresent() {
    return isChildPresent(mapContainer,
        By.cssSelector(".leaflet-overlay-pane img[style*='z-index: 2;']"));
  }

  public boolean isLegendPresent() {
    return isChildDisplayed(root, LegendPanel.ROOT_LOCATOR);
  }

  /**
   * Getting map titles id.
   * 
   * @return map titles id
   */
  public String getMapTilesId() {
    waitForFullLoad();
    String[] str = tile.getAttribute("src").split("id=");
    return str[1].substring(0, 31);
  }

  /**
   * Wait for page to be fully loaded.
   */
  public void waitForFullLoad() {
    waitForElementToAppearWithCustomTime(
        By.cssSelector(".mapboxgl-ctrl-bottom-right"),
        "Tiles not loaded", 120);
    sleep(2000);
  }

  public MapInformationPanel clickCenterOfMapInfo() {
    logger.debug("Click on the map");
    waitForElementToBeClickable(mapWithInfoChecked,
        "Map with Checked Information control is not appeared").click();
    return new MapInformationPanel(driver);
  }
  
  public MapInformationPanel clickOnMapInfoByCoordinates(int x, int y) {
    logger.debug("Click on the map bz coordinates: (" + x + ", " + y + ")");
    new Actions(driver).moveToElement(mapWithInfoChecked).moveByOffset(x, y).click().perform();
    return new MapInformationPanel(driver);
  }
  
  public Point getHighlightedLocationCoordinates() {
    waitForElementToAppear(highlightedLocations.get(1), "No highlighted area");
    return highlightedLocations.get(1).getLocation();
  }

  // TODO fix this methos. now wont working
  public MapViewPanel dragAndDrop(int offsetXPoz, int offsetYPoz) {
    logger.debug("Move Map with offset: X=" + offsetXPoz + ", Y=" + offsetYPoz);

    Actions actions = new Actions(driver);
    actions.dragAndDropBy(legend, offsetXPoz, offsetYPoz).perform();
    waitForElementToStop(legend);
    return new MapViewPanel(driver);
  }
  
  public int getMapPointsCount() {
    return mapPoints.size();
  }
  
  public BusinessPointsPanel clickOnMapPointByIndex(int index) {
    waitForElementToBeClickable(mapPoints.get(index), "Map point is not clickable").click();
    return new BusinessPointsPanel(driver);
  }
  
  public int getNumberOnMapPoint(int index) {
    return Integer.parseInt(mapPoints.get(index).getText().trim());
  }
  
}
