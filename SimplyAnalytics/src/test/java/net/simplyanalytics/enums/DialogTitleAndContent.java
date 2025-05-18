package net.simplyanalytics.enums;

import java.util.Arrays;
import java.util.List;

public enum DialogTitleAndContent {
  
  INSTITUTION_USERNAME_REQUIRED(
      "Authentication Failed", 
      Arrays.asList("Please provide a valid institution name to sign in.")),
  INSTITUTION_PASSWORD_REQUIRED(
      "Authentication Failed",
      Arrays.asList("Please provide a valid password to sign in.")),
  INSTITUTION_FAILED(
      "Authentication Failed",
      Arrays.asList("Please provide a valid institution name and password to sign in.")),
  
  USER_EMAIL_REQUIRED(
      "Authentication Failed",
      Arrays.asList("Please provide a valid email address to sign in.")),
  USER_PASSWORD_REQUIRED(
      "Authentication Failed",
      Arrays.asList("Please provide a valid password to sign in.")),
  USER_FAILED(
      "Authentication Failed", 
      Arrays.asList(
          "Please provide a valid email address and password to sign in.",
          "If you have forgotten your password we can send you an email with a new password.")),
  
  USER_CREATED("Account Created", 
      Arrays.asList(
          "Your account has been created. Please check your email for details on activating it.",
          "If you do not receive an activation email in the next five minutes please check your spam folder.")),
  USER_INACTIVE(
      "User Activation Required",
      Arrays.asList(
          "This account has been registered but has not yet been activated. "
              + "Please activate your account by clicking on the link in the activation email.",
          "Would you like us to resend the activation email?")),
  USER_ACTIVATED(
      "Your account has been activated",
      Arrays.asList("Please return to the GRI QA Test Account sign-in page to use SimplyAnalytics.")),
  RESET_PASSWORD(
      "Reset Password", 
      Arrays.asList(
          "Your password has been reset.", 
          "Please check your email for the details of your new password.")),
  CHANGE_EMAIL_ADDRESS(
      "Change Email Address", 
      Arrays.asList(
          "Your email address has been updated.")),
  
  ;
  
  private final String title;
  private final List<String> messages;
  
  DialogTitleAndContent(String title, List<String> messages) {
    this.title = title;
    this.messages = messages;
  }
  
  public String getTitle() {
    return title;
  }
  
  public List<String> getMessages() {
    return messages;
  }
  
  public String getSingleMessage() {
    return messages.get(0);
  }
  
  @Override
  public String toString() {
    return "Dialog [Tittle: \"" + getTitle() + "\"; Messages: " + getMessages() + " ]";
  }
}
