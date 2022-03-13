# EN605789-service-api-design
## Heroku deploy
- Click settings
- Add a new Config Var "TARGET_WAR"
- Point it to the desired WAR (e.g., key=TARGET_WAR, value=Module6/target/*.war)

## Embedded tomcat deploy
- cd \<Module dir\> & mvn package cargo:run