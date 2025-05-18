package net.simplyanalytics.tests.view.edit;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import net.simplyanalytics.constants.EditViewWarning;
import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.LocationType;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditScatterPlotPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.pages.main.views.ScatterPlotPage;
import net.simplyanalytics.pageobjects.sections.toolbar.DropdownToolbar;
import net.simplyanalytics.pageobjects.sections.view.editview.containers.CheckboxScatterPlotContainerPanel;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

public class EditScatterPlotTests extends TestBase {

  private ViewType viewType = ViewType.SCATTER_PLOT;
  private ScatterPlotPage scatterPlotPage;
  
  @Before
  public void createProjectWithMapView() {
    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
    SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
        InstitutionUser.PASSWORD);
    WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
    NewProjectLocationWindow createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();
    MapPage mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.LOS_ANGELES_CA_CITY);

    mapPage.getLdbSection().getLocationsTab().voidChooseLocation(Location.CHICAGO_IL_CITY);
    mapPage.getLdbSection().getLocationsTab().voidChooseLocation(Location.MIAMI_FL_CITY);

    EditScatterPlotPage editScatterPlotPage = (EditScatterPlotPage) mapPage.getViewChooserSection().clickNewView().getActiveView().clickCreate(viewType);
    scatterPlotPage = (ScatterPlotPage) editScatterPlotPage.clickDone();
  }
  
  @Test
  public void testChangeActiveLocation() {
    
    verificationStep("Verify that active location is " + Location.LOS_ANGELES_CA_CITY.getName());
    Assert.assertEquals(Location.LOS_ANGELES_CA_CITY.getName() + " should be active", 
        Location.LOS_ANGELES_CA_CITY, scatterPlotPage.getToolbar().getActiveLocation());
    
    List<Location> locations = new ArrayList<>();
    locations.add(Location.LOS_ANGELES_CA_CITY);
    locations.add(Location.USA);
    locations.add(Location.CHICAGO_IL_CITY);
    locations.add(Location.MIAMI_FL_CITY);

    DropdownToolbar dropdownToolbar = scatterPlotPage.getToolbar().openLocationMenu();
    List<Location> actualLocations = dropdownToolbar.getLocationList();
    dropdownToolbar.clickonLocation(Location.USA);
    
    verificationStep("Verify that location list is expected");
    Assert.assertTrue("Location list is not the expected", 
        locations.equals(actualLocations));
    
    verificationStep("Verify that active location has changed");
    Assert.assertEquals("Active location should be changed", Location.USA, scatterPlotPage.getToolbar().getActiveLocation());
    
    scatterPlotPage.getToolbar().openLocationMenu().clickonLocation(Location.CHICAGO_IL_CITY);
    
    verificationStep("Verify that active location has changed");
    Assert.assertEquals("Active location should be changed", Location.CHICAGO_IL_CITY, scatterPlotPage.getToolbar().getActiveLocation());
    
    scatterPlotPage.getToolbar().openLocationMenu().clickonLocation(Location.MIAMI_FL_CITY);
    
    verificationStep("Verify that active location has changed");
    Assert.assertEquals("Active location should be changed", Location.MIAMI_FL_CITY, scatterPlotPage.getToolbar().getActiveLocation());
  }
  
  @Test
  public void testChangeActiveLocationType() {
    
    verificationStep("Verify that active location type is " + LocationType.ZIP_CODE.getPluralName());
    Assert.assertEquals(LocationType.ZIP_CODE.getPluralName() + " should be active", 
        LocationType.ZIP_CODE, scatterPlotPage.getToolbar().getActiveLocationType());
    
    List<LocationType> locationTypes = new ArrayList<>();
    locationTypes.add(LocationType.CITY);
    locationTypes.add(LocationType.ZIP_CODE);
    locationTypes.add(LocationType.CENSUS_TRACT);
    locationTypes.add(LocationType.BLOCK_GROUP);

    DropdownToolbar dropdownToolbar = scatterPlotPage.getToolbar().clickFilterByLocationTypeListMenu();
    List<LocationType> actualLocationTypes = dropdownToolbar.getLocationTypeList();
    dropdownToolbar.clickLocationType(LocationType.CITY);
    
    verificationStep("Verify that location type list is the expected");
    Assert.assertTrue("Location list is not the expected", 
        locationTypes.equals(actualLocationTypes));
    
    verificationStep("Verify that active location type has changed");
    Assert.assertEquals("Active location type should be changed", LocationType.CITY, scatterPlotPage.getToolbar().getActiveLocationType());
    
    scatterPlotPage.getToolbar().clickFilterByLocationTypeListMenu().clickLocationType(LocationType.CENSUS_TRACT);
    
    verificationStep("Verify that active location type has changed");
    Assert.assertEquals("Active location type should be changed", LocationType.CENSUS_TRACT, scatterPlotPage.getToolbar().getActiveLocationType());
    
    scatterPlotPage.getToolbar().clickFilterByLocationTypeListMenu().clickLocationType(LocationType.BLOCK_GROUP);
    
    verificationStep("Verify that active location type has changed");
    Assert.assertEquals("Active location type should be changed", LocationType.BLOCK_GROUP, scatterPlotPage.getToolbar().getActiveLocationType());
    }
  
  @Deprecated 
  public void testClearButton() {
    EditScatterPlotPage editScatterPlotPage = (EditScatterPlotPage) scatterPlotPage.getToolbar().clickViewActions().clickEditView();
    
    CheckboxScatterPlotContainerPanel checkboxContainerPanel = editScatterPlotPage.getActiveView().getDataPanel();
    
    verificationStep("Verify that x-Axis checkbox with index: " + 0 + " selected");
    Assert.assertTrue("x-Axis checkbox with index: " + 0 + " should be selected", checkboxContainerPanel.isXAxisCheckboxSelected(0));
    
    verificationStep("Verify that y-Axis checkbox with index: " + 1 + " selected");
    Assert.assertTrue("y-Axis checkbox with index: " + 1 + " should be selected", checkboxContainerPanel.isYAxisCheckboxSelected(1));
    
    scatterPlotPage = (ScatterPlotPage) editScatterPlotPage.clickDone();
    
    editScatterPlotPage = (EditScatterPlotPage) scatterPlotPage.getToolbar().clickViewActions().clickEditView();
    checkboxContainerPanel = editScatterPlotPage.getActiveView().getDataPanel();
    checkboxContainerPanel.clickClear();
    
    String checkedXAxis = checkboxContainerPanel.getXAxisCheckedData();
    String checkedYAxis = checkboxContainerPanel.getYAxisCheckedData();
    
    verificationStep("Verify that no x-Axis checkboxes is checked");
    Assert.assertTrue("x-Axis for some data is still checked", checkedXAxis.equals(""));
    
    verificationStep("Verify that no y-Axis checkbox is checked");
    Assert.assertTrue("y-Axis for some data is still checked", checkedYAxis.equals(""));
    
    verificationStep("Verify that the \"" + EditViewWarning.SELECT_DATA_VARIABLE_SCATTER_PLOT + "\" error appears");
    Assert.assertTrue("An errors message should appear",
        editScatterPlotPage.getActiveView().isErrorMessageDisplayed());
    Assert.assertEquals("The errors message is not the expected", EditViewWarning.SELECT_DATA_VARIABLE_SCATTER_PLOT,
        editScatterPlotPage.getActiveView().getErrorMessage());
    
    checkboxContainerPanel.clickRowDataItem(1);
    
    checkboxContainerPanel.clickColumnDataItem(0);
    
    checkedXAxis = checkboxContainerPanel.getXAxisCheckedData();
    checkedYAxis = checkboxContainerPanel.getYAxisCheckedData();
    
    verificationStep("Verify that x-Axis checkbox with index: " + 1 + " selected");
    Assert.assertTrue("x-Axis checkbox with index: " + 1 + " should be selected", checkboxContainerPanel.isXAxisCheckboxSelected(1));
    
    verificationStep("Verify that y-Axis checkbox with index: " + 0 + " selected");
    Assert.assertTrue("y-Axis checkbox with index: " + 0 + " should be selected", checkboxContainerPanel.isYAxisCheckboxSelected(0));
  }
  
}
