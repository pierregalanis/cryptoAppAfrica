from ace_tools import display_dataframe_to_user
import pandas as pd

commands = [
{
"Step": "1. SSH into EC2",
"Command": "ssh -i africacrypto-key.pem ubuntu@3.148.173.108",
"Purpose": "Connect to the EC2 Ubuntu server securely using SSH and private key."
},
{
"Step": "2. Install Docker",
"Command": "sudo apt update && sudo apt install docker.io -y",
"Purpose": "Install Docker on the EC2 instance to enable containerization."
},
{
"Step": "3. Start Docker",
"Command": "sudo systemctl start docker && sudo systemctl enable docker",
"Purpose": "Start and enable Docker service to run on boot."
},
{
"Step": "4. Add User to Docker Group",
"Command": "sudo usermod -aG docker $USER",
"Purpose": "Add current user to Docker group to run docker without sudo."
},
{
"Step": "5. Install AWS CLI",
"Command": "sudo apt install awscli -y",
"Purpose": "Install AWS CLI to interact with AWS services (e.g. SSM for secrets)."
},
{
"Step": "6. Configure AWS CLI",
"Command": "aws configure",
"Purpose": "Set AWS access key, secret key, region, and output format for CLI usage."
},
{
"Step": "7. Create MySQL DB in RDS",
"Command": "CREATE DATABASE `africacrypto-db`;",
"Purpose": "Executed in RDS MySQL client to create the app's database."
},
{
"Step": "8. Build Spring Boot Project",
"Command": "mvn clean package",
"Purpose": "Build and package the Spring Boot backend into a JAR file."
},
{
"Step": "9. Copy JAR to EC2",
"Command": "scp -i africacrypto-key.pem target/africacrypto-0.0.1-SNAPSHOT.jar ubuntu@3.148.173.108:~",
"Purpose": "Securely copy the packaged JAR file from local to EC2 server."
},
{
"Step": "10. Dockerize Spring Boot App",
"Command": "docker build -t africacrypto-backend .",
"Purpose": "Build a Docker image for the Spring Boot app using the Dockerfile."
},
{
"Step": "11. Run Docker Container",
"Command": 'docker run -p 8080:8080 -e JWT_SECRET="your-secret" africacrypto-backend',
"Purpose": "Run the app container exposing port 8080 and setting JWT secret."
},
{
"Step": "12. Test App via curl",
"Command": 'curl -X POST http://<EC2-IP>:8080/api/auth/register -H "Content-Type: application/json" -d \'{"username":"aboh", "email":"aboh@example.com", "password":"Smash12!"}\'',
"Purpose": "Test the registration endpoint via terminal using curl."
},
{
"Step": "13. Get JWT Token",
"Command": 'curl -X POST http://<EC2-IP>:8080/api/auth/login -H "Content-Type: application/json" -d \'{"email":"aboh@example.com", "password":"Smash12!"}\'',
"Purpose": "Log in to the app and retrieve a JWT token from the API."
},
{
"Step": "14. Access Protected Endpoint",
"Command": 'curl -H "Authorization: Bearer <your_token>" http://<EC2-IP>:8080/api/user/watchlist',
"Purpose": "Call a secured API endpoint using JWT authorization header."
}
]

df = pd.DataFrame(commands)
display_dataframe_to_user(name="EC2 + Docker + Spring Boot Deployment Commands", dataframe=df)
