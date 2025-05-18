package net.simplyanalytics.tests.view.actions;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.DataType;
import net.simplyanalytics.enums.DataVariable;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditRelatedDataReportPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.pages.main.views.RelatedDataReportPage;
import net.simplyanalytics.pageobjects.sections.view.editview.containers.RadioButtonContainerPanel;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

public class RelatedDataToolbarTest extends TestBase { 
  
  private RelatedDataReportPage relatedDataReportPage;
  private DataVariable dataVariableZero = defaultDataVariables.get(0);
  private DataVariable dataVariableOne = defaultDataVariables.get(1);
  //TODO Delete if change stays
  //private DataVariable dataVariableTwo = defaultDataVariables.get(2);
  
  
  @Before
  public void login() {
    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
    SignInPage signInPage = institutionPage.institutionLogin(InstitutionUser.INSTITUTION,
        InstitutionUser.PASSWORD);
    WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
    NewProjectLocationWindow createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();
    MapPage mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.LOS_ANGELES_CA_CITY);
    EditRelatedDataReportPage editRelatedDataReportPage = (EditRelatedDataReportPage) mapPage.getViewChooserSection().clickNewView()
        .getActiveView().clickCreate(ViewType.RELATED_DATA);
    RadioButtonContainerPanel dataPanel = editRelatedDataReportPage.getActiveView().getDataPanel();
    dataPanel.clickElement(dataVariableOne.getFullName());
    relatedDataReportPage = (RelatedDataReportPage) editRelatedDataReportPage.clickDone();
  }
  
  @Test
  public void testRelatedDataToolbar() {
    List<List<String>> rows = relatedDataReportPage.getActiveView().getCellValues("Data Variable");
    relatedDataReportPage.getToolbar().changeLocation(Location.USA);
    List<List<String>> rows1 = relatedDataReportPage.getActiveView().getCellValues("Data Variable");
    
    //TODO No this data variable - delete if the change stays
    /*relatedDataReportPage.getToolbar().clickDataVariable().clickSortByDatavariable(dataVariableTwo);
    List<List<String>> rows2 = relatedDataReportPage.getActiveView().getCellValues("Data Variable");
    
    Assert.assertNotEquals("Location is not changed", rows1, rows);
    Assert.assertNotEquals("Data is not changed", rows1, rows2);
    
    relatedDataReportPage.getToolbar().openDataTypeMenu().clickOnDataType(DataType.COUNT);
    verificationStep("Verify that count data type is selected");
    Assert.assertEquals("Data type is not corrrect", DataType.COUNT.getType().toLowerCase(), relatedDataReportPage.getToolbar().getActiveDataType());
    
    List<String> dataVariables = relatedDataReportPage.getActiveView().getRowHeaderValues();
    for(String dataVariable : dataVariables) {
      verificationStep("Verify that data variable is a percent value");
      Assert.assertTrue("Data variable is not percent value: " + dataVariable, dataVariable.startsWith("#"));
    }*/
    
    
    relatedDataReportPage.getToolbar().clickDataVariable().clickSortByDatavariable(dataVariableOne);
    
    relatedDataReportPage.getToolbar().openDataTypeMenu().clickOnDataType(DataType.MEDIAN);
    verificationStep("Verify that mean data type is selected");
    Assert.assertEquals("Data type is not corrrect", DataType.MEDIAN.getType().toLowerCase(), relatedDataReportPage.getToolbar().getActiveDataType());
    
    List<String> dataVariables = relatedDataReportPage.getActiveView().getRowHeaderValues();
    for(String dataVariable : dataVariables) {
      verificationStep("Verify that data variable is a percent value");
      Assert.assertTrue("Data variable is not percent value: " + dataVariable, dataVariable.contains("Median"));
    }
    
    /*
    relatedDataReportPage.getToolbar().clickDataVariable().clickSortByDatavariable(dataVariableOne);
    
    relatedDataReportPage.getToolbar().openDataTypeMenu().clickOnDataType(DataType.PERCENT);
    verificationStep("Verify that percent data type is selected");
    Assert.assertEquals("Data type is not corrrect", DataType.PERCENT.getType().toLowerCase(), relatedDataReportPage.getToolbar().getActiveDataType());
    
    verificationStep("Verify that only the percent data variables are present");
    List<String> dataVariables = relatedDataReportPage.getActiveView().getRowHeaderValues();
    for(String dataVariable : dataVariables) {
      verificationStep("Verify that data variable is a percent value");
      Assert.assertTrue("Data variable is not percent value: " + dataVariable, dataVariable.startsWith("%"));
    }
    */
    
    relatedDataReportPage.getToolbar().clickDataVariable().clickSortByDatavariable(dataVariableZero);
    
    relatedDataReportPage.getToolbar().openDataTypeMenu().clickOnDataType(DataType.ALL);
    verificationStep("Verify that count data type is selected");
    Assert.assertEquals("Data type is not corrrect", DataType.ALL.getType(), relatedDataReportPage.getToolbar().getActiveDataType());
  }
}
