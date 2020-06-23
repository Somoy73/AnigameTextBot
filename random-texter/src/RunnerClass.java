import java.util.Scanner;
import java.io.*;
import java.util.concurrent.TimeUnit;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Clipboard;
import java.awt.event.KeyEvent;


public class RunnerClass{
    public static void main(String[] args) throws Exception{
        Scanner sc = new Scanner(System.in);
        
        //Processing Inputs
        System.out.println("Please Input How many lines you want to type");
        int x = sc.nextInt();
        System.out.println("Please Enter the time delay per message that you want.");
        double timeDelay = sc.nextDouble();
        
        //Reading From File
        File f = new File("./random.txt");
        TimeUnit.SECONDS.sleep(10);
        
        //Robot will Simulate KeyPress
        Robot rbt = new Robot();
        
        while(x!=0){
            BufferedReader br = new BufferedReader(new FileReader(f));
            String textLine;
            while(((textLine = br.readLine()) != null) && x!=0 ){
                x--;
                StringSelection selectedString = new StringSelection(textLine);
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(selectedString, selectedString);

                //Pressing Cntrl+V
                rbt.keyPress(KeyEvent.VK_CONTROL);
                rbt.keyPress(KeyEvent.VK_V);
                
                //Delay
                Thread.sleep(30);
                
                //Releasing Keys
                rbt.keyRelease(KeyEvent.VK_V);
                rbt.keyRelease(KeyEvent.VK_CONTROL);
                
                //Pressing Enter for Text Submission
                rbt.keyPress(KeyEvent.VK_ENTER);
                rbt.keyRelease(KeyEvent.VK_ENTER);
                Thread.sleep((long)(1000.0*timeDelay));
            }
        }
    }
    
}

