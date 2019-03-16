package World;

import World.City.ICity;
import World.Item.IItem;
import World.Specimen.ISpecimen;
import World.Specimen.Specimen;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class World {

  private int dimension;
  private int numberOfItems;
  private int capacityOfBackpack;
  ;
  private double minSpeed;
  private double maxSpeed;
  private double rentingRatio;
  private String edgeWeightType;

  private List<ICity> cities = new ArrayList<>();
  private Map<Integer, List<IItem>> citiesItems = new LinkedHashMap<>();

  private List<ISpecimen> population = new LinkedList<>();

  public void initializeWorld(Loader loader) {
    this.dimension = loader.getDimension();
    this.numberOfItems = loader.getNumberOfItems();
    this.capacityOfBackpack = loader.getCapacityOfBackpack();
    this.minSpeed = loader.getMinSpeed();
    this.maxSpeed = loader.getMaxSpeed();
    this.rentingRatio = loader.getRentingRatio();
    this.edgeWeightType = loader.getEdgeWeightType();

    for (ICity city : loader.getCities()) {
      cities.add(city);
      citiesItems.putIfAbsent(city.getIndex(), new LinkedList<>());
    }

    for (IItem item : loader.getItems()) {
      if (citiesItems.containsKey(item.getCityIndex())) {
        citiesItems.get(item.getCityIndex()).add(item);
      }
    }
  }

  public void initializePopulation(int numberOfPopulation) {
    for (int i = 0; i < numberOfPopulation; i++) {
      ISpecimen specimen = new Specimen(this);
      specimen.initialise(citiesItems);
      population.add(specimen);
    }
  }

  public int getDimension() {
    return dimension;
  }

  public int getNumberOfItems() {
    return numberOfItems;
  }

  public int getCapacityOfBackpack() {
    return capacityOfBackpack;
  }

  public double getMinSpeed() {
    return minSpeed;
  }

  public double getMaxSpeed() {
    return maxSpeed;
  }

  public double getRentingRatio() {
    return rentingRatio;
  }

  public String getEdgeWeightType() {
    return edgeWeightType;
  }

  public List<ICity> getCities() {
    return cities;
  }

  public List<ISpecimen> getPopulation() {
    return population;
  }

  public void setPopulation(List<ISpecimen> population) {
    this.population = population;
  }

  public ICity getCity(Integer cityIndex) {
    for (ICity city : cities) {
      if (city.getIndex() == cityIndex) {
        return city;
      }
    }
    return null;
  }

  public List<IItem> getItems(Integer cityIndex) {
    return citiesItems.get(cityIndex);
  }

  @Override
  public String toString() {
    return "World.World{" +
        "dimension=" + dimension +
        ", numberOfItems=" + numberOfItems +
        ", capacityOfBackpack=" + capacityOfBackpack +
        ", minSpeed=" + minSpeed +
        ", maxSpeed=" + maxSpeed +
        ", rentingRatio=" + rentingRatio +
        ", edgeWeightType='" + edgeWeightType + '\'' +
        ", cities=" + cities +
        ", citiesItems=" + citiesItems +
        ", population=" + population +
        '}';
  }
}
