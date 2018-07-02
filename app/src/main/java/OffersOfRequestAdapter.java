import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hp.offermagnet.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ASUS on 25/04/2018.
 */

public class OffersOfRequestAdapter  extends  RecyclerView.Adapter<OffersOfRequestAdapter.viewHolder>{
    private static final String TAG = "OffersOfRequestAdapter";

    public OffersOfRequestAdapter(Context mycontext, ArrayList<String> imgs, ArrayList<String> userNames, ArrayList<String> offers, ArrayList<String> radioChecks) {
        this.imgs = imgs;
        this.userNames = userNames;
        this.offers = offers;
        this.radioChecks = radioChecks;
        this.mycontext = mycontext;
    }

    private ArrayList <String> imgs=new ArrayList<>();
    private ArrayList <String> userNames=new ArrayList<>();

    private ArrayList <String> offers=new ArrayList<>();

    private ArrayList <String> radioChecks=new ArrayList<>();
    private Context mycontext;




    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.offer_of_request,parent,false);
        viewHolder holder=new viewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(viewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called.");
        Glide.with(mycontext).asBitmap().load(imgs.get(position)).into(holder.profile_img);
        holder.txtName.setText(userNames.get(position));
        holder.txtOffer.setText(offers.get(position));

    }

    @Override
    public int getItemCount() {
        return imgs.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
RadioButton btn;
TextView txtName,txtOffer;
CircleImageView profile_img;
RelativeLayout offerLayout;

        //viewHolder holds every wedget in memory for each individual item
        public viewHolder(View itemView) {
            super(itemView);
            //btn=itemView.findViewById(R.id.offerItem);
            profile_img=itemView.findViewById(R.id.profile_img);
            txtName =itemView.findViewById(R.id.txt_name);
            txtOffer =itemView.findViewById(R.id.txt_offer);
            //fferLayout =itemView.findViewById(R.id.offer_layout);
        }
    }
}
