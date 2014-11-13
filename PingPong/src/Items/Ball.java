package Items;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Ball extends ABasicItems {
	int r;
	 double fx = 5, fy = 5;
	int count = 0;
	JPanel panel;

	public Ball(JPanel panel, String key, int x, int y, int r) {
		setKey(key);
		this.x = x;
		this.y = y;
		this.r = r;
		this.panel = panel;
		System.out.println(panel.size().height + " " + panel.size().width);
	}

	@Override
	public void onDraw(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(Color.BLUE);
		g.fillArc(x, y, r, r, 0, 360);
		// g.fillOval(x, y, 5, 5);
		count++;
		if (count % 100 == 0) {
			panel.repaint();
			if (x < 0) {
				fx = -fx;
				x += fx;
				y += fy;
			} else if (x > 477) {
				fx = -fx;
				x += fx;
				y += fy;
			} else if (y < 0) {
				fy = -fy;
				x += fx;
				y += fy;
			} else if (y > 216) {
				fy = -fy;
				x += fx;
				y += fy;
			} else {
				x += fx;
				y += fy;
				System.out.println(x + " " + y);
			}
			
		}

	}

	@Override
	public void setKey(String key) {
		// TODO Auto-generated method stub
		this.key = key;
	}

	// @Override
	// public void run() {
	// // TODO Auto-generated method stub
	// while (true) {
	// x+=fx;
	// y+=fy;
	// }
	// }

}
