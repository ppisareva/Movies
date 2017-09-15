package polina.example.com.movies.Models;

import java.util.List;

/**
 * Created by polina on 9/13/17.
 */

public class Movie {

    int id;
    String title;
    String image_port;
    String image_land;
    float rating;
    List<Integer> genre;
    String description;

    public Movie(int id, String title, String image_port, String image_land, float rating, List<Integer> genre, String description) {
        this.id = id;
        this.title = title;
        this.image_port = image_port;
        this.image_land = image_land;
        this.rating = rating;
        this.genre = genre;
        this.description = description;
    }

    public Movie() {
    }

    public String getImage_land() {
        return image_land;
    }

    public void setImage_land(String image_land) {
        this.image_land = image_land;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage_port() {
        return image_port;
    }

    public void setImage_port(String image_port) {
        this.image_port = image_port;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public List<Integer> getGenre() {
        return genre;
    }

    public void setGenre(List<Integer> genre) {
        this.genre = genre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
