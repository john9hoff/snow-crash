import com.twilio.twiml.MessagingResponse;
import com.twilio.twiml.messaging.Body;
import com.twilio.twiml.messaging.Message;
import com.wanasit.chrono.Chrono;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

public class SmsConversation {
    String username = "Karen";
    SmsSender smsSender;
    String nextFunctionCall, userResponse, functionResponse;
    HashMap<String,Runnable> functions;
    HashMap<String, LinkedList<Reminder>> schedule;

    public SmsConversation(){
        smsSender = new SmsSender();
        nextFunctionCall = "confirmDrugs";

        functions = new HashMap<>();
        functions.put("confirmDrugs", () -> confirmDrugs());
        functions.put("processConfirmDrugResponse",() -> processConfirmDrugResponse());
        functions.put("consentToReminders", () -> consentToReminders());
        functions.put("processConsentToReminders", () -> processConsentToReminders());
        functions.put("queryReminderTime", () -> queryReminderTime());
        functions.put("remind", () -> remind());

        schedule = new HashMap<>();
        mock();

        smsSender.sendMessage("Hi " + username + "!");
        confirmDrugs();
    }

    private void confirmDrugs(){
        smsSender.sendMessage("Have you received your prescription?");
        nextFunctionCall = "processConfirmDrugResponse";
    }

    private void processConfirmDrugResponse(){
        if(userResponse.equals("y")){
            smsSender.sendMessage("Excellent!");
            consentToReminders();
        }
        else{
            functionResponse = "Why not?";
            nextFunctionCall = "confirmDrugs";
        }
    }
    private void consentToReminders(){
        functionResponse = "Would you like to use the SMS perscription reminder system?";
        nextFunctionCall = "processConsentToReminders";
    }

    private void processConsentToReminders(){
        if(userResponse.equals("y")){
            functionResponse = "Ok.  Please enter the time you would like to be reminded.  Examples: 9 AM, 8:45 PM \n" +
                    "Or type \"Schedule\" to see your current medication schedule";
            nextFunctionCall = "queryReminderTime";
        }
        else{
            functionResponse = "Ok.  If you have any questions, dial (555) 555-5551 to speak with a Pharmacist";
        }
    }

    private void queryReminderTime(){
        if(!userResponse.toLowerCase().contains("schedule")) {
            Date result = Chrono.ParseDate(userResponse);

            String remindTime = result.getHours() + ":" + result.getMinutes(); // for HashMap
            Reminder reminder = new Reminder("3 LCDs", "Netflixium", result.getHours(), result.getMinutes());
            addToSchedule(remindTime, reminder);

            smsSender.sendMessage("Ok, I've set a reminder for " + reminder.getAmPmTime());
            remind();
        }
        else{
            functionResponse = "Medication Schedule:\n\n";
            for(int h = 0; h < 13; h++){
                for(int m = 0; m < 60; m++){
                    String hourString = String.valueOf(h);
                    String minString;
                    if(m < 10){
                        minString = "0"+String.valueOf(m);
                    }
                    else{
                        minString = String.valueOf(m);
                    }
                    String key = hourString + ":" + minString;
                    if(schedule.get(key) != null){
                        for(Reminder r : schedule.get(key)){
                            functionResponse += "• " + r.dose + " of " + r.medicationName + " at " + r.getAmPmTime() + "\n";
                        }
                    }
                }
            }
            functionResponse += "\n" + "Please enter the time you would like to be reminded.  Examples: 9 AM, 8:45 PM \n" +
            "Or type \"Schedule\" to see your current medication schedule";
        }
    }
    private void remind(){
        functionResponse = "";
        boolean reminded = false;
        while(!reminded){
            Date time = new Date();
            String timeString = time.getHours() + ":" + time.getMinutes();
            if(schedule.get(timeString) != null){
                for(Reminder reminder : schedule.get(timeString)){
                    functionResponse += ("Medication reminder:\n\n" +
                            "Take " + reminder.dose + " of " + reminder.medicationName + "\n");
                }
                reminded = true;
            }
        }
        functionResponse += "\nReply \"cancel\" to cancel this reminder or \"stop\" to " +
                "stop using Express Scripts' reminder service";
        nextFunctionCall = "confirmDrugs";
    }

    public MessagingResponse handleRequest(String message){
        String parsed = parseSMS(message);
        userResponse = parsed;

        functions.get(nextFunctionCall).run();

        return sendResponse(functionResponse);
    }

    public MessagingResponse sendResponse(String responseText){
        Body body = new Body
                .Builder(responseText)
                .build();
        Message sms = new Message
                .Builder()
                .body(body)
                .build();
        MessagingResponse twiml = new MessagingResponse
                .Builder()
                .message(sms)
                .build();
        return twiml;
    }

    public String parseSMS(String unparsed){
        String parsed = unparsed.substring(unparsed.indexOf("Body=") + 5, unparsed.indexOf("&FromCountry"));
        parsed = parsed.replace('+',' ');
        parsed = parsed.replace("%2B","+");
        parsed = parsed.replace("%3A", ":");
        return parsed;
    }

    private void addToSchedule(String remindTime, Reminder reminder){
        if(schedule.get(remindTime) == null){
            LinkedList<Reminder> timeList = new LinkedList<>();
            timeList.add(reminder);
            schedule.put(remindTime,timeList);
        }
        else{
            schedule.get(remindTime).add(reminder);
        }
    }

    private void mock(){
        Reminder r1 = new Reminder("2 capsules","Amazonalto", 9, 30);
        addToSchedule("9:30",r1);
        Reminder r2 = new Reminder("5 grams", "IBMTren", 12,30);
        addToSchedule("12:30",r2);
    }
}
