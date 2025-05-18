package net.simplyanalytics.containers;

import net.simplyanalytics.enums.DataVariable;
import net.simplyanalytics.enums.DataVariableRelation;

public class SimpleFilter {
  
  private DataVariable dataVariable;
  private DataVariableRelation condition;
  private String value;
  private String value2;
  
  public SimpleFilter() {
    super();
  }
  
  /**
   * Simple Filter.
   * @param dataVariable data variable
   * @param condition condition
   * @param value value
   */
  public SimpleFilter(DataVariable dataVariable, DataVariableRelation condition, String value) {
    super();
    this.dataVariable = dataVariable;
    this.condition = condition;
    this.value = value;
  }
  
  public DataVariable getDataVariable() {
    return dataVariable;
  }
  
  public void setDataVariable(DataVariable dataVariable) {
    this.dataVariable = dataVariable;
  }
  
  public DataVariableRelation getCondition() {
    return condition;
  }
  
  public void setCondition(DataVariableRelation condition) {
    this.condition = condition;
  }
  
  public String getValue() {
    return value;
  }
  
  public void setValue(String value) {
    this.value = value;
  }
  
  public String getValue2() {
    return value2;
  }
  
  public void setValue2(String value2) {
    this.value2 = value2;
  }
  
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((condition == null) ? 0 : condition.hashCode());
    result = prime * result + ((dataVariable == null) ? 0 : dataVariable.hashCode());
    result = prime * result + ((value == null) ? 0 : value.hashCode());
    result = prime * result + ((value2 == null) ? 0 : value2.hashCode());
    return result;
  }
  
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    SimpleFilter other = (SimpleFilter) obj;
    if (condition != other.condition) {
      return false;
    }
    if (dataVariable != other.dataVariable) {
      return false;
    }
    if (value == null) {
      if (other.value != null) {
        return false;
      }
    } else if (!value.equals(other.value)) {
      return false;
    }
    if (value2 == null) {
      return other.value2 == null;
    } else {
      return value2.equals(other.value2);
    }
  }
  
  @Override
  public String toString() {
    return "SimpleFilter [dataVariable=" + dataVariable + ", condition=" + condition + ", value="
      + value + ", value2=" + value2 + "]";
  }
}
