package com.ribomation.expressionmetrics;

/**
 * Discards all log outputs.
 *
 * @author jens
 * @date 2012-06-18
 */
public class NullLogger implements Logger {
    @Override
    public boolean isVerbose() {
        return false;
    }

    @Override
    public void v(String pattern, Object... args) {

    }

    @Override
    public void i(String pattern, Object... args) {

    }

    @Override
    public void e(String msg, Throwable error) {

    }
}
