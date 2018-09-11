/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.Observable;

/**
 *
 * @author anthony-pc
 */
public class GameEventObservable extends Observable {

    public GameEventObservable() {
    }

    @Override
    protected synchronized void setChanged() {
        super.setChanged();
    }
    
    
}
