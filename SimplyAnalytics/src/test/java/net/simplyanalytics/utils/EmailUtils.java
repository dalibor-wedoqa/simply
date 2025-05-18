package net.simplyanalytics.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.SubjectTerm;

import net.simplyanalytics.enums.FileFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import io.qameta.allure.Step;

/**
 * http://angiejones.tech/test-automation-to-verify-email/
 */
public class EmailUtils {
  
  private static final Logger logger = LoggerFactory.getLogger(EmailUtils.class);
  
  private Folder folder;
  
  public enum EmailFolder {
    INBOX("INBOX"), SPAM("SPAM");
    
    private String text;
    
    private EmailFolder(String text) {
      this.text = text;
    }
    
    public String getText() {
      return text;
    }
  }
  
  /**
   * Uses email.username and email.password properties from the properties file.
   * Reads from Inbox folder of the email application.
   * 
   * @throws MessagingException Messaging Exception
   */
  public EmailUtils() throws MessagingException {
    this(EmailFolder.INBOX);
  }
  
  /**
   * Uses username and password in properties file to read from a given folder of
   * the email application.
   * 
   * @param emailFolder Folder in email application to interact with
   * @throws MessagingException MessagingException
   */
  public EmailUtils(EmailFolder emailFolder) throws MessagingException {
    this(getEmailUsernameFromProperties(), getEmailPasswordFromProperties(),
        getEmailServerFromProperties(), emailFolder);
  }
  
  /**
   * Connects to email server with credentials provided to read from a given
   * folder of the email application.
   * 
   * @param username    Email username (e.g. janedoe@email.com)
   * @param password    Email password
   * @param server      Email server (e.g. smtp.email.com)
   * @param emailFolder Folder in email application to interact with
   */
  public EmailUtils(String username, String password, String server, EmailFolder emailFolder)
      throws MessagingException {
    Properties props = System.getProperties();
    try {
      props.load(new FileInputStream(new File("src/test/resources/email.properties")));
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(-1);
    }
    
    Session session = Session.getInstance(props);
    Store store = session.getStore("imaps");
    store.connect(server, username, password);
    
    folder = store.getFolder(emailFolder.getText());
    folder.open(Folder.READ_WRITE);
  }
  
  // ************* GET EMAIL PROPERTIES *******************
  
  public static String getEmailAddressFromProperties() {
    return System.getProperty("email.address");
  }
  
  public static String getEmailUsernameFromProperties() {
    return System.getProperty("email.username");
  }
  
  public static String getEmailPasswordFromProperties() {
    return System.getProperty("email.password");
  }
  
  public static String getEmailProtocolFromProperties() {
    return System.getProperty("email.protocol");
  }
  
  public static int getEmailPortFromProperties() {
    return Integer.parseInt(System.getProperty("email.port"));
  }
  
  public static String getEmailServerFromProperties() {
    return System.getProperty("email.server");
  }
  
  // ************* EMAIL ACTIONS *******************
  
  public void openEmail(Message message) throws Exception {
    message.getContent();
  }
  
  public int getNumberOfMessages() throws MessagingException {
    return folder.getMessageCount();
  }
  
  public int getNumberOfUnreadMessages() throws MessagingException {
    return folder.getUnreadMessageCount();
  }
  
  /**
   * Gets a message by its position in the folder. The earliest message is indexed
   * at 1.
   */
  public Message getMessageByIndex(int index) throws MessagingException {
    return folder.getMessage(index);
  }
  
  public Message getLatestMessage() throws MessagingException {
    return getMessageByIndex(getNumberOfMessages());
  }
  
  /**
   * Gets all messages within the folder.
   */
  public Message[] getAllMessages() throws MessagingException {
    return folder.getMessages();
  }
  
  /**
   * .
   * @param maxToGet maximum number of messages to get, starting from the latest.
   *                 For example, enter 100 to get the last 100 messages received.
   */
  public Message[] getMessages(int maxToGet) throws MessagingException {
    Map<String, Integer> indices = getStartAndEndIndices(maxToGet);
    return folder.getMessages(indices.get("startIndex"), indices.get("endIndex"));
  }
  
