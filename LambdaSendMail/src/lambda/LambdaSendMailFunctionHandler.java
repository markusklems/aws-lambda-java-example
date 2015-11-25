package lambda;

import java.util.Map.Entry;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.StreamRecord;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent.DynamodbStreamRecord;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;

public class LambdaSendMailFunctionHandler implements
		RequestHandler<DynamodbEvent, Object> {

	static final String BODY = "This email was sent through Amazon SES by using the AWS SDK for Java.";
	static final String SUBJECT = "Amazon SES test (AWS SDK for Java)";

	@Override
	public Object handleRequest(DynamodbEvent input, Context context) {
		String employeeId = "";
		String employeeName = "";
		String expenseType = "";
		Double amount = 0.0;

		for (DynamodbStreamRecord r : input.getRecords()) {
			// context.getLogger().log("Event id: "+r.getEventID());
			// context.getLogger().log("Event name: "+r.getEventName());
			// context.getLogger().log("Event source: "+r.getEventSource());
			StreamRecord sr = r.getDynamodb();
			for (Entry<String, AttributeValue> entry : sr.getNewImage()
					.entrySet()) {
				String k = entry.getKey();
				AttributeValue v = entry.getValue();
				switch (k) {
				case "employee_id":
					employeeId = v.getS();
					break;
				case "employee_name":
					employeeName = v.getS();
					break;
				case "expense_type":
					expenseType = v.getS();
					break;
				case "amount":
					amount = Double.valueOf(entry.getValue().getN());
					break;
				default:
					context.getLogger().log("Key " + k + " is unknown.");
				}
			}
		}

		context.getLogger().log(
				"ENTRY: " + employeeId + " | " + employeeName + " | "
						+ expenseType + " | " + amount);

		String from = "klems@tu-berlin.de"; // TODO Replace with your "From" address.
											// This address must be verified.
		String to = "klems@tu-berlin.de"; // TODO Replace with a "To" address. If you
											// have not yet requested production
											// access, this address must be
											// verified.
		String subject = String.format("Expense reimbursment request by %s",
				employeeName);
		// TODO Replace with your own approval URL
		String approvalUrl = String
				.format("https://.......execute-api.eu-west-1.amazonaws.com/test/reimbursment?id=%s",
						employeeId);
		String body = String
				.format("Hello boss,\n\nplease approve my expense reimbursment:\n%s\n\nExpense type: %s\nAmount: %s EUR\n\nThanks!\n%s\nEmployee ID: %s ",
						approvalUrl, expenseType, amount, employeeName,
						employeeId);
		sendMail(from, to, subject, body);
		context.getLogger().log("Email sent from " + from + " to " + to);

		return null;
	}

	private void sendMail(final String from, final String to,
			final String subjectStr, final String bodyStr) {
		// Construct an object to contain the recipient address.
		Destination destination = new Destination()
				.withToAddresses(new String[] { to });

		// Create the subject and body of the message.
		Content subject = new Content().withData(subjectStr);
		Content textBody = new Content().withData(bodyStr);
		Body body = new Body().withText(textBody);

		// Create a message with the specified subject and body.
		Message message = new Message().withSubject(subject).withBody(body);

		// TODO Assemble the email.

		// TODO Send the email.

	}
}