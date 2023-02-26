import java.util.*;
import java.io.*;

public class TestNN {

  public static String delimiter = ",";
  public static LinkedList<String> label;
  public static int nColumns;
  public static LinkedList<LinkedList<String>> atributes;

  public static void read(String csvFile, LinkedList<String[]> l, LinkedList<String> label){
    try{
      File file = new File(csvFile);
      FileReader fr = new FileReader(file);
      BufferedReader br = new BufferedReader(fr);
      String line = "";
      String[] temp;
      line = br.readLine();
      temp = line.split(delimiter);
      for(String s : temp) label.add(s);
      while((line = br.readLine()) != null){
        temp = line.split(delimiter);
        l.add(temp);
      }
      br.close();
    } catch (IOException ioe){
      ioe.printStackTrace();
    }
  }

  public static double[][] read(String csvFile){
    LinkedList<String[]> l = new LinkedList<>();
    try{
      File file = new File(csvFile);
      FileReader fr = new FileReader(file);
      BufferedReader br = new BufferedReader(fr);
      String line = "";
      String[] temp;
      temp = line.split(delimiter);
      while((line = br.readLine()) != null){
        temp = line.split(delimiter);
        l.add(temp);
      }
      br.close();
    } catch (IOException ioe){
      ioe.printStackTrace();
    }
    double[][] v = new double[l.size()][l.get(0).length];
    int c= 0;
    for(String[] s : l){
      for(int i=0; i<s.length; i++){
        v[c][i] = Double.parseDouble(s[i]);
      }
      c++;
    }
    return v;
  }

  public static double[][] exceptID (double[][] in){
    double[][] nova = new double[in.length][in[0].length-1];
    for(int i=0; i<in.length; i++){
      for(int j=1; j<in[0].length; j++){
        nova[i][j-1] = in[i][j];
      }
    }
    return nova;
  }



	public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    System.out.println("Neural Network trained with iris.csv. ");
    System.out.println("Please enter the desired .csv file for testing: ");
    String testFile = in.nextLine();
    String csv_file = "iris.csv";
    LinkedList<String[]> l = new LinkedList<>();
    LinkedList<String> label = new LinkedList<>();

    TreeMap<String, Double> tm = new TreeMap<>();
    tm.put("Iris-setosa", 0.0);
    tm.put("Iris-versicolor", 0.5);
    tm.put("Iris-virginica", 1.0);

    read(csv_file, l, label);
    double classe[][] = new double[l.size()][1];
    double values[][] = new double[l.size()][l.get(1).length-2];
    int c = 0;
    for(String[] s : l){
      for(int i=1; i<s.length-1; i++) values[c][i-1] = Double.parseDouble(s[i]);
      String s1 = s[s.length-1];
      classe[c][0] = tm.get(s1);
      c++;
    }

		NeuralNetwork nn = new NeuralNetwork(4,4,1);
		List<Double>output;

		nn.fit(values, classe, 150000);
		double [][] input = read(testFile);
    double[][] input2 = exceptID(input);
    System.out.println();
		for(double d[]:input2) {
			output = nn.predict(d);
      double v = output.get(0);
      for(double p : d) System.out.print(p + " ");
      System.out.print("--> ");
      if(v <= 0.33) System.out.println("Iris-setosa");
      else if(v <= 0.66) System.out.println("Iris-versicolor");
      else System.out.println("Iris-virginica");
		}
	}
};
