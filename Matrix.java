import java.util.Arrays;

public class Matrix {
    private int rows;
    private int columns;
    private double[][] elements;
    private final static String NON_SQUARE_ERROR = "Error! Non-square matrix";

    public Matrix(int rows, int columns, double[][] elements) {
	this.rows = rows;
	this.columns = columns;
	this.elements = elements;
    }

    public static Matrix add(Matrix a, Matrix b) {
	double result[][] = new double[a.rows][a.columns];
	for (int i = 0; i < a.rows; i++)
	{
	    for (int j = 0; j < a.columns; j++)
	    {
		result[i][j] = a.getElement(i, j) + b.getElement(i, j);
	    }
	}

	Matrix sum = new Matrix(a.rows, a.columns, result);
	return sum;
    }

    public static Matrix subtract(Matrix a, Matrix b) {
	double result[][] = new double[a.rows][a.columns];
	for (int i = 0; i < a.rows; i++)
	{
	    for (int j = 0; j < a.columns; j++)
	    {
		result[i][j] = a.getElement(i, j) - b.getElement(i, j);
	    }
	}

	Matrix diff = new Matrix(a.rows, a.columns, result);
	return diff;
    }

    public static Matrix multiply(Matrix a, Matrix b) {

	double result[][] = new double[a.rows][b.columns];
	for (int i = 0; i < a.rows; i++)
	{
	    for (int j = 0; j < b.columns; j++)
	    {
		double prod = 0;

		for (int k = 0; k < a.columns; k++)
		{
		    prod += a.getElement(i, k) * b.getElement(k, j);
		}
		result[i][j] = prod;
	    }
	}

	Matrix product = new Matrix(a.rows, b.columns, result);
	return product;
    }

    public static Matrix transpose(Matrix a) {

	double[][] result = new double[a.columns][a.rows];

	for (int i = 0; i < a.columns; i++)
	{
	    for (int j = 0; j < a.rows; j++)
	    {
		result[i][j] = a.getElement(j, i);
	    }
	}

	Matrix trans = new Matrix(a.columns, a.rows, result);
	return trans;
    }

    public static double trace(Matrix a) {
	if (a.getRows() != a.getColumns())
	{
	    System.err.println("Error, non-square matrix");
	    return -1.0;
	}
	else
	{
	    double traceSum = 0;
	    for (int i = 0; i < a.getRows(); i++)
	    {
		traceSum += a.getElement(i, i);
	    }

	    return traceSum;
	}
    }

    public static Matrix rref(Matrix a) {

	int m = a.getRows();
	int n = a.getColumns();
	double[][] matrix = new double[m][n];

	for (int i = 0; i < m; i++)
	{
	    for (int j = 0; j < n; j++)
	    {
		matrix[i][j] = a.getElement(i, j);
	    }
	}

	boolean foundNonZero = false;
	int onRow = -1; // keep track of what row you found the non-zero element
	// on.
	// iterate through columns ->
	int countOnRow = 0;
	for (int i = 0; i < n; i++)
	{ // iterate through column i
	    for (int j = countOnRow; j < m; j++)
	    {
		if (matrix[j][i] != 0)
		{
		    onRow = j;
		    foundNonZero = true;
		    break;
		}
	    }
	    double pivot = 0;
	    if (foundNonZero)
	    {
		pivot = matrix[onRow][i];
	    }
	    if (foundNonZero && matrix[onRow][i] != 1)
	    {
		for (int c = 0; c < n; c++)
		{
		    matrix[onRow][c] /= pivot; // divide row by pivot
		    // iff pivot !=0,1

		}
	    }

	    System.out.println("Reducing rows!");
	    if (foundNonZero)
	    {
		// look at rows above and below row you're working with
		for (int r = 0; r < m; r++)
		{
		    double currRowLead = matrix[r][i];
		    if (r != onRow && currRowLead != 0.0)
		    {
			double multiplyRow = -1.0 * currRowLead;
			// apply EROs
			for (int c = 0; c < n; c++)
			{
			    matrix[r][c] += multiplyRow * matrix[onRow][c];
			}
		    }
		}

	    }
	    if (foundNonZero && onRow != countOnRow)
	    {
		// swap rows as necessary
		for (int c = 0; c < n; c++)
		{
		    double temp = matrix[countOnRow][c];
		    matrix[countOnRow][c] = matrix[onRow][c];
		    matrix[onRow][c] = temp;
		}
	    }
	    // print the matrix
	    for (int k = 0; k < m; k++)
	    {
		for (int j = 0; j < n; j++)
		{
		    System.out.print(matrix[k][j] + " ");
		}
		System.out.println();
	    }
	    // reset to starting conditions

	    if (countOnRow < m - 1 && foundNonZero)
	    {
		countOnRow++;
	    }
	    else if (foundNonZero && countOnRow == m - 1)
	    {
		break;
	    }

	    foundNonZero = false;
	    onRow = -1;

	}

	for (int i = 0; i < m; i++)
	{
	    for (int j = 0; j < n; j++)
	    {
		if (-0.001 < matrix[i][j] && 0.001 > matrix[i][j])
		{
		    matrix[i][j] = 0;
		}
	    }
	}

	Matrix result = new Matrix(m, n, matrix);
	return result;

    }

