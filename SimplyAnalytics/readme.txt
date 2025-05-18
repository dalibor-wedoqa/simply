Automation tests for SimplyAnalytics (SimplyMap 3)

---- TESTS -----

This repository contains tests for the following site: test.simplyanalytics.net

There are implemented test cases for:
- Institution login (valid login, empty/invalid login) 
- User login (valid user login, valid guest login, empty/invalid login) 
- User logout (guest user, valid user)
- Create new user
- Default behavior for guest user (welcome tutorial, new project for a location, default selected data variables)
- Create, Open, Edit, Delete project 
- Create new view
- Rename view
- Delete view
- Edit view
- Export tests
- Manage project 
- Specific Location, Data, Business selection tests
- Recent and Favorite
- Filtering (Comparison, Map, Ranking)
- View specific action tests (Comparison, Map, Business)
- Sorting test (Comparison, Ranking, Businesses)
- Hide Rows/Columns from reports
- Data Variable Metadata Window (from different views)
- Add Alias Location Name (from different views)
- Creating new view from a cell on an existing view (Comparison, Ranking, Related Data)
- Data Variable Metadata Window (from LDB section)
- Add Alias Location Name (from LDB section)

TODO tests for:
- Add Combination Location, Create Radius Location (Favorites/Recent Location menu actions)
---
- More real user tests (not with guest user)
------
- Canada

---- PAGE OBJECTS -----

The page objects are implemented in that way that all inner page of SimplyAnalytics contains 5 section:

+------------+--------------------------------+
|            |             Header             |
|            +--------------------------------+
|            |             Toolbar            |
|            +-----------------------+--------+
|            |                       |        |
|            |                       |        |
|   LDB      |                       |  View  |
|            |     Active View       | Chooser|
|            |   (View, Content)     |        |
|            |                       |        |
|            |                       |        |
|            |                       |        |
|            |                       |        |
+------------+-----------------------+--------+

All elementary step methods (click on a button, enter username, etc.) are implemented inside these sections. 
Block steps (login as user {username}/{password}, add a random business, etc.) are implemented inside the pages. 

Section/panel page objects should have root element or locator which is sent to the BasePage constructor. 
It will initialize web element fields under the given root.
This is required because the DOM contains previous views and selenium can find the wrong, invisible elements   

---- DOCUMENTATION -----

Working on test case automated documentation with logs and allure @Step annotations


---- TEST RUNS -----

Test cases are parallel safe, the limit is the number of concurrently allowed users for the given institution
Tests are executed on a jenkins machine without slave machine (tests are running on the same machine as the jenkins runs)
Test runs create allure reports. 

Test failures in the reports contains:
 - Successfully executed steps and the failed step
 - Logs
 - Screenshot
 - Error message