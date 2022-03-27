Module9 is configured for API testing via Postman of Module7.

To run tests:
1) Open Postman
2) Click 'Collections'
3) Import DJ - Collection 1.postman_collection
4) Import DJ - Collection 2.postman_collection
5) If built locally, will need to update the environment var for baseUrl to 'http://localhost:8080/Module7'. Otherwise, ignore this step.
6) Select 'DJ - Collection 2.postman_collection' and click 'Run'

To build and deploy locally:
- cd EN605789-service-api-design/Module7 & mvn package cargo:run