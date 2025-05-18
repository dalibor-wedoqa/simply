package net.simplyanalytics.core.rules;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class DocumentationRule implements TestRule {
  
  private static boolean documentation;
  private static File file;
  
  static {
    String documentation = System.getProperty("documentation");
    if (documentation != null) {
      DocumentationRule.documentation = Boolean.parseBoolean(documentation);
    } else {
      DocumentationRule.documentation = true;
    }
    if (DocumentationRule.documentation) {
      file = new File("documentation" + File.separator + "PassedTests__"
        + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd__HH-mm")) + ".txt");
      try {
        file.createNewFile();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    
  }
  
  @Override
  public Statement apply(Statement base, Description description) {
    return new Statement() {
      @Override
      public void evaluate() throws Throwable {
        base.evaluate();
        if (documentation) {
          String[] stepsAndVerifications = getStepsAndVerifications(description);
          
          FileWriter fileWriter = new FileWriter(file, true);
          try {
            fileWriter.write(description.getClassName() + "." + description.getMethodName() + "\n");
            
            fileWriter.write("Steps:\n");
            fileWriter.write(stepsAndVerifications[0]);
            fileWriter.write("\n");
            fileWriter.write("\n");
            
            fileWriter.write("Expected results:\n");
            fileWriter.write(stepsAndVerifications[1]);
            fileWriter.write("\n");
            fileWriter.write("\n");
            fileWriter.write("\n");
          } finally {
            fileWriter.close();
          }
        }
      }
      
    };
  }
  
  private String[] getStepsAndVerifications(Description description) throws Exception {
    String path = "logs" + File.separator
        + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + File.separator
        + description.getClassName() + "." + description.getMethodName() + ".log";
      
    List<StringBuilder> steps = new ArrayList<StringBuilder>();
    List<String> expected = new ArrayList<String>();
    StringBuilder lastStep = null;
    int verificationCount = 0;
    boolean alreadyVerified = false;
    int offCount = 0;
    List<String> ourFile = FileUtils.readLines(new File(path), Charset.defaultCharset());
    for (String line : ourFile ) {
      line = line.replaceFirst("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2},\\d{3} ", "");
      line = line.replaceFirst("[n,i]\\.[s,q]\\.(.)+\\] -", "-~~-");
      String[] splitedLine = line.split("-~~-");
      if (splitedLine.length != 2) {
        throw new AssertionError(
            "There should be exactly two part: " + line + " count: " + splitedLine.length);
      }
      String level = splitedLine[0];
      String message = splitedLine[1];
      
      if (level.equals("WARN ")) {
        if (message.equals("off")) {
          offCount++;
        } else if (message.equals("on")) {
          offCount--;
        } else {
          throw new AssertionError("unexpected warn message: " + message);
        }
      } else if (offCount <= 0) {
        if (level.equals("DEBUG")) {
          steps.add(new StringBuilder(message));
          lastStep = steps.get(steps.size() - 1);
          alreadyVerified = false;
        } else if (level.equals("INFO ")) {
          int verificationNumber;
          
          if (expected.stream()
              .anyMatch(expectedCondition -> expectedCondition.contains(message))) {
            verificationNumber = Integer.parseInt(
                expected.stream().filter(expectedCondition -> expectedCondition.contains(message))
                    .findFirst().get().split(" - ")[0]);
          } else {
            verificationNumber = ++verificationCount;
            expected.add(verificationNumber + " - " + message);
          }
          
          if (!alreadyVerified) {
            lastStep.append(" - " + verificationNumber);
            alreadyVerified = true;
          } else {
            lastStep.append(", " + verificationNumber);
          }
          
        } else {
          // System.out.println("Ignoring line: " + line);
        }
      }
    }
    String[] result = { String.join("\n", steps), String.join("\n", expected) };
    return result;
  }
}
