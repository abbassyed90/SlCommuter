package project.as224qc.dv606.slcommuter.fragment;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.Iterator;

import project.as224qc.dv606.slcommuter.EventBus;
import project.as224qc.dv606.slcommuter.R;
import project.as224qc.dv606.slcommuter.SQLiteContext;
import project.as224qc.dv606.slcommuter.adapter.SubscribedDeviationAdapter;
import project.as224qc.dv606.slcommuter.adapter.SubscriptionAdapter;
import project.as224qc.dv606.slcommuter.event.OnItemClickEvent;
import project.as224qc.dv606.slcommuter.model.DeviationDTO;
import project.as224qc.dv606.slcommuter.model.Subscription;
import project.as224qc.dv606.slcommuter.util.Constants;
import project.as224qc.dv606.slcommuter.util.Utils;
import project.as224qc.dv606.slcommuter.widget.DividerItemDecoration;
import project.as224qc.dv606.slcommuter.widget.ExtendedRecyclerView;

/**
 * Lets the user subscribe to specific bus/train/subway routes.
 * Then every deviation that the user subscribes too will be shown.
 *
 * @author Abbas Syed
 * @packageName project.as224qc.dv606.slcommuter.fragment
 */
public class SubscribedDeviationsFragment extends Fragment implements View.OnClickListener {

    private SubscribedDeviationAdapter adapter;
    private SubscriptionAdapter subscriptionAdapter;

    private FloatingActionButton addButton;
    private ExtendedRecyclerView recyclerView;
    private RecyclerView subscriptionRecyclerView;
    private DrawerLayout navigationDrawer;

    public static SubscribedDeviationsFragment getInstance() {
        return new SubscribedDeviationsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new SubscribedDeviationAdapter();
        subscriptionAdapter = new SubscriptionAdapter();
        new FetchDeviationsTask().execute();
        new FetchSubscriptionTask().execute();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        adapter = null;
        subscriptionAdapter = null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_subscriped_deviations, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(Utils.getInstance().createToolbarTitle(getString(R.string.title_subscribed_deviation)));

        // init recyclerview
        recyclerView = (ExtendedRecyclerView) view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));

        // init recyclerview
        subscriptionRecyclerView = (RecyclerView) view.findViewById(R.id.subscriptionRecyclerView);
        subscriptionRecyclerView.setHasFixedSize(true);
        subscriptionRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        subscriptionRecyclerView.setAdapter(subscriptionAdapter);
        subscriptionRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));

        // init views
        addButton = (FloatingActionButton) view.findViewById(R.id.addButton);
        navigationDrawer = (DrawerLayout) view.findViewById(R.id.drawer_layout);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        recyclerView = null;
        subscriptionRecyclerView = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getInstance().register(this);

        addButton.setOnClickListener(this);

        // set to true so that menu is inflated
        setHasOptionsMenu(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getInstance().unregister(this);

        addButton.setOnClickListener(null);

        // set to false so that menu is removed
        setHasOptionsMenu(false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_subscribed_deviations, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_more:
                navigationDrawer.openDrawer(Gravity.RIGHT);
                return true;
            default:
                return false;
        }
    }

    @Subscribe
    public void onItemClickEvent(OnItemClickEvent event) {
        switch (event.getView().getId()) {
            case R.id.deleteButton:
                Subscription subscription = subscriptionAdapter.getSubscriptions().get(event.getPosition());

                // remove subscribed deviations
                removeSubscribedDeviations(adapter.getDeviation(), subscription);

                // update iu
                subscriptionAdapter.getSubscriptions().remove(subscription);
                subscriptionAdapter.notifyDataSetChanged();
                adapter.notifyDataSetChanged();

                // remove subscription from database
                SQLiteContext.getInstance().getController().deleteSubscription(subscription);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addButton:
                showAddSubscriptionDialog();
                break;
        }
    }

    /**
     * Removes all deviations that contains the subscription name
     *
     * @param deviations
     * @param subscription
     */
    private void removeSubscribedDeviations(ArrayList<DeviationDTO> deviations, Subscription subscription) {
        Iterator<DeviationDTO> iterator = deviations.iterator();

        while (iterator.hasNext()) {
            DeviationDTO deviation = iterator.next();
            if (deviation.getScope().contains(subscription.getName())) {
                SQLiteContext.getInstance().getController().deleteDeviation(deviation);
                iterator.remove();
            }
        }
    }

    /**
     * Shows a dialog box where the user enter a bus/train/subway route
     */
    public void showAddSubscriptionDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = LayoutInflater.from(getActivity());
        final View dialogView = inflater.inflate(R.layout.dialog_add_subscription, null);
        dialogBuilder.setView(dialogView);

        final EditText editText = (EditText) dialogView.findViewById(R.id.subscriptionEdittext);

        dialogBuilder.setTitle(getString(R.string.dialog_title_subscription));
        dialogBuilder.setMessage(getString(R.string.msg_add_subscription));
        dialogBuilder.setPositiveButton(getString(R.string.add), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String input = editText.getText().toString().trim();
                if (input.isEmpty()) {
                    Toast.makeText(getActivity(), getString(R.string.error_field_empty), Toast.LENGTH_SHORT).show();
                } else {
                    Subscription subscription = new Subscription(input, Constants.TravelTypes.BUS);
                    SQLiteContext.getInstance().getController().insertSubscription(subscription);
                    subscriptionAdapter.getSubscriptions().add(subscription);
                    subscriptionAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                }
            }
        });
        dialogBuilder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    /**
     * Asynctask that fetches all saved deviations
     * and then updates the adapter class
     */
    private class FetchDeviationsTask extends AsyncTask<Void, Void, ArrayList<DeviationDTO>> {

        @Override
        protected ArrayList<DeviationDTO> doInBackground(Void... params) {
            return SQLiteContext.getInstance().getController().getDeviations();
        }

        @Override
        protected void onPostExecute(ArrayList<DeviationDTO> deviationDTOs) {
            super.onPostExecute(deviationDTOs);
            adapter.getDeviation().addAll(deviationDTOs);
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * Asynctask that fetches all saved subscriptions
     * and then updated the adapter class
     */
    private class FetchSubscriptionTask extends AsyncTask<Void, Void, ArrayList<Subscription>> {

        @Override
        protected ArrayList<Subscription> doInBackground(Void... params) {
            return SQLiteContext.getInstance().getController().getSubscriptions();
        }

        @Override
        protected void onPostExecute(ArrayList<Subscription> subscriptions) {
            super.onPostExecute(subscriptions);
            subscriptionAdapter.getSubscriptions().addAll(subscriptions);
            subscriptionAdapter.notifyDataSetChanged();
        }
    }
}
