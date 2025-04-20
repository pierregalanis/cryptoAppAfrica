docker run -p 8080:8080 \
-e JWT_SECRET=$(aws ssm get-parameter \
--name africacrypto-jwt-secret \
--with-decryption \
--query Parameter.Value \
--output text \
--region us-east-1) \
africacrypto-backend
curl http://localhost:8080/api/auth/register-test

On Windows, run this in PowerShell:

powershell
Copy
Edit
netstat -ano | findstr :8080
Then:

powershell
Copy
Edit
taskkill /PID <PID> /F
Replace <PID> with the process ID from the output.

To build docker image
docker build -t africacrypto-backend .

# On your local machine
./mvnw clean package

# Then re-copy the JAR to your EC2 instance:
scp -i africacrypto-key.pem target/africacrypto-0.0.1-SNAPSHOT.jar ubuntu@3.148.173.108:~

# Then on EC2 again, rebuild Docker
docker build -t africacrypto-backend .

# And rerun the container
docker run -p 8080:8080 \
-e JWT_SECRET=$(aws ssm get-parameter \
--name africacrypto-jwt-secret \
--with-decryption \
--query Parameter.Value \
--output text \
--region us-east-1) \
africacrypto-backend

to run it
docker build -t africacrypto-backend .


./mvnw clean package
Copy to EC2:

bash
Copy
Edit
scp -i africacrypto-key.pem target/africacrypto-0.0.1-SNAPSHOT.jar ubuntu@3.148.173.108:~
SSH into EC2, and then:

bash
Copy
Edit
docker build -t africacrypto-backend .
Run it again:

bash
Copy
Edit
docker run -p 8080:8080 \
-e JWT_SECRET=$(aws ssm get-parameter \
--name africacrypto-jwt-secret \
--with-decryption \
--query Parameter.Value \
--output text \
--region us-east-1) \
africacrypto-backend


