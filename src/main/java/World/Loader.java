package World;

import World.City.City;
import World.City.ICity;
import World.Item.IItem;
import World.Item.Item;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Loader {

//  private static final Logger logger = LoggerFactory.getLogger(World.Loader.class);

  private int dimension;
  private int numberOfItems;
  private int capacityOfBackpack;
  private double minSpeed;
  private double maxSpeed;
  private double rentingRatio;
  private String edgeWeightType;

  private List<ICity> cities = new LinkedList<>();
  private List<IItem> items = new LinkedList<>();


  public void scan(String filePath) {
    if (filePath != null) {
      File sourceFile = new File(filePath);
      try {
        Scanner sc = new Scanner(sourceFile);

        //problem name
//        logger.info(sc.nextLine());
        //KNAPSACK DATA TYPE
//        logger.info(sc.nextLine());
        System.out.println(sc.nextLine());
        System.out.println(sc.nextLine());

        String dimension = sc.nextLine();
        this.dimension = Integer.valueOf(dimension.substring(11));

        String numberOfItems = sc.nextLine();
        this.numberOfItems = Integer.valueOf(numberOfItems.substring(18));

        String capacityOfKnapsack = sc.nextLine();
        this.capacityOfBackpack = Integer.valueOf(capacityOfKnapsack.substring(23));

        String minSpeed = sc.nextLine();
        this.minSpeed = Double.valueOf(minSpeed.substring(12));

        String maxSpeed = sc.nextLine();
        this.maxSpeed = Double.valueOf(maxSpeed.substring(12));

        String rentingRatio = sc.nextLine();
        this.rentingRatio = Double.valueOf(rentingRatio.substring(16));

        String edgheWeightType = sc.nextLine();
        this.edgeWeightType = edgheWeightType.substring(18);

        System.out.println(sc.nextLine());

        int cityIndex;
        double cityXCoord;
        double cityYCoord;

        String line = sc.nextLine();

        while (!line.equals("ITEMS SECTION\t(INDEX, PROFIT, WEIGHT, ASSIGNED NODE NUMBER): ")) {

          String[] attributes = line.split("\t");
          cityIndex = Integer.valueOf(attributes[0]);
          cityXCoord = Double.valueOf(attributes[1]);
          cityYCoord = Double.valueOf(attributes[2]);

          cities.add(new City(cityIndex, cityXCoord, cityYCoord));

          line = sc.nextLine();
        }

        int itemIndex;
        int itemProfit;
        int itemWeight;
        int itemCity;

        while (sc.hasNext()) {
          itemIndex = sc.nextInt();
          itemProfit = sc.nextInt();
          itemWeight = sc.nextInt();
          itemCity = sc.nextInt();

          items.add(new Item(itemIndex, itemProfit, itemWeight, itemCity));
        }

      } catch (FileNotFoundException e) {
        e.printStackTrace();
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

  public List<IItem> getItems() {
    return items;
  }

  @Override
  public String toString() {
    return "World.Loader{" +
        "dimension=" + dimension +
        ", numberOfItems=" + numberOfItems +
        ", capacityOfBackpack=" + capacityOfBackpack +
        ", minSpeed=" + minSpeed +
        ", maxSpeed=" + maxSpeed +
        ", rentingRatio=" + rentingRatio +
        ", edgeWeightType='" + edgeWeightType + '\'' +
        ", cities=" + cities +
        ", items=" + items +
        '}';
  }
}
