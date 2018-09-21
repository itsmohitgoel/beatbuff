package com.itsmohitgoel.beatbuff.listeners;

import com.itsmohitgoel.beatbuff.models.MusicItem;

/**
 * Allows @PlayerActivity to control media playback of @MediaPlayerHolder.
 */
public interface PlayerAdapter {

    void loadMedia(MusicItem musicItem);

    void release();

    boolean isPlaying();

    void play();

    void pause();

    void reset();

    void initializeProgressCallback();

    void seekTo(int position);

    void previous();

    void next();
}
