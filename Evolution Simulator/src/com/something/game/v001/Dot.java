package com.something.game.v001;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Dot extends GameObject implements Comparable<Dot>{
	private static ID id = ID.Dot;
	private static final int steps = 640;
	
	private int width = 10, height = 10;
	private int[] plan;
	private int step = 0;
	private Color dotColor = Color.black;
	private boolean isDead = false, isVictor = false;
	private double[] fitnessInterval;
	private double mutationRate = 0.0;
	private boolean selected = false;
	private double fitness = 0;
	private int velX = 0, velY = 0;
	
	public Dot(int x, int y, Handler handler) {
		super(x, y, id, handler);
		
		plan = new int[steps];
		isDead = false;
		fitnessInterval = new double[2];
		for (int i =0; i < plan.length; i++) {
			plan[i] = (int)((Math.random()*2-1)*5);
		}
	}
	
	public Dot(int x, int y, Handler handler, int[] plan) {
		super(x, y, id, handler);
		this.plan = plan;
		isDead = false;
		fitnessInterval = new double[2];
	}
	
	@Override 
	public String toString() {
		return "Dot, position = (" + x + " , " + y + ")" + "\t\t" 
				+ "Fitness = " + fitness + " this dot is a victor: " + isVictor();
	}
	
	public void select() {
		selected = true;
	}
	
	
	public boolean isSelected() {
		return selected;
	}
	
	public boolean isDead() {
		return isDead;
	}
	
	private void checkCollision() {
		for (int i = 0; i < handler.size(); i ++) {
			GameObject object = handler.get(i);
			if (object.id == ID.SimpleBarrier) {
				if (this.getBounds().intersects(object.getBounds())) {
					isDead = true;
				}
			}
			if (object.id == ID.Goal) {
				if (this.getBounds().intersects(object.getBounds())) {
					isDead = true;
					isVictor = true;
				}
			}
		}
		if (this.x > DotGame.WIDTH || this.y > DotGame.HEIGHT || this.x < 0 || this.y < 0) {
			isDead = true;
		}
	}
	
	public void setFitnessInterval(double[] interval) {
		fitnessInterval = interval;
	}
	
	public double[] getFitnessInterval() {
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
			isDead = true;
		}
		else {
			checkCollision();
		}
		
		
		if( !isDead() ){
			velX += plan[step];
			step ++;
		
			velY += plan[step];
			step ++;
			
			
			DotGame.clamp(velX, -2, 2);
			DotGame.clamp(velY, -2, 2);
			y += velY;
			x += velX;
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
		
		int[] newPlan = new int[plan.length];
		for (int i = 0; i < newPlan.length; i ++ ) {
			if (Math.random() <= mutationRate) {
				newPlan[i] = (int)((Math.random()*2-1)*5);
			}
			else {
				newPlan[i] = plan[i];
				mutationRate += .00001;
			}
		}
		
		return new Dot(x, y, handler, newPlan);
	}
	
	public double distanceToVal(int x, int y) {
		//System.out.println("getX() - x = " + (getX()-x));
		double distX = Math.pow((getX() - x), 2);
		//System.out.println("x^2 = " + distX);
		double distY = Math.pow((getY() - y), 2);
		//System.out.println("Y^2 = " + distY);
		return Math.sqrt(distX + distY);
	}

	public void setFitnessScore(double fitness) {
		this.fitness  = fitness;
	}

	public double getFitnessScore() {
		return fitness;
	}

	@Override
	public int compareTo(Dot compareDot) {
		if (this.fitness > compareDot.fitness) {
			return 1;
		}
		if (this.fitness < compareDot.fitness) {
			return -1;
		}
		return 0;
	}

	public void restart() {
		this.x = DotGame.STARTX;
		this.y = DotGame.STARTY;
		this.step = 0;
		this.isDead = false;
		this.isVictor = false;
	}

	public void setColor(Color red) {
		this.dotColor = red;
	}
	
	public boolean isVictor() {
		return isVictor;
	}
	
	public void setIsVictor(boolean isVictor) {
		this.isVictor = isVictor;
	}
	
	
	
}
