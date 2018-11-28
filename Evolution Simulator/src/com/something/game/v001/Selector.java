package com.something.game.v001;

import java.util.ArrayList;

public class Selector {
	private Handler handler;
	private Population myPop;
	
	public Selector(Handler handler, Population myPop) {
		this.handler = handler;
		this.myPop = myPop;
	}
	
	public void tick() {
		if (myPop.isDead()){
			handler.remove(myPop);
			myPop.setDots(makeBabies());
			handler.add(myPop);
		}
	}
	
	private Dot[] makeBabies() {
		int totalFitness = 0;
		
		//calculate total fitness
		
		for (Dot dot : (Dot[])myPop.getDots()) {
			double fitness = fitness(dot, 0, 0);
			totalFitness += fitness;
			int[] fitnessInterval = {(int) (totalFitness-fitness), totalFitness};
			dot.setFitnessInterval(fitnessInterval);
			System.out.print("Dot of distance: " + 
					dot.distanceToVal(DotGame.WIDTH/2, 100));
			System.out.println("\t was given a fitness score of: " + fitness);
		}
		System.out.println("calculated total fitness: " + totalFitness);
		
		
		ArrayList<Dot> newDots = new ArrayList<Dot>();
		
		//select dots based on fitness and add them to an ArrayList of chosen dots
		while (newDots.size() < myPop.populationSize/2) {
			int selection = (int)(Math.random()*totalFitness);
			for (Dot dot : (Dot[])myPop.getDots()) {
				if (dot.inFitnessInterval(selection)){
					if (!(dot.isSelected())) {
						dot.select();
						newDots.add(dot);
					}
				}
			}
		}
		Dot[] newDotArray = new Dot[myPop.populationSize];
		
		for (int i = 0; i < newDotArray.length; i ++) {
			if ( i < newDots.size()) {
				//System.out.println("Reseting a dot...");
				newDotArray[i] = newDots.get(i).reset();
			}else {
				//System.out.println("Creating new dot...");
				newDotArray[i] = newDots.get(myPop.populationSize-i-1).makeBaby();
			}
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

	private double fitness(Dot dot, int goalX, int goalY) {
		double dist = dot.distanceToVal(goalX, goalY);
		return 1 / (Math.pow(dist, 2));
	}
}
