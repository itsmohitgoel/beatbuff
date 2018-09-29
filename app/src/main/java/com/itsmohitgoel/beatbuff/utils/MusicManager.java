package com.itsmohitgoel.beatbuff.utils;

import android.content.ContentUris;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.util.Log;

import com.itsmohitgoel.beatbuff.R;
import com.itsmohitgoel.beatbuff.fragments.CategoryListFragment;
import com.itsmohitgoel.beatbuff.models.CategoryDataModel;
import com.itsmohitgoel.beatbuff.models.CategorySingleItemModel;
import com.itsmohitgoel.beatbuff.models.MusicItem;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.UUID;

public class MusicManager {
    private static final String TAG = MusicManager.class.getSimpleName();

    private static MusicManager sMusicManager;
    private ArrayList<MusicItem> mMusicItems;
    private Context mContext;

    public static MusicManager getInstance(Context context) {
        if (sMusicManager == null) {
            sMusicManager = new MusicManager(context);
        }

        return sMusicManager;
    }

    private MusicManager(Context context) {
        mContext = context;
        mMusicItems = new ArrayList<>();

    }

    public ArrayList<MusicItem> getMusicItems() {
        if (mMusicItems != null && mMusicItems.size() > 0) {
            return mMusicItems;
        }

        int[] resIds = getAllRawMusicResourceIds();

        for (int resId : resIds) {
            MusicItem musicItem = new MusicItem();
            addMetadataInfo(musicItem, resId);
            mMusicItems.add(musicItem);

        }

        return mMusicItems;
    }

    /**
     * Returns data for first horizontal recycler view of Home Screen.
     * i.e Genres list
     */
    public CategoryDataModel getAllGenreData() {
        CategoryDataModel genreDataModel = new CategoryDataModel();
        genreDataModel.setHeaderTitle(mContext.getString(R.string.label_header_genre));
        genreDataModel.setMusicCategory(CategoryListFragment.MusicCategory.GENRE);

        ArrayList<CategorySingleItemModel> genreItemsList = new ArrayList<>();
        String[] genreTitles = mContext.getResources()
                .getStringArray(R.array.title_artists_array);
        int[] genreImageResources = new int[]{
                R.drawable.genre_rock, R.drawable.genre_pop, R.drawable.genre_hiphop,
                R.drawable.genre_electronic, R.drawable.genre_folk, R.drawable.genre_metal};

        for (int i = 0; i < genreImageResources.length; i++) {
            genreItemsList.add(new CategorySingleItemModel(genreTitles[i], genreImageResources[i]));
        }

        genreDataModel.setAllItemsInCategory(genreItemsList);

        return genreDataModel;
    }

    /**
     * Returns data for second horizontal recycler view of Home Screen.
     * i.e Artists list
     */
    public CategoryDataModel getAllArtistData() {
        CategoryDataModel artistDataModel = new CategoryDataModel();
        artistDataModel.setHeaderTitle(mContext.getString(R.string.label_header_artists));
        artistDataModel.setMusicCategory(CategoryListFragment.MusicCategory.ARTIST);

        ArrayList<CategorySingleItemModel> artistsItemsList = new ArrayList<>();
        String[] artistTitles = mContext.getResources()
                .getStringArray(R.array.title_artists_array);
        int[] artistImageResources = new int[]{
                R.drawable.artist_charlie, R.drawable.artist_sia, R.drawable.artist_chainsmokers,
                R.drawable.artist_alan_walker, R.drawable.artist_marshmellow,
                R.drawable.artist_maroon5, R.drawable.artist_drake, R.drawable.artist_justine,
                R.drawable.artist_selena};
        for (int i = 0; i < artistImageResources.length; i++) {
            artistsItemsList.add(new CategorySingleItemModel(artistTitles[i], artistImageResources[i]));
        }

        artistDataModel.setAllItemsInCategory(artistsItemsList);

        return artistDataModel;
    }

