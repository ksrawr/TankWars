import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

public class UnBreakableWall extends JComponent {
	
	private final TankRotationExample tre;
	private static BufferedImage img;
	private int x;
	private int y;
	private Rectangle bounds;
	
	public UnBreakableWall(TankRotationExample tre,BufferedImage img, int x, int y ) {
		this.tre = tre;
		UnBreakableWall.img = img;
		this.x = x;
		this.y = y;
		this.bounds = new Rectangle(this.x, this.y, UnBreakableWall.img.getWidth(), UnBreakableWall.img.getHeight() );
	}
	
	public Rectangle getBounds() {
		return this.bounds;
	}

	public void drawImage(Graphics g) {
		//this.printXY();
		g.drawImage(img, this.x, this.y, UnBreakableWall.img.getWidth(), UnBreakableWall.img.getHeight(), null);
	}
	
	public void update() {
		this.CollidesWithBullet();
		this.CollidesWithTank1();
		this.CollidesWithTank2();
		this.CollidesWithTank1();
		this.CollidesWithTank2();
		this.CollidesWithTank1();
		this.CollidesWithTank2();
	}
	
	public void CollidesWithTank1() {
		if(this.bounds.intersects(this.tre.t1.getBounds())) {
			//this.printBounds();
			//System.out.println("Tank 1 " + this.tre.t1.printBounds());
			if(this.tre.t1.getX() > this.x) {
				//System.out.println("x:"+this.tre.t1.getX());
				this.tre.t1.setX(this.tre.t1.getX() + 3);
				//System.out.println("new x: " + (this.tre.t1.getX()));
				System.out.println("Tank 1 Collision");
				//System.out.println("Tank 1 " + this.tre.t1.printBounds());
				this.tre.t1.getBounds().setLocation(this.tre.t1.getX(), this.tre.t1.getY());
			} 
			else {
				//System.out.println("x:"+this.tre.t1.getX());
				this.tre.t1.setX(this.tre.t1.getX() - 3);
				///System.out.println("new x: " + (this.tre.t1.getX()));
				System.out.println("Tank 1 Collision");
				//System.out.println("Tank 1 " + this.tre.t1.printBounds());
				this.tre.t1.getBounds().setLocation(this.tre.t1.getX(), this.tre.t1.getY());
			}
			if(this.tre.t1.getY() > this.y) {
				this.tre.t1.setY(this.tre.t1.getY() + 3);
				System.out.println("Tank 1 Collision");
				this.tre.t1.getBounds().setLocation(this.tre.t1.getX(), this.tre.t1.getY());
			} 
			else {
				this.tre.t1.setY(this.tre.t1.getY() - 3);
				System.out.println("Tank 1 Collision");
				this.tre.t1.getBounds().setLocation(this.tre.t1.getX(), this.tre.t1.getY());
			}
		} 
	}
	
	public void CollidesWithTank2() {
		if(this.bounds.intersects(this.tre.t2.getBounds())) {
			//this.printBounds();
			//System.out.println("Tank 2 " + this.tre.t2.printBounds());
			if(this.tre.t2.getX() > this.x) {
				//System.out.println("x:"+this.tre.t2.getX());
				this.tre.t2.setX(this.tre.t2.getX() + 3);
				//System.out.println("new x: " + (this.tre.t2.getX()));
				System.out.println("Tank 2 Collision");
				//System.out.println("Tank 2 " + this.tre.t2.printBounds());
				this.tre.t2.getBounds().setLocation(this.tre.t2.getX(), this.tre.t2.getY());
			} 
			else {
				//System.out.println("x:"+this.tre.t2.getX());
				this.tre.t2.setX(this.tre.t2.getX() - 3);
				//System.out.println("new x: " + (this.tre.t2.getX()));
				System.out.println("Tank 2 Collision");
				//System.out.println("Tank 2 " + this.tre.t2.printBounds());
				this.tre.t2.getBounds().setLocation(this.tre.t2.getX(), this.tre.t2.getY());
			}
			if(this.tre.t2.getY() > this.y) {
				this.tre.t2.setY(this.tre.t2.getY() + 3);
				//System.out.println("Tank 2 Collision");
				this.tre.t2.getBounds().setLocation(this.tre.t2.getX(), this.tre.t2.getY());
			} 
			else {
				this.tre.t2.setY(this.tre.t2.getY() - 3);
				System.out.println("Tank 2 Collision");
				this.tre.t2.getBounds().setLocation(this.tre.t2.getX(), this.tre.t2.getY());
			}
		} 
	}
	
	public void CollidesWithBullet() {
		if (this.tre.bullet != null) {
			if (this.tre.bullet.getBounds().intersects(this.bounds)) {
				this.tre.ammo.remove(this.tre.bullet);
				//System.out.println("Bullet removed");
			}
		}
	}
}
