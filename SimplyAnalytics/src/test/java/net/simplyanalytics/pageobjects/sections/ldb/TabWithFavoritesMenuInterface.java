package net.simplyanalytics.pageobjects.sections.ldb;

import net.simplyanalytics.pageobjects.sections.ldb.search.RecentFavoriteMenu;

public interface TabWithFavoritesMenuInterface {
  
  RecentFavoriteMenu clickFavorites();
  
  boolean isFavoritesPresent();
}
