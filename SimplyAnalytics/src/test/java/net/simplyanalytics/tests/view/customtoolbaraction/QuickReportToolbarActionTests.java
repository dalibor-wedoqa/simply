package net.simplyanalytics.tests.view.customtoolbaraction;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.simplyanalytics.constants.InstitutionUser;
import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.ReportContent;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.login.AuthenticateInstitutionPage;
import net.simplyanalytics.pageobjects.pages.login.SignInPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.pages.main.views.QuickReportPage;
import net.simplyanalytics.pageobjects.windows.NewProjectLocationWindow;
import net.simplyanalytics.pageobjects.windows.WelcomeScreenTutorialWindow;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static net.simplyanalytics.utils.DatasetYearUpdater.updateDatasetYearsInList;

public class QuickReportToolbarActionTests extends TestBase {
  
  private QuickReportPage quickReportPage;
  
  private static Map<String, List<String>> prizmReport;
  private static Map<String, List<String>> demographicOverview;
  private static Map<String, List<String>> housingDetail;
  private static Map<String, List<String>> cexSpotlight;
  static String year = "2025";
  static String previousYear = "2024";
  
  //prizm report
  static {
    
    List<String> urbancity_categories = Arrays.asList(
        "PRIZM Premier Urban Households, 2025",
        "PRIZM Premier Suburban Households, 2025",
        "PRIZM Premier Second City Households, 2025",
        //"PRIZM Premier Metro Mix Households, 2021", 
        "PRIZM Premier Town Households, 2025"
        //"PRIZM Premier Rural Households, 2021"
        );
    
    List<String> urban_urban_uptown = Arrays.asList(
        "Households Social - Urban U1: Urban Uptown, 2024",
        "Households Segment 4: Young Digerati, 2024",
        "Households Segment 7: Money & Brains, 2024",
        "Households Segment 19: American Dreams, 2024",
        "Households Segment 21: The Cosmopolitans, 2024"
        );
    
    List<String> urban_midtown_mix = Arrays.asList(
        "Households Social - Urban U2: Midtown Mix, 2024",
        "Households Segment 17: Urban Elders, 2024",
        "Households Segment 31: Connected Bohemians, 2024",
        "Households Segment 35: Urban Achievers, 2024",
        "Households Segment 40: Aspiring A-Listers, 2024"
        );
    
    List<String> urban_urban_cores = Arrays.asList(
        "Households Social - Urban U3: Urban Cores, 2024",
        "Households Segment 42: Multi-Culti Mosaic, 2024",
        "Households Segment 43: City Roots, 2024",
        "Households Segment 45: Urban Modern Mix, 2024",
        "Households Segment 56: Multi-Culti Families, 2024",
        "Households Segment 63: Low-Rise Living, 2024"
        );
    
    List<String> suburban_elite_suburbs = Arrays.asList(
        "Households Social - Suburban S1: Elite Suburbs, 2024",
        "Households Segment 1: Upper Crust, 2024",
        "Households Segment 2: Networked Neighbors, 2024",
        "Households Segment 3: Movers & Shakers, 2024"
        );
    
    List<String> suburban_the_affluentials = Arrays.asList(
        "Households Social - Suburban S2: The Affluentials, 2024",
        "Households Segment 6: Winner's Circle, 2024",
        "Households Segment 8: Gray Power, 2024",
        "Households Segment 10: Executive Suites, 2024",
        "Households Segment 12: Cruisin' to Retirement, 2024",
        "Households Segment 13: Upward Bound, 2024",
        "Households Segment 14: Kids & Cul-de-Sacs, 2024",
        "Households Segment 16: Beltway Boomers, 2024"
        );
    
    List<String> suburban_middleburbs = Arrays.asList(
        "Households Social - Suburban S3: Middleburbs, 2024",
        "Households Segment 20: Empty Nests, 2024",
        "Households Segment 25: Up-and-Comers, 2024",
        "Households Segment 26: Home Sweet Home, 2024",
        "Households Segment 30: Pools & Patios, 2024"
        );
    
    List<String> suburban_inner_suburbs = Arrays.asList(
        "Households Social - Suburban S4: Inner Suburbs, 2024",
        "Households Segment 34: Young & Influential, 2024",
        "Households Segment 36: Toolbelt Traditionalists, 2024",
        "Households Segment 41: Domestic Duos, 2024",
        "Households Segment 50: Metro Grads, 2024"
        );
    
    List<String> second_city_second_city_society = Arrays.asList(
        "Households Social - Second City C1: Second City Society, 2024",
        "Households Segment 22: Middleburg Managers, 2024",
        "Households Segment 33: Second City Startups, 2024",
        "Households Segment 37: Bright Lights, Li'l City, 2024"
        );
    
    List<String> second_city_city_centers = Arrays.asList(
        "Households Social - Second City C2: City Centers, 2024",
        "Households Segment 47: Striving Selfies, 2024",
        "Households Segment 48: Generation Web, 2024",
        "Households Segment 49: American Classics, 2024",
        "Households Segment 53: Lo-Tech Singles, 2024",
        "Households Segment 54: Struggling Singles, 2024"
        );
    
    List<String> second_city_micro_city_mix = Arrays.asList(
        "Households Social - Second City C3: Micro-City Mix, 2024",
        "Households Segment 59: New Melting Pot, 2024",
        "Households Segment 61: Second City Generations, 2024",
        "Households Segment 64: Family Thrifts, 2024",
        "Households Segment 66: New Beginnings, 2024",
        "Households Segment 67: Park Bench Seniors, 2024"
        );
    
    List<String> town_and_rural_landed_gentry = Arrays.asList(
        "Households Social - Town & Rural T1: Landed Gentry, 2024",
        "Households Segment 5: Country Squires, 2024",
        "Households Segment 9: Big Fish, Small Pond, 2024",
        "Households Segment 11: Fast-Track Families, 2024",
        "Households Segment 15: New Homesteaders, 2024"
        );
    
    List<String> town_and_rural_country_comfort = Arrays.asList(
        "Households Social - Town & Rural T2: Country Comfort, 2024",
        "Households Segment 18: Mayberry-ville, 2024",
        "Households Segment 23: Township Travelers, 2024",
        "Households Segment 24: Pickup Patriarchs, 2024",
        "Households Segment 27: Big Sky Families, 2024",
        "Households Segment 28: Country Casuals, 2024",
        "Households Segment 29: White Picket Fences, 2024"
        );
    
    List<String> town_and_rural_middle_america = Arrays.asList(
        "Households Social - Town & Rural T3: Middle America, 2024",
        "Households Segment 32: Traditional Times, 2024",
        "Households Segment 38: Hometown Retired, 2024",
        "Households Segment 39: Kid Country, USA, 2024",
        "Households Segment 44: Country Strong, 2024",
        "Households Segment 46: Heartlanders, 2024",
        "Households Segment 51: Campers & Camo, 2024",
        "Households Segment 52: Simple Pleasures, 2024"
        );
    
    List<String> town_and_rural_rustic_living = Arrays.asList(
        "Households Social - Town & Rural T4: Rustic Living, 2024",
        "Households Segment 55: Red, White & Blue, 2024",
        "Households Segment 57: Back-Country Folks, 2024",
        "Households Segment 58: Golden Ponds, 2024",
        "Households Segment 60: Small-Town Collegiates, 2024",
        "Households Segment 62: Crossroad Villagers, 2024",
        "Households Segment 65: Young & Rustic, 2024",
        "Households Segment 68: Bedrock America, 2024"
        );
    
    prizmReport = new HashMap<>();
    prizmReport.put("URBANICITY CATEGORIES", urbancity_categories);
    prizmReport.put("URBAN: URBAN UPTOWN", urban_urban_uptown);
    prizmReport.put("URBAN: MIDTOWN MIX", urban_midtown_mix);
    prizmReport.put("URBAN: URBAN CORES", urban_urban_cores);
    prizmReport.put("SUBURBAN: ELITE SUBURBS", suburban_elite_suburbs);
    prizmReport.put("SUBURBAN: THE AFFLUENTIALS", suburban_the_affluentials);
    prizmReport.put("SUBURBAN: MIDDLEBURBS", suburban_middleburbs);
    prizmReport.put("SUBURBAN: INNER SUBURBS", suburban_inner_suburbs);
    prizmReport.put("SECOND CITY: SECOND CITY SOCIETY", second_city_second_city_society);
    prizmReport.put("SECOND CITY: CITY CENTERS", second_city_city_centers);
    prizmReport.put("SECOND CITY: MICRO-CITY MIX", second_city_micro_city_mix);
    prizmReport.put("TOWN & RURAL: LANDED GENTRY", town_and_rural_landed_gentry);
    prizmReport.put("TOWN & RURAL: COUNTRY COMFORT", town_and_rural_country_comfort);
    prizmReport.put("TOWN & RURAL: MIDDLE AMERICA", town_and_rural_middle_america);
    prizmReport.put("TOWN & RURAL: RUSTIC LIVING", town_and_rural_rustic_living);
  }
  
