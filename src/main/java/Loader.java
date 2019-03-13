import City.ICity;
import Item.IItem;
import org.slf4j.Logger;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Scanner;
import org.slf4j.LoggerFactory;

public class Loader {

//  private static final Logger logger = LoggerFactory.getLogger(Loader.class);

  private int dimension;
  private int numberOfItems;
  private int capacityOfBackpack;;
  private double minSpeed;
  private double maxSpeed;
  private double rentingRatio;
  private String edgeWeightType;

  private Collection<ICity> cities;
  private Collection<IItem> items;

  public void scan(String filePath){
    if(filePath!=null){
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
        this.edgeWeightType = edgheWeightType .substring(18);

        //NODE_COORD_SECTION	(INDEX, X, Y):
//        logger.info(sc.nextLine());

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

  public Collection<ICity> getCities() {
    return cities;
  }

  public Collection<IItem> getItems() {
    return items;
  }

  @Override
  public String toString() {
    return "Loader{" +
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
