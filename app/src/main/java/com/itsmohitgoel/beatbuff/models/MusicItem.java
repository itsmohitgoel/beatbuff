package com.itsmohitgoel.beatbuff.models;

import java.util.UUID;

public class MusicItem {
    private UUID mId;
    private String mTitle;
    private String mArtistName;
    private String mAlbumName;
    private String mPlaylist;
    private String mThumbnailImageId;
    private short mDuration;


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

    public String getAlbumName() {
        return mAlbumName;
    }

    public void setAlbumName(String albumName) {
        mAlbumName = albumName;
    }

    public String getPlaylist() {
        return mPlaylist;
    }

    public void setPlaylist(String playlist) {
        mPlaylist = playlist;
    }

    public String getThumbnailImageId() {
        return mThumbnailImageId;
    }

    public void setThumbnailImageId(String thumbnailImageId) {
        mThumbnailImageId = thumbnailImageId;
    }

    public short getDuration() {
        return mDuration;
    }

    public void setDuration(short duration) {
        mDuration = duration;
    }
}
