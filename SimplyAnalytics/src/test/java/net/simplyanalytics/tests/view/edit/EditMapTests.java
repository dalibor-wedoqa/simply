package net.simplyanalytics.tests.view.edit;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.DataVariable;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditMapPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.sections.view.editview.containers.RadioButtonContainerPanel;
import net.simplyanalytics.pageobjects.sections.view.map.LegendPanel;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EditMapTests extends TestBase {
  
  private MapPage mapPage;
  private ViewType view = ViewType.MAP;
  private DataVariable original = defaultDataVariables.get(0);
  private final DataVariable edited = defaultDataVariables.get(1);


  
  /**
   * Signing in and creating new project.
   */
  @Before
  public void createProjectWithMapView() {
    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
    SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
        InstitutionUser.PASSWORD);
    WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
    NewProjectLocationWindow createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();
    mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.LOS_ANGELES_CA_CITY);
  }
  
  @Test
  public void testEditDataVariable() {
    
    // verify default active data
    verificationStep("Verify that the default active data variable is present on the legend panel");
    Assert.assertEquals("The default active data is incorrect on the legent panel", original,
        mapPage.getActiveView().getLegend().getActiveData());
    
    verificationStep("Verify that the default active data variable is present in the toolbar");
    Assert.assertEquals("The default active data is incorrect in the toolbar", original,
        mapPage.getToolbar().getActiveDataVariable());
    
    EditMapPage editMapPage = (EditMapPage) mapPage.getViewChooserSection()
        .openViewMenu(view.getDefaultName()).clickEdit();
    
    RadioButtonContainerPanel dataPanel = editMapPage.getActiveView().getDataPanel();
    dataPanel.clickElement(edited.getFullName());
    
    verificationStep("Verify that the data variable become selected");
    Assert.assertEquals("The active data is not the selected", edited.getFullName(),
        dataPanel.getSelectedElement());
    
    mapPage = (MapPage) editMapPage.clickDone();
    
    // verify edited active data
    verificationStep("Verify that the selected data is the active data on the legend panel");
    Assert.assertEquals("The active data is incorrect on the legent panel", edited,
        mapPage.getActiveView().getLegend().getActiveData());
    verificationStep("Verify that the selected data is the active data in the toolbar");
    Assert.assertEquals("The active data is incorrect in the toolbar", edited,
        mapPage.getToolbar().getActiveDataVariable());
  }
  
  @Test
  public void testEditLocation() {
    mapPage = mapPage.getLdbSection().getLocationsTab().chooseLocation(Location.CHICAGO_IL_CITY);
    Location original = Location.CHICAGO_IL_CITY;
    Location edited = Location.LOS_ANGELES_CA_CITY;
    
    // verify default active location
    verificationStep("Verify that the default active location is present in the toolbar");
    Assert.assertEquals("The default active location is incorrect in the toolbar", original,
        mapPage.getToolbar().getActiveLocation());
    
    EditMapPage editMapPage = (EditMapPage) mapPage.getViewChooserSection()
        .openViewMenu(view.getDefaultName()).clickEdit();
    
    RadioButtonContainerPanel dataPanel = editMapPage.getActiveView().getLocationsPanel();
    dataPanel.clickElement(edited.getName());
    
    verificationStep("Verify that the location become selected");
    Assert.assertEquals("The active location is not the selected", edited.getName(),
        dataPanel.getSelectedElement());
    
    mapPage = (MapPage) editMapPage.clickDone();
    
    // verify edited active data
    verificationStep("Verify that the selected location is the active location in the toolbar");
    Assert.assertEquals("The active location is incorrect in the toolbar", edited,
        mapPage.getToolbar().getActiveLocation());
  }
  
  /**
   * . SA-837 SA-672
   */
  @Test
  public void testEditBusiness() {
    final String business1 = mapPage.getLdbSection().getBusinessTab().addRandomBusinesses();
    String business2 = mapPage.getLdbSection().getBusinessTab().addRandomBusinesses();
    
    sleep(500);
    
    LegendPanel legendPanel = mapPage.getActiveView().getLegend();
    // verify active business
    verificationStep("Verify that the second selected business is present in the toolbar");
    Assert.assertEquals("The active business is incorrect on the legent panel", business2,
        legendPanel.getActiveBusiness());
    
    // verify business layout
    verificationStep("Verify that the layout of business elements are in the legend panel");
    Assert.assertTrue(
        "The width of legend panel and the total width of bullet elements are not the expected: "
          + legendPanel.getLegendWidth() + ", " + legendPanel.getSumOfBusinessElementsWidth(),
        // 30px counted for margins, 5px error allowed
        Math.abs(
            legendPanel.getLegendWidth() - legendPanel.getSumOfBusinessElementsWidth() - 30) < 5);
    
    // TODO: SA-672 - we should reopen this ticket and when it's fixed, uncomment
    // height assert
    // verificationStep("Verify that the business element fulfill the reserved
    // height");
    // Assert.assertTrue(
    // "The height of legend panel and the reserved height are not the expected: "
    // + legendPanel.getSpaceHeightForBusiness() + ", " +
    // legendPanel.getBusinessHeight(),
    // // 30px counted for margins, 5px error allowed
    // Math.abs(
    // legendPanel.getSpaceHeightForBusiness() - legendPanel.getBusinessHeight() -
    // 30) < 5);
    
    EditMapPage editMapPage = (EditMapPage) mapPage.getViewChooserSection()
        .openViewMenu(view.getDefaultName()).clickEdit();
    
    RadioButtonContainerPanel businessPanel = editMapPage.getActiveView().getBusinessesPanel();
    businessPanel.clickElement(business1);
    
    verificationStep("Verify that the business become selected");
    Assert.assertEquals("The active location is not the selected", business1,
        businessPanel.getSelectedElement());
    
    mapPage = (MapPage) editMapPage.clickDone();
    legendPanel = mapPage.getActiveView().getLegend();
    
    // verify edited active data
    verificationStep("Verify that the selected business is the active business in the toolbar");
    Assert.assertEquals("The active business is incorrect on the legent panel", business1,
        legendPanel.getActiveBusiness());
    
    // verify business layout
    verificationStep("Verify that the layout of business elements are in the legend panel");
    Assert.assertTrue(
        "The width of legend panel and the total width of bulett elements are not the expected",
        // 30px counted for margins, 5px error allowed
        Math.abs(
            legendPanel.getLegendWidth() - legendPanel.getSumOfBusinessElementsWidth() - 30) < 5);
    
    // TODO: SA-672 - we should reopen this ticket and when it's fixed, uncomment
    // height assert
    // verificationStep("Verify that the business element fulfil the reserved
    // height");
    // Assert.assertTrue("The height of legend panel and the reserved height are not
    // the expected",
    // // 20px counted for margins, 5px error allowed
    // Math.abs(
    // legendPanel.getSpaceHeightForBusiness() - legendPanel.getBusinessHeight() -
    // 20) < 5);
  }
  
  @Test
  public void testNoneDataVariable() {
    String none = "None";
    EditMapPage editMapPage = (EditMapPage) mapPage.getViewChooserSection()
        .openViewMenu(view.getDefaultName()).clickEdit();
    
    RadioButtonContainerPanel dataPanel = editMapPage.getActiveView().getDataPanel();
    dataPanel.clickElement(none);
    
    verificationStep("Verify that the None button is the active data variable");
    Assert.assertEquals("The active data is not the selected", none,
        dataPanel.getSelectedElement());
    
    mapPage = (MapPage) editMapPage.clickDone();
    
    verificationStep("Verify that the legend title not appear");
    Assert.assertFalse("The active data information should be invisible",
        mapPage.getActiveView().getLegend().isLegendTitleDisplayed());
  }
  
  @Test
  public void testNoneLocation() {
    String none = "None";
    
    EditMapPage editMapPage = (EditMapPage) mapPage.getViewChooserSection()
        .openViewMenu(view.getDefaultName()).clickEdit();
    
    RadioButtonContainerPanel dataPanel = editMapPage.getActiveView().getLocationsPanel();
    dataPanel.clickElement(none);
    
    verificationStep("Verify that the None button is the active location");
    Assert.assertEquals("The active location is not the selected", none,
        dataPanel.getSelectedElement());
    
    mapPage = (MapPage) editMapPage.clickDone();
    
    // verify edited active data
    verificationStep("Verify that the location in the toolbar is \"None\"");
    Assert.assertEquals("The active location is incorrect in the toolbar", Location.NONE,
        mapPage.getToolbar().getActiveLocation());
  }
  
  @Test
  public void testNoneBusiness() {
    mapPage.getLdbSection().getBusinessTab().addRandomBusinesses();
    String none = "None";
    
    EditMapPage editMapPage = (EditMapPage) mapPage.getViewChooserSection()
        .openViewMenu(view.getDefaultName()).clickEdit();
    
    RadioButtonContainerPanel businessPanel = editMapPage.getActiveView().getBusinessesPanel();
    businessPanel.clickElement(none);
    
    verificationStep("Verify that the None button is the active business");
    Assert.assertEquals("The active location is not the selected", none,
        businessPanel.getSelectedElement());
    
    mapPage = (MapPage) editMapPage.clickDone();
    
    // verify edited active data
    verificationStep("Verify that in the legend panel no active business present");
    Assert.assertFalse("The active business information should be invisible",
        mapPage.getActiveView().getLegend().isLegendBusinessDisplayed());
  }
}