  //TODO delete when no longer exists
  //demographic overview
  static {
    /*List<String> 2021uages = Arrays.asList(
        "Language Spoken At Home For The Population 5 Years And Over [C16001] | Population 5 years and over, 2021",
        "Language Spoken At Home For The Population 5 Years And Over [C16001] | Population 5 years and over | Speak only English, 2021", 
        "Language Spoken At Home For The Population 5 Years And Over [C16001] | Population 5 years and over | Spanish, 2021", 
        "Language Spoken At Home For The Population 5 Years And Over [C16001] | Population 5 years and over | French, Haitian, or Cajun, 2021", 
        "Language Spoken At Home For The Population 5 Years And Over [C16001] | Population 5 years and over | German or other West Germanic languages, 2021",
        "Language Spoken At Home For The Population 5 Years And Over [C16001] | Population 5 years and over | Russian, Polish, or other Slavic languages, 2021",
        "Language Spoken At Home For The Population 5 Years And Over [C16001] | Population 5 years and over | Other Indo-European languages, 2021",
        "Language Spoken At Home For The Population 5 Years And Over [C16001] | Population 5 years and over | Korean, 2021",
        "Language Spoken At Home For The Population 5 Years And Over [C16001] | Population 5 years and over | Chinese (incl. Mandarin, Cantonese), 2021",
        "Language Spoken At Home For The Population 5 Years And Over [C16001] | Population 5 years and over | Vietnamese, 2021",
        "Language Spoken At Home For The Population 5 Years And Over [C16001] | Population 5 years and over | Tagalog (incl. Filipino), 2021",
        "Language Spoken At Home For The Population 5 Years And Over [C16001] | Population 5 years and over | Other Asian and Pacific Island languages, 2021",
        "Language Spoken At Home For The Population 5 Years And Over [C16001] | Population 5 years and over | Arabic, 2021",
        "Language Spoken At Home For The Population 5 Years And Over [C16001] | Population 5 years and over | Other and unspecified languages, 2021");
    List<String> populationAndGender = Arrays.asList(
        "Total Population [B01003] | Total population, 2021",
        "Sex By Age [B01001] | Total population | Male, 2021", 
        "Sex By Age [B01001] | Total population | Female, 2021");
    List<String> raceAndEthnicity = Arrays.asList(
        "Race [B02001] | Total population, 2021",
        "Race [B02001] | Total population | White alone, 2021", 
        "Race [B02001] | Total population | Black or African American alone, 2021", 
        "Race [B02001] | Total population | American Indian and Alaska Native alone, 2021", 
        "Race [B02001] | Total population | Asian alone, 2021",
        "Race [B02001] | Total population | Native Hawaiian and Other Pacific Islander alone, 2021",
        "Hispanic Or Latino Origin [B03003] | Total population | Hispanic or Latino, 2021");
    List<String> income = Arrays.asList(
        "Median Household Income In The Past 12 Months (In 2021 Inflation-Adjusted Dollars) [B19013] | Median household income in the past 12 months (in 2021 inflation-adjusted dollars), 2021",
        "Per Capita Income In The Past 12 Months (In 2021 Inflation-Adjusted Dollars) [B19301] | Per capita income in the past 12 months (in 2021 inflation-adjusted dollars), 2021", 
        "Household Income In The Past 12 Months (In 2021 Inflation-Adjusted Dollars) [B19001] | Households, 2021", 
        "Household Income In The Past 12 Months (In 2021 Inflation-Adjusted Dollars) [B19001] | Households | Less than $10,000, 2021", 
        "Household Income In The Past 12 Months (In 2021 Inflation-Adjusted Dollars) [B19001] | Households | $10,000 to $14,999, 2021",
        "Household Income In The Past 12 Months (In 2021 Inflation-Adjusted Dollars) [B19001] | Households | $15,000 to $19,999, 2021", 
        "Household Income In The Past 12 Months (In 2021 Inflation-Adjusted Dollars) [B19001] | Households | $20,000 to $24,999, 2021", 
        "Household Income In The Past 12 Months (In 2021 Inflation-Adjusted Dollars) [B19001] | Households | $25,000 to $29,999, 2021",
        "Household Income In The Past 12 Months (In 2021 Inflation-Adjusted Dollars) [B19001] | Households | $30,000 to $34,999, 2021", 
        "Household Income In The Past 12 Months (In 2021 Inflation-Adjusted Dollars) [B19001] | Households | $35,000 to $39,999, 2021", 
        "Household Income In The Past 12 Months (In 2021 Inflation-Adjusted Dollars) [B19001] | Households | $40,000 to $44,999, 2021",
        "Household Income In The Past 12 Months (In 2021 Inflation-Adjusted Dollars) [B19001] | Households | $45,000 to $49,999, 2021", 
        "Household Income In The Past 12 Months (In 2021 Inflation-Adjusted Dollars) [B19001] | Households | $50,000 to $59,999, 2021", 
        "Household Income In The Past 12 Months (In 2021 Inflation-Adjusted Dollars) [B19001] | Households | $60,000 to $74,999, 2021",
        "Household Income In The Past 12 Months (In 2021 Inflation-Adjusted Dollars) [B19001] | Households | $75,000 to $99,999, 2021",
        "Household Income In The Past 12 Months (In 2021 Inflation-Adjusted Dollars) [B19001] | Households | $100,000 to $124,999, 2021",
        "Household Income In The Past 12 Months (In 2021 Inflation-Adjusted Dollars) [B19001] | Households | $125,000 to $149,999, 2021",
        "Household Income In The Past 12 Months (In 2021 Inflation-Adjusted Dollars) [B19001] | Households | $150,000 to $199,999, 2021",
        "Household Income In The Past 12 Months (In 2021 Inflation-Adjusted Dollars) [B19001] | Households | $200,000 or more, 2021");
    List<String> housing = Arrays.asList(
        "Housing Units [B25001] | Housing units, 2021",
        "Occupancy Status [B25002] | Housing units | Occupied, 2021", 
        "Occupancy Status [B25002] | Housing units | Vacant, 2021", 
        "Tenure [B25003] | Occupied housing units | Owner occupied, 2021", 
        "Tenure [B25003] | Occupied housing units | Renter occupied, 2021");
    List<String> education = Arrays.asList(
        "Educational Attainment For The Population 25 Years And Over [B15003] | Population 25 years and over, 2021",
        "Educational Attainment For The Population 25 Years And Over [B15003] | Population 25 years and over | No schooling completed, 2021", 
        "Educational Attainment For The Population 25 Years And Over [B15003] | Population 25 years and over | Nursery school, 2021", 
        "Educational Attainment For The Population 25 Years And Over [B15003] | Population 25 years and over | Kindergarten, 2021", 
        "Educational Attainment For The Population 25 Years And Over [B15003] | Population 25 years and over | 1st grade, 2021",
        "Educational Attainment For The Population 25 Years And Over [B15003] | Population 25 years and over | 2nd grade, 2021",
        "Educational Attainment For The Population 25 Years And Over [B15003] | Population 25 years and over | 3rd grade, 2021",
        "Educational Attainment For The Population 25 Years And Over [B15003] | Population 25 years and over | 4th grade, 2021",
        "Educational Attainment For The Population 25 Years And Over [B15003] | Population 25 years and over | 5th grade, 2021",
        "Educational Attainment For The Population 25 Years And Over [B15003] | Population 25 years and over | 6th grade, 2021",
        "Educational Attainment For The Population 25 Years And Over [B15003] | Population 25 years and over | 7th grade, 2021",
        "Educational Attainment For The Population 25 Years And Over [B15003] | Population 25 years and over | 8th grade, 2021",
        "Educational Attainment For The Population 25 Years And Over [B15003] | Population 25 years and over | 9th grade, 2021",
        "Educational Attainment For The Population 25 Years And Over [B15003] | Population 25 years and over | 10th grade, 2021",
        "Educational Attainment For The Population 25 Years And Over [B15003] | Population 25 years and over | 11th grade, 2021",
        "Educational Attainment For The Population 25 Years And Over [B15003] | Population 25 years and over | 12th grade, no diploma, 2021",
        "Educational Attainment For The Population 25 Years And Over [B15003] | Population 25 years and over | Regular high school diploma, 2021",
        "Educational Attainment For The Population 25 Years And Over [B15003] | Population 25 years and over | GED or alternative credential, 2021",
        "Educational Attainment For The Population 25 Years And Over [B15003] | Population 25 years and over | Some college, less than 1 year, 2021",
        "Educational Attainment For The Population 25 Years And Over [B15003] | Population 25 years and over | Some college, 1 or more years, no degree, 2021",
        "Educational Attainment For The Population 25 Years And Over [B15003] | Population 25 years and over | Associate's degree, 2021",
        "Educational Attainment For The Population 25 Years And Over [B15003] | Population 25 years and over | Bachelor's degree, 2021",
        "Educational Attainment For The Population 25 Years And Over [B15003] | Population 25 years and over | Master's degree, 2021",
        "Educational Attainment For The Population 25 Years And Over [B15003] | Population 25 years and over | Professional school degree, 2021",
        "Educational Attainment For The Population 25 Years And Over [B15003] | Population 25 years and over | Doctorate degree, 2021");
    List<String> age = Arrays.asList(
        "Sex By Age [B01001] | Total population, 2021",
        "Sex By Age [B01001] | Total population | Under 5 years, 2021", 
        "Sex By Age [B01001] | Total population | 5 to 9 years, 2021", 
        "Sex By Age [B01001] | Total population | 10 to 14 years, 2021", 
        "Sex By Age [B01001] | Total population | 15 to 17 years, 2021",
        "Sex By Age [B01001] | Total population | 18 and 19 years, 2021",
        "Sex By Age [B01001] | Total population | 20 years, 2021",
        "Sex By Age [B01001] | Total population | 21 years, 2021",
        "Sex By Age [B01001] | Total population | 22 to 24 years, 2021",
        "Sex By Age [B01001] | Total population | 25 to 29 years, 2021",
        "Sex By Age [B01001] | Total population | 30 to 34 years, 2021",
        "Sex By Age [B01001] | Total population | 35 to 39 years, 2021",
        "Sex By Age [B01001] | Total population | 40 to 44 years, 2021",
        "Sex By Age [B01001] | Total population | 45 to 49 years, 2021",
        "Sex By Age [B01001] | Total population | 50 to 54 years, 2021",
        "Sex By Age [B01001] | Total population | 55 to 59 years, 2021",
        "Sex By Age [B01001] | Total population | 60 and 61 years, 2021",
        "Sex By Age [B01001] | Total population | 62 to 64 years, 2021",
        "Sex By Age [B01001] | Total population | 65 and 66 years, 2021",
        "Sex By Age [B01001] | Total population | 67 to 69 years, 2021",
        "Sex By Age [B01001] | Total population | 70 to 74 years, 2021",
        "Sex By Age [B01001] | Total population | 75 to 79 years, 2021",
        "Sex By Age [B01001] | Total population | 80 to 84 years, 2021",
        "Sex By Age [B01001] | Total population | 85 years and over, 2021");*/
	  
	  List<String> languages = Arrays.asList(
		        "Language Spoken at Home | Population 5 years and over, 2024",
		        "Language Spoken at Home | Speak only English, 2024",
		        "Language Spoken at Home | Spanish, 2024",
		        "Language Spoken at Home | French, Haitian, or Cajun, 2024",
		        "Language Spoken at Home | German or other West Germanic languages, 2024",
		        "Language Spoken at Home | Russian, Polish, or other Slavic languages, 2024",
		        "Language Spoken at Home | Other Indo-European languages, 2024",
		        "Language Spoken at Home | Korean, 2024",
		        "Language Spoken at Home | Chinese (incl. Mandarin, Cantonese), 2024",
		        "Language Spoken at Home | Vietnamese, 2024",
		        "Language Spoken at Home | Tagalog (incl. Filipino), 2024",
		        "Language Spoken at Home | Other Asian and Pacific Island languages, 2024",
		        "Language Spoken at Home | Arabic, 2024",
		        "Language Spoken at Home | Other and unspecified languages, 2024");

      /*List<String> languages = Arrays.asList(
              "Language Spoken at Home | Population 5 years and over, 2026",
              "Language Spoken at Home | Speak only English, 2026",
              "Language Spoken at Home | Spanish, 2026",
              "Language Spoken at Home | French, Haitian, or Cajun, 2026",
              "Language Spoken at Home | German or other West Germanic languages, 2026",
              "Language Spoken at Home | Russian, Polish, or other Slavic languages, 2026",
              "Language Spoken at Home | Other Indo-European languages, 2026",
              "Language Spoken at Home | Korean, 2026",
              "Language Spoken at Home | Chinese (incl. Mandarin, Cantonese), 2026",
              "Language Spoken at Home | Vietnamese, 2026",
              "Language Spoken at Home | Tagalog (incl. Filipino), 2026",
              "Language Spoken at Home | Other Asian and Pacific Island languages, 2026",
              "Language Spoken at Home | Arabic, 2026",
              "Language Spoken at Home | Other and unspecified languages, 2026");*/

    List<String> populationAndGender = Arrays.asList(
            "Total Population, 2024",
            "Sex | Male, 2024",
            "Sex | Female, 2024");

    /*List<String> populationAndGender = Arrays.asList(
            "Total Population, 2026",
            "Sex | Male, 2026",
            "Sex | Female, 2026");*/

    /*List<String> raceAndEthnicity = Arrays.asList(
		        "Race | White alone, 2020",
		        "Race | Black or African American alone, 2020", 
		        "Race | American Indian and Alaska Native alone, 2020", 
		        "Race | Asian alone, 2020", 
		        "Race | Native Hawaiian and Other Pacific Islander alone, 2020",
		        "Race | Some other race alone, 2020",
		        "Race | Two or more races, 2020",
				"Hispanic or Latino | Not Hispanic or Latino, 2020",
				"Hispanic or Latino | Hispanic or Latino, 2020");*/

    List<String> raceAndEthnicity = Arrays.asList(
            "Race | White alone, 2024",
            "Race | Black or African American alone, 2024",
            "Race | American Indian and Alaska Native alone, 2024",
            "Race | Asian alone, 2024",
            "Race | Native Hawaiian and Other Pacific Islander alone, 2024",
            "Race | Some other race alone, 2024",
            "Race | Two or more races, 2024",
            "Hispanic or Latino | Not Hispanic or Latino, 2024",
            "Hispanic or Latino | Hispanic or Latino, 2024");

    /*List<String> raceAndEthnicity = Arrays.asList(
              "Race | White alone, 2026",
              "Race | Black or African American alone, 2026",
              "Race | American Indian and Alaska Native alone, 2026",
              "Race | Asian alone, 2026",
              "Race | Native Hawaiian and Other Pacific Islander alone, 2026",
              "Race | Some other race alone, 2026",
              "Race | Two or more races, 2026",
              "Hispanic or Latino | Not Hispanic or Latino, 2026",
              "Hispanic or Latino | Hispanic or Latino, 2026");*/

		    /*List<String> income = Arrays.asList(
		        "Median Household Income, 2020",
		        "Per Capita Income, 2020", 
		        "Household Income | Households, 2020", 
		        "Household Income | Less than $10,000, 2020", 
		        "Household Income | $10,000 to $19,999, 2020",
		        "Household Income | $20,000 to $29,999, 2020", 
		        "Household Income | $30,000 to $39,999, 2020", 
		        "Household Income | $40,000 to $49,999, 2020",
		        "Household Income | $50,000 to $59,999, 2020", 
		        "Household Income | $60,000 to $74,999, 2020", 
		        "Household Income | $75,000 to $99,999, 2020",
		        "Household Income | $100,000 to $124,999, 2020", 
		        "Household Income | $125,000 to $149,999, 2020", 
		        "Household Income | $150,000 to $199,999, 2020",
		        "Household Income | $200,000 or more, 2020");*/

    List<String> income = Arrays.asList(
            "Median Household Income, 2024",
            "Per Capita Income, 2024",
            "Household Income | Households, 2024",
            "Household Income | Less than $10,000, 2024",
            "Household Income | $10,000 to $19,999, 2024",
            "Household Income | $20,000 to $29,999, 2024",
            "Household Income | $30,000 to $39,999, 2024",
            "Household Income | $40,000 to $49,999, 2024",
            "Household Income | $50,000 to $59,999, 2024",
            "Household Income | $60,000 to $74,999, 2024",
            "Household Income | $75,000 to $99,999, 2024",
            "Household Income | $100,000 to $124,999, 2024",
            "Household Income | $125,000 to $149,999, 2024",
            "Household Income | $150,000 to $199,999, 2024",
            "Household Income | $200,000 or more, 2024");

      /*List<String> income = Arrays.asList(
              "Median Household Income, 2026",
              "Per Capita Income, 2026",
              "Household Income | Households, 2026",
              "Household Income | Less than $10,000, 2026",
              "Household Income | $10,000 to $19,999, 2026",
              "Household Income | $20,000 to $29,999, 2026",
              "Household Income | $30,000 to $39,999, 2026",
              "Household Income | $40,000 to $49,999, 2026",
              "Household Income | $50,000 to $59,999, 2026",
              "Household Income | $60,000 to $74,999, 2026",
              "Household Income | $75,000 to $99,999, 2026",
              "Household Income | $100,000 to $124,999, 2026",
              "Household Income | $125,000 to $149,999, 2026",
              "Household Income | $150,000 to $199,999, 2026",
              "Household Income | $200,000 or more, 2026");*/

      List<String> housing = Arrays.asList(
              "Housing Units, 2024",
              "Housing Occupancy Status | Occupied, 2024",
              "Housing Occupancy Status | Vacant, 2024",
              "Housing Tenure | Owner occupied, 2024",
              "Housing Tenure | Renter occupied, 2024");

      /*List<String> housing = Arrays.asList(
            "Housing Units, 2026",
            "Housing Occupancy Status | Occupied, 2026",
            "Housing Occupancy Status | Vacant, 2026",
            "Housing Tenure | Owner occupied, 2026",
            "Housing Tenure | Renter occupied, 2026");*/

	  /*List<String> education = Arrays.asList(
		     "Educational Attainment | Population 25 years and over, 2020",
		     "Educational Attainment | Less than high school diploma, 2020",
		     "Educational Attainment | High school graduate (includes equivalency), 2020",
		     "Educational Attainment | Some college, 2020",
		     "Educational Attainment | Associate's degree, 2020",
		     "Educational Attainment | Bachelor's degree, 2020",
		     "Educational Attainment | Master's degree, 2020",
		     "Educational Attainment | Professional school degree, 2020",
		     "Educational Attainment | Doctorate degree, 2020");*/

    List<String> education = Arrays.asList(
            "Educational Attainment | Population 25 years and over, 2024",
            "Educational Attainment | Less than high school diploma, 2024",
            "Educational Attainment | High school graduate (includes equivalency), 2024",
            "Educational Attainment | Some college, 2024",
            "Educational Attainment | Associate's degree, 2024",
            "Educational Attainment | Bachelor's degree, 2024",
            "Educational Attainment | Master's degree, 2024",
            "Educational Attainment | Professional school degree, 2024",
            "Educational Attainment | Doctorate degree, 2024");

    /*List<String> education = Arrays.asList(
            "Educational Attainment | Population 25 years and over, 2026",
            "Educational Attainment | Less than high school diploma, 2026",
            "Educational Attainment | High school graduate (includes equivalency), 2026",
            "Educational Attainment | Some college, 2026",
            "Educational Attainment | Associate's degree, 2026",
            "Educational Attainment | Bachelor's degree, 2026",
            "Educational Attainment | Master's degree, 2026",
            "Educational Attainment | Professional school degree, 2026",
            "Educational Attainment | Doctorate degree, 2026");*/

    /*List<String> age = Arrays.asList(
		        "Age | Under 5 years, 2020",
		        "Age | 5 to 9 years, 2020", 
		        "Age | 10 to 14 years, 2020", 
		        "Age | 15 to 17 years, 2020", 
		        "Age | 18 and 19 years, 2020",
		        "Age | 20 to 24 years, 2020",
		        "Age | 25 to 34 years, 2020",
		        "Age | 35 to 44 years, 2020",
		        "Age | 45 to 54 years, 2020",
		        "Age | 55 to 64 years, 2020",
		        "Age | 65 to 74 years, 2020",
		        "Age | 75 to 84 years, 2020",
		        "Age | 85 years and over, 2020");*/

    List<String> age = Arrays.asList(
            "Age | Under 5 years, 2024",
            "Age | 5 to 9 years, 2024",
            "Age | 10 to 14 years, 2024",
            "Age | 15 to 17 years, 2024",
            "Age | 18 and 19 years, 2024",
            "Age | 20 to 24 years, 2024",
            "Age | 25 to 34 years, 2024",
            "Age | 35 to 44 years, 2024",
            "Age | 45 to 54 years, 2024",
            "Age | 55 to 64 years, 2024",
            "Age | 65 to 74 years, 2024",
            "Age | 75 to 84 years, 2024",
            "Age | 85 years and over, 2024");

    /*List<String> age = Arrays.asList(
            "Age | Under 5 years, 2026",
            "Age | 5 to 9 years, 2026",
            "Age | 10 to 14 years, 2026",
            "Age | 15 to 17 years, 2026",
            "Age | 18 and 19 years, 2026",
            "Age | 20 to 24 years, 2026",
            "Age | 25 to 34 years, 2026",
            "Age | 35 to 44 years, 2026",
            "Age | 45 to 54 years, 2026",
            "Age | 55 to 64 years, 2026",
            "Age | 65 to 74 years, 2026",
            "Age | 75 to 84 years, 2026",
            "Age | 85 years and over, 2026");*/

    /*List<String> citizenship = Arrays.asList(
		        "Citizenship Status | Total population in the United States, 2020",
		        "Citizenship Status | U.S. citizen, born in the United States, 2020", 
		        "Citizenship Status | U.S. citizen, born in Puerto Rico or U.S. Island Areas, 2020", 
		        "Citizenship Status | U.S. citizen, born abroad of American parent(s), 2020", 
		        "Citizenship Status | U.S. citizen by naturalization, 2020",
		        "Citizenship Status | Not a U.S. citizen, 2020");*/

    List<String> citizenship = Arrays.asList(
            "Citizenship Status | Total population in the United States, 2024",
            "Citizenship Status | U.S. citizen, born in the United States, 2024",
            "Citizenship Status | U.S. citizen, born in Puerto Rico or U.S. Island Areas, 2024",
            "Citizenship Status | U.S. citizen, born abroad of American parent(s), 2024",
            "Citizenship Status | U.S. citizen by naturalization, 2024",
            "Citizenship Status | Not a U.S. citizen, 2024");

      /*List<String> citizenship = Arrays.asList(
              "Citizenship Status | Total population in the United States, 2026",
              "Citizenship Status | U.S. citizen, born in the United States, 2026",
              "Citizenship Status | U.S. citizen, born in Puerto Rico or U.S. Island Areas, 2026",
              "Citizenship Status | U.S. citizen, born abroad of American parent(s), 2026",
              "Citizenship Status | U.S. citizen by naturalization, 2026",
              "Citizenship Status | Not a U.S. citizen, 2026");*/
    
    demographicOverview = new HashMap<>();
    demographicOverview.put("POPULATION & SEX", populationAndGender);
    demographicOverview.put("AGE", age);
    demographicOverview.put("RACE & ETHNICITY", raceAndEthnicity);
    demographicOverview.put("INCOME", income);
    demographicOverview.put("EDUCATION", education);
    demographicOverview.put("LANGUAGE", languages);
    demographicOverview.put("CITIZENSHIP", citizenship);
    demographicOverview.put("HOUSING", housing);
    
  }
  
