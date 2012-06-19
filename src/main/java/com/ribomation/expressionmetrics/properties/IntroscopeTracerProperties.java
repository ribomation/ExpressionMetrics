package com.ribomation.expressionmetrics.properties;

import com.ribomation.expressionmetrics.MetricType;
import com.ribomation.expressionmetrics.TracerProperties;
import com.wily.util.StringUtils;
import com.wily.util.properties.AttributeListing;

/**
 * Wrapper around a {@link com.wily.util.properties.AttributeListing} object.
 *
 * @author jens
 * @date 2012-06-19
 */
public class IntroscopeTracerProperties implements TracerProperties {
    private AttributeListing delegate;

    public IntroscopeTracerProperties(AttributeListing delegate) {
        this.delegate = delegate;
    }

    @Override
    public String getString(String name, String defaultValue) {
        String value = delegate.get(name);
        if (StringUtils.isEmpty(value)) return defaultValue;
        return value;
    }

    @Override
    public int getInteger(String name, int defaultValue) {
        String value = delegate.get(name);
        if (StringUtils.isEmpty(value)) return defaultValue;
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) { return defaultValue; }
    }

    @Override
    public boolean getBoolean(String name, boolean defaultValue) {
        String value = delegate.get(name);
        if (StringUtils.isEmpty(value)) return defaultValue;
        return value.equalsIgnoreCase("true");
    }

    @Override
    public MetricType getMetricType(String name, MetricType defaultValue) {
        String value = delegate.get(name);
        if (StringUtils.isEmpty(value)) return defaultValue;
        try {
            return MetricType.valueOf(value);
        } catch (Exception e) { return defaultValue; }
    }

}
