/**
 * Copyright (c) 2012, University of California
 * All rights reserved.
 * 
 * Redistribution and use is governed by the LICENSE.txt file included with this
 * source code and available at http://leeps.ucsc.edu/cong/wiki/license
 **/
package edu.ucsc.leeps.fire.config;

import au.com.bytecode.opencsv.CSVReader;
import edu.ucsc.leeps.fire.reflection.ClassFinder;
import edu.ucsc.leeps.fire.reflection.ObjectMapper;
import java.io.*;
import java.util.*;

/**
 * This class store and manages the config types.  This class also contains methods
 * to read in the values from a comfig file and load the config typse from the 
 * keys using csv readder and object mapper instances.
 * @author jpettit
 * @see edu.ucsc.leeps.fire.reflection.ObjectMapper
 */
public class Configurator<ConfigType extends BaseConfig> {

    private int periodListIndex;
    private List<String> periods;
    private Map<String, ConfigType> defaultConfigs;
    private Map<Integer, Map<String, ConfigType>> configs;
    private ConfigStore defaultStore, subjectStore;
    private String configSource;
    public boolean loaded;

    /**
     * Creates a hashMap to get types of Configurator. Uses defaultConfigs to
     * determine initial conditions as established in FIRE. Additionally, gets
     * classes for configs using other programs.
     *
     * Sets initial period number to 1.
     */
    public Configurator() {
        periods = new ArrayList<String>();
        defaultConfigs = new HashMap<String, ConfigType>();
        configs = new HashMap<Integer, Map<String, ConfigType>>();
        periodListIndex = 0;
    }
    
    /**
     * returns the list of all the periods
     * @return 
     */
    public List<String> getPeriods() {
        List<String> tmp = new ArrayList<String>();
        for (String period : periods) {
            tmp.add(period.toString());
        }
        return tmp;
    }

    /**
     * Sets the period number in this configurator.
     * @param periodNum
     */
    public boolean setPeriod(String period) {
        int i = 0;
        for (String key : periods) {
            if (key.equals(period)) {
                periodListIndex = i;
                break;
            }
            i++;
        }
        return defaultConfigs.containsKey(period);
    }

    /**
     * Returns the type of the current default config
     * @return the type of the current default config
     */
    public ConfigType getConfig() {
        return defaultConfigs.get(periods.get(periodListIndex));
    }

    /**
     * If there is more than 1 period, and if there is no ID, add an ID to
     * configurator. If there is no period number, clone, get period number and
     * change in configurator. Return id and period number. If there are no more
     * periods, assert false, and return null.
     *
     * @param id identification for subject
     * @return configs for id and period number
     */
    public ConfigType getConfig(int id) {
        if (!configs.containsKey(id)) {
            configs.put(id, new HashMap<String, ConfigType>());
        }
        if (!configs.get(id).containsKey(periods.get(periodListIndex))) {
            ConfigType clone = clone(defaultConfigs.get(periods.get(periodListIndex)));
            configs.get(id).put(periods.get(periodListIndex), clone);
        }
        return configs.get(id).get(periods.get(periodListIndex));
    }
    
    /**
     * Returns a linked list containing all of the config types defined for the 
     * current period
     * @return a linked list containing all of the config types defined for the 
     * current period
     */
    public List<ConfigType> getDefinedConfigs() {
        List<ConfigType> definedConfigs = new LinkedList<ConfigType>();
        for (int id : configs.keySet()) {
            if (configs.get(id).containsKey(periods.get(periodListIndex))) {
                definedConfigs.add(configs.get(id).get(periods.get(periodListIndex)));
            }
        }
        return definedConfigs;
    }
    
    /**
     * returns the path to the config file
     * @return the path to the config file
     */
    public String getConfigSource() {
        return configSource;
    }
    
    /**
     * returns a map of all the config types for the current period list index
     * @return a map of all the config types for the current period list index
     */
    public Map<Integer, ConfigType> getConfigs() {
        Map<Integer, ConfigType> c = new HashMap<Integer, ConfigType>();
        for (int id : configs.keySet()) {
            c.put(id, configs.get(id).get(periods.get(periodListIndex)));
        }
        return c;
    }
    /**
     * returns a map of all of the default config types
     * @return a map of all of the default config types
     */
    public Map<String, ConfigType> getDefaultConfigs() {
        return defaultConfigs;
    }

