import World.Loader;
import World.SelectionMethods.ISelectionMethod;
import World.SelectionMethods.Tournament;
import World.Specimen.ISpecimen;
import World.World;
import java.util.Comparator;

public class Main {

  public static void main(String[] args) {

    Loader loader = new Loader();
    loader.scan("src/main/resources/trivial_0.ttp");

    World world = new World();
    world.initializeWorld(loader);

    ISelectionMethod selectionMethod = new Tournament(5, 100);
    int numberOfGeneration = 100;
    int reproductionChance = 70;
    int mutationChance = 10;

    world.initializePopulation(100);
    world.evaluate();

    for (int i = 0; i < numberOfGeneration; i++) {
      world.selection(selectionMethod);
      world.reproduction(reproductionChance);
      world.mutation(mutationChance);
      world.evaluate();
    }

    ISpecimen best = world.getPopulation().stream().max(Comparator.comparingDouble(
        ISpecimen::getRateEvaluation)).get();
    System.out.println(best);
    System.out.println(best.getRateEvaluation());
  }
}
