package io.benaychh.ticketsales
import com.amazonaws.services.lambda.runtime.*


fun contextFactory(
        awsRequestId: String = "aws-request-id",
        logGroupName: String = "log-group-name",
        logStreamName: String = "log-stream-name",
        functionName: String = "function-name",
        functionVersion: String = "function-version",
        invokedFunctionArn: String = "invoked-function-arn",
        cognitoIdentityId: String = "cognito-identity-id",
        cognitoIdentityPoolId: String = "cognito-identity-pool-id",
        clientContextInstallationId: String = "client-installation-id",
        clientContextAppTitle: String = "client-app-title",
        clientContextAppVersionName: String = "client-app-version-name",
        clientContextAppVersionCode: String = "client-app-version-code",
        clientContextAppPackageName: String = "client-app-package-name",
        clientCustom: Map<String, String> = HashMap<String, String>(),
        clientEnvironment: Map<String, String> = HashMap<String, String>(),
        remainingTimeInMillis: Int = 1000 * 60,
        memoryLimitInMb: Int = 500,
        loggingFunction: (line: String) -> Unit = { string -> println(string)}
        ): Context {
    val cognitoIdentity = object: CognitoIdentity {
        override fun getIdentityId(): String = cognitoIdentityId;
        override fun getIdentityPoolId(): String = cognitoIdentityPoolId
    }

    val clientContextClient = object: Client {
        override fun getInstallationId(): String = clientContextInstallationId
        override fun getAppTitle(): String = clientContextAppTitle
        override fun getAppVersionName(): String = clientContextAppVersionName
        override fun getAppVersionCode(): String = clientContextAppVersionCode
        override fun getAppPackageName(): String = clientContextAppPackageName
    }

    val clientContext = object: ClientContext {
        override fun getClient(): Client = clientContextClient
        override fun getCustom(): Map<String, String> = clientCustom
        override fun getEnvironment(): Map<String, String> = clientEnvironment
    }

    val lambdaLogger = object: LambdaLogger {
        override fun log(line: String) { loggingFunction(line) }
    }

    return object: Context {
        override fun getAwsRequestId(): String = awsRequestId
        override fun getLogGroupName(): String = logGroupName
        override fun getLogStreamName(): String = logStreamName
        override fun getFunctionName(): String = functionName
        override fun getFunctionVersion(): String = functionVersion
        override fun getInvokedFunctionArn(): String = invokedFunctionArn
        override fun getIdentity(): CognitoIdentity = cognitoIdentity
        override fun getClientContext(): ClientContext = clientContext
        override fun getRemainingTimeInMillis(): Int = remainingTimeInMillis
        override fun getMemoryLimitInMB(): Int = memoryLimitInMb
        override fun getLogger(): LambdaLogger = lambdaLogger
    }
}