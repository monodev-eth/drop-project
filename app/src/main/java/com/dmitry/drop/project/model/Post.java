package com.dmitry.drop.project.model;

import android.location.Location;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by Laptop on 20/05/2016.
 */

@Table(name = "Posts")
public class Post extends Model {

    final double INIT_RADIUS = 100;
    @Column(name = "ImageFilePath")
    String imageFilePath;
    @Column(name = "ThumbnailFilePath")
    String thumbnailFilePath;
    @Column(name = "Annotation")
    String annotation;
    @Column(name = "Latitude")
    double latitude;
    @Column(name = "Longitude")
    double longitude;
    @Column(name = "Radius")
    double radius;
    //TODO: Dynamic Radius for posts

    @Column(name = "Date")
    String dateCreated;

    // Needed for ActiveAndroid library
    public Post() {
        super();
    }

    public Post(String annotation, String imageFilePath, String thumbnailFilePath, double latitude, double longitude, String dateCreated) {
        super();
        this.annotation = annotation;
        this.imageFilePath = imageFilePath;
        this.thumbnailFilePath = thumbnailFilePath;
        this.latitude = latitude;
        this.longitude = longitude;
        this.dateCreated = dateCreated;
        radius = INIT_RADIUS;
    }

    public static List<Post> getAll() {
        return new Select().from(Post.class).execute();
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public String getImageFilePath() {
        return imageFilePath;
    }

    public void setImageFilePath(String imageFilePath) {
        this.imageFilePath = imageFilePath;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LatLng getLatLng() {
        return new LatLng(latitude, longitude);
    }

    public List<Reply> replies() {
        return getMany(Reply.class, "Post");
    }

    public String getThumbnailFilePath() {
        return thumbnailFilePath;
    }

    public boolean isWithinRadius(double latitude, double longitude) {
        float[] distance = new float[1];
        Location.distanceBetween(latitude, longitude, this.latitude, this.longitude, distance);
        return distance[0] < radius;
    }
}
