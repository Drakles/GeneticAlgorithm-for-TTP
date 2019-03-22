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
  private List<Integer> averageSpecimen;
  private List<Long> elapsedTime;

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
    elapsedTime = new LinkedList<>();
  }

  public void runGeneticAlgorithm(int numberOfIterations) {
    long t = System.currentTimeMillis();
    for (int i = 0; i < numberOfIterations; i++) {
      world.initializePopulation(sizeOfPopulation);
      world.evaluate();

      saveResults(System.currentTimeMillis() - t);

      for (int j = 0; j < numberOfGeneration; j++) {
        world.selection(selectionMethod);
        world.reproduction(reproductionChance);
        world.mutation(mutationChance);
        world.evaluate();

        saveResults(System.currentTimeMillis() - t);
      }
      world.killAll();
    }
  }

  public void runForRandomSearch() {
    long t = System.currentTimeMillis();
    world.initializePopulation(sizeOfPopulation);
    world.evaluate();

    saveResults(System.currentTimeMillis() - t);
  }

  public void runForGreedy() {
    long t = System.currentTimeMillis();
    world.initializeGreedy();
    world.evaluate();

    saveResults(System.currentTimeMillis() - t);
  }

  private void saveResults() {
    bestSpecimens.add(getBestSpecimen());
    worstSpecimens.add(getWorstSpecimen());
    averageSpecimen.add(getAverageRateEvaluation());
  }

  private void saveResults(Long time) {
    bestSpecimens.add(getBestSpecimen());
    worstSpecimens.add(getWorstSpecimen());
    averageSpecimen.add(getAverageRateEvaluation());
    elapsedTime.add(time);
  }

  ISpecimen getBestSpecimen() {
    return world.getBestSpecimen();
  }

  ISpecimen getWorstSpecimen() {
    return world.getWorstSpecimen();
  }

  Integer getAverageRateEvaluation() {
    return world.getAverageResult();
  }

  String getDescription() {
    return fileName + " " + selectionMethod + " " + numberOfGeneration + " " + reproductionChance
        + " " + mutationChance + " " + sizeOfPopulation;
  }

  List<String> getHeader() {
    List<String> header = new ArrayList<>(5);
    header.add("nr_pokolenia");
    header.add("najlepsza_ocena");
    header.add("średnia_ocen");
    header.add("najgorsza_ocena");
    header.add("czas wykonania");

    return header;
  }

  public List<List<String>> getData() {
    List<List<String>> data = new LinkedList<>();
    for (int i = 0; i < bestSpecimens.size(); i++) {
      data.add(new LinkedList<>());
      data.get(i).add(String.valueOf(i));
      data.get(i).add(String.valueOf(bestSpecimens.get(i).getRateEvaluation()));
      data.get(i).add(String.valueOf(averageSpecimen.get(i)));
      data.get(i).add(String.valueOf(worstSpecimens.get(i).getRateEvaluation()));
    }
    return data;
  }

  public List<List<String>> getDataWithTime() {
    List<List<String>> data = new LinkedList<>();
    for (int i = 0; i < bestSpecimens.size(); i++) {
      data.add(new LinkedList<>());
      data.get(i).add(String.valueOf(i));
      data.get(i).add(String.valueOf(bestSpecimens.get(i).getRateEvaluation()));
      data.get(i).add(String.valueOf(averageSpecimen.get(i)));
      data.get(i).add(String.valueOf(worstSpecimens.get(i).getRateEvaluation()));
      data.get(i).add(String.valueOf(elapsedTime.get(i)));
    }
    return data;
  }
}
