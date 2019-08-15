package com.goodcompany.gamechangernotes.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.goodcompany.gamechangernotes.Listeners.OnListCancelListener;
import com.goodcompany.gamechangernotes.R;
import com.goodcompany.gamechangernotes.Utils.Constants;

import java.util.ArrayList;


/**
 * Created by iapp on 7/8/18.
 */

public class ListItemsAdapter extends RecyclerView.Adapter<ListItemsAdapter.ListItemsViewHolder> {
    private Context mContext;
    ArrayList<String> eventList;
    OnListCancelListener onEventCancelListener;


    public ListItemsAdapter(Context activity, ArrayList eventList,OnListCancelListener onEventCancelListener) {
        this.eventList = eventList;
        mContext = activity;
        this.onEventCancelListener = onEventCancelListener;
    }

    @NonNull
    @Override
    public ListItemsAdapter.ListItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.custom_list_item_row, parent, false);
        return new ListItemsAdapter.ListItemsViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListItemsAdapter.ListItemsViewHolder holder, final int position) {
        holder.listHeading.setText(eventList.get(position));
        holder.listHeading.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/SF-UI-DISPLAY-BOLD.OTF"));
        holder.removeEvent.getLayoutParams().height= (int) (0.07* Constants.sWidth);
        holder.removeEvent.getLayoutParams().width= (int) (0.07* Constants.sWidth);

    }

    public static void setBottomMargin(View view, int bottomMargin) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            params.setMargins(params.leftMargin, params.topMargin, params.rightMargin, bottomMargin);
            view.requestLayout();
        }
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public void deleteItem(int index) {
        eventList.remove(index);
        notifyItemRemoved(index);
    }

    public void addItem(int index) {
//        activeList.add("String");
        notifyItemInserted(index);
    }

    public class ListItemsViewHolder extends RecyclerView.ViewHolder {
        private ImageView removeEvent;
        private TextView listHeading;

        public ListItemsViewHolder(View itemView) {
            super(itemView);
            removeEvent = itemView.findViewById(R.id.cancel_iv);
            listHeading = itemView.findViewById(R.id.list_head_tv);
            removeEvent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onEventCancelListener.onListCancelledClicked(getAdapterPosition(), view,"");
                }
            });
        }
    }
}

