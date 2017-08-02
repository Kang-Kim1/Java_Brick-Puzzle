package cse115.lab11.controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.border.LineBorder;

import cse115.lab11.model.BoardModel;

public class ClickHandler implements ActionListener {

	private int _r;
	private int _c;
	private BoardModel _model;

	public ClickHandler(int r, int c, BoardModel model) {
		_r = r;
		_c = c;
		_model = model;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

//		System.out.println("r : " + _r);
//		System.out.println("c : " + _c);

		if (_model.is_tileSelected() == false) {
			_model.set_tileSelected(true);
			_model.setPiekcedTile(_r, _c);
			_model.update();
		} else if (_model.is_tileSelected() == true) {
			if (_model.isAdjacent(_r, _c)) {
//				System.out.println("is adjacent");
				_model.swapTiles(_r, _c);
				_model.resetPickedTile();
				_model.update();
			} else {
//				System.out.println("not adjecent");
				_model.resetPickedTile();
				_model.update();
			}
		}
	}
}
