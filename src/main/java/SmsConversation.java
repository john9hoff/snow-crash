import spark.Response;

public class SmsConversation {
    SmsSender smsSender;

    public SmsConversation(){
        smsSender = new SmsSender();
    }

    public void advance(Response request){
        String parsedRequest = parse(request.body());
    }



    private void confirmDrugs(){
        sendResponse("Have you recieved drugs?");
        String response =
    }
    private void consentToReminders(){}
    private void querryReminderTime(){}
    private void remind(){}


    public void sendResponse(String responceText){

    }

    public String handleRes





    private String parse(String unparsed){
        String parsed = unparsed.substring(unparsed.indexOf("Body=") + 5, unparsed.indexOf("&FromCountry"));
        parsed = parsed.replace('+',' ');
        parsed = parsed.replace("%2B","+");
        return parsed;
    }
}
