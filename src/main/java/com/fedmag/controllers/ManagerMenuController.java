package com.fedmag.controllers;

import com.fedmag.data.company.Company;
import com.fedmag.menu.MenuElement;
import java.util.List;
import java.util.Scanner;

public class ManagerMenuController extends AbstractMenuController { ;

  public ManagerMenuController(Controller controller) {
    super(controller);
  }

  MenuElement generateLoginAsManager(List<MenuElement> backMenu) {
    final MenuElement logInAsAManager = new MenuElement(1, "Log in as a manager", null);
    MenuElement companyListBtn = new MenuElement(1, "Company list", null);
    MenuElement createCompanyBtn = new MenuElement(2, "Create a company", null);
    MenuElement backBtn = new MenuElement(0, "Back", backMenu );

    // COMPANIES
    companyListBtn.setOnSelect((request) -> {
      // retrieve all companies and print them
      List<Company> allCompanies = controller.companyDao.getAll();
      if (allCompanies.isEmpty()) {
        System.out.println("The company list is empty!\n");
        companyListBtn.setChildren(List.of(companyListBtn, createCompanyBtn, backBtn));
        return;
      }
      System.out.println("Choose the company:");
      List<MenuElement> children = generateChildren(allCompanies, request, new CompanyItemProcessor());
      children.add(new MenuElement(0, "Back", request.getCurrentElements()));
      companyListBtn.setChildren(children);
      System.out.println();
    });
    createCompanyBtn.setOnSelect(request -> {
      System.out.println("Enter the company name:");
      Scanner sc = new Scanner(System.in);
      String name = sc.nextLine();
      System.out.println(formatInsertedValue(name));
      // SAVE TO DB
      controller.companyDao.insert(new Company(name));
      createCompanyBtn.setChildren(request.getCurrentElements());
    });

    logInAsAManager.setChildren(List.of(companyListBtn, createCompanyBtn, backBtn));
    return logInAsAManager;
  }

  private class CompanyItemProcessor implements ItemProcessor {

    @Override
    public MenuElement processItem(Object item) {
      Company company = (Company) item;
      return new MenuElement(company.getName(), company.getId());
    }

    @Override
    public List<MenuElement> getChildren(Object item, MenuElement back) {
      Company company = (Company) item;
      MenuElement carList = controller.carController.generateCarListElement(company);
      MenuElement createCar = controller.carController.generateCreateCarElement(company);
      return List.of(carList, createCar, back);
    }
  }

  private String formatInsertedValue(String str) {
    return String.format("> %s", str);
  }

}
