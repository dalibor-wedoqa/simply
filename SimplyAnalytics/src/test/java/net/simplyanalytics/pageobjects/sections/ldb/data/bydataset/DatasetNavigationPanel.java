package net.simplyanalytics.pageobjects.sections.ldb.data.bydataset;

import net.simplyanalytics.pageobjects.base.BasePage;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import io.qameta.allure.Step;

import static net.simplyanalytics.utils.StringParsing.formatXPathString;

public class DatasetNavigationPanel extends BasePage {

  protected static final By ROOT_LOCATOR = By.cssSelector(".sa-data-folders-tree");
  protected WebElement root;
  public static String dataFolderPath;

  /**
   * DatasetNavigationPanel constructor.
   * @param driver WebDriver
   * @param rootFolderName root folder name
   */
  public DatasetNavigationPanel(WebDriver driver, String rootFolderName) {
    super(driver, ROOT_LOCATOR);
    root = driver.findElement(ROOT_LOCATOR);
    waitForLoad(rootFolderName);
  }
  
  @Override
  public void isLoaded() {

  }

  /**
   * Click on folder.
   * @param folderName folder name
   * @return DataByDatasetDropDown
   */
  @Step("Click on folder {0}")
  public DataByDatasetDropDown clickFolder(String folderName) {
    logger.debug("Click on the folder " + folderName);
    WebElement folder = root
        .findElement(By.xpath("//button[contains(@class, 'sa-data-folders-tree-item')][.//span[text()=\""
            + folderName + "\"]]"));
    waitForElementToBeClickable(folder,"folder " + folderName + " is not clickable");
    folder.click();
    return new DataByDatasetDropDown(driver, folderName);
  }

  @Step("Open random folders")
  public DataByDatasetDropDown openRandomFolders() {
    waitForAllElementsToAppear(driver.findElements(By.cssSelector(".sa-data-folders-tree > .sa-data-folders-tree-list > li > button"))
            ,"All folders are not displayed");
    List<WebElement> folderList = driver.findElements(By.cssSelector(".sa-data-folders-tree > .sa-data-folders-tree-list > li > button"));
    int randomIndex = new Random().nextInt(folderList.size());
    WebElement folder = folderList.get(randomIndex);
    String folderName = folder.findElement(By.cssSelector(".sa-data-folders-tree-item-name")).getText().trim();
    if(folder.getAttribute("class").contains("sa-data-folders-tree-item-leaf")) {
      clickFolder(folderName);
      waitForLeafFolderOpen(folderName);
    }
    else if (folder.getAttribute("class").contains("sa-data-folders-tree-item-leaf")) {
      clickFolder(folderName);
      waitForLeafFolderOpen(folderName);
    }
    else {
      clickFolder(folderName);
      waitForFolderOpen(folderName);
      navigateToFolders(folder);
    }
    return new DataByDatasetDropDown(driver,
            folder.findElement(By.cssSelector(".sa-data-folders-tree-item-name")).getText().trim());
  }


  @Step("Open random folders B")
  public DataByDatasetDropDown openRandomFoldersB() {
    System.out.println("Step: Finding all folder elements using CSS Selector.");
    // Find all folder elements in the tree using the specified CSS selector
    waitForAllElementsToAppear(driver.findElements(By.cssSelector(".sa-data-folders-tree > .sa-data-folders-tree-list > li > button")),
            "All folders are not displayed");

    System.out.println("Step: Storing the list of folder elements.");
    // Store the folder elements in a list
    List<WebElement> folderList = driver.findElements(By.cssSelector(".sa-data-folders-tree > .sa-data-folders-tree-list > li > button"));

    System.out.println("Step: Selecting a random index from the folderList array.");
    // Generate a random index to select a folder from the list
    int randomIndex = new Random().nextInt(folderList.size());

    System.out.println("Step: Selecting the folder based on the random index.");
    // Select the folder element at the random index
    WebElement folder = folderList.get(randomIndex);

    System.out.println("Step: Getting the folder name from the selected folder.");
    // Extract the folder name from the selected folder element
    String folderName = folder.findElement(By.cssSelector(".sa-data-folders-tree-item-name")).getText().trim();

    System.out.println("Step: Checking if the selected folder is a leaf folder.");
    // Check if the folder is a leaf folder
    if (folder.getAttribute("class").contains("sa-data-folders-tree-item-leaf")) {
      System.out.println("Step: Folder is a leaf folder, clicking the folder and waiting for it to open.");
      // If it's a leaf folder, click and wait for it to open
      clickFolder(folderName);
      waitForLeafFolderOpen(folderName);
    }
    else {
      System.out.println("Step: Folder is not a leaf folder, clicking and waiting for folder to open.");
      // If it's not a leaf folder, click and wait for the folder to open
      clickFolder(folderName);
      waitForFolderOpen(folderName);

      System.out.println("Step: Navigating to subfolders within the selected folder.");
      // Navigate to subfolders inside the folder
      navigateToFoldersB(folder);
    }

    System.out.println("Step: Returning a new DataByDatasetDropDown object with the selected folder's name.");
    // Return a new DataByDatasetDropDown object with the selected folder's name
    return new DataByDatasetDropDown(driver,
            folder.findElement(By.cssSelector(".sa-data-folders-tree-item-name")).getText().trim());
  }