    public static double det(Matrix a) {

	// make sure matrix is square BEFORE being used.

	if (a.rows != a.columns)
	{
	    // error
	    System.err.println(NON_SQUARE_ERROR);
	    return -1;
	}

	else
	{
	    return detOfArray(a.elements);

	}
    }

    private static double detOfArray(double[][] matrix) {

	double det;
	if (matrix[0].length == 1)
	{
	    return matrix[0][0];
	}

	else if (matrix[0].length == 2)
	{
	    return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
	}
	else
	{
	    det = 0;
	    for (int i = 0; i < matrix.length; i++)
	    {
		det += Math.pow(-1, i)
			* detOfArray(subArray(matrix, 0, i, matrix.length))
			* matrix[0][i];
	    }
	}
	return det;
    }

    private static double[][] subArray(double[][] matrix, int i, int j, int n) {
	double[][] subMatrix = new double[n - 1][n - 1];

	// if it does not have the same row number or column number, put it in!

	for (int a = 0; a < n; a++)
	{
	    for (int b = 0; b < n; b++)
	    {
		if (a != i && b != j)
		{
		    if (a < i && b < j)
		    {
			subMatrix[a][b] = matrix[a][b];
		    }
		    // !(a<i && b < j) = a>i || b > j
		    else
		    // a > i or b > i
		    {
			if (a > i && b > j) // a > i and b > j
			{
			    subMatrix[a - 1][b - 1] = matrix[a][b];
			}
			else if (a > i && b < j)
			// a> i and b < j
			{
			    subMatrix[a - 1][b] = matrix[a][b];
			}
			else
			// a < i and b > j
			{
			    subMatrix[a][b - 1] = matrix[a][b];
			}
		    }
		}
	    }
	}
	return subMatrix;
    }

    public double getElement(int i, int j) {
	return elements[i][j];
    }

    public int getRows() {
	return this.rows;
    }

    public int getColumns() {
	return this.columns;
    }

    public static boolean isSquare(Matrix a) {
	return a.getColumns() == a.getRows();
    }

    public static void main(String[] args) {
	double a[][] = { { 3, 2, 1 }, { 3, 1, 2 }, { 2, 1, 3 } };
	double b[][] = { { 3, 2, 1 }, { 3, 1, 2 }, { 2, 1, 3 } };
	double d[][] = { { 9091290, 12, -10.2213, 16 }, { 2, 4, 84, -10 },
		{ 12.12, 1243.4124514, 12.13313, 2 } };

	Matrix A = new Matrix(3, 3, a);
	Matrix B = new Matrix(3, 3, b);
	Matrix C = new Matrix(3, 4, d);

	Matrix c = (rref(C));
	for (int i = 0; i < c.rows; i++)
	{
	    for (int j = 0; j < c.columns; j++)
	    {
		System.out.print(c.getElement(i, j) + " ");
	    }
	    System.out.println();
	}
	System.out.println("\n" + det(A));
    }
}