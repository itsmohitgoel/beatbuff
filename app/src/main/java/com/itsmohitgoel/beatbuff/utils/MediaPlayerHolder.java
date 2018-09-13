package com.itsmohitgoel.beatbuff.utils;

import android.content.ContentUris;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;

import com.itsmohitgoel.beatbuff.R;
import com.itsmohitgoel.beatbuff.listeners.PlayerAdapter;

import java.io.IOException;

public class MediaPlayerHolder implements PlayerAdapter{
    private static final String TAG = MediaPlayerHolder.class.getSimpleName();

    private final Context mContext;
    private MediaPlayer mMediaPlayer;
    private int mResourceId;

    public MediaPlayerHolder(Context context) {
        mContext = context.getApplicationContext();
    }

    @Override
    public void loadMedia(int resourceId) {
        mResourceId = resourceId;

        initializeMediaPlayer();

        Uri baseUriForAudioFiles = Uri.parse("android.resource://" + mContext.getPackageName() + "/raw");
        Uri uri = ContentUris.withAppendedId(baseUriForAudioFiles, mResourceId);
        try {
            Log.i(TAG, "load() {1. setDataSource}");
            mMediaPlayer.setDataSource(mContext, uri);
        } catch (IOException ioe) {
            Log.e(TAG, "Cannot load data source in player: setDataSource() failed", ioe);
        }

        try {
            Log.i(TAG, "load() {2. prepare}");
            mMediaPlayer.prepare();
        } catch (IOException ioe) {
            Log.e(TAG, "Cannot prepare mMediaPlayer: prepare() failed", ioe);
        }

    }

    @Override
    public void release() {
    }

    @Override
    public boolean isPlaying() {
        return false;
    }

    @Override
    public void play() {
        mMediaPlayer.start();
    }

    @Override
    public void pause() {

    }

    @Override
    public void reset() {

    }

    @Override
    public void initializeProgressCallback() {

    }

    @Override
    public void seekTo(int position) {

    }

    private void initializeMediaPlayer() {
        if (mMediaPlayer == null) {
            mMediaPlayer = new MediaPlayer();

            Log.i(TAG, "mMediaPlayer = new MediaPlayer()");
        }
    }
}
