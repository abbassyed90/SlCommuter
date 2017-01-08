package project.as224qc.dv606.slcommuter.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import project.as224qc.dv606.slcommuter.OnItemClickListener;
import project.as224qc.dv606.slcommuter.R;
import project.as224qc.dv606.slcommuter.model.StationDTO;

/**
 * @author Abbas Syed
 * @packageName project.as224qc.dv606.slcommuter.adapter
 */
public class StationAdapter extends RecyclerView.Adapter<StationAdapter.NormalViewHolder> {

    private ArrayList<StationDTO> stations = new ArrayList<>();

    public StationAdapter() {
    }

    @Override
    public NormalViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_station, parent, false);
        final NormalViewHolder normalViewHolder = new NormalViewHolder(view);

        normalViewHolder.itemView.setOnClickListener(new OnItemClickListener(normalViewHolder));

        return normalViewHolder;
    }

    @Override
    public void onBindViewHolder(NormalViewHolder holder, int position) {
        StationDTO station = stations.get(position);

        String stationName = station.getName();
        if (stationName.contains("(")) {
            String secondaryInformation = null;
            secondaryInformation = stationName.substring(stationName.indexOf("(")).trim();
            stationName = stationName.substring(0, stationName.indexOf("(")).trim();

            holder.stationTextView.setText(createSpannableString(holder.itemView.getContext(), stationName, secondaryInformation));
        } else {
            holder.stationTextView.setText(station.getName().trim());
        }

        holder.stationNameTextView.setText(String.valueOf(station.getName().charAt(0)));
    }

    @Override
    public int getItemCount() {
        return stations.size();
    }

    private CharSequence createSpannableString(Context context, String primaryInformation, String secondaryInformation) {
        SpannableString primarySpannable = new SpannableString(primaryInformation);
        SpannableString secondarySpannable = new SpannableString(secondaryInformation);

        secondarySpannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.grey)), 0, secondarySpannable.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return TextUtils.concat(primarySpannable, "\n", secondarySpannable);
    }

    public ArrayList<StationDTO> getStations() {
        return stations;
    }

    class NormalViewHolder extends RecyclerView.ViewHolder {

        TextView stationTextView;
        TextView stationNameTextView;

        NormalViewHolder(View itemView) {
            super(itemView);
            stationTextView = (TextView) itemView.findViewById(R.id.stationTextView);
            stationNameTextView = (TextView) itemView.findViewById(R.id.stationNameTextView);
        }
    }

}