import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JComponent;

@SuppressWarnings("serial")
public class LifeStock extends JComponent {
	private final TankRotationExample tre;
	
	public LifeStock(TankRotationExample tre) {
		this.tre = tre;
	}
	
	public void drawImage(Graphics g) {
		/////////////
		// Tank 1 Health & Lives
		g.setColor(Color.GREEN);
		
		if (this.tre.t1.getHealth() < 48) {
			g.setColor(Color.YELLOW);
		} else if (this.tre.t1.getHealth() < 32) {
			g.setColor(Color.ORANGE);
		} else if (this.tre.t1.getHealth() < 16) {
			g.setColor(Color.RED);
		}
		
		g.fillRect(50, 35, this.tre.t1.getHealth(), 35);
		g.drawRect(50, 35, 80, 35);
		
		int space = 30;
		int xHealthOffsetT1 = 20; 
		g.setColor(Color.RED);
		for (int i = 0; i < this.tre.t1.getLives(); i++) {
			g.fillOval(xHealthOffsetT1 + space, 80, 20, 20);
			g.drawOval(xHealthOffsetT1 + space, 80, 20, 20);
			xHealthOffsetT1 += space;
		}
		
		if(this.tre.t1.getHealth() <= 0 && this.tre.t1.getLives() != 0) {
			this.tre.t1.setHealth(80);
			this.tre.t1.setLives(this.tre.t1.getLives() - 1);
		} else if (this.tre.t1.getHealth() <= 0 && this.tre.t1.getLives() == 0) {
			this.tre.t1.setHealth(0);
			g.setColor(Color.WHITE);
			g.setFont(new Font("TimesRoman", Font.BOLD, 90));
			g.drawString("GAME OVER", 50, TankRotationExample.screen_height /4 );
			g.drawString("Player 2 WINS", TankRotationExample.screen_width / 2 + 50, TankRotationExample.screen_height /4 );
			this.tre.explosion.setXY(this.tre.t1.getX(), this.tre.t1.getY());
			this.tre.explosion.drawImage(g);
		}
		
		/////////////
		// Tank 2 Health & Lives
		g.setColor(Color.GREEN);
		
		if (this.tre.t2.getHealth() < 48) {
			g.setColor(Color.YELLOW);
		} else if (this.tre.t2.getHealth() < 32) {
			g.setColor(Color.ORANGE);
		} else if (this.tre.t2.getHealth() < 16) {
			g.setColor(Color.RED);
		}
		
		int xHealthOffSetT2 = 20; 
		// 450 for spacing 
		g.fillRect(TankRotationExample.screen_width / 2 + 500, 35, this.tre.t2.getHealth(), 35);
		g.drawRect(TankRotationExample.screen_width / 2 + 500, 35, 80, 35);
		g.setColor(Color.BLUE);
		for (int i = 0; i < this.tre.t2.getLives(); i++) {
			g.fillOval(TankRotationExample.screen_width / 2 + xHealthOffSetT2 + space + 450, 80, 20, 20);
			g.drawOval(TankRotationExample.screen_width / 2 + xHealthOffSetT2 + space+ 450, 80, 20, 20);
			xHealthOffSetT2 += space;
		}
		
		if(this.tre.t2.getHealth() <= 0 && this.tre.t2.getLives() != 0) {
			this.tre.t2.setHealth(80);
			this.tre.t2.setLives(this.tre.t2.getLives() - 1);
		} else if (this.tre.t2.getHealth() <= 0 && this.tre.t2.getLives() == 0) {
			this.tre.t2.setHealth(0);
			g.setColor(Color.WHITE);
			g.setFont(new Font("TimesRoman", Font.BOLD, 90));
			g.drawString("GAME OVER", TankRotationExample.screen_width / 2 + 30, TankRotationExample.screen_height /4 );
			g.drawString("Player 1 WINS", 20, TankRotationExample.screen_height /4);
			this.tre.explosion.setXY(this.tre.t2.getX(), this.tre.t2.getY());
			this.tre.explosion.drawImage(g);
		}
	}
}
