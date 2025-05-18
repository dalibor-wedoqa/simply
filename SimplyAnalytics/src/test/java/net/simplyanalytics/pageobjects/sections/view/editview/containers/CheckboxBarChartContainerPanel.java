package net.simplyanalytics.pageobjects.sections.view.editview.containers;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.text.StringEscapeUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;

public class CheckboxBarChartContainerPanel extends BaseContainerPanel {

	@FindBy(css = ".sa-edit-3-panel-header-select-all-button")
	private WebElement clearButton;

	@FindBy(css = ".sa-edit-3-panel-header-clear-button")
	private WebElement selectAllButton;

	@Override
	public void isLoaded() {
//      waitForElementToAppear(selectAllButton, "Select all button should appear");
//      waitForElementToAppear(clearButton, "Clear button should appear");
	}

	private String panelName;

	public CheckboxBarChartContainerPanel(WebDriver driver, By root, String panelName) {
		super(driver, root);
		this.panelName = panelName;
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

	public List<String> getSelectedElements() {
		List<String> result = new ArrayList<>();
		root.findElements(By.cssSelector(".sa-checkbox-checked .sa-check-field-label-text")).stream()
				.forEach(element -> {
					Pattern pattern = Pattern.compile("<span>(.+)<span>");
					Matcher matcher = pattern.matcher(element.getAttribute("outerHTML"));
					if (matcher.find()) {
						result.add(StringEscapeUtils.unescapeHtml4(matcher.group(1).trim()));
					} else {
						result.add(element.getText());
					}
				});
		return result;
	}
	

	public List<String[]> getAllElementsAndBadges() {
		List<WebElement> elements = root.findElements(By.cssSelector(".sa-radio-label-text"));
		return elements.stream().map(element -> {
			Pattern pattern = Pattern.compile("<span>(.+)<span>");
			Matcher matcher = pattern.matcher(element.getAttribute("outerHTML"));
			String badge = element.findElement(By.cssSelector(".sa-attribute-badge")).getText().trim();
			if (matcher.find()) {
				String[] dataArray = { StringEscapeUtils.unescapeHtml4(matcher.group(1).replace("  ", " ").trim()),
						badge };
				return dataArray;
			} else {
				logger.info(element.getText().trim());
				String[] dataArray = { element.getText().trim(), badge };
				return dataArray;
			}
		}).collect(Collectors.toList());
	}

	/**
	 * Hover a badge of LDB item.
	 * 
	 * @param itemName item name
	 */
	@Step("Hover the badge of LDB item: \"{0}\"")
	public List<String> getDatasetNameandVendorAfterHovering(String itemName) {
		List<String> tooltip = new ArrayList<String>();
		logger.debug("Hover badge of LDB item: " + itemName);
		WebElement dataElement = root.findElements(By.cssSelector(".sa-checkbox-checked .sa-check-field-label-text"))
				.stream().filter(element -> element.getText().trim().contains(itemName)).collect(Collectors.toList())
				.get(0);
		Actions builder = new Actions(driver);
		builder.moveToElement(dataElement.findElement(By.cssSelector(".sa-attribute-badge "))).perform();
		sleep(200);
		String text = driver.findElement(By.cssSelector(".sa-tooltip-content")).getText().trim();
		Pattern pattern = Pattern.compile("Dataset: (.+)");
		Matcher matcher = pattern.matcher(text);
		if (matcher.find()) {
			tooltip.add(matcher.group(1).trim());
		}
		pattern = Pattern.compile("Vendor: (.+)");
		matcher = pattern.matcher(text);
		if (matcher.find()) {
			tooltip.add(matcher.group(1).trim());
		}

		return tooltip;
	}
}
