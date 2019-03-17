import World.Loader;
import World.SelectionMethods.ISelectionMethod;
import World.Specimen.ISpecimen;
import World.World;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class WorldManager {

  private World world;
  private ISelectionMethod selectionMethod;
  private int numberOfGeneration;
  private int reproductionChance;
  private int mutationChance;
  private int sizeOfPopulation;
  private final String fileName;

  private List<ISpecimen> bestSpecimens;
  private List<ISpecimen> worstSpecimens;
  private List<Double> averageSpecimen;

  public WorldManager(String pathFile, ISelectionMethod selectionMethod, int numberOfGeneration,
      int reproductionChance, int mutationChance, int sizeOfPopulation) {

    Loader loader = new Loader();
    loader.scan(pathFile);

    world = new World();
    world.initializeWorld(loader);

    this.selectionMethod = selectionMethod;
    this.numberOfGeneration = numberOfGeneration;
    this.reproductionChance = reproductionChance;
    this.mutationChance = mutationChance;
    this.sizeOfPopulation = sizeOfPopulation;
    this.fileName = pathFile.substring(19, pathFile.length() - 4);

    bestSpecimens = new LinkedList<>();
    worstSpecimens = new LinkedList<>();
    averageSpecimen = new LinkedList<>();
  }

  public void run() {
    world.initializePopulation(sizeOfPopulation);
    world.evaluate();

    saveResults();

    for (int i = 0; i < numberOfGeneration; i++) {
      world.selection(selectionMethod);
      world.reproduction(reproductionChance);
      world.mutation(mutationChance);
      world.evaluate();

      saveResults();
    }
  }

  private void saveResults() {
    bestSpecimens.add(getBestSpecimen());
    worstSpecimens.add(getWorstSpecimen());
    averageSpecimen.add(getAverageRateEvaluation());
  }

  ISpecimen getBestSpecimen() {
    return world.getBestSpecimen();
  }

  ISpecimen getWorstSpecimen() {
    return world.getWorstSpecimen();
  }

  Double getAverageRateEvaluation() {
    return world.getAverageResult();
  }

  String getDescription() {
    return fileName + " " + selectionMethod + " " + numberOfGeneration + " " + reproductionChance
        + " " + mutationChance + " " + sizeOfPopulation;
  }

  List<String> getHeader() {
    List<String> header = new ArrayList<>(4);
    header.add("nr_pokolenia");
    header.add("najlepsza_ocena");
    header.add("średnia_ocen");
    header.add("najgorsza_ocena");

    return header;
  }

  public List<List<String>> getData() {
    List<List<String>> data = new LinkedList<>();
    for (int i = 0; i < numberOfGeneration; i++) {
      data.add(new LinkedList<>());
      data.get(i).add(String.valueOf(i));
      data.get(i).add(String.valueOf(bestSpecimens.get(i).getRateEvaluation()));
      data.get(i).add(String.valueOf(averageSpecimen.get(i)));
      data.get(i).add(String.valueOf(worstSpecimens.get(i).getRateEvaluation()));
    }
    return data;
  }
}
