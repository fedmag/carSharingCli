package carsharing.controllers;

import carsharing.menu.Controller;
import carsharing.menu.MenuElement;
import java.util.ArrayList;
import java.util.List;

public class MainMenuController {
  private final MenuElement exit;
  private final MenuElement logInAsAManager;
  private final MenuElement logInAsCustomer;
  private final MenuElement createCustomer;
  private final List<MenuElement> currentMenu = new ArrayList<>();

  public MainMenuController(Controller controller) {
    this.exit = new MenuElement(0, "Exit", null);
    this.logInAsAManager = new ManagerMenuController(controller).generateLoginAsManager(currentMenu);
    this.logInAsCustomer = new CustomerMenuController(controller).generateLoginAsCustomer(currentMenu);
    this.createCustomer = new CreateCustomerController(controller).generateCreateCustomer(currentMenu);
    currentMenu.addAll(List.of( logInAsAManager, logInAsCustomer, createCustomer, exit));
  }

    public List<MenuElement> initMainMenu() {
    return currentMenu;
  }
}
