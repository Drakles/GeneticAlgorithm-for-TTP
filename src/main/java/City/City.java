package City;

public class City implements ICity{
  private final int index;
  private final int x;
  private final int y;

  public City(int index, int x, int y) {
    this.index = index;
    this.x = x;
    this.y = y;
  }


  public int getIndex() {
    return index;
  }

  public double getX() {
    return x;
  }

  public double getY() {
    return y;
  }
}
