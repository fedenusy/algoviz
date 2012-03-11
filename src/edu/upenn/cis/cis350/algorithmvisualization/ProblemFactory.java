package edu.upenn.cis.cis350.algorithmvisualization;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Generates the Bins and BinObjects for each problem difficulty, as specified in
 * res/xml/problems.xml.
 */
public class ProblemFactory {

	private ArrayList<BinObject> easyObjects, mediumObjects, hardObjects;
	private ArrayList<Bin> easyBins, mediumBins, hardBins;
	
	/**
	 * Reads in the app config specified in res/xml/problems.xml and generates the 
	 * corresponding Bin and BinObject collections for each difficulty.
	 */
	public ProblemFactory() {
		//TODO
	}
	
	/**
	 * @param difficulty The difficulty of the object collection as specified in problems.xml.
	 * @return a Collection of all BinObjects for the specified difficulty.
	 */
	public Collection<BinObject> getBinObjects(String difficulty) {
		if ("easy".equalsIgnoreCase(difficulty)) return easyObjects;
		else if ("medium".equalsIgnoreCase(difficulty)) return mediumObjects;
		else if ("hard".equalsIgnoreCase(difficulty)) return hardObjects;
		return null;
	}
	
	/**
	 * @param difficulty The difficulty of the bin collection as specified in problems.xml.
	 * @return a Collection of all Bins for the specified difficulty.
	 */
	public Collection<Bin> getBins(String difficulty) {
		if ("easy".equalsIgnoreCase(difficulty)) return easyBins;
		else if ("medium".equalsIgnoreCase(difficulty)) return mediumBins;
		else if ("hard".equalsIgnoreCase(difficulty)) return hardBins;
		return null;
	}
	
}
