import City.ICity;
import Item.IItem;
import Specimen.ISpecimen;
import java.util.Collection;
import java.util.Map;

public class World {
  int dimension;
  int numberOfItems;
  int capacityOfBackpack;;
  double minSpeed;
  double maxSpeed;
  double rentingRatio;
  String edgeWeightType;

  Collection<ISpecimen> population;
  Map<ICity,Collection<IItem>> citiesWithItems;

}
