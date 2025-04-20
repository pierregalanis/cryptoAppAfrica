To get: To find the public IP address of your EC2 instance with ID i-044d2c6e7f3796dac in the us-east-2 region, you can use the AWS CLI as follows:
aws ec2 describe-instances \
--instance-ids i-044d2c6e7f3796dac \
--region us-east-2 \
--query "Reservations[].Instances[].PublicIpAddress" \
--output text

AMI Type	Default SSH Username
Amazon Linux / Amazon Linux 2	ec2-user
Ubuntu (any version)	ubuntu
Debian	admin or debian
RHEL	ec2-user
CentOS	centos

sudo apt update
sudo apt install -y docker.io
sudo systemctl start docker
sudo systemctl enable docker

./mvnw clean package -DskipTests
scp -i africacrypto-key.pem target/africacrypto-0.0.1-SNAPSHOT.jar ubuntu@3.148.173.108:~
docker build -t africacrypto-backend .
docker run -p 8080:8080 \
-e JWT_SECRET=$(aws ssm get-parameter \
--name africacrypto-jwt-secret \
--with-decryption \
--query Parameter.Value \
--output text \
--region us-east-1) \
africacrypto-backend

This command copies files from your local machine to your EC2 Ubuntu instance using scp (Secure Copy Protocol):
scp -i africacrypto-key.pem Dockerfile target/africacrypto-0.0.1-SNAPSHOT.jar ubuntu@3.148.173.108:~

To login into EC@ ubunto instance ( not ec2-user)::
ssh -i africacrypto-key.pem ubuntu@3.148.173.108



