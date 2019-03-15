package Specimen;

import City.ICity;
import Item.IItem;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface ISpecimen {

  void initialise(Map<Integer, List<IItem>> citiesItems);

  void mutate(int mutateParam);

  ISpecimen reproduce(ISpecimen otherSpecimen,int reproduceParam);

  int getRateEvaluation();

  List<Integer> getCities();

  List<IItem> getItems();
}
