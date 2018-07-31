import com.twilio.twiml.MessagingResponse;
import com.twilio.twiml.messaging.Body;
import com.twilio.twiml.messaging.Message;

import static spark.Spark.*;

public class SmsApp {
    public static void main(String[] args) {
        get("/", (req, res) -> "Hello Web");

        post("/sms", (req, res) -> {
            res.type("application/xml");

            String parsed = parse(req.body());

            Body body = new Body
                    .Builder("The Robots are coming! Head for the hills!")
                    .build();
            Message sms = new Message
                    .Builder()
                    .body(body)
                    .build();
            MessagingResponse twiml = new MessagingResponse
                    .Builder()
                    .message(sms)
                    .build();
            return twiml.toXml();
        });
    }

    private static String parse(String unparsed){
        String parsed = unparsed.substring(unparsed.indexOf("Body=") + 5, unparsed.indexOf("&FromCountry"));
        parsed = parsed.replace('+',' ');
        parsed = parsed.replace("%2B","+");
        return parsed;
    }
}