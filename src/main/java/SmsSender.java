// Install the Java helper library from twilio.com/docs/libraries/java
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class SmsSender {
    // Find your Account Sid and Auth Token at twilio.com/console
    public static final String ACCOUNT_SID =
            "AC1c40a84078610f7edf7ee185c9cdf881";
    public static final String AUTH_TOKEN =
            "d239c178cade34f1a9217d93f79ab307";

    public static void main(String[] args) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message
                .creator(new PhoneNumber("+13202952616"), // to
                        new PhoneNumber("+13205230127"), // from
                        "Test Text: Your access code is wombat")
                .create();

        System.out.println(message.getSid());
    }
}