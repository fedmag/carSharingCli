package carsharing.menu;

import java.util.List;


public class MenuElement {
  int digit;
  String message;
  List<MenuElement> children;
  OnSelect onSelect;


  public MenuElement(String message, int digit) {
    this.message = message;
    this.digit = digit;
  }

  public MenuElement(int digit, String message,
      List<MenuElement> children) {
    this.digit = digit;
    this.message = message;
    this.children = children;
  }

  public int getDigit() {
    return digit;
  }

  public void setDigit(int digit) {
    this.digit = digit;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public List<MenuElement> getChildren() {
    return children;
  }

  public void setChildren(List<MenuElement> children) {
    this.children = children;
  }

  public OnSelect getOnSelect() {
    return onSelect;
  }

  public void setOnSelect(OnSelect onSelect) {
    this.onSelect = onSelect;
  }

  @Override
  public String toString() {
    return this.digit + ". " + this.message;
  }
}
