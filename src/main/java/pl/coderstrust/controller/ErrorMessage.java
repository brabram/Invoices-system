package pl.coderstrust.controller;

import java.util.ArrayList;
import java.util.List;

public class ErrorMessage {

  private String message;
  private List<String> details;

  protected ErrorMessage() {
  }

  public ErrorMessage(String message) {
    this.message = message;
    details = new ArrayList<>();
  }

  public ErrorMessage(String message, List<String> details) {
    this.message = message;
    this.details = details;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public List<String> getDetails() {
    return details;
  }

  public void setDetails(List<String> details) {
    this.details = details;
  }
}
