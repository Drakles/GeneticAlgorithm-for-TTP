package World.Item;

import java.util.Objects;

public class Item implements IItem {

  private final int index;
  private final int profit;
  private final int weight;
  private final int cityIndex;

  public Item(int index, int profit, int weight, int cityIndex) {
    this.index = index;
    this.profit = profit;
    this.weight = weight;
    this.cityIndex = cityIndex;
  }

  public int getIndex() {
    return index;
  }

  public int getProfit() {
    return profit;
  }

  public int getWeight() {
    return weight;
  }

  public int getCityIndex() {
    return cityIndex;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Item item = (Item) o;
    return index == item.index &&
        profit == item.profit &&
        weight == item.weight &&
        cityIndex == item.cityIndex;
  }

  @Override
  public int hashCode() {
    return Objects.hash(index, profit, weight, cityIndex);
  }

  @Override
  public String toString() {
    return "World.Item{" +
        "index=" + index +
        ", profit=" + profit +
        ", weight=" + weight +
        ", cityIndex=" + cityIndex +
        '}';
  }
}
