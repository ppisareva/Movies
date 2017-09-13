package polina.example.com.movies.Models;

import java.util.List;

/**
 * Created by polina on 9/13/17.
 */

public class Movie {

    int id;
    String title;
    String image;
    double rating;
    List<Integer> genre;
    String description;

    public Movie(int id, String title, String image, double rating, List<Integer> genre, String description) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.rating = rating;
        this.genre = genre;
        this.description = description;
    }

    public Movie() {
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
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
