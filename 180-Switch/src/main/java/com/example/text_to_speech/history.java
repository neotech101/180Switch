
//NewGen Coders - Piyush Rana, Tunde Olokun, Kachail Fahmid

package com.example.text_to_speech;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class history extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    private FirebaseRecyclerAdapter<showDataItems,showDataViewHolder> mFirebaseAdapter;

    public history()
    {
        // Empty Constructor
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        Toast.makeText(this,"Welcome to History",Toast.LENGTH_SHORT).show();

        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference("User Details");

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(history.this));
        Toast.makeText(this, "Your Files Are Being Fetched ..", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart()
    {
        //Toast.makeText(this, "hey hey hey hey " , Toast.LENGTH_SHORT).show();
        super.onStart();

        mFirebaseAdapter = new FirebaseRecyclerAdapter<showDataItems,showDataViewHolder>
                (showDataItems.class,R.layout.show_data_single_item,showDataViewHolder.class,myRef)
        {
            public void populateViewHolder(final showDataViewHolder viewHolder,showDataItems mode,final int position)
            {
                viewHolder.File_URL(getApplicationContext(),mode.getFile_URL());
                viewHolder.File_Title(mode.getFile_Title());

                //onClick Item
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v)
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(history.this);
                        builder.setMessage("Do You Want To Delete This Data?").setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which)
                                    {
                                        int selectedItems = position;
                                        mFirebaseAdapter.getRef(selectedItems).removeValue();
                                        mFirebaseAdapter.notifyItemRemoved(selectedItems);
                                        recyclerView.invalidate();
                                        onStart();
                                    }
                                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.setTitle("Confirm");
                        dialog.show();
                    }
                });
            }
        };

        recyclerView.setAdapter(mFirebaseAdapter);

    }

    public static class showDataViewHolder extends RecyclerView.ViewHolder
            {
                View mView;
                private final TextView file_title;
                private final ImageView file_url;

                public showDataViewHolder(View itemView)
                {
                    super(itemView);
                    mView = itemView;
                    file_url = (ImageView) itemView.findViewById(R.id.fetch_image);
                    file_title = (TextView) itemView.findViewById(R.id.file_title);
                }

                private void File_Title(String title)
                {
                    file_title.setText(title);
                }

                public void File_URL(Context context,String image)
                {
//                    Glide.with(itemView.getContext())
//                            .load(title)
//                            .crossFade()
//                            .placeholder(R.drawable.download)
//                            .thumbnail(0.1f)
//                            .diskCacheStrategy(DiskCacheStrategy.ALL)
//                            .into(file_url);

                    Picasso.with(context).load(image).into(file_url);
                }
            }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.mic:
                Intent t2s = new Intent(android.content.Intent.ACTION_VIEW);
                t2s.setData(Uri.parse("http://searchmobilecomputing.techtarget.com/definition/text-to-speech"));
                startActivity(t2s);
                return true;
            case R.id.help:
                Intent help = new Intent(android.content.Intent.ACTION_VIEW);
                help.setData(Uri.parse("http://searchmobilecomputing.techtarget.com/definition/text-to-speech"));
                startActivity(help);

                return true;
            case R.id.name:


                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                final String email = user.getEmail();

                final TextView useremail = (TextView) findViewById(R.id.name);

                useremail.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v) {
                        useremail.setText(email);
                    }
                });

                return true;

            case R.id.exit:
                new AlertDialog.Builder(this).setTitle(R.string.exit).setMessage(R.string.exitCon).setCancelable(true)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                                System.exit(0);
                            }
                        }).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
//        return false;
    }
}
