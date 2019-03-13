package Item;

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

  public int getItemIndex() {
    return index;
  }

  public int getItemProfit() {
    return profit;
  }

  public int getItemWeight() {
    return weight;
  }

  public int getItemCityIndex() {
    return cityIndex;
  }

  @Override
  public String toString() {
    return "Item{" +
        "index=" + index +
        ", profit=" + profit +
        ", weight=" + weight +
        ", cityIndex=" + cityIndex +
        '}';
  }
}
