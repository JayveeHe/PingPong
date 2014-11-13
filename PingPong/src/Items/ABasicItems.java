package Items;

import java.awt.Graphics;

public abstract class ABasicItems {
	int x;
	int y;
	String key;

	public abstract void onDraw(long deltaTime, Graphics g);

	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
		System.out.println(getClass().getName() + y);
	}

	public abstract void setKey(String key);

	public String getKey() {
		return key;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}
