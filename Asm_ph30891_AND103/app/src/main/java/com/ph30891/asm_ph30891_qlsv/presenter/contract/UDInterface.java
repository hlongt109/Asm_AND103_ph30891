package com.ph30891.asm_ph30891_qlsv.presenter.contract;

import android.app.Activity;
import android.content.Context;

public interface UDInterface {
    Activity getActivity();
    Context getContext();
    void nameError();
    void msvError();
    void diemTbError();
    void avatarError();
    void clearErr();
    void showLoad();
    void hideLoad();
}
