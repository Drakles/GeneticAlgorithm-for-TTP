package World;

import World.City.ICity;
import World.Item.IItem;
import World.SelectionMethods.ISelectionMethod;
import World.Specimen.ISpecimen;
import World.Specimen.Specimen;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class World {

  private int dimension;
  private int numberOfItems;
  private int capacityOfBackpack;
  private double minSpeed;
  private double maxSpeed;
  private double rentingRatio;
  private String edgeWeightType;

  private List<ICity> cities = new ArrayList<>();
  private Map<Integer, List<IItem>> citiesItems = new LinkedHashMap<>();

  private List<ISpecimen> population = new LinkedList<>();
  private int currentGeneration;

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
    currentGeneration = 0;
  }

  public void selection(ISelectionMethod selectionMethod) {
    population = selectionMethod.nextGeneration(population);
    currentGeneration++;
  }

  public void evaluate() {
    population.forEach(ISpecimen::evaluate);
  }

  public void reproduction(int reproductionChance) {
    List<ISpecimen[]> parents = createPairsForCrossover();
    population.clear();
    for (ISpecimen[] pair : parents) {
      population.add(pair[0].reproduce(pair[1], reproductionChance));
      population.add(pair[1].reproduce(pair[0], reproductionChance));
    }
  }

  private List<ISpecimen[]> createPairsForCrossover() {
    List<ISpecimen> source = new ArrayList<>(population);
    Collections.shuffle(source);
    List<ISpecimen[]> result = new ArrayList<>();
    for (int i = 0; i < source.size() / 2; i++) {
      result.add(new ISpecimen[]{source.get(2 * i), source.get(2 * i + 1)});
    }
    return result;
  }

  public void mutation(int mutationChance) {
    population.forEach(s -> s.mutate(mutationChance));
  }


  public int getCurrentGeneration() {
    return currentGeneration;
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

  public ISpecimen getBestSpecimen() {
    return population.stream().max(Comparator.comparingDouble(ISpecimen::getRateEvaluation)).get();
  }

  public ISpecimen getWorstSpecimen() {
    return population.stream().min(Comparator.comparingDouble(ISpecimen::getRateEvaluation)).get();
  }

  public Double getAverageResult() {
    return population.stream().mapToDouble(ISpecimen::getRateEvaluation).sum() / population.size();
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
