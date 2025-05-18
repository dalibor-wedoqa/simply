package net.simplyanalytics.pageobjects.sections.view;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.sections.view.base.TableViewWithoutDataVariableColoumnPanel;
import net.simplyanalytics.pageobjects.sections.view.histogram.LegendPanel;

public class HistogramViewPanel extends TableViewWithoutDataVariableColoumnPanel {
  
  @FindBy(xpath = "//*[name()= 'g' and @position='above']//*[name()='text']")
  private List<WebElement> graphTitle;
	
  @FindBy(xpath = "//*[name()= 'g' and @position='below']//*[name()='tspan']")
  private List<WebElement> graphVariable;
  
  @FindBy(xpath = "//*[name()= 'g' and @position='left']//*[name()='text']")
  private List<WebElement> graphYText;
	
  @FindBy(xpath = "//*[name()= 'g' and @id='content']//*[name()=\"text\" and not(@transform)]")
  private List<WebElement> valueOfYAxis;
  
  @FindBy(xpath = "//*[name()= 'g' and @id='content']//*[name()=\"text\" and contains(@transform, \"rotate\")]")
  private List<WebElement> valueOfXAxis;
  
  @FindBy(css = ".sa-mask-view")
  private WebElement histogramViewWindow;
  
  @FindBy(css = "#content g rect")
  private List<WebElement> bars;
  
  private LegendPanel legendPanel;
  
  public HistogramViewPanel(WebDriver driver) {
    super(driver, ViewType.HISTOGRAM);
    
  }
  
  @Override
  public void isLoaded() {
    waitForElementToAppear(histogramViewWindow, "Histogram data window is not present");
  }
  
  public List<String> getValuesOfYAxis() {
    List<String> listValuesOfAxis = new ArrayList<String>();
    for (WebElement value : valueOfYAxis) {
      listValuesOfAxis.add(value.getText().trim());
    }
    return listValuesOfAxis;
  }
  
  public int getBarHight(int index) {
    return bars.get(index).getSize().getHeight();
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

}
