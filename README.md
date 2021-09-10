# Configure AWS SAM to run serverless applications in your local environment(JAVA/Quarkus)
AWS Serverless examples using SAM

### Step 1
First of all lets install the aws-cli v2.0, follow the commands:

```
$ curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip"
$ unzip awscliv2.zip
$ sudo ./aws/install
```

Now lets check if the instalation was successful by running the following command:

```
$ aws --version
```
You should see something like this:

```
aws-cli/2.1.29 Python/3.7.4 Linux/4.14.133-113.105.amzn2.x86_64 botocore/2.0.0
```

You can now configure your [aws credentials](https://docs.aws.amazon.com/serverless-application-model/latest/developerguide/serverless-getting-started-set-up-credentials.html).


### Step 2

Now we can install the Docker, so you can run aws container images. You can install Docker by following the [official get-docker page](https://docs.docker.com/get-docker/).

### Step 3

We can now install the AWS SAM CLI.

First lets download the [zip file](https://github.com/aws/aws-sam-cli/releases/latest/download/aws-sam-cli-linux-x86_64.zip).

Then unzip the installation file
```
$ unzip aws-sam-cli-linux-x86_64.zip -d sam-installation
```
Install the AWS SAM CLI.
```
$ sudo ./sam-installation/install
```
Verify the installation
```
$ sam --version
```

## Setup your JAVA environment

- Install JAVA 11
- Install Maven
- Install and configure the GraalVM 10 so we can build our native image

After configuring your development environment we can now test a sample application.
We can use a Quarkus Maven Archetype, on your cli just put:
```
mvn archetype:generate \
       -DarchetypeGroupId=io.quarkus \
       -DarchetypeArtifactId=quarkus-amazon-lambda-archetype \
       -DarchetypeVersion=2.2.2.Final
```

Start your Docker:
```
sudo service docker start
```

Now just run the following command:
```
sam local invoke --template target/sam.jvm.yaml --event payload.json
```
---
** If you get the following error from Docker after trying invoke a lambda:
```
ERROR: Got permission denied while trying to connect to the Docker daemon socket at unix:///var/run/docker.sock
```
Grant the followin permissions for Docker:
```
sudo chmod 666 /var/run/docker.sock
```
----

If all goes right, you should see something like this as output from your sample lambda function:
```
{"result":"hello Bill","requestId":"c426ea01-d149-43fa-aed2-b50f36b50506"}
```

## Create a Docker Network
We need to create a Docker network were we gonna run our local application:
```
docker network create sam-demo
```

### Lets integrate with DynamoDB

First let's install the DynamoDB imagem:
```
docker pull amazon/dynamodb-local
```

After that we can start our DynamoDB container:
```
docker run -d -v "$PWD":/dynamodb_local_db -p 8000:8000 --network sam-demo --name dynamodb amazon/dynamodb-local
```

(Optional) GUI for DynamoDB
```
npm install -g dynamodb-admin
```

Set up the endpoint on the application, pointing to the network that we previously set, i.e:
```
quarkus.dynamodb.endpoint-override=http://dynamodb:8000
```

Now we can run our Lambda with the following command:
```
sam local invoke --docker-network sam-demo --template target/sam.jvm.yaml --event payload.json
```
