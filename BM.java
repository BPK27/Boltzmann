import java.util.Random;


public class BM {

	int neurons;
	int P;
	double test1[][] = {{-1,-1,-1,-1,-1,1}, 
						{-1,-1,1,-1,-1,-1}, 
						{1,-1,-1,-1,-1,-1}};
	
	double test2[][] = {{-1, -1, -1, -1, -1, -1, -1, -1, -1, 1}, 
						{-1, 1, 1, 1, 1, 1, 1, 1, 1, 1}};
	
	double test3[][] = {{-1, -1, 1, -1, -1, -1, -1, 1, -1, -1, -1, -1, 1, -1, -1, -1, -1, 1, -1, -1, -1, -1, 1, -1, -1}, // 1
						{-1, 1, 1, 1, -1, -1, -1, -1, 1, -1, -1, 1, 1, 1, -1, -1, 1, -1, -1, -1, -1, 1, 1, 1, -1}, // 2
						{-1, 1, 1, 1, -1, -1, -1, -1, 1, -1, -1, 1, 1, 1, -1, -1, -1, -1, 1, -1, -1, 1, 1, 1, -1}, // 3
						{-1, 1, -1, 1, -1, -1, 1, -1, 1, -1, -1, 1, 1, 1, -1, -1, -1, -1, 1, -1, -1, -1, -1, 1, -1}, // 4
						{1, 1, 1, 1, -1, -1, 1, -1, -1, -1, 1, 1, 1, 1, -1, -1, -1, -1, 1, -1, 1, 1, 1, 1, -1}}; // 5
	double net[];
	double W[][];
	double dw[][];
	double examples[][];
	double samples;
	double eta;
	double max_flips;
			
	public BM(int n, int s){
		neurons = n;
		P = s;
		net = new double [neurons];
		W = new double [neurons][neurons];
		dw = new double [neurons][neurons];
		examples = new double [P][neurons];
		samples = 1000;
		eta = 0.001;
		max_flips = 200;
	}
	
	public void printWeights(){
		for (int i=0; i < neurons; i++){
			for (int j=0; j< neurons; j++){
				System.out.print(W[i][j] + " ");
			}
			System.out.println();
		}
		
	}
	
	public void printArray(double []a){
		for (int i=0; i < a.length; i++){
			System.out.print(a[i] + ", ");
		}
	}
	
	public void network(double [] newNet){
		net = newNet.clone();
		
	}
	
	public void printNetwork(){
		for(double t : net){
			System.out.print(t + ", ");
		}
		System.out.println();
	}
	
	public void setExamples(int testNum){
		for(int i = 0; i < P; i++){
			for(int j = 0; j < neurons; j++){
				if(testNum == 1){
					examples[i][j] = test1[i][j];
				}
				else if(testNum == 2){
					examples[i][j] = test2[i][j];
				}
				else if(testNum == 3){
					examples[i][j] = test3[i][j];
				}
			}
		}
	}
	
	public void initialiseWeights(){
		for(int i = 0; i < neurons; i++){
			for(int j = 0; j < neurons; j++){
				W[i][j] = 0.2*(Math.random()-0.5);
			}
		}printWeights();
	}
	
	public int subActivation(int j){
		int z = 0;
		for (int i=0;i<neurons;i++) {
			if (i != j) {
				z += W[j][i] * net[i];
			}
		}
		
		return z;
	}

	public void learning(){
		
		for (int s=0;s<samples;s++) { // “a lot of times”
			awake();
			setNet();
			converge();
			dreaming();
			for(int i = 0; i < neurons; i++){
				for(int j =0; j < neurons; j++){
					if (i != j){
						W[i][j] += dw[i][j];
					}
					dw[i][j] = 0;
				}
			}
		}
		
		
	}
		
	
	
	public void awake(){
		for (int i=0;i<P;i++) {// for all examples
			for (int j=0;j<neurons;j++) {//for all neurons
				for (int k=0;k<neurons;k++) {
						dw[j][k] += eta*examples[i][j]*examples[i][k];
				}
			}
		}
	}
	
	public void converge(){
		//let the BM evolve ‘til it converges
		for (int q=0;q<max_flips;q++) {
			for (int j=0;j<neurons;j++) {
				double z = subActivation(j);
				double T= 0.001*((max_flips-q)/max_flips); // some value for the temperature;
				if (1/(1+Math.exp(-2*z/T))>Math.random()) {
					net[j] = 1;
				} else {
					net[j] = -1;
				}
			}
		}
	}

	public void setNet(){
		for (int j=0;j<neurons;j++) {
				if (Math.random()<0.5) {
					net[j]=-1;
				} else {
				net[j]=1;
				}
			}
	}
	
	public void dreaming(){
		
			//store the correlations between neuron values in the
			//final state of the BM.
			//Note the “-=”, i.e. the BM is un-learning here.
			for (int j=0;j<neurons;j++) {
				for (int k=0;k<neurons;k++) {
					dw[j][k] -= eta*neurons*net[j]*net[k]/samples;
				}
			}
		}	
	
}
