package lanet.bhavin.rxjavasample;

import android.app.job.JobInfo;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.os.PersistableBundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.Toast;

import com.eftimoff.androipathview.PathView;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lanet.bhavin.rxjavasample.adapters.UserAdapter;
import lanet.bhavin.rxjavasample.interfaces.RecyclerClickListener;
import lanet.bhavin.rxjavasample.interfaces.UserService;
import lanet.bhavin.rxjavasample.models.Question;
import lanet.bhavin.rxjavasample.models.User;
import lanet.bhavin.rxjavasample.presenters.UserPresnter;
import lanet.bhavin.rxjavasample.services.QuestionJobService;

public class MainActivity extends AppCompatActivity {
    public static final int MSG_UNCOLOUR_START = 0;
    public static final int MSG_UNCOLOUR_STOP = 1;
    public static final int MSG_SERVICE_OBJ = 2;
    private static final String TAG = "MainActivity";
    private static int kJobId = 1;
    String sToken = "";
    RecyclerView rlUsers;
    UserAdapter mPostsAdapter;
    UserPresnter mListPresenter;
    UserService mForumService;
    Map<String, String> paramMap = new HashMap<>();
    ComponentName mServiceComponent;
    QuestionJobService jobService;
    Handler mHandler = new Handler(/* default looper */) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_UNCOLOUR_START:
//                    mShowStartView.setBackgroundColor(defaultColor);
                    break;
                case MSG_UNCOLOUR_STOP:
//                    mShowStopView.setBackgroundColor(defaultColor);
                    break;
                case MSG_SERVICE_OBJ:
                    jobService = (QuestionJobService) msg.obj;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        jobService.setUiCallback(MainActivity.this);
                    }
            }
        }
    };
    /**
     * Kicks off the authorization flow.
     */
    int page = 300;
    int offSet = 30;
    private AppCompatButton mAuthorize;
    private ArrayList<User> posts;
    private PathView pathView;
    private LinearLayoutManager llm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        int height = 48;
//        int length = 24;
//        final Path path = new Path();
//        path.moveTo(0.0f, 0.0f);
//        path.lineTo(length / 4f, 0.0f);
//        path.lineTo(length, height / 2.0f);
//        path.lineTo(length / 4f, height);
//        path.lineTo(0.0f, height);
//        path.lineTo(length * 3f / 4f, height / 2f);
//        path.lineTo(0.0f, 0.0f);
//        path.close();
        pathView = (PathView) findViewById(R.id.viewEmpty);
//        pathView.getPathAnimator().start();
        pathView.getPathAnimator()
                .delay(100)
                .duration(1000)
                .interpolator(new AccelerateDecelerateInterpolator())
                .start();
        mAuthorize = (AppCompatButton) findViewById(R.id.authorize);
        mAuthorize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                DialogWbviewFragment fragment = DialogWbviewFragment.newInstance(BuildVars.OPEN_URL);
//                fragment.onTokenReceiver(new OnNewTokenReceived() {
//                    @Override
//                    public void onTokenReceived(String Token) {
//                        Log.d(TAG, "onTokenReceived() called with: Token = [" + Token + "]");
//                        sToken = Token;
//                        if (!TextUtils.isEmpty(sToken)) {
//                            paramMap.put("key", BuildVars.KEY);
//                            paramMap.put("access_token", sToken);
//                        }
                mListPresenter.loadPosts(paramMap);
                //addNewData();
            }
        });
//                fragment.show(getFragmentManager(), "WebView");
//            }
//        });
        rlUsers = (RecyclerView) findViewById(R.id.rlUsers);
        posts = new ArrayList<>();
        mPostsAdapter = new UserAdapter(this, posts, new RecyclerClickListener() {

            @Override
            public void onItemClicked(int position, Object object) {
                if (object != null) {
                    User user = (User) object;
                    Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                    intent.putExtra("User", user);
                    startActivity(intent);
                }
            }
        });
        rlUsers.setLayoutManager(llm = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));
        rlUsers.setAdapter(mPostsAdapter);

        mForumService = new UserService();
        mListPresenter = new UserPresnter(this, mForumService);

        paramMap.put("page", "10");
        paramMap.put("pagesize", "60");
        paramMap.put("order", "desc");
        paramMap.put("sort", "reputation");
        paramMap.put("site", "stackoverflow");
        paramMap.put("filter", "!23Iiy8heidb98bgFnwYV-");

//        paramMap.put("page", "1");
//        paramMap.put("pagesize", "10");
//        paramMap.put("order", "desc");
//        paramMap.put("sort", "month");
//        paramMap.put("tagged", "android");
//        paramMap.put("site", "stackoverflow");
//        paramMap.put("filter", "!teUCY-5rJg4bWNmYQQ0uZ-5lk7d06.H");


        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                Object value = getIntent().getExtras().get(key);
                Log.d(TAG, "Key: " + key + " Value: " + value);
            }
        }
        // [END handle_data_extras]

        Button subscribeButton = (Button) findViewById(R.id.subscribeButton);
        subscribeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build();

                FirebaseMessaging.getInstance().subscribeToTopic("news");
                String msg = getString(R.string.msg_subscribed);
                Log.d(TAG, msg);
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });

        Button logTokenButton = (Button) findViewById(R.id.logTokenButton);
        logTokenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get token
                String token = FirebaseInstanceId.getInstance().getToken();
                // Log and toast
                String msg = getString(R.string.msg_token_fmt, token);
                Log.d(TAG, msg);
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
        mServiceComponent = new ComponentName(this, QuestionJobService.class);
        Intent startServiceIntent = new Intent(this, QuestionJobService.class);
        startServiceIntent.putExtra("messenger", new Messenger(mHandler));
        startService(startServiceIntent);
    }

    private boolean ensureTestService() {
        if (jobService == null) {
            Toast.makeText(MainActivity.this, "Service null, never got callback?",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void displayPosts(List<User> posts) {
        this.posts.addAll(posts);
        mPostsAdapter.notifyDataSetChanged();
        Log.d(TAG, "Let's schedule Job for retrieve questions");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            scheduleJob();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void scheduleJob() {
        if (!ensureTestService()) {
            return;
        }
        JobInfo.Builder builder = new JobInfo.Builder(kJobId, mServiceComponent);
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED);
        builder.setRequiresCharging(true);
        PersistableBundle bundle = new PersistableBundle();
        bundle.putInt(QuestionJobService.KEY_PAGE, kJobId);
        builder.setExtras(bundle);
        jobService.scheduleJob(builder.build());
    }

    public void displayQuestions(List<Question> questions) {

    }

    public void addNewData() {
        if (mPostsAdapter == null) {
            posts = new ArrayList<>();
            mPostsAdapter = new UserAdapter(this, posts, new RecyclerClickListener() {

                @Override
                public void onItemClicked(int position, Object object) {
                    if (object != null) {
                        User user = (User) object;
                        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                        intent.putExtra("User", user);
                        startActivity(intent);
                    }
                }
            });
            rlUsers.setLayoutManager(llm = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));
            rlUsers.setAdapter(mPostsAdapter);
        } else {
            Log.d(TAG, "addNewData: " + llm.findFirstVisibleItemPosition() + "::" + llm.findFirstCompletelyVisibleItemPosition());
        }
        for (int i = page; i >= page - offSet; i--) {
            posts.add(0, new User(i));
        }
        page -= offSet;
        mPostsAdapter.notifyItemRangeInserted(0, offSet);
    }
}
