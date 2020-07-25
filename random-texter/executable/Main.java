/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hourglassinc.textbot;
import java.util.Scanner;
import java.io.*;
import java.util.concurrent.TimeUnit;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Clipboard;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Somoy_Subandhu
 * @project_name AnigameTextBot
 */
public class Main {
    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    public static String randomAlphaNumeric(long count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }
    private static void stringCopy(String textLine,double timeDelay)throws Exception{
        Robot rbt = new Robot();
        StringSelection selectedString = new StringSelection(textLine);
        Clipboard clipboard;
        clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selectedString, selectedString);

        //Pressing Cntrl+V
        rbt.keyPress(KeyEvent.VK_CONTROL);
        rbt.keyPress(KeyEvent.VK_V);

        //Delay
        Thread.sleep(10);

        //Releasing Keys
        rbt.keyRelease(KeyEvent.VK_V);
        rbt.keyRelease(KeyEvent.VK_CONTROL);

        //Pressing Enter for Text Submission
        rbt.keyPress(KeyEvent.VK_ENTER);
        rbt.keyRelease(KeyEvent.VK_ENTER);
        Thread.sleep((long)(1000.0*timeDelay));    
    }
    private static boolean verifyCheck(){
        if(new File("C:/Users/Downloads/verify").exists()){
            File f = new File("C:/Users/Downloads/verify");
            f.delete();
            return true;
        }if(new File("./verify").exists()){
            File f = new File("./verify");
            f.delete();
            return true;
        }
        return false;
    }
    private static boolean claimCheck(){
        if(new File("C:/Users/Downloads/claim").exists()){
            File f = new File("C:/Users/Downloads/verify");
            f.delete();
            return true;
        }if(new File("./claim").exists()){
            File f = new File("./claim");
            f.delete();
            return true;
        }
        return false;
    }
    public static void main(String[] args) throws Exception{
        Scanner sc = new Scanner(System.in);
        
        //Processing Inputs
        System.out.println("Please Input How many lines you want to type");
        int x = sc.nextInt();
        System.out.println("Please Enter the time delay per message that you want.");
        double timeDelay = sc.nextDouble();
        System.out.println("Bot Will Start in 10 seconds. Please put your cursor where you want to type");
        
        //Reading From File
        boolean fileFlag = true;
        File f = null;
        if( new File("./random.txt").exists() ){
            f = new File("./random.txt");
        }else{
            fileFlag = false;
        }
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        int cnt = 0;
        
        while(x!=0){
            String textLine;
            long count;
            if(fileFlag==false){
                count = (long)(Math.random()*50);
                textLine = randomAlphaNumeric(count);
                x--;
                cnt++;
                if(claimCheck()){
                        textLine = ".claim";
                }else if(verifyCheck()){
                    System.out.println("Captcha Occured");
                    break;
                }
                stringCopy(textLine,timeDelay);
            }
            else{
                BufferedReader br = new BufferedReader(new FileReader(f));
                while( fileFlag && ((textLine = br.readLine()) != null) && x!=0){               
                    x--;
                    cnt++;
                    if(claimCheck()){
                        textLine = ".claim";
                    }else if(verifyCheck()){
                        System.out.println("Captcha Occured");
                        x = 0;
                        break;
                    }
                    stringCopy(textLine,timeDelay);
                }
            }
        }
    }
}
