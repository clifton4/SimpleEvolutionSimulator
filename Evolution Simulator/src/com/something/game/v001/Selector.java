package com.something.game.v001;

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
		double totalFitness = 0;
		
		//calculate total fitness
		
		for (Dot dot : myPop.getDots()) {
			double fitness = fitness(dot);
			totalFitness = totalFitness + fitness;
			//System.out.println("fitness =   " + fitness + "\t total = " + totalFitness);
			double[] fitnessInterval = {(totalFitness-fitness), totalFitness};
			dot.setFitnessInterval(fitnessInterval);
			dot.setFitnessScore(fitness);
		}
		
		
		
		ArrayList<Dot> newDots = new ArrayList<Dot>();
		Arrays.sort(myPop.getDots(), Collections.reverseOrder());
		
		//randomly select fitness scores and keep the dots within that range 
		/**
		while (newDots.size() < myPop.populationSize/2) {
			int selection = (int)(Math.random()*totalFitness);
			for (Dot dot : myPop.getDots()) {
				if (dot.inFitnessInterval(selection)){
					if (!(dot.isSelected())) {
						dot.select();
						newDots.add(dot);
					}
				}
			}
		}
		**/
		//or select all the highest scoring dots
		
		System.out.println("The best dot was " + myPop.getDots()[0]);
		int counter = 0;
		while (newDots.size() < (myPop.populationSize/2)) {
				Dot dot = myPop.getDots()[counter];
				dot.select(); 
				counter ++;
				newDots.add(dot);
		}
		
		
		
		//fill the remaining slots with babies of the existing dots
		Dot[] newDotArray = new Dot[myPop.populationSize];
		
		for (int i = 0; i < newDots.size(); i ++) {
				newDotArray[i] = newDots.get(i);
				newDotArray[i + myPop.populationSize/2] = newDots.get(i).makeBaby();
				//System.out.println("added dot " + newDots.get(i));
			}
		for (Dot dot : newDotArray) {
			
			dot.restart();
		}

		return newDotArray;
	}
	
	private Dot[] convertListToArray(ArrayList<Dot> dots) {
		Dot[] array = new Dot[dots.size()];
		for (int i = 0; i <dots.size(); i ++) {
			array[i] = dots.get(i);
		}
		return array;
	}

	private double fitness(Dot dot) {
		double dist = dot.distanceToVal(goalX, goalY);
		double score = Double.MAX_VALUE / (Math.pow(dist, 5));
		
		//truncate the double to 5 decimcal places and return it
		//not doing this was causing serious errors in adding fitnesses to find total fitness
		String scoreStr = Double.toString(score);
		return Double.parseDouble(scoreStr.substring(0, scoreStr.indexOf(".") + 6));
	}
}
