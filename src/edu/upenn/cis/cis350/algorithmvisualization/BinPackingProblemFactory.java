package edu.upenn.cis.cis350.algorithmvisualization;

import java.util.ArrayList;
import java.util.Collection;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.util.Log;

/**
 * Generates the Bins and BinObjects for each Bin Packing problem difficulty, 
 * as specified in res/xml/problems.xml. Also calculates optimal solutions.
 */
public class BinPackingProblemFactory {
	
	private Collection<BinObject> easyObjects, mediumObjects, hardObjects;
	private Collection<Bin> easyBins, mediumBins, hardBins;
	private double easyOptSol, mediumOptSol, hardOptSol;
	
	/**
	 * @param difficulty The difficulty of the problem as specified in problems.xml.
	 * @return a Collection of all BinObjects for the specified difficulty, or null if the
	 * difficulty is unspecified in problems.xml.
	 */
	public Collection<BinObject> getBinObjects(String difficulty) {
		if ("easy".equalsIgnoreCase(difficulty)) return easyObjects;
		else if ("medium".equalsIgnoreCase(difficulty)) return mediumObjects;
		else if ("hard".equalsIgnoreCase(difficulty)) return hardObjects;
		return null;
	}
	
	/**
	 * @param difficulty The difficulty of the problem as specified in problems.xml.
	 * @return a Collection of all Bins for the specified difficulty, or null if the
	 * difficulty is unspecified in problems.xml.
	 */
	public Collection<Bin> getBins(String difficulty) {
		if ("easy".equalsIgnoreCase(difficulty)) return easyBins;
		else if ("medium".equalsIgnoreCase(difficulty)) return mediumBins;
		else if ("hard".equalsIgnoreCase(difficulty)) return hardBins;
		return null;
	}
	
	/**
	 * @param difficulty The difficulty of the problem as specified in problems.xml.
	 * @return the value of the optimal solution for the specified difficulty, or -1 if the
	 * difficulty is unspecified in problems.xml.
	 */
	public double getOptimalSolution(String difficulty) {
		if ("easy".equalsIgnoreCase(difficulty)) return easyOptSol;
		else if ("medium".equalsIgnoreCase(difficulty)) return mediumOptSol;
		else if ("hard".equalsIgnoreCase(difficulty)) return hardOptSol;
		return -1;
	}
	
	/**
	 * For each difficulty: reads in the app config specified in res/xml/problems.xml,
	 * generates the corresponding Bin and BinObject collections, and calculates
	 * the value of the optimal solution.
	 */
	public BinPackingProblemFactory(Context context) {
		//Initialize instance variables
		easyBins = new ArrayList<Bin>();
		mediumBins = new ArrayList<Bin>();
		hardBins = new ArrayList<Bin>();
		easyObjects = new ArrayList<BinObject>();
		mediumObjects = new ArrayList<BinObject>();
		hardObjects = new ArrayList<BinObject>();
		easyOptSol = -1;
		mediumOptSol = -1;
		hardOptSol = -1;
		
		//Parse problems.xml
		XmlResourceParser parser;
		parser = context.getResources().getXml(R.xml.problems);
		try {
			int event = parser.next();
			String difficulty = "";
			while (!(event == XmlPullParser.END_TAG && parser.getName().equalsIgnoreCase("item"))) {
				if (event == XmlPullParser.START_TAG) {
					String element = parser.getName();
					System.out.println("name: " + element);
					if ("problem".equals(element)) { //Parse problem difficulty
						difficulty = parser.getAttributeValue(null, "difficulty");
						if (!"easy".equalsIgnoreCase(difficulty) && !"medium".equalsIgnoreCase(difficulty)
								&& !"hard".equalsIgnoreCase(difficulty)) 
							throw new XmlPullParserException("Invalidly formatted file");
					} else if ("bin".equals(element)) { //Parse Bin element
						double capacity = new Double(parser.getAttributeValue(null,"capacity"));
						Bin bin = new Bin(capacity);
						if ("easy".equalsIgnoreCase(difficulty)) easyBins.add(bin);
						else if ("medium".equalsIgnoreCase(difficulty)) mediumBins.add(bin);
						else if ("hard".equalsIgnoreCase(difficulty)) hardBins.add(bin);
						else throw new XmlPullParserException("Invalidly formatted file");
					} else if ("object".equals(element)) { //Parse BinObject element
						double weight = new Double(parser.getAttributeValue(null,"weight"));
						double value = new Double(parser.getAttributeValue(null,"value"));
						String type = parser.getAttributeValue(null,"type");
						BinObject obj = new BinObject(weight, value, type);
						if ("easy".equalsIgnoreCase(difficulty)) easyObjects.add(obj);
						else if ("medium".equalsIgnoreCase(difficulty)) mediumObjects.add(obj);
						else if ("hard".equalsIgnoreCase(difficulty)) hardObjects.add(obj);
						else throw new XmlPullParserException("Invalidly formatted file");
					}
				}
				event = parser.next();
			}
		} catch (Exception e) { //Invalidly formatted XML file
			System.err.println("Problem parsing res/xml/problems.xml - please check file format");
			System.exit(-1);
		} finally {
			parser.close();
		}

		easyOptSol = calculateSoltion(easyBins, easyObjects);
		mediumOptSol = calculateSoltion(mediumBins, mediumObjects);
		hardOptSol = calculateSoltion(hardBins, hardObjects);
	}
	