    /**
     * Determine what period the experiment is in.
     *
     * @return period number
     */
    public String getPeriod() {
        return periods.get(periodListIndex);
    }
    
    /**
     * Returns the number of the period that came previously
     * @return the number of the period that came previously
     */
    public String getPrevPeriod() {
        return periods.get(periodListIndex - 1);
    }

    /**
     * Go to the next period
     */
    public void nextPeriod() {
        periodListIndex++;
    }
    
    /**
     * Go to the period at the passed index
     * @param i the period's index
     */
    public void setPeriodListIndex(int i) {
        periodListIndex = i;
    }

    /**
     * Allows more periods only if less than total allowable number of periods
     * as defined by defaultConfigs. Number of period MUST be less than
     * specified in defaultConfigs.
     *
     * @return
     */
    public boolean hasMorePeriods() {
        return periodListIndex < periods.size();
    }
    
    /**
     * Attempts to load the config types from a different configurator instance.
     * @param configurator
     * @throws edu.ucsc.leeps.fire.config.BaseConfig.ConfigException 
     */
    public void load(Configurator<ConfigType> configurator) throws BaseConfig.ConfigException {
        periods.clear();
        defaultStore = configurator.defaultStore;
        subjectStore = configurator.subjectStore;
        try {
            load();
        } catch (ObjectMapper.ObjectMapException ex) {
            throw new BaseConfig.ConfigException(ex.getMessage());
        }
        for (ConfigType config : defaultConfigs.values()) {
            config.test();
        }
    }
    
    /**
     * REads the confg file and stores the keys and values into a map
     * @param configFile a file object for the config file
     * @throws IOException 
     */
    public void setConfigFile(File configFile) throws IOException {
        this.configSource = configFile.getAbsolutePath();
        ConfigStore curr = null;
        CSVReader reader = new CSVReader(new FileReader(configFile), ',');
        String[] line;
        while ((line = reader.readNext()) != null) {
            if (curr == null || line[0].contains("SUBJECTS")) {
                if (line[0].contains("SUBJECTS")) {
                    line = reader.readNext();
                    subjectStore = new ConfigStore();
                    curr = subjectStore;
                } else {
                    defaultStore = new ConfigStore();
                    curr = defaultStore;
                }
                curr.keys = line;
                curr.rows = new LinkedList<String[]>();
                line = reader.readNext();
            }
            curr.rows.add(line);
        }
    }
    
    private ConfigType clone(ConfigType base) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(base);
            oos.flush();
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
            ConfigType clone = (ConfigType) ois.readObject();
            return clone;
        } catch (ClassNotFoundException ex) {
            return null;
        } catch (IOException ex) {
            return null;
        }
    }

    /**
     * Loads in the config types from the keys loaded by calls to 
     * <code>setConfigFile(File configFile)</code> using the load method in the
     * Object mapper class
     * @throws edu.ucsc.leeps.fire.reflection.ObjectMapper.ObjectMapException 
     */
    private void load() throws ObjectMapper.ObjectMapException {
        for (int row = 0; row < defaultStore.rows.size(); row++) {
            ConfigType config = (ConfigType) ClassFinder.newConfig();
            ObjectMapper.load(config, row, defaultStore);
            defaultConfigs.put(config.period, config);
            periods.add(config.period);
        }
        if (subjectStore != null) {
            for (int row = 0; row < subjectStore.rows.size(); row++) {
                ConfigType config = (ConfigType) ClassFinder.newConfig();
                ObjectMapper.load(config, row, subjectStore);
                ConfigType subjectConfig = clone(defaultConfigs.get(config.period));
                ObjectMapper.load(subjectConfig, row, subjectStore);
                if (!configs.containsKey(subjectConfig.subject)) {
                    configs.put(subjectConfig.subject, new HashMap<String, ConfigType>());
                }
                configs.get(subjectConfig.subject).put(subjectConfig.period, subjectConfig);
            }
        }
    }
    
    /**
     * Convenience class for storing the contents of a csv file
     */
    public static class ConfigStore implements Serializable {

        public String[] keys;
        public List<String[]> rows;
    }
}
