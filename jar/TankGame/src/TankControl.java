/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Observable;

/**
 *
 * @author anthony-pc
 */
public class TankControl extends Observable implements KeyListener {

    private Tank t1;
    private final int up;
    private final int down;
    private final int right;
    private final int left;
    private final int fire;

    public TankControl(Tank t1, int up, int down, int left, int right, int fire) {
        this.t1 = t1;
        this.up = up;
        this.down = down;
        this.right = right;
        this.left = left;
        this.fire = fire;
    }

    public void keyTyped(KeyEvent ke) {

    }

    public void keyPressed(KeyEvent ke) {
        int keyPressed = ke.getKeyCode();
        if (keyPressed == up) {
            this.t1.toggleUpPressed();
            //System.out.println(keyPressed);
        }
        if (keyPressed == down) {
            this.t1.toggleDownPressed();
            //System.out.println(keyPressed);
        }
        if (keyPressed == left) {
            this.t1.toggleLeftPressed();
            //System.out.println(keyPressed);
        }
        if (keyPressed == right) {
            this.t1.toggleRightPressed();
            //System.out.println(keyPressed);
        }
        if (keyPressed == fire ) {
        	this.t1.toggleFirePressed();
        	//System.out.println(keyPressed);
        }
    }

    public void keyReleased(KeyEvent ke) {
        int keyReleased = ke.getKeyCode();
        if (keyReleased  == up) {
            this.t1.unToggleUpPressed();
        }
        if (keyReleased == down) {
            this.t1.unToggleDownPressed();
        }
        if (keyReleased  == left) {
            this.t1.unToggleLeftPressed();
        }
        if (keyReleased  == right) {
            this.t1.unToggleRightPressed();
        }
        if (keyReleased == fire ) {
        	this.t1.unToggleFirePressed();
        }
    }
}
