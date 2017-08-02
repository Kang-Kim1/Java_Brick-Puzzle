package cse115.lab11.model;

import java.awt.Color;
import java.util.ArrayList;

import cse115.lab11.observer.Observer;
import cse115.lab11.view.BoardGUI;

public class BoardModel implements Observer {

	// game stopper
	public boolean _isGameOver;
	
	// turn info
	public static int MAX_TURN = 10;
	public int _turn;

	// advanced scoring implemented
	public int _score;
	public int _numOfMatchedTile;

	// size of board
	public static int ROW = 5;
	public static int COL = ROW;

	private BoardGUI _gui;

	private ArrayList<ArrayList<Tile>> _tileList;

	// used for lab 10
	private int _swapCounter = 0;
	
	// tile that firslty picked to check if swappable
	private ArrayList<Integer> _pickedTile; // store info about firstly picked

	private boolean _matchChecker;
	private boolean _tileSelected = false;

	public BoardModel() {
		_isGameOver = false;
		_score = 0;
		_turn = MAX_TURN;
		_pickedTile = new ArrayList<>();
		_matchChecker = false;
		_tileList = new ArrayList<>();

		initGame();
		_gui = new BoardGUI(this);
	}

	public void initGame() {
		System.out.println("here is initGame()");
		for (int i = 0; i < ROW; i++) {
			ArrayList<Tile> tl = new ArrayList<>();
			for (int j = 0; j < COL; j++) {
				Tile t = new Tile(i, j);
				tl.add(t);
			}
			_tileList.add(tl);
		}
		initBeginnableChecker();
	}

	public void initBeginnableChecker() {
		System.out.println("here is initBeginnableChecker()");
		boolean isFirstTurn = _turn == MAX_TURN;
		if (isFirstTurn) {
			if (checkMatches()) {
				// System.out.println("matches exists cant initalzie");
				_tileList.clear();
				initGame();
			}
		}
	}

	public void printMatches() {
		if (_matchChecker) {
			System.out.println("The board has a match.");
		} else {
			System.out.println("The board has NO match.");
		}
	}

	public boolean checkMatches() {
		System.out.println("checkMathces()");
		// pointer picks
		boolean matchExists = false;

		for (int i = 0; i < ROW - 2; i++) {
			for (int j = 0; j < COL; j++) {
				// same row case
				if (_tileList.get(i).get(j).get_color().equals(_tileList.get(i + 1).get(j).get_color())
						&& _tileList.get(i).get(j).get_color().equals(_tileList.get(i + 2).get(j).get_color())) {
					// System.out.println("match!!");
					matchExists = true;
					return matchExists;
				}
			}
		}
		for (int i = 0; i < ROW; i++) {
			for (int j = 0; j < COL - 2; j++) {
				// same col case
				if (_tileList.get(i).get(j).get_color().equals(_tileList.get(i).get(j + 1).get_color())
						&& _tileList.get(i).get(j).get_color().equals(_tileList.get(i).get(j + 2).get_color())) {
					// System.out.println("match!!");
					matchExists = true;
					return matchExists;
				}
			}
		}
		return matchExists;
	}

	public boolean updateMatches() {
		// pointer picks
		boolean matchExists = false;

		int rowNum = 0;
		for (int i = 0; i < ROW - 2; i++) {
			for (int j = 0; j < COL; j++) {

				// same row case
				if (_tileList.get(i).get(j).get_color().equals(_tileList.get(i + 1).get(j).get_color())
						&& _tileList.get(i).get(j).get_color().equals(_tileList.get(i + 2).get(j).get_color())) {
					rowNum = 3;

					if (i + 3 < COL) {
						if (_tileList.get(i).get(j).get_color().equals(_tileList.get(i + 3).get(j).get_color())) {
							rowNum++;
							if (i + 4 < COL) {
								if (_tileList.get(i).get(j).get_color()
										.equals(_tileList.get(i + 4).get(j).get_color())) {
									rowNum++;
								}
							}
						}
					}

					int currRow = i;
					// System.out.println("currRow : " + i);
					// System.out.println("rowNum : " + rowNum);

					for (int k = (currRow + rowNum) - 1; k > 0; k--) {
						// set new tile
						if (k - rowNum < 0) {
							// System.out.println("Random *** r / c : " + k + "
							// / " + j);
							Tile temp = new Tile(k, j);
							_tileList.get(k).set(j, temp);
							// blocks above
						} else {
							// System.out.println("Fill *** " + (k - rowNum) + "
							// / " + j + " ==>" + k + " / " + j);
							Tile temp = new Tile(_tileList.get(k - rowNum).get(j));
							_tileList.get(k).set(j, temp);
						}
					}
					_numOfMatchedTile += rowNum;
					System.out.println(rowNum+" tiles vertically to remove!");
					updateMatches();
				}
			}
		}

		int colNum = 0;
		// row match case
		for (int i = 0; i < ROW; i++) {
			for (int j = 0; j < COL - 2; j++) {
				// same col case
				if (_tileList.get(i).get(j).get_color().equals(_tileList.get(i).get(j + 1).get_color())
						&& _tileList.get(i).get(j).get_color().equals(_tileList.get(i).get(j + 2).get_color())) {
					colNum = 3;
					if (j + 3 < ROW) {
						if (_tileList.get(i).get(j).get_color().equals(_tileList.get(i).get(j + 3).get_color())) {
							colNum++;
							if (j + 4 < ROW) {
								if (_tileList.get(i).get(j).get_color()
										.equals(_tileList.get(i).get(j + 4).get_color())) {
									colNum++;
								}
							}
						}
					}

					int currRow = i;
					// System.out.println("currRow : " + currRow);
					// System.out.println("colNum : " + colNum);
					for (int k = currRow; k > 0; k--) {
						// set above tiless
						// System.out.println((k - 1) + " row to => " + k + "
						// row");
						// System.out.println("from [" + j + "] " + "to" + "[" +
						// (j + colNum) + "]");
						ArrayList<Tile> temp = new ArrayList<>(_tileList.get(k - 1).subList(j, (j + colNum)));
						for (int l = 0; l < temp.size(); l++) {
							_tileList.get(k).set(j + l, temp.get(l));
						}
					}
					// set random for row 0
					for (int l = 0; l < colNum; l++) {
						_tileList.get(0).set(j + l, new Tile(0, j + l));
					}
					_numOfMatchedTile += colNum;
					System.out.println(colNum+" tiles horizontaly to remove!");
					updateMatches();
				}
			}
		}
		return matchExists;

	}

