package com.city81.interpolation.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * This class provides a method to linear interpolate a series of numbers.
 * 
 * @author Geraint Jones
 */
public class InterpolateService {

	static final String VALUE_ARG_NOT_NULL_EXCP_TEXT 
		= "value arguments cannot be null.";
	static final String STEPS_ARG_NOT_VALID_EXCP_TEXT 
		= "steps argument cannot be null or less than one.";
	static final String VALUE_LIST_NOT_VALID_EXCP_TEXT 
		= "value list cannot be null or contain less than two values.";

	// parallel task executor
	private final ForkJoinPool forkJoinPool = new ForkJoinPool();

	/**
	 * Submits two values and the number of steps to be interpolated between
	 * them.
	 * 
	 * @param valueOne
	 *            the first value of the number series
	 * @param valueTwo
	 *            the last value of the number series
	 * @param steps
	 *            the number of steps to be interpolated
	 * @return a linear interpolated list of numbers
	 * @throws IllegalArgumentException
	 *             if any of the arguments are null, or 
	 *             if the steps arg is less than one
	 */
	public List<Double> interpolate(Double valueOne, Double valueTwo,
			Integer steps) throws IllegalArgumentException {

		if ((valueOne == null) || (valueTwo == null)) {
			throw new IllegalArgumentException(VALUE_ARG_NOT_NULL_EXCP_TEXT);
		}
		if ((steps == null) || (steps < 1)) {
			throw new IllegalArgumentException(STEPS_ARG_NOT_VALID_EXCP_TEXT);
		}

		ForkJoinTask<List<Double>> job = forkJoinPool.submit(
				new InterpolateTask(valueOne, valueTwo, steps));
		return job.join();
	}

	/**
	 * Submits a list of values and the number of steps to be interpolated
	 * between each value.
	 * 
	 * @param valueList
	 *            the list of values to be interpolated
	 * @param steps
	 *            the number of steps to be interpolated
	 * @return a linear interpolated list of numbers
	 * @throws IllegalArgumentException
	 *             if the value list is null or has less than two elements, or 
	 *             if the steps arg is null or less than one
	 */
	public List<Double> interpolate(List<Double> valueList, Integer steps)
			throws IllegalArgumentException {

		if ((valueList == null) || (valueList.size() < 2)) {
			throw new IllegalArgumentException(VALUE_LIST_NOT_VALID_EXCP_TEXT);
		}
		if ((steps == null) || (steps < 1)) {
			throw new IllegalArgumentException(STEPS_ARG_NOT_VALID_EXCP_TEXT);
		}

		List<Double> interpolatedList = new ArrayList<Double>();

		for (int firstPairValuePos = 0; firstPairValuePos < (valueList.size() - 1); firstPairValuePos++) {
			// remove last value which will be overwritten in the addAll
			if (firstPairValuePos != 0) {
				interpolatedList.remove(interpolatedList.size() - 1);
			}
			interpolatedList.addAll(interpolate(
					valueList.get(firstPairValuePos),
					valueList.get(firstPairValuePos + 1), steps));
		}

		return interpolatedList;
	}

	/**
	 * A recursive task to subdivide the interpolation of two values until the
	 * lowest unit of work is reached.
	 */
	private static class InterpolateTask extends RecursiveTask<List<Double>> {

		private static final long serialVersionUID = 1L;
		
		private final double valueOne;
		private final double valueTwo;
		private final int steps;
		private static final int LOWEST_STEP = 2;

		private InterpolateTask(double valueOne, double valueTwo, int steps) {
			this.valueOne = valueOne;
			this.valueTwo = valueTwo;
			this.steps = steps;
		}

		/**
		 * Subdivide the unit of work to the smallest part then build up the
		 * interpolated list of numbers by joining the results of subdivided
		 * tasks
		 * 
		 * @return a linear interpolated list of numbers
		 */
		@Override
		protected List<Double> compute() {

			List<Double> interpolatedArray = new ArrayList<Double>();

			// check if reached lowest unit of work ie a number not divisable 
			// by two or steps is equal to two
			if ((this.steps % 2 != 0) || (this.steps == LOWEST_STEP)) {
				
				interpolatedArray.add(valueOne);

				double diff = valueTwo - valueOne;
				double intervalValue = diff / steps;
				
				for (int i=1;i<steps;i++) {
					interpolatedArray.add(valueOne + (i * intervalValue));
				}
				interpolatedArray.add(valueTwo);
				
			} else {
				double interpolatedValue = interpolate(valueOne, valueTwo);

				InterpolateTask interpolateTask1 = new InterpolateTask(
						valueOne, interpolatedValue, (steps / 2));
				interpolateTask1.fork();
				InterpolateTask interpolateTask2 = new InterpolateTask(
						interpolatedValue, valueTwo, (steps / 2));
				List<Double> interpolatedArrayTask2 = interpolateTask2.compute();
				List<Double> interpolatedArrayTask1 = interpolateTask1.join();
				interpolatedArray.addAll(interpolatedArrayTask1.subList(0,
						interpolatedArrayTask1.size() - 1));
				interpolatedArray.addAll(interpolatedArrayTask2);
			}
			return interpolatedArray;
		}

		/**
		 * For the two supplied values return the mid point
		 * 
		 * @param valueOne
		 *            the first value of the number series
		 * @param valueTwo
		 *            the last value of the number series
		 * @return mid point of the two values
		 */
		private double interpolate(double valueOne, double valueTwo) {
			return ((valueTwo - valueOne) / 2.0) + valueOne;
		}

	}	
	
}
