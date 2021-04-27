package com.educards.nuts.ui;

import com.educards.nuts.RequestFailData;
import com.educards.nuts.Template;

/**
 * Observable data for {@link Template<T>}.
 */
public class TemplateData<T> implements Template<T> {

    public enum RequestState {

        READY(true, false),
        IN_PROGRESS(false, false),
        SUCCEEDED(false, true),
        FAILED(false, true);

        private boolean initialState;

        private boolean targetState;

        RequestState(boolean initialState, boolean targetState) {
            this.initialState = initialState;
            this.targetState = targetState;
        }

        public boolean isInitialState() {
            return initialState;
        }

        public boolean isTargetState() {
            return targetState;
        }

    }

    /**
     * The state of the very last request.
     */
    public final Observable<RequestState> requestState;

    /**
     * <ul>
     *     <li>{@link Boolean#TRUE} if a request is being executed</li>
     *     <li>{@link Boolean#FALSE} if a request execution is finished
     *     or no request has been executed so far</li>
     * </ul>
     */
    public final Observable<Boolean> requestInProgress;

    /**
     * <ul>
     *     <li><code>null</code> if no request has finished yet</li>
     *     <li>{@link Boolean#TRUE} if the very last request succeeded</li>
     *     <li>{@link Boolean#FALSE} if the very last request failed</li>
     * </ul>
     */
    public final Observable<T> requestSucceeded;

    /**
     * <ul>
     *     <li><code>null</code> if no request has failed yet</li>
     *     <li>{@link RequestFailData} which can be used to show reason of the fail on UI,
     *     or to re-execute the very same request (e.g. immediately or after the
     *     problem is resolved)</li>
     * </ul>
     */
    public final Observable<RequestFailData> requestFailed;

    public TemplateData() {
        this.requestState = initRequestStateObservable();
        this.requestInProgress = initRequestInProgress();
        this.requestSucceeded = initRequestSucceeded();
        this.requestFailed = initRequestFailed();
    }

    // TODO Use builder patter instead of overriding
    //      all of the following 'init' methods.

    public Observable<RequestState> initRequestStateObservable() {
        ObservableData observable = new ObservableData<>();
        observable.setValue(RequestState.READY); // init value
        return observable;
    }

    public Observable<Boolean> initRequestInProgress() {
        ObservableData<Boolean> observable = new ObservableData<>();
        observable.setValue(Boolean.FALSE); // init value
        return observable;
    }

    public Observable<T> initRequestSucceeded() {
        return new ObservableData<>();
    }

    public Observable<RequestFailData> initRequestFailed() {
        return new ObservableData<>();
    }

    @Deprecated
    public void clearValues() {
        throw new RuntimeException("Deprecated call: " +
                "This object is immutable so instead of clearing the " +
                "values call close() and recreate the LiveData again.");
    }

    @Override
    public void onRequestInProgress() {
        requestInProgress.postValue(true);
        setRequestStateChanged(RequestState.IN_PROGRESS);
    }

    @Override
    public void onRequestSucceeded(T responseData) {
        requestSucceeded.postValue(responseData);
        requestInProgress.postValue(false);
        setRequestStateChanged(RequestState.SUCCEEDED);
    }

    @Override
    public void onRequestFailed(RequestFailData requestFailData) {
        requestInProgress.postValue(false);
        requestFailed.postValue(requestFailData);
        setRequestStateChanged(RequestState.FAILED);
    }

    /**
     * Must be executed as the very last call to enable
     * clients to query actual values of dedicated
     * query attributes (requestInProgress, requestSucceeded, requestFailed).
     * Client's usually listen for 'state change' and upon their notification
     * they query other things like value of 'requestSucceeded', 'requestInProgress', etc.
     */
    private void setRequestStateChanged(RequestState requestStateValue) {
        requestState.postValue(requestStateValue);
    }

}
