package World.Specimen;

import World.Item.IItem;
import World.Item.Item;
import World.World;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


public class GeneticSpecimen implements ISpecimen {

  private final List<IItem> items;
  private final List<Integer> cities;
  private final static Random random = new Random();
  private final World world;
  private Integer rateEvaluation;

  public GeneticSpecimen(World world) {
    items = new LinkedList<>();
    cities = new LinkedList<>();
    this.world = world;
  }

  public GeneticSpecimen(List<Integer> cities, List<IItem> items, World world) {
    this.cities = cities;
    this.items = items;
    this.world = world;
  }

  public GeneticSpecimen(List<Integer> cities, List<IItem> items, World world,
      Integer rateEvaluation) {
    this.cities = cities;
    this.items = items;
    this.world = world;
    this.rateEvaluation = rateEvaluation;
  }

  public void initialise(Map<Integer, List<IItem>> citiesItems) {
    Random random = new Random();
    List<Integer> keys = new ArrayList<>(citiesItems.keySet());
    AtomicInteger actualCapacityOfBackpack = new AtomicInteger(world.getCapacityOfBackpack());

    for (int i = 0; i < citiesItems.size(); i++) {
      int randomInt = random.nextInt(keys.size());
      int cityIndexChose = keys.get(randomInt);

      List<IItem> chosenItems = citiesItems.get(cityIndexChose);
      if (!chosenItems.isEmpty()) {
        items.add(greedlyChoiceItem(chosenItems, actualCapacityOfBackpack, cityIndexChose));
        cities.add(cityIndexChose);
      } else {
        cities.add(0, cityIndexChose);
        items.add(0, new Item(-1, 0, 0, 0));
      }
      keys.remove(randomInt);
    }
  }

  @Override
  public void mutate(int mutateParam) {
    int chance = random.nextInt(100);
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
    int chance = random.nextInt(100);
    ISpecimen specimen = this;
    if (chance < reproduceParam) {

      int splitIndex = random.nextInt(cities.size());
      List<Integer> childCities = new LinkedList<>(cities.subList(0, splitIndex));
      List<IItem> childItems = new LinkedList<>();

      childCities.addAll(otherSpecimen.getCities());

      childCities = childCities.stream().distinct().collect(Collectors.toList());
      AtomicInteger actualBackpackCapacity = new AtomicInteger(world.getCapacityOfBackpack());

      for (Integer city : childCities) {
        childItems.add(greedlyChoiceItem(world.getItems(city), actualBackpackCapacity, city));
      }
      specimen = new GeneticSpecimen(childCities, childItems, world);
    }
    return specimen;
  }

  @Override
  public void evaluate() {
    rateEvaluation = getItemsProfitSum(items) - getCityTravelTotalTime(world, cities, items);
  }

  @Override
  public int getRateEvaluation() {
    return rateEvaluation;
  }

  @Override
  public List<Integer> getCities() {
    return cities;
  }

  @Override
  public List<IItem> getItems() {
    return items;
  }

  @Override
  public ISpecimen copy() {
    return new GeneticSpecimen(new ArrayList<>(cities), new ArrayList<>(items), world,
        rateEvaluation);
  }

  @Override
  public World getWorld() {
    return world;
  }

  @Override
  public String toString() {
    return "Specimen{" +
        "rateEvaluation=" + rateEvaluation +
        '}';
  }
}
