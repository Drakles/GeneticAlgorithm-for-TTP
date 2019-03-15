import Specimen.ISpecimen;
import Specimen.Specimen;

public class Main {

  public static void main(String[] args) {

    Loader loader = new Loader();
    loader.scan("src/main/resources/trivial_1.ttp");

    World world = new World();
    world.initialize(loader);

//    System.out.println(world);
//    System.out.println(loader);

    ISpecimen specimenP1 = new Specimen();
    specimenP1.initialise(world.getCitiesItems());

    ISpecimen specimenP2 = new Specimen();
    specimenP2.initialise(world.getCitiesItems());
//    System.out.println(specimen);

//    specimen.mutate(100);

    ISpecimen child = specimenP1.reproduce(specimenP2,100);
    System.out.println(child);

  }
}
