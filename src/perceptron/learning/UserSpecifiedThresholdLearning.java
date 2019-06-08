package perceptron.learning;

import java.util.List;

import perceptron.classifier.Perceptron;

/**
 * Class responsible for taking into account a threshold as
 * training size. To use this class, you have to define the threshold.
 * As default value, the maxIterations is 0.005
 */
public class UserSpecifiedThresholdLearning extends Learning {

	/** The threshold */
	private double threshold;

	/**
	 * Constructor with threshold = 0.005
	 */
	public UserSpecifiedThresholdLearning() {
		this(0.005);
	}

	/**
	 * Constructor
	 * 
	 * @param threshold the threshold
	 */
	public UserSpecifiedThresholdLearning(double threshold) {
		this.threshold = threshold;
	}

	@Override
	public boolean isStoppingConditionReached(Perceptron perceptron, List<double[]> inputs) {

		double sum = 0.0;

		for (double[] input : inputs) {

			double output = perceptron.classify(input);
			double expected = input[input.length - 1];

			sum += Math.abs(expected - output);
		}

		double average = (double) sum / (double) inputs.size();

		if (average < threshold) {
			return true;
		}

		return false;
	}

}