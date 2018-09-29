package com.itsmohitgoel.beatbuff.utils;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

import com.itsmohitgoel.beatbuff.listeners.PlaybackInfoListener;
import com.itsmohitgoel.beatbuff.listeners.PlayerAdapter;
import com.itsmohitgoel.beatbuff.models.MusicItem;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * This class implements an interface 'PlayerAdapter' which
 * allows the PlayerFragment to control playback functions.
 */
public class MediaPlayerHolder implements PlayerAdapter {
    private static final String TAG = MediaPlayerHolder.class.getSimpleName();

    private static final int PLAYBACK_POSITION_REFRESH_INTERVAL_MS = 100;

    private final Context mContext;
    private MediaPlayer mMediaPlayer;
    private PlaybackInfoListener mPlaybackInfoListener;
    private ScheduledExecutorService mExecutor;
    private Runnable mSeekbarPositionUpdateTask;

    public MediaPlayerHolder(Context context) {
        mContext = context.getApplicationContext();
    }

    @Override
    public void loadMedia(MusicItem musicItem) {
        initializeMediaPlayer();

        try {
            Log.i(TAG, "load() {1. setDataSource}");
            mMediaPlayer.setDataSource(mContext, musicItem.getUriPath());
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
            startUpdatingCallbackWithPosition();
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
            Log.d(TAG, "reset()");
        if (mMediaPlayer != null) {
            mMediaPlayer.reset();
        }
    }

    @Override
    public void seekTo(int position) {
        if (mMediaPlayer != null) {
            mMediaPlayer.seekTo(position);
        }
    }

    public void setPlaybackInfoListener(PlaybackInfoListener playbackInfoListener) {
        mPlaybackInfoListener = playbackInfoListener;
    }

    @Override
    public void initializeProgressCallback() {
            Log.d(TAG, "initializeProgressCallback");
        final int duration = mMediaPlayer.getDuration();
        if (mPlaybackInfoListener != null) {
            mPlaybackInfoListener.onDurationChanged(duration);
            mPlaybackInfoListener.onPositionChanged(0);
        }
    }

    /**
     * Initialize Media player and define listeners on it.
     */
    private void initializeMediaPlayer() {
        if (mMediaPlayer == null) {
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopUpdatingCallbackWithPosition(true);
                }
            });

            Log.d(TAG, "mMediaPlayer = new MediaPlayer()");
        }
    }

    /**
     * Provide visual feedback as audio playback progresses
     */
    private void startUpdatingCallbackWithPosition() {
        if (mExecutor == null) {
            mExecutor = Executors.newSingleThreadScheduledExecutor();
        }
        if (mSeekbarPositionUpdateTask == null) {
            mSeekbarPositionUpdateTask = new Runnable() {
                @Override
                public void run() {
                    try {

                        updateProgressCallbackTask();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
        }
        mExecutor.scheduleAtFixedRate(mSeekbarPositionUpdateTask,
                0,
                PLAYBACK_POSITION_REFRESH_INTERVAL_MS,
                TimeUnit.MILLISECONDS);
    }

    private void updateProgressCallbackTask() {
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            int currentPosition = mMediaPlayer.getCurrentPosition();
            if (mPlaybackInfoListener != null) {
                mPlaybackInfoListener.onPositionChanged(currentPosition);
            }
        }
    }

    /**
     * Update Player Screen's UI as playback stops
     */
    private void stopUpdatingCallbackWithPosition(boolean resetUIPlayBackPosition) {
        Log.d(TAG, " stopUpdatingCallbackWithPosition(" + resetUIPlayBackPosition + ")");
        if (mExecutor != null) {
            mExecutor.shutdownNow();
            mExecutor = null;
            mSeekbarPositionUpdateTask = null;
            if (resetUIPlayBackPosition && mPlaybackInfoListener != null) {
                mPlaybackInfoListener.onPositionChanged(0);
            }
        }
    }


}
