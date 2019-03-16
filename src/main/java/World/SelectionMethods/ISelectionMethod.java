package World.SelectionMethods;

import World.Specimen.ISpecimen;
import java.util.List;

public interface ISelectionMethod {

  List<ISpecimen> nextGeneration(List<ISpecimen> population);
}
