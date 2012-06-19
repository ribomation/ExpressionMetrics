package com.ribomation.expressionmetrics.metric;

import com.ribomation.expressionmetrics.Metric;
import com.ribomation.expressionmetrics.MetricType;
import com.wily.introscope.agent.stat.DataAccumulatorFactory;
import com.wily.introscope.agent.stat.IDataAccumulator;

/**
 * Abstract base class for Introscope metrics.
 *
 * @author jens
 * @date 2012-06-19
 */
public abstract class IntroscopeMetric implements Metric {
    private IDataAccumulator metric;
    private Object lastValue = 0L;

    protected IntroscopeMetric(DataAccumulatorFactory factory, String metricName) {
        this.metric = doCreate(factory, metricName);
    }

    public static Metric create(MetricType type, String metricName, DataAccumulatorFactory factory) {
        switch (type) {
            case average: return new AverageMetric(factory, metricName);
            case perIntervalCounter: return new PerIntervalCounterMetric(factory, metricName);
        }
        throw new RuntimeException("Illegal metric-type " + type);
    }

    @Override
    public abstract MetricType getType();

    protected abstract void doAddValue(Object value);

    protected abstract IDataAccumulator doCreate(DataAccumulatorFactory factory, String metricName);

    @Override
    public final void addValue(Object value) {
        lastValue = value;
        doAddValue(value);
    }

    protected IDataAccumulator getMetric() {
        return metric;
    }

    @Override
    public String getName() {
        return getMetric().IDataAccumulator_getMetric().getAttributeName();
    }

    @Override
    public void addLastValue() {
        addValue(getLastValue());
    }

    @Override
    public Object getLastValue() {
        return lastValue;
    }

    protected long toLong(Object v) {
        if (v == null) return 0;
        if (v instanceof Number) return ((Number) v).longValue();
        try {
            return Long.parseLong(v.toString());
        } catch (Exception e) {
            return 0;
        }
    }
}
