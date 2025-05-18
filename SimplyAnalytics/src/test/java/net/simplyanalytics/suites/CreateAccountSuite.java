package net.simplyanalytics.suites;

import net.simplyanalytics.tests.SignInTests;

import org.junit.experimental.categories.Categories.IncludeCategory;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@IncludeCategory(CreateAccountCategory.class)
@SuiteClasses(SignInTests.class)
@SuppressWarnings("ucd")
public class CreateAccountSuite {
}
