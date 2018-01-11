package com.cai.plugin2
import org.gradle.api.Plugin
import org.gradle.api.Project

public class SecondPlugin2 implements Plugin<Project> {

    void apply(Project project) {
        System.out.println("========================");
        System.out.println("这是一个不需要打包，直接更新的插件");
        System.out.println("========================");
    }
}