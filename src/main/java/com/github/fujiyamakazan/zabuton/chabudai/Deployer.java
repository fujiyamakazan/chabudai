package com.github.fujiyamakazan.zabuton.chabudai;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.fujiyamakazan.zabuton.runnable.RunnableJarBuilder;

public class Deployer extends RunnableJarBuilder {
    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(Deployer.class);

    public static void main(String[] args) {
        new Deployer().execute();
    }
}
