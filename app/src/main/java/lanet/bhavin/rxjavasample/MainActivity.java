package lanet.bhavin.rxjavasample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lanet.bhavin.rxjavasample.adapters.UserAdapter;
import lanet.bhavin.rxjavasample.global.BuildVars;
import lanet.bhavin.rxjavasample.interfaces.UserService;
import lanet.bhavin.rxjavasample.models.User;
import lanet.bhavin.rxjavasample.presenters.UserPresnter;

public class MainActivity extends AppCompatActivity {
    private AppCompatButton mAuthorize;
    private static final String TAG = "MainActivity";
    String sToken = "";
    RecyclerView rlUsers;
    UserAdapter mPostsAdapter;
    UserPresnter mListPresenter;
    UserService mForumService;
    private ArrayList<User> posts;
    Map<String, String> paramMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuthorize = (AppCompatButton) findViewById(R.id.authorize);
        mAuthorize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogWbviewFragment fragment = DialogWbviewFragment.newInstance(BuildVars.OPEN_URL);
                fragment.onTokenReceiver(new OnNewTokenReceived() {
                    @Override
                    public void onTokenReceived(String Token) {
                        Log.d(TAG, "onTokenReceived() called with: Token = [" + Token + "]");
                        sToken = Token;
                        if (!TextUtils.isEmpty(sToken)) {
                            paramMap.put("key", BuildVars.KEY);
                            paramMap.put("access_token", sToken);
                        }
                        mListPresenter.loadPosts(paramMap);
                    }
                });
                fragment.show(getFragmentManager(), "WebView");
            }
        });
        rlUsers = (RecyclerView) findViewById(R.id.rlUsers);
        posts = new ArrayList<>();
        mPostsAdapter = new UserAdapter(this, posts);
        rlUsers.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));
        rlUsers.setAdapter(mPostsAdapter);

        mForumService = new UserService();
        mListPresenter = new UserPresnter(this, mForumService);

        paramMap.put("page", "10");
        paramMap.put("pagesize", "60");
        paramMap.put("order", "desc");
        paramMap.put("sort", "reputation");
        paramMap.put("site", "stackoverflow");
        paramMap.put("filter", "!23Iiy8heidb98bgFnwYV-");
    }

    public void displayPosts(List<User> posts) {
        this.posts.addAll(posts);
        mPostsAdapter.notifyDataSetChanged();
    }
    /**
     * Kicks off the authorization flow.
     */
}