  //housing details
  static {
    /*List<String> housing_counts = Arrays.asList(
        "Housing Units [B25001] | Housing units, 2021",
        "Occupancy Status [B25002] | Housing units | Occupied, 2021", 
        "Occupancy Status [B25002] | Housing units | Vacant, 2021",
        "Tenure [B25003] | Occupied housing units | Owner occupied, 2021",
        "Tenure [B25003] | Occupied housing units | Renter occupied, 2021"
        );*/
	  
	  /*List<String> housing_counts = Arrays.asList(
		        "Housing Units, 2020",
		        "Housing Occupancy Status | Occupied, 2020", 
		        "Housing Occupancy Status | Vacant, 2020",
		        "Housing Tenure | Owner occupied, 2020",
		        "Housing Tenure | Renter occupied, 2020"
		        );*/

    List<String> housing_counts = Arrays.asList(
            "Housing Units, 2024",
            "Housing Occupancy Status | Occupied, 2024",
            "Housing Occupancy Status | Vacant, 2024",
            "Housing Tenure | Owner occupied, 2024",
            "Housing Tenure | Renter occupied, 2024"
    );

    /*List<String> housing_counts = Arrays.asList(
            "Housing Units, 2026",
            "Housing Occupancy Status | Occupied, 2026",
            "Housing Occupancy Status | Vacant, 2026",
            "Housing Tenure | Owner occupied, 2026",
            "Housing Tenure | Renter occupied, 2026"
      );*/
    
    /*List<String> building_size = Arrays.asList(
        "Units In Structure [B25024] | Housing units | 1, detached, 2021",
        "Units In Structure [B25024] | Housing units | 1, attached, 2021",
        "Units In Structure [B25024] | Housing units | 2, 2021",
        "Units In Structure [B25024] | Housing units | 3 or 4, 2021",
        "Units In Structure [B25024] | Housing units | 5 to 9, 2021",
        "Units In Structure [B25024] | Housing units | 10 to 19, 2021",
        "Units In Structure [B25024] | Housing units | 20 to 49, 2021",
        "Units In Structure [B25024] | Housing units | 50 or more, 2021", 
        "Units In Structure [B25024] | Housing units | Mobile home, 2021", 
        "Units In Structure [B25024] | Housing units | Boat, RV, van, etc., 2021"
        );*/
    
    /*List<String> building_size = Arrays.asList(
            "Housing Units in Structure | 1, detached, 2020",
            "Housing Units in Structure | 1, attached, 2020",
            "Housing Units in Structure | 2, 2020",
            "Housing Units in Structure | 3 or 4, 2020",
            "Housing Units in Structure | 5 to 9, 2020",
            "Housing Units in Structure | 10 to 19, 2020",
            "Housing Units in Structure | 20 to 49, 2020",
            "Housing Units in Structure | 50 or more, 2020", 
            "Housing Units in Structure | Mobile home, 2020", 
            "Housing Units in Structure | Boat, RV, van, etc., 2020"
            );*/

    List<String> building_size = Arrays.asList(
            "Housing Units in Structure | 1, detached, 2024",
            "Housing Units in Structure | 1, attached, 2024",
            "Housing Units in Structure | 2, 2024",
            "Housing Units in Structure | 3 or 4, 2024",
            "Housing Units in Structure | 5 to 9, 2024",
            "Housing Units in Structure | 10 to 19, 2024",
            "Housing Units in Structure | 20 to 49, 2024",
            "Housing Units in Structure | 50 or more, 2024",
            "Housing Units in Structure | Mobile home, 2024",
            "Housing Units in Structure | Boat, RV, van, etc., 2024"
    );

      /*List<String> building_size = Arrays.asList(
              "Housing Units in Structure | 1, detached, 2026",
              "Housing Units in Structure | 1, attached, 2026",
              "Housing Units in Structure | 2, 2026",
              "Housing Units in Structure | 3 or 4, 2026",
              "Housing Units in Structure | 5 to 9, 2026",
              "Housing Units in Structure | 10 to 19, 2026",
              "Housing Units in Structure | 20 to 49, 2026",
              "Housing Units in Structure | 50 or more, 2026",
              "Housing Units in Structure | Mobile home, 2026",
              "Housing Units in Structure | Boat, RV, van, etc., 2026"
      );*/
    
    /*List<String> year_moved_in = Arrays.asList(
        "Median Year Householder Moved Into Unit By Tenure [B25039] | Median year householder moved into unit | Occupied housing units, 2021",
        "Median Year Householder Moved Into Unit By Tenure [B25039] | Median year householder moved into unit | Owner occupied, 2021", 
        "Median Year Householder Moved Into Unit By Tenure [B25039] | Median year householder moved into unit | Renter occupied, 2021",
        "Tenure By Year Householder Moved Into Unit [B25038] | Occupied housing units, 2021", 
        "Tenure By Year Householder Moved Into Unit [B25038] | Occupied housing units | Owner occupied, 2021",
        "Tenure By Year Householder Moved Into Unit [B25038] | Occupied housing units | Owner occupied | Moved in 2017 or later, 2021", 
        "Tenure By Year Householder Moved Into Unit [B25038] | Occupied housing units | Owner occupied | Moved in 2015 to 2016, 2021",
        "Tenure By Year Householder Moved Into Unit [B25038] | Occupied housing units | Owner occupied | Moved in 2010 to 2014, 2021",
        "Tenure By Year Householder Moved Into Unit [B25038] | Occupied housing units | Owner occupied | Moved in 2000 to 2009, 2021",
        "Tenure By Year Householder Moved Into Unit [B25038] | Occupied housing units | Owner occupied | Moved in 1990 to 1999, 2021",
        "Tenure By Year Householder Moved Into Unit [B25038] | Occupied housing units | Owner occupied | Moved in 1989 or earlier, 2021",
        "Tenure By Year Householder Moved Into Unit [B25038] | Occupied housing units | Renter occupied, 2021",
        "Tenure By Year Householder Moved Into Unit [B25038] | Occupied housing units | Renter occupied | Moved in 2017 or later, 2021",
        "Tenure By Year Householder Moved Into Unit [B25038] | Occupied housing units | Renter occupied | Moved in 2015 to 2016, 2021",
        "Tenure By Year Householder Moved Into Unit [B25038] | Occupied housing units | Renter occupied | Moved in 2010 to 2014, 2021",
        "Tenure By Year Householder Moved Into Unit [B25038] | Occupied housing units | Renter occupied | Moved in 2000 to 2009, 2021",
        "Tenure By Year Householder Moved Into Unit [B25038] | Occupied housing units | Renter occupied | Moved in 1990 to 1999, 2021",
        "Tenure By Year Householder Moved Into Unit [B25038] | Occupied housing units | Renter occupied | Moved in 1989 or earlier, 2021"
        );*/
    
    List<String> year_moved_in = Arrays.asList(
            "Year Moved In | Occupied housing units, 2024",
            "Year Moved In | Moved in 2017 or later, 2024",
            "Year Moved In | Moved in 2015 to 2016, 2024",
            "Year Moved In | Moved in 2010 to 2014, 2024",
            "Year Moved In | Moved in 2000 to 2009, 2024",
            "Year Moved In | Moved in 1990 to 1999, 2024",
            "Year Moved In | Moved in 1989 or earlier, 2024"
            );
    
    /*List<String> year_built = Arrays.asList(
        "Median Year Structure Built [B25035] | Median year structure built, 2021",
        "Year Structure Built [B25034] | Housing units, 2021",
        "Year Structure Built [B25034] | Housing units | Built 2014 or later, 2021",
        "Year Structure Built [B25034] | Housing units | Built 2010 to 2013, 2021",
        "Year Structure Built [B25034] | Housing units | Built 2000 to 2009, 2021",
        "Year Structure Built [B25034] | Housing units | Built 1990 to 1999, 2021",
        "Year Structure Built [B25034] | Housing units | Built 1980 to 1989, 2021",
        "Year Structure Built [B25034] | Housing units | Built 1970 to 1979, 2021",
        "Year Structure Built [B25034] | Housing units | Built 1960 to 1969, 2021",
        "Year Structure Built [B25034] | Housing units | Built 1950 to 1959, 2021",
        "Year Structure Built [B25034] | Housing units | Built 1940 to 1949, 2021",
        "Year Structure Built [B25034] | Housing units | Built 1939 or earlier, 2021"
        );*/
    
    List<String> year_built = Arrays.asList(
         "Housing Median Year Structure Built, 2024",
         "Housing Year Structure Built | Built 2014 or later, 2024",
         "Housing Year Structure Built | Built 2010 to 2013, 2024",
         "Housing Year Structure Built | Built 2000 to 2009, 2024",
         "Housing Year Structure Built | Built 1990 to 1999, 2024",
         "Housing Year Structure Built | Built 1980 to 1989, 2024",
         "Housing Year Structure Built | Built 1970 to 1979, 2024",
         "Housing Year Structure Built | Built 1960 to 1969, 2024",
         "Housing Year Structure Built | Built 1950 to 1959, 2024",
         "Housing Year Structure Built | Built 1940 to 1949, 2024",
         "Housing Year Structure Built | Built 1939 or earlier, 2024"
         );
    
    /*List<String> rent = Arrays.asList(
        "Median Gross Rent (Dollars) [B25064] | Median gross rent, 2021",
        "Gross Rent [B25063] | Renter-occupied housing units, 2021",
        "Gross Rent [B25063] | Renter-occupied housing units | With cash rent | Less than $100, 2021",
        "Gross Rent [B25063] | Renter-occupied housing units | With cash rent | $100 to $149, 2021",
        "Gross Rent [B25063] | Renter-occupied housing units | With cash rent | $150 to $199, 2021",
        "Gross Rent [B25063] | Renter-occupied housing units | With cash rent | $200 to $249, 2021",
        "Gross Rent [B25063] | Renter-occupied housing units | With cash rent | $250 to $299, 2021",
        "Gross Rent [B25063] | Renter-occupied housing units | With cash rent | $300 to $349, 2021",
        "Gross Rent [B25063] | Renter-occupied housing units | With cash rent | $350 to $399, 2021",
        "Gross Rent [B25063] | Renter-occupied housing units | With cash rent | $400 to $449, 2021",
        "Gross Rent [B25063] | Renter-occupied housing units | With cash rent | $450 to $499, 2021",
        "Gross Rent [B25063] | Renter-occupied housing units | With cash rent | $500 to $549, 2021",
        "Gross Rent [B25063] | Renter-occupied housing units | With cash rent | $550 to $599, 2021",
        "Gross Rent [B25063] | Renter-occupied housing units | With cash rent | $600 to $649, 2021",
        "Gross Rent [B25063] | Renter-occupied housing units | With cash rent | $650 to $699, 2021",
        "Gross Rent [B25063] | Renter-occupied housing units | With cash rent | $700 to $749, 2021",
        "Gross Rent [B25063] | Renter-occupied housing units | With cash rent | $750 to $799, 2021",
        "Gross Rent [B25063] | Renter-occupied housing units | With cash rent | $800 to $899, 2021",
        "Gross Rent [B25063] | Renter-occupied housing units | With cash rent | $900 to $999, 2021",
        "Gross Rent [B25063] | Renter-occupied housing units | With cash rent | $1,000 to $1,249, 2021",
        "Gross Rent [B25063] | Renter-occupied housing units | With cash rent | $1,250 to $1,499, 2021",
        "Gross Rent [B25063] | Renter-occupied housing units | With cash rent | $1,500 to $1,999, 2021",
        "Gross Rent [B25063] | Renter-occupied housing units | With cash rent | $2,000 to $2,499, 2021",
        "Gross Rent [B25063] | Renter-occupied housing units | With cash rent | $2,500 to $2,999, 2021",
        "Gross Rent [B25063] | Renter-occupied housing units | With cash rent | $3,000 to $3,499, 2021",
        "Gross Rent [B25063] | Renter-occupied housing units | With cash rent | $3,500 or more, 2021"
        );*/
    
    /*List<String> rent = Arrays.asList(
            "Housing Median Gross Rent, 2020",
            "Housing Gross Rent | Renter-occupied housing units, 2020",
            "Housing Gross Rent | Less than $250, 2020",
            "Housing Gross Rent | $250 to $499, 2020",
            "Housing Gross Rent | $500 to $749, 2020",
            "Housing Gross Rent | $750 to $999, 2020",
            "Housing Gross Rent | $1,000 to $1,499, 2020",
            "Housing Gross Rent | $1,500 to $1,999, 2020",
            "Housing Gross Rent | $2,000 to $2,499, 2020",
            "Housing Gross Rent | $2,500 to $2,999, 2020",
            "Housing Gross Rent | $3,000 to $3,499, 2020",
            "Housing Gross Rent | $3,500 or more, 2020"
            );*/

    List<String> rent = Arrays.asList(
            "Housing Median Gross Rent, 2024",
            "Housing Gross Rent | Renter-occupied housing units, 2024",
            "Housing Gross Rent | Less than $250, 2024",
            "Housing Gross Rent | $250 to $499, 2024",
            "Housing Gross Rent | $500 to $749, 2024",
            "Housing Gross Rent | $750 to $999, 2024",
            "Housing Gross Rent | $1,000 to $1,499, 2024",
            "Housing Gross Rent | $1,500 to $1,999, 2024",
            "Housing Gross Rent | $2,000 to $2,499, 2024",
            "Housing Gross Rent | $2,500 to $2,999, 2024",
            "Housing Gross Rent | $3,000 to $3,499, 2024",
            "Housing Gross Rent | $3,500 or more, 2024"
    );

      /*List<String> rent = Arrays.asList(
              "Housing Median Gross Rent, 2026",
              "Housing Gross Rent | Renter-occupied housing units, 2026",
              "Housing Gross Rent | Less than $250, 2026",
              "Housing Gross Rent | $250 to $499, 2026",
              "Housing Gross Rent | $500 to $749, 2026",
              "Housing Gross Rent | $750 to $999, 2026",
              "Housing Gross Rent | $1,000 to $1,499, 2026",
              "Housing Gross Rent | $1,500 to $1,999, 2026",
              "Housing Gross Rent | $2,000 to $2,499, 2026",
              "Housing Gross Rent | $2,500 to $2,999, 2026",
              "Housing Gross Rent | $3,000 to $3,499, 2026",
              "Housing Gross Rent | $3,500 or more, 2026"
      );*/
    
    /*List<String> value = Arrays.asList(
        "Median Value (Dollars) [B25077] | Median value (dollars), 2021",
        "Value [B25075] | Owner-occupied housing units, 2021",
        "Value [B25075] | Owner-occupied housing units | Less than $10,000, 2021",
        "Value [B25075] | Owner-occupied housing units | $10,000 to $14,999, 2021",
        "Value [B25075] | Owner-occupied housing units | $15,000 to $19,999, 2021",
        "Value [B25075] | Owner-occupied housing units | $20,000 to $24,999, 2021",
        "Value [B25075] | Owner-occupied housing units | $25,000 to $29,999, 2021",
        "Value [B25075] | Owner-occupied housing units | $30,000 to $34,999, 2021",
        "Value [B25075] | Owner-occupied housing units | $35,000 to $39,999, 2021",
        "Value [B25075] | Owner-occupied housing units | $40,000 to $49,999, 2021",
        "Value [B25075] | Owner-occupied housing units | $50,000 to $59,999, 2021",
        "Value [B25075] | Owner-occupied housing units | $60,000 to $69,999, 2021",
        "Value [B25075] | Owner-occupied housing units | $70,000 to $79,999, 2021",
        "Value [B25075] | Owner-occupied housing units | $80,000 to $89,999, 2021",
        "Value [B25075] | Owner-occupied housing units | $90,000 to $99,999, 2021",
        "Value [B25075] | Owner-occupied housing units | $100,000 to $124,999, 2021",
        "Value [B25075] | Owner-occupied housing units | $125,000 to $149,999, 2021",
        "Value [B25075] | Owner-occupied housing units | $150,000 to $174,999, 2021",
        "Value [B25075] | Owner-occupied housing units | $175,000 to $199,999, 2021",
        "Value [B25075] | Owner-occupied housing units | $200,000 to $249,999, 2021",
        "Value [B25075] | Owner-occupied housing units | $250,000 to $299,999, 2021",
        "Value [B25075] | Owner-occupied housing units | $300,000 to $399,999, 2021",
        "Value [B25075] | Owner-occupied housing units | $400,000 to $499,999, 2021",
        "Value [B25075] | Owner-occupied housing units | $500,000 to $749,999, 2021",
        "Value [B25075] | Owner-occupied housing units | $750,000 to $999,999, 2021",
        "Value [B25075] | Owner-occupied housing units | $1,000,000 to $1,499,999, 2021",
        "Value [B25075] | Owner-occupied housing units | $1,500,000 to $1,999,999, 2021",
        "Value [B25075] | Owner-occupied housing units | $2,000,000 or more, 2021"
        );*/
    
    /*
    Housing Value 2020
    List<String> value = Arrays.asList(
            "Housing Value | Owner-occupied housing units, 2020",
            "Housing Value | Less than $50,000, 2020",
            "Housing Value | $50,000 to $99,999, 2020",
            "Housing Value | $100,000 to $149,999, 2020",
            "Housing Value | $150,000 to $199,999, 2020",
            "Housing Value | $200,000 to $299,999, 2020",
            "Housing Value | $300,000 to $499,999, 2020",
            "Housing Value | $500,000 to $749,999, 2020",
            "Housing Value | $750,000 to $999,999, 2020",
            "Housing Value | $1,000,000 to $1,499,999, 2020",
            "Housing Value | $1,500,000 to $1,999,999, 2020",
            "Housing Value | $2,000,000 or more, 2020"
            );*/

    List<String> value = Arrays.asList(
            "Housing Value | Owner-occupied housing units, 2024",
            "Housing Value | Less than $50,000, 2024",
            "Housing Value | $50,000 to $99,999, 2024",
            "Housing Value | $100,000 to $149,999, 2024",
            "Housing Value | $150,000 to $199,999, 2024",
            "Housing Value | $200,000 to $299,999, 2024",
            "Housing Value | $300,000 to $499,999, 2024",
            "Housing Value | $500,000 to $749,999, 2024",
            "Housing Value | $750,000 to $999,999, 2024",
            "Housing Value | $1,000,000 to $1,499,999, 2024",
            "Housing Value | $1,500,000 to $1,999,999, 2024",
            "Housing Value | $2,000,000 or more, 2024"
    );

    /*
    Housing Value 2026
    List<String> value = Arrays.asList(
              "Housing Value | Owner-occupied housing units, 2026",
              "Housing Value | Less than $50,000, 2026",
              "Housing Value | $50,000 to $99,999, 2026",
              "Housing Value | $100,000 to $149,999, 2026",
              "Housing Value | $150,000 to $199,999, 2026",
              "Housing Value | $200,000 to $299,999, 2026",
              "Housing Value | $300,000 to $499,999, 2026",
              "Housing Value | $500,000 to $749,999, 2026",
              "Housing Value | $750,000 to $999,999, 2026",
              "Housing Value | $1,000,000 to $1,499,999, 2026",
              "Housing Value | $1,500,000 to $1,999,999, 2026",
              "Housing Value | $2,000,000 or more, 2026"
      );
    */
    housingDetail = new HashMap<>();


    housingDetail.put("HOUSING COUNTS", updateDatasetYearsInList(housing_counts, previousYear));
    housingDetail.put("BUILDING SIZE", updateDatasetYearsInList(building_size, previousYear));
//    housingDetail.put("YEAR MOVED IN", year_moved_in);
//    housingDetail.put("YEAR BUILT", year_built);
    housingDetail.put("RENT", updateDatasetYearsInList(rent, previousYear));
    housingDetail.put("VALUE", updateDatasetYearsInList(value, previousYear));

  }
  
