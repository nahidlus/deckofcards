package com.decks.types;

public enum MimeTypes {
  JPEG("image/jpeg"),
  PNG("image/png"),
  BMP("image/bmp"),
  TIFF("image/tiff"),
  MP4("video/mp4"),
  MPEG("video/mpeg"),
  APP_JSON("application/JSON"),
  APP_ZIP("application/ZIP"),
  ARTICLE_ZIP("application/vnd.adobe.article+zip"),
  FOLIO_ZIP("application/vnd.adobe.folio+zip"),
  SYMBOL_LINK("application/vnd.adobe.symboliclink+json"),
  TTF("application/x-font-ttf"),
  OTF("application/x-font-opentype"),
  WOFF("application/x-font-woff");

  private final String value;

  private MimeTypes(String type) {
    this.value = type;
  }

  public String toString() {
    return this.value;
  }
}
