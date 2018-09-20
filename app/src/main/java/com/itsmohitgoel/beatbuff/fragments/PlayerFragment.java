package com.itsmohitgoel.beatbuff.fragments;

import android.content.ContentUris;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.itsmohitgoel.beatbuff.R;
import com.itsmohitgoel.beatbuff.listeners.PlaybackInfoListener;
import com.itsmohitgoel.beatbuff.listeners.PlayerAdapter;
import com.itsmohitgoel.beatbuff.models.MusicItem;
import com.itsmohitgoel.beatbuff.utils.Blur;
import com.itsmohitgoel.beatbuff.utils.CircularSeekBar;
import com.itsmohitgoel.beatbuff.utils.MediaPlayerHolder;
import com.itsmohitgoel.beatbuff.utils.MusicManager;
import com.itsmohitgoel.beatbuff.utils.TimeConvertor;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class PlayerFragment extends Fragment {
    private static final String TAG = PlayerFragment.class.getSimpleName();

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({Playback.PREVIOUS, Playback.CURRENT, Playback.NEXT})
    @interface Playback {
        int PREVIOUS = 0;
        int CURRENT = 1;
        int NEXT = 2;
    }

    @BindView(R.id.google_cast_button)
    ImageView mGoogleCastButton;

    @BindView(R.id.share_button)
    ImageView mShareButton;
    @BindView(R.id.equalizer_button)
    ImageView mEqualizerButton;
    @BindView(R.id.repeat_button)
    ImageView mRepeatButton;
    @BindView(R.id.shuffle_button)
    ImageView mShuffleButton;
    @BindView(R.id.playlist_button)
    ImageView mPlaylistButton;
    @BindView(R.id.background_image_view)
    ImageView mBackgroundImageView;
    @BindView(R.id.circle_image_view)
    CircleImageView mProfileImageView;
    @BindView(R.id.play_pause_button)
    ImageView mPlayPauseButton;
    @BindView(R.id.previous_button)
    ImageView mPreviousButton;
    @BindView(R.id.next_button)
    ImageView mNextButton;
    @BindView(R.id.circular_seekbar)
    CircularSeekBar mCircularSeekBar;
    @BindView(R.id.current_time_text_view)
    TextView mCurrentTimeTextView;
    @BindView(R.id.total_time_text_view)
    TextView mTotalTimeTextView;

    private PlayerAdapter mPlayerAdapter;
    private boolean mUserIsSeeking = false;
    private ArrayList<MusicItem> mMusicItems;
    private MusicItem mCurrentTrack;
    private int mCurrentTrackIndex;
    private boolean mShouldRepeat;
    private boolean mShouldShuffle;
    private boolean mShouldGoogleCast;
    private boolean mShouldShare;
    private boolean mOpenEqualizer;
    private boolean mHasPlaylist;
    private boolean mIsFavourite;


    public static PlayerFragment newInstance() {
        return new PlayerFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMusicItems = MusicManager.getInstance().getMusicItems();
        mCurrentTrackIndex = 0;
        mCurrentTrack = mMusicItems.get(mCurrentTrackIndex);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater
                .inflate(R.layout.fragment_player, container, false);

        initializeUI(view);
        initializeSeekbar();
        initializePlaybackController();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
//        mPlayerAdapter.loadMedia(mCurrentTrack.getResourceId());
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mPlayerAdapter.isPlaying()) {
            //Do nothing
        } else {
            mPlayerAdapter.release();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPlayerAdapter.release();
    }

    private View.OnClickListener controlButtonsListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.google_cast_button:
                    mShouldGoogleCast = !mShouldGoogleCast;
                    setBackgroundTint(mGoogleCastButton.getDrawable(), mShouldGoogleCast);
                    break;
                case R.id.share_button:
                    mShouldShare = !mShouldShare;
                    setBackgroundTint(mShareButton.getDrawable(), mShouldShare);
                    break;
                case R.id.equalizer_button:
                    mOpenEqualizer = !mOpenEqualizer;
                    setBackgroundTint(mEqualizerButton.getDrawable(), mOpenEqualizer);
                    break;
                case R.id.repeat_button:
                    mShouldRepeat = !mShouldRepeat;
                    setBackgroundTint(mRepeatButton.getDrawable(), mShouldRepeat);
                    break;
                case R.id.shuffle_button:
                    mShouldShuffle = !mShouldShuffle;
                    setBackgroundTint(mShuffleButton.getDrawable(), mShouldShuffle);
                    break;
                case R.id.playlist_button:
                    mHasPlaylist = !mHasPlaylist;
                    setBackgroundTint(mPlaylistButton.getDrawable(), mHasPlaylist);
                    break;
                case R.id.play_pause_button:
//                    mPlayerAdapter.play();
                    playTrack(Playback.CURRENT);
                case R.id.previous_button:
                    playTrack(Playback.PREVIOUS);
                    break;
                case R.id.next_button:
                    playTrack(Playback.NEXT);
                    break;
                default:
                    break;
            }
        }

        private void setBackgroundTint(Drawable drawable, boolean isEnabled) {
            int resId = isEnabled ? R.color.cyan_bright : R.color.matte_clay;
            DrawableCompat.setTint(drawable,
                    ContextCompat.getColor(getActivity(), resId));
        }
    };

    private void initializeUI(View view) {
        ButterKnife.bind(this, view);

        updateUI();

        mGoogleCastButton.setOnClickListener(controlButtonsListener);
        mShareButton.setOnClickListener(controlButtonsListener);
        mEqualizerButton.setOnClickListener(controlButtonsListener);
        mRepeatButton.setOnClickListener(controlButtonsListener);
        mShuffleButton.setOnClickListener(controlButtonsListener);
        mPlaylistButton.setOnClickListener(controlButtonsListener);
        mPlayPauseButton.setOnClickListener(controlButtonsListener);
        mPreviousButton.setOnClickListener(controlButtonsListener);
        mNextButton.setOnClickListener(controlButtonsListener);
    }

    private void updateUI() {
        Bitmap albumArtImage = getAlbumArtImage(mCurrentTrack.getResourceId());
        mProfileImageView.setImageBitmap(albumArtImage);

        Bitmap albumArtBlurImage = Blur.fastblur(getActivity(), albumArtImage, 5);
        mBackgroundImageView.setImageBitmap(albumArtBlurImage);
        mBackgroundImageView.setColorFilter(Blur.getGrayScaleFilter());
    }

    /**
     * Extract the Album cover from the audio resource file itself
     */
    private Bitmap getAlbumArtImage(long resId) {
        Uri baseUriForAudioFiles = Uri.parse("android.resource://" + getActivity().getPackageName() + "/raw");
        Uri uri = ContentUris.withAppendedId(baseUriForAudioFiles, resId);
        Bitmap image;

        MediaMetadataRetriever mData = new MediaMetadataRetriever();
        mData.setDataSource(getActivity(), uri);
        byte art[] = mData.getEmbeddedPicture();
        image = BitmapFactory.decodeByteArray(art, 0, art.length);

        return image;
    }

    private void initializePlaybackController() {
        MediaPlayerHolder mediaPlayerHolder = new MediaPlayerHolder(getActivity());
        mediaPlayerHolder.setPlaybackInfoListener(new PlaybackListener());
        Log.d(TAG, "initializePlaybackController: created MediaPlayerHolder");
        mPlayerAdapter = mediaPlayerHolder;
    }

    private void initializeSeekbar() {
        mCircularSeekBar.setOnSeekBarChangeListener(new CircularSeekBar.OnCircularSeekBarChangeListener() {
            int userSelectedPosition = 0;

            @Override
            public void onStartTrackingTouch(CircularSeekBar seekBar) {
                mUserIsSeeking = true;
            }

            @Override
            public void onProgressChanged(CircularSeekBar circularSeekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    userSelectedPosition = progress;
                }
            }

            @Override
            public void onStopTrackingTouch(CircularSeekBar seekBar) {
                mUserIsSeeking = false;
                mPlayerAdapter.seekTo(userSelectedPosition);
            }
        });
    }

    private void playTrack(@Playback int trackNumber) {
        switch (trackNumber) {
            case Playback.PREVIOUS:
                if (--mCurrentTrackIndex < 0) {
                    Toast.makeText(getActivity(), "You are currently listening to first track!",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                mCurrentTrack = mMusicItems.get(mCurrentTrackIndex);
                mPlayerAdapter.reset();
                break;

            case Playback.CURRENT:
//                mPlayerAdapter.play();
                break;
            case Playback.NEXT:
                if (++mCurrentTrackIndex >= mMusicItems.size()) {
                    Toast.makeText(getActivity(), "There is no track remaining in current playlist",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                mCurrentTrack = mMusicItems.get(mCurrentTrackIndex);
                mPlayerAdapter.reset();
                break;
        }

        mPlayerAdapter.loadMedia(mCurrentTrack.getResourceId());
        updateUI();
        mPlayerAdapter.play();


    }

    /**
     * Callback listener to receive callback on events like progress change, seek position
     */
    public class PlaybackListener extends PlaybackInfoListener {
        @Override
        public void onDurationChanged(int duration) {
            mCircularSeekBar.setMax(duration);
            mTotalTimeTextView.setText(TimeConvertor.milliSecondsToTimer(duration));
        }

        @Override
        public void onPositionChanged(final int position) {
            if (!mUserIsSeeking) {
//                Log.e(TAG, "onPositionChanged(position): " + position);

                mCircularSeekBar.post(new Runnable() {
                    @Override
                    public void run() {
                        mCircularSeekBar.setProgress(position);
//                        Log.d(TAG, position + "ms to sec " + TimeConvertor.milliSecondsToTimer(position));
                        mCurrentTimeTextView.setText(TimeConvertor.milliSecondsToTimer(position));

                    }
                });
            }
        }

    }
}
