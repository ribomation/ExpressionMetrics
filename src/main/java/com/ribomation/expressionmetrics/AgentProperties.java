package com.ribomation.expressionmetrics;

import java.io.File;

/**
 * Facad to the agent configuration properties.
 *
 * @author jens
 * @date 2012-06-19
 */
public interface AgentProperties {
    /**
     * Returns the common prefix for all properties.
     * @return its property name prefix
     */
    String getPrefix();

    /**
     * Returns the directory where the agent-profile is installed.
     * @return its dir
     */
    File getAgentDir();

    /**
     * Returns a string property from the agent profile.
     * @param name              its name
     * @param defaultValue      if not found
     * @return                  its value
     */
    String getString(String name, String defaultValue);

    /**
     * Returns a property converted to an integer, from the agent profile.
     * @param name              its name
     * @param defaultValue      if not found
     * @return                  its value
     */
    int getInteger(String name, int defaultValue);

    /**
     * Returns a property converted to a boolean, from the agent profile.
     * @param name              its name
     * @param defaultValue      if not found
     * @return                  its value
     */
    boolean getBoolean(String name, boolean defaultValue);

    /**
     * Returns a property converted to a float, from the agent profile.
     * @param name              its name
     * @param defaultValue      if not found
     * @return                  its value
     */
    float getFloat(String name, float defaultValue);

    /**
     * Returns a property converted to a File object, from the agent profile.
     * @param name              its name
     * @param defaultValue      if not found
     * @return                  its value
     */
    File getFile(String name, String defaultValue);
    
}