  /**
   * Searches for messages with a specific subject.
   * 
   * @param subject     Subject to search messages for
   * @param unreadOnly  Indicate whether to only return matched messages that are
   *                    unread
   * @param maxToSearch maximum number of messages to search, starting from the
   *                    latest. For example, enter 100 to search through the last
   *                    100 messages.
   */
  public Message[] getMessagesBySubject(String subject, boolean unreadOnly, int maxToSearch)
      throws Exception {
    Map<String, Integer> indices = getStartAndEndIndices(maxToSearch);
    
    Message[] messages = folder.search(new SubjectTerm(subject),
        folder.getMessages(indices.get("startIndex"), indices.get("endIndex")));
    
    if (unreadOnly) {
      List<Message> unreadMessages = new ArrayList<Message>();
      for (Message message : messages) {
        if (isMessageUnread(message)) {
          unreadMessages.add(message);
        }
      }
      messages = unreadMessages.toArray(new Message[] {});
    }
    
    return messages;
  }
  
  /**
   * Waiting for message by subject.
   * @param subject message subject
   * @return message
   * @throws Exception Exception
   */
  public Message waitMessageBySubject(String subject) throws Exception {
    int maxWait = 30;
    int count = 0;
    List<Message> messages = Arrays.asList(getMessagesBySubject(subject, true, 10));
    while (messages.isEmpty() && count < maxWait) {
      Thread.sleep(1000);
      count++;
      messages = Arrays.asList(getMessagesBySubject(subject, true, 10));
    }
    if (messages.isEmpty()) {
      throw new Exception("The email did not arrived");
    } else {
      return messages.get(0);
    }
  }
  
  /**
   * Waiting for message by subject and message.
   * @param subject message subject
   * @return email
   * @throws Exception Exception
   */
  public Message waitMessageBySubjectAndMessage(String subject, String message) throws Exception {
    int maxWait = 60;
    int count = 0;
    logger.trace("get emails");
    List<Message> messages = Arrays.asList(getMessagesBySubject(subject, false, 10));
    while (count < maxWait) {
      for (Message email : messages) {
        //logger.trace("if");
        if (isMessageContentContains(email, message, 20)) {
          return email;
        }
      }
      //logger.trace("sleep");
      Thread.sleep(1000);
      count++;
      messages = Arrays.asList(getMessagesBySubject(subject, false, 10));
    }
    throw new Error("The email is missing");
  }
  
  /**
   * Getting value from email.
   * @param prefix prefix
   * @param message message
   * @return value
   * @throws Exception Exception
   */
  public String getValueFromMail(String prefix, Message message) throws Exception {
    BufferedReader reader = new BufferedReader(new InputStreamReader(message.getInputStream()));
    String line;
    while ((line = reader.readLine()) != null) {
      if (line.startsWith(prefix)) {
        return line.replace(prefix, "").trim();
      } else {
        List<String> rowsWithHtml = Arrays.asList(line.split("(<br>|<br\\/>)"));
        List<String> rowsWithoutHtml = rowsWithHtml.stream()
            .map(row -> row.replaceAll("<[^>]*>", "")).collect(Collectors.toList());
        Optional<String> match = rowsWithoutHtml.stream().filter(row -> row.startsWith(prefix))
            .findFirst();
        if (match.isPresent()) {
          return match.get().replace(prefix, "").trim();
        }
      }
    }
    throw new Exception(
        "The prefix not found in email\nPrefix: " + prefix + "\nContent:\n" + message.getContent());
  }
  
  /**
   * Returns HTML of the email's content.
   */
  public String getMessageContent(Message message) throws Exception {
    StringBuffer buffer = new StringBuffer();
    BufferedReader reader = new BufferedReader(new InputStreamReader(message.getInputStream()));
    String line;
   
    while ((line = reader.readLine()) != null) {
      // encoding issue
      if (line.endsWith("=")) {
        line = line.substring(0, line.length() - 1);
      }
      buffer.append(line);
    }
    return buffer.toString();
  }
  
  public String getAttachment(Message message, String regex) throws Exception {

    byte[] decodedBytes = getAttachmentInByte(message, regex);
    String decodedString = new String(decodedBytes);
    return decodedString;
    
  }
  
