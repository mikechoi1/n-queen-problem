import java.util.Random;

public class Queen implements Comparable<Queen> {

	private int[] position;
	private int cost;
	
	public Queen(int n) {
		Random rng = new Random();
		position = new int[n];
		for(int i = 0; i < n; i++) {
			position[i] = rng.nextInt(n);
		}
		setCost();
	}
	public Queen(Queen copy) {
		position = copy.position.clone();
		setCost();
	}
	public Queen(int[] chessboard) {
		position = chessboard.clone();
		setCost();
	}
	public Queen copy(int[] chessboard) {
		int[] temp = new int[chessboard.length];
		for(int i = 0; i < temp.length; i++) {
			temp[i] = chessboard[i];
		}
		return new Queen(temp);
	}
//	public boolean canAttack(Queen other) {
//		boolean canAttack = false;
//		if(row == other.row || col == other.col) {
//			canAttack = true;
//		}
//		else if(Math.abs(col - other.getCol()) == Math.abs(row - other.getRow())) {
//			canAttack = true;
//		}
//		return canAttack;
//	}
	public void setRow(int col, int row) {
		position[col] = row;
	}
	public void setCost() {
		cost = calcCost();
	}
	public int calcCost() {
		int cost = 0;
		for(int i = 0; i < position.length - 1; i++) {
			for(int j = i + 1; j < position.length; j++) {
				//same row
				if(position[i] == position[j]) {
					cost++;
				}
				//same col
				else if(i == j) {
					cost++;
				}
				//same diagonal
				else if(Math.abs(i - j) == Math.abs(position[i] - position[j])) {
					cost++;
				}
			}
		}
		return cost;
	}
	public int getCost() {
		return cost;
	}
	public int[] getPos() {
		return position;
	}
	@Override
	public int compareTo(Queen o2) {
		return Double.compare(this.getCost(), o2.getCost());
	}
	public String toString() {
		String content = "";
		for(int i = 0; i < position.length; i++) {
			for(int j = 0; j < position.length; j++) {
				if(position[j] == i) {
					content += " Q ";
				}
				else {
					content += " - ";
				}
			}
			content += "\n";
		}
		return content;
	}
}
