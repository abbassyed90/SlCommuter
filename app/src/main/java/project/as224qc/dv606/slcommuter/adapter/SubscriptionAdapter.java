package project.as224qc.dv606.slcommuter.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import project.as224qc.dv606.slcommuter.OnItemClickListener;
import project.as224qc.dv606.slcommuter.R;
import project.as224qc.dv606.slcommuter.model.Subscription;

/**
 * @author Abbas Syed
 * @packageName project.as224qc.dv606.slcommuter.adapter
 */
public class SubscriptionAdapter extends RecyclerView.Adapter<SubscriptionAdapter.ViewHolder> {

    private ArrayList<Subscription> subscriptions = new ArrayList<>();

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subscription, parent, false));
        viewHolder.itemView.findViewById(R.id.deleteButton).setOnClickListener(new OnItemClickListener(viewHolder));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.subscriptionTextView.setText(subscriptions.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return subscriptions.size();
    }

    public ArrayList<Subscription> getSubscriptions() {
        return subscriptions;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView subscriptionTextView;
        ImageView deleteButton;

        ViewHolder(View itemView) {
            super(itemView);
            subscriptionTextView = (TextView) itemView.findViewById(R.id.subscription);
            deleteButton = (ImageView) itemView.findViewById(R.id.deleteButton);
        }
    }
}
