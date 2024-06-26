package com.fedmag.controllers;

import com.fedmag.menu.MenuElement;
import java.util.ArrayList;
import java.util.List;

public class MainMenuController {

  private final List<MenuElement> currentMenu = new ArrayList<>();

  public MainMenuController(Controller controller) {
    MenuElement exit = new MenuElement(0, "Exit", null);
    MenuElement logInAsAManager =
        new ManagerMenuController(controller).generateLoginAsManager(currentMenu);
    MenuElement logInAsCustomer =
        new CustomerMenuController(controller).generateLoginAsCustomer(currentMenu);
    MenuElement createCustomer =
        new CreateCustomerController(controller).generateCreateCustomer(currentMenu);
    currentMenu.addAll(List.of(logInAsAManager, logInAsCustomer, createCustomer, exit));
  }

  public List<MenuElement> initMainMenu() {
    return currentMenu;
  }
}
