

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Set;


public class DecisionTree {

	public DecisionTree() {
		super();
	}

	/**
	 * @param featureList
	 * @param output
	 */
	public DecisionTree(ArrayList<Feature> featureList, Feature output) {
		super();
		this.featureList = featureList;
		this.output = output;
		this.random = new Random();
	}
	
	public void generateTree(){
		HashSet<Integer> positive = new HashSet<>();
		HashSet<Integer> negative = new HashSet<>();
		HashSet<Integer> availableFeatures = new HashSet<>();
		
		for (int i = 0; i < output.getValue().size(); i++) {
			if (output.getValue().get(i) == 1) {
				positive.add(i);
			} else {
				negative.add(i);
			}
		}
		for (int i = 0; i < featureList.size(); i++){
			availableFeatures.add(i);
		}
		root = new DecisionTreeNode(availableFeatures, positive, negative);

		//Grow tree
		PriorityQueue<DecisionTreeNode> heap = new PriorityQueue<>();
		root = DecisionTreeUtil.chooseFeature(root, featureList);
		heap.add(root);

		while (!heap.isEmpty()) {
			DecisionTreeNode node = heap.poll();
			if (node.getBestGain().getGain() == 0) {
				// can divide this anymore
				node.setPureClass(true);
				if (node.getNegative().size() > node.getPositive().size())
					node.setClassType(0);
				else if (node.getNegative().size() < node.getPositive().size())
					node.setClassType(0);
				else
					node.setClassType(this.random.nextBoolean() ? 1 : 0);
			} else {
				availableFeatures = new HashSet<>();

				availableFeatures.addAll(node.getAvailableFeatures());
				availableFeatures.remove(node.getFeatureIndex());
				node.setLeftChild(new DecisionTreeNode(availableFeatures, node.getBestGain().getPp(), node.getBestGain().getPn()));
				node.setRightChild(new DecisionTreeNode(availableFeatures, node.getBestGain().getNp(), node.getBestGain().getNn()));
				if (!node.getLeftChild().isPureClass()) {
					node.setLeftChild(DecisionTreeUtil.chooseFeature(node.getLeftChild(), featureList));
					heap.add(node.getLeftChild());
				}
				if (!node.getRightChild().isPureClass()) {
					node.setRightChild(DecisionTreeUtil.chooseFeature(node.getRightChild(), featureList));
					heap.add(node.getRightChild());
				}
			}
		}
	}
	
	public void pruneTree(double factor, ArrayList<Feature> validationList, Feature output) {
		double acc = DecisionTreeUtil.eval(root, validationList, output);
		
		// List NonLeaf Nodes
		List<DecisionTreeNode> fullList = getNodeList();
		
		while (true) {
			int m = (int) Math.floor(factor * fullList.size());
			List<DecisionTreeNode> modified = new ArrayList<>();
			List<DecisionTreeNode> list = fullList;
			for (int j = 0; j < m; j++) {
				int n = list.size();
				if (n == 0) {
					break;
				}
				int p = this.random.nextInt(n);
				DecisionTreeNode d = list.get(p);
				modified.add(d);
				d.setPureClass(true);
				if (d.getNegative().size() > d.getPositive().size()) {
					d.setClassType(0);
				} else if (d.getNegative().size() < d.getPositive().size()) {
					d.setClassType(1);
				} else {
					d.setClassType(this.random.nextBoolean() ? 1 : 0);
				}
				list = getNodeList();
			}
			double accNew = DecisionTreeUtil.eval(root, validationList, output);
			if (accNew <= acc) {
				for (DecisionTreeNode n : modified) {
					n.setPureClass(false);
				}
			}
			else
				break;
		}
	}
	
	private List<DecisionTreeNode> getNodeList(){
		List<DecisionTreeNode> list = new ArrayList<>();
		Queue<DecisionTreeNode> queue = new LinkedList<>();
		queue.add(root);
		while (!queue.isEmpty()) {
			DecisionTreeNode node = queue.poll();
			if (!node.isPureClass()) {
				list.add(node);
				queue.add(node.getLeftChild());
				queue.add(node.getRightChild());
			}
		}
		return list;
	}
	
	public int getNumberOfNodes(){
		int n = 0;
		Queue<DecisionTreeNode> queue = new LinkedList<>();
		queue.add(root);
		while (!queue.isEmpty()) {
			DecisionTreeNode node = queue.poll();
			if (!node.isPureClass()) {
				queue.add(node.getLeftChild());
				queue.add(node.getRightChild());
			}
			n++;
		}
		return n;
	}
	
	public int getNumberofLeafNodes(){
		int n = 0;
		Queue<DecisionTreeNode> queue = new LinkedList<>();
		queue.add(root);
		while (!queue.isEmpty()) {
			DecisionTreeNode node = queue.poll();
			if (!node.isPureClass()) {
				queue.add(node.getLeftChild());
				queue.add(node.getRightChild());
			}
			else{
				n++;
			}
		}
		return n;
	}

	/**
	 * @return the root
	 */
	public DecisionTreeNode getRoot() {
		return root;
	}

	/**
	 * @param root the root to set
	 */
	public void setRoot(DecisionTreeNode root) {
		this.root = root;
	}
	
