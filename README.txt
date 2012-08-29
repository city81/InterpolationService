With the release of Java 7 came the new Executor Service, ForkJoinPool. The class uses the notion of work-stealing threads which execute tasks which have been created by other tasks. Ultimately, this division of work will result in large problems becoming a series of smaller ones which will eventually result in one answer when all the tasks have completed their processing. 

This project (based on the blog post http://city81.blogspot.co.uk/2012/01/parallel-processing-with-forkjoinpool.html) will demonstrate this new threading feature by a simple linear interpolation example. 

eg for values 5.0 and 25.0 with 4 stpes will result in a list as follows: [5.0, 10.0, 15.0, 20.0, 25.0]

TODO

1. Change to handle ints ie rounding interpolated values

2. Change to handle different types of interpolation other than linear  