  @Step("Open random folders American Community")
  public DataByDatasetDropDown openRandomFoldersACSurvey() {
    waitForAllElementsToAppear(driver.findElements(By.cssSelector(".sa-data-folders-tree > .sa-data-folders-tree-list > li > button"))
            ,"All folders are not displayed");
    List<WebElement> folderList = driver.findElements(By.cssSelector(".sa-data-folders-tree > .sa-data-folders-tree-list > li > button"));
    int randomIndex = new Random().nextInt(folderList.size());
    WebElement folder = folderList.get(randomIndex);
    String folderName = folder.findElement(By.cssSelector(".sa-data-folders-tree-item-name")).getText().trim();
    if(folder.getAttribute("class").contains("sa-data-folders-tree-item-leaf")) {
      clickFolder(folderName);
      waitForLeafFolderOpen(folderName);
    }
    else if (folder.getAttribute("class").contains("sa-data-folders-tree-item-leaf")) {
      clickFolder(folderName);
      waitForLeafFolderOpen(folderName);
    }
    else {
      clickFolder(folderName);
      waitForFolderOpen(folderName);
      navigateToFoldersACSurvey(folder);
    }
    return new DataByDatasetDropDown(driver,
            folder.findElement(By.cssSelector(".sa-data-folders-tree-item-name")).getText().trim());
  }

  private boolean isFolderExpanded(WebElement folder) {
    return folder.getAttribute("class").contains("sa-data-folders-tree-item-expanded");
  }

  @Step("Navigate to folders")
  private void navigateToFolders(WebElement parentFolderElement) {
    String folderName = parentFolderElement.findElement(By.cssSelector(".sa-data-folders-tree-item-name")).getText().trim();
    if(!isFolderExpanded(parentFolderElement)) {
      logger.debug("Click on the folder: " + folderName);
      Actions actions = new Actions(driver);
      actions.moveToElement(parentFolderElement);
      actions.perform();
      clickFolder(folderName);
      //waitForFolderOpen(folderName);
    }
    waitForElementToAppear(parentFolderElement.findElement(By.xpath("//span[contains(@class,'sa-data-folders-tree-item-expander')]/../.."))
            ,"Parent folder root is not displayed");
    WebElement parentFolderRoot = parentFolderElement.findElement(By.xpath("//span[contains(@class,'sa-data-folders-tree-item-expander')]/../.."));
    waitForAllElementsToAppear(parentFolderRoot.findElements(By.xpath(".//ul//li//button")),"Root folder elements are not displayed");
    List<WebElement> folderList = parentFolderRoot.findElements(By.xpath(".//ul//li//button"));
    int randomIndex = new Random().nextInt(folderList.size());
    WebElement childFolder = folderList.get(randomIndex);
    //waitForAttibuteToDisappear(childFolder, By.cssSelector(".sa-data-folders-tree-item-loading"), "class", "sa-data-folders-tree-item-loading");
    folderName = childFolder.findElement(By.cssSelector(".sa-data-folders-tree-item-name")).getText().trim();

    if(!childFolder.getAttribute("class").contains("sa-data-folders-tree-item-leaf")) {
      navigateToFolders(childFolder);
    }
    else {
      logger.info("Leaf folder: " + folderName);
      waitForElementToBeClickable(childFolder, "Element is not clickable").click();
      waitForLeafFolderOpen(folderName);
      sleep(1800);
    }
  }

