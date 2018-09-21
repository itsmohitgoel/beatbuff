package com.itsmohitgoel.beatbuff.models;

import android.graphics.Bitmap;
import android.net.Uri;

import java.util.UUID;

public class MusicItem {
    private UUID mId;
    private String mTitle;
    private String mArtistName;
    private Bitmap mCoverArt;
    private String mDuration;
    private Uri mUriPath;


    public MusicItem() {
        mId = UUID.randomUUID();
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getArtistName() {
        return mArtistName;
    }

    public void setArtistName(String artistName) {
        mArtistName = artistName;
    }

    public Bitmap getCoverArt() {
        return mCoverArt;
    }

    public void setCoverArt(Bitmap coverArt) {
        mCoverArt = coverArt;
    }

    public String getDuration() {
        return mDuration;
    }

    public void setDuration(String duration) {
        mDuration = duration;
    }

    public Uri getUriPath() {
        return mUriPath;
    }

    public void setUriPath(Uri uriPath) {
        mUriPath = uriPath;
    }
}
