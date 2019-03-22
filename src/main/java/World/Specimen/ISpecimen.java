package World.Specimen;

import World.City.ICity;
import World.Item.IItem;
import World.Item.Item;
import World.World;
import com.google.common.util.concurrent.AtomicDouble;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public interface ISpecimen {

  void initialise(Map<Integer, List<IItem>> citiesItems);

  void mutate(int mutateParam);

  ISpecimen reproduce(ISpecimen otherSpecimen, int reproduceParam);

  void evaluate();

  int getRateEvaluation();

  List<Integer> getCities();

  List<IItem> getItems();

  ISpecimen copy();

  World getWorld();

  default double routeLength(ICity firstCity, ICity secondCity) {
    return Math.sqrt(Math.pow(Math.abs(firstCity.getX() - secondCity.getX()), 2) + Math
        .pow(Math.abs(firstCity.getY() - secondCity.getY()), 2));
  }

  default IItem greedlyChoiceItem(List<IItem> items, AtomicInteger actualCapacity, int cityIndex) {
    if (!items.isEmpty()) {
      IItem bestItem = items.get(0);
      for (IItem item : items) {
        if (item.getProfit() / item.getWeight() > bestItem.getProfit() / bestItem.getWeight()) {
          bestItem = item;
        }
      }
      if (bestItem.getWeight() <= actualCapacity.get()) {
        actualCapacity.addAndGet(-bestItem.getWeight());
        return bestItem;
      }
    }
    return new Item(-1, 0, 0, cityIndex);
  }

  default int getItemsProfitSum(List<IItem> items) {
    return items.stream().mapToInt(IItem::getProfit).sum();
  }

  default int getCityTravelTotalTime(World world, List<Integer> cities, List<IItem> items) {
    int totalTime = 0;
    AtomicDouble actualSpeed = new AtomicDouble(world.getMaxSpeed());
    AtomicInteger actualCapacity = new AtomicInteger(world.getCapacityOfBackpack());
    List<IItem> backpack = new LinkedList<>();

    for (int i = 0; i < cities.size() - 1; i++) {
      IItem item = items.get(i);
      backpack.add(item);
      actualCapacity.addAndGet(-item.getWeight());

      totalTime += travel(cities.get(i), backpack, cities.get(i + 1), actualSpeed, world);
    }
    IItem lastItem = items.get(items.size() - 1);
    backpack.add(lastItem);
    actualCapacity.addAndGet(-lastItem.getWeight());

    totalTime += travel(cities.get(cities.size() - 1), backpack, cities.get(0), actualSpeed, world);

    return totalTime;
  }

  default double travel(Integer firstCityIndex, List<IItem> backpack,
      Integer secondCityIndex, AtomicDouble actualSpeed, World world) {
    actualSpeed.set(currentSpeed(backpack.stream().mapToDouble(IItem::getWeight).sum(), world));
    return routeLength(world.getCity(firstCityIndex), world.getCity(secondCityIndex)) / actualSpeed
        .get();
  }

  default double currentSpeed(double actualWeightSum, World world) {
    return world.getMaxSpeed() - actualWeightSum * ((world.getMaxSpeed() - world.getMinSpeed())
        / world.getCapacityOfBackpack());
  }
}
