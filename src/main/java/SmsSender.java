// Install the Java helper library from twilio.com/docs/libraries/java
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class SmsSender {
    // Find your Account Sid and Auth Token at twilio.com/console
    public static final String ACCOUNT_SID =
            "AC4c726d0ba0e16d5121d2f55e073acb5e";
    public static final String AUTH_TOKEN =
            "61db361d0f0e58b2674050d8686a01b7";

    public static void main(String[] args) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message
                .creator(new PhoneNumber("+19257862700"), // to
                        new PhoneNumber("+"), // from
                        "Where's Wallace?")
                .create();

        System.out.println(message.getSid());
    }
}