  @Step("Navigate to folders B")
  private void navigateToFoldersB(WebElement parentFolderElement) {
    // Get folder name
    String folderName = parentFolderElement.findElement(By.cssSelector(".sa-data-folders-tree-item-name")).getText().trim();
    System.out.println("Found parent folder: " + folderName);
    if (dataFolderPath.isEmpty()) {dataFolderPath = folderName;};

    // Check if folder is expanded
    if(!isFolderExpanded(parentFolderElement)) {
      System.out.println("Folder is not expanded: " + folderName);
      logger.debug("Click on the folder: " + folderName);

      // Move to folder element
      Actions actions = new Actions(driver);
      System.out.println("Moving to the folder: " + folderName);
      actions.moveToElement(parentFolderElement);
      actions.perform();

      // Click on the folder to expand it
      System.out.println("Clicking on the folder: " + folderName);
      clickFolder(folderName);

      // Wait for the folder to open (commented out, but you can add it)
      // waitForFolderOpen(folderName);
    }

    // Wait for parent folder root element to appear
//    System.out.println("Waiting for the parent folder root element to appear. dataFolderPath: "+dataFolderPath);
//    waitForElementToAppear(parentFolderElement.findElement(By.xpath("//*[@data-folder-path=\"" + dataFolderPath + "\"]")),
//            "Parent folder root is not displayed");
    sleep(1000);

    WebElement parentFolderRoot = driver.findElement(By.xpath("//*[@data-folder-path=\"" + formatXPathString(dataFolderPath) + "\"]"));


    waitForAllElementsToAppear(parentFolderRoot.findElements(By.xpath(".//ul//li//button")), "Root folder elements are not displayed");
    // Get all child folders under the parent folder
    List<WebElement>  folderList = parentFolderRoot.findElements(By.xpath(".//ul//li//button"));



    // Wait for all folder elements under the parent folder root to appear
    System.out.println("Number of child folders found: " + folderList.size());

    // Select a random child folder
    int randomIndex = new Random().nextInt(folderList.size());
    WebElement childFolder = folderList.get(randomIndex);
    System.out.println("Randomly selected child folder: " + randomIndex);

    // Get the name of the child folder
    folderName = childFolder.findElement(By.cssSelector(".sa-data-folders-tree-item-name")).getText().trim();
    System.out.println("Child folder name: " + folderName);

    // Check if the child folder is a leaf node
    if(!childFolder.getAttribute("class").contains("sa-data-folders-tree-item-leaf")) {
      System.out.println("The folder is not a leaf. Recursively navigating to subfolders: " + folderName);
      dataFolderPath = dataFolderPath + " » " + folderName;
      navigateToFoldersB(childFolder); // Recursively navigate further if it's not a leaf folder
    } else {
      dataFolderPath = "";
      System.out.println("Leaf folder found: " + folderName);
      logger.info("Leaf folder: " + folderName);

      // Wait for the element to be clickable
      System.out.println("Waiting for the leaf folder to be clickable: " + folderName);
      waitForElementToBeClickable(childFolder, "Element is not clickable").click();

      // Wait for the leaf folder to open
      System.out.println("Waiting for the leaf folder to open: " + folderName);
      waitForLeafFolderOpen(folderName);

      // Adding a delay (you could replace this with a smart wait)
      System.out.println("Sleeping for 1800ms after the leaf folder opens.");
      sleep(1800);
    }
  }


