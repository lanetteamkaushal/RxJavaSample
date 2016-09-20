package lanet.bhavin.rxjavasample.presenters;

import android.app.ListActivity;

import java.util.List;
import java.util.Map;

import lanet.bhavin.rxjavasample.MainActivity;
import lanet.bhavin.rxjavasample.interfaces.UserService;
import lanet.bhavin.rxjavasample.models.User;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class UserPresnter {

    MainActivity mView;
    UserService mForum;

    public UserPresnter(MainActivity view, UserService forum) {
        mView = view;
        mForum = forum;
    }

    public void loadPosts(Map<String, String> options) {

        mForum.getUserApi()
                .getUsers(options)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<User>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<User> users) {
                        mView.displayPosts(users);
                    }
                });
    }

}