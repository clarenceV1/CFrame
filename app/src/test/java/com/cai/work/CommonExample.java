package com.cai.work;

import java.io.File;

public class CommonExample {
    private boolean isExist() {
        return false;
    }

    public boolean callPrivateMethod() {
        return isExist();
    }

    public boolean callArgumentInstance(File file) {
        return file.exists();
    }

    public boolean callFinalMethod(ClassDependency dependency) {
        return dependency.isAlive();
    }

    public boolean callArgumentInstance(String path) {
        File file = new File(path);
        return file.exists();
    }
}
