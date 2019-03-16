package World.Specimen;

import World.City.ICity;
import World.Item.IItem;
import World.Item.Item;
import World.World;
import com.google.common.util.concurrent.AtomicDouble;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


public class Specimen implements ISpecimen {

  private final List<IItem> items;
  private final List<Integer> cities;
  private final static Random random = new Random();
  private final World world;

  public Specimen(List<Integer> cities, List<IItem> items, World world) {
    this.cities = cities;
    this.items = items;
    this.world = world;
  }

  public Specimen(World world) {
    items = new LinkedList<>();
    cities = new LinkedList<>();
    this.world = world;
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
        items.add(choseItem(chosenItems, actualCapacityOfBackpack, cityIndexChose));
        cities.add(cityIndexChose);
      } else {
        cities.add(0, cityIndexChose);
        items.add(0, new Item(-1, 0, 0, 0));
      }
      keys.remove(randomInt);
    }
  }

  private IItem choseItem(List<IItem> items, AtomicInteger actualCapacity, int cityIndex) {
    IItem bestItem = items.get(0);
    for (IItem item : items) {
      if (item.getProfit() / item.getWeight() > bestItem.getProfit() / bestItem.getWeight()) {
        bestItem = item;
      }
    }
    if (bestItem.getWeight() <= actualCapacity.get()) {
      actualCapacity.addAndGet(-bestItem.getWeight());
      return bestItem;
    } else {
      return new Item(-1, 0, 0, cityIndex);
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
    Specimen specimen = null;
    if (chance < reproduceParam) {

      int splitIndex = random.nextInt(cities.size());
      List<Integer> childCities = new LinkedList<>(cities.subList(0, splitIndex));
      List<IItem> childItems = new LinkedList<>();

      childCities.addAll(otherSpecimen.getCities());

      childCities = childCities.stream().distinct().collect(Collectors.toList());
      AtomicInteger actualBackpackCapacity = new AtomicInteger(world.getCapacityOfBackpack());

      for (Integer city : childCities) {
        childItems.add(choseItem(world.getItems(city), actualBackpackCapacity, city));
      }
      specimen = new Specimen(childCities, childItems, world);
    }
    return specimen;
  }

  @Override
  public double getRateEvaluation() {
    return getItemsProfitSum() - getCityTravelTotalTime();
  }

  private int getItemsProfitSum() {
    return items.stream().mapToInt(IItem::getProfit).sum();
  }

  private double getCityTravelTotalTime() {
    double totalTime = 0;
    AtomicDouble actualSpeed = new AtomicDouble(world.getMaxSpeed());
    AtomicInteger actualCapacity = new AtomicInteger(world.getCapacityOfBackpack());
    List<IItem> backpack = new LinkedList<>();

    for (int i = 0; i < cities.size() - 1; i++) {
      IItem item = items.get(i);
      backpack.add(item);
      actualCapacity.addAndGet(-item.getWeight());

      totalTime += travel(cities.get(i), backpack, cities.get(i + 1), actualSpeed);
    }
    IItem lastItem = items.get(items.size() - 1);
    backpack.add(lastItem);
    actualCapacity.addAndGet(-lastItem.getWeight());

    totalTime += travel(cities.get(cities.size() - 1), backpack, cities.get(0), actualSpeed);

    return totalTime;
  }

  private double travel(Integer firstCityIndex, List<IItem> backpack,
      Integer secondCityIndex, AtomicDouble actualSpeed) {
    actualSpeed.set(currentSpeed(backpack.stream().mapToDouble(IItem::getWeight).sum()));
    return routeLength(firstCityIndex, secondCityIndex) / actualSpeed.get();
  }

  private double currentSpeed(double actualWeightSum) {
    return world.getMaxSpeed() - actualWeightSum * ((world.getMaxSpeed() - world.getMinSpeed())
        / world.getCapacityOfBackpack());
  }

  private double routeLength(Integer firstCityIndex, Integer secondCityIndex) {
    ICity firstCity = world.getCity(firstCityIndex);
    ICity secondCity = world.getCity(secondCityIndex);

    return Math.sqrt(Math.pow(Math.abs(firstCity.getX() - secondCity.getX()), 2) + Math
        .pow(Math.abs(firstCity.getY() - secondCity.getY()), 2));
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
  public String toString() {
    return "World.Specimen{" +
        "items=" + items +
        ", cities=" + cities +
        '}';
  }
}
