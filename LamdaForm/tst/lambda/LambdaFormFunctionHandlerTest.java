package lambda;

import java.io.IOException;
import java.util.HashMap;

import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class LambdaFormFunctionHandlerTest {

    private static Object input;

    @BeforeClass
    public static void createInput() throws IOException {
		// TODO: set up your sample input object here.
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("employee_id", "99");
        hashMap.put("employee_name", "Jimmy");
        hashMap.put("expense_type", "travel");
        hashMap.put("amount", "465.98");
        input = hashMap ;
    }

    private Context createContext() {
        TestContext ctx = new TestContext();

        // TODO: customize your context here if needed.
        ctx.setFunctionName("LambdaForm");

        return ctx;
    }

    @Test
    public void testLambdaFormFunctionHandler() {
        LambdaFormFunctionHandler handler = new LambdaFormFunctionHandler();
        Context ctx = createContext();

        Object output = handler.handleRequest(input, ctx);

        // TODO: validate output here if needed.
        if (output != null) {
            System.out.println(output.toString());
        }
    }
}
