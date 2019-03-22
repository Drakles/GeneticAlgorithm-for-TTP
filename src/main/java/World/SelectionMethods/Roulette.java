package World.SelectionMethods;

import World.Specimen.ISpecimen;
import World.World;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Roulette implements ISelectionMethod {

  private static final Random random = new Random();

  @Override
  public List<ISpecimen> nextGeneration(List<ISpecimen> population) {
    List<ISpecimen> nextGeneration = new LinkedList<>();
    if (!population.isEmpty()) {

      World world = population.get(0).getWorld();
      for (int i = 0; i < population.size(); i++) {

        ISpecimen selected = null;
        while (selected == null) {

          int index = random.nextInt(population.size());
          ISpecimen current = population.get(index);

          double probability =
              (double) (current.getRateEvaluation() - world.getWorstSpecimen().getRateEvaluation())
                  / (double) (
                  world.getBestSpecimen().getRateEvaluation() - world.getWorstSpecimen()
                      .getRateEvaluation());

          if (random.nextDouble() <= probability) {
            selected = current;
            nextGeneration.add(selected);
          }
        }
      }
    }
    return nextGeneration;
  }
  @Override
  public String toString() {
    return "Roulette";
  }
}
