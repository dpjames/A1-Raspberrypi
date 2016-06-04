import java.time.ZonedDateTime;
import java.time.ZoneId;
public class Alarm{
   private int h,m;
   public Alarm(){
      this.h = 0;
      this.m = 0;
   }
   public Alarm(int h){
      this.h = h;
      this.m = 0;
   }
   public Alarm(int h, int m){
      this.h = h;
      this.m = m;
   }
   public int[] getTime(){
      int[] t = {h,m};
      return t;
   }
   public boolean isPast(){
      ZonedDateTime time = ZonedDateTime.now(ZoneId.of("Atlantic/Azores"));
      return !(time.getMinute() < m || time.getHour()< h);
         
   }
}
