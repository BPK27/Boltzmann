import java.awt.print.Printable;
import java.io.ObjectInputStream.GetField;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		double[][] pattern = {{-1, -1, -1, -1, 1, 1}, {-1, -1, -1, -1, -1, -1}, {1, 1, 1, 1, -1, -1}};
		double[][] pattern2 = {{-1, -1, 1, -1, 1, -1, -1, -1, -1, 1},{-1, 1, -1, 1, 1, -1, 1, 1, 1, 1}};
		
		
		BM b = new BM(10,2);
		//BM b = new BM(6,3);
		b.initialiseWeights();
		b.setExamples(2);  //test 1, 2
		b.learning();
		
		
		for(int i = 0; i < 2; i++){
			b.printArray(pattern2[i]);
			System.out.println("\n");
			
			
			for(int j = 0; j < 5; j++){
				b.network(pattern2[i]);  // pass in each time?
				b.converge();
				b.printNetwork();
			}
			System.out.println("********");
			
		}
		b.printWeights();
		
	}

}
