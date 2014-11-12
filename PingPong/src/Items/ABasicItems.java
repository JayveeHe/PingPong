package Items;

import java.awt.Graphics;

public abstract class ABasicItems {
	int x;
	int y;

	public abstract void onDraw(Graphics g);

	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}
