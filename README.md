# AWS Lambda Java Example

A simple Java Lambda based server-less application that implements a reimbursement use case, with AWS Lambda, AWS API Gateway, AWS SES, and AWS DynamoDB. Set up the project with Eclipse and AWS Toolkit plugin.

Please take a look at the wiki for a detailed [Getting Started - Step-by-Step](https://github.com/markusklems/aws-lambda-java-example/wiki/Getting-Started) guide.

## Getting Started (Short)
1. Set up Eclipse, import the projects, and install the AWS Eclipse Toolkit plugin.
2. Upload the Lambda Functions (Right click on project > Amazon Web Services > Upload Function to AWS Lambda)
3. Set up a DynamoDB table 'lambda-reimbursment' with Hash Key 'employee_id' (String). Set the DynamoDB tables as event source for LambdaSendMail and LambdaApproval. Create an API method, so that a click on the link (HTTP GET) calls the LambdaApproval Function (with a parameter 'employee_id').
4. Call the LambdaForm Function from Eclipse with the following JSON input:
```{"employee_id":"1", "employee_name":"John Doh", "expense_type":"travel","amount": "456.75" }```

If the Lambda Function completes successfully, a new entry will be added to the DynamoDB table lambda-reimbursment that you created in step 1 of the AWS Services setup.

LambdaSendMail gets triggered by the DynamoDB stream (pull model) and sends an e-mail with the info that has been added to the table.

If you have received the e-mail and click on the approval URL in the e-mail body, the LambdaApproval Function will be called and add an "approved" column entry to DynamoDB.

More detailed "Getting Started" info: https://github.com/markusklems/aws-lambda-java-example/wiki/Getting-Started
