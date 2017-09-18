package polina.example.com.movies.Models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.os.ParcelableCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by polina on 9/13/17.
 */

public class Movie implements Parcelable {

    int id;
    String title;
    String image_port;
    String image_land;
    float rating;
    List<Integer> genre;
    String description;

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };


    public Movie() {
    }

    public Movie(Parcel in) {
        id=in.readInt();
        title = in.readString();
        image_port = in.readString();
        image_land = in.readString();
        rating = in.readFloat();
        genre = new ArrayList<>();
        in.readList(genre, Integer.class.getClassLoader());
        description = in.readString();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(image_port);
        parcel.writeString(image_land);
        parcel.writeFloat(rating);
        parcel.writeList(genre);
        parcel.writeString(description);
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
        return .5f*rating;
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