	// set picked tile to paint and record
	public void setPiekcedTile(int r, int c) {
		_pickedTile.add(r);
		_pickedTile.add(c);
		// System.out.println("picked tile has been updated = r :" +
		// _pickedTile.get(0) + " c : " + _pickedTile.get(1));
		this._tileList.get(r).get(c).set_picked(true);
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if (_tileList.get(i).get(j).is_picked() == true) {
					// System.out.println(i + "/" + j);
					// System.out.println("_tileList.get(i).get(j).get_c() : " +
					// _tileList.get(i).get(j).get_c());
					// System.out.println("_tileList.get(i).get(j).get_r() : " +
					// _tileList.get(i).get(j).get_r());
				}
			}
		}
	}

	public void resetPickedTile() {
		System.out.println("resetPickedTile()");
		this._pickedTile.clear();
		;
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if (_tileList.get(i).get(j).is_picked() == true) {
					// System.out.println(i + "/" + j);
					_tileList.get(i).get(j).set_picked(false);
				}
			}
		}
		_tileSelected = false;
	}

	// check if the target location is adjacent
	public boolean isAdjacent(int tar_r, int tar_c) {
		boolean row_adj = false;
		boolean col_adj = false;
		// System.out.println(_pickedTile.get(0) + " / " + _pickedTile.get(1) +
		// " *******");
		// System.out.println("tar_r :" + tar_r + " / tar_c : " + tar_c);

		// System.out.println((_pickedTile.get(0) + 1 == tar_c) + " || " +
		// (_pickedTile.get(0) - 1 == tar_c));
		if (_pickedTile.get(0) + 1 == tar_r || _pickedTile.get(0) - 1 == tar_r) {
			col_adj = true;
		}
		if (_pickedTile.get(1) + 1 == tar_c || _pickedTile.get(1) - 1 == tar_c) {
			row_adj = true;
		}
		//
		// System.out.println("isAdjacent ? : " + (col_adj || row_adj));
		return col_adj || row_adj;
	}

	public void swapTiles(int r, int c) {
		// System.out.println("swapTiles");
		// System.out.println("r : " + _pickedTile.get(0) + " / c : " +
		// _pickedTile.get(1));
		// System.out.println(" TO =>");
		// System.out.println("r : " + r + " / c : " + c);
		Color temp1 = _tileList.get(r).get(c).get_color();
		Color temp2 = _tileList.get(_pickedTile.get(0)).get(_pickedTile.get(1)).get_color();

		_tileList.get(r).get(c).set_color(temp2);
		_tileList.get(_pickedTile.get(0)).get(_pickedTile.get(1)).set_color(temp1);
		_swapCounter++;
		_turn--;
		System.out.println("turn : " + _turn);

		updateMatches();
		updateScore();
	}

	private void updateScore() {
		System.out.println("updateScore()");
		System.out.println("current score : " + _score);
		System.out.println(_numOfMatchedTile);
		if (_numOfMatchedTile > 0) {
			int score = ((_numOfMatchedTile - 3) * (_numOfMatchedTile - 3)) + 3;
			_score += score;
			_numOfMatchedTile = 0;
		}
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		System.out.println("update GUI");
		checkGameOver();
		_gui.update();
	}

	private void checkGameOver() {
		// TODO Auto-generated method stub
		if (_turn == 0) {
			_isGameOver = true;
			System.out.println("GAME OVER");
		}
	}

	// all accessor methods below
	public BoardGUI get_gui() {
		return _gui;
	}

	public void set_gui(BoardGUI _gui) {
		this._gui = _gui;
	}

	public ArrayList<ArrayList<Tile>> get_tileList() {
		return _tileList;
	}

	public void set_tileList(ArrayList<ArrayList<Tile>> _tileList) {
		this._tileList = _tileList;
	}

	public int get_swapCounter() {
		return _swapCounter;
	}

	public void set_swapCounter(int _swapCounter) {
		this._swapCounter = _swapCounter;
	}

	public ArrayList<Integer> get_pickedTile() {
		return _pickedTile;
	}

	public void set_pickedTile(ArrayList<Integer> _pickedTile) {
		this._pickedTile = _pickedTile;
	}

	public boolean is_matchChecker() {
		return _matchChecker;
	}

	public void set_matchChecker(boolean _matchChecker) {
		this._matchChecker = _matchChecker;
	}

	public boolean is_tileSelected() {
		return _tileSelected;
	}

	public void set_tileSelected(boolean _tileSelected) {
		this._tileSelected = _tileSelected;
	}
}
