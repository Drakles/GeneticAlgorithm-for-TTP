import World.Loader;
import World.SelectionMethods.ISelectionMethod;
import World.Specimen.ISpecimen;
import World.World;
import java.util.Comparator;

public class WorldManager {

  private World world = new World();
  private ISelectionMethod selectionMethod;
  private int numberOfGeneration;
  private int reproductionChance;
  private int mutationChance;
  private int numberOfPopulation;

  public WorldManager(String pathFile, ISelectionMethod selectionMethod, int numberOfGeneration,
      int reproductionChance, int mutationChance, int numberOfPopulation) {

    Loader loader = new Loader();
    loader.scan(pathFile);

    world = new World();
    world.initializeWorld(loader);

    this.selectionMethod = selectionMethod;
    this.numberOfGeneration = numberOfGeneration;
    this.reproductionChance = reproductionChance;
    this.mutationChance = mutationChance;
    this.numberOfPopulation = numberOfPopulation;
  }

  public void run(){
    world.initializePopulation(numberOfPopulation);
    world.evaluate();

    for (int i = 0; i < numberOfGeneration; i++) {
      world.selection(selectionMethod);
      world.reproduction(reproductionChance);
      world.mutation(mutationChance);
      world.evaluate();
    }
  }

  ISpecimen getBestSpecimen(){
    return world.getPopulation().stream().max(Comparator.comparingDouble(
        ISpecimen::getRateEvaluation)).get();
  }
}
