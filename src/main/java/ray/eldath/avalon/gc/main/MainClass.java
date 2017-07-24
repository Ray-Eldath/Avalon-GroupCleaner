package ray.eldath.avalon.gc.main;

import ray.eldath.avalon.gc.core.RuleParser;

import java.io.File;
import java.io.IOException;

public class MainClass {
    public static void main(String[] args) throws IOException, InstantiationException, IllegalAccessException {
        System.out.println(RuleParser.parse(new File("F:\\Code\\Project\\Avalon-GroupCleaner\\rule\\groovy\\_rule.groovy")).toString());
    }
}
