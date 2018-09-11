import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JComponent;

public class Bullet extends JComponent {
	private final TankRotationExample tre;
	private int x;
	private int y;
	private final int r = 6;
	private int vx;
	private int vy;
	private short angle;
	private static BufferedImage img;
	private Tank t1;
	private Rectangle bounds;
	private int offset = 50;
	private int shoot_x = 0;
	private int shoot_y = 0;
	private int offsetx = 0;
	private int offsety = 0;
	private String player;
	
	public Bullet(TankRotationExample tre, Tank t, BufferedImage img, int x, int y, int vx, int vy, short angle) {
		this.tre = tre;
		this.t1 = t;
		this.player = t.getPlayer();
		Bullet.img = img;
		this.x = x; 
		this.y = y + 50;
		this.vx = vx;
		this.vy = vy;
		this.angle = angle;
		this.bounds = new Rectangle(x, y, Bullet.img.getWidth(), Bullet.img.getHeight());
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public void setVx(int vx) {
		this.vx = vx;
	}
	
	public void setVy(int vy) {
		this.vy = vy;
	}
	
	public void setAngle(short angle) {
		this.angle = angle;
	}
	
	public void setImage(BufferedImage img) {
		Bullet.img = img;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public void update() {
		// TODO Auto-generated method stub
		this.moveForwards();
		//this.repaint();
		if(this.CollidesWithTank(this.tre.t1)) {
			if(this.tre.t1.getPlayer() != this.player) {
				System.out.println(" Tank 1 Hit");
				this.tre.t1.setHealth(this.tre.t1.getHealth() - 16);
				this.tre.ammo.remove(this);
			}
		}
		
		if(this.CollidesWithTank(this.tre.t2)) {
			if(this.tre.t2.getPlayer() != this.player) {
				System.out.println("Tank 2 Hit");
				this.tre.t2.setHealth(this.tre.t2.getHealth() - 16 );
				this.tre.ammo.remove(this);
			}
		}
		
	}
	
	private void moveForwards() {
        vx = (int) Math.round(r * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(r * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
        //checkBorder();
        this.bounds.setLocation(x,y);
    }
	
	public void paintComponent( Graphics g) {
		super.paintComponent(g);
		
		AffineTransform rotation = AffineTransform.getTranslateInstance(this.x, this.y);
        rotation.rotate(Math.toRadians(angle),  this.t1.getX() +(img.getWidth() / 2), this.t1.getY() + (img.getHeight() / 2));
        Graphics2D graphic2D = (Graphics2D) g;
        graphic2D.drawImage(img, rotation, null);
        
        //System.out.println(this.toString());
	}
	
	public Rectangle getBounds() {
		return this.bounds;
	}
	
	public boolean checkBorder() {
		if ( this.x < 0 || this.x >= 740 || this.y < 0 || this.y >= 720) {
			return true;
		} else {
			return false;
		}
	}
	
	public String toSring() {
		return "Bullet: x=" + x + ", y=" + y + ", angle=" + angle;
	}
	
	public void drawImage(Graphics g) {
		// TODO Auto-generated method stub
		
		AffineTransform rotation = AffineTransform.getTranslateInstance(x+offset, y);
        rotation.rotate(Math.toRadians(angle), img.getWidth() / 2, img.getHeight() / 2);
        Graphics2D graphic2D = (Graphics2D) g;
        graphic2D.drawImage(img, rotation, null);
        
        //System.out.println(this.toString());
	}
	
	public boolean CollidesWithTank(Tank t1) {
		if(this.bounds.intersects(t1.getBounds())) {
			return true;
		} else {
			return false;
		}
	}
}
