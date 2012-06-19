package com.ribomation.expressionmetrics.metric;

import com.ribomation.expressionmetrics.Metric;
import com.ribomation.expressionmetrics.MetricType;

/**
 * Simple metric intended for testing
 *
 * @author jens
 * @date 2012-06-19
 */
public class SimpleMetric implements Metric {
    private MetricType type;
    private String name;
    private Object lastValue;

    @Override
    public MetricType getType() {
        return type;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void addValue(Object value) {
        lastValue = value;
    }

    @Override
    public void addLastValue() {
        addValue(getLastValue());
    }

    @Override
    public Object getLastValue() {
        return lastValue;
    }
}
