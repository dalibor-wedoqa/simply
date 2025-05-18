package net.simplyanalytics.pageobjects.sections.ldb.data;

import net.simplyanalytics.enums.DataVariable;
import net.simplyanalytics.enums.Page;
import net.simplyanalytics.pageobjects.base.BasePage;
import net.simplyanalytics.pageobjects.pages.main.MainPage;
import net.simplyanalytics.pageobjects.pages.main.NewViewPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditCrosstabPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditMapPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditScarboroughCrosstabPage;
import net.simplyanalytics.pageobjects.pages.main.views.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import io.qameta.allure.Step;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DataFolderPanel extends BasePage {

    protected static final By ROOT_LOCATOR = By.xpath("//div[contains(@class, 'sa-data-folders-browser-default')]");
    protected WebElement root;

    @FindBy(css = ".sa-close")
    private WebElement closeXButton;

    @FindBy(css = "span.sa-data-search-results-attribute-name")
    private List<WebElement> allDataList;

    @FindBy(xpath = "//div[not(contains(@class, 'sa-data-search-results-attribute-selected'))]")
    private List<WebElement> allNonSelectedData;

    private By allSelectedData = By.cssSelector(".sa-data-search-results-attribute-selected span.sa-data-search-results-attribute-name");

    public DataFolderPanel(WebDriver driver) {
        super(driver, ROOT_LOCATOR);
        root = driver.findElement(ROOT_LOCATOR);
    }

    @Override
    public void isLoaded() {
        waitForElementToAppearByLocatorWithCustomTime(ROOT_LOCATOR, "Data Folder panel did not appear.", 5);
    }

    /**
     * Click on the close button.
     *
     * @param page page
     * @return choosen page
     */
    @Step("Click on the close button")
    public MainPage clickClose(Page page) {
        logger.debug("Click on the close button");
        closeXButton.click();
        waitForElementToDisappear(root);
        switch (page) {
            case RANKING_VIEW:
                return new RankingPage(driver);
            case COMPARISON_REPORT_VIEW:
                return new ComparisonReportPage(driver);
            case BUSINESSES_VIEW:
                return new BusinessesPage(driver);
            case MAP_VIEW:
                return new MapPage(driver);
            case EDIT_MAP:
                return new EditMapPage(driver);
            case RING_STUDY_VIEW:
                return new RingStudyPage(driver);
            case RELATED_DATA_VIEW:
                return new RelatedDataReportPage(driver);
            case TIME_SERIES_VIEW:
                return new TimeSeriesPage(driver);
            case BAR_CHART:
                return new BarChartPage(driver);
            case SIMMONS_CROSSTAB_PAGE:
                return new CrosstabPage(driver);
            case EDIT_SIMMONS_CROSSTAB_PAGE:
                return new EditCrosstabPage(driver);
            case SCATTER_PLOT:
                return new ScatterPlotPage(driver);
            case SCARBOROUGH_CROSSTAB_PAGE:
                return new ScarboroughCrosstabPage(driver);
            case EDIT_SCARBOROUGH_CROSSTAB_PAGE:
                return new EditScarboroughCrosstabPage(driver);
            case NEW_VIEW:
                return new NewViewPage(driver);
            default:
                throw new AssertionError();
        }
    }

    /**
     * Check all the selected data.
     *
     * @return List of elements
     */
    public List<String> getAllDataList() {
        logger.debug("Get all the selected data from the data folder");
        List<String> result = new ArrayList<>();
        String year = DataVariable.getActualYear();
        waitForAllElementsToAppearByLocator(allSelectedData, "Checked data elements are not present ")
                .forEach(element -> result.add(getDataVariableText(element) + ", " + year));
        return result;
    }

    public List<String> getCanadaAllDataList() {
        logger.debug("Get all the selected data from the data folder");
        List<String> result = new ArrayList<>();
        String year = DataVariable.getActualCanadaYear();
        waitForAllElementsToAppearByLocator(allSelectedData, "Checked data elements are not present ")
                .forEach(element -> result.add(getDataVariableText(element) + ", " + year));
        return result;
    }

    public List<String> getAllDataListSeedVariable() {
        logger.debug("Get all the selected data from the data folder");
        List<String> result = new ArrayList<>();
        String year = DataVariable.getSeedVariablesYear();
        waitForAllElementsToAppearByLocator(allSelectedData, "Checked data elements are not present ")
                .forEach(element -> result.add(getDataVariableText(element) + ", " + year));
        return result;
    }

    public List<String> getAllDataList(boolean crosstab) {
        logger.debug("Get all the selected data from the data folder");
        List<String> result = new ArrayList<>();
        waitForAllElementsToAppearByLocator(allSelectedData, "Checked data elements are not present ")
                .forEach(element -> result.add(getDataVariableText(element) + ", " + "2019"));
        return result;
    }

    public List<String> getAllDataListNoYear(boolean crosstab) {
        logger.debug("Get all the selected data from the data folder");
        List<String> result = new ArrayList<>();
        waitForAllElementsToAppearByLocator(allSelectedData, "Checked data elements are not present ")
                .forEach(element -> result.add(getDataVariableText(element) + ", "));
        return result;
    }

    public String selectRandomCanadaData() {
        int random = new Random().nextInt(allDataList.size()-1);
        WebElement randomData = allDataList.get(random);
        randomData.click();
        String selectedData = randomData.getText() + ", " + DataVariable.getActualCanadaYear();
        selectedData = selectedData.replace(" est", "");
        logger.debug("Click on random  data variable");
        logger.trace(selectedData);
        return selectedData;
    }
    public String selectRandomDataSeedVariable() {
        int random = new Random().nextInt(allDataList.size()-1);
        WebElement randomData = allDataList.get(random);
        randomData.click();
        String selectedData = randomData.getText() + ", " + DataVariable.getSeedVariablesYear();
        selectedData = selectedData.replace(" est", "");
        logger.debug("Click on random  data variable");
        logger.trace(selectedData);
        return selectedData;
    }

    public String selectRandomCrosstabVariable() {
        int random = new Random().nextInt(allDataList.size()-1);
        WebElement randomData = allDataList.get(random);
        randomData.click();
        String selectedData = randomData.getText() + ", " + DataVariable.getCrosstabYear();
        selectedData = selectedData.replace(" est", "");
        logger.debug("Click on random  data variable");
        logger.trace(selectedData);
        return selectedData;
    }

    public String selectRandomData(boolean crosstab) {
        int random = new Random().nextInt(allDataList.size()-1);
        WebElement randomData = allDataList.get(random);
        randomData.click();
        String selectedData = randomData.getText() + ", 2024";
        selectedData = selectedData.replace(" est", "");
        logger.debug("Click on random  data variable");
        logger.trace(selectedData);
        return selectedData;
    }

    public String selectRandomDataYear(boolean crosstab) {
        int random = new Random().nextInt(allDataList.size()-1);
        WebElement randomData = allDataList.get(random);
        randomData.click();
        String selectedData = randomData.getText() + ", " + DataVariable.getYear2020();
        selectedData = selectedData.replace(" est", "");
        logger.debug("Click on random  data variable");
        logger.trace(selectedData);
        return selectedData;
    }



}
