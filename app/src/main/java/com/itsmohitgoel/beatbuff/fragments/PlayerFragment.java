package com.itsmohitgoel.beatbuff.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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
    @BindView(R.id.favourite_button)
    ImageView mFavouriteButton;
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
    @BindView(R.id.track_title_text_view)
    TextView mTrackTitleTextView;
    @BindView(R.id.track_info_text_view)
    TextView mTrackInfoTextView;
    @BindView(R.id.previous_track_title_text_view)
    TextView mPreviousTrackTitleTextView;
    @BindView(R.id.previous_track_info_text_view)
    TextView mPreviousTrackInfoTextView;
    @BindView(R.id.next_track_title_text_view)
    TextView mNextTrackTitleTextView;
    @BindView(R.id.next_track_info_text_view)
    TextView mNextTrackInfoTextView;

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
    private boolean mIsPlaying = false;


    public static PlayerFragment newInstance() {
        return new PlayerFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMusicItems = MusicManager.getInstance(getActivity()).getMusicItems();
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
        mPlayerAdapter.loadMedia(mCurrentTrack);
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
                    toggleBackgroundTint(mGoogleCastButton.getDrawable(), mShouldGoogleCast);
                    break;
                case R.id.share_button:
                    mShouldShare = !mShouldShare;
                    toggleBackgroundTint(mShareButton.getDrawable(), mShouldShare);
                    break;
                case R.id.equalizer_button:
                    mOpenEqualizer = !mOpenEqualizer;
                    toggleBackgroundTint(mEqualizerButton.getDrawable(), mOpenEqualizer);
                    break;
                case R.id.repeat_button:
                    mShouldRepeat = !mShouldRepeat;
                    toggleBackgroundTint(mRepeatButton.getDrawable(), mShouldRepeat);
                    break;
                case R.id.shuffle_button:
                    mShouldShuffle = !mShouldShuffle;
                    toggleBackgroundTint(mShuffleButton.getDrawable(), mShouldShuffle);
                    break;
                case R.id.playlist_button:
                    mHasPlaylist = !mHasPlaylist;
                    toggleBackgroundTint(mPlaylistButton.getDrawable(), mHasPlaylist);
                    break;
                case R.id.favourite_button:
                    mIsFavourite = !mIsFavourite;
                    int resId = mIsFavourite ? R.drawable.ic_liked : R.drawable.ic_like;
                    mFavouriteButton.setImageResource(resId);
                    break;
                case R.id.play_pause_button:
                    playTrack(Playback.CURRENT);
                    break;
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

        private void toggleBackgroundTint(Drawable drawable, boolean isEnabled) {
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
        mFavouriteButton.setOnClickListener(controlButtonsListener);
        mPlayPauseButton.setOnClickListener(controlButtonsListener);
        mPreviousButton.setOnClickListener(controlButtonsListener);
        mNextButton.setOnClickListener(controlButtonsListener);
    }

    private void updateUI() {
        Bitmap albumArtImage = mCurrentTrack.getCoverArt();
        if (albumArtImage != null) {
            mProfileImageView.setImageBitmap(albumArtImage);
        }else {
            albumArtImage = BitmapFactory.decodeResource(getResources(), R.drawable.alan_walker);
        }

        Bitmap albumArtBlurImage = Blur.fastblur(getActivity(), albumArtImage, 5);
        mBackgroundImageView.setImageBitmap(albumArtBlurImage);
        mBackgroundImageView.setColorFilter(Blur.getGrayScaleFilter());

        mTrackTitleTextView.setText(mCurrentTrack.getTitle());
        mTrackInfoTextView.setText(mCurrentTrack.getArtistName());

        if (mCurrentTrackIndex == 0) {
            mPreviousTrackInfoTextView.setVisibility(View.GONE);
            mPreviousTrackTitleTextView.setVisibility(View.GONE);

            mNextTrackTitleTextView.setVisibility(View.VISIBLE);
            mNextTrackInfoTextView.setVisibility(View.VISIBLE);
        }else if (mCurrentTrackIndex == (mMusicItems.size() - 1)) {
            mPreviousTrackInfoTextView.setVisibility(View.VISIBLE);
            mPreviousTrackTitleTextView.setVisibility(View.VISIBLE);

            mNextTrackTitleTextView.setVisibility(View.GONE);
            mNextTrackInfoTextView.setVisibility(View.GONE);
        } else {
            mPreviousTrackTitleTextView.setText(mMusicItems.get(mCurrentTrackIndex - 1).getTitle());
            mPreviousTrackInfoTextView.setText(mMusicItems.get(mCurrentTrackIndex - 1).getArtistName());

            mNextTrackTitleTextView.setText(mMusicItems.get(mCurrentTrackIndex + 1).getTitle());
            mNextTrackInfoTextView.setText(mMusicItems.get(mCurrentTrackIndex + 1).getArtistName());

            mPreviousTrackInfoTextView.setVisibility(View.VISIBLE);
            mPreviousTrackTitleTextView.setVisibility(View.VISIBLE);

            mNextTrackTitleTextView.setVisibility(View.VISIBLE);
            mNextTrackInfoTextView.setVisibility(View.VISIBLE);
        }

        int resID = mIsPlaying ? R.drawable.ic_pause_circle : R.drawable.ic_play_circle;
        mPlayPauseButton.setImageResource(resID);

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
                if ((mCurrentTrackIndex - 1) < 0) {
                    Toast.makeText(getActivity(), "You are currently listening to first track!",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                mCurrentTrack = mMusicItems.get(--mCurrentTrackIndex);
                mPlayerAdapter.reset();
                mPlayerAdapter.loadMedia(mCurrentTrack);
                if (mIsPlaying){ mPlayerAdapter.play();}
                break;

            case Playback.CURRENT:
                if (mIsPlaying) {
                    mPlayerAdapter.pause();
                }else {
                    mPlayerAdapter.play();
                }
                mIsPlaying = !mIsPlaying;
                break;

            case Playback.NEXT:
                if ((mCurrentTrackIndex + 1) >= mMusicItems.size()) {
                    Toast.makeText(getActivity(), "There is no track remaining in current playlist",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                mCurrentTrack = mMusicItems.get(++mCurrentTrackIndex);
                mPlayerAdapter.reset();
                mPlayerAdapter.loadMedia(mCurrentTrack);
                if (mIsPlaying){ mPlayerAdapter.play();}
                break;
        }

        updateUI();
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

                mCircularSeekBar.post(new Runnable() {
                    @Override
                    public void run() {
                        mCircularSeekBar.setProgress(position);
                        mCurrentTimeTextView.setText(TimeConvertor.milliSecondsToTimer(position));

                    }
                });
            }
        }

    }
}
