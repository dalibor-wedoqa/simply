package net.simplyanalytics.core.parallel;

import org.apache.commons.lang3.StringUtils;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SingleTestRunner extends BlockJUnit4ClassRunner {
  
  private static final Logger logger_ConcurrentTestRunner = LoggerFactory
      .getLogger(ConcurrentTestRunner.class);
  
  /**
   * // // // // .
   **/
  
  @SuppressWarnings("ucd")
  public SingleTestRunner(Class<?> klass) throws InitializationError {
    super(klass);
    logger_ConcurrentTestRunner.debug("ConcurrentTestRunner contructor");
    String threads = "1";
    if (StringUtils.isNotEmpty(System.getProperty("threadCount"))) {
      threads = System.getProperty("threadCount");
    }
    setScheduler(new ParallelScheduler(threads));
  }
}
