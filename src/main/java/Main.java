import World.Specimen.ISpecimen;
import World.World;
import World.Loader;
import java.util.Comparator;

public class Main {

  public static void main(String[] args) {

    Loader loader = new Loader();
    loader.scan("src/main/resources/trivial_0.ttp");

    World world = new World();
    world.initializeWorld(loader);

//    System.out.println(world);
//    System.out.println(loader);

//    ISpecimen specimenP1 = new World.Specimen();
//    specimenP1.initialise(world.getCitiesItems());
//
//    ISpecimen specimenP2 = new World.Specimen();
//    specimenP2.initialise(world.getCitiesItems());
//    System.out.println(specimen);

//    specimen.mutate(100);

//    ISpecimen child = specimenP1.reproduce(specimenP2,100);
//    System.out.println(child);

    world.initializePopulation(100);

    ISpecimen best = world.getPopulation().stream().max(Comparator.comparingDouble(
        ISpecimen::getRateEvaluation)).get();
    System.out.println(best);
    System.out.println(best.getRateEvaluation());

  }
}
