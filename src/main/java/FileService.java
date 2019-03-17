import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.List;

public class FileService {

  public static void createCsvFile(String fileName, List<String> header, List<List<String>> data) {
    try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName + ".csv"))) {
      bufferedWriter.write(saveLine(header));
      for (List<String> line : data) {
        bufferedWriter.newLine();
        bufferedWriter.write(saveLine(line));
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private static String saveLine(List<String> line) {
    StringBuilder builder = new StringBuilder();
    if (!line.isEmpty()) {
      builder.append(line.get(0));
      for (int i = 1; i < line.size(); i++) {
        builder.append(",").append(line.get(i));
      }
    }
    return builder.toString();
  }
}
