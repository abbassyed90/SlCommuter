package project.as224qc.dv606.slcommuter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import project.as224qc.dv606.slcommuter.event.OnItemClickEvent;

/**
 * Helper class that is used in conjunction with
 * recyclerview adapters that adds on click events
 * to nested views
 *
 * @author Abbas Syed
 * @packageName project.as224qc.dv606.slcommuter
 */
public class OnItemClickListener implements View.OnClickListener {

    private int position = 0;
    private RecyclerView.ViewHolder viewHolder;

    public OnItemClickListener(RecyclerView.ViewHolder viewHolder) {
        this.viewHolder = viewHolder;
    }

    @Override
    public void onClick(View v) {
        // get position of clicked view
        int adapterPosition = viewHolder.getAdapterPosition();
        if (adapterPosition != RecyclerView.NO_POSITION) {
            this.position = adapterPosition;
        }

        // post to event bus
        EventBus.getInstance().post(new OnItemClickEvent(v, position));
    }
}
