package Items;

import java.awt.Graphics;

public class Board {
	Graphics g;
	int x;
	int y;

	public Board(int x, int y) {
		// g.draw3DRect(10, 10, x, y, true);
		this.x = x;
		this.y = y;
	}

	public void onDraw(Graphics g) {
		g.draw3DRect(10, 10, x, y, true);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}
