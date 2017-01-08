package project.as224qc.dv606.slcommuter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.text.style.TypefaceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import project.as224qc.dv606.slcommuter.R;
import project.as224qc.dv606.slcommuter.model.Leg;

/**
 * @author Abbas Syed
 * @packageName project.as224qc.dv606.slcommuter.adapter
 */

public class LegAdapter extends RecyclerView.Adapter<LegAdapter.ViewHolder> {

    private static final String METRO = "METRO";
    private static final String BUS = "BUS";
    private static final String TRAIN = "TRAIN";
    private static final String WALK = "WALK";
    private static final String SHIP = "SHIP";

    private final int[] resIds;
    private ArrayList<Leg> legs = new ArrayList<>();

    public LegAdapter() {
        resIds = new int[5];
        resIds[0] = R.drawable.ic_directions_bus_blue_grey_500_18dp;
        resIds[1] = R.drawable.ic_train_blue_grey_500_18dp;
        resIds[2] = R.drawable.ic_subway_blue_grey_500_18dp;
        resIds[3] = R.drawable.ic_directions_walk_blue_grey_500_18dp;
        resIds[4] = R.drawable.ic_directions_boat_blue_grey_500_18dp;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Leg leg = legs.get(position);

        String transportationMode = leg.getType();

        Context context = holder.itemView.getContext();

        holder.typeTextView.setText(leg.getLine() + " " + leg.getDirection());

        holder.originTextView.setText(leg.getOrigin().getTime() + " " + leg.getOrigin().getName());
        holder.destinationTextView.setText(leg.getDestination().getTime() + " " + leg.getDestination().getName());

        if (transportationMode.equals(METRO)) {
            holder.typeImageView.setImageResource(resIds[2]);
        } else if (transportationMode.equals(BUS)) {
            holder.typeImageView.setImageResource(resIds[0]);
        } else if (transportationMode.equals(TRAIN)) {
            holder.typeImageView.setImageResource(resIds[1]);
        } else if (transportationMode.equals(WALK)) {
            holder.typeImageView.setImageResource(resIds[3]);
            holder.typeTextView.setText(context.getString(R.string.walk));
        } else if (transportationMode.equals(SHIP)) {
            holder.typeImageView.setImageResource(resIds[4]);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_leg, parent, false));
    }

    @Override
    public int getItemCount() {
        return legs.size();
    }

    public ArrayList<Leg> getLegs() {
        return legs;
    }

    private CharSequence createSpannableString(int color, String key, String value) {
        SpannableString sb1 = new SpannableString(key);
        SpannableString sb2 = new SpannableString(value);

        TypefaceSpan typefaceSpan = new TypefaceSpan("sans-serif-medium");

        sb1.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0, sb1.length(), 0);
        sb1.setSpan(new RelativeSizeSpan(0.7f), 0, sb1.length(), 0);
        sb1.setSpan(typefaceSpan, 0, sb1.length(), 0);
        sb1.setSpan(new ForegroundColorSpan(color), 0, sb1.length(), 0);

        return TextUtils.concat(sb1, "\n", sb2);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView typeTextView;
        private ImageView typeImageView;

        private TextView originTextView;
        private TextView destinationTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            typeTextView = (TextView) itemView.findViewById(R.id.typeTextView);
            typeImageView = (ImageView) itemView.findViewById(R.id.typeImageView);

            originTextView = (TextView) itemView.findViewById(R.id.originTextView);
            destinationTextView = (TextView) itemView.findViewById(R.id.destinationTextView);
        }
    }
}