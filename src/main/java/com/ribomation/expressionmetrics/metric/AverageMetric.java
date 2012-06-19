package com.ribomation.expressionmetrics.metric;

import com.ribomation.expressionmetrics.MetricType;
import com.wily.introscope.agent.stat.DataAccumulatorFactory;
import com.wily.introscope.agent.stat.IDataAccumulator;
import com.wily.introscope.agent.stat.ILongAverageDataAccumulator;

/**
 * Aggregates the average value of an Introscope interval (15s).
 *
 * @author jens
 * @date 2012-06-19
 */
public class AverageMetric extends IntroscopeMetric {
    public AverageMetric(DataAccumulatorFactory factory, String metricName) {
        super(factory, metricName);
    }

    @Override
    public MetricType getType() {
        return MetricType.average;
    }

    @Override
    protected IDataAccumulator doCreate(DataAccumulatorFactory f, String name) {
        return f.safeGetLongAverageDataAccumulator(name);
    }

    @Override
    protected void doAddValue(Object value) {
        ((ILongAverageDataAccumulator) getMetric()).ILongAggregatingDataAccumulator_recordDataPoint(toLong(value));
    }

}
