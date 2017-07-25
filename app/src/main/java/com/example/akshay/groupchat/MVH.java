package com.example.akshay.groupchat;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Akshay on 7/25/2017.
 */

class MVH extends RecyclerView.ViewHolder {

    TextView message,email;
    public MVH(View itemView) {
        super(itemView);
        email= (TextView) itemView.findViewById(R.id.email);
        message= (TextView) itemView.findViewById(R.id.meassage);

    }

    public void bind(Meassages ms, View.OnClickListener onclick){
        email.setText(ms.getEmail());
        message.setText(ms.getMessage());

    }

}

