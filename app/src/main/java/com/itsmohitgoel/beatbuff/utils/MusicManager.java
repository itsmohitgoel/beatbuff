package com.itsmohitgoel.beatbuff.utils;

import com.itsmohitgoel.beatbuff.models.MusicItem;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MusicManager {
    private static MusicManager sMusicManager;
    private List<MusicItem> mMusicItems;

    public static MusicManager getInstance() {
        if (sMusicManager == null) {
            sMusicManager = new MusicManager();
        }

        return sMusicManager;
    }

    private MusicManager() {
        mMusicItems = new ArrayList<>();

    }

    public List<MusicItem> getMusicItems() {
        return mMusicItems;
    }

    public MusicItem getMusicItem(UUID uuid) {
        for (MusicItem song : mMusicItems) {
            if (song.getId().equals(uuid)) {
                return song;
            }
        }
        return null;
    }
}
