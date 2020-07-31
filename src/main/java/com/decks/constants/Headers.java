package com.decks.constants;

public class Headers {

  public static final String JSON_CONTENT_TYPE = "application/json;charset=UTF-8";
  public static final String JSON_CONTENT_NO_CHARSET_TYPE = "application/json";
  public static final String DEFAULT_CONTENT_TYPE = JSON_CONTENT_TYPE;


  public static HeaderItem CONTENT_TYPE = new HeaderItem("Content-Type", DEFAULT_CONTENT_TYPE);
  public static HeaderItem ACCEPT = new HeaderItem("Accept", DEFAULT_CONTENT_TYPE);
  public static HeaderItem CONTENT_LENGTH = new HeaderItem("Content-Length", "");
  public static HeaderItem AUTHORIZATION = new HeaderItem("Authorization", "");

  public static class HeaderItem {
    private String headerName;
    private String defaultValue;

    public HeaderItem(String headerName, String defaultValue) {
      this.headerName = headerName;
      this.defaultValue = defaultValue;
    }

    @Override
    public String toString() {
      return headerName;
    }

    public String defaultValue() {
      return defaultValue;
    }
  }
}
