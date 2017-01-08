package project.as224qc.dv606.slcommuter.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import project.as224qc.dv606.slcommuter.R;
import project.as224qc.dv606.slcommuter.model.RecyclerViewHeaderItem;
import project.as224qc.dv606.slcommuter.model.RecyclerViewItem;
import project.as224qc.dv606.slcommuter.model.RecyclerViewNormalItem;
import project.as224qc.dv606.slcommuter.model.TransportationDTO;
import project.as224qc.dv606.slcommuter.util.Constants;

/**
 * @author Abbas Syed
 * @packageName project.as224qc.dv606.slcommuter.adapter
 */
public class RealTimeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String METRO = "METRO";
    private static final String BUS = "BUS";
    private static final String TRAIN = "TRAIN";

    private int[] resIds;

    private ArrayList<TransportationDTO> transportations = new ArrayList<>();
    private ArrayList<RecyclerViewItem> items = new ArrayList<>();

    public RealTimeAdapter() {
        resIds = new int[3];
        resIds[0] = R.drawable.ic_directions_bus_blue_grey_500_18dp;
        resIds[1] = R.drawable.ic_train_blue_grey_500_18dp;
        resIds[2] = R.drawable.ic_subway_blue_grey_500_18dp;
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position) instanceof RecyclerViewHeaderItem) {
            return Constants.RecyclerViewHelper.HEADER;
        }
        return Constants.RecyclerViewHelper.ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;

        if (viewType == Constants.RecyclerViewHelper.HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_header, parent, false);
            ViewHolderHeader viewHolderHeader = new ViewHolderHeader(view);
            holder = viewHolderHeader;
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_real_time, parent, false);
            ViewHolderNormal viewHolderNormal = new ViewHolderNormal(view);
            holder = viewHolderNormal;
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == Constants.RecyclerViewHelper.ITEM) {
            onBindViewHolderNormal((ViewHolderNormal) holder, items.get(position));
        } else {
            onBindViewHolderHeader((ViewHolderHeader) holder, items.get(position));
        }
    }

    public void onBindViewHolderHeader(ViewHolderHeader holderHeader, RecyclerViewItem item) {
        RecyclerViewHeaderItem headerItem = (RecyclerViewHeaderItem) item;
        String headerTitle = headerItem.getHeaderTitle();
        holderHeader.headerTextView.setText(headerTitle);
    }

    public void onBindViewHolderNormal(ViewHolderNormal holder, RecyclerViewItem item) {
        RecyclerViewNormalItem normalItem = (RecyclerViewNormalItem) item;

        TransportationDTO transportation = transportations.get(normalItem.getPosition());
        String transportationMode = transportation.getTransportMode();

        holder.destinationTextView.setText(transportation.getDestination());
        holder.displayTimeTextView.setText(transportation.getDisplayTime());

        if (transportationMode.equals(METRO)) {
            holder.imageView.setImageResource(resIds[2]);
        } else if (transportationMode.equals(BUS)) {
            holder.destinationTextView.setText(transportation.getLineNumber() + " " + transportation.getDestination());

            holder.imageView.setImageResource(resIds[0]);
        } else if (transportationMode.equals(TRAIN)) {
            holder.imageView.setImageResource(resIds[1]);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public ArrayList<TransportationDTO> getTransportations() {
        return transportations;
    }

    public ArrayList<RecyclerViewItem> getItems() {
        return items;
    }

    class ViewHolderNormal extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView destinationTextView;
        private TextView displayTimeTextView;

        ViewHolderNormal(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            destinationTextView = (TextView) itemView.findViewById(R.id.destinationTextView);
            displayTimeTextView = (TextView) itemView.findViewById(R.id.displayTime);
        }
    }

    class ViewHolderHeader extends RecyclerView.ViewHolder {

        private TextView headerTextView;

        ViewHolderHeader(View itemView) {
            super(itemView);
            headerTextView = (TextView) itemView.findViewById(R.id.section_text);
        }
    }

}