## Injection Not Working in Serverless Function

### Works in "app"
```bash
cd app
./gradlew shadowJar
sam local invoke -t sam_template.yml -e event.json
```

Output:
```bash
Invoking io.micronaut.function.aws.proxy.MicronautLambdaHandler (java11)
Decompressing /Users/charlieharvey/workspace/samples/micronaut-serverless-injection/app/build/libs/app-0.1-all.jar
Skip pulling image and use local one: amazon/aws-sam-cli-emulation-image-java11:rapid-1.6.2.

Mounting /private/var/folders/g1/d28hb7pn1258d97kscfq7_dc0000gn/T/tmp8b4bp1b_ as /var/task:ro,delegated inside runtime container
20:29:43.987 [main] INFO  i.m.f.a.p.AbstractLambdaContainerHandler - Starting Lambda Container Handler
20:29:44.512 [main] INFO  i.m.context.env.DefaultEnvironment - Established active environments: [ec2, cloud, function, lambda]
START RequestId: 2ff4ca62-16ee-1217-0240-4ef45d4716ce Version: $LATEST
END RequestId: 2ff4ca62-16ee-1217-0240-4ef45d4716ce
REPORT RequestId: 2ff4ca62-16ee-1217-0240-4ef45d4716ce	Init Duration: 12895.44 ms	Duration: 8390.56 ms	Billed Duration: 8400 ms	Memory Size: 512 MB	Max Memory Used: 111 MB

{"statusCode":200,"multiValueHeaders":{"Content-Type":["application/json"]},"body":"{\"name\":\"Building Microservices\",\"isbn\":\"8790c6e8-5394-4add-9b5f-d6bb8566822c\",\"thing\":\"awesome\"}","base64Encoded":false}
```

### Fails in "func"
```bash
cd app
./gradlew shadowJar
sam local invoke -t sam_template.yml -e event.json
```

Output:
```bash
Invoking example.micronaut.BookRequestHandler (java11)
Decompressing /Users/charlieharvey/workspace/samples/micronaut-serverless-injection/func/build/libs/func-0.1-all.jar
Skip pulling image and use local one: amazon/aws-sam-cli-emulation-image-java11:rapid-1.6.2.

Mounting /private/var/folders/g1/d28hb7pn1258d97kscfq7_dc0000gn/T/tmpx1v432n1 as /var/task:ro,delegated inside runtime container
20:32:47.357 [main] INFO  i.m.context.env.DefaultEnvironment - Established active environments: [ec2, cloud, function, lambda]
Failed to inject value for field [thing] of class: example.micronaut.BookRequestHandler

Message: Error resolving field value [${stuff.thing}]. Property doesn't exist or cannot be converted
Path Taken: BookRequestHandler.thing: io.micronaut.context.exceptions.DependencyInjectionException
io.micronaut.context.exceptions.DependencyInjectionException: Failed to inject value for field [thing] of class: example.micronaut.BookRequestHandler

Message: Error resolving field value [${stuff.thing}]. Property doesn't exist or cannot be converted
Path Taken: BookRequestHandler.thing
	at io.micronaut.context.AbstractBeanDefinition.getValueForField(AbstractBeanDefinition.java:1258)
	at example.micronaut.$BookRequestHandlerDefinition.injectBean(Unknown Source)
	at io.micronaut.context.AbstractBeanDefinition.inject(AbstractBeanDefinition.java:339)
	at io.micronaut.context.DefaultBeanContext.doInject(DefaultBeanContext.java:2150)
	at io.micronaut.context.DefaultBeanContext.inject(DefaultBeanContext.java:789)
	at io.micronaut.function.aws.MicronautRequestHandler.<init>(MicronautRequestHandler.java:49)
	at example.micronaut.BookRequestHandler.<init>(BookRequestHandler.kt:9)
	at java.base/jdk.internal.reflect.NativeConstructorAccessorImpl.newInstance0(Native Method)
	at java.base/jdk.internal.reflect.NativeConstructorAccessorImpl.newInstance(Unknown Source)
	at java.base/jdk.internal.reflect.DelegatingConstructorAccessorImpl.newInstance(Unknown Source)
	at java.base/java.lang.reflect.Constructor.newInstance(Unknown Source)

START RequestId: 109f4930-d6f5-1608-4d6b-217b4768ca33 Version: $LATEST
END RequestId: 109f4930-d6f5-1608-4d6b-217b4768ca33
REPORT RequestId: 109f4930-d6f5-1608-4d6b-217b4768ca33	Init Duration: 4330.29 ms	Duration: 4.26 ms	Billed Duration: 100 ms	Memory Size: 512 MB	Max Memory Used: 75 MB

{"errorType":"io.micronaut.context.exceptions.DependencyInjectionException","errorMessage":"Failed to inject value for field [thing] of class: example.micronaut.BookRequestHandler\n\nMessage: Error resolving field value [${stuff.thing}]. Property doesn't exist or cannot be converted\nPath Taken: BookRequestHandler.thing"}
```