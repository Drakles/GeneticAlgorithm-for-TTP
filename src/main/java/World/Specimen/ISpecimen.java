package World.Specimen;

import World.Item.IItem;
import java.util.List;
import java.util.Map;

public interface ISpecimen {

  void initialise(Map<Integer, List<IItem>> citiesItems);

  void mutate(int mutateParam);

  ISpecimen reproduce(ISpecimen otherSpecimen,int reproduceParam);

  double getRateEvaluation();

  List<Integer> getCities();

  List<IItem> getItems();
}
