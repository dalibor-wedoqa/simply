package net.simplyanalytics.enums;

import java.awt.Point;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum BusinessSearchConditions {
  
  CONTAINS("contains"),
  STARTS("starts with"),
  GREATER("is greater than"),
  LESS("is less than"),
  EQUAL("is equal to"),
  GREATEREQUAL("is greater than or equal to"),
  LESSEQUAL("is less than or equal to"),
  NOT_EQUAL("is not equal to"),
  ;
  
  private String name;
  
  BusinessSearchConditions(String name) {
    this.name = name;
  }
  
  public String getName() {
    return name;
  }
  
  private static Map<String, BusinessSearchConditions> byName = Stream.of(values())
      .collect(Collectors.toMap(BusinessSearchConditions::getName, Function.identity()));
  
  public static BusinessSearchConditions getByName(String name) {
    BusinessSearchConditions match = byName.get(name);
    if (byName == null) {
      throw new AssertionError("No condition found with name: " + name);
    }
    return match;
  }
  
  public static Predicate<Point> getNumericCondition(BusinessSearchConditions condition) {
    switch (condition) {
      
      case GREATER:
        return new Predicate<Point>() {
          @Override
          public boolean test(Point t) {
            return t.getX() > t.getY();
          }
        };
      
      case LESS:
        return new Predicate<Point>() {
          @Override
          public boolean test(Point t) {
            return t.getX() < t.getY();
          }
        };
      
      case EQUAL:
        return new Predicate<Point>() {
          @Override
          public boolean test(Point t) {
            return t.getX() == t.getY();
          }
        };
      
      case LESSEQUAL:
        return new Predicate<Point>() {
          @Override
          public boolean test(Point t) {
            return t.getX() <= t.getY();
          }
        };
      
      case GREATEREQUAL:
        return new Predicate<Point>() {
          @Override
          public boolean test(Point t) {
            return t.getX() >= t.getY();
          }
        };
      
      case NOT_EQUAL:
        return new Predicate<Point>() {
          @Override
          public boolean test(Point t) {
            return t.getX() != t.getY();
          }
        };
      
      default:
        throw new AssertionError("The condition is not supported: " + condition);
    }
  }
  
  @Override
  public String toString() {
    return getName();
  }
}
