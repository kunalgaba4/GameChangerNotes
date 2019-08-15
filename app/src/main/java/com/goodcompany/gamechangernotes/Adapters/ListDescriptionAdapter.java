package com.goodcompany.gamechangernotes.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.goodcompany.gamechangernotes.Activities.NotesActivity;
import com.goodcompany.gamechangernotes.Database.DBNote;
import com.goodcompany.gamechangernotes.Listeners.OnListItemClickListeners;
import com.goodcompany.gamechangernotes.Modals.Subject;
import com.goodcompany.gamechangernotes.R;

import java.util.ArrayList;


/**
 * Created by iapp on 8/8/18.
 */

public class ListDescriptionAdapter extends RecyclerView.Adapter<ListDescriptionAdapter.ListDescriptionVieHolder> {
    private Context mContext;
    OnListItemClickListeners onListItemClickListeners;
    ArrayList<Subject> categories;
    private boolean isSelectedAll;
    private int checkCount;
    DBNote dbNote;



    public ListDescriptionAdapter(Context mContext, ArrayList subjectList, OnListItemClickListeners onListItemClickListeners) {
        this.categories = subjectList;
        this.mContext = mContext;
        this.onListItemClickListeners = onListItemClickListeners;
        dbNote = new DBNote(mContext);
    }

    @NonNull
    @Override
    public ListDescriptionAdapter.ListDescriptionVieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.custom_list_desc_row, parent, false);
        return new ListDescriptionAdapter.ListDescriptionVieHolder(rootView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ListDescriptionAdapter.ListDescriptionVieHolder holder, final int position) {
        holder.title_tv.setText(categories.get(position).getSubjectName());
        holder.title_tv.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/SF-UI-DISPLAY-BOLD.OTF"));
        holder.date_tv.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/SF-UI-DISPLAY-BOLD.OTF"));
        holder.date_tv.setText(""+dbNote.getNoteOfSubject(mContext,categories.get(position).getSubjectName()).size());


    }

    @Override
    public int getItemCount() {
        return categories.size();
    }




    public class ListDescriptionVieHolder extends RecyclerView.ViewHolder {
        private TextView title_tv, date_tv;
        private CheckBox checkBox;
        private ImageView delete_iv, edit_iv;

        public ListDescriptionVieHolder(final View itemView) {
            super(itemView);
            title_tv = itemView.findViewById(R.id.title_tv);
            checkBox = itemView.findViewById(R.id.desc_check_box);
            delete_iv = itemView.findViewById(R.id.delete_iv);
            edit_iv = itemView.findViewById(R.id.edit_iv);
            date_tv = itemView.findViewById(R.id.date_tv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onListItemClickListeners.onItemClicked("",getAdapterPosition());
                }
            });

            delete_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onListItemClickListeners.onListItemDelted("" + categories.get(getAdapterPosition()).getSubjectId(), getAdapterPosition());
                }
            });

            edit_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onListItemClickListeners.onListItemEdited("" + categories.get(getAdapterPosition()).getSubjectId(), getAdapterPosition(), "");
                }
            });
        }
    }
}