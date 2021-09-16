# Configure AWS SAM to run serverless applications in your local environment(JAVA/Quarkus)

## Prerequisite

I will assume that you already have a running Java 11 environment with Maven set up.

You also gonna need to install and set up a GraalVM runtime. The GraalVM can be installed with [homebrew](https://docs.brew.sh/Installation):

```
$ brew install --cask graalvm/tap/graalvm-ce-lts-java11
```

Now that we have to configure:
 - AWS-CLI
 - SAM-CLI
 - DOCKER

### Step 1 - Install AWS-CLI
First of all lets install the aws-cli v2.0, follow the commands:

##### On Linux
```
$ curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip"
$ unzip awscliv2.zip
$ sudo ./aws/install
```

##### On MacOS
```
$ brew install awscli
```

Now lets check if the instalation was successful by running the following command:

```
$ aws --version
```
You should see something like this:

```
$ aws-cli/2.1.29 Python/3.7.4 ...
```

You can now configure your [aws credentials](https://docs.aws.amazon.com/serverless-application-model/latest/developerguide/serverless-getting-started-set-up-credentials.html).

### Step 2 - Install [SAM-CLI](https://docs.aws.amazon.com/serverless-application-model/latest/developerguide/what-is-sam.html)

We can now install the AWS SAM CLI.

##### On Linux
First lets download the [zip file](https://github.com/aws/aws-sam-cli/releases/latest/download/aws-sam-cli-linux-x86_64.zip).

Then unzip the installation file
```
$ unzip aws-sam-cli-linux-x86_64.zip -d sam-installation
```
Install the AWS SAM CLI.
```
$ sudo ./sam-installation/install
```

##### On MacOS
```
$ pip install aws-sam-cli
```

Verify the installation
```
$ sam --version
```


### Step 3 - Install Docker 

Now we can install the Docker, so we can run our aws container images locally. You can install Docker by following the [official get-docker page](https://docs.docker.com/get-docker/).



## Setup our JAVA project


After configuring your development environment we can now test a sample application.
We can use a Quarkus Maven Archetype, on your cli just put:
```
$ mvn archetype:generate \
       -DarchetypeGroupId=io.quarkus \
       -DarchetypeArtifactId=quarkus-amazon-lambda-archetype \
       -DarchetypeVersion=2.2.2.Final
```

Start your Docker:
```
$ sudo service docker start
```

Now just run the following command:
```
$ sam local invoke --template target/sam.jvm.yaml --event payload.json
```
---
** If you get the following error from Docker after trying invoke a lambda:
```
$ ERROR: Got permission denied while trying to connect to the Docker daemon socket at unix:///var/run/docker.sock
```
Grant the following permissions for Docker:
```
$ sudo chmod 666 /var/run/docker.sock
```
----

If all goes right, you should see something like this as output from your sample lambda function:
```
{"result":"hello Bill","requestId":"c426ea01-d149-43fa-aed2-b50f36b50506"}
```

### Let's integrate our application with DynamoDB

First let's install the DynamoDB docker imagem:
```
$ docker pull amazon/dynamodb-local
```

We also gonna need to create a Docker network so our containers can interact with each other:
```
$ docker network create sam-demo
```

After that we can start our DynamoDB container:
```
$ docker run -d -v "$PWD":/dynamodb_local_db -p 8000:8000 --network sam-demo --name dynamodb amazon/dynamodb-local
```
Note that we set a __--name__ AND a __--network__ to the container


> (Optional) GUI for DynamoDB
>```
>$ npm install -g dynamodb-admin
>```

Set up the endpoint on the application.properties of the project, pointing to the **--name** that we previously gave to our DynamoDB docker container, i.e:
```
quarkus.dynamodb.endpoint-override=http://dynamodb:8000
```

Now we can run our application with the following command:
```
$ sam local invoke --docker-network sam-demo --template target/sam.jvm.yaml --event payload.json
```
