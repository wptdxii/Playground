package com.wptdxii.framekit.base;

/**
 * Created by wptdxii on 2018/1/26
 */

public abstract class BasePresenter<V extends BaseContract.BaseView> implements BaseContract.BasePresenter<V> {

    private V mView;

    public BasePresenter(V view) {
        this.mView = view;
    }

    @Override
    public void attachView(V view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        if (mView != null) {
            mView = null;
        }
    }

    @Override
    public V getView() {
        return mView;
    }

    @Override
    public boolean isViewAttached() {
        return mView != null;
    }

    public void checkViewAttached() {
        if (!isViewAttached()) throw new MvpViewNotAttachedException();
    }

    private static class MvpViewNotAttachedException extends RuntimeException {
        MvpViewNotAttachedException() {
            super("Please call Presenter.attachView(V view) before " +
                    "requesting data to the Presenter");
        }
    }
}
