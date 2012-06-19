package com.ribomation.expressionmetrics.util;

/**
 * Keeps track of the elapsed time of a task.
 *
 * @author jens
 * @date 2012-06-19
 */
public class StopWatch {
    private long    start, elapsed;

    public StopWatch() {
        start();
    }
    
    public void start() {
        start = System.nanoTime();
        elapsed = 0;
    }
    
    public long stop() {
        elapsed = System.nanoTime() - start;
        return elapsed;
    }
    
    public double elapsedAsSeconds() {
        return elapsed * 1E-9;
    }
    
    public double elapsedAsMilliseconds() {
        return elapsed * 1E-6;
    }

    @Override
    public String toString() {
        return String.format("elapsed %.3f ms", elapsedAsMilliseconds());
    }
    
}
