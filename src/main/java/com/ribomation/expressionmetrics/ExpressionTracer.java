package com.ribomation.expressionmetrics;

import com.ribomation.expressionmetrics.expression.JexlExpression;
import com.ribomation.expressionmetrics.logger.IntroscopeLogger;
import com.ribomation.expressionmetrics.metric.IntroscopeMetric;
import com.ribomation.expressionmetrics.properties.IntroscopeAgentProperties;
import com.ribomation.expressionmetrics.properties.IntroscopeTracerProperties;
import com.ribomation.expressionmetrics.target.IntroscopeTarget;
import com.wily.introscope.agent.IAgent;
import com.wily.introscope.agent.stat.IDataAccumulator;
import com.wily.introscope.agent.trace.ASingleMetricTracerFactory;
import com.wily.introscope.agent.trace.InvocationData;
import com.wily.introscope.agent.trace.ProbeIdentification;
import com.wily.util.heartbeat.ITimestampedRunnable;
import com.wily.util.properties.AttributeListing;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Produces metric-values based on reflective method-call expressions.
 *
 * @author jens
 * @date 2012-06-19
 */
public class ExpressionTracer extends ASingleMetricTracerFactory implements ITimestampedRunnable {
    private Logger log;
    private MetricType type;
    private Metric metric;
    private Expression expression;
    private AtomicBoolean noValue = new AtomicBoolean(true);
    private long retainInterval;
    private long retainTimestamp;

    public ExpressionTracer(IAgent agent, AttributeListing tracerParams, ProbeIdentification probe, Object sample) {
        super(agent, tracerParams, probe, sample);

        this.log = new IntroscopeLogger(this.getClass().getSimpleName(), agent);
        IntroscopeAgentProperties   agentProperties  = new IntroscopeAgentProperties("ExpressionMetrics", agent);
        IntroscopeTracerProperties  tracerProperties = new IntroscopeTracerProperties(tracerParams);

        
        // . . .
        
        type = tracerProperties.getMetricType("type", MetricType.average);
        retainInterval = tracerProperties.getInteger("retain", 0) * 1000;
        if (retainInterval > 0) {
            agent.IAgent_getCommonHeartbeat().addBehavior(this, "", true, 7500, false);
            retainTimestamp = System.currentTimeMillis();
        }
    }

    @Override
    public void ITracer_startTrace(int idx, InvocationData data) {
        if (metric == null) {
            String metricName = getNameParameter();
            String[] segments = metricName.split(";");
            if (segments.length != 2) {
                throw new RuntimeException("Malformed expression metric definition '"+metricName+"'. Expected 'metric;expression'");
            }
            
            metricName = segments[0];
            String expressionText = segments[1];
            if (expressionText.startsWith("@")) {
                expressionText = expressionText.substring(1);
                expression = ExpressionRepository.instance.get(expressionText);
            } else {
                expression = new JexlExpression(metricName, expressionText);
            }
            if (expression == null) {
                expression = new JexlExpression(metricName, "$$.toString()");
            }
            
            metricName = getCustomNameFormatter().INameFormatter_format(metricName, data);
            metric     = IntroscopeMetric.create(type, metricName, getDataAccumulatorFactory());
        }
        noValue.set(false);
    }

    @Override
    public void ITracer_finishTrace(int idx, InvocationData data) {
        Object value = expression.eval(new IntroscopeTarget(data));
        if (value != null) metric.addValue(value);
    }

    @Override
    public void ITimestampedRunnable_execute(long currentTimestamp) {
        long now = System.currentTimeMillis();
        if (noValue.compareAndSet(true, true)) {
            if (now < (retainTimestamp + retainInterval)) metric.addLastValue();
        } else {
            retainTimestamp = now;
        }
    }

    @Override
    protected IDataAccumulator createDataAccumulator(String metric) {
        return null;
    }
    
}
