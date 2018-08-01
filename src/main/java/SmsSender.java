// Install the Java helper library from twilio.com/docs/libraries/java
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class SmsSender {
    // Find your Account Sid and Auth Token at twilio.com/console
    public static final String ACCOUNT_SID =
            "ACe36aede20fcaa84b22f9df555606e07f";
    public static final String AUTH_TOKEN =
            "8ceb2a17002627dfc6d57ad194c72492";

    public void sendMessage(String outgoing){
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message
                .creator(new PhoneNumber("+17632227520"), // to
                        new PhoneNumber("+17639511646"), // from
                        outgoing)
                .create();
    }

    public static void main(String[] args) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message
                .creator(new PhoneNumber("+17632227520"), // to
                        new PhoneNumber("+17639511646"), // from
                        "Happy cool things")
                .create();
    }
}