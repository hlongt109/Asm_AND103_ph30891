package com.ph30891.asm_ph30891_qlsv.presenter.contract;

import android.content.Context;

public interface LoginInterface {
    Context getContext();
    void loginSuccess();
    void loginFailure();
    void emailError();
    void passwordError();
    void setLoading();
    void stopLoading();
    void clearError();

}
