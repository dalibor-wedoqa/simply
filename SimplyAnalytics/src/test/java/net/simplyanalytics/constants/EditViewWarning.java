package net.simplyanalytics.constants;

public class EditViewWarning {
  
  public static final String LOCATION_MISSING = "Please select a location to view the report.";
  public static final String DATA_MISSING = "Please select a data variable to view the report.";
  public static final String BUSINESS_MISSING = "Please add a business search to the project.";
  public static final String SELECT_DATA_VARIABLE = "Please select at least one row data variable and one column data variable.";
  public static final String CROSSTABLIMITMESSAGE = "Your crosstab table contains too many data variables. "
      + "The product of the number of columns and number of rows in a crosstab cannot exceed 1,600. "
      + "For example, your crosstab can have a maximum of 40 columns × 40 rows, or 20 columns × 80 rows.";
  public static final String SELECT_DATA_VARIABLE_SCATTER_PLOT = "Please select one x and one y data variable for your Scatter Plot.";
}
