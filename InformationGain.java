

import java.util.HashSet;

public class InformationGain {

	public InformationGain(double gain, HashSet<Integer> pp, HashSet<Integer> pn, HashSet<Integer> np, HashSet<Integer> nn) {
		this.gain = gain;
		this.pp = pp;
		this.pn = pn;
		this.np = np;
		this.nn = nn;
	}

	@Override
	public String toString() {
		return "" + gain;
	}

	/**
	 * @return the gain
	 */
	public double getGain() {
		return gain;
	}

	/**
	 * @param gain the gain to set
	 */
	public void setGain(double gain) {
		this.gain = gain;
	}

	/**
	 * @return the pp
	 */
	public HashSet<Integer> getPp() {
		return pp;
	}

	/**
	 * @param pp the pp to set
	 */
	public void setPp(HashSet<Integer> pp) {
		this.pp = pp;
	}

	/**
	 * @return the pn
	 */
	public HashSet<Integer> getPn() {
		return pn;
	}

	/**
	 * @param pn the pn to set
	 */
	public void setPn(HashSet<Integer> pn) {
		this.pn = pn;
	}

	/**
	 * @return the np
	 */
	public HashSet<Integer> getNp() {
		return np;
	}

	/**
	 * @param np the np to set
	 */
	public void setNp(HashSet<Integer> np) {
		this.np = np;
	}

	/**
	 * @return the nn
	 */
	public HashSet<Integer> getNn() {
		return nn;
	}

	/**
	 * @param nn the nn to set
	 */
	public void setNn(HashSet<Integer> nn) {
		this.nn = nn;
	}
	
	private double gain;
	private HashSet<Integer> pp;
	private HashSet<Integer> pn;
	private HashSet<Integer> np;
	private HashSet<Integer> nn;
	
}