package net.simplyanalytics.enums;

import java.util.HashMap;
import java.util.Map;

import net.simplyanalytics.pageobjects.sections.view.RingStudyViewPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum DataVariable {

    //seed variables USA
    HASHTAG_TOTAL_POPULATION_2020("# Total Population", 2020),
    HASHTAG_TOTAL_POPULATION_2023("# Total Population", 2023),
    HASHTAG_TOTAL_POPULATION_DEFAULT_2018("# Total Population", 2018),
    HASHTAG_TOTAL_POPULATION_DEFAULT_2020("# Total Population", 2020),
    HASHTAG_TOTAL_POPULATION_DEFAULT_2021("# Total Population", 2021),
    HASHTAG_TOTAL_POPULATION_DEFAULT_2024("# Total Population", 2024),
    HASHTAG_TOTAL_POPULATION_DEFAULT_2026("# Total Population", 2026),
    PERCENT_PUBLIC_TRANSPORTATION_2018("% Sex Of Workers By Means Of Transportation To Work [B08006] | Workers 16 years and over | Public transportation (excluding taxicab)", 2018),
    MEDIAN_HOUSEHOLD_INCOME__IN_PAST_12_MONTHS_2018("Median Household Income In The Past 12 Months (In 2018 Inflation-Adjusted Dollars) [B19013] | Median household income in the past 12 months (in 2018 inflation-adjusted dollars)", 2018),
    HASHTAG_HOUSING_UNITS_2018("# Housing Units", 2018),
    HASHTAG_HOUSING_UNITS_2020("# Housing Units", 2020),
    HASHTAG_HOUSING_UNITS_2021("# Housing Units", 2021),
    PERCENT_HOUSING_BUILT_1939_OR_EARLIER_2018("% Year Structure Built [B25034] | Housing units | Built 1939 or earlier", 2018),
    PERCENT_TYPES_OF_COMPUTER_IN_HOUSHOLD_2018("% Types Of Computers In Household [B28001] | Households | No Computer", 2018),
    HOUSING_YEAR_STRUCTURE_BUILT_2020("% Housing Year Structure Built | Built 1939 or earlier", 2020),
    HOUSING_YEAR_STRUCTURE_BUILT_2018("% Housing Year Structure Built | Built 1939 or earlier", 2018),
    PERCENT_AGE_65_YEARS_AND_OVER_2018("% Age | 65 years and over", 2018),
    PERCENT_AGE_65_YEARS_AND_OVER_2021("% Age | 65 years and over", 2021),
    PERCENT_HOUSEHOLD_INCOME_100000_OR_MORE_2020("% Household Income | $100,000 or more", 2020),
    PERCENT_HOUSEHOLD_INCOME_100000_OR_MORE_2021("% Household Income | $100,000 or more", 2021),
    PERCENT_HOUSEHOLD_INCOME_100000_OR_MORE_2024("% Household Income | $100,000 or more", 2024),
    PERCENT_VETERAN_STATUS_2021("% Veteran Status | Veteran",2021),
    PERCENT_HOUSING_TENURE_OWNER_OCCUPIED_2021("% Housing Tenure | Owner occupied",2021),
    PERCENT_HOUSING_TENURE_RENTER_OCCUPIED_2021("% Housing Tenure | Renter occupied",2021),

    //seed variable Canada

    PERCENT_HOUSEHOLD_POPULATION_25_TO_64_2022("% Household Population 25 to 64 Years by Educational Attainment | Household Population 25 To 64 Years | University Certificate, Diploma Or Degree At Bachelor Level Or Above", 2022),
    PERCENT_HOUSEHOLD_POPULATION_25_TO_64_2024("% Household Population 25 to 64 Years by Educational Attainment | Household Population 25 To 64 Years | University Certificate, Diploma Or Degree At Bachelor Level Or Above", 2024),
    PERCENT_HOUSEHOLD_POPULATION_25_TO_64_2020("% Household Population 25 to 64 Years by Educational Attainment | Household Population 25 To 64 Years | University Certificate, Diploma Or Degree At Bachelor Level Or Above", 2020),
    HASHTAG_HOUSEHOLD_POPULATION_25_TO_64_2022("# Household Population 25 to 64 Years by Educational Attainment | Household Population 25 To 64 Years | University Certificate, Diploma Or Degree At Bachelor Level Or Above", 2022),
    HASHTAG_HOUSEHOLD_POPULATION_25_TO_64_2024("# Household Population 25 to 64 Years by Educational Attainment | Household Population 25 To 64 Years | University Certificate, Diploma Or Degree At Bachelor Level Or Above", 2024),
    HASHTAG_BASIC_POPULATION_2024("# Basics | Total Population", 2024),
    HASHTAG_BASIC_POPULATION_2028("# Basics | Total Population", 2028),

    // default data
    MEDIAN_HOUSEHOLD_INCOME_2018("Median Household Income", 2018),
    MEDIAN_HOUSEHOLD_INCOME_2019("Median Household Income", 2019),
    MEDIAN_HOUSEHOLD_INCOME_2020("Median Household Income", 2020),
    MEDIAN_HOUSEHOLD_INCOME_2021("Median Household Income", 2021),
    MEDIAN_HOUSEHOLD_INCOME_2024("Median Household Income", 2024),
    MEDIAN_HOUSEHOLD_INCOME_BY_AGE_OF_HOUSEHOLDER_2020("Median Household Income by Age of Householder | Households", 2020),
    PERCENT_EDUCATION_ATTAINMENT_COLLEGE_MASTER_DOCTORATE_DEGREE_OR_HIGHER_2024(
            "% Educational Attainment | Bachelor's degree or higher", 2024),
    PERCENT_EDUCATION_ATTAINMENT_COLLEGE_MASTER_DOCTORATE_DEGREE_2020(
            "% Education Attainment, College, Master's or Doctorate Degree", 2020),
    PERCENT_EDUCATION_ATTAINMENT_COLLEGE_MASTER_DOCTORATE_DEGREE_OR_HIGHER_2021(
            "% Educational Attainment | Bachelor's degree or higher", 2021),
    PERCENT_EDUCATION_ATTAINMENT_COLLEGE_MASTER_DOCTORATE_DEGREE_OR_HIGHER_2020(
            "% Educational Attainment | Bachelor's degree or higher", 2020),
    PERCENT_EDUCATION_ATTAINMENT_COLLEGE_MASTER_DOCTORATE_DEGREE_OR_HIGHER_2018(
            "% Educational Attainment | Bachelor's degree or higher", 2018),
    PERCENT_EDUCATION_ATTAINMENT_COLLEGE_MASTER_DOCTORATE_DEGREE_OR_HIGHER_2010(
            "% Educational Attainment | Bachelor's degree or higher", 2010),
    PERCENT_HOUSING_YEAR_STRUCTURE_BUILT_1939_OR_EARLIER_2020("% Housing Year Structure Built | Built 1939 or earlier", 2020),
    PERCENT_HOUSING_YEAR_STRUCTURE_BUILT_1939_OR_EARLIER_2018("% Housing Year Structure Built | Built 1939 or earlier", 2018),
    PERCENT_HOUSING_BUILT_1939_OR_EARLIER_2019("% Housing, Built 1939 or Earlier", 2019),
    PERCENT_HOUSING_BUILT_1939_OR_EARLIER_2020("% Housing, Built 1939 or Earlier", 2020),
    PERCENT_HOUSING_BUILT_1939_OR_EARLIER_2021("% Housing, Built 1939 or Earlier", 2021),

    // population data
    HASHTAG_POPULATION_2010("# Population", 2010),
    HASHTAG_POPULATION_2019("# Population", 2019),
    HASHTAG_POPULATION_2020("# Population", 2020),
    HASHTAG_POPULATION_POVERITY_2020("# Population in Poverty, Total", 2020),
    HASHTAG_POPULATION_POVERITY_2018("# Population in Poverty, Total", 2018),
    HASHTAG_POPULATION_2022("# Population", 2022),
    TOTAL_POPULATION_2018("Total Population", 2018),
    TOTAL_POPULATION_2020("Total Population", 2020),
    HASHTAG_TOTAL_POPULATION_2010("# Total Population", 2010),
    HASHTAG_TOTAL_POPULATION_2019("# Total Population", 2019),
    HASHTAG_TOTAL_POPULATION_2017("# Total Population", 2017),
    TOTAL_POPULATION_2021("Total Population", 2021),
    TOTAL_POPULATION_2024("Total Population", 2024),
    TOTAL_POPULATION_2026("Total Population", 2026),
    POPULATION_2020("Population (Pop)", 2020),
    FAMILY_POPULATION_PROCENTS_2019("% Family Population", 2019),
    POPULATION_DENSITY_PER_SQ_MILE_2020("Population Density (per sq. mile)", 2020),
    TOTAL_POPULATION_MALE_2018("Sex By Age [B01001] | Total population | Male", 2018),
    HASHTAG_EDUCATION_ATTAINMENT_COLLEGE_MASTER_DOCTORATE_DEGREE_OR_HIGHER_2018(
            "# Educational Attainment | Bachelor's degree or higher", 2018),
    HASHTAG_EDUCATION_ATTAINMENT_COLLEGE_MASTER_DOCTORATE_DEGREE_OR_HIGHER_2020(
            "# Educational Attainment | Bachelor's degree or higher", 2020),
    HASHTAG_EDUCATION_ATTAINMENT_COLLEGE_MASTER_DOCTORATE_DEGREE_OR_HIGHER_2021(
            "# Educational Attainment | Bachelor's degree or higher", 2021),
    HASHTAG_EDUCATION_ATTAINMENT_COLLEGE_MASTER_DOCTORATE_DEGREE_OR_HIGHER_2024(
            "# Educational Attainment | Bachelor's degree or higher", 2024),

    // employment data
    EMPLOYMENT_CONSTRUCTION_PROCENTS_2019("% Employment, Construction", 2019),
    EMPLOYMENT_MANUFACTORING_PROCENTS_2019("% Employment, Manufacturing", 2019),

    //income
    AVERAGE_HOUSEHOLD_NET_WORTH_2019("Average Household Net Worth", 2019),
    HOUSEHOLD_INCOME_PER_CAPITA_$_2019("Household Income, Per Capita ($)", 2019),
    PER_CAPITA_INCOME_2020("Per Capita Income", 2020),
    PER_CAPITA_INCOME_2021("Per Capita Income", 2021),
    PER_CAPITA_INCOME_BY_RACE_2020("Per Capita Income by Race | White", 2020),

    // consumer behavior
    FOOD_IN_DOLARS_2019("Food ($000)", 2019),

    // income data
    PERSONAL_INCOME_TOTAL_2020("Personal Income, Total ($)", 2020)
    ;

    private static final String ACTUAL_YEAR = "2024";
    private static final String ACTUAL_CANADA_YEAR = "2024";
    // private static final String ACTUAL_CANADA_YEAR = "2022";
    private static final String YEAR_2020 = "2020";
    private static final String CROSSTAB_YEAR = "2023";
    private static final String SEED_VARIABLES_YEAR = "2024";
    public static Logger logger = LoggerFactory.getLogger(RingStudyViewPanel.class);

    private final String name;
    private final int year;

    DataVariable(String name, int year) {
        this.name = name;
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    public String getFullName() {
        return name + ", " + year;
    }

    // fields and methods to get enum by name and date
    private static final Map<String, DataVariable> fullNameMap;

    static {
        fullNameMap = new HashMap<>();
        for (DataVariable dataVariable : DataVariable.values()) {
            fullNameMap.put(dataVariable.getFullName(), dataVariable);
        }
    }

    public static DataVariable getByFullName(String fullName) {
        logger.trace("search for data variable with full name: " + fullName);
        return fullNameMap.get(fullName);
    }

    public static String getActualYear() {
        return ACTUAL_YEAR;
    }

    public static String getActualCanadaYear() {
        return ACTUAL_CANADA_YEAR;
    }

    public static String getCrosstabYear() {
        return CROSSTAB_YEAR;
    }

    public static String getSeedVariablesYear() {
        return SEED_VARIABLES_YEAR;
    }
    public static String getYear2020() {
        return YEAR_2020;
    }

    @Override
    public String toString() {
        return getFullName();
    }
}
