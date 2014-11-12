package Items;

import java.awt.Color;
import java.awt.Graphics;

public class Board extends ABasicItems {

	public Board(String key, int x, int y) {
		this.key = key;
		this.x = x;
		this.y = y;
	}

	@Override
	public void onDraw(Graphics g) {
		g.setColor(Color.BLACK);
		g.fill3DRect(getX(), getY(), 10, 100, true);
	}

	@Override
	public void setKey(String key) {
		this.key = key;

	}

}
