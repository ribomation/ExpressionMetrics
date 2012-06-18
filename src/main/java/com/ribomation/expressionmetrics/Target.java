package com.ribomation.expressionmetrics;

/**
 * Encapsulates an instrumentation point (object/method).
 *
 * @author jens
 * @date 2012-06-04
 */
public interface Target {
    
    /**
     * Returns the target/instrumented object-
     * @return  the target
     */
    Object getObject();

    /**
     * Returns the returned value from the target method.
     * @return the returned value
     */
    Object getReturn();

    /**
     * Returns the parameter object of the target method.
     * @param idx   parameter index (starting from 1)
     * @return the parameter object
     */
    Object getParameter(int idx);

    /**
     * Returns the number of parameters.
     * @return number of available parameter objects.
     */
    int getParameterCount();

    /**
     * Returns the name of the target method.
     * @return its name
     */
    String getMethod();
}
