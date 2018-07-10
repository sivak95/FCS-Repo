import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The Class Comparison.
 */
public class Comparison {

  /** The max. */
  public int max;

  /** The min. */
  public int min;

  /** The number of comparisons. */
  public static int numberOfComparisons = 0;

  /**
   * This function takes an Integer array, the first index and last index, as input and returns a
   * Comparison object containing min and max values.
   */
  public static Comparison minMax(Integer[] mainArray, int firstIndex, int lastIndex) {

    Comparison minMaxHolder = new Comparison();
    int mid;

    // If the subarray contains only one element,
    // no comparison is made
    if (firstIndex == lastIndex) {

      minMaxHolder.min = mainArray[firstIndex];
      minMaxHolder.max = mainArray[firstIndex];
      return minMaxHolder;

      // If the subarray contains two elements,
      // one comparison is made
    } else if (lastIndex == firstIndex + 1) {

      numberOfComparisons++;

      if (mainArray[firstIndex] < mainArray[lastIndex]) {
        minMaxHolder.min = mainArray[firstIndex];
        minMaxHolder.max = mainArray[lastIndex];
      } else {
        minMaxHolder.min = mainArray[lastIndex];
        minMaxHolder.max = mainArray[firstIndex];
      }

      return minMaxHolder;
    }

    // If the subarray contains more than two elements,
    // two comparisons are made

    Comparison leftSubArray = new Comparison();
    Comparison rightSubArray = new Comparison();

    int subArraySize = lastIndex - firstIndex + 1;

    // if the subarray contains an odd number of elements,
    // it is split in the middle
    if (subArraySize % 2 != 0) {
      mid = (firstIndex + lastIndex) / 2;

      leftSubArray = minMax(mainArray, firstIndex, mid);
      rightSubArray = minMax(mainArray, mid + 1, lastIndex);

      if (leftSubArray.min < rightSubArray.min) {
        minMaxHolder.min = leftSubArray.min;
      } else {
        minMaxHolder.min = rightSubArray.min;
      }
      numberOfComparisons++;

      if (leftSubArray.max > rightSubArray.max) {
        minMaxHolder.max = leftSubArray.max;
      } else {
        minMaxHolder.max = rightSubArray.max;
      }
      numberOfComparisons++;

      return minMaxHolder;
      // if the subarray contains an even number of elements,
      // the last two elements are considered as the right subarray
    } else {
      mid = lastIndex - 2;

      leftSubArray = minMax(mainArray, firstIndex, mid);
      rightSubArray = minMax(mainArray, mid + 1, lastIndex);

      if (leftSubArray.min < rightSubArray.min) {
        minMaxHolder.min = leftSubArray.min;
      } else {
        minMaxHolder.min = rightSubArray.min;
      }
      numberOfComparisons++;

      if (leftSubArray.max > rightSubArray.max) {
        minMaxHolder.max = leftSubArray.max;
      } else {
        minMaxHolder.max = rightSubArray.max;
      }
      numberOfComparisons++;

      return minMaxHolder;

    }
  }


  /**
   * Reads the contents of the file and returns an integer array with the temperature values.
   *
   * @return the integer[]
   * @throws NumberFormatException the number format exception
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static Integer[] readFile() throws NumberFormatException, IOException {

    BufferedReader reader = new BufferedReader(new FileReader(new File("Input.txt")));
    List<Integer> temperatureList = new ArrayList<Integer>();
    String line;

    while ((line = reader.readLine()) != null) {
      String[] lineContents = line.split(",");

      // Converting fahrenheit and kelvin to celsius
      if (lineContents[4].equals("C")) {
        double temperatureValue = Double.parseDouble(lineContents[3]);
        int finalValue = (int) temperatureValue;
        temperatureList.add(finalValue);
      } else if (lineContents[4].equals("K")) {
        double tempInKelvin = Double.parseDouble(lineContents[3]);
        int tempInCelsius = (int) (tempInKelvin - 273.15);
        temperatureList.add(tempInCelsius);
      } else {
        double tempInFahrenheit = Double.parseDouble(lineContents[3]);
        int tempInCelsius = (int) ((tempInFahrenheit - 32) / 1.8);
        temperatureList.add(tempInCelsius);
      }
    }
    reader.close();

    Integer[] inputArray = temperatureList.toArray(new Integer[temperatureList.size()]);

    return inputArray;
  }


  /**
   * Writes the final minimum and maximum values to the output file.
   */
  public static void writeToFile(String text) throws IOException {
    BufferedWriter output = null;
    File file = new File("Output.txt");
    output = new BufferedWriter(new FileWriter(file));
    output.write(text);
    output.close();
  }



  /**
   * The main function.
   *
   */
  public static void main(String[] args) throws NumberFormatException, IOException {

    Integer[] input = readFile();

    int inputSize = input.length;
    Comparison minMaxOutput = new Comparison();

    minMaxOutput = minMax(input, 0, inputSize - 1);

    String minString = Integer.toString(minMaxOutput.min);
    String maxString = Integer.toString(minMaxOutput.max);
    String outputString = minString + "," + maxString;

    writeToFile(outputString);

    System.out.println("Input: " + Arrays.toString(input));
    System.out.println("Total number of elements: " + inputSize);
    System.out.println("Minimum temperature: " + minMaxOutput.min + " Celsius");
    System.out.println("Maximum temperature: " + minMaxOutput.max + " Celsius");
    System.out.println("Total number of comparisons: " + Comparison.numberOfComparisons);

  }
}
