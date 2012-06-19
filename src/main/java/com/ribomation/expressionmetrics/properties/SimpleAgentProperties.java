package com.ribomation.expressionmetrics.properties;

import com.ribomation.expressionmetrics.AgentProperties;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Simple implementation intended for testing.
 *
 * @author jens
 * @date 2012-06-19
 */
public class SimpleAgentProperties implements AgentProperties {
    private String prefix = "";
    private File dir;
    private Map<String, Object> props = new HashMap<String, Object>();

    public SimpleAgentProperties(String prefix, File dir, Map<String, Object> props) {
        this.prefix = prefix;
        this.dir = dir;
        this.props = props;
    }

    public SimpleAgentProperties put(String name, Object value) {
        props.put(name, value);
        return this;
    }

    @Override
    public String getPrefix() {
        return prefix;
    }

    @Override
    public File getAgentDir() {
        return dir;
    }

    @Override
    public String getString(String name, String defaultValue) {
        name = getPrefix() + name;
        if (props.containsKey(name)) return (String) props.get(name);
        return defaultValue;
    }

    @Override
    public int getInteger(String name, int defaultValue) {
        name = getPrefix() + name;
        if (props.containsKey(name)) return (Integer) props.get(name);
        return defaultValue;
    }

    @Override
    public boolean getBoolean(String name, boolean defaultValue) {
        name = getPrefix() + name;
        if (props.containsKey(name)) return (Boolean) props.get(name);
        return defaultValue;
    }

    @Override
    public float getFloat(String name, float defaultValue) {
        name = getPrefix() + name;
        if (props.containsKey(name)) return (Float) props.get(name);
        return defaultValue;
    }

    @Override
    public File getFile(String name, String defaultValue) {
        name = getPrefix() + name;
        if (props.containsKey(name)) return (File) props.get(name);
        return new File(defaultValue);
    }
}
