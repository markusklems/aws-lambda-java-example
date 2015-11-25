package lambda;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

/**
 * 
 * @author markusklems
 *
 */
public class LambdaFormFunctionHandler implements
		RequestHandler<Object, Object> {

	private static AmazonDynamoDBClient dynamoDB;

	/**
	 * Example object:
	 * 
	 * {"employee_id":"1", "employee_name":"John Doh", "expense_type":"travel",
	 * "amount": "2565.75" }
	 */
	@Override
	public Object handleRequest(Object input, Context context) {
		dynamoDB = new AmazonDynamoDBClient().withRegion(Region
				.getRegion(Regions.EU_WEST_1));
		// TODO
		// Parse the input object into a DynamoDB item, e.g., by using the newItem helper method
		// Create a PutItemRequest and send the request at the DynamoDB table with name 'lambda-reimbursment'

		return "{'status':'done'}";
	}

	/**
	 * Helper method.
	 * 
	 * @param employee_id
	 * @param employee_name
	 * @param expense_type
	 * @param amount
	 * @return DynamoDB item
	 */
	private static Map<String, AttributeValue> newItem(String employee_id,
			String employee_name, String expense_type, String amount) {
		Map<String, AttributeValue> item = new HashMap<String, AttributeValue>();
		item.put("employee_id", new AttributeValue((String) employee_id));
		item.put("employee_name", new AttributeValue((String) employee_name));
		item.put("expense_type", new AttributeValue((String) expense_type));
		item.put("amount", new AttributeValue().withN(amount));
		return item;
	}

}
