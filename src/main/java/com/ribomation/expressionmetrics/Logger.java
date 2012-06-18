package com.ribomation.expressionmetrics;

/**
 * Facad around the logging framework.
 * This interface simplifies unit-testing and reduces clutter.
 *
 * @author jens
 * @date 2012-06-18
 */
public interface Logger {
    boolean isVerbose();

    /**
     * Verbose logging.
     * @param pattern   pattern as for {@link java.lang.String#format(String, Object...)}
     * @param args      arguments
     */
    void v(String pattern, Object... args);

    /**
     * Info logging.
     * @param pattern   pattern as for {@link java.lang.String#format(String, Object...)}
     * @param args      arguments
     */
    void i(String pattern, Object... args);

    /**
     * Error logging.
     * @param msg       message
     * @param error     exception
     */
    void e(String msg, Throwable error);
    
}
