package net.simplyanalytics.pageobjects.sections.view.map;

import net.simplyanalytics.pageobjects.base.BasePage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ScalePanel extends BasePage {

  protected WebElement root;
  
  protected ScalePanel(WebDriver driver, WebElement root) {
    super(driver, root);
    this.root = root;
  }
  
  @Override
  protected void isLoaded() {

  }

  public String getMapScale() {
    return root.getText();
  }
  
}
