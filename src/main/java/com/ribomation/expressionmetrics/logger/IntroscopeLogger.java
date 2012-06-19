package com.ribomation.expressionmetrics.logger;

import com.ribomation.expressionmetrics.Logger;
import com.wily.introscope.agent.IAgent;
import com.wily.util.feedback.IModuleFeedbackChannel;
import com.wily.util.feedback.SimpleModuleFeedbackChannel;

/**
 * Wraps an Introscope IModuleFeedbackChannel.
 *
 * @author jens
 * @date 2012-06-19
 */
public class IntroscopeLogger implements Logger {
    private IModuleFeedbackChannel delegate;

    public IntroscopeLogger(String name, IAgent agent) {
        this.delegate = new SimpleModuleFeedbackChannel(agent.IAgent_getModuleFeedback(), name);
    }

    @Override
    public boolean isVerbose() {
        return delegate.isVerboseEnabled();
    }

    @Override
    public void v(String pattern, Object... args) {
        delegate.verbose(String.format(pattern, args));
    }

    @Override
    public void i(String pattern, Object... args) {
        delegate.info(String.format(pattern, args));
    }

    @Override
    public void e(String msg, Throwable error) {
        delegate.error(msg, error);
    }
}
