package com.goodcompany.gamechangernotes.Activities;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.goodcompany.gamechangernotes.Adapters.ListDescriptionAdapter;
import com.goodcompany.gamechangernotes.Adapters.NotesListAdapter;
import com.goodcompany.gamechangernotes.Adapters.NotesMainAdapter;
import com.goodcompany.gamechangernotes.Database.DBNote;
import com.goodcompany.gamechangernotes.Database.DBSubject;
import com.goodcompany.gamechangernotes.Listeners.OnListItemClickListeners;
import com.goodcompany.gamechangernotes.Modals.Note;
import com.goodcompany.gamechangernotes.Modals.Subject;
import com.goodcompany.gamechangernotes.R;
import com.goodcompany.gamechangernotes.Singelton.SubjectSingleton;
import com.goodcompany.gamechangernotes.Utils.ShadowLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements OnListItemClickListeners {

    DBSubject dbSubject = new DBSubject(MainActivity.this);
    DBNote dbNote = new DBNote(MainActivity.this);
    ArrayList<Subject> savedSubjectsArraylist = new ArrayList<>();
    @BindView(R.id.dashboard_tv)
    TextView dashboardTv;
    @BindView(R.id.settings_iv)
    ImageView settingsIv;
    @BindView(R.id.dash_parent_rl)
    RelativeLayout dashParentRl;
    @BindView(R.id.active_list_parent_ll)
    LinearLayout activeListParentLl;
    @BindView(R.id.categories_rv)
    RecyclerView categoriesRv;
    @BindView(R.id.add_event_iv)
    ImageView addEventIv;
    @BindView(R.id.rel_ll)
    RelativeLayout relLl;
    @BindView(R.id.dash_parent_fl)
    FrameLayout dashParentFl;
    @BindView(R.id.active_list_tv)
    TextView activeListTv;
    @BindView(R.id.list_tv)
    TextView listTv;
    @BindView(R.id.active_tick_iv)
    ImageView activeTickIv;
    @BindView(R.id.active_sl)
    ShadowLayout activeSl;
    @BindView(R.id.upcoming_list_tv)
    TextView upcomingListTv;
    @BindView(R.id.events_tv)
    TextView eventsTv;
    @BindView(R.id.upcoming_tick_iv)
    ImageView upcomingTickIv;
    @BindView(R.id.upcoming_events_sl)
    ShadowLayout upcomingEventsSl;
    @BindView(R.id.notes_rv)
    RecyclerView notesRv;
    private LinearLayoutManager linearLayoutManager;
    private ListDescriptionAdapter listDescriptionAdapter;
    Context mContext;
    boolean allNotes, categroies;
    private NotesMainAdapter notesListAdapter;
    private ArrayList<Note> allNotes1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mContext = MainActivity.this;
        savedSubjectsArraylist = dbSubject.getAllSubject(mContext);
        setupAllCat();
        addFontToTheView();

    }

    private void addFontToTheView() {
        dashboardTv.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/SF-UI-DISPLAY-BOLD.OTF"));
        activeListTv.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/SF-UI-DISPLAY-BOLD.OTF"));
        listTv.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/SF-UI-DISPLAY-BOLD.OTF"));
        upcomingListTv.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/SF-UI-DISPLAY-BOLD.OTF"));
        eventsTv.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/SF-UI-DISPLAY-BOLD.OTF"));
    }

    private void setupDescriptionAdapter() {
        savedSubjectsArraylist = dbSubject.getAllSubject(mContext);
        listDescriptionAdapter = new ListDescriptionAdapter(mContext, savedSubjectsArraylist, this);
        linearLayoutManager = new LinearLayoutManager(mContext);
        categoriesRv.setLayoutManager(linearLayoutManager);
        categoriesRv.setAdapter(listDescriptionAdapter);
    }

    private void setupNotesAdapter() {
        allNotes1 = dbNote.getAllNote(this);
        notesListAdapter = new NotesMainAdapter(mContext, allNotes1, this);
        linearLayoutManager = new LinearLayoutManager(mContext);
        notesRv.setLayoutManager(linearLayoutManager);
        notesRv.setAdapter(notesListAdapter);
    }

