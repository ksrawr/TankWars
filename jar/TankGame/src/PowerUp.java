import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

public class PowerUp extends JComponent {
	
	private final TankRotationExample tre;
	private static BufferedImage img;
	private int x;
	private int y;
	private Rectangle bounds;
	private int time = 20;
	
	public PowerUp(TankRotationExample tre, BufferedImage img, int x, int y) {
		this.tre = tre;
		PowerUp.img = img;
		this.x = x;
		this.y = y;
		this.bounds = new Rectangle(x, y, PowerUp.img.getWidth(), PowerUp.img.getHeight());
	}
	
	public Rectangle getBounds() {
		return this.bounds;
	}
	
	public void update() {
		this.CollidesWithTank();
	}
	
	public void CollidesWithTank() {
			if(this.bounds.intersects(this.tre.t1.getBounds())) {
				this.tre.powerUpList.remove(this.tre.powerUp);
				this.tre.t1.setLives(this.tre.t1.getLives() + 1);
				System.out.println("Tank1 picked up PowerUp");
			}
			if(this.bounds.intersects(this.tre.t2.getBounds())) {
				this.tre.powerUpList.remove(this.tre.powerUp);
				this.tre.t2.setLives(this.tre.t2.getLives() + 1);
				System.out.println("Tank2 picked up PowerUp");
			}
	}
	
	public void drawImage(Graphics g) {
		g.drawImage(img, this.x, this.y, PowerUp.img.getWidth(), PowerUp.img.getHeight(), null);
	}
	public void UpdateTime(int time) {
		this.time -= time;
	}
}
