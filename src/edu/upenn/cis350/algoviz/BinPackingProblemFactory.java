package edu.upenn.cis350.algoviz;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.xmlpull.v1.XmlPullParser;

import edu.upenn.cis350.algoviz.R;

import android.content.Context;
import android.content.res.XmlResourceParser;

/**
 * Generates the Bins and BinObjects for each Bin Packing problem difficulty, 
 * as specified in res/xml/problems.xml. Also calculates optimal solutions.
 */
public class BinPackingProblemFactory {
	
	///// Instance variables /////
	private ArrayList<String> _problems;
	private HashMap<String, Collection<Bin>> _bins;
	private HashMap<String, Collection<BinObject>> _objects;
	private HashMap<String, Double> _optimalSolutions;
	private XmlResourceParser _parser; //TODO ugly instance variable - find a way to remove
	
	
	///// Constructor /////
	public BinPackingProblemFactory(Context context) {
		_parser = context.getResources().getXml(R.xml.problems);
		
		_problems = new ArrayList<String>();
		_bins = new HashMap<String, Collection<Bin>>();
		_objects = new HashMap<String, Collection<BinObject>>();
		parseProblemsFile();
		
		_optimalSolutions = new HashMap<String, Double>();
		calculateOptimalSolutions();
	}
	
	
	///// Public methods /////
	/**
	 * @return An ordered Collection of all the problem names specified in problems.xml.
	 */
	public Collection<String> getProblemNames() {
		return _problems;
	}
	
	/**
	 * @param problemName The name of the problem as specified in problems.xml.
	 * @return a Collection of all BinObjects for the specified problem, or null if the
	 * problem is unspecified in problems.xml.
	 */
	public Collection<BinObject> getBinObjects(String problemName) {
		return _objects.get(problemName);
	}
	
	/**
	 * @param problemName The name of the problem as specified in problems.xml.
	 * @return a Collection of all Bins for the specified problem, or null if the
	 * problem is unspecified in problems.xml.
	 */
	public Collection<Bin> getBins(String problemName) {
		return _bins.get(problemName);
	}
	
	/**
	 * @param problemName The name of the problem as specified in problems.xml.
	 * @return the value of the optimal solution for the specified problem, or null if the
	 * problem is unspecified in problems.xml.
	 */
	public Double getOptimalSolution(String problemName) {
		return _optimalSolutions.get(problemName);
	}
	
	
	///// Private methods /////
	private void parseProblemsFile() {
		try {
			int event = _parser.next();
			while (!doneParsingBinPacking(event)) {
				if (isStartTag(event)) parseTag();
				event = _parser.next();
			}
		} catch (Exception e) {
			System.err.println("Problem parsing res/xml/problems.xml - please check file format");
			System.exit(-1);
		} finally {
			_parser.close();
		}
	}
	
	private boolean doneParsingBinPacking(int event) {
		return "item".equalsIgnoreCase(_parser.getName()) && event == XmlPullParser.END_TAG;
	}
	
	private boolean isStartTag(int event) {
		return event == XmlPullParser.START_TAG;
	}
	
	private void parseTag() {
		String element = _parser.getName();
		if ("problem".equalsIgnoreCase(element)) instantiateNewProblem();
		else if ("bin".equalsIgnoreCase(element)) parseBin();				
		else if ("object".equalsIgnoreCase(element)) parseObject();
	}
	
	private void instantiateNewProblem() {
		String problemName = _parser.getAttributeValue(null, "name");
		_problems.add(problemName);
		_bins.put(problemName, new ArrayList<Bin>());
		_objects.put(problemName, new ArrayList<BinObject>());
	}
	
	private String lastProblem() {
		return _problems.get(_problems.size()-1);
	}
	
	private void parseBin() {
		Double capacity = new Double(_parser.getAttributeValue(null, "capacity"));
		Bin bin = new Bin(capacity);
		
		Collection<Bin> bins = _bins.get(lastProblem());
		bins.add(bin);
		
		_bins.put(lastProblem(), bins);
	}
	
	private void parseObject() {
		Double weight = new Double(_parser.getAttributeValue(null, "weight"));
		Double value = new Double(_parser.getAttributeValue(null, "value"));
		String type = _parser.getAttributeValue(null, "type");
		BinObject object = new BinObject(weight, value, type);
		
		Collection<BinObject> objects = _objects.get(lastProblem());
		objects.add(object);
		
		_objects.put(lastProblem(), objects);
	}
	
	private void calculateOptimalSolutions() {
		for (String problemName : getProblemNames()) {
			Collection<Bin> bins = getBins(problemName);
			Collection<BinObject> objects = getBinObjects(problemName);
			Double optimalSolution = calculateSolution(bins, objects);
			_optimalSolutions.put(problemName, optimalSolution);
		}
	}
	
	/**
	 * Calculates the optimal solution for the given collections of Bins and BinObjects. Note that if
	 * there's more than 1 Bin involved, the problem becomes NP-Hard. In this case the method only
	 * returns an approximation to the optimal solution.
	 * @param bins
	 * @param objects
	 * @return The value of the optimal solution to the Bin-Packing problem.
	 */
	private double calculateSolution(Collection<Bin> bins, Collection<BinObject> objects) {
		double sol = 0;
		
		ArrayList<BinObject> objs = new ArrayList<BinObject>();
		objs.addAll(objects);
		
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