	public void print() {
		printUtil(this.root, 0);
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

	private void printUtil(DecisionTreeNode node, int level) {
		if (node.isPureClass()) {
			System.out.print(node.getClassType());
			System.out.println("");
			return;
		}
		System.out.println("");
		for (int i = 0; i < level; i++)
			System.out.print(" | ");

		System.out.print(node.getFeatureName() + " = 0 : ");
		printUtil(node.getRightChild(), level + 1);

		for (int i = 0; i < level; i++)
			System.out.print(" | ");
		System.out.print(node.getFeatureName() + " = 1 : ");
		printUtil(node.getLeftChild(), level + 1);
	}

	private ArrayList<Feature> featureList;
	private Feature output;
	private DecisionTreeNode root;
	private Random random;
}

class DecisionTreeNode implements Comparable {
	
	public DecisionTreeNode(Set<Integer> availableFeatures, Set<Integer> positive, Set<Integer> negative) {
		this.featureIndex = -1;
		this.positive = positive;
		this.negative = negative;
		this.availableFeatures = availableFeatures;
		this.pureClass = positive.isEmpty() || negative.isEmpty();
		if (this.pureClass)
			this.classType = negative.isEmpty() ? 1 : 0;
		this.entropy = DecisionTreeUtil.calculateEntropy(positive.size(), negative.size());
	}

	@Override
	public String toString() {
		if (bestGain != null) {
			return " " + (bestGain.getGain() - 0.69) * 10000;
		} else {
			return "0";
		}
	}
	
	@Override
	public int compareTo(Object o) {
		DecisionTreeNode decisionTreeNode = (DecisionTreeNode) o;
		if (this.bestGain.getGain() < decisionTreeNode.bestGain.getGain()) {
			return -1;
		} else if (this.bestGain.getGain() > decisionTreeNode.bestGain.getGain()) {
			return 1;
		} else {
			return 0;
		}
	}
	
	/**
	 * @return the featureIndex
	 */
	public int getFeatureIndex() {
		return featureIndex;
	}

	/**
	 * @param featureIndex the featureIndex to set
	 */
	public void setFeatureIndex(int featureIndex) {
		this.featureIndex = featureIndex;
	}

	/**
	 * @return the featureName
	 */
	public String getFeatureName() {
		return featureName;
	}

	/**
	 * @param featureName the featureName to set
	 */
	public void setFeatureName(String featureName) {
		this.featureName = featureName;
	}

	/**
	 * @return the positive
	 */
	public Set<Integer> getPositive() {
		return positive;
	}

	/**
	 * @param positive the positive to set
	 */
	public void setPositive(Set<Integer> positive) {
		this.positive = positive;
	}

	/**
	 * @return the negative
	 */
	public Set<Integer> getNegative() {
		return negative;
	}

	/**
	 * @param negative the negative to set
	 */
	public void setNegative(Set<Integer> negative) {
		this.negative = negative;
	}

	/**
	 * @return the leftChild
	 */
	public DecisionTreeNode getLeftChild() {
		return leftChild;
	}

	/**
	 * @param leftChild the leftChild to set
	 */
	public void setLeftChild(DecisionTreeNode leftChild) {
		this.leftChild = leftChild;
	}

	/**
	 * @return the rightChild
	 */
	public DecisionTreeNode getRightChild() {
		return rightChild;
	}

	/**
	 * @param rightChild the rightChild to set
	 */
	public void setRightChild(DecisionTreeNode rightChild) {
		this.rightChild = rightChild;
	}

	/**
	 * @return the bestGain
	 */
	public InformationGain getBestGain() {
		return bestGain;
	}

	/**
	 * @param bestGain the bestGain to set
	 */
	public void setBestGain(InformationGain bestGain) {
		this.bestGain = bestGain;
	}

	/**
	 * @return the availableFeatures
	 */
	public Set<Integer> getAvailableFeatures() {
		return availableFeatures;
	}

	/**
	 * @param availableFeatures the availableFeatures to set
	 */
	public void setAvailableFeatures(Set<Integer> availableFeatures) {
		this.availableFeatures = availableFeatures;
	}

	/**
	 * @return the pureClass
	 */
	public boolean isPureClass() {
		return pureClass;
	}

	/**
	 * @param pureClass the pureClass to set
	 */
	public void setPureClass(boolean pureClass) {
		this.pureClass = pureClass;
	}

	/**
	 * @return the classType
	 */
	public int getClassType() {
		return classType;
	}

	/**
	 * @param classType the classType to set
	 */
	public void setClassType(int classType) {
		this.classType = classType;
	}

	/**
	 * @return the entropy
	 */
	public double getEntropy() {
		return entropy;
	}

	/**
	 * @param entropy the entropy to set
	 */
	public void setEntropy(double entropy) {
		this.entropy = entropy;
	}

	/**
	 * @return the f
	 */
	public boolean isF() {
		return f;
	}

	/**
	 * @param f the f to set
	 */
	public void setF(boolean f) {
		this.f = f;
	}

	private int featureIndex;
	private String featureName;
	private Set<Integer> positive;
	private Set<Integer> negative;
	private DecisionTreeNode leftChild;
	private DecisionTreeNode rightChild;
	private InformationGain bestGain;
	private Set<Integer> availableFeatures;
	private boolean pureClass;
	private int classType;
	private double entropy;
	private boolean f = false;
	
}