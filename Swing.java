import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class Swing extends JFrame {

    final Vector<Matrix> vect = new Vector<Matrix>();
    HashMap<String, Matrix> map = new HashMap<String, Matrix>();
    JTextArea area;
    DecimalFormat df = new DecimalFormat("#.###");

    public Swing() {
	initUI();

    }

    public final void initUI() {

	int windowWidth = 800;
	int windowHeight = 500;
	int bWidth = 80;
	int bHeight = 30;

	// set up main panel
	JPanel mainPanel = new JPanel();
	mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
	add(mainPanel);

	JPanel leftP = new JPanel();
	leftP.setLayout(new BoxLayout(leftP, BoxLayout.Y_AXIS));
	leftP.setAlignmentX(Component.LEFT_ALIGNMENT);
	mainPanel.add(leftP);

	leftP.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

	JPanel centerP = new JPanel();
	centerP.setLayout(new BoxLayout(centerP, BoxLayout.LINE_AXIS));
	mainPanel.add(centerP);

	centerP.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

	JPanel rightP = new JPanel();
	rightP.setLayout(new BoxLayout(rightP, BoxLayout.Y_AXIS));
	mainPanel.add(rightP);

	rightP.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

	// define new matrix

	JButton dimButton = new JButton("New Matrix");
	dimButton.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent arg0) {

		String name = "";
		int rows = 0;
		int columns = 0;

		String nameInput = JOptionPane
			.showInputDialog("Enter a name for the matrix (make sure it's less than 10 characters) e.g. A, B, C, etc.");

		while (!(nameInput.length() < 10 && !map.containsKey(nameInput)))
		{
		    if (nameInput.equals(Integer.toString(JOptionPane.CLOSED_OPTION))
			    || nameInput.equals(Integer
				    .toString(JOptionPane.CANCEL_OPTION)))
		    {

		    }
		    else
		    {
			nameInput = JOptionPane
				.showInputDialog("Something went wrong :(\nEnter a name for the matrix.");
		    }

		}

		name = nameInput;

		String elements = JOptionPane
			.showInputDialog("Enter the elements for this matrix from left-to-right and top-to-bottom. Press enter after finishing each row!");

		double[][] d = { { 2, 3 }, { 2, 1 } };
		Matrix c = new Matrix(2, 2, d);
		map.put("Woah", c);

		while (!validInput(elements))
		{
		    if (Integer.parseInt(elements) == JOptionPane.CLOSED_OPTION
			    || Integer.parseInt(elements) == JOptionPane.CANCEL_OPTION)
		    {

		    }

		    else
		    {
			elements = JOptionPane
				.showInputDialog("Something went wrong :(\nEnter the elements for this matrix from left-to-right and top-to-bottom. Press enter after finishing each row!");

		    }
		}

		// double[][] contents = getContents(elements, rows, columns);
		// Matrix dimmedMatrix = new Matrix(rows, columns, contents);
		// map.put(name, dimmedMatrix);
	    }

	});

	JButton editButton = new JButton("Edit Matrix");
	editButton.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent arg0) {
		// edit matrix

	    }

	});

	JButton viewButton = new JButton("View Matrix");
	viewButton.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent arg0) {

		final ArrayList<String> matrices = new ArrayList<String>();
		matrices.addAll(map.keySet());

		Object[] matList = matrices.toArray();

		final JFrame frame = new JFrame("View Matrix");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		final JComboBox comMatOne = new JComboBox(matList);
		comMatOne.setEditable(true);
		frame.add(comMatOne, BorderLayout.NORTH);

		final JLabel viewLabel = new JLabel("Choose a matrix to view!", null, JLabel.CENTER);
		frame.add(viewLabel, BorderLayout.CENTER);

		final JButton okButton = new JButton("OK");
		frame.add(okButton, BorderLayout.SOUTH);
		okButton.addActionListener(new ActionListener() {

		    @Override
		    public void actionPerformed(ActionEvent arg0) {
			if (matrices.size() != 0)
			{
			    String chosenMatrix = (String) comMatOne.getSelectedItem();
			    printMatrixString(chosenMatrix);
			    area.append("\n");
			}
			else
			{
			    area.append("Error: no matrix selected.\n");
			}
			frame.dispose();
		    }

		});

		frame.setSize(300, 200);
		frame.setVisible(true);	
		frame.setLocationRelativeTo(null);

		frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

	    }
	});

	// define add matrices button
	JButton addButton = new JButton("Add");
	addButton.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent arg0) {
		final JFrame mainFrame = new JFrame("Add Matrices");
		JPanel top = new JPanel();
		mainFrame.add(top);

		final ArrayList<String> matrices = new ArrayList<String>();
		matrices.addAll(map.keySet());
		Object[] matList = matrices.toArray();

		final JComboBox comMatOne = new JComboBox(matList);
		comMatOne.setEditable(true);
		top.add(comMatOne, BorderLayout.NORTH);

		final JLabel addLabel = new JLabel();
		addLabel.setText("Choose two equally sized matrices to add.");
		top.add(addLabel, BorderLayout.CENTER);

		final JComboBox comMatTwo = new JComboBox(matList);
		comMatTwo.setEditable(true);
		top.add(comMatTwo, BorderLayout.SOUTH);

		final JButton okButton = new JButton("OK");
		mainFrame.add(okButton, BorderLayout.SOUTH);
		okButton.addActionListener(new ActionListener() {

		    @Override
		    public void actionPerformed(ActionEvent arg0) {
			if (matrices.size() == 0)
			{
			    area.append("Error: no matrices selected\n");
			}
			else
			{
			    String addOne = (String) comMatOne.getSelectedItem();
			    String addTwo = (String) comMatTwo.getSelectedItem();
			    if (map.get(addOne).getRows() == map.get(addTwo).getRows()
				    && map.get(addOne).getColumns() == map.get(addTwo)
					    .getColumns())
			    {

				area.append("Adding matrices " + addOne + " and "
					+ addTwo);
				printMatrixString(addOne);
				area.append("\n + \n");
				printMatrixString(addTwo);
				area.append("\n = \n\n");
				printMatrix(Matrix.add(map.get(addOne), map.get(addTwo)));
				area.append("\n");
			    }

			    else
			    {
				area.append("Error: only equally sized matrices can be added.\n");

			    }
			}
			mainFrame.dispose();
		    }

		});

		mainFrame.setSize(300, 200);
		mainFrame.setVisible(true);
		mainFrame.setLocationRelativeTo(null);

		mainFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

	    }

	});

	// define subtract matrices button
	JButton subtractButton = new JButton("Subtract");
	subtractButton.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent arg0) {
		final JFrame mainFrame = new JFrame("Subtract Matrices");
		JPanel top = new JPanel();
		mainFrame.add(top);

		final ArrayList<String> matrices = new ArrayList<String>();
		matrices.addAll(map.keySet());
		Object[] matList = matrices.toArray();

		final JComboBox comMatOne = new JComboBox(matList);
		comMatOne.setEditable(true);
		top.add(comMatOne, BorderLayout.NORTH);

		final JLabel subLabel = new JLabel();
		subLabel.setText("Choose two equally sized matrices to subtract.");
		top.add(subLabel, BorderLayout.CENTER);

		final JComboBox comMatTwo = new JComboBox(matList);
		comMatTwo.setEditable(true);
		top.add(comMatTwo, BorderLayout.SOUTH);

		final JButton okButton = new JButton("OK");
		mainFrame.add(okButton, BorderLayout.SOUTH);
		okButton.addActionListener(new ActionListener() {

		    @Override
		    public void actionPerformed(ActionEvent arg0) {
			if (matrices.size() == 0)
			{
			    area.append("Error: no matrices selected.\n");
			}
			else
			{
			    String subtractOne = (String) comMatOne.getSelectedItem();
			    String subtractTwo = (String) comMatTwo.getSelectedItem();
			    if (map.get(subtractOne).getRows() == map.get(subtractTwo)
				    .getRows()
				    && map.get(subtractOne).getColumns() == map.get(
					    subtractTwo).getColumns())
			    {

				area.append("Subtracting matrices " + subtractOne
					+ " and " + subtractTwo);
				printMatrixString(subtractOne);
				area.append("\n + \n");
				printMatrixString(subtractTwo);
				area.append("\n = \n");
				printMatrix(Matrix.subtract(map.get(subtractOne),
					map.get(subtractTwo)));
				area.append("\n");
			    }

			    else
			    {
				area.append("Error: only equally sized matrices can be subtracted.\n");

			    }
			}
			mainFrame.dispose();
		    }

		});

		mainFrame.setSize(300, 200);
		mainFrame.setVisible(true);
		mainFrame.setLocationRelativeTo(null);

		mainFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

	    }

	});

	// define multiply matrices button
	JButton multiplyButton = new JButton("Multiply");
	multiplyButton.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent arg0) {
		// multiply matrices
	    }
	});

	// define transpose button
	JButton transposeButton = new JButton("Transpose");
	transposeButton.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent arg0) {
		// tranpose matrix
		
		final JFrame frame = new JFrame("Tranpose Matrix");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		final ArrayList<String> matrices = new ArrayList<String>();
		matrices.addAll(map.keySet());

		Object[] matList = matrices.toArray();

		final JComboBox comMatOne = new JComboBox(matList);
		comMatOne.setEditable(true);
		frame.add(comMatOne, BorderLayout.NORTH);

		final JLabel rrefLabel = new JLabel(
			"Choose a matrix to tranpose!",
			null, JLabel.CENTER);
		frame.add(rrefLabel);

		final JButton okButton = new JButton("OK");
		frame.add(okButton, BorderLayout.SOUTH);
		okButton.addActionListener(new ActionListener() {

		    @Override
		    public void actionPerformed(ActionEvent arg0) {
			if (matrices.size() != 0)
			{
			    String chosenMatrix = (String) comMatOne.getSelectedItem();
			    area.append("Finding the tranpose of " + chosenMatrix + "\n\n");
			    printMatrix(Matrix.transpose(map.get(chosenMatrix)));
			    area.append("\n");
			}
			else
			{
			    area.append("Error: no matrix selected.\n");
			}
			frame.dispose();
		    }

		});

		frame.setSize(300, 200);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);

		frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	    }
	});

	// define row reduce button

	JButton rowReduceButton = new JButton("Row Reduce");
	rowReduceButton.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent arg0) {
		// row reduce matrix

		final JFrame frame = new JFrame("Row Reduce Matrix");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		final ArrayList<String> matrices = new ArrayList<String>();
		matrices.addAll(map.keySet());

		Object[] matList = matrices.toArray();

		final JComboBox comMatOne = new JComboBox(matList);
		comMatOne.setEditable(true);
		frame.add(comMatOne, BorderLayout.NORTH);

		final JLabel rrefLabel = new JLabel(
			"Choose a matrix whose RREF (row-reduced echelon form) you want to find!",
			null, JLabel.CENTER);
		frame.add(rrefLabel);

		final JButton okButton = new JButton("OK");
		frame.add(okButton, BorderLayout.SOUTH);
		okButton.addActionListener(new ActionListener() {

		    @Override
		    public void actionPerformed(ActionEvent arg0) {
			if (matrices.size() != 0)
			{
			    String chosenMatrix = (String) comMatOne.getSelectedItem();
			    area.append("Finding the RREF of " + chosenMatrix + "\n\n");
			    printMatrix(Matrix.rref(map.get(chosenMatrix)));
			    area.append("\n");
			}
			else
			{
			    area.append("Error: no matrix selected.\n");
			}
			frame.dispose();
		    }

		});

		frame.setSize(600, 200);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);

		frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

	    }
	});

	JButton solveButton = new JButton("Solve Linear System");
	solveButton.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent arg0) {
		// solve linear system corresponding to matrix
	    }
	});

	JButton detButton = new JButton("Determinant");
	detButton.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent arg0) {
		// find determinant of square matrix

		final ArrayList<String> matrices = new ArrayList<String>();
		matrices.addAll(map.keySet());

		for (int i = 0; i < matrices.size(); i++)
		{
		    if (map.get(matrices.get(i)).getRows() != map.get(matrices.get(i))
			    .getColumns())
		    {
			matrices.remove(i);
		    }
		}
		Object[] matList = matrices.toArray();

		final JFrame frame = new JFrame("Determinant of Matrix");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		final JComboBox comMatOne = new JComboBox(matList);
		comMatOne.setEditable(true);
		frame.add(comMatOne, BorderLayout.NORTH);

		final JLabel addLabel = new JLabel();
		addLabel.setText("Choose a square matrix whose determinant you want to find!");
		frame.add(addLabel, BorderLayout.CENTER);

		final JButton okButton = new JButton("OK");
		frame.add(okButton, BorderLayout.SOUTH);
		okButton.addActionListener(new ActionListener() {

		    @Override
		    public void actionPerformed(ActionEvent arg0) {
			if (matrices.size() != 0)
			{
			    String chosenMatrix = (String) comMatOne.getSelectedItem();
			    area.append("Finding the determinant of " + chosenMatrix
				    + "\n\n");
			    printMatrix((map.get(chosenMatrix)));
			    area.append("\n The determinant of " + chosenMatrix
				    + " is:\n" + Matrix.det(map.get(chosenMatrix))
				    + "\n\n");
			}
			else
			{
			    area.append("Error: no matrix selected.\n\n");
			}
			frame.dispose();
		    }

		});

		frame.setSize(300, 200);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);

		frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	    }
	});

	JButton inverseButton = new JButton("Inverse");
	inverseButton.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent arg0) {
		// find inverse of matrix
	    }
	});

	JButton eigenButton = new JButton("Eigenvalues & Eigenvectors");
	eigenButton.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent arg0) {
		// find eigenvalues and vectors of square matrix
	    }
	});

	JButton traceButton = new JButton("Trace");
	traceButton.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent arg0) {
		// find trace of square matrix
	    }
	});

	// define clear button
	JButton clearButton = new JButton("Clear Log");
	clearButton.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent arg0) {
		area.setText("");
	    }
	});

	// define quit button
	JButton quitButton = new JButton("Quit");
	quitButton.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent event) {
		System.exit(0);
	    }
	});

	// define standard dimension size
	Dimension standardDimension = new Dimension(0, 10);

	// add buttons and rigid areas

	dimButton.setAlignmentX(CENTER_ALIGNMENT);
	leftP.add(dimButton);
	leftP.add(Box.createRigidArea(standardDimension));

	editButton.setAlignmentX(CENTER_ALIGNMENT);
	leftP.add(editButton);
	leftP.add(Box.createRigidArea(standardDimension));

	viewButton.setAlignmentX(CENTER_ALIGNMENT);
	leftP.add(viewButton);
	leftP.add(Box.createRigidArea(standardDimension));

	addButton.setAlignmentX(CENTER_ALIGNMENT);
	leftP.add(addButton);
	leftP.add(Box.createRigidArea(standardDimension));

	subtractButton.setAlignmentX(CENTER_ALIGNMENT);
	leftP.add(subtractButton);
	leftP.add(Box.createRigidArea(standardDimension));

	multiplyButton.setAlignmentX(CENTER_ALIGNMENT);
	leftP.add(multiplyButton);
	leftP.add(Box.createRigidArea(standardDimension));

	transposeButton.setAlignmentX(CENTER_ALIGNMENT);
	leftP.add(transposeButton);
	leftP.add(Box.createRigidArea(standardDimension));

	rowReduceButton.setAlignmentX(CENTER_ALIGNMENT);
	leftP.add(rowReduceButton);
	leftP.add(Box.createRigidArea(standardDimension));

	solveButton.setAlignmentX(CENTER_ALIGNMENT);
	leftP.add(solveButton);
	leftP.add(Box.createRigidArea(standardDimension));

	// Main Console Area

	mainPanel.add(Box.createVerticalGlue());
	JScrollPane pane = new JScrollPane();
	area = new JTextArea();
	area.setLineWrap(true);
	area.setWrapStyleWord(true);
	area.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	pane.getViewport().add(area);
	centerP.add(pane);

	area.setEditable(false);
	area.append("Welcome to Fun with Matrices! Use this as a matrix calculator, or a way to brush up on your knowledge of linear algebra! \n\nStart by defining or 'dimming' a matrix as shown at the top left!\n\n");

	detButton.setAlignmentX(CENTER_ALIGNMENT);
	rightP.add(detButton);
	rightP.add(Box.createRigidArea(standardDimension));

	inverseButton.setAlignmentX(CENTER_ALIGNMENT);
	rightP.add(inverseButton);
	rightP.add(Box.createRigidArea(standardDimension));

	eigenButton.setAlignmentX(CENTER_ALIGNMENT);
	rightP.add(eigenButton);
	rightP.add(Box.createRigidArea(standardDimension));

	traceButton.setAlignmentX(CENTER_ALIGNMENT);
	rightP.add(traceButton);
	rightP.add(Box.createRigidArea(standardDimension));

	clearButton.setAlignmentX(CENTER_ALIGNMENT);
	rightP.add(clearButton);
	rightP.add(Box.createRigidArea(standardDimension));

	quitButton.setAlignmentX(CENTER_ALIGNMENT);
	rightP.add(quitButton);
	rightP.add(Box.createRigidArea(standardDimension));

	setTitle("~ Fun with Matrices ~");
	setSize(windowWidth, windowHeight); // width, height
	setLocationRelativeTo(null);
	setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
	SwingUtilities.invokeLater(new Runnable() {
	    public void run() {
		Swing ex = new Swing();
		ex.setVisible(true);
	    }
	});
    }

    public static boolean isParsableToInt(String check) {
	try
	{
	    Integer.parseInt(check);
	    return true;
	}
	catch (NumberFormatException nfe)
	{
	    return false;
	}
    }

    private double[][] getContents(String elements, int rows, int columns) {
	double[][] contents = new double[rows][columns];
	for (int i = 0; i < rows; i++)
	{
	    for (int j = 0; j < columns; j++)
	    {
		contents[i][j] = Double.parseDouble(elements.split(" ")[columns * i + j]);
	    }
	}
	return contents;
    }

    private boolean validInput(String elements) {
	// todo
	return true;
    }

    private void printMatrix(Matrix matrix) {
	for (int i = 0; i < matrix.getRows(); i++)
	{
	    for (int j = 0; j < matrix.getColumns(); j++)
	    {
		area.append(df.format(matrix.getElement(i, j)) + " ");
	    }
	    area.append("\n");
	}
    }

    private void printMatrixString(String matrix) {
	area.append("\nMatrix: " + matrix + "\n");
	for (int i = 0; i < map.get(matrix).getRows(); i++)
	{
	    for (int j = 0; j < map.get(matrix).getColumns(); j++)
	    {
		area.append(df.format(map.get(matrix).getElement(i, j)) + " ");
	    }
	    area.append("\n");
	}

    }

}