package com.something.game.v001;

import java.util.ArrayList;

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
			handler.remove(myPop);
			myPop.setDots(makeBabies());
			handler.add(myPop);
		}
	}
	
	private Dot[] makeBabies() {
		double totalFitness = 0;
		
		//calculate total fitness
		
		for (Dot dot : (Dot[])myPop.getDots()) {
			double fitness = fitness(dot);
			totalFitness += fitness;
			double[] fitnessInterval = {(totalFitness-fitness), totalFitness};
			dot.setFitnessInterval(fitnessInterval);
			System.out.print("Dot of distance: " + 
					dot.distanceToVal(DotGame.WIDTH/2, 100));
			System.out.println("\t was given a fitness score of: " + fitness);
		}
		System.out.println("calculated total fitness: " + totalFitness);
		
		
		ArrayList<Dot> newDots = new ArrayList<Dot>();
		
		
		//randomly select fitness scores and keep the dots within that range 
		/**
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
		**/
		//or select all the highest scoring dots
		
		
		
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

	private double fitness(Dot dot) {
		double dist = dot.distanceToVal(goalX, goalY);
		return 1 / (Math.pow(dist, 3));
	}
}
