package sg.edu.nus.iss.app.workshop26.repositories;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.app.workshop26.models.Comment;

@Repository
public class CommentRepository {
    
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Comment> search(List<String> includes, List<String> excludes) {
        return search(includes, excludes, 10, 0);
    }

    private List<Comment> search(List<String> includes, List<String> excludes, int i, int j) {
        return null;
    }

    public List<Comment> search(List<String> includes, List<String> excludes, Integer limit) {
        return search(includes, excludes, limit, 0);
    }
    
    public List<Comment> searchCommentByText(List<String> includes,
    List<String> excludes, int limit, int offset) {

    TextCriteria tc = TextCriteria.forDefaultLanguage()
    .matchingAny(includes.toArray(new String[includes.size()]))
    .notMatchingAny(excludes.toArray(new String[excludes.size()]));

    TextQuery tq = (TextQuery) TextQuery.queryText(tc)
        .includeScore()
        .sortByScore()
        .limit(limit)
        .skip(offset);

    return mongoTemplate.find(tq, Document.class, "comments")
        .stream()
        .map(c -> Comment.create(c))
        .toList();
    }
}
