package com.wptdxii.framekit.base;

public interface BaseContract {

    interface BaseView {
    }

    interface BasePresenter<V extends BaseView> {

        void attachView(V view);

        void detachView();

        V getView();

        boolean isViewAttached();
    }

}
