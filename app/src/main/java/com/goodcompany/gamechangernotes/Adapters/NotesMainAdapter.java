package com.goodcompany.gamechangernotes.Adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.goodcompany.gamechangernotes.Activities.NotesActivity;
import com.goodcompany.gamechangernotes.Listeners.OnListItemClickListeners;
import com.goodcompany.gamechangernotes.Modals.Note;
import com.goodcompany.gamechangernotes.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class NotesMainAdapter extends RecyclerView.Adapter<NotesMainAdapter.NotesMainHolder> {
    private Context mContext;
    OnListItemClickListeners onListItemClickListeners;
    ArrayList<Note> notesArray;
    private boolean isSelectedAll;
    private int checkCount;
    private NotesActivity notesActivity;
    ArrayList<Note> allNotes;



    public NotesMainAdapter(Context mContext, ArrayList subjectList, OnListItemClickListeners onListItemClickListeners) {
        this.notesArray = subjectList;
        allNotes =  new ArrayList<>();
        allNotes.addAll(notesArray);
        this.mContext = mContext;
        this.onListItemClickListeners = onListItemClickListeners;
    }

    @NonNull
    @Override
    public NotesMainAdapter.NotesMainHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.custom_notes_row, parent, false);
        return new NotesMainAdapter.NotesMainHolder(rootView);
    }



    @Override
    public void onBindViewHolder(@NonNull final NotesMainAdapter.NotesMainHolder holder, final int position) {
        holder.title_tv.setText(notesArray.get(position).getNoteTitle());
        holder.title_tv.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/SF-UI-DISPLAY-BOLD.OTF"));
        holder.date_tv.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/SF-UI-DISPLAY-BOLD.OTF"));
        try {
            String dateStr = notesArray.get(position).getDateTime();
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            java.util.Date date = formatter.parse(dateStr);

            if (isTomorrow(date)) {
                holder.date_tv.setText("Tomorrow");
            } else if (isToday(date)) {
                holder.date_tv.setText("Today");
            } else {
                SimpleDateFormat month_date = new SimpleDateFormat("MMMM dd , YYYY ", Locale.getDefault());
                String month_name = month_date.format(date);
                holder.date_tv.setText(month_name);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        if (position + 1 == getItemCount()) {
            // set bottom margin to 72dp.
            setBottomMargin(holder.itemView, (int) (72 * Resources.getSystem().getDisplayMetrics().density));
        } else {
            // reset bottom margin back to zero. (your value may be different)
            setBottomMargin(holder.itemView, 0);
        }

    }


    public static boolean isTomorrow(Date d) {
        return DateUtils.isToday(d.getTime() - DateUtils.DAY_IN_MILLIS);
    }

    public static boolean isToday(Date d) {
        return DateUtils.isToday(d.getTime());
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
        return notesArray.size();
    }

    public void deleteItem(int index) {
        notesArray.remove(index);
        notifyItemRemoved(index);
    }

    public void addItem(int index) {
//        activeList.add("String");
        notifyItemInserted(index);
    }






    public class NotesMainHolder extends RecyclerView.ViewHolder {
        private TextView title_tv,date_tv;
        private CheckBox checkBox;
        private ImageView delete_iv, edit_iv;

        public NotesMainHolder(final View itemView) {
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
                    onListItemClickListeners.onListItemDelted("" + notesArray.get(getAdapterPosition()).getNoteId(), getAdapterPosition());
                }
            });

            edit_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onListItemClickListeners.onListItemEdited("" + notesArray.get(getAdapterPosition()).getNoteId(), getAdapterPosition(), "");
                }
            });
        }
    }
}

