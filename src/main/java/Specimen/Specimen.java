package Specimen;

import Item.IItem;
import Item.Item;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class Specimen implements ISpecimen {

  private final List<IItem> items;
  private final List<Integer> cities;
  private final static Random random = new Random();

  public Specimen() {
    items = new LinkedList<>();
    cities = new LinkedList<>();
  }

  public Specimen(List<IItem> items, List<Integer> cities) {
    this.items = items;
    this.cities = cities;
  }

  public void initialise(Map<Integer, List<IItem>> citiesItems) {
    Random random = new Random();
    List<Integer> keys = new ArrayList<>(citiesItems.keySet());

    for (int i = 0; i < citiesItems.size(); i++) {
      int randomInt = random.nextInt(keys.size());
      int cityChose = keys.get(randomInt);

      List<IItem> chosenItems = citiesItems.get(cityChose);
      if (!chosenItems.isEmpty()) {
        IItem item = chosenItems.get(random.nextInt(chosenItems.size()));
        items.add(item);
        cities.add(cityChose);
      } else {
        cities.add(0, cityChose);
        items.add(0, new Item(-1,0,0,cityChose));
      }
      keys.remove(randomInt);
    }
  }

  @Override
  public void mutate(int mutateParam) {
    int chance = random.nextInt(101);
    if (chance < mutateParam) {

      int firstRandomCityIndex = random.nextInt(cities.size() - 1) + 1;
      int firstCity = cities.remove(firstRandomCityIndex);
      IItem firstCityItem = items.remove(firstRandomCityIndex);

      int secondRandomCityIndex = random.nextInt(cities.size() - 1) + 1;
      int secondCity = cities.remove(secondRandomCityIndex);
      IItem secondCityItem = items.remove(secondRandomCityIndex);

      cities.add(secondRandomCityIndex, firstCity);
      cities.add(firstRandomCityIndex, secondCity);

      items.add(secondRandomCityIndex, firstCityItem);
      items.add(firstRandomCityIndex, secondCityItem);
    }
  }

  @Override
  public ISpecimen reproduce(ISpecimen otherSpecimen, int reproduceParam) {
    int chance = random.nextInt(101);
    Specimen specimen = null;
    if (chance < reproduceParam) {

      int splitIndex = random.nextInt(cities.size());
      List<Integer> childCities = new LinkedList<>(cities.subList(0, splitIndex));
      List<IItem> childItems = new LinkedList<>(items.subList(0, splitIndex));

      childCities.addAll(otherSpecimen.getCities());
      List<IItem> items = otherSpecimen.getItems();

      childItems.addAll(items);

      childCities = childCities.stream().distinct().collect(Collectors.toList());
      childItems = childItems.stream().distinct().collect(Collectors.toList());

      specimen = new Specimen(childItems, childCities);
    }
    return specimen;
  }

  @Override
  public int getRateEvaluation() {
    return 0;
  }

  @Override
  public List<Integer> getCities() {
    return cities;
  }

  @Override
  public List<IItem> getItems() {
    return items;
  }

}
