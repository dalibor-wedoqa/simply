package net.simplyanalytics.pageobjects.pages.main.export.mapexport;

import java.util.HashMap;
import java.util.Map;

public enum Font {

  ARIAL("Arial"), COURIER_NEW("Courier New"), GEORGIA("Georgia"),
  TIMES_NEW_ROMAN("Times New Roman"), VERDANA("Verdana"),;

  private String fontName;

  Font(String fontName) {
    this.fontName = fontName;
  }

  public String getFontName() {
    return fontName;
  }

  @Override
  public String toString() {
    return fontName;
  }

  private static final Map<String, Font> fontMap;

  static {
    fontMap = new HashMap<>();
    for (Font font : values()) {
      fontMap.put(font.getFontName(), font);
    }
  }

  /**
   * Getting font by name.
   * 
   * @param fontName font name
   * @return font
   */
  public static Font getFontByName(String fontName) {
    Font font = fontMap.get(fontName);
    if (font == null) {
      throw new Error("No font with the given name: " + fontName);
    }
    return font;
  }
}