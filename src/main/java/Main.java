import World.SelectionMethods.ISelectionMethod;
import World.SelectionMethods.Tournament;

public class Main {

  public static void main(String[] args) {
    ISelectionMethod selectionMethod = new Tournament(5, 100);

    WorldManager worldManager = new WorldManager("src/main/resources/trivial_0.ttp",
        selectionMethod, 100, 70, 10, 100);

    worldManager.run();
    System.out.println(worldManager.getBestSpecimen());
  }
}
