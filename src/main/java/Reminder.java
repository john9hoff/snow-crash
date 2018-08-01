public class Reminder {
    String dose;
    String medicationName;
    String time;

    Reminder(String dose, String medicationName, String time){
        this.dose = dose;
        this.medicationName = medicationName;
        this.time = time;
    }

    public void setTime(String newTime){
        time = newTime;
    }
}
