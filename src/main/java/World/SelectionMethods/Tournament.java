package World.SelectionMethods;

import World.Specimen.ISpecimen;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Tournament implements ISelectionMethod {

  private final int numberOfSpecimenTaken;
  private final int numberOfTournaments;
  private final static Random random = new Random();

  public Tournament(int numberOfSpecimenTaken, int numberOfTournaments) {
    this.numberOfSpecimenTaken = numberOfSpecimenTaken;
    this.numberOfTournaments = numberOfTournaments;
  }

  @Override
  public List<ISpecimen> nextGeneration(List<ISpecimen> population) {
    List<ISpecimen> nextGeneration = new LinkedList<>();

    List<ISpecimen> populationCopy = new LinkedList<>(population);
    for (int i = 0; i < numberOfTournaments; i++) {
      Set<ISpecimen> tournamentSet = new HashSet<>(numberOfSpecimenTaken);
      for (int j = 0; j < numberOfSpecimenTaken; j++) {
        tournamentSet.add(populationCopy.remove(random.nextInt(populationCopy.size())));
      }
      nextGeneration
          .add(tournamentSet.stream().max(Comparator.comparingDouble(ISpecimen::getRateEvaluation))
              .get().copy());

      populationCopy.addAll(tournamentSet);
      tournamentSet.clear();
    }
    return nextGeneration;
  }

  @Override
  public String toString() {
    return "Tournament";
  }
}
