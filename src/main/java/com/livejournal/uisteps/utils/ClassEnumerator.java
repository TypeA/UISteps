/*
 * Copyright 2014 ASolyankin.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.livejournal.uisteps.utils;

/**
 *
 * @author ASolyankin
 */
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClassEnumerator {

    private ArrayList<Class<?>> classes = new ArrayList<>();
    private String currentPackage;

    public ClassEnumerator() {

    }

    public ClassEnumerator(Package pkg) {
        getClassesForPackage(pkg);
    }

    public ClassEnumerator(String pkg) {
        getClassesForPackage(pkg);
    }

    private Class<?> loadClass(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new AssertionError("Unexpected ClassNotFoundException loading class '" + className + "'");
        }
    }

    private void processDirectory(File directory, String pkgname) {
        // Get the list of the files contained in the package
        String[] fileNames = directory.list();
        for (String fileName : fileNames) {
            String className = null;
            // we are only interested in .class files
            if (fileName.endsWith(".class")) {
                // removes the .class extension
                className = pkgname + '.' + fileName.substring(0, fileName.length() - 6);
            }
            if (className != null) {
                classes.add(loadClass(className));
            }
            File subdir = new File(directory, fileName);
            if (subdir.isDirectory()) {
                processDirectory(subdir, pkgname + '.' + fileName);
            }
        }
    }

    private void processJarfile(URL resource, String pkgname) {
        String relPath = pkgname.replace('.', '/');
        String resPath = resource.getPath();
        String jarPath = resPath.replaceFirst("[.]jar[!].*", ".jar").replaceFirst("file:", "");
        JarFile jarFile;
        try {
            jarFile = new JarFile(jarPath);
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException reading JAR File '" + jarPath + "'", e);
        }
        Enumeration<JarEntry> entries = jarFile.entries();
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            String entryName = entry.getName();
            String className = null;
            if (entryName.endsWith(".class") && entryName.startsWith(relPath) && entryName.length() > (relPath.length() + "/".length())) {
                className = entryName.replace('/', '.').replace('\\', '.').replace(".class", "");
            }
            if (className != null) {
                classes.add(loadClass(className));
            }
        }
    }

    public final ArrayList<Class<?>> getClassesForPackage(Package pkg) {
        String pkgname = pkg.getName();
        return getClassesForPackage(pkgname);
    }

    public ArrayList<Class<?>> getClassesForPackage(String pkgname) {
        if (currentPackage == null || currentPackage.equals("") || !currentPackage.equals(pkgname)) {
            String relPath = pkgname.replace('.', '/');
            // Get a File object for the package
            URL resource = ClassLoader.getSystemClassLoader().getResource(relPath);
            if (resource == null) {
                throw new AssertionError("Unexpected problem: No resource for " + relPath);
            }
            resource.getPath();
            if (resource.toString().startsWith("jar:")) {
                processJarfile(resource, pkgname);
            } else {
                processDirectory(new File(resource.getPath()), pkgname);
            }
        }
        currentPackage = pkgname;
        return classes;
    }

    public final Class<?> getClassBySimpleName(String className, String pkgname) {
        classes = getClassesForPackage(pkgname);
        return getClassBySimpleName(className);
    }

    public Class<?> getClassBySimpleName(String className, Package pkg) {
        classes = getClassesForPackage(pkg);
        return getClassBySimpleName(className);
    }

    public Class<?> getClassBySimpleName(String className) {
        if (currentPackage == null || currentPackage.equals("")) {
            throw new AssertionError("Current package is not set!\n");
        }
        ArrayList<Class<?>> foundClasses = getClassesBySimpleName(className);
        if (foundClasses.size() > 1) {
            String error = "More than one class was found: ";
            for (Class<?> klass : foundClasses) {
                error += klass.getName() + ",";
            }
            throw new AssertionError(error.replaceAll(error.substring(error.length() - 2), "!\n"));
        }
        return foundClasses.get(0);

    }

    public ArrayList<Class<?>> getClassesBySimpleName(String className) {
        if (currentPackage == null || currentPackage.equals("")) {
            throw new AssertionError("Current package is not set!\n");
        }
        ArrayList<Class<?>> foundClasses = new ArrayList<>();
        Pattern p = Pattern.compile(".*\\.(.*?)$");
        for (Class<?> klass : classes) {
            Matcher m = p.matcher(klass.getName());
            if (m.find() && m.group(1).equals(className)) {
                foundClasses.add(klass);
            }
        }
        if (foundClasses.isEmpty()) {
            throw new AssertionError("Cannot find class by name: " + className + "!\n");
        }
        return foundClasses;

    }
}
