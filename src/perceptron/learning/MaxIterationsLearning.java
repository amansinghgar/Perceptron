package perceptron.learning;

import java.util.List;

import perceptron.classifier.Perceptron;

/**
 * Class responsible for taking into account the number of iterations as
 * training size. To use this class, you have to define the maxIterations.
 * As default value, the maxIterations is 30
 */
public class MaxIterationsLearning extends Learning {

	/** Max number of iterations */
	private int maxIterations;

	/** The current number of iterations */
	private int count;

	/**
	 * Constructor with maxIterations = 30
	 */
	public MaxIterationsLearning() {
		this(30);
	}
	
	
	/**
	 * Constructor
	 * 
	 * @param maxIterations Max number of iterations
	 */
	public MaxIterationsLearning(int maxIterations) {
		this.count = 0;
		this.maxIterations = maxIterations;
	}

	public boolean isStoppingConditionReached(Perceptron perceptron, List<double[]> inputs) {

		this.count++;

		if (count < this.maxIterations) {
			return false;
		}

		return true;
	}
}