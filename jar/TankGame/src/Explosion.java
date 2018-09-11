import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

@SuppressWarnings("serial")
public class Explosion extends JComponent{
	private final TankRotationExample tre;
	private static BufferedImage img;
	private int x = 0;
	private int y = 0;
	
	public Explosion(TankRotationExample tre, BufferedImage img) {
		this.tre = tre;
		Explosion.img = img;
	}
	
	public void setXY( int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void drawImage(Graphics g) {
		g.drawImage(img, this.x, this.y, Explosion.img.getWidth(), Explosion.img.getHeight(), null);
	}

	public TankRotationExample getTRE() {
		return tre;
	}
}
