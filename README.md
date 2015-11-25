# AWS Lambda Java Example

A simple Java Lambda based server-less application that implements a reimbursement use case, with AWS Lambda, AWS API Gateway, AWS SES, and AWS DynamoDB. Set up the project with Eclipse and AWS Toolkit plugin.

## Getting Started
### IDE Setup
1. Open Eclipse
2. Install the AWS Toolkit for Eclipse and set up your AWS access key id and secret access key: http://docs.aws.amazon.com/AWSToolkitEclipse/latest/ug/tke_setup_install.html
3. Import project LambdaForm: Right click on the Package Explorer > Import > General > Existing Projects into Workspace. Do the same for LambdaSendMail and LambdaApproval.
4. Sign up for AWS Lambda, API Gateway, DynamoDB, and SES (if you have not done so, yet).

### Upload Lambda Functions
1. Right click on the LambdaForm project folder > Amazon Web Services > Upload Function to AWS Lambda > Follow the steps in the wizard and create a new Lambda Function.
2. Upload the Lambda Functions of the other two projects LambdaSendMail and LambdaApproval.
3. Give the Lambda Functions an IAM role that allows access to DynamoDB and SES.

### AWS Services setup
1. Set up a DynamoDB table named 'lambda-reimbursment' with the Hash Key 'employee_id' of type String. In the example, the EU-WEST-1 region (Ireland) is chosen.
2. In the AWS dashboard, go to Lambda > LambdaSendMail > Event source. Add an event source of type DynamoDB (table: lambda-reimbursment) with default settings.
3. Do the same (as in step 2) for LambdaApproval.
4. In the AWS dashboard, go to API Gateway and create a new API that points at the LambdaApproval Function. Create a Resource "reimbursment" and a GET method. Click on "Method Request" and add a query string parameter "id". Click on "Integration Request" and add a Mapping Template with content type "application/json" and Template:
```
{
    "employee_id" : "$input.params('id')"
}
```
Deploy the API.

### Run Lambda Functions
1. Right click on the LambdaForm project folder > Amazon Web Services > Run Function on AWS Lambda > Enter a JSON object like this one:
```{"employee_id":"1", "employee_name":"John Doh", "expense_type":"travel","amount": "456.75" }```
2. If the Lambda Function completes successfully, a new entry will be added to the DynamoDB table lambda-reimbursment that you created in step 1 of the AWS Services setup.
3. LambdaSendMail gets triggered by the DynamoDB stream (pull model) and sends an e-mail with the info that has been added to the table.
4. If you have received the e-mail and click on the approval URL in the e-mail body, the LambdaApproval Function will be called and add an "approved" column entry to DynamoDB.