    /**
     * Returns data for third horizontal recycler view of Home Screen.
     * i.e Albums list
     */
    public CategoryDataModel getAllAlbumsData() {
        CategoryDataModel albumsDataModel = new CategoryDataModel();
        albumsDataModel.setHeaderTitle(mContext.getString(R.string.label_header_albums));
        albumsDataModel.setMusicCategory(CategoryListFragment.MusicCategory.ALBUMS);

        ArrayList<CategorySingleItemModel> albumItemsList = new ArrayList<>();
        String[] albumTitles = mContext.getResources().getStringArray(R.array.title_albums_array);
        int[] albumImageResources = new int[]{
                R.drawable.album_voicenotes, R.drawable.album_this_is_acting, R.drawable.album_collage,
                R.drawable.album_speak_your_mind, R.drawable.album_red_pill_blues,
                R.drawable.album_how_long, R.drawable.album_wolves};
        for (int i = 0; i < albumImageResources.length; i++) {
            albumItemsList.add(new CategorySingleItemModel(albumTitles[i], albumImageResources[i]));
        }

        albumsDataModel.setAllItemsInCategory(albumItemsList);

        return albumsDataModel;
    }

    /**
     * Returns data for fourth horizontal recycler view of Home Screen.
     * i.e All Songs list
     */
    public CategoryDataModel getAllSongsData() {
        CategoryDataModel allSongsDataModel = new CategoryDataModel();
        allSongsDataModel.setHeaderTitle(mContext.getString(R.string.label_header_all_songs));
        allSongsDataModel.setMusicCategory(CategoryListFragment.MusicCategory.ALL_SONGS);

        ArrayList<CategorySingleItemModel> allSongsItemsList = new ArrayList<>();
        String[] songTitles = mContext.getResources().getStringArray(R.array.title_songs_array);
        int[] albumImageResources = new int[]{
                R.drawable.artist_charlie, R.drawable.artist_sia, R.drawable.artist_chainsmokers,
                R.drawable.alan_walker, R.drawable.artist_alan_walker,
                R.drawable.artist_marshmellow, R.drawable.artist_maroon5,
                R.drawable.artist_charlie, R.drawable.artist_justine, R.drawable.artist_chainsmokers,
                R.drawable.artist_selena};
        for (int i = 0; i < albumImageResources.length; i++) {
            allSongsItemsList.add(new CategorySingleItemModel(songTitles[i], albumImageResources[i]));
        }

        allSongsDataModel.setAllItemsInCategory(allSongsItemsList);

        return allSongsDataModel;
    }

    /**
     * Retrieve all mp3 files from res/raw folder at once during runtime
     */
    private int[] getAllRawMusicResourceIds() {
        Field[] fields = R.raw.class.getDeclaredFields();
        int[] ids = new int[fields.length];
        R.raw r = new R.raw();

        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            try {
                ids[i] = field.getInt(r);
            } catch (IllegalAccessException iae) {
                Log.d(TAG, "cannot access int id from R file", iae);
            }
        }

        return ids;
    }

    /**
     * Extract Metadata info from .mp3 file and store it in the
     * pojo passed as argment
     * @param musicItem need to be filled with metadata
     * @param resourceId of mp3 tracks present in raw resource folder
     */
    private void addMetadataInfo(MusicItem musicItem, int resourceId) {
        Uri baseUriForAudioFiles = Uri.parse("android.resource://" + mContext.getPackageName() + "/raw");
        Uri uri = ContentUris.withAppendedId(baseUriForAudioFiles, resourceId);

        MediaMetadataRetriever mData = new MediaMetadataRetriever();
        mData.setDataSource(mContext, uri);
        String artist = mData.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
        String duration = mData.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        duration = TimeConverter.milliSecondsToTimer(Integer.parseInt(duration));
        String title = mData.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
        byte art[] = mData.getEmbeddedPicture();
        Bitmap coverImage;
        if (art != null && art.length > 0) {
            coverImage = BitmapFactory.decodeByteArray(art, 0, art.length);
        } else {
            coverImage = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.alan_walker);
        }

        musicItem.setTitle(title);
        musicItem.setArtistName(artist);
        musicItem.setDuration(duration);
        musicItem.setCoverArt(coverImage);
        musicItem.setUriPath(uri);

        Log.i(TAG, String.format("Artist : [%s];\t\t Duration : [%s]; \t\t Title : [%s]",
                artist, duration, title));
    }
}



















