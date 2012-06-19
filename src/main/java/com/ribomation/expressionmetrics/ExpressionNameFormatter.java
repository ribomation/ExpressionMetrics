package com.ribomation.expressionmetrics;

import com.ribomation.expressionmetrics.logger.IntroscopeLogger;
import com.ribomation.expressionmetrics.target.IntroscopeTarget;
import com.ribomation.expressionmetrics.util.StopWatch;
import com.wily.introscope.agent.IAgent;
import com.wily.introscope.agent.trace.INameFormatter;
import com.wily.introscope.agent.trace.InvocationData;
import com.wily.util.StringUtils;

/**
 * Introscope Agent metric name-formatter, based on reflective expressions.
 *
 * @author jens
 * @date 2012-06-04, 2012-06-19
 */
public class ExpressionNameFormatter implements INameFormatter {
    private Logger log;
    private MetricNameFormatter fmt;

    public ExpressionNameFormatter(IAgent agent) {
        this.log = new IntroscopeLogger(this.getClass().getSimpleName(), agent);
        fmt = new MetricNameFormatter();
        if (log.isVerbose()) log.v("Created");
    }

    @Override
    public String INameFormatter_format(String metricName, InvocationData data) {
        StopWatch   sw = new StopWatch();
        try {
            if (log.isVerbose()) log.v("input: %s", metricName);
            if (StringUtils.isEmpty(metricName)) return "<empty metric definition>";

            String formattedMetricName = fmt.format(metricName, new IntroscopeTarget(data));
            if (log.isVerbose()) log.v("output: %s", formattedMetricName);
            return formattedMetricName;
        } finally {
            sw.stop();
            if (log.isVerbose()) log.v(sw.toString());
        }
    }
    
}
