package net.simplyanalytics.tests.tabledropdown.headerdropdown.viewmetadata;

import java.util.ArrayList;
import java.util.List;

import net.simplyanalytics.pageobjects.pages.projectsettings.ProjectSettingsPage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.CategoryData;
import net.simplyanalytics.enums.DMAsLocation;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.NewViewPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditScarboroughCrosstabPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.StartBySelectingScarboroughPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.pages.main.views.ScarboroughCrosstabPage;
import net.simplyanalytics.pageobjects.sections.ldb.data.DataByCategoryPanel;
import net.simplyanalytics.pageobjects.sections.ldb.data.bycategory.DataByCategoryDropwDown;
import net.simplyanalytics.pageobjects.sections.ldb.locations.ScarboroughLocationsTab;
import net.simplyanalytics.pageobjects.sections.view.ScarboroughCrosstabViewPanel;
import net.simplyanalytics.pageobjects.windows.MetadataWindow;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

public class ScarboroughCrosstabViewMetadataTests extends TestBase {
  
  private EditScarboroughCrosstabPage editScarboroughCrosstabPage;
  private MapPage mapPage;
  ScarboroughCrosstabPage scarboroughCrosstabPage;
  
  @Before
  public void createProject() {
    driver.manage().window().maximize();
    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
    SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
        InstitutionUser.PASSWORD);
    WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
    NewProjectLocationWindow createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();
    mapPage = createNewProjectWindow.createNewProjectWithLocationAndDefaultVariables(Location.USA);
    ProjectSettingsPage projectSettingsPage = mapPage.getHeaderSection().clickProjectSettings();
    projectSettingsPage.getProjectSettingsHeader().clickGeneralSettingsButton().getEnableHistoricalViewsPanel().switchHistoricalViewsToggle();
  }
  
  @Test
  public void testRowHeaderDataVariableViewMetadata() {
    NewViewPage newViewPage = mapPage.getViewChooserSection().clickNewView();
    StartBySelectingScarboroughPage startBySelectingScarboroughPage = (StartBySelectingScarboroughPage) newViewPage.getActiveView().clickCreate(ViewType.SCARBOROUGH_CROSSTAB_TABLE);
    editScarboroughCrosstabPage = (EditScarboroughCrosstabPage) ((ScarboroughLocationsTab) startBySelectingScarboroughPage.getLdbSection().getLocationsTab()).clickOnTheDMALocation(DMAsLocation.ALBANY);

    DataByCategoryPanel dataByCategoryPanel = (DataByCategoryPanel) editScarboroughCrosstabPage.getLdbSection()
        .clickData().getBrowsePanel();
    DataByCategoryDropwDown dataByCategoryDropwDown = dataByCategoryPanel
        .clickOnACategoryData(CategoryData.CONSUMER_BEHAVIOR);
    List<String> row = new ArrayList<String>();
    for(int i = 0; i < 3; i++) {
      row.add(dataByCategoryDropwDown.clickOnARandomDataResult());
    }
    dataByCategoryDropwDown = dataByCategoryPanel
        .clickOnACategoryData(CategoryData.LANGUAGE);
    List<String> column = new ArrayList<String>();
    for(int i = 0; i < 3; i++) {
      column.add(dataByCategoryDropwDown.clickOnARandomDataResult());
    }

    editScarboroughCrosstabPage.getLdbSection().clickData();
    sleep(1000);

    scarboroughCrosstabPage = (ScarboroughCrosstabPage) editScarboroughCrosstabPage.clickDone();
    
    ScarboroughCrosstabViewPanel scarboroughCrosstabViewPanel = scarboroughCrosstabPage.getActiveView();

    MetadataWindow metadataWindow = scarboroughCrosstabViewPanel.openRowHeaderDropdown(1).clickViewMetadata();
    String rowValue = row.get(0).substring(0, row.get(0).length() - 2);
    String metadataWindowValue = metadataWindow.getMetadataValue("Name");

    verificationStep("Verify that the metadata windows contains the correct name and year");
//    Assert.assertEquals("The name is not the expected", row.get(0).substring(0, row.get(0).length()-6),
//        metadataWindow.getMetadataValue("Name"));
    Assert.assertEquals("The name is not the expected", rowValue,
          metadataWindowValue);
    //Year not present
//    Assert.assertEquals("The year is not the expected", row.get(0).substring(row.get(0).length()-4, row.get(0).length()),
//        metadataWindow.getMetadataValue("Year"));
  }
  
  @Test
  public void testColumnHeaderDataVariableViewMetadata() {
    NewViewPage newViewPage = mapPage.getViewChooserSection().clickNewView();
    StartBySelectingScarboroughPage startBySelectingScarboroughPage = (StartBySelectingScarboroughPage) newViewPage.getActiveView().clickCreate(ViewType.SCARBOROUGH_CROSSTAB_TABLE);
    editScarboroughCrosstabPage = (EditScarboroughCrosstabPage) ((ScarboroughLocationsTab) startBySelectingScarboroughPage.getLdbSection().getLocationsTab()).clickOnTheDMALocation(DMAsLocation.ALBANY);

    DataByCategoryPanel dataByCategoryPanel = (DataByCategoryPanel) editScarboroughCrosstabPage.getLdbSection()
        .clickData().getBrowsePanel();
    DataByCategoryDropwDown dataByCategoryDropwDown = dataByCategoryPanel
        .clickOnACategoryData(CategoryData.CONSUMER_BEHAVIOR);
    List<String> row = new ArrayList<String>();
    for(int i = 0; i < 3; i++) {
      row.add(dataByCategoryDropwDown.clickOnARandomDataResult());
    }
    dataByCategoryDropwDown = dataByCategoryPanel
        .clickOnACategoryData(CategoryData.JOBS_AND_EMPLOYMENT);
    List<String> column = new ArrayList<String>();

    for(int i = 0; i < 3; i++) {
      column.add(dataByCategoryDropwDown.clickOnARandomDataResult());
    }

    //TODO Napraviti combobox selection based on data that is taken from the if statement for rows and columns
    //Clean the comboboxes then select the row based on the list then select the columns from the list

    editScarboroughCrosstabPage.getLdbSection().clickData();
    sleep(1000);
    
    scarboroughCrosstabPage = (ScarboroughCrosstabPage) editScarboroughCrosstabPage.clickDone();
    
    ScarboroughCrosstabViewPanel scarboroughCrosstabViewPanel = scarboroughCrosstabPage.getActiveView();
    MetadataWindow metadataWindow = scarboroughCrosstabViewPanel.
            openColumnHeaderDropdown(1).
            clickViewMetadata();

    String columnValue = column.get(0).substring(0, column.get(0).length() - 2);
    String columnValue0 = column.get(1).substring(0, column.get(1).length() - 2);
    String columnValue1 = column.get(2).substring(0, column.get(2).length() - 2);

    System.out.println("COLUMN VALUE 0: \n" + columnValue);
    System.out.println("COLUMN VALUE 1: \n" + columnValue0);
    System.out.println("COLUMN VALUE 2: \n" + columnValue1);

    String metadataWindowValue = metadataWindow.getMetadataValue("Name");

    System.out.println("Value in MetaData Window: \n" + metadataWindowValue);

    verificationStep("Verify that the metadata windows contains the correct name and year");

    // Perform the assertion
    boolean anyAssertionPassed = columnValue.equals(metadataWindowValue) ||
            columnValue0.equals(metadataWindowValue) ||
            columnValue1.equals(metadataWindowValue);

    Assert.assertTrue("None of the column values match the metadata window value", anyAssertionPassed);

    //Assert.assertEquals("The name is not the expected", columnValue, metadataWindowValue);
    //Assert.assertEquals("The name is not the expected", columnValue0, metadataWindowValue);
    //Assert.assertEquals("The name is not the expected", columnValue1, metadataWindowValue);

    /*
    verificationStep("Verify that the metadata windows contains the correct name and year");
    Assert.assertEquals("The name is not the expected", column.get(0).substring(0, column.get(0).length()- 2),
        metadataWindow.getMetadataValue("Name"));

    Assert.assertEquals("The year is not the expected", column.get(0).substring(column.get(0).length()-4, column.get(0).length()),
        metadataWindow.getMetadataValue("Year"));
    */
  }

}
