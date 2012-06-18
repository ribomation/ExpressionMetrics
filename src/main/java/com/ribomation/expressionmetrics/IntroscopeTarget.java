package com.ribomation.expressionmetrics;

import com.wily.introscope.agent.trace.InvocationData;

/**
 * Wrapper around an InvocationData object.
 *
 * @author jens
 * @date 2012-06-04
 */
public class IntroscopeTarget implements Target {
    private InvocationData delegate;

    public IntroscopeTarget(InvocationData delegate) {
        this.delegate = delegate;
    }

    @Override
    public Object getObject() {
        return delegate.getInvocationObject();
    }

    @Override
    public Object getReturn() {
        return delegate.getInvocationReturnValueAsObject();
    }

    @Override
    public Object getParameter(int idx) {
        return delegate.getInvocationParameterAsObject(idx - 1);
    }

    @Override
    public int getParameterCount() {
        return delegate.getInvocationParameterCount();
    }

    @Override
    public String getMethod() {
        return delegate.getProbeInformation().getProbeIdentification().getProbeMethodName();
    }

}
