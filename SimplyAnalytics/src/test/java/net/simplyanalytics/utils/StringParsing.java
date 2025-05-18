package net.simplyanalytics.utils;

import java.text.DecimalFormat;

public class StringParsing {
  
  private static DecimalFormat df = new DecimalFormat("0.00");
  
  /**
   * .
   * 
   * @param price
   *          in format $xx,xxx.00
   * @return the xxxxx integer
   */
  public static int parsePriceToInt(String price) {
    return Integer.parseInt(parsePriceToString(price));
  }
  
  public static double parsePriceToDouble(String price) {
    return Double.parseDouble(parsePriceToString(price));
  }
  
  public static String parsePriceToString(String price) {
	  if (price.contains("."))
	  price = price.substring(0 , price.indexOf(".")); // $xx,xxx.xx -> $xx,xxx
    return  price.replace(",", "") // $xx,xxx -> $xxxxx
	        .replace("$", ""); // $xxxxx -> xxxxx
  }
  
//  /**
//   * .
//   *
//   * @param percentage
//   *          in format xx.xx%
//   * @return the xx.xx double
//   */
  public static double parsePercentageToDouble(String percentage) {
    return Double.parseDouble(parsePercentageToString(percentage)); // xx.xx% -> xx.xx
  }
  
  public static String parsePercentageToString(String percentage) {
    return percentage.replace("%", ""); // xx.xx% -> xx.xx
  }
  
  public static String roundString(String numberString) {
    double number = Double.parseDouble(numberString);
    return df.format(number);  //xx.xxxx -> xx.xx
  }
  
  public static String cutZeros(String numberString) {
    return numberString.split("\\.0+$")[0];  // xx.000 -> xx
  }
  
  /*Round given double value on the given number of decimals*/
  public static double roundString(Double d, int numberOfDecimals) {
    DecimalFormat df2 = new DecimalFormat();
    String pattern;
    if (numberOfDecimals != 0)
      pattern = "0.";
    else
      pattern = "0";
    
    for(int i = 0; i < numberOfDecimals; i++)
      pattern = pattern.concat("0");
    df2.applyPattern(pattern);
    return Double.parseDouble(df2.format(d));
  }

  public static String formatXPathString(String input) {
    if (input == null) {
      return null;
    }

    // If the string contains both single and double quotes, use XPath concat function
    if (input.contains("\"") && input.contains("'")) {
      // Split the string on the single quote and use concat
      return buildXPathConcat(input);
    } else {
      // Regular escaping
      return input.replace("&", "&amp;")
              .replace("<", "&lt;")
              .replace(">", "&gt;")
              .replace("\\", "\\\\")
              // Escape single quotes if using single quotes in XPath expression
              .replace("'", "\\'");
    }
  }

  // Builds an XPath concat() expression for strings with both ' and "
  private static String buildXPathConcat(String input) {
    String[] parts = input.split("\"");
    StringBuilder xpathConcat = new StringBuilder("concat(");
    for (int i = 0; i < parts.length; i++) {
      xpathConcat.append("\"").append(parts[i]).append("\"");
      if (i < parts.length - 1) {
        xpathConcat.append(", '\"', ");
      }
    }
    xpathConcat.append(")");
    return xpathConcat.toString();
  }
  
}
