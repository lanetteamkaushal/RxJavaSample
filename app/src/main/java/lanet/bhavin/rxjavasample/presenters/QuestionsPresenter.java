package lanet.bhavin.rxjavasample.presenters;

import java.util.Map;

import lanet.bhavin.rxjavasample.MainActivity;
import lanet.bhavin.rxjavasample.interfaces.QuestionService;
import lanet.bhavin.rxjavasample.interfaces.ResultCallBack;
import lanet.bhavin.rxjavasample.models.Questions;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by lcom75 on 12/10/16.
 */

public class QuestionsPresenter {

    MainActivity mView;
    QuestionService mForum;
    ResultCallBack resultCallBack;

    public QuestionsPresenter(MainActivity view, QuestionService forum) {
        mView = view;
        mForum = forum;
    }

    public QuestionsPresenter(QuestionService mForum) {
        this.mView = null;
        this.mForum = mForum;
    }

    public QuestionsPresenter(QuestionService mForum, ResultCallBack resultCallBack) {
        this.mForum = mForum;
        this.resultCallBack = resultCallBack;
    }

    public void loadPosts(Map<String, String> options) {

        mForum.getQuestionApi()
                .getUsers(options)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Questions>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Questions users) {
                        if (mView != null) mView.displayQuestions(users.getItems());
                        if (resultCallBack != null)
                            resultCallBack.onQuestionsLoaded(users.getItems());
                    }
                });
    }

}
