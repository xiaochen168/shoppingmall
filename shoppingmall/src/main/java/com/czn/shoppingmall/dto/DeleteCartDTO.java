package com.czn.shoppingmall.dto;

import java.util.List;

public class DeleteCartDTO {
    List<Integer> deleteIds;

    public List<Integer> getDeleteIds() {
        return deleteIds;
    }

    public void setDeleteIds(List<Integer> deleteIds) {
        this.deleteIds = deleteIds;
    }
}
