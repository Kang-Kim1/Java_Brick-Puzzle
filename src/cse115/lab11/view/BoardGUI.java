package cse115.lab11.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import cse115.lab11.controller.ClickHandler;
import cse115.lab11.model.BoardModel;
import cse115.lab11.model.Tile;
import cse115.lab11.observer.Observer;

public class BoardGUI implements Observer {

	private BoardModel _model;
	private JFrame _board;
	private JPanel _panel;
	private JPanel _infoPanel;

	public BoardGUI(BoardModel model) {

		this._model = model;
		_board = new JFrame("Bowen Deng's Lab 10");
		_board.setLayout(new GridLayout(1, 2));
		_panel = new JPanel();
		_infoPanel = new JPanel();
		_infoPanel.setLayout(new GridLayout(2, 1));

		update();
	}

	@Override
	public void update() {
		_panel.removeAll();
		// TODO Auto-generated method stub

		// panel design
		_panel.setLayout(new GridLayout(BoardModel.ROW, BoardModel.COL));
		_panel.setPreferredSize(new Dimension(300, 300));

		for (int i = 0; i < BoardModel.COL; i++) {
			for (int j = 0; j < BoardModel.ROW; j++) {

				Tile tempTile = _model.get_tileList().get(i).get(j);
				// Tile tempTile = _model._tileList.get(i).get(j);
				/*
				 * 0BLUE 1ORANGE 2YELLOW 3PINK 4GREEN 5CYAN
				 */

				JButton b = new JButton();
				// btn design
				Image img;

				if (tempTile.get_color().equals(Color.BLUE)) {
					// b = JButton
					b.setIcon(new ImageIcon("images/Tile-0.png"));
				} else if (tempTile.get_color().equals(Color.ORANGE)) {
					b.setIcon(new ImageIcon("images/Tile-1.png"));
				} else if (tempTile.get_color().equals(Color.YELLOW)) {
					b.setIcon(new ImageIcon("images/Tile-2.png"));
				} else if (tempTile.get_color().equals(Color.PINK)) {
					b.setIcon(new ImageIcon("images/Tile-3.png"));
				} else if (tempTile.get_color().equals(Color.GREEN)) {
					b.setIcon(new ImageIcon("images/Tile-4.png"));
				} else {
					b.setIcon(new ImageIcon("images/Tile-5.png"));
				}
				// b.setBackground(tempTile.get_color());
				// b.setPreferredSize(new Dimension(50, 50));

				b.setName(i + " " + j);
				b.addActionListener(new ClickHandler(i, j, _model));
				// store into AL

				if (tempTile.is_picked() == true) {
					b.setBorder(new LineBorder(Color.RED, 12));
				}

				// store into panel
				_panel.add(b);
			}
		}

		_board.add(_panel);
		_infoPanel.removeAll();
		
		JLabel textLabel = new JLabel();
		if (_model._isGameOver) {
			textLabel.setHorizontalAlignment(JLabel.CENTER);
			textLabel.setVerticalAlignment(JLabel.CENTER);
			textLabel.setText("!!!! Game Over !!!!");
			_infoPanel.setLayout(new GridLayout(1, 0));
			_infoPanel.add(textLabel);
			_board.add(_infoPanel);
		} else {
			JLabel scoreLabel = new JLabel();
			JLabel turnLabel = new JLabel();
			scoreLabel.setSize(new Dimension(300, 300));
			// turn / score
			String scoreInfo = "Current Score : " + _model._score;
			String turnInfo = "Remaining Turn : " + _model._turn;
			scoreLabel.setText(scoreInfo);
			turnLabel.setText(turnInfo);
			_infoPanel.add(scoreLabel);
			_infoPanel.add(turnLabel);
			_board.add(_infoPanel);
		}
		_board.setVisible(true);
		_board.pack();
		// closer
		_board.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		_model.set_matchChecker(_model.checkMatches());
		// _model._matchChecker = _model.checkMatches();
	}

}
