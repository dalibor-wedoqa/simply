package net.simplyanalytics.core;

import java.io.File;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.parallel.ConcurrentTestRunner;
import net.simplyanalytics.core.rules.ConsoleLogRule;
import net.simplyanalytics.core.rules.DocumentationRule;
import net.simplyanalytics.core.rules.KillDriverRule;
import net.simplyanalytics.core.rules.LoggerRule;
import net.simplyanalytics.core.rules.LogoutRule;
import net.simplyanalytics.core.rules.RetryRule;
import net.simplyanalytics.core.rules.ScreenshotRule;
import net.simplyanalytics.enums.CategoryData;
import net.simplyanalytics.enums.DMAsLocation;
import net.simplyanalytics.enums.DataVariable;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.Page;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.NewViewPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditCrosstabPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditScarboroughCrosstabPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.StartBySelectingScarboroughPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.pages.projectsettings.ProjectSettingsPage;
import net.simplyanalytics.pageobjects.sections.ldb.data.DataByCategoryPanel;
import net.simplyanalytics.pageobjects.sections.ldb.data.bycategory.DataByCategoryDropwDown;
import net.simplyanalytics.pageobjects.sections.ldb.locations.ScarboroughLocationsTab;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;
import net.simplyanalytics.utils.ActiveNodeDeterminer;

import org.apache.commons.text.RandomStringGenerator;
import org.junit.Rule;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;
import org.junit.runners.model.Statement;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.qameta.allure.Step;

/**
 * . Main test framework class
 * 
 * @author WeDoQA
 *
 */
@RunWith(ConcurrentTestRunner.class)
public class TestBase {

//TODO delete when refactorisation is completed
	/*
	 * protected static final DataVariable medianDefaultDataVariable =
	 * DataVariable.MEDIAN_HOUSEHOLD_INCOME_2018;
	 * 
	 * //default data variables should be in the correct order protected static
	 * final List<DataVariable> defaultDataVariables = Arrays.asList( DataVariable.
	 * PERCENT_EDUCATION_ATTAINMENT_COLLEGE_MASTER_DOCTORATE_DEGREE_OR_HIGHER_2018,
	 * medianDefaultDataVariable,
	 * DataVariable.PERCENT_HOUSING_YEAR_STRUCTURE_BUILT_1939_OR_EARLIER_2018);
	 */

	// Set your per-test timeout here
//	private final long TIMEOUT_SECONDS = 3600;

//	@Rule
//	public TestRule safeTimeout = (base, description) -> new Statement() {
//		@Override
//		public void evaluate() throws Throwable {
//			Thread testThread = Thread.currentThread();
//
//			// ⏱️ Watchdog to forcibly quit browser on timeout
//			Thread watchdog = new Thread(() -> {
//				try {
//					Thread.sleep(TIMEOUT_SECONDS * 1000);
//					System.err.println("⏰ Timeout reached. Cleaning up WebDriver.");
//					if (driver != null) {
//						driver.quit();
//					}
//					testThread.interrupt(); // Optionally interrupt the test thread
//				} catch (InterruptedException ignored) {}
//			});
//
//			watchdog.setDaemon(true);
//			watchdog.start();
//
//			try {
//				base.evaluate(); // Run the actual test
//			} finally {
//				if (driver != null) {
//					System.out.println("🧼 Cleaning up WebDriver in finally.");
//					driver.quit();
//				}
//			}
//		}
//	};

	protected static final DataVariable medianDefaultDataVariable = DataVariable.MEDIAN_HOUSEHOLD_INCOME_2024;
	protected static final DataVariable percentDefaultDataVariable = DataVariable.PERCENT_EDUCATION_ATTAINMENT_COLLEGE_MASTER_DOCTORATE_DEGREE_OR_HIGHER_2024;

	//NB Code Start:
	protected static final DataVariable countDefaultDataVariable = DataVariable.HASHTAG_TOTAL_POPULATION_DEFAULT_2024;
	@FindBy(css = ".sa-view-chooser-item:nth-of-type(3)")
	private WebElement viewChooserItem;
	//NB Code End;

