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
	public void setKey(String key) {
		this.key = key;
	}

	@Override
	public void onDraw(long deltaTime, Graphics g) {
		g.setColor(Color.BLUE);
		g.fillArc(x, y, r, r, 0, 360);
		count++;
		if (count % 100 == 0) {
			panel.repaint();
			// count=0;
			x += fx;
			y += fy;

			if (x < 0) {
				fx = -fx;
			}

			if (x > 477) {
				fx = -fx;
			}
			if (y < 0) {
				fy = -fy;
			}
			if (y > 216) {
				fy = -fy;
			}

			System.out.println(count + " " + x + " " + y);

		}

	}
}
