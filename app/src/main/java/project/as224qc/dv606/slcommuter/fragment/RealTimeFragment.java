package project.as224qc.dv606.slcommuter.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import project.as224qc.dv606.slcommuter.ApiService;
import project.as224qc.dv606.slcommuter.EventBus;
import project.as224qc.dv606.slcommuter.R;
import project.as224qc.dv606.slcommuter.StationSearchActivity;
import project.as224qc.dv606.slcommuter.adapter.RealTimeAdapter;
import project.as224qc.dv606.slcommuter.event.TimeEvent;
import project.as224qc.dv606.slcommuter.model.StationDTO;
import project.as224qc.dv606.slcommuter.util.Constants;
import project.as224qc.dv606.slcommuter.util.EmptyStateHelper;
import project.as224qc.dv606.slcommuter.util.IntentHelper;
import project.as224qc.dv606.slcommuter.util.Utils;
import project.as224qc.dv606.slcommuter.widget.ExtendedRecyclerView;


/**
 * Lets the user see all departures from a specific station
 *
 * @author Abbas Syed
 * @packageName project.as224qc.dv606.slcommuter.fragment
 */
public class RealTimeFragment extends Fragment implements View.OnClickListener {

    private StationDTO selectedStation = null;

    private EmptyStateHelper emptyStateHelper;
    private RealTimeAdapter adapter;

    private ExtendedRecyclerView recyclerView;
    private EditText searchBar;
    private TextView searchButton;

    public static RealTimeFragment getInstance() {
        return new RealTimeFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new RealTimeAdapter();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        adapter = null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_time_table, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        View emptyState = ((ViewStub) view.findViewById(R.id.viewStub)).inflate();

        initEmptyState(emptyState);
        initRecyclerView(view, emptyState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(Utils.getInstance().createToolbarTitle(getString(R.string.title_real_time)));

        searchBar = (EditText) view.findViewById(R.id.searchBar);
        searchButton = (TextView) view.findViewById(R.id.searchButton);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        recyclerView = null;
        searchBar = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getInstance().register(this);

        searchBar.setOnClickListener(this);
        searchButton.setOnClickListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getInstance().unregister(this);

        searchButton.setOnClickListener(null);
        searchBar.setOnClickListener(null);
    }

    @Subscribe
    public void realTimeEvent(TimeEvent event) {
        if (event.isSuccess()) {
            adapter.getTransportations().addAll(event.getTransportationDTOs());
            adapter.getItems().addAll(event.getRecyclerViewItems());
            adapter.notifyDataSetChanged();
        } else {
            // show network error empty state
            emptyStateHelper.showNetworkErrorState(getActivity());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.searchBar:
                IntentHelper.startStationSearchActivity(this, v.getId());
                break;
            case R.id.searchButton:
                search();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.RequestCode.STATION_SEARCH && data != null) {
            selectedStation = data.getParcelableExtra(StationSearchActivity.SELECTED_STATION);
            searchBar.setText(selectedStation.getName());
        }
    }

    /**
     * Init recyclerView
     *
     * @param view
     * @param emptyState
     */
    private void initRecyclerView(View view, View emptyState) {
        recyclerView = (ExtendedRecyclerView) view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        recyclerView.setEmptyView(emptyState);
    }

    /**
     * Init empty state
     *
     * @param view
     */
    private void initEmptyState(View view) {
        emptyStateHelper = new EmptyStateHelper(view);
        emptyStateHelper.setEmptyStateTitle(getString(R.string.empty_state_title_timetable));
        emptyStateHelper.setEmptyStatePicture(R.drawable.ic_bus_stop);
        emptyStateHelper.showEmptyStateScreen(getActivity(), false);
    }

    /**
     * Make call to server to fetch data
     */
    private void search() {
        if (selectedStation != null) {
            ApiService.getInstance().realTimeInformation(selectedStation, 10);

            adapter.getTransportations().clear();
            adapter.getItems().clear();

            adapter.notifyDataSetChanged();
            emptyStateHelper.showLoadingScreen(getActivity(), false);
        } else {
            Toast.makeText(getActivity(), getString(R.string.error_no_station_selected), Toast.LENGTH_SHORT).show();
        }
    }
}
