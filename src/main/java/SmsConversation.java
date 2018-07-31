import com.twilio.twiml.MessagingResponse;
import com.twilio.twiml.messaging.Body;
import com.twilio.twiml.messaging.Message;

import java.util.HashMap;

public class SmsConversation {
    SmsSender smsSender;
    String nextFunctionCall, userResponse, functionResponse;
    HashMap<String,Runnable> functions;

    public SmsConversation(){
        smsSender = new SmsSender();
        confirmDrugs();
        nextFunctionCall = "confirmDrugs";

        functions = new HashMap<>();
        functions.put("confirmDrugs", () -> confirmDrugs());
        functions.put("processConfirmDrugResponse",() -> processConfirmDrugResponse());
        functions.put("confirmDrugsNo", () -> confirmDrugsNo());
        functions.put("consentToReminders", () -> consentToReminders());
        functions.put("queryReminderTime", () -> queryReminderTime());
        functions.put("reminder", () -> remind());
        sendResponse("Hey there");

    }


    private void confirmDrugs(){
        functionResponse = "Have you recieved drugs?";
    }

    private void processConfirmDrugResponse(){
    }

    private void confirmDrugsNo(){
        sendResponse("Why not?");
    }
    private void consentToReminders(){}
    private void queryReminderTime(){}
    private void remind(){}

    public MessagingResponse handleRequest(String message){
        String parsed = parse(message);
        userResponse = parsed;

        System.out.println(userResponse);

        functions.get("confirmDrugs");

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






    public String parse(String unparsed){
        String parsed = unparsed.substring(unparsed.indexOf("Body=") + 5, unparsed.indexOf("&FromCountry"));
        parsed = parsed.replace('+',' ');
        parsed = parsed.replace("%2B","+");
        return parsed;
    }
}
