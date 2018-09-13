package com.itsmohitgoel.beatbuff.utils;

import android.content.ContentUris;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;

import com.itsmohitgoel.beatbuff.R;
import com.itsmohitgoel.beatbuff.listeners.PlaybackInfoListener;
import com.itsmohitgoel.beatbuff.listeners.PlayerAdapter;

import java.io.IOException;

public class MediaPlayerHolder implements PlayerAdapter {
    private static final String TAG = MediaPlayerHolder.class.getSimpleName();

    private final Context mContext;
    private MediaPlayer mMediaPlayer;
    private int mResourceId;
    private PlaybackInfoListener mPlaybackInfoListener;

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

        initializeProgressCallback();
    }

    @Override
    public void release() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    @Override
    public boolean isPlaying() {
        if (mMediaPlayer != null) {
            return mMediaPlayer.isPlaying();
        }
        return false;
    }

    @Override
    public void play() {
        if (mMediaPlayer != null && !mMediaPlayer.isPlaying()) {
            mMediaPlayer.start();
        }
    }

    @Override
    public void pause() {
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            Log.d(TAG, "playbackPause()");
        }
    }

    @Override
    public void reset() {
        if (mMediaPlayer != null) {
            mMediaPlayer.reset();
            loadMedia(mResourceId);
        }
    }

    @Override
    public void seekTo(int position) {
        if (mMediaPlayer != null) {
            mMediaPlayer.seekTo(position);
        }
    }

    @Override
    public void previous() {

    }

    @Override
    public void next() {

    }

    public void setPlaybackInfoListener(PlaybackInfoListener playbackInfoListener) {
        mPlaybackInfoListener = playbackInfoListener;
    }

    private void initializeMediaPlayer() {
        if (mMediaPlayer == null) {
            mMediaPlayer = new MediaPlayer();

            Log.i(TAG, "mMediaPlayer = new MediaPlayer()");
        }
    }

    @Override
    public void initializeProgressCallback() {
        final int duration = mMediaPlayer.getDuration();
        if (mPlaybackInfoListener != null) {
            mPlaybackInfoListener.onDurationChanged(duration);
            mPlaybackInfoListener.onPositionChanged(0);
        }
    }


}
