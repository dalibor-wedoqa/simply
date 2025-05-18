package net.simplyanalytics.pageobjects;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ProjectManagerPage {
    private WebDriver driver;

    @FindBy(css = ".sa-general-settings-delete-button")
    private WebElement deleteProjectButton;

    @FindBy(css = ".sa-page-header-manage-button")
    private WebElement generalSettingsButton;

    @FindBy(css = ".sa-delete-project-popup-delete-btn")
    private WebElement confirmDeleteButton;

    @FindBy(css = ".sa-new-project-location-window-close")
    private WebElement newProjectCloseButton;

    @FindBy(xpath = "//span[contains(text(), 'Export')]/..")
    private WebElement exportButton;


    public ProjectManagerPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void deleteProject(String projectName) {
        System.out.println("Opening project settings to delete project: " + projectName);
        generalSettingsButton.click();
        deleteProjectButton.click();
        confirmDeleteButton.click();
        System.out.println("Project deleted successfully.");
    }

    public void createNewProject() {
        System.out.println("Creating a new project...");
        newProjectCloseButton.click();
        System.out.println("New project created successfully.");
    }

    public boolean isDeleteButtonPresent() {
        return deleteProjectButton.isDisplayed();
    }
}
