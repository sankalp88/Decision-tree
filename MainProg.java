public class MainProg {

	public static void main(String[] args) throws Exception {

		DataContainer trainingSet = IOHelper.readFile(args[0]);
		DataContainer validationSet = IOHelper.readFile(args[1]);
		DataContainer testSet = IOHelper.readFile(args[2]);
		double pruneFactor = 0;
		try{
			pruneFactor = Double.parseDouble(args[3]);
		} catch (Exception e) {
			throw new Exception("The Arguments passed are of invalid type");
		}

		DecisionTree tree = new DecisionTree(trainingSet.getFeatureList(), trainingSet.getOutput());
		tree.generateTree();
		System.out.println("Pre-Prune:");
		System.out.println("Total number of nodes in the tree = " + tree.getNumberOfNodes());
		System.out.println("Number	of	leaf	nodes	in	the	tree = " + tree.getNumberofLeafNodes());
		printUtil(tree, trainingSet, "training"); 
		printUtil(tree, validationSet, "validation");
		printUtil(tree, testSet, "test");
		System.out.println();
		System.out.println();
		System.out.println("tree plot");
		tree.print();
		tree.pruneTree(pruneFactor , validationSet.getFeatureList(), validationSet.getOutput());
		System.out.println();
		System.out.println();
		System.out.println("Post-Prune:");
		System.out.println("Total number of nodes in the tree = " + tree.getNumberOfNodes());
		System.out.println("Number	of	leaf	nodes	in	the	tree = " + tree.getNumberofLeafNodes());
		printUtil(tree, trainingSet, "training"); 
		printUtil(tree, validationSet, "validation");
		printUtil(tree, testSet, "test");
		System.out.println();
		System.out.println();
		System.out.println("tree plot");
		tree.print();
	}
	
	static void printUtil(DecisionTree tree, DataContainer dc, String name){
		System.out.println("Number of " + name + " instances = " + dc.getNumberOfInstances());
		System.out.println("Number of " + name + " attributes = " + dc.getNumberOfFeatures());
		double acc = 100 * DecisionTreeUtil.eval(tree.getRoot(), dc.getFeatureList(), dc.getOutput());
		System.out.println("Accuracy of the model on the " + name + " dataset = " + acc + "%");	
	}

}
