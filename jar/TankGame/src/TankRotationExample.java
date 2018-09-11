/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author anthony-pc
 */
public class TankRotationExample extends JPanel {

    private final GameEventObservable geobv;
    public static final int world_width  = 1920;
    public static final int world_height = 1440;
    public static final int screen_width = 1280;
    public static final int screen_height = 960;
    private int[][] map_level;
    
    BufferedImage world;
    Graphics2D buffer;
    BufferedImage bimg;
    BufferedImage bulletImg;
	BufferedImage floorImg;
	BufferedImage brkWallImg;
	BufferedImage wallImg;
	BufferedImage powerImg;
	BufferedImage explosionImg;
    JFrame frame;
    Tank t1;
    Tank t2;
    ArrayList<Bullet> ammo;
    Bullet bullet;
    FloorTile floor_T;
    MapLevel level;
    BreakableWall brkWallObj;
    UnBreakableWall wallObj;
    ArrayList<BreakableWall> brkWallList;
    ArrayList<UnBreakableWall> wallList;
    LifeStock lifePanel;
    PowerUp powerUp;
    ArrayList<PowerUp> powerUpList;
    Explosion explosion;
    
    /*
        BufferedImage world;
        Graphics2D buffer;
        BufferedImage bimg;
        JFrame frame;
        Tank t1;
        Tank t2;
        ArrayList<Drawable> drawables;
        ArrayList<Bullets> bulletCount;

        NOTE: any line of "tre" is the same as TankRotationExample
    */