  public byte[] getAttachmentInByte(Message message, String regex) throws Exception {
    String encodedMessage = getMessageContent(message);
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(encodedMessage);
    if (matcher.find())
      encodedMessage = matcher.group(0);
 
    encodedMessage = encodedMessage
        .substring(encodedMessage.lastIndexOf("\"") + 1, encodedMessage.indexOf("--") - 1);
   
    byte[] decodedBytes = Base64.getDecoder().decode(encodedMessage);
    return decodedBytes;
  }


  public boolean isMessageContentContains(Message message, String content, int maxRow) throws Exception {
    StringBuffer buffer = new StringBuffer();
    BufferedReader reader = new BufferedReader(new InputStreamReader(message.getInputStream()));
    String line;
    int i = 0;
    try {
      while ((line = reader.readLine()) != null && i < maxRow) {
        //logger.trace("readline");
        // encoding issue
        if (line.endsWith("=")) {
          line = line.substring(0, line.length() - 1);
        }
        buffer.append(line);
        if (buffer.toString().contains(content)) {
          return true;
        }
        //logger.trace(line);
        i++;
      }
    } catch (Exception e) {
      logger.error(e.toString());
    }
    return false;
  }
  
  /**
   * Returns all urls from an email message with the linkText specified.
   */
  public List<String> getUrlsFromMessage(Message message, String linkText) throws Exception {
    String html = getMessageContent(message);
    List<String> allMatches = new ArrayList<String>();
    Matcher matcher = Pattern.compile("(<a [^>]+>)" + linkText + "</a>").matcher(html);
    while (matcher.find()) {
      String aTag = matcher.group(1);
      allMatches.add(aTag.substring(aTag.indexOf("http"), aTag.indexOf(">") - 1));
    }
    return allMatches;
  }
  
  private Map<String, Integer> getStartAndEndIndices(int max) throws MessagingException {
    int endIndex = getNumberOfMessages();
    int startIndex = endIndex - max;
    
    // In event that maxToGet is greater than number of messages that exist
    if (startIndex < 1) {
      startIndex = 1;
    }
    
    Map<String, Integer> indices = new HashMap<String, Integer>();
    indices.put("startIndex", startIndex);
    indices.put("endIndex", endIndex);
    
    return indices;
  }
  
  /**
   * Searches an email message for a specific string.
   */
  public boolean isTextInMessage(Message message, String text) throws Exception {
    String content = getMessageContent(message);
    
    // Some Strings within the email have whitespace and some have break coding.
    // Need to be the same.
    content = content.replace("&nbsp;", " ");
    return content.contains(text);
  }
  
  public boolean isMessageInFolder(String subject, boolean unreadOnly) throws Exception {
    int messagesFound = getMessagesBySubject(subject, unreadOnly, getNumberOfMessages()).length;
    return messagesFound > 0;
  }
  
  public boolean isMessageUnread(Message message) throws Exception {
    return !message.isSet(Flags.Flag.SEEN);
  }
  
  /**
   * Checking if export is present.
   * @param message message
   * @param fileFormat fileFormat
   * @param name name
   * @param nameVar nameVar
   * @return boolean is Export Present
   * @throws Exception Exception
   */
  @Step("Verified that the email attachment have name of the exported map: {0}")
  public boolean isExportPresent(Message message, FileFormat fileFormat, String name,
      String nameVar) throws Exception {
    logger.trace("expected names: " + name + ", " + nameVar);
    String content = getMessageContent(message);
    String prefix = "filename=\"";
    String postfix = "\"";
    
    List<String> attachmentNames = new ArrayList<>();
    
    int start = 0;
    int end = 0;
    while (start > -1 && end > -1) {
      start = content.indexOf(prefix, end);
      end = content.indexOf(postfix, start + prefix.length());
      
      if (start > -1 && end > -1) {
        attachmentNames.add(content.substring(start + prefix.length(), end));
      }
    }
    
    attachmentNames.forEach(attachmentName -> logger.trace("Attachment Name: " + attachmentName));
    return attachmentNames.stream().anyMatch(
        attachmentName -> (attachmentName.contains(name) || attachmentName.contains(nameVar))
        && attachmentName.endsWith("." + fileFormat.getExtension()));
  }
}