	/**
	 * Calculates the optimal solution for the given collections of Bins and BinObjects. Note that if
	 * there's more than 1 Bin involved, the problem becomes NP-Hard. In this case the method only
	 * returns an approximation to the optimal solution.
	 * @param bins
	 * @param objects
	 * @return The value of the optimal solution to the Bin-Packing problem.
	 */
	private double calculateSoltion(Collection<Bin> bins, Collection<BinObject> objects) {
		double sol = 0;
		ArrayList<BinObject> objs = new ArrayList<BinObject>();
		for (BinObject obj : objects) objs.add(obj);
		
		for (Bin bin : bins) {
			boolean[] choices = knapsack(bin, objs);
			ArrayList<BinObject> chosen = new ArrayList<BinObject>();
			for (int i=0; i<choices.length; i++) {
				if (choices[i]) {
					chosen.add(objs.get(i));
				}
			}
			for (BinObject obj : chosen) {
				sol += obj.getValue();
				objs.remove(obj);
			}
		}
		
		return sol;
	}
	
	/**
	 * Takes a bin and a collection of objects and calculates the optimal solution.
	 * @param bin
	 * @param objects
	 * @return Which objects are included in the optimal solution.
	 */
	private boolean[] knapsack(Bin bin, Collection<BinObject> objects) {
		Object[] objs = objects.toArray();
		int numObjs = objs.length;
		int capacity = (int) Math.floor(bin.getCapacity());

		//Optimal solution for packing objects 0..n+1 with capacity limit c
		double[][] optSol = new double[numObjs+1][capacity+1];
		for (int c=0; c<capacity+1; c++) optSol[0][c] = 0; //Packing 0 items has 0 value
		
		//Whether the corresponding optimal solution includes item n
		boolean[][] solChoice = new boolean[numObjs+1][capacity+1];
		for (int c=0; c<capacity+1; c++) solChoice[0][c] = false; //Packing no items if you pick 0 of them

		for (int n=1; n<numObjs+1; n++) {
			//Object's weight and value
			int weight = (int) Math.ceil(((BinObject)objs[n-1]).getWeight());
			double value = ((BinObject)objs[n-1]).getValue();

			for (int c=0; c<capacity+1; c++) {
				//Value from leaving the item
				double leaveItem = optSol[n-1][c];

				//Value from taking the item
				double takeItem = 0;
				if (weight <= c) takeItem = value + optSol[n-1][c-weight];

				//Select optimal solution
				if (takeItem > leaveItem) {
					optSol[n][c] = takeItem;
					solChoice[n][c] = true;
				} else {
					optSol[n][c] = leaveItem;
					solChoice[n][c] = false;
				}
			}

		}
		
		//Calculate which items were chosen
		boolean[] choices = new boolean[numObjs];
		for (int n=numObjs, c=capacity; n>0; n--) {
			if (solChoice[n][c]) {
				choices[n-1] = true;
				c = c - (int) Math.ceil(((BinObject)objs[n-1]).getWeight());
			} else choices[n-1] = false;
		}
		
		return choices;
	}
	
}
