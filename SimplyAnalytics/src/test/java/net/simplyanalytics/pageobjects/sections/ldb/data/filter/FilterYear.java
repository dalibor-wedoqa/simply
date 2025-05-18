package net.simplyanalytics.pageobjects.sections.ldb.data.filter;

import java.util.ArrayList;
import java.util.List;

import net.simplyanalytics.pageobjects.base.BasePage;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import io.qameta.allure.Step;

public class FilterYear extends BasePage {
  
  private String type = " ";
  private String content = " ";
  
  public FilterYear(WebDriver driver) {
    super(driver);
  }
  
  @Override
  protected void isLoaded() {
  }
  
  @Step("Filter the Year")
  public List<String> filterYearClass(List<WebElement> allFilterElements) {
    List<String> filteredElements = new ArrayList<String>();
    for (int i = 0; i < allFilterElements.size(); i++) {
      try {
        if (allFilterElements.get(i).getAttribute("data-type").equals("year")) {
          type = allFilterElements.get(i).getText().split(" = ")[0];
          content = allFilterElements.get(i).getText().split(" = ")[1];
          filteredElements.add(content);
        }
      } catch (StaleElementReferenceException x) {
        if (allFilterElements.get(i).getAttribute("data-type").equals("year")) {
          type = allFilterElements.get(i).getText().split(" = ")[0];
          content = allFilterElements.get(i).getText().split(" = ")[1];
          filteredElements.add(content);
        }
      }
    }
    return filteredElements;
  }
  
}