	// default data variables should be in the correct order
	protected static final List<DataVariable> defaultDataVariables = Arrays.asList(
			DataVariable.PERCENT_EDUCATION_ATTAINMENT_COLLEGE_MASTER_DOCTORATE_DEGREE_OR_HIGHER_2024,
			medianDefaultDataVariable);

	protected static final DataVariable firstDefaultDataVariable = defaultDataVariables.get(0);

	public static Logger logger = LoggerFactory.getLogger(TestBase.class);

	private boolean retryFlag = false;

	protected static DriverConfiguration driverConfiguration;

	static int retry;

	static {
		driverConfiguration = new DriverConfiguration();

		if (System.getProperty("retry") != null) {
			retry = Integer.parseInt(System.getProperty("retry"));
		} else {
			retry = 1;
		}
		logger.debug("Retry: " + retry);

	}

	private static Lock lock;

	protected NewViewPage newViewPage;

	static {
		lock = new ReentrantLock();
	}

	protected WebDriver driver;

	@Rule
	public RuleChain allRules = setRules(driverConfiguration.getBrowser());

	public static final int DEFAULT_WAIT = 10;

	protected RandomStringGenerator randomNumericStringGenerator = new RandomStringGenerator.Builder()
			.withinRange('0', '9').build();

	/**
	 * .
	 * 
	 * 
	 * 
	 *
	 */

	public TestBase() {

	}

	/*
	 * There is problem with the firefox and the ConsoleLogRule must be disabled.
	 */
	public RuleChain setRules(String browser) {

		RuleChain rule = null;

		if (browser == "chrome") {
			rule = RuleChain.outerRule(new RetryRule(retry, this))
					.around(new KillDriverRule(this))
					.around(new LogoutRule(this))
					.around(new DocumentationRule())
					.around(new LoggerRule())
					.around(new ConsoleLogRule(this))
					.around(new ScreenshotRule(this));
		} else if (browser == "edge") {
			rule = RuleChain.outerRule(new RetryRule(retry, this))
					.around(new KillDriverRule(this))
					.around(new LogoutRule(this))
					.around(new DocumentationRule())
					.around(new LoggerRule())
					.around(new ConsoleLogRule(this))
					.around(new ScreenshotRule(this));
		} else if (browser == "firefox") {
			rule = RuleChain.outerRule(new RetryRule(retry, this))
					.around(new KillDriverRule(this))
					.around(new LogoutRule(this))
					.around(new DocumentationRule())
					.around(new LoggerRule())
					.around(new ScreenshotRule(this));
		}

		return rule;
	}

	public void initDriver(boolean headless) {
		driver = DriverFactory.getInstance().createDriver(driverConfiguration, headless);

		driver.manage().timeouts().implicitlyWait(DEFAULT_WAIT, TimeUnit.SECONDS);
		driver.manage().timeouts().setScriptTimeout(5, TimeUnit.SECONDS);
	}

	@Step("{0}")
	public void allureStep(String step) {
		logger.debug(step);
	}

	public void lockUser() {
		lock.lock();
		logger.trace("Lock the user");
	}

	public void unlockUser() {
		logger.trace("Unlock the user");
		lock.unlock();
	}

	@Step("{0}")
	public void verificationStep(String step) {
		logger.info(step);
	}

	//Original code for createOneViewFromEachType
	protected NewViewPage createOneViewFromEachType() {
		AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
		SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION, InstitutionUser.PASSWORD);
		WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
		NewProjectLocationWindow createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();
		final MapPage mapPage = createNewProjectWindow
				.createNewProjectWithLocationAndDefaultVariables(Location.LOS_ANGELES_CA_CITY);

		List<ViewType> views = new ArrayList<>();
		views.addAll(Arrays.asList(ViewType.values()));

		// Comparison Report, Quick Report, Map and Ranking added by default
		views.remove(ViewType.COMPARISON_REPORT);
		views.remove(ViewType.QUICK_REPORT);
		views.remove(ViewType.MAP);
		views.remove(ViewType.RANKING);

		mapPage.getLdbSection().getBusinessTab().addRandomBusinesses();
		views.remove(ViewType.BUSINESSES);
		views.remove(ViewType.CROSSTAB_TABLE);
		views.remove(ViewType.SCARBOROUGH_CROSSTAB_TABLE);

