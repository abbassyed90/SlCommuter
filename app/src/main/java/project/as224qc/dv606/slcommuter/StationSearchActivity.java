package project.as224qc.dv606.slcommuter;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewStub;
import android.widget.EditText;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import project.as224qc.dv606.slcommuter.adapter.StationAdapter;
import project.as224qc.dv606.slcommuter.event.OnItemClickEvent;
import project.as224qc.dv606.slcommuter.event.StationEvent;
import project.as224qc.dv606.slcommuter.model.StationDTO;
import project.as224qc.dv606.slcommuter.util.EmptyStateHelper;
import project.as224qc.dv606.slcommuter.util.Utils;
import project.as224qc.dv606.slcommuter.widget.ExtendedRecyclerView;

import static project.as224qc.dv606.slcommuter.R.id.recyclerview;

/**
 * @author Abbas Syed
 * @packageName project.as224qc.dv606.slcommuter
 */
public class StationSearchActivity extends AppCompatActivity {

    public static final String SELECTED_STATION = "station.selected";
    public static final String VIEW_ID = "view.id";

    private int viewId;

    private EmptyStateHelper emptyStateHelper;
    private StationAdapter adapter;

    private EditText searchEditText;
    private ExtendedRecyclerView recyclerView;
    private TextWatcher stationSearchTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            ApiService.getInstance().getStationId(s.toString());

            // show loading empty state when user is searching for a station
            if (s.length() > 2) {
                emptyStateHelper.showEmptyStateScreen(getApplicationContext(), false);
            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        // retrieve all previous selected stations/sites
        new PreviousStationFetcherTask().execute();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_search);

        // get view id of view that was calling this activity
        viewId = getIntent().getIntExtra(VIEW_ID, 0);

        // init toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(Utils.getInstance().createToolbarTitle(getString(R.string.title_station_search)));
        setSupportActionBar(toolbar);

        searchEditText = (EditText) findViewById(R.id.searchSiteEditText);

        // init adapter
        adapter = new StationAdapter();

        initRecyclerView();
        View view = initEmptyState();

        recyclerView.setEmptyView(view);
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getInstance().register(this);

        searchEditText.addTextChangedListener(stationSearchTextWatcher);
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getInstance().unregister(this);

        searchEditText.removeTextChangedListener(stationSearchTextWatcher);
    }

    @Subscribe
    public void stationEvent(StationEvent event) {
        if (event.isSuccess()) {
            // remove all previous stations
            // and add all new stations from server
            adapter.getStations().clear();
            adapter.getStations().addAll(event.getStations());
            adapter.notifyDataSetChanged();
        }
    }

    @Subscribe
    public void onItemClickEvent(OnItemClickEvent event) {
        // save selected station into sql site table
        new StationSaverTask().execute(adapter.getStations().get(event.getPosition()));

        // send result back to calling activity/fragment
        Intent intent = new Intent();
        intent.putExtra(VIEW_ID, viewId);
        intent.putExtra(SELECTED_STATION, adapter.getStations().get(event.getPosition()));
        setResult(RESULT_OK, intent);
        finish();
    }

    private void initRecyclerView() {
        recyclerView = (ExtendedRecyclerView) findViewById(recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);
    }

    private View initEmptyState() {
        // init empty state handler
        // and view
        View view = ((ViewStub) findViewById(R.id.viewStub)).inflate();
        emptyStateHelper = new EmptyStateHelper(view);
        emptyStateHelper.setEmptyStateTitle(getString(R.string.empty_state_title_station));
        emptyStateHelper.setEmptyStatePicture(R.drawable.ic_station_search);
        emptyStateHelper.showEmptyStateScreen(getApplicationContext(), false);
        return view;
    }

    /**
     * Async task used to query sqlite for previous selected sites
     */
    private class PreviousStationFetcherTask extends AsyncTask<Void, Void, ArrayList<StationDTO>> {

        @Override
        protected ArrayList<StationDTO> doInBackground(Void... params) {
            return SQLiteContext.getInstance().getController().getStations();
        }

        @Override
        protected void onPostExecute(ArrayList<StationDTO> stations) {
            super.onPostExecute(stations);
            adapter.getStations().addAll(stations);
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * Async task used to save sites that the user has selected
     */
    private class StationSaverTask extends AsyncTask<StationDTO, Void, Void> {

        @Override
        protected Void doInBackground(StationDTO... params) {
            SQLiteContext.getInstance().getController().insertStation(params[0]);
            return null;
        }
    }
}
