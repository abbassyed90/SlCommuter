package project.as224qc.dv606.slcommuter.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import project.as224qc.dv606.slcommuter.R;

/**
 * A helper class to handle empty states in recycler views
 *
 * @author Abbas Syed
 * @packageName project.as224qc.dv606.slcommuter.util
 */
public class EmptyStateHelper {

    private View view;

    private String title = "";
    private String description = "";
    private int pictureResourceId = -1;

    public EmptyStateHelper(View view) {
        this.view = view;
    }

    /**
     * Set title that is shown when state is empty
     *
     * @param title
     */
    public void setEmptyStateTitle(String title) {
        this.title = title;
    }

    /**
     * Set picture that is shown when state is empty
     *
     * @param resourceId
     */
    public void setEmptyStatePicture(int resourceId) {
        this.pictureResourceId = resourceId;
    }

    public void showLoadingScreen(Context context, final boolean animated) {
        if (view == null) {
            return;
        }

        // find views
        final View loadingEmptyState = view.findViewById(R.id.loadingEmptyState);
        final View normalEmptyState = view.findViewById(R.id.normalEmptyState);

        // cross fade views
        if (animated) {
            loadingEmptyState.setVisibility(View.VISIBLE);
            loadingEmptyState.animate().alpha(1).setDuration(300).setListener(null);

            normalEmptyState.animate().alpha(0).setDuration(300).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    animation.removeAllListeners();
                    normalEmptyState.setVisibility(View.GONE);
                }
            });
        } else {
            normalEmptyState.setVisibility(View.GONE);
            loadingEmptyState.setVisibility(View.VISIBLE);
            loadingEmptyState.setAlpha(1);
        }
    }

    /**
     * hide loading screen and show empty state
     */
    public void showEmptyStateScreen(Context context, final boolean animated) {
        if (view == null) {
            return;
        }

        // find views
        final View loadingEmptyState = view.findViewById(R.id.loadingEmptyState);
        View normalEmptyState = view.findViewById(R.id.normalEmptyState);

        // find views
        ImageView emptyStateImageView = (ImageView) view.findViewById(R.id.emptyStateImageView);
        TextView titleTextView = ((TextView) view.findViewById(R.id.emptyStateText));

        // set title of empty state
        titleTextView.setText(title);

        // set picture if resource id has been set
        if (pictureResourceId != -1) {
            emptyStateImageView.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), pictureResourceId));
        }

        // cross fade views
        if (animated) {
            normalEmptyState.setVisibility(View.VISIBLE);
            normalEmptyState.animate().alpha(1).setDuration(300).setListener(null);

            loadingEmptyState.animate().alpha(0).setDuration(300).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    animation.removeAllListeners();
                    loadingEmptyState.setVisibility(View.GONE);
                }
            });
        } else {
            loadingEmptyState.setVisibility(View.GONE);
            normalEmptyState.setVisibility(View.VISIBLE);
            normalEmptyState.setAlpha(1);
        }
    }

    /**
     * show network error empty view
     *
     * @param context
     */
    public void showNetworkErrorState(Context context) {
        if (view == null) {
            return;
        }
        final View loadingEmptyState = view.findViewById(R.id.loadingEmptyState);
        View normalEmptyState = view.findViewById(R.id.normalEmptyState);

        ImageView emptyStateImageView = (ImageView) view.findViewById(R.id.emptyStateImageView);
        TextView titleTextView = ((TextView) view.findViewById(R.id.emptyStateText));

        titleTextView.setText(context.getString(R.string.empty_state_title_network_error));

        emptyStateImageView.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_empty_state_network_error));

        // set alpha to 0
        normalEmptyState.setVisibility(View.VISIBLE);
        normalEmptyState.setAlpha(0);

        // animate alpha to 1
        normalEmptyState.animate().alpha(1).setDuration(300).setListener(null);

        // animate alpha of loading view to 0
        loadingEmptyState.animate().alpha(0).setDuration(300).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                animation.removeAllListeners();
                loadingEmptyState.setVisibility(View.GONE);
            }
        });
    }


    /**
     * release empty state view, will set views to null
     */
    public void release() {
        ImageView imageView;

        if (view != null) {
            imageView = (ImageView) view.findViewById(R.id.emptyStateImageView);
            if (imageView != null) {
                imageView.setImageBitmap(null);
                imageView.setImageDrawable(null);
                imageView = null;
            }
        }

        imageView = null;
        view = null;
        title = null;
        description = null;
    }

}