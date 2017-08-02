package cse115.lab11.model;

import java.awt.Color;
import java.util.Random;

public class Tile {
	private Color _color;

	private boolean _picked;
	private int _r;
	private int _c;

	public Tile(int r, int c) {
		setColor(); // set itself a random color
		_r = r;
		_c = c;
		_picked = false;
	}
	
	public Tile(Tile t ){
		this._color = t._color;
		this._picked = t._picked;
		this._r = t._r;
		this._c = t._c;
	}

	// set random color when its initiated
	public void setColor() {

		Random r = new Random();

		int r_int = r.nextInt(6) + 0; // 0,1,2,3,4,5

		switch (r_int) {
		
		
		case 0:	
			//assume image 0
			this._color = Color.BLUE;
			break;
		case 1:
			//assume image 1
			this._color = Color.ORANGE;
			break;
		case 2:
			
			//assume image 2
			this._color = Color.YELLOW;
			break;
		case 3:
			//assume image 3
			this._color = Color.PINK;
			break;
		case 4:
			//assume image 4
			this._color = Color.GREEN;
			break;
		case 5:
			//assume image 5
			this._color = Color.CYAN;
			break;
		}
	}

	// get its color to paint a button

	public Color get_color() {
		return _color;
	}

	public void set_color(Color _color) {
		this._color = _color;
	}

	public boolean is_picked() {
		return _picked;
	}

	public void set_picked(boolean _picked) {
		this._picked = _picked;
	}

	public int get_r() {
		return _r;
	}

	public void set_r(int _r) {
		this._r = _r;
	}

	public int get_c() {
		return _c;
	}

	public void set_c(int _c) {
		this._c = _c;
	}
}
