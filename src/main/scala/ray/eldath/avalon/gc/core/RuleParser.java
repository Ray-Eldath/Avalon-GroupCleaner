package ray.eldath.avalon.gc.core;

import groovy.lang.GroovyClassLoader;
import ray.eldath.avalon.gc.model.Rule;

import java.io.File;
import java.io.IOException;

public class RuleParser {

    public static Rule parse(File ruleFile) throws IllegalAccessException, InstantiationException, IOException {
        return (Rule) new GroovyClassLoader(ClassLoader.getSystemClassLoader()).parseClass(ruleFile).newInstance();
    }
}
