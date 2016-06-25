package com.dmitry.drop.project;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.EditText;
import android.widget.ImageView;

import com.dmitry.drop.project.model.Post;
import com.dmitry.drop.project.model.PostModelImpl;
import com.dmitry.drop.project.presenter.CreatePostPresenter;
import com.dmitry.drop.project.presenter.CreatePostPresenterImpl;
import com.dmitry.drop.project.utility.Constants;
import com.dmitry.drop.project.view.CreatePostView;
import com.hannesdorfmann.mosby.mvp.MvpActivity;

import java.text.DateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Laptop on 7/06/2016.
 */
public class CreatePostActivity extends MvpActivity<CreatePostView, CreatePostPresenter>
        implements
        CreatePostView {

    private static final int REQUEST_CAMERA_PHOTO = 1;
    @BindView(R.id.createPost_cameraImg)
    ImageView cameraImg;
    @BindView(R.id.createPost_annotation)
    EditText annotation;
    @BindView(R.id.createPost_dropButton)
    ImageView dropButton;
    private double mLongitude;
    private double mLatitude;
    private String mCameraImageFilePath;
    private String mThumbnailImageFilePath;

    public static Intent createIntent(Context context, double latitude, double longitude) {
        Intent intent = new Intent(context, CreatePostActivity.class);
        intent.putExtra(LATITUDE_EXTRA, latitude);
        intent.putExtra(LONGITUDE_EXTRA, longitude);
        return intent;
    }

    @NonNull
    @Override
    public CreatePostPresenter createPresenter() {
        return new CreatePostPresenterImpl(new PostModelImpl());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
        ButterKnife.bind(this);

        mLatitude = getIntent().getDoubleExtra(LATITUDE_EXTRA, 0);
        mLongitude = getIntent().getDoubleExtra(LONGITUDE_EXTRA, 0);

        Intent takePhoto = CameraActivity.createIntent(this);
        startActivityForResult(takePhoto, REQUEST_CAMERA_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CAMERA_PHOTO && resultCode == RESULT_OK) {
            mCameraImageFilePath = data.getStringExtra(CameraActivity.CAMERA_IMG_FILE_PATH);
            mThumbnailImageFilePath = data.getStringExtra(CameraActivity.THUMBNAIL_IMG_FILE_PATH);
            Bitmap photo = BitmapFactory.decodeFile(mCameraImageFilePath);
            cameraImg.setImageBitmap(photo);
        } else if (requestCode == REQUEST_CAMERA_PHOTO && resultCode == RESULT_CANCELED) {
            finish();
        }
    }

    @OnClick(R.id.createPost_cameraImg)
    public void onDropButtonClick() {
        String annotationText = annotation.getText().toString();
        String date = DateFormat.getInstance().format(new Date());
        presenter.onDropButtonClick(annotationText, mCameraImageFilePath, mThumbnailImageFilePath,
                mLatitude, mLongitude, date);
    }

    @Override
    public void returnToWorldMap(Post post) {
        Intent worldMapIntent = new Intent();
        worldMapIntent.putExtra(LATITUDE_EXTRA, mLatitude);
        worldMapIntent.putExtra(LONGITUDE_EXTRA, mLongitude);
        worldMapIntent.putExtra(POST_EXTRA, post);
        setResult(RESULT_OK, worldMapIntent);
        finish();
    }
}
