package World.Specimen;

import World.City.ICity;
import World.Item.IItem;
import World.Item.Item;
import World.World;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class GreedySpecimen implements ISpecimen {

  private final List<IItem> items;
  private final List<Integer> cities;
  private final World world;
  private Integer rateEvaluation;

  public GreedySpecimen(World world) {
    items = new LinkedList<>();
    cities = new LinkedList<>();
    this.world = world;
  }

  public GreedySpecimen(List<Integer> cities, List<IItem> items, World world,
      Integer rateEvaluation) {
    this.cities = cities;
    this.items = items;
    this.world = world;
    this.rateEvaluation = rateEvaluation;
  }

  @Override
  public void initialise(Map<Integer, List<IItem>> citiesItems) {
    Set<Integer> citiesIndexes = new HashSet<>(citiesItems.keySet());
    AtomicInteger actualCapacityOfBackpack = new AtomicInteger(world.getCapacityOfBackpack());

    cities.add(0, 1);
    items.add(0, new Item(-1, 0, 0, 1));
    citiesIndexes.remove(1);

    int numberOfCities = citiesIndexes.size();

    for (int i = 0; i < numberOfCities; i++) {
      int nearestCityIndex = getNearestCity(cities.get(cities.size() - 1), citiesIndexes);
      citiesIndexes.remove(nearestCityIndex);
      cities.add(nearestCityIndex);
      List<IItem> itemsFromCity = citiesItems.get(nearestCityIndex);
      items.add(greedlyChoiceItem(itemsFromCity, actualCapacityOfBackpack, nearestCityIndex));
    }
  }

  private int getNearestCity(Integer startCityIndex, Set<Integer> citiesIndexes) {
    return getCitiesObjects(citiesIndexes)
        .stream()
        .min(Comparator.comparingDouble(c -> routeLength(world.getCity(startCityIndex), c))).get()
        .getIndex();
  }

  private List<ICity> getCitiesObjects(Set<Integer> citiesIndexes) {
    return citiesIndexes.stream().map(world::getCity).collect(Collectors.toList());
  }

  @Override
  public void evaluate() {
    rateEvaluation = getItemsProfitSum(items) - getCityTravelTotalTime(world, cities, items);
  }

  @Override
  public void mutate(int mutateParam) {

  }

  @Override
  public ISpecimen reproduce(ISpecimen otherSpecimen, int reproduceParam) {
    return null;
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
    return new GreedySpecimen(new ArrayList<>(cities), new ArrayList<>(items), world,
        rateEvaluation);
  }

  @Override
  public World getWorld() {
    return world;
  }
}
