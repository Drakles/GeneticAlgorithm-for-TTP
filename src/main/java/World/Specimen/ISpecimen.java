package World.Specimen;

import World.Item.IItem;
import World.World;
import java.util.List;
import java.util.Map;

public interface ISpecimen {

  void initialise(Map<Integer, List<IItem>> citiesItems);

  void mutate(int mutateParam);

  ISpecimen reproduce(ISpecimen otherSpecimen,int reproduceParam);

  void evaluate();

  double getRateEvaluation();

  List<Integer> getCities();

  List<IItem> getItems();

  ISpecimen copy();

  World getWorld();
}
