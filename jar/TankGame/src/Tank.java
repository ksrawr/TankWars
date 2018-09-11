import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 *
 * @author anthony-pc
 */
public class Tank extends JComponent implements Observer {
	
	private final TankRotationExample tre;
    private int x;
    private int y;
    private final int r = 6;
    private int vx;
    private int vy;
    private int shoot_x = 0; // where to fire Bullet
    private int shoot_y = 0; // where to fire Bullet
    private short angle;
    private static BufferedImage img;
    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;
    private boolean FirePressed;
    private int health = 80;
    private int lives = 3;
    private int left_x = 0;
    private int left_y = 0;
    private int right_x = 0;
    private int right_y = 0;
    private String player;
    //private int fire;
    //public ArrayList<Bullet> tankAmmo;
    private Rectangle bounds;

    public Tank(TankRotationExample tre, String player, int x, int y, int vx, int vy, short angle) {
        this.tre = tre;
        this.player = player;
    	this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.angle = angle;
    }
    
    public String getPlayer() {
    	return this.player;
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
    
    public BufferedImage getImg() {
    	return Tank.img;
    }

    public void setImg(BufferedImage img) {
        Tank.img = img;
        this.setBounds();
    }
    
    public void setBounds() {
    	this.bounds = new Rectangle(x, y, Tank.img.getWidth(), Tank.img.getWidth());
    }
    
    public String printBounds() {
    	return "Bounds: " + this.bounds;
    }
    
    public int getX() {
    	return this.x;
    }
    
    public int getY() {
    	return this.y;
    }
    
    public int getVX() {
    	return this.vx;
    }
    
    public int getVY() {
    	return this.vy;
    }
    
    public int getTileX() {
    	return Tank.img.getWidth();
    }
    
    public int getTileY() {
    	return Tank.img.getHeight();
    }
    
    public String printImgXY() {
    	return "x: " + Tank.img.getWidth() + " y: " + Tank.img.getHeight();
    }
    
    public int getHealth() {
    	return this.health;
    }
    
    public void setHealth(int h) {
    	this.health = h;
    }
    
    public int getLives() {
    	return this.lives;
    }
    
    public void setLives(int l) {
    	this.lives = l;
    }
    
    public int getShootX() {
    	return this.shoot_x;
    }
    
    public int getShootY() {
    	return this.shoot_y;
    }
        
    public void toggleUpPressed() {
        this.UpPressed = true;
    }

    public void toggleDownPressed() {
        this.DownPressed = true;
    }

    public void toggleRightPressed() {
        this.RightPressed = true;
    }

    public void toggleLeftPressed() {
        this.LeftPressed = true;
    }
    
    public void toggleFirePressed() {
    	this.FirePressed = true;
    }

    public void unToggleUpPressed() {
        this.UpPressed = false;
    }

    public void unToggleDownPressed() {
        this.DownPressed = false;
    }

    public void unToggleRightPressed() {
        this.RightPressed = false;
    }

    public void unToggleLeftPressed() {
        this.LeftPressed = false;
    }
    
    public void unToggleFirePressed() {
    	this.FirePressed = false;
    }
    
    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), img.getWidth() / 2, img.getHeight() / 2);
        Graphics2D graphic2D = (Graphics2D) g;
        graphic2D.drawImage(img, rotation, null);
        
        //System.out.println(this.toString());
        int cImgX = img.getWidth() / 2; 
        int cImgY = img.getHeight() / 2;
        int cImgVX = (int) Math.round(43 * Math.cos(Math.toRadians(angle)));
        int cImgVY = (int) Math.round(43 * Math.sin(Math.toRadians(angle))); 
        
        this.shoot_x = cImgX + cImgVX;
        this.shoot_y = cImgY + cImgVY;
        /*
        AffineTransform rotationShoot = AffineTransform.getTranslateInstance(this.shoot_x, this.shoot_y);
        rotationShoot.rotate(Math.toRadians(angle), img.getWidth() / 2, img.getHeight() /2);
        //Graphics2D gShootD = (Graphics2D)g;
        g.setColor(Color.RED);
        g.fillOval(shoot_x, shoot_y, 20, 20);
        g.drawOval(shoot_x, shoot_y, 20, 20);
        */
    }

    public void update(Observable o, Object o1) {
        if (this.UpPressed) {
            this.moveForwards();
        }
        if (this.DownPressed) {
            this.moveBackwards();
        }

        if (this.LeftPressed) {
            this.rotateLeft();
        }
        if (this.RightPressed) {
            this.rotateRight();
        }
        if (this.FirePressed) {
        	this.addBullet();
        }
        this.Center();
        this.repaint();
    }

    private void rotateLeft() {
        this.angle -= 3;
    }

    private void rotateRight() {
        this.angle += 3;
    }

    private void moveBackwards() {
        vx = (int) Math.round(r * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(r * Math.sin(Math.toRadians(angle)));
        x -= vx;
        y -= vy;
        checkBorder();
        this.bounds.setLocation(x,y);
    }

    private void moveForwards() {
        vx = (int) Math.round(r * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(r * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
        checkBorder();
        this.bounds.setLocation(x,y);
    }

    private void checkBorder() {
        if (x < 0) {
            x = 0;
        }
        if (x >= 1910) {
            x = 1910;
        }
        if (y < 0) {
            y = 0;
        }
        if (y >= 1350) {
            y = 1350;
        }
    }

    @Override
    public String toString() {
        return "x=" + x + ", y=" + y + ", angle=" + angle;
    }
    
    public void addBullet() {
    	//pass in x and y of tank to determine where bullet gets drawn
    	this.tre.loadBullet(this, this.x, this.y, this.vx, this.vy, this.angle);
    	//this.tankAmmo.add(new Bullet(this.tre, this, TankRotationExample.bulletImg, this.x, this.y, this.vx, this.vy, this.angle));
    	//System.out.println("Bullet added");
    }
    
    public Rectangle getBounds() {
    	return this.bounds;
    }
    
	public void drawImage(Graphics g) {
		// TODO Auto-generated method stub
		
		AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), img.getWidth() / 2, img.getHeight() / 2);
        Graphics2D graphic2D = (Graphics2D) g;
        graphic2D.drawImage(img, rotation, null);
        
        int cImgX = img.getWidth() / 2; 
        int cImgY = img.getHeight() / 2;
        int cImgVX = (int) Math.round(43 * Math.cos(Math.toRadians(angle)));
        int cImgVY = (int) Math.round(43 * Math.sin(Math.toRadians(angle))); 
        
        this.shoot_x = cImgX + cImgVX;
        this.shoot_y = cImgY + cImgVY;
        /*
        AffineTransform rotationShoot = AffineTransform.getTranslateInstance(this.shoot_x, this.shoot_y);
        rotationShoot.rotate(Math.toRadians(angle), img.getWidth() / 2, img.getHeight() /2);
        //Graphics2D gShootD = (Graphics2D)g;
        g.setColor(Color.RED);
        g.fillOval(shoot_x, shoot_y, 20, 20);
        g.drawOval(shoot_x, shoot_y, 20, 20);
        */
	}
	
	public void Center() {
		/*
		 * world_width = 1920; screen_width = 1280;
		 * world_height = 1280; screen_height = 960;
		 */
		
		// Update Left side
		if(this.tre.t1.getX() - TankRotationExample.screen_width / 4 > 0 ) {
			left_x = this.tre.t1.getX() - TankRotationExample.screen_width / 4;
		} else {
			left_x = 0;
		}
		if(this.tre.t1.getY() - TankRotationExample.screen_height > 0 ) {
			left_y = this.tre.t1.getY() - (TankRotationExample.world_height - TankRotationExample.screen_height);
		} else {
			left_y = 0;
		}
		if( left_y > TankRotationExample.world_height - TankRotationExample.screen_height ) {
			left_y = TankRotationExample.world_height - TankRotationExample.screen_height;
		}
		if( left_x > TankRotationExample.world_width - TankRotationExample.screen_width / 2 ) {
			left_x = TankRotationExample.world_width - TankRotationExample.screen_width / 2;
		}	
		
		// Update RightSide
		if(this.tre.t2.getX() - TankRotationExample.screen_width / 4 > 0 ) {
			right_x = this.tre.t2.getX() - TankRotationExample.screen_width / 4;
		} else {
			right_x = 0;
		}
		if(this.tre.t2.getY() - TankRotationExample.screen_height > 0 ) {
			right_y = this.tre.t2.getY() - (TankRotationExample.world_height - TankRotationExample.screen_height);
		} else {
			right_y = 0;
		}
		if( right_y > TankRotationExample.world_height - TankRotationExample.screen_height ) {
			right_y = TankRotationExample.world_height - TankRotationExample.screen_height;
		}
		if( right_x > TankRotationExample.world_width - TankRotationExample.screen_width / 2 ) {
			right_x = TankRotationExample.world_width - TankRotationExample.screen_width / 2;
		}	
		
	
	}
	
	public int getRight_x() {
		return this.right_x;
	}
	
	public int getRight_y() {
		return this.right_y;
	}
	
	public int getLeft_x() {
		return this.left_x; 
	}
	
	public int getLeft_y() {
		return this.left_y;
	}
}
