import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;


public class FIAR extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JLabel[][] boardLabels = new JLabel[10][12];
	ImageIcon[][] defSq = new ImageIcon[10][12];
	ImageIcon[][] redSq = new ImageIcon[10][12];
	ImageIcon[][] blueSq = new ImageIcon[10][12];
	String currentPlayer = "blue";
	final String[][] lockedPlaces = new String[10][12];


	FIAR(){
		//Declarations

		final JPanel board = new JPanel();
		final JPanel players = new JPanel();
		JPanel setNames = new JPanel();
		JLabel splash = new JLabel(new ImageIcon(getClass().getResource("fiar.jpg")));
		final String[] playerNames = new String[2];


		//Initialisations


		for (int x = 0; x < 10; x++){
			for (int y = 0; y < 12; y++){
				boardLabels[x][y] = new JLabel();

			}
		}

		for (int x = 0; x < 10; x++){
			for (int y = 0; y < 12; y++){
				defSq[x][y] = new ImageIcon(getClass().getResource("defaultSquare.png"));
				boardLabels[x][y].setIcon(defSq[x][y]);
			}
		}

		for (int x = 0; x < 10; x++){
			for (int y = 0; y < 12; y++){
				redSq[x][y] = new ImageIcon(getClass().getResource("redSquare.png"));
			}
		}

		for (int x = 0; x < 10; x++){
			for (int y = 0; y < 12; y++){
				blueSq[x][y] = new ImageIcon(getClass().getResource("blueSquare.png"));
			}
		}

		for (int x = 0; x < 10; x++){
			for (int y = 0; y < 12; y++){
				lockedPlaces[x][y] = "0";
			}
		}

		// Setting up Game Board
		board.setLayout(new GridLayout(10, 12, 0, 0));

		for (int x = 0; x < 10; x++){
			for (int y = 0; y < 12; y++){
				boardLabels[x][y].setBackground(Color.BLACK);
				board.add(boardLabels[x][y]);

			}
		}

		// Setting up OpeningScreen

		players.setLayout(new GridBagLayout());
		GridBagConstraints gbcPlayers = new GridBagConstraints();
		gbcPlayers.insets = new Insets(5,5,5,5);

		gbcPlayers.gridx = 0;
		gbcPlayers.gridy = 0;
		players.add(splash);

		// Setting up Add Players

		final JTextField nameField1 = new JTextField("Player One", 16); //Character limit is (16)
		setNames.add(nameField1);

		final JTextField nameField2 = new JTextField("Player Two", 16); //Character limit is (16)
		setNames.add(nameField2);

		JButton beginGame = new JButton("Begin Game");
		setNames.add(beginGame);

		gbcPlayers.gridx = 0;
		gbcPlayers.gridy = 1;
		players.add(setNames, gbcPlayers);

		setLayout(new GridBagLayout());
		GridBagConstraints gbcBoard = new GridBagConstraints();
		gbcBoard.insets = new Insets(5,5,5,5);

		gbcBoard.gridx = 0;
		gbcBoard.gridy = 0;

		add(board, gbcBoard);	
		board.setVisible(false);

		add(players, gbcBoard);

		for (int x = 0; x < 10; x++){
			for (int y = 0; y < 12; y++){

				boardLabels[x][y].addMouseListener(new MouseListener(){

					@Override
					public void mouseClicked(MouseEvent arg0) {	
					}
					@Override
					public void mouseEntered(MouseEvent arg0) {	
					}
					@Override
					public void mouseExited(MouseEvent arg0) {
					}
					@Override
					public void mousePressed(MouseEvent evt) {
						int xLoc = evt.getComponent().getX()/50;
						System.out.println(xLoc);
						int yLoc = evt.getComponent().getY()/50;
						System.out.println(yLoc);

						if ((currentPlayer.equals("blue")) && (lockedPlaces[yLoc][xLoc].equals("0"))){

							System.out.println("Blue (" + xLoc + "," + yLoc + ")");
							boardLabels[yLoc][xLoc].setIcon(blueSq[yLoc][xLoc]);
							switchCurPlayer();
							lockedPlaces[yLoc][xLoc] = "B";
							if(checkWinNew("BBBBB", xLoc, yLoc) == true){

								if (JOptionPane.showConfirmDialog(getParent(), "Player: " + playerNames[0] + 
										" Wins!\n" + "Play Again?", playerNames[0] + 
										" Wins!", JOptionPane.YES_NO_OPTION) == 0){
									reset();

								} else {
									System.exit(0);
								}
							}
						} else if (currentPlayer.equals("red") && (lockedPlaces[yLoc][xLoc].equals("0"))){

							System.out.println("Red (" + xLoc + "," + yLoc + ")");
							boardLabels[yLoc][xLoc].setIcon(redSq[yLoc][xLoc]);
							switchCurPlayer();
							lockedPlaces[yLoc][xLoc] = "R";
							if(checkWinNew("RRRRR", xLoc, yLoc) == true){

								if (JOptionPane.showConfirmDialog(getParent(), "Player: " + playerNames[1] + 
										" Wins!\n" + "Play Again?", playerNames[1] + 
										" Wins!", JOptionPane.YES_NO_OPTION) == 0){
									reset();
								} else {
									System.exit(0);
								}
							}
						}
					}
					@Override
					public void mouseReleased(MouseEvent arg0) {
					}
				});
			}
		}

		beginGame.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent arg0) {	
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {	
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
			}
			@Override
			public void mousePressed(MouseEvent evt) {
				playerNames[0] = nameField1.getText();
				playerNames[1] = nameField2.getText();

				board.setVisible(true);
				players.setVisible(false);
			}
			@Override
			public void mouseReleased(MouseEvent arg0) {	
			}
		});
	}

	private void switchCurPlayer(){

		if (currentPlayer.equals("blue")){

			currentPlayer = "red";

		} else if (currentPlayer.equals("red")){

			currentPlayer = "blue";

		}
	}

	private void reset(){
		
		
		
		for (int x = 0; x < 10; x++){
			for (int y = 0; y < 12; y++){
				defSq[x][y] = new ImageIcon(getClass().getResource("defaultSquare.png"));
				boardLabels[x][y].setIcon(defSq[x][y]);
			}
		}

		for (int x = 0; x < 10; x++){
			for (int y = 0; y < 12; y++){
				redSq[x][y] = new ImageIcon(getClass().getResource("redSquare.png"));
			}
		}

		for (int x = 0; x < 10; x++){
			for (int y = 0; y < 12; y++){
				blueSq[x][y] = new ImageIcon(getClass().getResource("blueSquare.png"));
			}
		}
		
		for (int x = 0; x < 10; x++){
			for (int y = 0; y < 12; y++){
				lockedPlaces[x][y] = "0";
			}
		}
		
		currentPlayer = "blue";
	}
	private boolean checkWinNew(String lastMove, int x, int y){
		String checkStr = "";
		// check in y
		for (int i = -5; i < 5; i++){
			if ((y + i) >= 0 && (y + i) < 10){
				checkStr += lockedPlaces[y+i][x];
			}
		}
		
		System.out.println(checkStr);

		if (checkStr.contains(lastMove)){
			return true;
		} else {
			checkStr = "";
		}
		//check in x
		for (int i = -5; i < 5; i++){
			if ((x + i) >= 0 && (x + i) < 12){
				checkStr += lockedPlaces[y][x+i];
			}
		}
		
		System.out.println(checkStr);

		if (checkStr.contains(lastMove)){
			
			return true;
		} else {
			
			checkStr = "";
		}
		//check in neg grad
		for (int i = -5; i < 5; i++){
			
			if ((x + i) >= 0 && (x + i) < 12 && (y + i) >= 0 && (y + i) < 10){
				
				checkStr += lockedPlaces[y+i][x+i];
			}
		}
		
		System.out.println(checkStr);

		if (checkStr.contains(lastMove)){
			return true;
		} else {
			checkStr = "";
		}
		//check in pos grad
		for (int i = -5; i < 5; i++){
			
			if (((x + i) >= 0) && ((x + i) < 12) && ((y - i) >= 0) && ((y - i) < 10)){
				
				checkStr += lockedPlaces[y-i][x+i];
			}
		}
		
		System.out.println(checkStr);

		if (checkStr.contains(lastMove)){
			
			return true;
		} else {
			
			checkStr = "";
		}
		
		return false;
	}
	/*
	  Broken. Doesn't check for last moves in the middle of the row.
	 
	private boolean checkWin(String lastMove, int x, int y){
		String winCheck = "";

		for (int i = 0; i < 5; i++){
			if ((y + i < 10)){
				winCheck += lockedPlaces[y+i][x];
			}
		}
		System.out.println(winCheck);

		if (winCheck.equals(lastMove)){
			return true;
		}

		winCheck = "";

		for (int i = 0; i < 5; i++){
			if ((y - i >= 0)){
				winCheck += lockedPlaces[y-i][x];
			}

		}
		System.out.println(winCheck);

		if (winCheck.equals(lastMove)){
			return true;
		}
		winCheck = "";

		for (int i = 0; i < 5; i++){
			if ((x + i < 12)){
				winCheck += lockedPlaces[y][x+i];
			}
		}
		System.out.println(winCheck);

		if (winCheck.equals(lastMove)){
			return true;
		}
		winCheck = "";

		for (int i = 0; i < 5; i++){
			if ((x - i >= 0)){
				winCheck += lockedPlaces[y][x-i];
			}
		}
		System.out.println(winCheck);

		if (winCheck.equals(lastMove)){
			return true;
		}
		winCheck = "";

		for (int i = 0; i < 5; i++){
			if ((y + i < 10) && (x + i < 12)){
				winCheck += lockedPlaces[y+i][x+i];
			}
		}
		System.out.println(winCheck);

		if (winCheck.equals(lastMove)){
			return true;
		}
		winCheck = "";

		for (int i = 0; i < 5; i++){
			if ((y - i >= 0) && (x - i >= 0)){
				winCheck += lockedPlaces[y-i][x-i];
			}
		}
		System.out.println(winCheck);

		if (winCheck.equals(lastMove)){
			return true;
		}
		winCheck = "";

		for (int i = 0; i < 5; i++){
			if ((y + i < 10) && (x - i >= 0)){
				winCheck += lockedPlaces[y+i][x-i];
			}
		}
		System.out.println(winCheck);

		if (winCheck.equals(lastMove)){
			return true;
		}
		winCheck = "";

		for (int i = 0; i < 5; i++){
			if ((y - i >= 0) && (x + i < 12)){
				winCheck += lockedPlaces[y-i][x+i];
			}
		}
		System.out.println(winCheck);

		if (winCheck.equals(lastMove)){
			return true;
		} else {
			return false;
		}
	}
*/
	public static void main(String[] args){

		try { 
			
			UIManager.put("nimbusBase", new Color(250, 250, 250));
			UIManager.put("nimbusBlueGrey", new Color(213, 213, 202));
			UIManager.put("control", new Color(250, 250, 250)); 

			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} 
		catch(Exception e){ 
			
			System.out.print("Stacktrace: " + e);
		}

		FIAR gui = new FIAR();

		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.setSize(700, 600);
		gui.setTitle("FIAR: Five in a Row");
		gui.setVisible(true);

	}
}
