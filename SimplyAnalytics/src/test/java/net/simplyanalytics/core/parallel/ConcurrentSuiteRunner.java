package net.simplyanalytics.core.parallel;

import org.apache.commons.lang3.StringUtils;
import org.junit.internal.builders.AllDefaultPossibilitiesBuilder;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConcurrentSuiteRunner extends Suite {
  
  private static final Logger logger_ConcurrentSuiteRunner = LoggerFactory
      .getLogger(ConcurrentSuiteRunner.class);
  
  /**
   * // // // // .
   **/
  
  @SuppressWarnings("ucd")
  public ConcurrentSuiteRunner(final Class<?> klass) throws InitializationError {
    super(klass, new AllDefaultPossibilitiesBuilder(true));
    logger_ConcurrentSuiteRunner.debug("ConcurrentTestRunner contructor");
    String threads = "2";
    if (StringUtils.isNotEmpty(System.getProperty("threadCountClasses"))) {
      threads = System.getProperty("threadCountClasses");
    } else if (StringUtils.isEmpty(System.getProperty("threadCountClasses"))
        && StringUtils.isNotEmpty(System.getProperty("threadCount"))) {
      threads = System.getProperty("threadCount");
    }
    setScheduler(new ParallelScheduler(threads));
  }
}
