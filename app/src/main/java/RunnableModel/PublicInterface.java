package RunnableModel;

import java.util.List;

import JavaBeen.PublicBeen;


/**
 * Created by Administrator on 2017/4/25.
 */

public interface PublicInterface {
    void onGetDataSuccess(List<PublicBeen> list);
    void onGetDataError(String errmessage);
    void onEmptyData(String Emptymessage);
}
