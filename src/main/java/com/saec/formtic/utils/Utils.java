package com.saec.formtic.utils;

import com.saec.formtic.model.status.StatusCategory;
import com.saec.formtic.model.status.StatusName;

public interface Utils {
    public static boolean itsBlankString(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static boolean itsRequiredStatusCategory(StatusCategory requiredStatusCategory, String sc) {
        StatusCategory statusCategory;
        try {
            statusCategory = StatusCategory.valueOf(sc.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return false;
        }
        return requiredStatusCategory == statusCategory;
    }
}
