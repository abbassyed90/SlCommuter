package project.as224qc.dv606.slcommuter.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import project.as224qc.dv606.slcommuter.R;
import project.as224qc.dv606.slcommuter.model.DeviationDTO;

/**
 * @author Abbas Syed
 * @packageName project.as224qc.dv606.slcommuter.adapter
 */
public class SubscribedDeviationAdapter extends RecyclerView.Adapter<SubscribedDeviationAdapter.ViewHolder> {

    private ArrayList<DeviationDTO> deviations = new ArrayList<>();

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subscribed_deviation, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DeviationDTO deviation = deviations.get(position);
        holder.scopeTextView.setText(deviation.getScope());
        holder.detailsTextView.setText(deviation.getDetails());
    }

    @Override
    public int getItemCount() {
        return deviations.size();
    }

    public ArrayList<DeviationDTO> getDeviation() {
        return deviations;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView scopeTextView;
        TextView detailsTextView;

        ViewHolder(View itemView) {
            super(itemView);
            scopeTextView = (TextView) itemView.findViewById(R.id.scopeTextView);
            detailsTextView = (TextView) itemView.findViewById(R.id.detailTextView);
        }
    }

}
