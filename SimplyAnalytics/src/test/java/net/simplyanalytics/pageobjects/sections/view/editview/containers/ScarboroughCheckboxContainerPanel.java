package net.simplyanalytics.pageobjects.sections.view.editview.containers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;

public class ScarboroughCheckboxContainerPanel extends BaseContainerPanel {

    @FindBy(xpath = ".//div[normalize-space(.)='Clear']")
    private WebElement clearButton;

    @FindBy(xpath = ".//div[normalize-space(.)='Select all']")
    private WebElement selectAllButton;

    //@FindBy(css = ".sa-scarborough-crosstab-attributes-panel-body:nth-child(3) .sa-crosstab-attributes-panel-item")
    @FindBy(css = ".sa-edit-panel-enabled-section .sa-crosstab-attributes-panel-item")
    private List<WebElement> supportedDataItems;

    // @FindBy(css = ".sa-scarborough-crosstab-attributes-panel-body:nth-child(3) .sa-crosstab-attributes-panel-item .sa-crosstab-attributes-panel-item-row-button-wrap div")
    @FindBy(css = ".sa-crosstab-attributes-panel-item .sa-crosstab-attributes-panel-item-row-button-wrap div")
    private List<WebElement> rowCheckbox;

    // @FindBy(css = ".sa-scarborough-crosstab-attributes-panel-body:nth-child(3) .sa-crosstab-attributes-panel-item .sa-crosstab-attributes-panel-item-column-button-wrap div")
    @FindBy(css = ".sa-crosstab-attributes-panel-item .sa-crosstab-attributes-panel-item-column-button-wrap div")
    private List<WebElement> columnCheckbox;

    @FindBy(css = ".sa-scarborough-crosstab-attributes-panel-unsupported-header:nth-child(4):not([style='display: none;'])")
    private WebElement notSupportedDataUnderDMAMessage;

    @FindBy(css = ".sa-scarborough-crosstab-attributes-panel-body:nth-child(5) .sa-crosstab-attributes-panel-item")
    private List<WebElement> notSupportedDMADataItems;

    @FindBy(css = ".sa-edit-panel-disabled-section:nth-child(5) .sa-edit-panel-disabled-section-header")
    private WebElement notSupportedDataMessage;

    @FindBy(css = ".sa-edit-panel-disabled-section-body .sa-edit-panel-disabled-item")
    private List<WebElement> notSupportedDataItems;

    @FindBy(css = ".sa-simple-container .sa-crosstab-attributes-panel-item .sa-simple-container-default .x-border-box")
    private List<WebElement> addedDataItems;


    private String panelName;

    public ScarboroughCheckboxContainerPanel(WebDriver driver, By root, String panelName) {
        super(driver, root);
        this.panelName = panelName;
    }

    @Override
    public void isLoaded() {
        //waitForElementToAppear(selectAllButton, "Select all button should appear");
        //waitForElementToAppear(clearButton, "Clear button should appear");
    }

    /**
     * Click on select all.
     */
    @Step("Click on the Select All button")
    public void clickSelectAll() {
        allureStep("Click on the Select All button on panel " + panelName);
        logger.debug("Click on the Select All button on panel " + panelName);
        selectAllButton.click();
    }

    /**
     * Click on clear.
     */
    @Step("Click on the Clear button")
    public void clickClear() {
        allureStep("Click on the Clear button on panel " + panelName);
        logger.debug("Click on the Clear button on panel " + panelName);
        clearButton.click();
    }

    @Step("Select the Row data item checkbox button")
    public void clickDataItem(int index) {
        logger.debug("Click Row checkbox beside data with index: " + index);
        rowCheckbox.get(index).click();
    }

    public boolean isRowCheckboxSelected(int index) {
        return rowCheckbox.get(index).getAttribute("class").contains("sa-check-button-checked");
    }

    public boolean isRowCheckboxSelected(String dataName) {
        List<WebElement> datas = supportedDataItems.stream()
                .filter(data -> data.findElement(By.cssSelector(".sa-crosstab-attributes-panel-item-label")).getText().trim().contains(dataName))
                .collect(Collectors.toList());
        WebElement data = datas.get(0).findElement(By.cssSelector(".sa-crosstab-attributes-panel-item-row-button-wrap .sa-check-button"));
        return data.getAttribute("class").contains("sa-check-button-checked");
    }

