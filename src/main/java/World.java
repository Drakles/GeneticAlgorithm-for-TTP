import City.ICity;
import Item.IItem;
import Specimen.ISpecimen;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class World {
  private int dimension;
  private int numberOfItems;
  private int capacityOfBackpack;;
  private double minSpeed;
  private double maxSpeed;
  private double rentingRatio;
  private String edgeWeightType;

  private List<ICity> cities = new ArrayList<>();
  private Map<Integer, List<IItem>> citiesItems = new LinkedHashMap<>();

  private List<ISpecimen> population = new LinkedList<>();

  public void initialize(Loader loader){
    this.dimension = loader.getDimension();
    this.numberOfItems = loader.getNumberOfItems();
    this.capacityOfBackpack = loader.getCapacityOfBackpack();
    this.minSpeed = loader.getMinSpeed();
    this.maxSpeed = loader.getMaxSpeed();
    this.rentingRatio = loader.getRentingRatio();
    this.edgeWeightType = loader.getEdgeWeightType();

    for (ICity city : loader.getCities()){
      cities.add(city);
      citiesItems.putIfAbsent(city.getIndex(),new LinkedList<>());
    }

    for (IItem item : loader.getItems()){
      if(citiesItems.containsKey(item.getCityIndex())){
        citiesItems.get(item.getCityIndex()).add(item);
      }
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

  public Map<Integer, List<IItem>> getCitiesItems() {
    return citiesItems;
  }

  public List<ISpecimen> getPopulation() {
    return population;
  }

  public void setDimension(int dimension) {
    this.dimension = dimension;
  }

  public void setNumberOfItems(int numberOfItems) {
    this.numberOfItems = numberOfItems;
  }

  public void setCapacityOfBackpack(int capacityOfBackpack) {
    this.capacityOfBackpack = capacityOfBackpack;
  }

  public void setMinSpeed(double minSpeed) {
    this.minSpeed = minSpeed;
  }

  public void setMaxSpeed(double maxSpeed) {
    this.maxSpeed = maxSpeed;
  }

  public void setRentingRatio(double rentingRatio) {
    this.rentingRatio = rentingRatio;
  }

  public void setEdgeWeightType(String edgeWeightType) {
    this.edgeWeightType = edgeWeightType;
  }

  public void setCities(List<ICity> cities) {
    this.cities = cities;
  }

  public void setCitiesItems(Map<Integer, List<IItem>> citiesItems) {
    this.citiesItems = citiesItems;
  }

  public void setPopulation(List<ISpecimen> population) {
    this.population = population;
  }

  @Override
  public String toString() {
    return "World{" +
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
