package perceptron.learning;

import java.util.List;

import perceptron.classifier.Perceptron;

/**
 * Class responsible for defining the number of trainings. For that you have to
 * see {@link MaxIterationsLearning} and {@link UserSpecifiedThresholdLearning}
 */
public abstract class Learning {
	
	/**
	 * Return if the stopping condition was reached
	 * 
	 * @param perceptron The classifier
	 * @param inputs The sample used for training
	 * @return True is reached and False otherwise
	 */
	public abstract boolean isStoppingConditionReached(Perceptron perceptron, List<double[]> inputs);
}
