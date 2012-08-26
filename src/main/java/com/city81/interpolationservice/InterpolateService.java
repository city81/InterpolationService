package com.city81.interpolationservice;

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
 
    // parallel task executor
    private final ForkJoinPool forkJoinPool = new ForkJoinPool();

    /**
     * Submits two values and the number of steps to be interpolated between 
     * them.
     * 
     * @param valueOne the first value of the number series
     * @param valueTwo the last value of the number series
     * @param steps the number of steps to be interpolated
     * @return a linear interpolated list of numbers 
     */
    public List<Double> interpolate(double valueOne, double valueTwo, int steps) {
        ForkJoinTask<List<Double>> job = forkJoinPool.submit(
            new InterpolateTask(valueOne, valueTwo, steps));
        return job.join();
    }
 
    /**
     * A recursive task to subdivide the interpolation of two values until
     * the lowest unit of work is reached.
     */
    private static class InterpolateTask extends RecursiveTask<List<Double>> {
 
        private final double valueOne;
        private final double valueTwo;
        private final int steps;
        private static final int LOWEST_STEP = 2;
 
        protected InterpolateTask(double valueOne, double valueTwo, int steps) {
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
     
            List<Double> interpolatedArray = new ArrayList<>();
            double interpolatedValue = interpolate(valueOne, valueTwo);
     
            if (this.steps == LOWEST_STEP) {
                interpolatedArray.add(valueOne);
                interpolatedArray.add(interpolatedValue);
                interpolatedArray.add(valueTwo);
            } else {
                InterpolateTask interpolateTask1 = 
                    new InterpolateTask(valueOne, interpolatedValue, (steps/2));
                interpolateTask1.fork();
                InterpolateTask interpolateTask2 = 
                    new InterpolateTask(interpolatedValue, valueTwo, (steps/2));
                List<Double> interpolatedArrayTask2 = interpolateTask2.compute();
                List<Double> interpolatedArrayTask1 = interpolateTask1.join();
                interpolatedArray.addAll(
                    interpolatedArrayTask1.subList(
                        0, interpolatedArrayTask1.size() - 1));
                interpolatedArray.addAll(interpolatedArrayTask2);
            }
            return interpolatedArray;
        }
 
        /**
         * For the two supplied values return the mid point
         * 
         * @param valueOne the first value of the number series
         * @param valueTwo the last value of the number series
         * @return mid point of the two values
         */
        private double interpolate(double valueOne, double valueTwo) {
            return ((valueTwo - valueOne) / 2.0) + valueOne;
        }
 
    }
 
}
