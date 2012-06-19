package com.ribomation.expressionmetrics.metric;

import com.ribomation.expressionmetrics.MetricType;
import com.wily.introscope.agent.stat.DataAccumulatorFactory;
import com.wily.introscope.agent.stat.IDataAccumulator;
import com.wily.introscope.agent.stat.ILongIntervalCounterDataAccumulator;

/**
 * Counts the number of invocations per Introscope interval (15s).
 *
 * @author jens
 * @date 2012-06-19
 */
public class PerIntervalCounterMetric extends IntroscopeMetric {
    protected PerIntervalCounterMetric(DataAccumulatorFactory factory, String metricName) {
        super(factory, metricName);
    }

    @Override
    public MetricType getType() {
        return MetricType.perIntervalCounter;
    }

    @Override
    protected IDataAccumulator doCreate(DataAccumulatorFactory f, String name) {
        return f.safeGetLongIntervalCounterDataAccumulator(name);
    }

    @Override
    protected void doAddValue(Object value) {
        if (value != null) {
            ((ILongIntervalCounterDataAccumulator) getMetric()).ILongIntervalCounterDataAccumulator_addBatchIncidents(toLong(value));
        } else {
            ((ILongIntervalCounterDataAccumulator) getMetric()).ILongIntervalCounterDataAccumulator_addSingleIncident();
        }
    }
}
