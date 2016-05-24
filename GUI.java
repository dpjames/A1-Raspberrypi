import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

import java.net.URL;
import java.net.URLConnection;

import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.InputStream;

import java.util.Scanner;
public class GUI extends JFrame{
   private JPanel panel;
   private static JButton update;
   public GUI(){
      super("GUI");
      this.setVisible(true);
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      panel = new JPanel();
      panel.setPreferredSize(new Dimension(320,240));
      update = new JButton("update");
      Weather weather = new Weather();
      update.addActionListener(weather);
      panel.add(update);
      update.setFont(new Font("Times New Roman", Font.PLAIN, 35));
      panel.repaint();
      this.add(panel);
      this.pack();
      
   }
   public static void updateWeather(){
      update.doClick(0);
   }
   private class Weather implements ActionListener{
      public void actionPerformed(ActionEvent e){
         try{
            URL website = new URL("http://forecast.weather.gov/MapClick.php?CityName=San+Luis+Obispo&state=CA&site=LOX&lat=35.2565&lon=-120.621#.V0NeFXUrLCJ");
            InputStream is = website.openStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            int temp = getTemp(website, br);
            String wind = getWind(website, br);
            int[] highlow = firstTemp(website, br);
            JButton button = (JButton)(e.getSource());
            button.setText("<html>" + 
               "Temp: " + temp + "° F<br/>" 
               + wind + "<br/>"
               + "High: " + highlow[1] + "° F<br/>"
               + "Low: " + highlow[0] + "° F<br/>"
               + "<html/>");
         }catch(Exception error){
            error.printStackTrace();
            //(JButton)(e.getSource()).setText("No Connection");
         }
      }
      private int[] firstTemp(URL website, BufferedReader br) throws Exception{
         int[] rval = {0,0};
         for(int i = 0; i < 42; i++){
            br.readLine();
         }
         String line = br.readLine();
         int temp1 = getHighLow(line);
         br.readLine();
         br.readLine();
         line = br.readLine();
         int temp2 = getHighLow(line);
         rval[0] = Math.min(temp1,temp2);
         rval[1] = Math.max(temp1,temp2);
         //System.out.println(rval[0]);
         //System.out.println(rval[1]);
         return rval;
      }
      private int getHighLow(String line){
         int i = 0;
         while(!line.substring(i,i+4).contains("Low") && !line.substring(i,i+4).contains("High")){
            i++;
         }
         return Integer.parseInt(line.substring(i+6,i+8));
      }
      private String getWind(URL website, BufferedReader br) throws Exception{// line 183
         for(int i = 0; i < 10; i++){
            br.readLine();
         }
         return br.readLine().substring(16,30);
      }
      private int getTemp(URL website, BufferedReader br) throws Exception{ //line 173
         for(int i = 0; i < 173; i++){
            br.readLine();
         }
         int i = Integer.parseInt(br.readLine().substring(40,42));
         //System.out.println(i);
         return i;
      }
      
   }



   public static void main(String[] args){
      GUI gui = new GUI();
   }
}