  //cex spotlight
  static {
    
    List<String> population_income = Arrays.asList(
        "Population (Pop), 2020",
        "Households (HHs), 2020",
        "Average Household Inc., Average ($), 2020",
        "Household Income, Median ($), 2020",
        "Households w/Income $100,000 and Over, 2020",
        "Households w/ Income $200,000 and Over, 2020"
        );

    List<String> food_beverages = Arrays.asList(
        "Food at home, 2020",
        "Cereals and bakery products, 2020",
        "Meats, poultry, fish, and eggs, 2020",
        "Dairy products, 2020",
        "Fruits and vegetables, 2020",
        "Baby food, 2020",
        "Nonalcoholic beverages, 2020",
        "Alcoholic beverages at home, 2020",
        "Meals at restaurants, carry outs and other, 2020",
        "Alcoholic beverages away from home, 2020"
        );
    
    List<String> housing_related = Arrays.asList(
        "Shelter, 2020",
        "Owned dwellings, 2020",
        "Rented dwellings, 2020",
        "Utilities, fuels, and public services, 2020"
        );
    
    List<String> transportation = Arrays.asList(
        "Transportation, 2020",
        "Vehicle purchases (net outlay), 2020",
        "Leased and rented vehicles, 2020",
        "Gasoline and motor oil, 2020"
        );
    
    List<String> health = Arrays.asList(
        "Healthcare, 2020",
        "Health insurance, 2020",
        "Medical services, 2019",
        "Drugs, 2020",
        "Medical supplies, 2020"
        );
    
    List<String> entertainment = Arrays.asList(
        "Entertainment, 2020",
        "Fees and admissions, 2020",
        "Audio and visual equipment and services, 2020",
        "Pets, 2020",
        "Toys, games, arts and crafts, and tricycles, 2020"
        );
    
    List<String> miscellaneous = Arrays.asList(
        "Personal care products and services, 2020",
        "Reading, 2020",
        "Education, 2020",
        "Tobacco products and smoking supplies, 2020",
        "Value of savings, checking, money market, and CDs, 2020"
        );
    
    cexSpotlight = new HashMap<>();
    cexSpotlight.put("POPULATION & INCOME", population_income);
    cexSpotlight.put("FOOD & BEVERAGES", food_beverages);
    cexSpotlight.put("HOUSING RELATED", housing_related);
    cexSpotlight.put("TRANSPORTATION", transportation);
    cexSpotlight.put("HEALTH", health);
    cexSpotlight.put("ENTERTAINMENT", entertainment);
    cexSpotlight.put("MISCELLANEOUS", miscellaneous);
  }
  public static HashMap<String, List<String>> updateYearInMap(Map<String, List<String>> dataMap, String year) {
    HashMap<String, List<String>> updatedMap = new HashMap<>();

    for (HashMap.Entry<String, List<String>> entry : dataMap.entrySet()) {
      String key = entry.getKey();
      List<String> updatedList = updateDatasetYearsInList(entry.getValue(), year);
      updatedMap.put(key, updatedList);
    }

    return updatedMap;
  }

