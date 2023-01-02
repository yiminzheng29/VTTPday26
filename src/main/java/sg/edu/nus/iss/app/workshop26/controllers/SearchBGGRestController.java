package sg.edu.nus.iss.app.workshop26.controllers;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import sg.edu.nus.iss.app.workshop26.models.Comment;
import sg.edu.nus.iss.app.workshop26.models.Game;
import sg.edu.nus.iss.app.workshop26.models.Games;
import sg.edu.nus.iss.app.workshop26.services.SearchBGGService;

@RestController
@RequestMapping(path="/api/bgg")
public class SearchBGGRestController {
    
    @Autowired
    private SearchBGGService bggSvc;

    @GetMapping(path="/games") // http://localhost:8080/api/bgg/games?limit=10&offset=3
    public ResponseEntity<String> getAllGames(@RequestParam Integer limit, @RequestParam Integer offset) {
        List<Game> gamesResult = bggSvc.searchAllGames(limit, offset);
        JsonObject result = null;

        // Build the result
        JsonObjectBuilder builder = Json.createObjectBuilder();
        Games gs = new Games();
        gs.setGames(gamesResult);
        gs.setLimit(limit);
        gs.setOffset(offset);
        gs.setTotal(gamesResult.size());
        gs.setTimestamp(DateTime.now());

        builder.add("bgg", gs.toJson());
        result = builder.build();
        return ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(result.toString());
    }

    // http://localhost:8080/api/bgg/games/rank
    @GetMapping(path="/games/rank")
    public ResponseEntity<String> getGamesByRank() {
        JsonArray result = null;
        List<Game> gamesByRank = bggSvc.getGamesByRank();
        JsonArrayBuilder builder = Json.createArrayBuilder();
        for (Game g: gamesByRank) {
            builder.add(g.toJson());
        }
        result = builder.build();
        return ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(result.toString());
    }

    // http://localhost:8080/api/bgg/games/3
    @GetMapping(path="/games/{gameId}")
    public ResponseEntity<String> getGamesById(@PathVariable Integer gameId) {
        JsonObject result = null;
        Game game = bggSvc.getGameDetailsById(gameId);
        System.out.println("gameId: " + gameId);
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("game", game.toJson());
        result = builder.build();

        return ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(result.toString());
    }

    // http://localhost:8080/api/bgg/comment?q="Fun"&limit=10&offset=0
    // Remember to set index in mongoshell
    @GetMapping(path="/comment")
    public ResponseEntity<String> searchComment(@RequestParam String q, @RequestParam Integer limit, @RequestParam Integer offset) {
        List<Comment> cResults = bggSvc.searchCommentByKeyword(q, limit, offset);
        JsonArray results = null;
        JsonArrayBuilder builder = Json.createArrayBuilder();
        for (Comment c: cResults) {
            builder.add(c.toJson());
        }
        results = builder.build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(results.toString());
    }
}
