package net.simplyanalytics.pageobjects.sections.view.editview.containers;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.simplyanalytics.pageobjects.base.BasePage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import io.qameta.allure.Step;

public abstract class BaseContainerPanel extends BasePage {

	protected WebElement root;

	public BaseContainerPanel(WebDriver driver, By root) {
		super(driver, root);
		this.root = driver.findElement(root);
	}

	public BaseContainerPanel(WebDriver driver, WebElement root){
		super(driver,root);
		this.root = root;
	}

	/**
	 * Getting all container panel.
	 *
	 * @return list of all container panel
	 */
	public List<String> getAll() {
		List<String> result = new ArrayList<>();
		root.findElements(By.cssSelector(".sa-simple-container label")).stream().forEach(element -> {
			Pattern pattern = Pattern.compile("<span>(.+)<span>");
			Matcher matcher = pattern.matcher(
					element.findElement(By.cssSelector(".sa-check-field-label-text")).getAttribute("outerHTML"));
			if (matcher.find()) {
				result.add(matcher.group(1).trim());
			} else {
				result.add(element.getText());
			}
		});
		return result;
	}

	@Step("Click on element with name {0}")
	public void clickElement(String elementName) {
		if (!isElementPresent(elementName)) {
			throw new Error("The element is missing with name: " + elementName);
		}
		logger.debug("Click on element with name: " + elementName);
		By locator = By.xpath(".//div[contains(@class,'sa-check-field') and .//span[contains(text(),"
				+ xpathSafe(elementName) + ")]]");
		WebElement element = root.findElement(locator);

		if (this instanceof RadioButtonContainerPanel) {
			String active = ((RadioButtonContainerPanel) this).getSelectedElement();
			WebElement previousElement = root
					.findElement(By.xpath(".//div[contains(@class,'sa-check-field') and .//span[contains(text(),"
							+ xpathSafe(active) + ")]]"));
			element.click();
			waitForAttibuteToContain(root, locator, "class", "checked");
			if (!active.equals(elementName)) {
				waitForElementToDisappear(previousElement);
			}
		} else {
			if (!element.getAttribute("class").contains("checked")) {
				element.click();
				waitForAttibuteToContain(root, locator, "class", "checked");
			} else {
				element.click();
				waitForAttibuteToDisappear(root, locator, "class", "checked");
			}
		}

	}

	private boolean isElementPresent(String elementName) {
		return getAll().contains(elementName);
	}

	public List<String> getAllDisabledElements() {
		List<String> result = new ArrayList<>();
		root.findElements(By.cssSelector(".sa-edit-panel-disabled-item")).stream().forEach(element -> {
			Pattern pattern = Pattern.compile("<span>(.+)<span>");
			Matcher matcher = pattern.matcher(element.getAttribute("outerHTML"));
			if (matcher.find()) {
				result.add(matcher.group(1).trim());
			} else {
				result.add(element.getText());
			}
		});
		return result;
	}

}