  @Before
  public void createProject() {
    driver.manage().window().maximize();
    AuthenticateInstitutionPage institutionPage = new AuthenticateInstitutionPage(driver);
    SignInPage signInPage = 
        institutionPage.institutionLogin(InstitutionUser.INSTITUTION, InstitutionUser.PASSWORD);
    WelcomeScreenTutorialWindow welcomeScreenTutorialWindow = signInPage.clickSignInAsGuest();
    NewProjectLocationWindow createNewProjectWindow = welcomeScreenTutorialWindow.clickClose();
    MapPage mapPage = createNewProjectWindow
        .createNewProjectWithLocationAndDefaultVariables(Location.LOS_ANGELES_CA_CITY);
    quickReportPage = 
        (QuickReportPage) mapPage.getViewChooserSection().clickNewView()
        .getActiveView().clickCreate(ViewType.QUICK_REPORT)
        .clickDone();
  }
  
  @Test
  public void testPrizmReport() {
    prizmReport = updateYearInMap(prizmReport,year);
    quickReportPage = quickReportPage.getToolbar().openReportContentListMenu()
        .clickReportContent(ReportContent.PRIZM_REPORT);
    
    verificationStep("Verify that the actual report content is the selected");
    Assert.assertEquals(
        "The report content is not the selected", 
        ReportContent.PRIZM_REPORT, 
        quickReportPage.getToolbar().getActiveReportContent());
    
    Map<String, List<String>> dataVariables = quickReportPage.getActiveView().getRowHeaders();
    
    verificationStep("Verify that the expected data variable categories appear");
    Assert.assertEquals("The key set does not match", prizmReport.keySet(), dataVariables.keySet());
    
    verificationStep("Verify that the expected data variables appear");
    for (String category: prizmReport.keySet()) {
      List<String> expectedDataVariables = prizmReport.get(category);
      List<String> actualDataVariables = dataVariables.get(category);
      Assert.assertEquals(
          "The data variables does not match for category: " + category,
          expectedDataVariables,
          actualDataVariables);
    }
  }
  
