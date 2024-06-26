package com.fedmag.controllers;

import com.fedmag.menu.MenuElement;
import com.fedmag.menu.Request;
import java.util.ArrayList;
import java.util.List;

// TODO REMOVE THIS CLASS
public class AbstractMenuController {

  protected final Controller controller;

  public AbstractMenuController(Controller controller) {
    this.controller = controller;
  }

  protected List<MenuElement> generateChildren(List<?> items, Request request,
      ItemProcessor processor) {
    List<MenuElement> children = new ArrayList<>();
    for (Object item : items) {
      MenuElement child = processor.processItem(item);
      MenuElement back = new MenuElement(0, "Back", request.getCurrentElements());
      child.setChildren(processor.getChildren(item, back));
      children.add(child);
    }
    return children;
  }

  protected interface ItemProcessor {

    /**
     * Transforms an object in a MenuElement.
     *
     * @param item to convert.
     * @return converted item
     */
    MenuElement processItem(Object item);

    /**
     * Creates the submenu to display if the current item is selected by the user.
     *
     * @param item that contains information used to obtain related objects. Usually an entity like
     *             Company or Customer.
     * @param back menu to display if the back option is chosen.
     * @return MenuElements to display if the current object is selected.
     */
    List<MenuElement> getChildren(Object item, MenuElement back);
  }

}
