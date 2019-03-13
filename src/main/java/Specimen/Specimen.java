package Specimen;

import Item.IItem;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Specimen implements ISpecimen {

  LinkedHashMap<Integer, IItem> citiesItemsPlan = new LinkedHashMap<Integer, IItem>();

  public void initialise(Map<Integer, List<IItem>> citiesItems) {
    Random random = new Random();
    Map<Integer, List<IItem>> citiesItemsCopy = new HashMap<Integer, List<IItem>>(citiesItems);


    for (int i = 0; i < citiesItems.size(); i++) {
      int choose = random.nextInt(citiesItemsCopy.size());

      List<IItem> choosenItems = citiesItemsCopy.get(choose);
      int chooseItem = random.nextInt(choosenItems.size());
      IItem item = choosenItems.get(chooseItem);

      citiesItemsPlan
          .put(choose, item);
      citiesItemsCopy.remove(choose);
    }
  }

  public void mutate() {

  }

  public ISpecimen reproduce(ISpecimen otherSpecimen) {
    return null;
  }

  public int getRateEvaluation() {
    return 0;
  }

  @Override
  public String toString() {
    return "Specimen{" +
        "citiesItemsPlan=" + citiesItemsPlan +
        '}';
  }
}
