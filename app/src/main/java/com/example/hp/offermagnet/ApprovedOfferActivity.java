package com.example.hp.offermagnet;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ApprovedOfferActivity extends AppCompatActivity {
    CircleImageView img;
    TextView name,titleOfRequest,offerApprove;
    Button contact;

    String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approved_offer);

        img=findViewById(R.id.user_image_offer);
        name=findViewById(R.id.user_name_approve);
        titleOfRequest=findViewById(R.id.request_Title);
        offerApprove=findViewById(R.id.approved_offer);
        contact=findViewById(R.id.btn_contact);
        final Bundle extras= getIntent().getExtras();
        final String title=extras.getString("title");
        final String description=extras.getString("desc");
        String path=extras.getString("picture");
        String name_user=extras.getString("name");
        phone=extras.getString("phone");
        name.setText(name_user);
        titleOfRequest.setText(title);
        Picasso.with(this)
                .load(path)
                .into(img);
        offerApprove.setText(description);
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApprovedOfferActivity.this.startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null)));

            }
        });







    }
}
