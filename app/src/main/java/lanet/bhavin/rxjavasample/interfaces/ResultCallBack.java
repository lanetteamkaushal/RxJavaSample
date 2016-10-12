package lanet.bhavin.rxjavasample.interfaces;

import java.util.List;

import lanet.bhavin.rxjavasample.models.Question;

/**
 * Created by lcom75 on 12/10/16.
 */

public interface ResultCallBack {
    void onQuestionsLoaded(List<Question> questions);
}
