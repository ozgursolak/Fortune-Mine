# Fortune-Mines-Case-Study

It is a reward mechanism application.On this app, a client is able to award players after specific events.

## Important For Setup: 
 
* The app is configured to connect to the _**fortune_mine**_ database which served on port **5448**.
If you want to change, you can do it on **_application.properties_**.

* After booting the app, you can send a get request to **/players/initialize** endpoint in order to initialize the db.
* This endpoint will create records for player, reward, wallet_reward, upcoming_reward and level_completion_reward tables.Afterwards,
* you will be able to use the endpoints which are serving on 8080 port. 
