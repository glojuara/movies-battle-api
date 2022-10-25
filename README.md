# Read Me
* [Open API Documentation](http://localhost:8080/swagger-ui/index.html)
* [Console H2](http://localhost:8080/h2-console/)


## Game Rules

- The player needs be authenticated to start the game
- After start the game, the player can access the quiz 
- On the quiz the player needs chose between two movies
- If the movie chosen is the highest scoring movie the player won points and can continue
- In each game the player can wrong 3 times
- If the player wrong 3 times, the game is ended 
- When the game is ended the score of player is calculated based on the rule: score = hits * ((hits / answers) * 100 )

### Additional Information
3 users will be loaded to the database when the application start.
You can add new user modifying the class br.com.ada.moviesbattleapi.infrastructure.repository.seed.UserSeed

- username: player1
- password: pwd1


- username: player2
- password: pwd2


- username: player3
- password: pwd3

The movies are loaded from data.sql into main/resources
