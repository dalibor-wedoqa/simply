package net.simplyanalytics.utils;

import java.awt.Color;

import org.openqa.selenium.WebElement;

public class ColorUtils {

  public static Color getBackgroundColor(WebElement element) {
    String style = element.getAttribute("style");
    style = style.replaceAll("background.*: rgb\\(", "");
    style = style.replaceAll("\\).*", "");
    String[] rgbNumbers = style.split(",");
    int r = Integer.parseInt(rgbNumbers[0].trim());
    int g = Integer.parseInt(rgbNumbers[1].trim());
    int b = Integer.parseInt(rgbNumbers[2].trim());
    return new Color(r, g, b);
  }
  
  public static String getBackgroundHexColor(WebElement element) {
    Color color = getBackgroundColor(element);
    return getHexCode(color);
  }
  
  public static String getHexCode(Color color) {
    return String.format("%02X%02X%02X", color.getRed(), color.getGreen(), color.getBlue());
  }
}
