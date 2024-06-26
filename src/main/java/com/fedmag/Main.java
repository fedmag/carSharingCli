package com.fedmag;

import com.fedmag.controllers.Controller;

public class Main {

  public static void main(String[] args) throws IllegalAccessException, ClassNotFoundException {

    Controller controller = new Controller();
    controller.init();
  }
}
