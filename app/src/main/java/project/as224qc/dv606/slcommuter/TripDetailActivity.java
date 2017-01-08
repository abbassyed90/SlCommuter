package project.as224qc.dv606.slcommuter;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import project.as224qc.dv606.slcommuter.adapter.LegAdapter;
import project.as224qc.dv606.slcommuter.model.Leg;
import project.as224qc.dv606.slcommuter.model.StationDTO;
import project.as224qc.dv606.slcommuter.model.TripDTO;

/**
 * @author Abbas Syed
 * @packageName project.as224qc.dv606.slcommuter
 */
public class TripDetailActivity extends AppCompatActivity {

    public static final String BUNDLE_DATA_TRIP = "BUNDLE_DATA.trip";
    public static final String BUNDLE_ORIGIN = "BUNDLE_DATA.origin";
    public static final String BUNDLE_DESTINATION = "BUNDLE_DATA.destination";

    private TripDTO trip;
    private ArrayList<Leg> legs;

    private LegAdapter legAdapter;

    private FloatingActionButton shareButton;
    private RecyclerView recyclerView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_detail);

        // get trip from bundle
        trip = getIntent().getParcelableExtra(BUNDLE_DATA_TRIP);
        legs = trip.getLegs();
        StationDTO origin = getIntent().getParcelableExtra(BUNDLE_ORIGIN);
        StationDTO destination = getIntent().getParcelableExtra(BUNDLE_DESTINATION);

        initToolbar(origin, destination);

        // init adapter for recyclerview
        legAdapter = new LegAdapter();
        legAdapter.getLegs().addAll(legs);

        // init recyclerview
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(legAdapter);

        // init floating action button
        shareButton = (FloatingActionButton) findViewById(R.id.shareButton);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, getTripPlainText(trip));
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });

        // init views
        TextView departureTextView = (TextView) findViewById(R.id.departureTextView);
        TextView arrivalTextView = (TextView) findViewById(R.id.arrivalTextView);
        TextView changesTextView = (TextView) findViewById(R.id.changesTextView);
        TextView durationTextView = (TextView) findViewById(R.id.durationTextView);

        // update text
        departureTextView.setText(createSpannableString(trip.getOriginTime(), getString(R.string.departure)));
        arrivalTextView.setText(createSpannableString(trip.getDestinationTime(), getString(R.string.arrival)));
        changesTextView.setText(createSpannableString(trip.getChanges(), getString(R.string.changes)));
        durationTextView.setText(createSpannableString(trip.getDuration(), getString(R.string.min)));

        legAdapter.notifyDataSetChanged();
    }

    /**
     * Will show the trip in plain text
     *
     * @param trip
     * @return
     */
    private String getTripPlainText(TripDTO trip) {
        String origin = trip.getLegs().get(0).getOrigin().getName();
        String destination = null;

        if (trip.getLegs().size() == 1) {
            destination = trip.getLegs().get(0).getDestination().getName();
        } else {
            destination = trip.getLegs().get(trip.getLegs().size() - 1).getDestination().getName();
        }

        return String.format(getString(R.string.trip_plain_text), origin, trip.getOriginTime(), destination, trip.getDestinationTime());
    }

    /**
     * Create spannable string
     * by resizing the text size of value
     *
     * @param value
     * @param key
     * @return
     */
    private CharSequence createSpannableString(String value, String key) {
        SpannableString valueSpannable = new SpannableString(value);
        SpannableString keySpannable = new SpannableString(key);

        valueSpannable.setSpan(new RelativeSizeSpan(1.6f), 0, valueSpannable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return TextUtils.concat(valueSpannable, "\n", keySpannable);
    }

    /**
     * init toolbar
     *
     * @param origin
     * @param destination
     */
    private void initToolbar(StationDTO origin, StationDTO destination) {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(origin.getName());
        toolbar.setSubtitle(destination.getName());
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setSubtitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
    }

}
