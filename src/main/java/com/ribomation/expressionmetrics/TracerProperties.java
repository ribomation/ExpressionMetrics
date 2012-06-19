package com.ribomation.expressionmetrics;

/**
 * Wrapper around a set of tracer configuration parameters.
 *
 * @author jens
 * @date 2012-06-19
 */
public interface TracerProperties {

    /**
     * Returns tracer parameter value.
     * @param name              its name
     * @param defaultValue      when not found
     * @return                  its value
     */
    String getString(String name, String defaultValue);

    /**
     * Returns tracer parameter value, converted to an integer.
     * @param name              its name
     * @param defaultValue      when not found
     * @return                  its value
     */
    int getInteger(String name, int defaultValue);

    /**
     * Returns tracer parameter value, converted to a boolean.
     * @param name              its name
     * @param defaultValue      when not found
     * @return                  its value
     */
    boolean getBoolean(String name, boolean defaultValue);

    MetricType getMetricType(String name, MetricType defaultValue);
    
}
