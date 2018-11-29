package com.something.game.v001;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Dot extends GameObject implements Comparable<Dot>{
	private static ID id = ID.Dot;
	private static final int steps = 1000;
	
	private int width = 10, height = 10;
	private int[] plan;
	private int step = 0;
	private Color dotColor = Color.black;
	private boolean dead;
	private double[] fitnessInterval;
	private static double mutationRate = .001;
	private boolean selected = false;
	private double fitness = 0.0;
	
	public Dot(int x, int y, Handler handler) {
		super(x, y, id, handler);
		
		plan = new int[steps];
		dead = false;
		
		fitnessInterval = new double[2];
		for (int i =0; i < plan.length; i++) {
			plan[i] = (int)((Math.random()*2-1)*5);
		}
	}
	
	public Dot(int x, int y, Handler handler, int[] plan) {
		super(x, y, id, handler);
		this.plan = plan;
		dead = false;
		fitnessInterval = new double[2];
	}
	
	@Override 
	public String toString() {
		return "Dot, position = (" + x + " , " + y + ")" + "\t\t" 
				+ "Fitness = " + fitness;
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
		
		int[] newPlan = new int[plan.length];
		for (int i = 0; i < newPlan.length; i ++ ) {
			if (Math.random() <= mutationRate) {
				//System.out.println("Mutation Made");
				newPlan[i] = (int)((Math.random()*2-1)*5);
			}
			else {
				newPlan[i] = plan[i];
				mutationRate += .0003;
			}
		}
		
		return new Dot(x, y, handler, newPlan);
	}
	
	public int distanceToVal(int x, int y) {
		return (int)Math.hypot(this.x - x, this.y - y);
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
		this.dead = false;
	}
	
}