    public static void main(String[] args) {
        Thread x;
        TankRotationExample trex = new TankRotationExample();
        trex.init();

        try {
            while (true) {
                trex.geobv.setChanged();
                trex.geobv.notifyObservers();
                trex.repaint();
                Thread.sleep(1000/144);
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(TankRotationExample.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public TankRotationExample() {
        this.geobv = new GameEventObservable();
    }

    private void init() {
    	powerUpList = new ArrayList<PowerUp>();
    	brkWallList = new ArrayList<BreakableWall>();
    	wallList = new ArrayList<UnBreakableWall>();
    	ammo = new ArrayList<Bullet>();
    	level = new MapLevel();
    	map_level = MapLevel.getMap_level().clone();
        this.frame = new JFrame("Tank Rotation");
        t1 = new Tank(this, "Player 1", 100, 100, 0, 0, (short)0);
        t2 = new Tank(this, "Player 2", 1700, 1300, 0, 0, (short)180);
        floor_T = new FloorTile();
        try {
        	world = new BufferedImage(world_width, world_height, BufferedImage.TYPE_3BYTE_BGR); //ImageIO.read(new File("./Images/Background.bmp"));
            BufferedImage i = ImageIO.read(new File("./Resources/Tank1.jpg"));
            bulletImg = ImageIO.read(new File("./Resources/bigBullet.png"));
            floorImg = ImageIO.read(new File("./Resources/Background.bmp"));
            brkWallImg = ImageIO.read(new File("./Resources/Wall1.gif"));
            wallImg = ImageIO.read(new File("./Resources/Wall2.gif"));
            powerImg = ImageIO.read(new File("./Resources/Shield2.gif"));
            explosionImg = ImageIO.read(new File("./Resources/Explosion_large.gif"));
            t1.setImg(i);
            t2.setImg(i);
            floor_T.setImg(floorImg);
            
            for ( int x = 0; x < map_level.length; x++ ) {
            	for( int y = 0; y < map_level[x].length; y++ ) {
            		//System.out.println("x: " + x + " y: " + y);
            		if(map_level[x][y] == 2) {
            			brkWallList.add(new BreakableWall(this, brkWallImg, x * this.brkWallImg.getWidth(), y * this.brkWallImg.getHeight()));
            		} else if (map_level[x][y] == 3) {
            			wallList.add(new UnBreakableWall(this, wallImg, x * this.wallImg.getWidth(), y * this.wallImg.getHeight()));
            		} else if (map_level[x][y] == 4) { // Note Scale PowerUpImg to 32 * 32
            			powerUpList.add(new PowerUp(this, powerImg, x * (this.powerImg.getWidth() / 2), y * (this.powerImg.getHeight()/2) ));
            		}
            	}
            }
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        TankControl tc1 = new TankControl(t2, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_ENTER);
        TankControl tc2 = new TankControl(t1, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE);
        lifePanel = new LifeStock(this);
        explosion = new Explosion(this, explosionImg);
        
        this.frame.add(this);

        this.frame.addKeyListener(tc1);
        this.frame.addKeyListener(tc2);

        this.geobv.addObserver(t1);
        this.geobv.addObserver(t2);

        this.frame.setSize(TankRotationExample.screen_width, TankRotationExample.screen_height);
        this.frame.setResizable(false);
        this.frame.setLocationRelativeTo(null);
        
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setVisible(true);
    }

//    @Override
//    public void run() {
//        while (true) {
//            try {
//                this.geobv.notifyObservers();
//                Thread.sleep(1000 / 144);
//            } catch (InterruptedException ex) {
//                System.out.println(ex.getMessage());
//            }
//        }
//    }
    
    public void loadBullet(Tank t, int x, int y, int vx, int vy, short angle) {
    	this.ammo.add(new Bullet(this, t, bulletImg, x, y, vx, vy, angle));
    	//System.out.println("ammo: " + this.ammo.size());
    }
    
    //////////////////////
    // Draw entire world!!!!!!!
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        buffer = world.createGraphics();
        super.paintComponent(g2);
        //h: 240 -> 8
        //w: 320 -> 6
        for(int i = 0; i <= ((int) (TankRotationExample.world_height/ this.floorImg.getHeight())); i++) {
        	for(int j = 0; j <= ((int) (TankRotationExample.world_width/ this.floorImg.getWidth())); j++) {
        		this.floor_T.setXY((j * this.floorImg.getWidth()), (i * this.floorImg.getHeight()));
        		this.floor_T.drawImage(buffer);
        	}
        }

        this.t1.drawImage(buffer);
        this.t2.drawImage(buffer);
        
        /////////////////////
        /* Load bullets */
        for(int i = 0; i < this.ammo.size(); i++) {
        	//System.out.println("***Drawing Bullet***");
        	this.bullet = this.ammo.get(i);
        	/*if(bullet.checkBorder()) { // NEED TO UPDATE THIS
        		this.ammo.remove(i);
        		System.out.println("Border Collison!");
        	}
        	else if(bullet.CollidesWithTank(this.t1)){
        		this.ammo.remove(i);
        		//EXPLOSION!
        		//this.explosion.setXY(this.t1.getX(), this.t1.getY());
        		//this.explosion.drawImage(g2);
        		System.out.println("Bullet Hit Tank1 !");
        	}
        	else if(bullet.CollidesWithTank(this.t2)){
        		this.ammo.remove(i);
        		//EXPLOSION!
        		//this.explosion.setXY(this.t2.getX(), this.t2.getY());
        		//this.explosion.drawImage(g2);
        		System.out.println("Bullet Hit Tank2 !");
        	}
        	else {
        		this.bullet.update();
        		this.bullet.drawImage(buffer);
        	}*/
        	this.bullet.update();
    		this.bullet.drawImage(buffer);
        }
        
        //////////////
	    // draw BreakableWalls
	    for(int z = 0; z < this.brkWallList.size(); z++) {
	    	this.brkWallObj = this.brkWallList.get(z);
	    	this.brkWallList.get(z).drawImage(buffer);
	    	this.brkWallObj.update();
	    }
	    
	    ////////////////
	    // draw Regular Walls
	    for (int y = 0; y < this.wallList.size(); y++) {
	    	this.wallObj = this.wallList.get(y);
	    	this.wallList.get(y).drawImage(buffer);
	    	this.wallObj.update();
	    }
	    
	    ///////////////
	    // draw PowerUps
	    for ( int x = 0; x < this.powerUpList.size(); x++) {
	    	this.powerUp = this.powerUpList.get(x);
	    	this.powerUpList.get(x).drawImage(buffer);
	    	this.powerUp.update();
	    }
	    
        /////////////////////////////
        // Draw Split Screen
        BufferedImage lefthalf = world.getSubimage(this.t1.getLeft_x(), this.t1.getLeft_y(), screen_width/2,screen_height);
        //BufferedImage righthalf = world.getSubimage(this.t2.getScreen_x(), this.t2.getScreen_y, );
        g2.drawImage(lefthalf, 0, 0, null);
        BufferedImage righthalf = world.getSubimage(this.t2.getRight_x(), this.t2.getRight_y(), screen_width/2 , screen_height);
        g2.drawImage(righthalf, screen_width /2 , 0, null);
        //g2.drawImage(righthalf, TRE.Screen_width /2 , 0, null);
        
        //////////////////////
        // Draw MiniMap
        //BufferedImage miniMap = world.getSubimage(0, 0, TRE.WORLD_WIDTH, TRE.WORLD_HEIGHT);
		/*
        BufferedImage miniMap = world.getSubimage(0, 0, world_width, world_height);
		AffineTransform tx = new AffineTransform();
		tx.scale(0.3,0.3);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
		miniMap = op.filter(miniMap, null);
		g2.drawImage(miniMap, 340, 490, null);
		*/
        lifePanel.drawImage(g2);
        
        BufferedImage miniMap = world.getSubimage(0, 0, world_width, world_height);
        g2.scale(0.2, 0.2);
        g2.drawImage(miniMap, 2200, 3200, null);
	    /*
	    BufferedImage lefthalf = world.getSubimage(x, y, w, h);
	    BufferedImage righthalf = world.getSubimage(x, y, w, h);
	    g2.drawImage(lefthalf, 0, 0, null);
	    g2.drawImage(righthalf, TankRotationExample.screen_width / 2, 0, null);
	    BufferedImage miniMap = world.getSubimage(0, 0, TankRotationExample.world_width, TankRotationExample.world_height);
	    g2.drawImage(miniMap, 575, 600, null);
	    */
        
	    /*
		//////////////////////
		//LIVES Tank2
        //g2.fillOval(x,y,width, height);
        g2.setColor(Color.BLUE);
        g2.fillOval(TankRotationExample.screen_width / 2 + 50, 80, 20, 20);
        g2.drawOval(TankRotationExample.screen_width / 2 + 50, 80, 20, 20);
        g2.fillOval(TankRotationExample.screen_width / 2 + 80, 80, 20, 20);
        g2.drawOval(TankRotationExample.screen_width / 2 + 80, 80, 20, 20);
        g2.fillOval(TankRotationExample.screen_width / 2 + 110, 80, 20, 20);
        g2.drawOval(TankRotationExample.screen_width / 2 + 110, 80, 20, 20);
        g2.setColor(Color.GREEN);
        g2.fillRect(TankRotationExample.screen_width / 2 + 50, 35, 80, 35);
        g2.drawRect(TankRotationExample.screen_width / 2 + 50, 35, 80, 35);
        
		//////////////////////
		//LIVES Tank1
        g2.setColor(Color.RED);
        g2.fillOval(50, 80, 20, 20);
		g2.drawOval(50, 80, 20, 20);
		g2.fillOval(80, 80, 20, 20);
		g2.drawOval(80, 80, 20, 20);
		g2.fillOval(110, 80, 20, 20);
		g2.drawOval(110, 80, 20, 20);
        g2.setColor(Color.GREEN);
        g2.fillRect(50, 35, 80, 35);
        g2.drawRect(50, 35, 80, 35);
		*/
    }

}
