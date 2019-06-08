package perceptron.classifier;

import java.util.List;

import perceptron.learning.Learning;
import perceptron.learning.MaxIterationsLearning;

public class Perceptron {

	/* Learning rate */
	private double lRate = 0.0020;

	/* The Weights */
	private double[] weights;
	
	/* Information without attributes */
	private double bias =10;
	
	/* Define the stopping criteria for training phase */
	private Learning learning;
	
	public Perceptron(double rate)
	{
		lRate=rate;
		
	}

	/* Constructor */
	public Perceptron() {
		this(8, new MaxIterationsLearning(50));
	}

	/* Constructor @param dimensions the size of the weights */
	public Perceptron(int dimensions, Learning learning) {
		this.weights = new double[dimensions];
		this.learning = learning;
		for(int i=0;i<dimensions;i++)
		{
			this.weights[i]=3.90;
		}
	}
	
	public void train(List<double[]> inputs) {

		for (double[] input : inputs) {

			double output = classify(input);
			double expected = input[input.length - 1]; //array start from 0

			if (output != expected) {

				updateWeightsAndBias(input, expected, output);
			}

		}
		
		if( ! learning.isStoppingConditionReached(this, inputs)) {
			train(inputs);
		}
	}
	
	protected void updateWeightsAndBias(double[] input, double expected, double output) {

		double error = (expected - output);
		
		for (int i = 0; i < input.length-1; i++) {

			weights[i] += (lRate * (error) * input[i]);
		}

		bias += lRate * error;
	}
	
	public  double classify(double[] input) {

		double result = 0.0;
		for (int i = 0; i < input.length - 1; i++) {
			
			result += input[i] * weights[i];
		}

		result += bias;

		return result;
	}

	public double getLRate() {
		return lRate;
	}

	public void setLRate(double lRate) {
		this.lRate = lRate;
	}

	public Learning getLearning() {
		return learning;
	}

	public void setLearning(Learning learning) {
		this.learning = learning;
	}


}