  @Test
  public void testDemographicOverview() {
    quickReportPage = quickReportPage.getToolbar().openReportContentListMenu()
        .clickReportContent(ReportContent.DEMOGRAPHIC_OVERVIEW);
    
    verificationStep("Verify that the actual report conetent is the selected");
    Assert.assertEquals(
        "The report content is not the selected", 
        ReportContent.DEMOGRAPHIC_OVERVIEW, 
        quickReportPage.getToolbar().getActiveReportContent());
    
    Map<String, List<String>> dataVariables = quickReportPage.getActiveView().getRowHeaders(); 
    
    logger.info("Verify that the expected data variable categories appear");
    Assert.assertEquals("The key set does not match", demographicOverview.keySet(),
        dataVariables.keySet());

    demographicOverview = updateYearInMap(demographicOverview,previousYear);

    logger.info("Verify that the expected data variables appear");
    for (String category: demographicOverview.keySet()) {
      List<String> expectedDataVariables = demographicOverview.get(category);
      List<String> actualDataVariables = dataVariables.get(category);
      Assert.assertEquals(
          "The data variables does not match for category: " + category,
          expectedDataVariables,
          actualDataVariables);
    }
  }
  
  @Test
  public void testHousingDetail() {
    System.out.println("Opening the report content list menu and selecting the HOUSING_DETAIL report.");
    quickReportPage = quickReportPage.getToolbar().openReportContentListMenu()
            .clickReportContent(ReportContent.HOUSING_DETAIL);

    System.out.println("Verifying that the actual report content matches the selected report.");
    verificationStep("Verify that the actual report content is the selected");
    System.out.println("Expected report content: " + ReportContent.HOUSING_DETAIL);
    System.out.println("Actual report content: " + quickReportPage.getToolbar().getActiveReportContent());
    Assert.assertEquals(
            "The report content is not the selected",
            ReportContent.HOUSING_DETAIL,
            quickReportPage.getToolbar().getActiveReportContent());

    System.out.println("Fetching row headers to get data variables.");
    Map<String, List<String>> dataVariables = quickReportPage.getActiveView().getRowHeaders();
    System.out.println("Data variables fetched: " + dataVariables);

    System.out.println("Verifying that the expected data variable categories appear.");
    logger.info("Verify that the expected data variable categories appear");
    System.out.println("Expected categories: " + housingDetail.keySet());
    System.out.println("Actual categories: " + dataVariables.keySet());
    Assert.assertEquals("The key set does not match", housingDetail.keySet(),
            dataVariables.keySet());

    System.out.println("Verifying that the expected data variables appear for each category.");
    logger.info("Verify that the expected data variables appear");
    for (String category: housingDetail.keySet()) {
      System.out.println("Processing category: " + category);
      List<String> expectedDataVariables = housingDetail.get(category);
      List<String> actualDataVariables = dataVariables.get(category);
      System.out.println("Expected data variables for category '" + category + "': " + expectedDataVariables);
      System.out.println("Actual data variables for category '" + category + "': " + actualDataVariables);
      Assert.assertEquals(
              "The data variables do not match for category: " + category,
              expectedDataVariables,
              actualDataVariables);
    }
  }

  
  @Test
  public void testCexSpotlight() {
    quickReportPage = quickReportPage.getToolbar().openReportContentListMenu()
        .clickReportContent(ReportContent.CEX_SPOTLIGHT);
    
    verificationStep("Verify that the actual report content is the selected");
    Assert.assertEquals(
        "The report content is not the selected", 
        ReportContent.CEX_SPOTLIGHT, 
        quickReportPage.getToolbar().getActiveReportContent());
    
    Map<String, List<String>> dataVariables = quickReportPage.getActiveView().getRowHeaders(); 
    
    logger.info("Verify that the expected data variable categories appear");
    Assert.assertEquals("The key set does not match", cexSpotlight.keySet(),
        dataVariables.keySet());
    
    logger.info("Verify that the expected data variables appear");
    for (String category: cexSpotlight.keySet()) {
      List<String> expectedDataVariables = cexSpotlight.get(category);
      List<String> actualDataVariables = dataVariables.get(category);
      Assert.assertEquals(
          "The data variables does not match for category: " + category,
          expectedDataVariables,
          actualDataVariables);
    }
  }
  
}
