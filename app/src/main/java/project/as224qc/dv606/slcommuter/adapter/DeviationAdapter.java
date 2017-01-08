package project.as224qc.dv606.slcommuter.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import project.as224qc.dv606.slcommuter.OnItemClickListener;
import project.as224qc.dv606.slcommuter.R;
import project.as224qc.dv606.slcommuter.model.DeviationDTO;

/**
 * @author Abbas Syed
 * @packageName project.as224qc.dv606.slcommuter.adapter
 */

public class DeviationAdapter extends RecyclerView.Adapter<DeviationAdapter.ViewHolder> {

    private final SimpleDateFormat dateFormat;
    private ArrayList<DeviationDTO> deviations = new ArrayList<>();


    public DeviationAdapter() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_deviation, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.itemView.setOnClickListener(new OnItemClickListener(viewHolder));

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DeviationDTO deviation = deviations.get(position);

        holder.headerTextView.setText(deviation.getHeader());
        holder.scopeTextView.setText(deviation.getScope());
        holder.detailsTextView.setText(deviation.getDetails());
        holder.createdTextView.setText(dateFormat.format(new Date(deviation.getCreated())));
    }

    @Override
    public int getItemCount() {
        return deviations.size();
    }

    public ArrayList<DeviationDTO> getDeviations() {
        return deviations;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView headerTextView;
        private TextView scopeTextView;
        private TextView detailsTextView;
        private TextView createdTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            headerTextView = (TextView) itemView.findViewById(R.id.headerTextView);
            scopeTextView = (TextView) itemView.findViewById(R.id.scopeTextView);
            detailsTextView = (TextView) itemView.findViewById(R.id.detailTextView);
            createdTextView = (TextView) itemView.findViewById(R.id.createdTextView);
        }
    }
}