  @Step("Navigate to folders For Lots Of data Crosstab New")
  private void navigateToFoldersACSurvey(WebElement parentFolderElement) {
    String folderName = parentFolderElement.findElement(By.cssSelector(".sa-data-folders-tree-item-name")).getText().trim();
    if(!isFolderExpanded(parentFolderElement)) {
      logger.debug("Click on the folder: " + folderName);
      Actions actions = new Actions(driver);
      actions.moveToElement(parentFolderElement);
      actions.perform();
      clickFolder(folderName);
      //waitForFolderOpen(folderName);
    }
    waitForElementToAppear(parentFolderElement.findElement(By.xpath("//span[contains(@class,'sa-data-folders-tree-item-expander')]/../.."))
            ,"Parent folder root is not displayed");
    WebElement parentFolderRoot = parentFolderElement.findElement(By.xpath("//span[contains(@class,'sa-data-folders-tree-item-expander')]/../.."));
    waitForAllElementsToAppear(parentFolderRoot.findElements(By.cssSelector("ul>li>button")),"Root folder elements are not displayed");
    List<WebElement> folderList = parentFolderRoot.findElements(By.cssSelector("ul>li>button"));
    int randomIndex = new Random().nextInt(folderList.size());
    WebElement childFolder = folderList.get(randomIndex);
    //waitForAttibuteToDisappear(childFolder, By.cssSelector(".sa-data-folders-tree-item-loading"), "class", "sa-data-folders-tree-item-loading");
    folderName = childFolder.findElement(By.cssSelector(".sa-data-folders-tree-item-name")).getText().trim();

    if(!childFolder.getAttribute("class").contains("sa-data-folders-tree-item-leaf")) {
      navigateToFolders(childFolder);
    }
    else {
      logger.info("Leaf folder: " + folderName);
      waitForElementToBeClickable(childFolder, "Element is not clickable").click();
      waitForLeafFolderOpen(folderName);
      sleep(1800);
    }
  }


  private void waitForLoad(String folderName) {
    if (folderName != null) {
      WebElement folder = root
          .findElement(By.xpath("//button[contains(@class, 'sa-data-folders-tree-item')][.//span[text()=\"" + folderName + "\"]]"));
      // folder contains folders and it is not expanded
      if (folder.getAttribute("class").contains("sa-data-folders-tree-item-expandable")) {
        waitForFolderOpen(folderName);
      } else {
        // folder contains data variables
        waitForLeafFolderOpen(folderName);
      }
    }
  }

  private void waitForFolderOpen(String folderName) {
    logger.trace("wait for folder to open: " + folderName);
    if(root.findElement(By.xpath("//button[contains(@class, 'sa-data-folders-tree-item')][.//span[text()=\"" + folderName + "\"]]")).getAttribute("class").contains("sa-data-folders-tree-item-expandable")) {
    	
	    if(!root.findElement(By.xpath("//button[contains(@class, 'sa-data-folders-tree-item')][.//span[text()=\"" + folderName + "\"]]")).getAttribute("class").contains("sa-data-folders-tree-item-expanded")) {
	    	waitForLoad(folderName);
	    }
    waitForAttibuteToContain(root,
        By.xpath("//button[contains(@class, 'sa-data-folders-tree-item')][.//span[text()=\"" + folderName + "\"]]"),
        "class", "sa-data-folders-tree-item-expanded");
    } else {
      waitForAttibuteToContain(root,
          By.xpath("//button[contains(@class, 'sa-data-folders-tree-item')][.//span[text()=\""
              + folderName + "\"]]"),
          "class", "sa-data-folders-tree-item-selected");
    }
  }

  private void waitForLeafFolderOpen(String folderName) {
    logger.trace("wait for folder to open: " + folderName);
    root.findElement(By.xpath(".//button[contains(@class, 'sa-data-folders-tree-item')][.//span[text()=\""
            + folderName + "\"]]")).click();
    waitForAttibuteToContain(root,
        By.xpath(".//button[contains(@class, 'sa-data-folders-tree-item')][.//span[text()=\""
            + folderName + "\"]]"),
        "class", "sa-data-folders-tree-item-selected");
    new DatasetSearchResultPanel(driver);
  }
  
  public List<String> getOpenedFolderPath() {
    List<String> path = root.findElements(By.cssSelector(".sa-data-folders-tree-item-expanded .sa-data-folders-tree-item-name"))
        .stream().map(folder -> folder.getText().trim()).collect(Collectors.toList());
    if(!root.findElement(By.cssSelector(".sa-data-folders-tree-item-selected")).getAttribute("class").contains("sa-data-folders-tree-item-expanded")) {
      path.add(root.findElement(By.cssSelector(".sa-data-folders-tree-item-selected .sa-data-folders-tree-item-name")).getText().trim());
    }
    return path;
  }
}