    public boolean isColumnCheckboxSelected(int index) {
        return columnCheckbox.get(index).getAttribute("class").contains("sa-check-button-checked");
    }

    public boolean isColumnCheckboxSelected(String dataName) {
        List<WebElement> datas = supportedDataItems.stream()
                .filter(data -> data.findElement(By.cssSelector(".sa-crosstab-attributes-panel-item-label")).getText().trim().contains(dataName))
                .collect(Collectors.toList());
        WebElement data = datas.get(0).findElement(By.cssSelector(".sa-crosstab-attributes-panel-item-column-button-wrap .sa-check-button"));
        return data.getAttribute("class").contains("sa-check-button-checked");
    }

    public List<String> getAllData(){
        return supportedDataItems.stream().map(data -> data.findElement(By.cssSelector(".sa-crosstab-attributes-panel-item-label")).getText().trim())
                .collect(Collectors.toList());
    }

    public List<String> getAllCheckedDataRows(){
        List<String> dataList = new ArrayList<String>();
        for(WebElement data : supportedDataItems) {
            if (data.findElement(By.cssSelector(".sa-crosstab-attributes-panel-item-row-button-wrap div"))
                    .getAttribute("class").contains("sa-check-button-checked")) {
                dataList.add(data.findElement(By.cssSelector(".sa-crosstab-attributes-panel-item-label")).getText());

            }
        }
        return dataList;
    }

    public List<String> getAllCheckedDataColumns(){
        List<String> dataList = new ArrayList<String>();
        for(WebElement data : supportedDataItems) {
            if (data.findElement(By.cssSelector(".sa-crosstab-attributes-panel-item-column-button-wrap div"))
                    .getAttribute("class").contains("sa-check-button-checked")) {
                dataList.add(data.findElement(By.cssSelector(".sa-crosstab-attributes-panel-item-label")).getText());
            }
        }
        return dataList;
    }

    public String getUnsupportedDataVariablesUnderDMAHeaderMessage() {
        return notSupportedDataUnderDMAMessage.getText().trim();
    }

    public List<String> getAllNotSupportedDataUnderDMA(){
        return notSupportedDMADataItems.stream().map(data -> data.findElement(By.cssSelector(".sa-crosstab-attributes-panel-item-label")).getText().trim())
                .collect(Collectors.toList());
    }

    public String getUnsupportedDataVariablesHeaderMessage() {
        return notSupportedDataMessage.getText().trim();
    }

    public List<String> getAllNotSupportedData(){
        List<String> result = new ArrayList<>();
        root.findElements(By.cssSelector(".sa-edit-panel-disabled-item-label")).stream()
                .forEach(element -> {
                    Pattern pattern = Pattern.compile("<span>(.+)<span>");
                    Matcher matcher = pattern.matcher(element.getAttribute("outerHTML"));
                    if(matcher.find()) {
                        result.add(matcher.group(1).trim());
                    }
                    else {
                        result.add(element.getText());
                    }
                });
        return result;
    }

    public List<Row> getRows() {
        waitForPageLoadedShort();
        return supportedDataItems.stream()
                .map(e -> new Row(driver, e))
                .collect(Collectors.toList());
    }

    public Row getRowByName(String name) {
        return getRows().stream()
                .filter(p -> p.getName().contains(name))
                .findFirst()
                .orElseThrow(()-> new AssertionError(String.format("Row with name \"%s\" absent", name)));
    }

    public class Row extends BaseContainerPanel{

        @FindBy(css = ".sa-crosstab-attributes-panel-item-label")
        protected WebElement dataName;

        @FindBy(css = "div.sa-crosstab-attributes-panel-item-row-button-wrap")
        protected WebElement checkboxRow;

        @FindBy(css = "div.sa-crosstab-attributes-panel-item-column-button-wrap")
        protected WebElement checkboxColumn;

        public Row(WebDriver driver, WebElement e) {
            super(driver, e);
        }

        @Override
        public void isLoaded() {

        }

        public String getName(){
            return waitForElementToAppear(dataName, "Data name was not found").getText().trim();
        }

        public void clickRowCheckbox(){
            waitForElementToAppear(checkboxRow, "Checkbox for row not present").click();
        }

        public void clickColumnCheckbox(){
            waitForElementToAppear(checkboxColumn, "Checkbox for column not present").click();
        }
    }
}
