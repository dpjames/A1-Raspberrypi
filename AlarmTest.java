import java.time.*;
public class AlarmTest{
   public static void main(String[] args){
      //System.out.println(ZoneId.getAvailableZoneIds());
      Alarm test1 = new Alarm(0,34);
      ZonedDateTime time = ZonedDateTime.now(ZoneId.of("Asia/Baku"));
      
         time = ZonedDateTime.now(ZoneId.of("Atlantic/Azores"));
         System.out.println("Alarm" + test1.getTime()[0] + " " + test1.getTime()[1]);
         System.out.println("Time" + time);
      /*}while(time.getHour() < test1.getTime()[0] 
            || time.getMinute() < test1.getTime()[1]);
      */
      //US/Pacific
      while(!test1.isPast()){
         System.out.println(ZonedDateTime.now(ZoneId.of("Atlantic/Azores")));
      }
      System.out.println("yes");
      
   }
}
