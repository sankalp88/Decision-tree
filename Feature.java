

import java.util.ArrayList;

public class Feature {

	public Feature() {
		super();
	}

	/**
	 * @param name
	 */
	public Feature(String name) {
		super();
		this.name = name;
		this.value = new ArrayList<>();
	}

	/**
	 * @param name
	 * @param value
	 */
	public Feature(String name, ArrayList<Integer> value) {
		super();
		this.name = name;
		this.value = value;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return name + ": " + value;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return the value
	 */
	public ArrayList<Integer> getValue() {
		return value;
	}
	
	/**
	 * @param value the value to set
	 */
	public void setValue(ArrayList<Integer> value) {
		this.value = value;
	}

	private String name;
	private ArrayList<Integer> value;

}
