import com.twilio.twiml.MessagingResponse;
import com.twilio.twiml.messaging.Body;
import com.twilio.twiml.messaging.Message;
import spark.Spark;

import static spark.Spark.*;

public class SmsApp {
    public static void main(String[] args) {
        SmsConversation convo = new SmsConversation();


        post("/sms", (req, res) -> {
            res.type("application/xml");

            MessagingResponse twiml = convo.handleRequest(req.body());

            return twiml.toXml();
        });


//        post("/sms", (req, res) -> {
//            res.type("application/xml");
//            Body body = new Body
//                    .Builder("The Robots are coming! Head for the hills!")
//                    .build();
//            Message sms = new Message
//                    .Builder()
//                    .body(body)
//                    .build();
//            MessagingResponse twiml = new MessagingResponse
//                    .Builder()
//                    .message(sms)
//                    .build();
//            return twiml.toXml();
//        });
    }
}