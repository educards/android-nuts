package com.educards.nuts.ui;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import com.educards.nuts.RequestFailData;
import com.educards.nuts.RequestFailReason;

/**
 * Immutable binder... may be used per multiple requests.
 *
 * @see #observeRequestState(Observer)
 * @see #observeRequestInProgress(Observer)
 * @see #observeRequestSucceeded(Observer)
 * @see #observeRequestFailed(Observer)
 *
 * @see #initRequestFailedHandler()
 *
 * @see #onAuthError(RequestFailedHandler)
 * @see #onNetworkingDisabled(RequestFailedHandler)
 * @see #onServerError(RequestFailedHandler)
 * @see #onOtherError(RequestFailedHandler)
 */
public class TemplateBinder<T> {

    private static final String TAG = "TemplateBinder";

    private TemplateData<T> data;

    private LifecycleOwner lifecycleOwner;

    private RequestFailedHandler<T> authErrorHandler = null;

    private RequestFailedHandler<T> serverErrorHandler = null;

    private RequestFailedHandler<T> networkingDisabledHandler = null;

    private RequestFailedHandler<T> otherErrorHandler = null;

    public interface RequestFailedHandler<T> {
        void handleRequestFailed(RequestFailData failData);
    }

    public TemplateBinder(@NonNull TemplateData<T> data, @NonNull LifecycleOwner lifecycleOwner) {

        failOnInvalidArguments(data, lifecycleOwner);

        this.data = data;
        this.lifecycleOwner = lifecycleOwner;

        initRequestFailedHandler();
    }

    private void failOnInvalidArguments(@NonNull TemplateData<T> dispatcher, @NonNull LifecycleOwner lifecycleOwner) {
        if (dispatcher == null || lifecycleOwner == null) {
            throw new RuntimeException("Invalid arguments");
        }
    }

    public TemplateBinder<T> observeRequestState(@NonNull Observer<? super TemplateData.RequestState> observer) {
        data.requestState.observe(lifecycleOwner, observer);
        return this;
    }

    public TemplateBinder<T> observeRequestSucceeded(@NonNull Observer<? super T> observer) {
        data.requestSucceeded.observe(lifecycleOwner, observer);
        return this;
    }

    public TemplateBinder<T> observeRequestFailed(@NonNull Observer<? super RequestFailData> observer) {
        data.requestFailed.observe(lifecycleOwner, observer);
        return this;
    }

    public TemplateBinder<T> observeRequestInProgress(@NonNull Observer<? super Boolean> observer) {
        data.requestInProgress.observe(lifecycleOwner, observer);
        return this;
    }

    protected void initRequestFailedHandler() {

        observeRequestFailed(failData -> {

            logRequestFailed(failData);

            if (failData == null) {
                handleRequestFailedOtherError();
            } else {
                RequestFailReason reason = failData.getRequestFailReason();
                if (reason == null) {
                    // TODO what's this?
                    // The emitter already handled the fail on UI

                } else {
                    switch (reason) {
                        case AUTH_ERROR: handleRequestFailedAuthError(failData); break;
                        case SERVER_ERROR: handleRequestFailedServerError(failData); break;
                        case NETWORKING_DISABLED: handleRequestFailedNetworkingDisabled(failData); break;
                        case OTHER: handleRequestFailedOtherError(failData); break;
                        default:
                            throw new RuntimeException(String.format("Unhandled case [reason=%s]", reason));
                    }
                }
            }
        });
    }

    protected void logRequestFailed(RequestFailData failData) {
        Log.e(TAG, String.format("Request failed [failData=%s]", failData));
    }

    protected void handleRequestFailedAuthError(@NonNull RequestFailData failData) {
        if (authErrorHandler != null) authErrorHandler.handleRequestFailed(failData);
    }

    protected void handleRequestFailedServerError(@NonNull RequestFailData failData) {
        if (serverErrorHandler != null) serverErrorHandler.handleRequestFailed(failData);
    }

    protected void handleRequestFailedNetworkingDisabled(@NonNull RequestFailData failData) {
        if (networkingDisabledHandler != null) networkingDisabledHandler.handleRequestFailed(failData);
    }

    protected void handleRequestFailedOtherError() {
        if (otherErrorHandler != null) otherErrorHandler.handleRequestFailed(null);
    }

    protected void handleRequestFailedOtherError(RequestFailData failData) {
        if (otherErrorHandler != null) otherErrorHandler.handleRequestFailed(failData);
    }

    public TemplateBinder<T> onAuthError(RequestFailedHandler<T> authErrorHandler) {
        this.authErrorHandler = authErrorHandler;
        return this;
    }

    public TemplateBinder<T> onServerError(RequestFailedHandler<T> serverErrorHandler) {
        this.serverErrorHandler = serverErrorHandler;
        return this;
    }

    public TemplateBinder<T> onNetworkingDisabled(RequestFailedHandler<T> networkingDisabledHandler) {
        this.networkingDisabledHandler = networkingDisabledHandler;
        return this;
    }

    public TemplateBinder<T> onOtherError(RequestFailedHandler<T> otherErrorHandler) {
        this.otherErrorHandler = otherErrorHandler;
        return this;
    }

    /**
     * Removes all the observers registered for this binder, or to be more specific for
     * the {@link LifecycleOwner lifecycle owner}, which was provided while initializing this binder.
     */
    public void unbind() {
        data.requestFailed.removeObservers(lifecycleOwner);
        data.requestSucceeded.removeObservers(lifecycleOwner);
        data.requestInProgress.removeObservers(lifecycleOwner);
        data.requestState.removeObservers(lifecycleOwner);
    }

}
