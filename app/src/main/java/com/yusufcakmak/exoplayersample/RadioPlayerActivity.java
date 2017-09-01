package com.yusufcakmak.exoplayersample;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;
import java.util.List;

public class RadioPlayerActivity extends AppCompatActivity implements Player.EventListener{


    private Button startButton;
    private Button stopButton;

    private SimpleExoPlayer player;
    private BandwidthMeter bandwidthMeter;
    private ExtractorsFactory extractorsFactory;
    private TrackSelection.Factory trackSelectionFactory;
    private TrackSelector trackSelector;
    private DefaultBandwidthMeter defaultBandwidthMeter;
    private DataSource.Factory dataSourceFactory;
    private MediaSource mediaSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio_player);
        List<String> urls = new ArrayList<>();
        urls.add("http://vo-hub.com:8888/media/dimen4/musics/01.mp3");
        urls.add("http://vo-hub.com:8888/media/dimen4/musics/02.mp3");
        urls.add("http://vo-hub.com:8888/media/dimen4/musics/03.mp3");
        urls.add("http://vo-hub.com:8888/media/dimen4/musics/04.mp3");
        urls.add("http://vo-hub.com:8888/media/dimen4/musics/05.mp3");
        urls.add("http://vo-hub.com:8888/media/dimen4/musics/06.mp3");
        urls.add("http://vo-hub.com:8888/media/dimen4/musics/07.mp3");
        urls.add("http://vo-hub.com:8888/media/dimen4/musics/08.mp3");

        startButton = (Button) findViewById(R.id.startButton);
        stopButton = (Button) findViewById(R.id.stopButton);

        bandwidthMeter = new DefaultBandwidthMeter();
        extractorsFactory = new DefaultExtractorsFactory();

        trackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);

        trackSelector = new DefaultTrackSelector(trackSelectionFactory);

/*        dataSourceFactory = new DefaultDataSourceFactory(this,
                Util.getUserAgent(this, "mediaPlayerSample"),
                (TransferListener<? super DataSource>) bandwidthMeter);*/

        defaultBandwidthMeter = new DefaultBandwidthMeter();
        dataSourceFactory = new DefaultDataSourceFactory(this,
                Util.getUserAgent(this, "mediaPlayerSample"), defaultBandwidthMeter);


        List<MediaSource> mediaSources = new ArrayList<>();
        MediaSource mediaSource;
        for (String url : urls) {
            mediaSource = new ExtractorMediaSource(Uri.parse(url),
                    dataSourceFactory, extractorsFactory, null, null);
            mediaSources.add(mediaSource);
        }
//        mediaSource = new ExtractorMediaSource(Uri.parse("http://rs1.radiostreamer.com:8030"), dataSourceFactory, extractorsFactory, null, null);
        final ConcatenatingMediaSource concatenatingMediaSource = new ConcatenatingMediaSource(
                mediaSources.toArray(new MediaSource[mediaSources.size()]));
        player = ExoPlayerFactory.newSimpleInstance(this, trackSelector);


        player.prepare(concatenatingMediaSource);
        player.setPlayWhenReady(true);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player.stop();
                player.seekToDefaultPosition(1);
                player.setPlayWhenReady(true);
//                player.prepare(concatenatingMediaSource);
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player.stop();
                player.seekToDefaultPosition(0);
                player.setPlayWhenReady(true);

//                player.prepare(concatenatingMediaSource);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.setPlayWhenReady(false);
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

    }

    @Override
    public void onRepeatModeChanged(int repeatMode) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }
}
