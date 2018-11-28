package com.something.game.v001;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Dot extends GameObject{
	private static ID id = ID.Dot;
	private static final int steps = 200;
	
	private int width = 10, height = 10;
	private int[] plan;
	private int step = 0;
	private Color dotColor = Color.black;
	private boolean dead;
	private int[] fitnessInterval;
	private static double mutationRate = .01;
	private boolean selected = false;
	
	public Dot(int x, int y, Handler handler) {
		super(x, y, id, handler);
		
		plan = new int[steps];
		dead = false;
		
		fitnessInterval = new int[2];
		for (int i =0; i < plan.length; i++) {
			plan[i] = (int)((Math.random()*2-1)*5);
		}
	}
	
	public Dot(Handler handler, int[] plan) {
		super(DotGame.STARTX, DotGame.STARTY, id, handler);
		this.plan = plan;
		dead = false;
		fitnessInterval = new int[2];
	}
	
	@Override 
	public String toString() {
		return "Dot, position = (" + x + " , " + y + ")" + "\n" + 
				"Fitness = " + fitnessInterval[1] + " - " +  fitnessInterval[0];
	}
	
	public void select() {
		selected = true;
	}
	
	
	public boolean isSelected() {
		return selected;
	}
	
	public boolean isDead() {
		return dead;
	}
	
	private void checkCollision() {
		for (int i = 0; i < handler.size(); i ++) {
			GameObject object = handler.get(i);
			if (object.id == ID.SimpleBarrier) {
				if (this.getBounds().intersects(object.getBounds())) {
					dead = true;
				}
			}
		}
	}
	
	public void setFitnessInterval(int[] interval) {
		fitnessInterval = interval;
	}
	
	public int[] getFitnessInterval() {
		return fitnessInterval;
	}
	
	public boolean inFitnessInterval(int fitness) {
		if (fitness >= fitnessInterval[0] && fitness < fitnessInterval[1]) {
			return true;
		}
		return false;
	}
	
	@Override
	public void tick() {
		if (step >= plan.length -1) {
			dead = true;
		}
		else {
			checkCollision();
		}
		
		
		if( !isDead() ){
			x += plan[step];
			step ++;
		
			y += plan[step];
			step ++;
		}
		else {
			
		}
		
		
	}
	
	@Override
	public void render(Graphics g) {
		g.setColor(dotColor);
		g.fillRect(x, y, width, height);
	}


	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}


	public Dot makeBaby() {
		// TODO Auto-generated method stub
		for (int i = 0; i < plan.length; i ++ ) {
			if (Math.random() <= mutationRate) {
				//System.out.println("Mutation Made");
				plan[i] = (int)((Math.random()*2-1)*5);
			}
		}
		return new Dot(handler, plan);
	}

	public Dot reset() {
		return new Dot(handler, plan);
	}
	
	
	public int distanceToVal(int x, int y) {
		return (int)Math.hypot(this.x - x, this.y - y);
	}
	
}
