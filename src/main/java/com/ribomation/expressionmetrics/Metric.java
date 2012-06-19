package com.ribomation.expressionmetrics;

/**
 * Facad around a Introscope metric.
 *
 * @author jens
 * @date 2012-06-19
 */
public interface Metric {

    MetricType getType();

    String getName();

    void addValue(Object value);

    void addLastValue();

    Object getLastValue();
}
