package com.ribomation.expressionmetrics.properties;

import com.ribomation.expressionmetrics.AgentProperties;
import com.wily.introscope.agent.IAgent;
import com.wily.util.properties.IndexedProperties;

import java.io.File;

/**
 * Wrapper around an Introscope IndexedProperties object.
 *
 * @author jens
 * @date 2012-06-19
 */
public class IntroscopeAgentProperties implements AgentProperties {
    private String prefix = "";
    private IAgent agent;
    private IndexedProperties delegate;

    public IntroscopeAgentProperties(String prefix, IAgent agent) {
        this.prefix = prefix;
        this.agent = agent;
        this.delegate = agent.IAgent_getIndexedProperties();
    }

    @Override
    public String getPrefix() {
        return prefix;
    }

    @Override
    public File getAgentDir() {
        return new File(agent.IAgent_getConfigurationResource().IResource_getLocation()).getParentFile();
    }

    @Override
    public String getString(String name, String defaultValue) {
        return delegate.getTrimmedProperty(getPrefix() + name, defaultValue);
    }

    @Override
    public int getInteger(String name, int defaultValue) {
        return delegate.getIntProperty(getPrefix() + name, defaultValue);
    }

    @Override
    public boolean getBoolean(String name, boolean defaultValue) {
        return delegate.getBooleanProperty(getPrefix() + name, defaultValue);
    }

    @Override
    public float getFloat(String name, float defaultValue) {
        try {
            return Float.parseFloat(delegate.getTrimmedProperty(getPrefix() + name, Float.toString(defaultValue)));
        } catch (Exception x) {
            return defaultValue;
        }
    }

    /**
     * Returns a file object for the given property.
     * If the file-name not absolute, its given relative to the agent profile directory.
     * @param name              its name
     * @param defaultValue      if not found
     * @return                  its value
     */
    @Override
    public File getFile(String name, String defaultValue) {
        try {
            File file = new File(delegate.getTrimmedProperty(getPrefix() + name, defaultValue));
            if (file.isAbsolute()) return file;
            return new File(getAgentDir(), file.toString());
        } catch (Exception e) {
            return new File(defaultValue);
        }
    }
    
}
