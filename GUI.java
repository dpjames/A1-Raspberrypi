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
   private static String currentSite;
   private static final String SLO_WEBSITE = "http://forecast.weather.gov/MapClick.php?CityName=San+Luis+Obispo&state=CA&site=LOX&lat=35.2565&lon=-120.621#.V0NeFXUrLCJ";
   private static final String TAC_WEBSITE = "http://forecast.weather.gov/MapClick.php?CityName=Tacoma&state=WA&site=SEW&textField1=47.2531&textField2=-122.443&e=1#.V0PxD3UrLCI";
   public GUI(){
      super("GUI");
      this.setVisible(true);
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      panel = new JPanel();
      panel.setPreferredSize(new Dimension(320,240));
      update = new JButton("update");
      JButton slo = new JButton("SLO");
      JButton tac = new JButton("Tacoma");
      Weather weather = new Weather();
      update.addActionListener(weather);
      slo.addActionListener(weather);
      tac.addActionListener(weather);
      panel.add(update);
      panel.add(slo);
      panel.add(tac);
      update.setFont(new Font("Times New Roman", Font.PLAIN, 35));
      panel.repaint();
      this.add(panel);
      this.pack();
      
      currentSite = SLO_WEBSITE;
      
   }
   public static void updateWeather(){
      update.doClick(0);
   }
   private class Weather implements ActionListener{
      public void actionPerformed(ActionEvent e){
         JButton button = (JButton)(e.getSource());
         if(button.getText().equals("Tacoma")){
            currentSite = TAC_WEBSITE;
            updateWeather();
            return;
         }else if(button.getText().equals("SLO")){
            currentSite = SLO_WEBSITE;
            updateWeather();
            return;
         }
         try{
            URL website = new URL(currentSite);
            InputStream is = website.openStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            int temp = getTemp(website, br);
            String wind = getWind(website, br);
            int[] highlow = firstTemp(website, br);
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
         return br.readLine().substring(16,25);
      }
      private int getTemp(URL website, BufferedReader br) throws Exception{ //line 173
         /*for(int i = 0; i < 173; i++){
            br.readLine();
         }*/
         String line = br.readLine();
         while(!line.contains("myforecast-current-lrg")){
            line = br.readLine();
         }
         //System.out.println(line);
         int i = Integer.parseInt(line.substring(40,42));
         //System.out.println(i);
         return i;
      }
      
   }



   public static void main(String[] args){
      GUI gui = new GUI();
   }
}
