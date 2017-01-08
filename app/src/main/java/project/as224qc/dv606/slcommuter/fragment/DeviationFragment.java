package project.as224qc.dv606.slcommuter.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;

import com.squareup.otto.Subscribe;

import project.as224qc.dv606.slcommuter.ApiService;
import project.as224qc.dv606.slcommuter.EventBus;
import project.as224qc.dv606.slcommuter.R;
import project.as224qc.dv606.slcommuter.adapter.DeviationAdapter;
import project.as224qc.dv606.slcommuter.event.DeviationEvent;
import project.as224qc.dv606.slcommuter.event.OnItemClickEvent;
import project.as224qc.dv606.slcommuter.util.EmptyStateHelper;
import project.as224qc.dv606.slcommuter.util.IntentHelper;
import project.as224qc.dv606.slcommuter.util.Utils;
import project.as224qc.dv606.slcommuter.widget.ExtendedRecyclerView;

/**
 * A fragment that shows all current deviations
 *
 * @author Abbas Syed
 * @packageName project.as224qc.dv606.slcommuter.fragment
 */
public class DeviationFragment extends Fragment {

    private DeviationAdapter adapter;
    private ExtendedRecyclerView recyclerView;
    private EmptyStateHelper emptyStateHelper;

    public static DeviationFragment getInstance() {
        return new DeviationFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new DeviationAdapter();
        ApiService.getInstance().getDeviations();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        adapter = null;
        emptyStateHelper = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getInstance().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getInstance().unregister(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_deviations, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(Utils.getInstance().createToolbarTitle(getString(R.string.title_deviation)));

        // init empty state and empty state helper
        View emptyState = ((ViewStub) view.findViewById(R.id.viewStub)).inflate();
        emptyStateHelper = new EmptyStateHelper(emptyState);
        emptyStateHelper.showLoadingScreen(getActivity(), true);

        // init recyclerview
        recyclerView = (ExtendedRecyclerView) view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        recyclerView.setEmptyView(emptyState);
    }

    @Subscribe
    public void deviationEvent(DeviationEvent event) {
        if (event.isSuccess()) {
            adapter.getDeviations().addAll(event.getDeviations());
        } else {
            emptyStateHelper.showNetworkErrorState(getActivity());
        }

        adapter.notifyDataSetChanged();
    }

    @Subscribe
    public void onItemClickEvent(OnItemClickEvent event) {
        IntentHelper.startDeviationDetailActivity(getActivity(), adapter.getDeviations().get(event.getPosition()));
    }

}
