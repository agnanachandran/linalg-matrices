package ca.pluszero;
import java.util.Scanner;

public class MatrixSolver {

	static int m;
	static int n;

	public static void main(String[] args) {
		// double[][] matrix = { { 2, 3, -1, -2 }, { 4, -3, 7, 8 }, { 1, 2, 0, 4
		// } };
		Scanner s = new Scanner(System.in);
		System.out.println("# of rows?");
		m = s.nextInt();
		System.out.println("# of columns?");
		n = s.nextInt();
		System.out.println("Now write each number in the matrix from left to"
				+ " right, top to bottom (like reading a newspaper),"
				+ " separated by a space or new line:");
		double[][] matrix = new double[m][n];

		// reads in each element of the matrix
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				matrix[i][j] = s.nextDouble();
			}
		}

		// m rows, n columns
		// closes the scanner
		s.close();

		boolean foundNonZero = false;
		int onRow = -1; // keep track of what row you found the non-zero element
		// on.
		// iterate through columns ->
		int countOnRow = 0;
		for (int i = 0; i < n; i++) { // iterate through column i
			for (int j = countOnRow; j < m; j++) {
				if (matrix[j][i] != 0) {
					onRow = j;
					foundNonZero = true;
					break;
				}
			}
			double pivot = 0;
			if (foundNonZero) {
				pivot = matrix[onRow][i];
			}
			if (foundNonZero && matrix[onRow][i] != 1) {
				for (int c = 0; c < n; c++) {
					matrix[onRow][c] /= pivot; // divide row by pivot
					// iff pivot !=0,1

				}
			}
			System.out.println("Reducing rows!");
			if (foundNonZero) {
				// look at rows above and below row you're working with
				for (int r = 0; r < m; r++) {
					double currRowLead = matrix[r][i];
					if (r != onRow && currRowLead != 0.0) {
						double multiplyRow = -1.0 * currRowLead;
						// apply EROs
						for (int c = 0; c < n; c++) {
							matrix[r][c] += multiplyRow * matrix[onRow][c];
						}
					}
				}

			}
			if (foundNonZero && onRow != countOnRow) {
				// swap rows as necessary
				for (int c = 0; c < n; c++) {
					double temp = matrix[countOnRow][c];
					matrix[countOnRow][c] = matrix[onRow][c];
					matrix[onRow][c] = temp;
				}
			}
			// print the matrix
			for (int k = 0; k < m; k++) {
				for (int j = 0; j < n; j++) {
					System.out.print(matrix[k][j] + " ");
				}
				System.out.println();
			}
			// reset to starting conditions

			if (countOnRow < m - 1 && foundNonZero) {
				countOnRow++;
			} else if (foundNonZero && countOnRow == m - 1) {
				break;
			}

			foundNonZero = false;
			onRow = -1;

		}

		// print the matrix
		for (int k = 0; k < m; k++) {
			for (int j = 0; j < n; j++) {
				System.out.print(matrix[k][j] + " ");
			}
			System.out.println();
		}

		/*
		 * System.out.print("Thus the answers are: "); double[] answers = new
		 * double[n]; for (int i = 0; i < n; i++) { answers[i] = matrix[i][n];
		 * System.out.print(answers[i] + " "); }
		 */
	}
}