		mapPage.getHeaderSection().clickProjectSettings().getProjectSettingsHeader().
				clickGeneralSettingsButton().getEnableHistoricalViewsPanel().switchHistoricalViewsToggle();

		NewViewPage newViewPage = mapPage.getViewChooserSection().clickNewView();
		for (ViewType viewType : views) {
			/*newViewPage = newViewPage.getActiveView().clickCreate(viewType).clickDone().getViewChooserSection()
					.clickNewView();*/
//			if (viewType.getPage().getViewPageName().contains("Simmons")||viewType.getPage().getViewPageName().contains("Scarborough")){
//
//			}else
			newViewPage = newViewPage.getActiveView().clickCreate(viewType).clickDone().getViewChooserSection().clickNewView();
		}
		// Create Crosstab View
		EditCrosstabPage editCrosstabPage = (EditCrosstabPage) newViewPage.getActiveView()
				.clickCreate(ViewType.CROSSTAB_TABLE);
		DataByCategoryPanel dataByCategoryPanel = (DataByCategoryPanel) editCrosstabPage.getLdbSection().clickData()
				.getBrowsePanel();
		DataByCategoryDropwDown dataByCategoryDropwDown = dataByCategoryPanel
				.clickOnACategoryData(CategoryData.AGE);
		for (int i = 0; i < 3; i++) {
			dataByCategoryDropwDown.clickOnARandomDataResult();
		}
		editCrosstabPage = (EditCrosstabPage) dataByCategoryDropwDown.clickClose(Page.EDIT_SIMMONS_CROSSTAB_PAGE);
		dataByCategoryDropwDown = dataByCategoryPanel.clickOnACategoryData(CategoryData.TECHNOLOGY);
		for (int i = 0; i < 3; i++) {
			dataByCategoryDropwDown.clickOnARandomDataResult();
		}

		editCrosstabPage = (EditCrosstabPage) dataByCategoryDropwDown.clickClose(Page.EDIT_SIMMONS_CROSSTAB_PAGE);

		newViewPage = editCrosstabPage.clickDone().getViewChooserSection().clickNewView();

		// Create Scarborough Crosstab View

		newViewPage.getActiveView().clickHistoricalViewsCheckbox();
		StartBySelectingScarboroughPage startBySelectingPage = (StartBySelectingScarboroughPage) newViewPage
				.getActiveView().clickCreate(ViewType.SCARBOROUGH_CROSSTAB_TABLE);
		EditScarboroughCrosstabPage editScarboroughCrosstabPage = ((ScarboroughLocationsTab) startBySelectingPage
				.getLdbSection().getLocationsTab()).clickOnTheDMALocation(DMAsLocation.LOS_ANGELES);
		dataByCategoryPanel = (DataByCategoryPanel) editScarboroughCrosstabPage.getLdbSection().clickData()
				.getBrowsePanel();
		dataByCategoryDropwDown = dataByCategoryPanel.clickOnACategoryData(CategoryData.AGE);
		for (int i = 0; i < 3; i++) {
			dataByCategoryDropwDown.clickOnARandomDataResult();
		}

		editScarboroughCrosstabPage = (EditScarboroughCrosstabPage) dataByCategoryDropwDown
				.clickClose(Page.EDIT_SCARBOROUGH_CROSSTAB_PAGE);

		dataByCategoryDropwDown = dataByCategoryPanel.clickOnACategoryData(CategoryData.CONSUMER_BEHAVIOR);
		for (int i = 0; i < 3; i++) {
			dataByCategoryDropwDown.clickOnARandomDataResult();
		}

		editScarboroughCrosstabPage = (EditScarboroughCrosstabPage) dataByCategoryDropwDown
				.clickClose(Page.EDIT_SCARBOROUGH_CROSSTAB_PAGE);

		newViewPage = editScarboroughCrosstabPage.clickDone().getViewChooserSection().clickNewView();

