package project.as224qc.dv606.slcommuter.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import project.as224qc.dv606.slcommuter.OnItemClickListener;
import project.as224qc.dv606.slcommuter.R;
import project.as224qc.dv606.slcommuter.model.PreviousTrip;
import project.as224qc.dv606.slcommuter.model.Trip;
import project.as224qc.dv606.slcommuter.model.TripDTO;

/**
 * @author Abbas Syed
 * @packageName project.as224qc.dv606.slcommuter.adapter
 */
public class TripAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int PREVIOUS_TRIP = 0;
    private static final int TRIP = 1;
    private ArrayList<Trip> trips = new ArrayList<>();

    public TripAdapter() {
    }

    @Override
    public int getItemViewType(int position) {
        return (trips.get(position) instanceof TripDTO) ? TRIP : PREVIOUS_TRIP;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (getItemViewType(viewType) == TRIP) {
            TripViewHolder tripViewHolder = new TripViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trip, parent, false));
            tripViewHolder.itemView.setOnClickListener(new OnItemClickListener(tripViewHolder));
            return tripViewHolder;
        } else {
            PreviousViewHolder previousViewHolder = new PreviousViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_previous_trip, parent, false));
            previousViewHolder.itemView.setOnClickListener(new OnItemClickListener(previousViewHolder));
            return previousViewHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Trip trip = trips.get(position);
        Context context = holder.itemView.getContext();

        if (getItemViewType(position) == TRIP) {
            TripViewHolder tripViewHolder = (TripViewHolder) holder;
            TripDTO tripDTO = (TripDTO) trip;
            tripViewHolder.changesTextView.setText(tripDTO.getChanges() + " " + context.getString(R.string.changes));
            tripViewHolder.durationTextView.setText(createSpannableDurationString(tripDTO.getDuration()));
            tripViewHolder.departureTextView.setText(tripDTO.getOriginTime() + " " + context.getString(R.string.departure));
            tripViewHolder.arrivalTextView.setText(tripDTO.getDestinationTime() + " " + context.getString(R.string.arrival));
        } else {
            PreviousViewHolder previousViewHolder = (PreviousViewHolder) holder;
            PreviousTrip previousTrip = (PreviousTrip) trip;
            previousViewHolder.originTextView.setText(previousTrip.getOrigin().getName());
            previousViewHolder.destinationTextView.setText(previousTrip.getDestination().getName());
        }
    }

    @Override
    public int getItemCount() {
        return trips.size();
    }

    public ArrayList<Trip> getTrips() {
        return trips;
    }

    /**
     * Create spannable string for the duration
     *
     * @param duration
     * @return
     */
    private CharSequence createSpannableDurationString(String duration) {
        SpannableString spannableString = new SpannableString(duration.trim());

        spannableString.setSpan(new RelativeSizeSpan(2.0f), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(Color.BLACK), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return TextUtils.concat(spannableString, "\n", "min");
    }

    class TripViewHolder extends RecyclerView.ViewHolder {

        private TextView durationTextView;
        private TextView departureTextView;
        private TextView arrivalTextView;
        private TextView changesTextView;

        TripViewHolder(View itemView) {
            super(itemView);
            durationTextView = (TextView) itemView.findViewById(R.id.durationTextView);
            departureTextView = (TextView) itemView.findViewById(R.id.departureTextView);
            arrivalTextView = (TextView) itemView.findViewById(R.id.arrivalTextView);
            changesTextView = (TextView) itemView.findViewById(R.id.changesTextView);
        }
    }

    class PreviousViewHolder extends RecyclerView.ViewHolder {

        private TextView originTextView;
        private TextView destinationTextView;

        public PreviousViewHolder(View itemView) {
            super(itemView);
            originTextView = (TextView) itemView.findViewById(R.id.originTextView);
            destinationTextView = (TextView) itemView.findViewById(R.id.destinationTextView);
        }
    }

}
