package com.fedmag.menu;

import java.util.List;

public class Request {
  List<MenuElement> currentElements;
  MenuElement invoker;

  public Request(List<MenuElement> currentElements, MenuElement invoker) {
    this.currentElements = currentElements;
    this.invoker = invoker;
  }

  public List<MenuElement> getCurrentElements() {
    return currentElements;
  }

  public MenuElement getInvoker() {
    return invoker;
  }
}
