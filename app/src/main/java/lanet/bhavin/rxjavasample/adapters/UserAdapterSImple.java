package lanet.bhavin.rxjavasample.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import lanet.bhavin.rxjavasample.R;
import lanet.bhavin.rxjavasample.interfaces.RecyclerClickListener;
import lanet.bhavin.rxjavasample.models.User;

/**
 * Created by lcom75 on 20/9/16.
 */

public class UserAdapterSImple extends RecyclerView.Adapter<UserAdapterSImple.UserHolder> {
    private static final String TAG = "UserAdapter";
    private final Context context;
    private final ArrayList<User> objects;
    LayoutInflater inflater;
    private RecyclerClickListener recyclerClickListener;

    public UserAdapterSImple(Context context, ArrayList<User> objects, RecyclerClickListener recyclerClickListener) {
        this.context = context;
        this.objects = objects;
        this.recyclerClickListener = recyclerClickListener;
        inflater = LayoutInflater.from(this.context);
    }

    private User getItem(int position) {
        if (position < objects.size())
            return objects.get(position);
        return null;
    }

    @Override
    public UserHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.user_row, parent, false);
        final UserHolder myHolder = new UserHolder(view);
        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (recyclerClickListener != null) {
                    recyclerClickListener.onItemClicked(myHolder.getAdapterPosition(), getItem(myHolder.getAdapterPosition()));
                }
            }
        });
        return myHolder;
    }

    @Override
    public void onBindViewHolder(UserHolder holder, int position) {
        User user = getItem(position);
        holder.tvUserName.setText(user.getDisplay_name());
        holder.tvLocation.setText(user.getLocation());
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    public class UserHolder extends RecyclerView.ViewHolder {
        TextView tvUserName;
        TextView tvLocation;

        public UserHolder(View itemView) {
            super(itemView);
            tvUserName = (TextView) itemView.findViewById(R.id.tvUserName);
            tvLocation = (TextView) itemView.findViewById(R.id.tvLocation);
        }
    }
}
