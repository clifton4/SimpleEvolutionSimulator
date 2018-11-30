package com.something.game.v001;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Selector {
	private Handler handler;
	private Population myPop;
	private int goalX, goalY;
	
	public Selector(Handler handler, Population myPop) {
		this.handler = handler;
		this.myPop = myPop;
		setGoal();
	}
	
	private void setGoal() {
		for (GameObject o : handler) {
			if (o.id == ID.Goal) {
				goalX = o.getX();
				goalY = o.getY();
				return;
			}
		}
	}
	
	public void tick() {
		if (myPop.isDead()){
			reset();
		}
	}
	
	public void reset() {

		handler.remove(myPop);
		
		myPop.setDots(makeBabies());
		handler.add(myPop);
		//for (Dot dot : myPop.getDots()) {
		//	System.out.println(dot);
		//}
		
	}
	
	public void generate() {
		handler.add(myPop);
		handler.add(new SimpleBarrier(0, 0, DotGame.WIDTH, 10, handler));
		handler.add(new SimpleBarrier(0, 0, 10, DotGame.HEIGHT, handler));
		
		handler.add(new SimpleBarrier(0, DotGame.HEIGHT-39, DotGame.WIDTH, 10, handler));
		handler.add(new SimpleBarrier(DotGame.WIDTH-15, 0, 10, DotGame.HEIGHT, handler));
		handler.add(new Goal(DotGame.WIDTH/2, 100, handler));
	}

	
	/**
	 * This method creates a new array of dots from myPop.getDots()
	 * It selects only the top scoring half according to the fitness function and replaces the bottom scoring half with children of the top
	 * 
	 * @return a new Dot array made of the fittest dots from last population and their children
	 */
	private Dot[] makeBabies() {
		int totalFitness = 0;
		//calculate total fitness
		
		for (Dot dot : myPop.getDots()) {
			int fitness = fitness(dot);
			totalFitness = totalFitness + fitness;
			//System.out.println("fitness =   " + fitness + "\t total = " + totalFitness);
			dot.setFitnessScore(fitness);
		}
		
		
		
		Dot[] newDots = new Dot[myPop.populationSize];
		Arrays.sort(myPop.getDots(), Collections.reverseOrder());
		
		for(Dot dot : myPop.getDots()) System.out.println(dot);
		Dot bestDot = myPop.getDots()[0];
		
		for (int i = 1; i < newDots.length; i ++) {
			int selection = (int)(Math.random() * totalFitness);
			int sumFit = 0;
			for (Dot oldDot : myPop.getDots()) {
				sumFit += oldDot.getFitnessScore();
				if (sumFit > selection) {
					newDots[i] = oldDot.makeBaby();
					break;
				}
			}
			if (newDots[i] == null) {
				System.out.println("Failed to find fit dot");
			}
		}
		
		
		newDots[0] = bestDot;
		newDots[0].setColor(Color.red);
		
		for (Dot dot : newDots) dot.restart();;
		return newDots;
		
	}
	
	private Dot[] convertListToArray(ArrayList<Dot> dots) {
		Dot[] array = new Dot[dots.size()];
		for (int i = 0; i <dots.size(); i ++) {
			array[i] = dots.get(i);
		}
		return array;
	}

	private int fitness(Dot dot) {
		float dist = dot.distanceToVal(goalX, goalY);
		float temp = (float) Math.pow(dist,  5);
		//System.out.println("temp = " + temp);
		float score = (1 / temp);
	
		
		//System.out.println(dot);
		//System.out.println("Score = " + score + " dist = " + dist);
		
		/*
		 * 	Dot, position = (307 , 429)		Fitness = 0 this dot is a victor: false
			Score = 8.371174E24
			Dot, position = (319 , 108)		Fitness = 0 this dot is a victor: true
			Score = 7.945899E25
		 * 
		 *  - cannot explain this result
		 */
		
		return Integer.parseInt(Float.toString(score).substring(0, 1));
	}
}
