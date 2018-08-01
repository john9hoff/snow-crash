public class Reminder {
    String dose;
    String medicationName;
    int hour;
    int min;

    Reminder(String dose, String medicationName, int hour, int min){
        this.dose = dose;
        this.medicationName = medicationName;
        this.hour = hour;
        this.min = min;
    }

    public void setTime(int hour, int min){
        this.hour = hour;
        this.min = min;
    }

    public String getAmPmTime(){
        String result = "";
        boolean am = hour < 12;
        if(am && hour == 0){
            result += "12:";
        }
        else{
            if(am) {
                result += String.valueOf(hour) + ":";
            }
            else{
                if(hour == 12) {
                    result += String.valueOf(hour) + ":";
                }
                else{
                    result += String.valueOf(hour - 12) + ":";
                }
            }
        }
        if(min < 10){
            result += "0" + String.valueOf(min);
        }
        else{
            result += String.valueOf(min);
        }
        if(am){
            result += " AM";
        }
        else{
            result += " PM";
        }
        return result;
    }
}
