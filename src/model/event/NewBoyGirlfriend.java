/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.event;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.BaseDrug;
import model.BaseEvent;
import model.Player;

/**
 *
 * @author CHRIS
 */
public class NewBoyGirlfriend extends BaseEvent {

    @Override
    public void effect(Player player) {
        int amount = new Random().nextInt(10) + 1;  //Amount of drugs
        ClassFinder finder = new ClassFinder();     //Init classFinder
        List<Class<?>> classes = finder.find("model.drug");     //Find all classes inside package model.drug
        int drug = new Random().nextInt(classes.size());        //Select a random one from within the classes of drugs
        Class drugClass = classes.get(drug);                    //Specific drug selected, using the rng number
        String drugName = drugClass.getName().replace("model.drug.", "");   //Get name of the drug
        BaseDrug newDrug = new BaseDrug(0, amount).getNewBaseDrug(drugName);    //Create drug with amount and specified name
        player.addDrug(newDrug);
        JOptionPane.showMessageDialog(null, "You aquired a new boy/girl friend!" + System.lineSeparator() +
                                            "You gain " + amount + " of " + drugName + " from him/her!");
    }

    @Override
    public boolean shouldFire(Player player) {
        /*
        int change = (player.hasHighFriends() ? 1 : 0);
        change += (player.hasGun() ? 2 : 0);
        int firePercentage = getFirePercentage() - change;
        Random r = new Random();
        int random = r.nextInt(100);
        System.out.println("[AngryPusher.shouldFire] random: " + random + ", firePercentage: " + firePercentage);
        return random <= firePercentage;
        */
        return true;
    }
    
    public class ClassFinder {

        private static final char PKG_SEPARATOR = '.';
        private static final char DIR_SEPARATOR = '/';
        private static final String CLASS_FILE_SUFFIX = ".class";
        private static final String BAD_PACKAGE_ERROR = "Unable to get resources from path '%s'. Are you sure the package '%s' exists?";

        List<Class<?>> find(String scannedPackage) {
            try {
                String scannedPath = scannedPackage.replace(PKG_SEPARATOR, DIR_SEPARATOR);
                URL scannedUrl = Thread.currentThread().getContextClassLoader().getResource(scannedPath);
                if (scannedUrl == null) {
                    throw new IllegalArgumentException(String.format(BAD_PACKAGE_ERROR, scannedPath, scannedPackage));
                }
                File scannedDir = new File(scannedUrl.toURI().getSchemeSpecificPart());
                List<Class<?>> classes = new ArrayList<Class<?>>();
                try {
                    if (scannedDir.listFiles() != null) {
                        for (File file : scannedDir.listFiles()) {
                            if (file != null && !file.getPath().contains("pricestrat")) {
                                System.out.println("Found file: " + file);
                                classes.addAll(find(file, scannedPackage));
                            }
                        }
                    } else {
                        System.out.println("Error, cannot find files in directory: " + scannedDir.getPath());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return classes;
            } catch (Exception e) {
                
            }
            return null;
        }

        private List<Class<?>> find(File file, String scannedPackage) {
            List<Class<?>> classes = new ArrayList<Class<?>>();
            String resource = scannedPackage + PKG_SEPARATOR + file.getName();
            if (file.isDirectory()) {
                for (File child : file.listFiles()) {
                    classes.addAll(find(child, resource));
                }
            } else if (resource.endsWith(CLASS_FILE_SUFFIX)) {
                int endIndex = resource.length() - CLASS_FILE_SUFFIX.length();
                String className = resource.substring(0, endIndex);
                try {
                    classes.add(Class.forName(className));
                } catch (ClassNotFoundException ignore) {
                }
            }
            return classes;
        }

    }
    
}