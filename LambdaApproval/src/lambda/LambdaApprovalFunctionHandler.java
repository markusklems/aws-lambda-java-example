package lambda;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.AttributeValueUpdate;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;
import com.amazonaws.services.dynamodbv2.model.UpdateItemRequest;
import com.amazonaws.services.dynamodbv2.model.UpdateItemResult;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

/**
 * 
 * @author markusklems
 *
 */
public class LambdaApprovalFunctionHandler implements
		RequestHandler<Object, Object> {

	private static AmazonDynamoDBClient dynamoDB;

	@Override
	public Object handleRequest(Object input, Context context) {
		context.getLogger().log("input: " + input);
		if (input.toString().equals("{}") || input.toString().equals("")) {
			context.getLogger().log("input is empty: abort");
			return "{\"status\":\"error\",\"message\":\"input at lambda function is empty\"}";
		}

		dynamoDB = new AmazonDynamoDBClient().withRegion(Region
				.getRegion(Regions.EU_WEST_1));

		HashMap<String, String> mapInput = (HashMap<String, String>) input;
		Map<String, AttributeValue> employeeKey = new HashMap<String, AttributeValue>();
		String employeeId = mapInput.get("employee_id");
		context.getLogger().log("employee_id: " + employeeId);
		employeeKey.put("employee_id", new AttributeValue().withS(employeeId));
		Map<String, AttributeValueUpdate> attributeUpdates = new HashMap<String, AttributeValueUpdate>();
		attributeUpdates.put("approval", new AttributeValueUpdate()
				.withValue(new AttributeValue().withS("approved")));
		UpdateItemRequest updateItemRequest = new UpdateItemRequest()
				.withKey(employeeKey).withAttributeUpdates(attributeUpdates)
				.withTableName("lambda-reimbursment");
		UpdateItemResult updateItemResult = dynamoDB
				.updateItem(updateItemRequest);
		context.getLogger().log("Result: " + updateItemResult);

		return "{'status':'done'}";
	}

}
