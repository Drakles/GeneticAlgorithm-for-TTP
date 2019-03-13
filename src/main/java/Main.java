import Item.IItem;
import Item.Item;
import Specimen.ISpecimen;
import Specimen.Specimen;
import java.io.File;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Main {

  public static void main(String[] args) {

    Loader loader = new Loader();
    loader.scan("src/main/resources/easy_1.ttp");

    System.out.println(loader);
  }
}
