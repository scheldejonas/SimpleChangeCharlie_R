/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 *
 * @author Chris
 */
public class DatabaseHandler {
 
    String url;
    
    public static void write(String user, String score) throws MalformedURLException, IOException {
        user = user.replaceAll(" ", "_");
        URL submit = new URL("http://exomemphiz.site90.net/SubmitData.php?user=" + user + "&score=" + score);
        URLConnection conn = submit.openConnection();
        conn.setDoOutput(true);
        conn.setDoInput(true);
        System.out.println(submit.toString());
        BufferedReader in = new BufferedReader(new InputStreamReader(submit.openStream()));
        String line;
        while ((line = in.readLine()) != null) {
            System.out.println(line);
        }
    }
    
    public static ArrayList<Highscore> read() throws MalformedURLException, IOException {
        URL submit = new URL("http://exomemphiz.site90.net/RetrieveData.php?user=");
        URLConnection conn = submit.openConnection();
        conn.setDoOutput(true);
        conn.setDoInput(true);
        BufferedReader in = new BufferedReader(new InputStreamReader(submit.openStream()));
        String highscoreString = in.readLine();
        ArrayList<Highscore> highscore = new ArrayList<>();
        String[] strings = highscoreString.split("<br>");
        for (String s : strings) {
            highscore.add(new Highscore(s));
        }
        return highscore;
    }
    
}