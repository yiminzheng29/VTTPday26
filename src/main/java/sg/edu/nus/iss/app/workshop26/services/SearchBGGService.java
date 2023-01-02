package sg.edu.nus.iss.app.workshop26.services;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.app.workshop26.models.Comment;
import sg.edu.nus.iss.app.workshop26.models.Game;
import sg.edu.nus.iss.app.workshop26.repositories.CommentRepository;
import sg.edu.nus.iss.app.workshop26.repositories.GameRepository;

@Service
public class SearchBGGService {
    
    @Autowired
    private GameRepository gameRepo;

    @Autowired
    private CommentRepository commentRepo;

    public List<Game> searchAllGames(Integer limit, Integer offset) {
        return (List<Game>) gameRepo.searchAllGames(limit, offset);
    }

    public List<Game> getGamesByRank() {
        return (List<Game>) gameRepo.getGamesByRank();
    }

    public Game getGameDetailsById(Integer gameId) {
        return gameRepo.getGameDetailsById(gameId);
    }

    public List<Comment> searchCommentByKeyword(String s, int limit, int offset) {

        List<String> includes = new LinkedList<>();
        List<String> excludes = new LinkedList<>();
        
        for (String w : s.split(" ")) {
            if (w.startsWith("-")) {
                excludes.add(w.split("-")[1]);
            } else {
                includes.add(w);
            }
        }

        return commentRepo.searchCommentByText(includes, excludes, limit, offset);
    }
}
