package Specimen;

import Item.IItem;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface ISpecimen {

  void initialise(Map<Integer, List<IItem>> citiesItems);

  void mutate();

  ISpecimen reproduce(ISpecimen otherSpecimen);

  int getRateEvaluation();
}
