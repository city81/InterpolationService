With the release of Java 7 came the new Executor Service, ForkJoinPool. The class uses the notion of work-stealing threads which execute tasks which have been created by other tasks. Ultimately, this division of work will result in large problems becoming a series of smaller ones which will eventually result in one answer when all the tasks have completed their processing. 

This project will demonstrate this new threading feature by an interpolation example. In this case, for two numbers with a given number of steps, the code will attempt to construct new points between those numbers using linear interpolation, and for simplicity using only a number of steps divisible by 2. eg for the numbers 5 and 25 with a step number of 4, the generated sequence would be:

[5.0, 10.0, 15.0, 20.0, 25.0]

TODO

1. Check in tests

2. Change to handle odd number of steps

3. Change to handle ints ie rounding interpolated values

4. Change to handle different types of interpolation other than linear  