		return newViewPage;
	}


	/*
	//NB start: Added code for creating one of each views
	*/
	protected NewViewPage createOneViewFromEachTypeFix() {

		AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
		SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION, InstitutionUser.PASSWORD);
		WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
		NewProjectLocationWindow createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();
		final MapPage mapPage = createNewProjectWindow.createNewProjectWithLocationAndDefaultVariables(Location.LOS_ANGELES_CA_CITY);
		//ProjectSettingsPage projectSettingsPage = mapPage.getHeaderSection().clickProjectSettings();
		//projectSettingsPage.getProjectSettingsHeader().clickGeneralSettingsButton().getEnableHistoricalViewsPanel().switchHistoricalViewsToggle();
		//viewChooserItem.click();
		//WebElement element = driver.findElement(By.cssSelector(".sa-view-chooser-item:nth-of-type(3)"));
		//element.click();

		List<ViewType> views = new ArrayList<>();
		views.addAll(Arrays.asList(ViewType.values()));

		// Comparison Report, Quick Report, Map and Ranking added by default
		views.remove(ViewType.COMPARISON_REPORT);
		views.remove(ViewType.QUICK_REPORT);
		views.remove(ViewType.MAP);
		views.remove(ViewType.RANKING);

		mapPage.getLdbSection().getBusinessTab().addRandomBusinesses();
		driver.manage().window().fullscreen();
		views.remove(ViewType.BUSINESSES);

        newViewPage = mapPage.getViewChooserSection().clickNewView();

		WebDriverWait wait = new WebDriverWait(driver, 10); // 10 seconds timeout

		for (ViewType viewType : views) {
			if (viewType == ViewType.CROSSTAB_TABLE) {
				EditCrosstabPage editCrosstabPage = (EditCrosstabPage) newViewPage.getActiveView().clickCreate(viewType);
				DataByCategoryPanel dataByCategoryPanel = (DataByCategoryPanel) editCrosstabPage.getLdbSection().clickData().getBrowsePanel();
				DataByCategoryDropwDown dataByCategoryDropwDown = dataByCategoryPanel.clickOnACategoryData(CategoryData.AGE);

				for (int i = 0; i < 3; i++) {
					dataByCategoryDropwDown.clickOnARandomDataResult();
				}
				editCrosstabPage = (EditCrosstabPage) dataByCategoryDropwDown.clickClose(Page.EDIT_SIMMONS_CROSSTAB_PAGE);

				dataByCategoryDropwDown = dataByCategoryPanel.clickOnACategoryData(CategoryData.TECHNOLOGY);
				for (int i = 0; i < 3; i++) {
					dataByCategoryDropwDown.clickOnARandomDataResult();
				}
				editCrosstabPage = (EditCrosstabPage) dataByCategoryDropwDown.clickClose(Page.EDIT_SIMMONS_CROSSTAB_PAGE);

				By enabledDoneButton = By.cssSelector(".sa-button.sa-edit-header-done-btn.x-unselectable.sa-button-primary.x-border-box");
				By disabledDoneButton = By.cssSelector(".sa-button.sa-edit-header-done-btn.x-unselectable.sa-button-primary.x-border-box.x-item-disabled");

				try {
					wait.until(ExpectedConditions.or(
							ExpectedConditions.presenceOfElementLocated(enabledDoneButton),
							ExpectedConditions.presenceOfElementLocated(disabledDoneButton)
					));

					if (driver.findElements(disabledDoneButton).size() > 0) {
						logger.debug("No row elements are selected, selecting random row elements");
						List<WebElement> rowElements = driver.findElements(By.cssSelector(".sa-crosstab-attributes-panel-item-row-button-wrap>.sa-check-button"));
						Random rand = new Random();
						for (int i = 0; i < 2; i++) {
							rowElements.get(rand.nextInt(rowElements.size())).click();
						}
					} else {
						logger.debug("Row elements selected, clicking on the Done button");
					}
					editCrosstabPage.clickDone();
				} catch (TimeoutException e) {
					logger.debug("Timed out waiting for the Done button state.");
				}

				newViewPage = newViewPage.getViewChooserSection().clickNewView();

			} else if (viewType == ViewType.SCARBOROUGH_CROSSTAB_TABLE) {
				StartBySelectingScarboroughPage startBySelectingPage = (StartBySelectingScarboroughPage) newViewPage.getActiveView().clickCreate(viewType);
				EditScarboroughCrosstabPage editScarboroughCrosstabPage = ((ScarboroughLocationsTab) startBySelectingPage.getLdbSection().getLocationsTab()).clickOnTheDMALocation(DMAsLocation.LOS_ANGELES);
				DataByCategoryPanel dataByCategoryPanel = (DataByCategoryPanel) editScarboroughCrosstabPage.getLdbSection().clickData().getBrowsePanel();
				DataByCategoryDropwDown dataByCategoryDropwDown = dataByCategoryPanel.clickOnACategoryData(CategoryData.AGE);

				for (int i = 0; i < 3; i++) {
					dataByCategoryDropwDown.clickOnARandomDataResult();
				}
				editScarboroughCrosstabPage = (EditScarboroughCrosstabPage) dataByCategoryDropwDown.clickClose(Page.EDIT_SCARBOROUGH_CROSSTAB_PAGE);

				dataByCategoryDropwDown = dataByCategoryPanel.clickOnACategoryData(CategoryData.CONSUMER_BEHAVIOR);
				for (int i = 0; i < 3; i++) {
					dataByCategoryDropwDown.clickOnARandomDataResult();
				}
				editScarboroughCrosstabPage = (EditScarboroughCrosstabPage) dataByCategoryDropwDown.clickClose(Page.EDIT_SCARBOROUGH_CROSSTAB_PAGE);

				By enabledDoneButton = By.cssSelector(".sa-button.sa-edit-header-done-btn.x-unselectable.sa-button-primary.x-border-box");
				By disabledDoneButton = By.cssSelector(".sa-button.sa-edit-header-done-btn.x-unselectable.sa-button-primary.x-border-box.x-item-disabled");

				try {
					wait.until(ExpectedConditions.or(
							ExpectedConditions.presenceOfElementLocated(enabledDoneButton),
							ExpectedConditions.presenceOfElementLocated(disabledDoneButton)
					));

					if (driver.findElements(disabledDoneButton).size() > 0) {
						logger.debug("No row elements are selected, selecting random row elements");
						List<WebElement> rowElements = driver.findElements(By.cssSelector(".sa-crosstab-attributes-panel-item-row-button-wrap>.sa-check-button"));
						Random rand = new Random();
						for (int i = 0; i < 2; i++) {
							rowElements.get(rand.nextInt(rowElements.size())).click();
						}
					} else {
						logger.debug("Row elements selected, clicking on the Done button");
					}
					editScarboroughCrosstabPage.clickDone();
				} catch (TimeoutException e) {
					logger.debug("Timed out waiting for the Done button state.");
				}

				newViewPage = newViewPage.getViewChooserSection().clickNewView();

			} else {
				newViewPage = newViewPage.getActiveView().clickCreate(viewType).clickDone().getViewChooserSection().clickNewView();
			}
		}

		return newViewPage;
	}


	//NB End of code

	protected final void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public WebDriver getDriver() {
		return driver;
	}

	public String getDownloadFilePath() {
		switch (driverConfiguration.getTestMode()) {
		case LOCAL:
			return DriverFactory.LOCAL_DOWNLOAD_FILE_PATH_ROOT + File.separator + "driver"
					+ Thread.currentThread().getId();
		case REMOTE:
			String[] address = System.getProperty("gridAddress").split(":");
			ActiveNodeDeterminer nodeDeterminer = new ActiveNodeDeterminer(address[0], Integer.parseInt(address[1]));
			try {
				String downloadPath = File.separator + File.separator
						+ nodeDeterminer.getNodeInfoForSession(((RemoteWebDriver) driver).getSessionId()).getNodeIp()
						+ File.separator + DriverFactory.REMOTE_DOWNLOAD_FILE_NAME + File.separator + "driver"
						+ Thread.currentThread().getId();
				System.out.println("Download address:" + downloadPath);
				return downloadPath;
			} catch (NullPointerException e) {
				logger.error("Download file path was unable to reach. Files are not deleted.");
				return null;
			}

		default:
			throw new Error("The test mode is not supported yet: " + driverConfiguration.getTestMode());
		}
	}

	public static DriverConfiguration getDriverConfiguration() {
		return driverConfiguration;
	}

	public void setRetryFlagTo(boolean bool) {
		retryFlag = bool;
	}

	public boolean getRetryFlag() {
		return retryFlag;
	}

}
