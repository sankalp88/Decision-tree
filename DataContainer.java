

import java.util.ArrayList;

public class DataContainer {

	public DataContainer() {
		super();
	}
	
	/**
	 * @param featureList
	 * @param output
	 */
	public DataContainer(ArrayList<Feature> featureList, Feature output) {
		super();
		this.featureList = featureList;
		this.output = output;
	}

	/**
	 * @return the featureList
	 */
	public ArrayList<Feature> getFeatureList() {
		return featureList;
	}

	/**
	 * @param featureList the featureList to set
	 */
	public void setFeatureList(ArrayList<Feature> featureList) {
		this.featureList = featureList;
	}

	/**
	 * @return the output
	 */
	public Feature getOutput() {
		return output;
	}

	/**
	 * @param output the output to set
	 */
	public void setOutput(Feature output) {
		this.output = output;
	}
	
	public int getNumberOfInstances(){
		if(output == null)
			return 0;
		return output.getValue().size();
	}
	
	public int getNumberOfFeatures(){
		return this.featureList.size();
	}

	private ArrayList<Feature> featureList;
	private Feature output;
	
}
