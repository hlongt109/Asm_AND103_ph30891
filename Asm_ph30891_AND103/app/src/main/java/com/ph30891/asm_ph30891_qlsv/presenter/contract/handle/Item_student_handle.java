package com.ph30891.asm_ph30891_qlsv.presenter.contract.handle;

import com.ph30891.asm_ph30891_qlsv.model.Students;

public interface Item_student_handle {
    public void Delete(String id);
    public void Update(String id, Students students);
}
