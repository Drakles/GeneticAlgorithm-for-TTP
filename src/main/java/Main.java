import World.SelectionMethods.ISelectionMethod;
import World.SelectionMethods.Roulette;

public class Main {

  public static void main(String[] args) {
    long t = System.currentTimeMillis();
//    ISelectionMethod selectionMethod = new Tournament(5, 100);
    ISelectionMethod selectionMethod = new Roulette();

    WorldManager random = new WorldManager("src/main/resources/medium_0.ttp", selectionMethod,
        2, 70, 10, 10);
    random.runGeneticAlgorithm(1);

    FileService.createCsvFile(random.getDescription(), random.getHeader(),
        random.getDataWithTime());

    System.out.println("Total execution time: " + (System.currentTimeMillis() - t));
  }
}
