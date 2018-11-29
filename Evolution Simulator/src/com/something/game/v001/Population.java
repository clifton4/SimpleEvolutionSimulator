package com.something.game.v001;

import java.awt.Graphics;
import java.awt.Rectangle;

public class Population extends GameObject{

	private int generation;
	private Dot[] dots;
	public int populationSize = 2; //must be even
	private static ID id = ID.Population;
	
	
	public Population(int x, int y, Handler handler) {
		super(x, y, id, handler);
		dots = new Dot[populationSize];
		
		for (int i = 0; i < populationSize; i ++) {
			dots[i] = new Dot(x, y, handler);
		}
	}
	

	@Override 
	public String toString() {
		String out = "";
		for (Dot dot : dots) {
			out += dot + "\n";
		}
		return out;
	}
	public void setDots(Dot[] dots) {
		this.dots = dots;
	}
	
	
	public int getGeneration() {
		return generation;
	}
	
	public void setGeneration(int gen) {
		this.generation = gen;
	}

	public void render(Graphics g) {
		for (int i = 0; i < dots.length; i ++) {
			dots[i].render(g);
		}
	}

	public void tick() {
		for (int i = 0; i < dots.length; i ++) {
			dots[i].tick();
		}
	}


	@Override
	public Rectangle getBounds() {
		// All game Objects must have bounds, 
		return null;
	}
	
	/**
	 * iterates over entire population. time comp heavy
	 */
	public boolean isDead() {
		boolean dead = true;
		for (Dot dot : dots) {
			if (!(dot.isDead())) {
				dead = false;
			}
		}
		return dead;
	}


	public Dot[] getDots() {
		// TODO Auto-generated method stub
		return dots;
	}

	
}
