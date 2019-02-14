import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class NQueenProblem {
	
	static int instance;
	static double saSolved = 0;
	static double gaSolved = 0;
	static double saSearchCost = 0;
	static double gaSearchCost = 0;
	static int SIZE;
	Queen chessBoard;
	double temperature;
	final int MAX_ITERATION = 10000;
	
	public NQueenProblem() {
		Scanner kb = new Scanner(System.in);
		System.out.print("How big should the board be? ");
		SIZE = kb.nextInt();
	}
	public void geneticAlgorithm() {
		boolean solved = false;
		double mutateRate = 0.5;
		Random rng = new Random();
		int[] temp;
		final int POPULATION_SIZE = 100;
		Queen[] population = new Queen[POPULATION_SIZE];
		for(int i = 0; i < population.length; i++) {
			population[i] = new Queen(SIZE);
			gaSearchCost++;
		}
		Arrays.sort(population); //potential problem of sorting using bad calc//switch comparing with calc
		Queen parent1, parent2;
		int i = 0;
		while(!solved && i++ <= MAX_ITERATION) {
			if(population[0].calcCost() == 0) {
				solved = true;
				System.out.println("Solution found at iteration #" + i + ".\n" + population[0]);
				gaSolved++;
				break;
			}
			if(i == MAX_ITERATION) {
				System.out.println("Max iteration reached. Couldn't solve.");
			}
			Queen[] newPopulation = new Queen[POPULATION_SIZE];
			for(int j = 0; j < POPULATION_SIZE; j++) {
				parent1 = population[rng.nextInt(10)];
				parent2 = population[rng.nextInt(10)];
				int crossover = rng.nextInt(SIZE - 1) + 1;
				temp = new int[SIZE];
				for(int k = 0; k < SIZE; k++) {
					if(k < crossover) {
						temp[k] = parent1.getPos()[k];
					}
					else {
						temp[k] = parent2.getPos()[k];
					}
				}
				if(Math.random() <= mutateRate) {
					int chosenCol = rng.nextInt(SIZE);
					int chosenRow;
					while((chosenRow = rng.nextInt(SIZE)) == chessBoard.getPos()[chosenCol]) {
					}
					temp[chosenCol] = chosenRow;
				}
				newPopulation[j] = new Queen(temp);
				gaSearchCost++;
			}
			population = newPopulation.clone();
			Arrays.sort(population);
		}
	}
	public void simulatedAnnealing() {
		saSearchCost++;
		chessBoard = new Queen(SIZE);
		temperature = 1000;
		int currentCost;
		boolean solved = false;
		for(int i = 0; i <= MAX_ITERATION && !solved; i++) {
			currentCost = chessBoard.calcCost();
			if(i == MAX_ITERATION)
				System.out.println("max iteration reached. Couldn't solve.");
			saSearchCost++;
			Queen temp = makeNeighbor();
			int tempCost = temp.calcCost();
			if(tempCost < currentCost) {
				chessBoard = temp;
				//System.out.println(chessBoard + " cost: " + chessBoard.calcCost());//%%%%%%
			}
			if(currentCost == 0) {
				solved = true;
				System.out.println("Solution found at iteration #" + i + ".\n" + chessBoard);
				saSolved++;
				break;
			}
			else {
				int dE = currentCost - tempCost;
				double acceptance = Math.min(Math.exp(dE / temperature), 1);
				//System.out.println("dE: " + dE + "temperature: " + temperature + "Acceptance:" + acceptance);
				if(Math.random() <= acceptance) {
					chessBoard = temp;
					//System.out.println(chessBoard + " cost: " + temp.calcCost());//%%%%%%
				}
			}
			temperature *= 0.99;
		}
	}
	
	public Queen makeNeighbor() {
		Random rng = new Random();
		int chosenCol = rng.nextInt(SIZE);
		int chosenRow;
		while((chosenRow = rng.nextInt(SIZE)) == chessBoard.getPos()[chosenCol]) {
		}
		Queen neighbor = new Queen(chessBoard);
		neighbor.setRow(chosenCol, chosenRow);
		return neighbor;
	}
	public static void main(String[] args) {
		NQueenProblem test = new NQueenProblem();
		Scanner kb = new Scanner(System.in);
		System.out.println("How many instances of N-Queen problems do you want to solve?");
		instance = kb.nextInt();
		System.out.println("----------Simulated Annealing----------");
		double saStartTime = System.nanoTime();
		for(int i = 0; i < instance; i++) {
			test.simulatedAnnealing();
		}
		double saEndTime = System.nanoTime();
		System.out.println("----------Genetic Algorithm----------");
		double gaStartTime = System.nanoTime();
		for(int i = 0; i < instance; i++) {
			test.geneticAlgorithm();
		}
		double gaEndTime = System.nanoTime();
		System.out.println("Simulated Annealing:\nn = " + SIZE + "\n% solved: " + saSolved / instance * 100 + "\nSearch Cost: " + saSearchCost / instance +  "\nAverage time (s): " + (saEndTime - saStartTime) / 1000000000 / instance);
		System.out.println("Genetic Algorithm:\nn = " + SIZE + "\n% solved: " + gaSolved / instance * 100  + "\nSearch Cost: " + gaSearchCost / instance + "\nAverage time (s): " + (gaEndTime - gaStartTime) / 1000000000 / instance);

	}

}
