package com.ribomation.expressionmetrics;

import com.ribomation.expressionmetrics.logger.IntroscopeLogger;
import com.ribomation.expressionmetrics.properties.IntroscopeAgentProperties;
import com.wily.introscope.agent.IAgent;
import com.wily.introscope.agent.service.IAgentService;
import com.wily.util.heartbeat.ITimestampedRunnable;

import java.io.File;
import java.util.Map;

/**
 * Service entry point for expression-metrics.
 * Loads expressions from a file and polls it for modifications.
 *
 * @author jens
 * @date 2012-06-19
 */
public class ExpressionMetrics implements IAgentService, ITimestampedRunnable {
    private Logger log;
    private AgentProperties agentProperties;

    @Override
    public void IAgentService_startService(IAgent agent, Map params) throws Exception {
        String name = this.getClass().getSimpleName();
        this.log = new IntroscopeLogger(name, agent);
        this.agentProperties = new IntroscopeAgentProperties(name, agent);
        ExpressionRepository.instance.setLogger(new IntroscopeLogger("ExpressionRepository", agent));
        agent.IAgent_getCommonHeartbeat().addBehavior(this, name, true, (long) (agentProperties.getInteger("file.interval.seconds", 120) * 1000), true);
        if (log.isVerbose()) log.v("Created");
    }

    @Override
    public void ITimestampedRunnable_execute(long currentTimestamp) {
        File file = agentProperties.getFile("file", "ExpressionMetrics.properties");
        if (file.isFile() && file.canRead()) {
            ExpressionRepository.instance.process(file);
        } else {
            if (log.isVerbose()) log.v("Expression file '%s' not readable", file.getAbsolutePath());
        } 
    }

    @Override
    public int IAgentService_getServiceVersion() {
        return 1;
    }
    
}
