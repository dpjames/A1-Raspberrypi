import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
public class IOControl{
   private static GpioPinDigitalOutput light;
   public static void setUp(){
      GpioController gpio = GpioFactory.getInstance();
      GpioPinDigitalInput mic = gpio.provisionDigitalInputPin(RaspiPin.GPIO_01);
      light = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_29);
      mic.addListener(new GList());
      
   }
   
   
   private static class GList implements GpioPinListenerDigital{
      private static boolean on;
      private static long lastAction;
      public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event){
         if(System.nanoTime() - lastAction < 500000000){
            return;
         }
         if(on){
            light.low();
         }else if(!on){
            light.high();
         }
         on=!on;
         lastAction = System.nanoTime();
         GUI.updateWeather();
      }
   }
}
