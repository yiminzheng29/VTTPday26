package sg.edu.nus.iss.app.workshop26.models;

import org.bson.Document;
import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Game {
    private String _id;
    private Integer gid;
    private String name;
    private Integer year;
    private Integer ranking;
    private Integer users_rated;
    private String url;
    private String image;
    
    public String get_id() {
        return _id;
    }
    public void set_id(String _id) {
        this._id = _id;
    }
    public Integer getGid() {
        return gid;
    }
    public void setGid(Integer gid) {
        this.gid = gid;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getYear() {
        return year;
    }
    public void setYear(Integer year) {
        this.year = year;
    }
    public Integer getRanking() {
        return ranking;
    }
    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }
    public Integer getUsers_rated() {
        return users_rated;
    }
    public void setUsers_rated(Integer users_rated) {
        this.users_rated = users_rated;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }

    // after this was included, the json could show the game details. Else, the json only shows the game memory
    @Override
    public String toString() {
        return "Game [gid= "
                .concat("" + this.gid)
                .concat(", name=")
                .concat(this.name)
                .concat(", year=")
                .concat("" + this.year)
                .concat(", ranking=")
                .concat("" + this.ranking)
                .concat(", usersRated=")
                .concat("" + this.users_rated)
                .concat(", url=")
                .concat(this.url)
                .concat(", image=")
                .concat(this.image)
                .concat("]");
    }

    public static Game create(Document d) {
        Game g = new Game();
        g.set_id(d.getObjectId("_id").toString());
        g.setGid(d.getInteger("gid"));
        g.setName(d.getString("name"));
        g.setYear(d.getInteger("year"));
        g.setRanking(d.getInteger("ranking"));
        g.setUsers_rated(d.getInteger("users_rated"));
        g.setUrl(d.getString("url"));
        g.setImage(d.getString("image"));

        return g;
    }

    public JsonObject toJson() {
        return Json.createObjectBuilder()
            .add("_id", this.get_id())
            .add("gid", this.getGid())
            .add("name", this.getName())
            .add("year", this.getYear())
            .add("ranking", this.getRanking())
            .add("users_rated", this.getUsers_rated() != null ? getUsers_rated() : 0)
            .add("url", this.getUrl())
            .add("image", this.getImage())
            .build();
    }
}
