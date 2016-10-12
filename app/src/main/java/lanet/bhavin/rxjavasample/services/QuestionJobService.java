package lanet.bhavin.rxjavasample.services;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Message;
import android.os.Messenger;
import android.os.PersistableBundle;
import android.os.RemoteException;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import lanet.bhavin.rxjavasample.MainActivity;
import lanet.bhavin.rxjavasample.interfaces.QuestionService;
import lanet.bhavin.rxjavasample.interfaces.ResultCallBack;
import lanet.bhavin.rxjavasample.models.Question;
import lanet.bhavin.rxjavasample.presenters.QuestionsPresenter;

/**
 * Created by lcom75 on 12/10/16.
 */

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class QuestionJobService extends JobService {
    public static final java.lang.String KEY_PAGE = "page";
    private static final String TAG = "QuestionJobService";
    private final LinkedList<JobParameters> jobParamsMap = new LinkedList<JobParameters>();
    QuestionService questionService;
    Map<String, String> paramMap = new HashMap<>();
    private MainActivity mActivity;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Messenger callback = intent.getParcelableExtra("messenger");
        Message m = Message.obtain();
        m.what = MainActivity.MSG_SERVICE_OBJ;
        m.obj = this;
        try {
            callback.send(m);
        } catch (RemoteException e) {
            Log.e(TAG, "Error passing service object back to activity.");
        }
        return START_NOT_STICKY;
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        jobParamsMap.add(params);
//        if (mActivity != null) {
//            mActivity.onReceivedStartJob(params);
//        }
        questionService = new QuestionService();
        QuestionsPresenter questionsPresenter = new QuestionsPresenter(questionService, new ResultCallBack() {
            @Override
            public void onQuestionsLoaded(List<Question> questions) {
                Log.d(TAG, "onQuestionsLoaded: :" + questions.size());
            }
        });
        PersistableBundle bundle = params.getExtras();
        int pageToLoad = bundle.getInt(KEY_PAGE);
        paramMap.put("page", String.valueOf(pageToLoad));
        paramMap.put("pagesize", "10");
        paramMap.put("order", "desc");
        paramMap.put("sort", "month");
        paramMap.put("tagged", "android");
        paramMap.put("site", "stackoverflow");
        paramMap.put("filter", "!teUCY-5rJg4bWNmYQQ0uZ-5lk7d06.H");
        questionsPresenter.loadPosts(paramMap);
        Log.i(TAG, "on start job: " + params.getJobId());
        return true;
    }

    /**
     * Send job to the JobScheduler.
     */
    public void scheduleJob(JobInfo t) {
        Log.d(TAG, "Scheduling job");
        JobScheduler tm =
                (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        tm.schedule(t);
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        // Stop tracking these job parameters, as we've 'finished' executing.
        jobParamsMap.remove(params);
//        if (mActivity != null) {
//            mActivity.onReceivedStopJob();
//        }
        Log.i(TAG, "on stop job: " + params.getJobId());
        return true;
    }

    public void setUiCallback(MainActivity uiCallback) {
        this.mActivity = uiCallback;
    }
}
