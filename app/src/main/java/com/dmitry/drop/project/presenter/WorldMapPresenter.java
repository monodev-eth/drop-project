package com.dmitry.drop.project.presenter;

import com.dmitry.drop.project.model.Post;
import com.dmitry.drop.project.view.ViewPostView;
import com.dmitry.drop.project.view.WorldMapView;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;

import java.util.List;

/**
 * Created by Laptop on 22/05/2016.
 */
public interface WorldMapPresenter extends MvpPresenter<WorldMapView> {

    void onMyLocationClicked();

    void onAddPostClick();

    void onMapClicked(List<Post> posts, double latitude, double longitude);

}
