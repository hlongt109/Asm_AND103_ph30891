package com.ph30891.asm_ph30891_qlsv.presenter.contract;

import android.content.Context;

public interface SignUpInterface {
    Context getContext();
    void nameError();
    void emailError();
    void passwordError();
    void clearError();
    void setLoading();
    void clearLoading();
    void signUpSuccess();
    void signUpFail();
    void emailExists();
}
