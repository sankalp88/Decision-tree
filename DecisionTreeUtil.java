

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class DecisionTreeUtil {

	public DecisionTreeUtil() {
		super();
	}
	
/*	public static double findaccuracy(DecisionTree tree){
		return 100 * eval(tree.getRoot(),);
	}
*/	
	public static double eval(DecisionTreeNode root, ArrayList<Feature> features, Feature output) {
		int j = 0;
		for (int i = 0; i < output.getValue().size(); i++) {
			int p = evalUtil(root, features, i);
			if (p != output.getValue().get(i)){
				j++;
			}
		}
		return 1 - (double) j / output.getValue().size();
	}

	private static int evalUtil(DecisionTreeNode root, List<Feature> features, int i) {
		if (root.isPureClass())
			return root.getClassType();
		if (root.getFeatureIndex() < 0) {
			System.out.println(root);
		}
		if (features.get(root.getFeatureIndex()).getValue().get(i) == 1)
			return evalUtil(root.getLeftChild(), features, i);
		return evalUtil(root.getRightChild(), features, i);
	}
	
	public static DecisionTreeNode chooseFeature(DecisionTreeNode decisionTreeNode, ArrayList<Feature> featureList) {
		double max = 0;
		if (!decisionTreeNode.isPureClass() && decisionTreeNode.getBestGain() == null) {
			decisionTreeNode.setBestGain(new InformationGain(0, null, null, null, null));
			for (int i : decisionTreeNode.getAvailableFeatures()) {
				InformationGain ig = calculateInformation(decisionTreeNode, featureList.get(i));
				if (ig.getGain() > max) {
					max = ig.getGain();
					decisionTreeNode.setBestGain(ig);
					decisionTreeNode.setFeatureIndex(i);
					decisionTreeNode.setFeatureName(featureList.get(i).getName());
				}
			}
		}
		return decisionTreeNode;
	}
	
	public static InformationGain calculateInformation(DecisionTreeNode decisionTreeNode, Feature feature) {
		int x1 = 0, x2 = 0, x3 = 0, x4 = 0;
		HashSet<Integer> pp = new HashSet<>();
		HashSet<Integer> pn = new HashSet<>();
		HashSet<Integer> np = new HashSet<>();
		HashSet<Integer> nn = new HashSet<>();

		for (int i : decisionTreeNode.getPositive()) {
			if (feature.getValue().get(i) == 1) {
				pp.add(i);
				x1++;
			} else {
				np.add(i);
				x3++;
			}
		}
		for (int i : decisionTreeNode.getNegative()) {
			if (feature.getValue().get(i) == 1) {
				pn.add(i);
				x2++;
			} else {
				nn.add(i);
				x4++;
			}
		}
		double p = (double) (x1 + x2) / (x1 + x2 + x3 + x4);
		return new InformationGain(decisionTreeNode.getEntropy() - p * calculateEntropy(x1, x2) - (1 - p) * calculateEntropy(x3, x4), pp, pn,
				np, nn);
	}
	
	public static double calculateEntropy(int pos, int neg) {
		if (pos == 0 || neg == 0){
			return 0;
		}
		double p = (double) pos / (pos + neg);
		return -(p * Math.log(p) + (1 - p) * Math.log(1 - p));
	}

}
