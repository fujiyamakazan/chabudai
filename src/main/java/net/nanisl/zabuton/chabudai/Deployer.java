package net.nanisl.zabuton.chabudai;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.nanisl.zabuton.tool.RunnableJarBuilder;

public class Deployer extends RunnableJarBuilder {
    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(Deployer.class);

    public static void main(String[] args) {
        new Deployer().execute();
    }
}
