# Configure AWS SAM to run serverless applications in your local environment 
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
