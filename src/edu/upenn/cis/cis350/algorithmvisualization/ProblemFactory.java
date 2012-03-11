package edu.upenn.cis.cis350.algorithmvisualization;

import java.util.Collection;

/**
 * Generates the Bins and BinObjects for each problem difficulty, as specified in
 * res/xml/problems.xml.
 */
public class ProblemFactory {

	private Collection<BinObject> easyObjects, mediumObjects, hardObjects;
	private Collection<Bin> easyBins, mediumBins, hardBins;
	private double easyOptSol, mediumOptSol, hardOptSol;
	
	/**
	 * For each difficulty: reads in the app config specified in res/xml/problems.xml,
	 * generates the corresponding Bin and BinObject collections, and calculates
	 * the value of the optimal solution.
	 */
	public ProblemFactory() {
		//TODO
	}
	
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
	
}