//    public void showDialog() {
//        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
//        alertDialogBuilder.setTitle("Add Subject");
//        final View dialogView = getLayoutInflater().inflate(R.layout.add_subject, null);
//        alertDialogBuilder.setView(dialogView);
//
//
//
//        alertDialogBuilder.setCancelable(false);
//
//        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
//        {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i)
//            {
//                Toast.makeText(getApplicationContext(), "Cancel Clicked", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//
//        alertDialogBuilder.setPositiveButton("Save", new DialogInterface.OnClickListener()
//        {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i)
//            {
//                addSubjectEditBox = (EditText) dialogView.findViewById(R.id.addSubjectEditText);
//
//                dbSubject.insertSubject(addSubject(addSubjectEditBox.getText().toString()));
//                Toast.makeText(getApplicationContext(), "Save Clicked " + addSubjectEditBox.getText().toString(), Toast.LENGTH_SHORT).show();
//
//                Intent refresh = new Intent(MainActivity.this, MainActivity.class);
//                startActivity(refresh);
//            }
//        });
//
//        AlertDialog mAlertDialog = alertDialogBuilder.create();
//        mAlertDialog.show();
//
//    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 46) {
            if (resultCode == Activity.RESULT_OK) {
                if (notesListAdapter != null) {
                    setupNotesAdapter();
                }
            }
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if ((savedSubjectsArraylist!=null) && (listDescriptionAdapter!=null)){
            setupDescriptionAdapter();
        }else if ((allNotes1!=null) && (notesListAdapter!=null)){
            setupNotesAdapter();
        }
    }

    public Subject addSubject(String subjectName) {
        Subject addSubject = new Subject();
        addSubject.setSubjectName(subjectName);
        return addSubject;
    }

    @OnClick({R.id.dashboard_tv, R.id.settings_iv, R.id.add_event_iv, R.id.active_sl, R.id.upcoming_events_sl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.active_sl:
                setupAllNotes();
                break;
            case R.id.upcoming_events_sl:
                setupAllCat();
                break;
            case R.id.dashboard_tv:
                break;
            case R.id.settings_iv:
                break;
            case R.id.add_event_iv:
                if (categroies) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Add Category");
                    final EditText input = new EditText(this);
                    input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
                    builder.setView(input);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        private String m_Text;

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            m_Text = input.getText().toString();
                            boolean flag = false;
                            if (m_Text != null && m_Text.length() > 0) {
                                if (savedSubjectsArraylist != null && savedSubjectsArraylist.size() > 0) {
                                    for (int i = 0; i < savedSubjectsArraylist.size(); i++) {
                                        if (m_Text.contains(savedSubjectsArraylist.get(i).getSubjectName())) {
                                            flag = true;
                                        }
                                    }
                                    if (flag == false) {
                                        dbSubject.insertSubject(addSubject(m_Text));
                                        setupDescriptionAdapter();
                                    }
                                } else {
                                    dbSubject.insertSubject(addSubject(m_Text));
                                    setupDescriptionAdapter();
                                }
                            }
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                }else {
                    Intent intentToNote = new Intent(getApplicationContext(), AddNote.class);
                    Bundle b = new Bundle();
                        String m_Text ="Default";
                        boolean flag = false;
                        if (savedSubjectsArraylist != null && savedSubjectsArraylist.size() > 0) {
                            for (int i = 0; i < savedSubjectsArraylist.size(); i++) {
                                if (m_Text.contains(savedSubjectsArraylist.get(i).getSubjectName())) {
                                    flag = true;
                                }
                            }
                            if (flag == false) {
                                dbSubject.insertSubject(addSubject(m_Text));
                            }
                        } else {
                            dbSubject.insertSubject(addSubject(m_Text));
                        }
                    SubjectSingleton.getInstance().setSubjectName(m_Text);
                    b.putString("subjectName", m_Text);
                    intentToNote.putExtras(b);
                    startActivityForResult(intentToNote, 46);
                }


                break;
        }
    }

    private void setupAllCat() {
        categroies = true;
        activeTickIv.setVisibility(View.INVISIBLE);
        notesRv.setVisibility(View.INVISIBLE);
        upcomingTickIv.setVisibility(View.VISIBLE);
        categoriesRv.setVisibility(View.VISIBLE);
        setupDescriptionAdapter();
    }

    private void setupAllNotes() {
        categroies = false;
        activeTickIv.setVisibility(View.VISIBLE);
        notesRv.setVisibility(View.VISIBLE);
        upcomingTickIv.setVisibility(View.INVISIBLE);
        categoriesRv.setVisibility(View.INVISIBLE);
        setupNotesAdapter();

    }

    @Override
    public void onListItemDelted(String id, final int adapterPos) {
        showDialogForDelete(adapterPos);

    }

    public void showDialogForDelete(final int i1) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
        alertDialogBuilder.setTitle("Do you really want to delete this Subject, All notes will be deleted!!!");
        alertDialogBuilder.setCancelable(true);

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), "No Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (categroies) {
                    dbSubject.deleteSubject(savedSubjectsArraylist.get(i1));
                    dbNote.deleteNoteWithSubject((savedSubjectsArraylist.get(i1)));
                    //Delete all the notes in this subject here.
                    setupDescriptionAdapter();
                    listDescriptionAdapter.notifyDataSetChanged();
                } else {
                    dbNote.deleteNote(allNotes1.get(i1));
                    //Delete all the notes in this subject here.
                    setupAllNotes();
                }

            }
        });


        AlertDialog mAlertDialog = alertDialogBuilder.create();
        mAlertDialog.show();
    }

    @Override
    public void onListItemEdited(String id, final int adapterPos, String item_name) {


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
        alertDialogBuilder.setTitle("Do you really want to delete this Subject, All notes will be deleted!!!");
        alertDialogBuilder.setCancelable(true);

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), "No Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (categroies) {
                    Subject subject = new Subject();
                    subject = savedSubjectsArraylist.get(adapterPos);

                    dbSubject.deleteSubject(savedSubjectsArraylist.get(adapterPos));
                    dbNote.deleteNoteWithSubject((savedSubjectsArraylist.get(adapterPos)));

                    //Delete all the notes in this subject here.
                    setupDescriptionAdapter();
                    listDescriptionAdapter.notifyDataSetChanged();
                }

            }
        });


        AlertDialog mAlertDialog = alertDialogBuilder.create();
        mAlertDialog.show();


    }

    @Override
    public void onListAllChecked(boolean isAllChecked) {

    }

    @Override
    public void onItemClicked(String id, int adapterPos) {
        if (categroies) {
            Intent intentToNote = new Intent(mContext, NotesActivity.class);
            intentToNote.putExtra("SubjectName", savedSubjectsArraylist.get(adapterPos).getSubjectName());
            startActivity(intentToNote);
        } else {
            Intent intentToEditNote = new Intent(getApplicationContext(), AddNote.class);
            Bundle editNoteBundle = new Bundle();
            editNoteBundle.putBoolean("isEdit", true);
            editNoteBundle.putSerializable("NoteData", allNotes1.get(adapterPos));
            intentToEditNote.putExtras(editNoteBundle);
            startActivityForResult(intentToEditNote, 46);
        }

    }


}
