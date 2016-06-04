public class Runner{
   public static void main(String[] args){
      GUI gui = new GUI();
      IOControl.setUp();
      Alarm alarm = new Alarm(9, 30);
      while(true){
         if(alarm.isPast()){ 
            IOControl.toggleLight();
            System.out.println("toggle");
            break;
         }
      }
   }
}
