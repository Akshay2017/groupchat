package com.example.akshay.groupchat;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Main2Activity extends AppCompatActivity {
    private DatabaseReference mData;
    private FirebaseDatabase mF;
    private FirebaseAuth mAuth;
    private RecyclerView measagelist;
    private TextView name,email;
    private EditText meaase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        name= (TextView) findViewById(R.id.name);
        email= (TextView) findViewById(R.id.email);

        meaase= (EditText) findViewById(R.id.editText);
        mAuth = FirebaseAuth.getInstance();

        measagelist= (RecyclerView) findViewById(R.id.list);
       measagelist.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseAuth.getInstance().signOut();
                Intent log = new Intent(Main2Activity.this , MainActivity.class);
                startActivity(log);

            }
        });

        FloatingActionButton fab1 = (FloatingActionButton) findViewById(R.id.floatingActionButton2);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                send();

            }
        });

        load();
    }



    private void send() {
        String uid= mAuth.getCurrentUser().getUid();
        String message=meaase.getText().toString();
        String email=mAuth.getCurrentUser().getEmail();

        mData=FirebaseDatabase.getInstance().getReference().child("meaasges").push();
        mData.child("message").setValue(message);
        mData.child("uid").setValue(uid);
        mData.child("email").setValue(email);
        Toast.makeText(this, "data insert", Toast.LENGTH_SHORT).show();

    }

    private FirebaseRecyclerAdapter<Meassages, RecyclerView.ViewHolder> firebaseadapter;

    private void load() {
        mData= FirebaseDatabase.getInstance().getReference();
        Query query=mData.child("meaasges");

         firebaseadapter= new FirebaseRecyclerAdapter<Meassages, RecyclerView.ViewHolder>(Meassages.class,R.layout.meassagelist,RecyclerView.ViewHolder.class,query) {


             private final int OUTGOING=1;
             private final int INCOMING=2;

             @Override
             protected void populateViewHolder(final RecyclerView.ViewHolder viewHolder, Meassages model, int position) {

                 if (currentuid(model)){

                     outgoingpopulateViewHolder((Outgoing) viewHolder,model);
                 }else {
                     incomingpopulateViewHolder((Incoming) viewHolder,model);
                 }

             }

             private void outgoingpopulateViewHolder(final Outgoing outgoingviewHolder, Meassages model) {

                 outgoingviewHolder.bind(model, new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {

                     }
                 });

             }

             private void incomingpopulateViewHolder(final Incoming incomingviewHolder, Meassages model) {

                 incomingviewHolder.bind2(model, new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {

                     }
                 });

             }


             @Override
             public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                 View view;
                switch (viewType){

                    case OUTGOING:
                        view= LayoutInflater.from(parent.getContext()).inflate(R.layout.meassagelist2,parent,false);
                        return new Outgoing(view);

                    case INCOMING:
                        view= LayoutInflater.from(parent.getContext()).inflate(R.layout.meassagelist,parent,false);
                        return new Incoming(view);

                 }

                 return super.onCreateViewHolder(parent, viewType);

             }

             @Override
             public int getItemViewType(int position) {
                 super.getItemViewType(position);

                 Meassages model=getItem(position);
                 if (currentuid(model)){

                     return OUTGOING;
                 }
                 return INCOMING;
             }

             private boolean currentuid(Meassages ms){
                 if (mAuth.getCurrentUser().getUid().equalsIgnoreCase(ms.getUid())){
                     return true;
                 }
                 return false;
             }




             class Outgoing extends RecyclerView.ViewHolder {

                 TextView message,email;
                 public Outgoing(View itemView) {
                     super(itemView);
                     email= (TextView) itemView.findViewById(R.id.email);
                     message= (TextView) itemView.findViewById(R.id.meassage);

                 }

                 public void bind(Meassages ms, View.OnClickListener onclick){
                     email.setText(ms.getEmail());
                     message.setText(ms.getMessage());

                 }

             }


             class Incoming extends RecyclerView.ViewHolder {

                 TextView message,email;
                 public Incoming(View itemView) {
                     super(itemView);
                     email= (TextView) itemView.findViewById(R.id.email);
                     message= (TextView) itemView.findViewById(R.id.meassage);

                 }

                 public void bind2(Meassages ms, View.OnClickListener onclick){
                     email.setText(ms.getEmail());
                     message.setText(ms.getMessage());

                 }

             }

         };

        measagelist.setAdapter(firebaseadapter);
    }




}
