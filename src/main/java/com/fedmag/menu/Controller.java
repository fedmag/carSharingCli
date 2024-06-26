package carsharing.menu;

import carsharing.controllers.CarController;
import carsharing.controllers.MainMenuController;
import carsharing.controllers.RentController;
import carsharing.data.car.CarDao;
import carsharing.data.car.CarDaoImpl;
import carsharing.data.company.CompanyDao;
import carsharing.data.company.CompanyDaoImpl;
import carsharing.data.customer.CustomerDao;
import carsharing.data.customer.CustomerDaoImpl;
import java.util.List;
import java.util.Scanner;

public class Controller {

  final Scanner sc = new Scanner(System.in);
  public final CarDao carDao = new CarDaoImpl();
  public final CompanyDao companyDao = new CompanyDaoImpl();
  public final CustomerDao customerDao = new CustomerDaoImpl();

  public final MainMenuController mainMenuController;
  public final CarController carController;
  public final RentController rentController;

  public Controller() throws ClassNotFoundException {
    mainMenuController = new MainMenuController(this);
    carController = new CarController(this);
    rentController = new RentController(this);
  }

  public void showMenuAndListen(List<MenuElement> elements) throws IllegalAccessException {
    elements.forEach(System.out::println);
    int selected = sc.nextInt();
    System.out.println(formatInsertedValue(selected + "\n"));
    MenuElement selectedElement = elements
        .stream()
        .filter(e -> e.digit == selected)
        .findFirst()
        .orElseThrow(() -> new IllegalAccessException("Invalid command"));
    if (selectedElement.getOnSelect() != null) {
      selectedElement.onSelect.execute(new Request(elements, selectedElement));
    }
    if (selectedElement.children != null && !selectedElement.children.isEmpty()) {
      showMenuAndListen(selectedElement.children);
    }
  }

  public void init() throws IllegalAccessException {
    showMenuAndListen(mainMenuController.initMainMenu());
  }

  private String formatInsertedValue(String str) {
    return String.format("> %s", str);
  }
}
