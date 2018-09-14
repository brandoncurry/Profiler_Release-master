package com.brandoncurry.profiler.constants;

import android.os.Build;

public class Constants {
    public static final int SDK_VERSION = Build.VERSION.SDK_INT;

    public static final String CUSTOM_BACKGROUND_COLOR_RED = "Red";
    public static final String CUSTOM_BACKGROUND_COLOR_ORANGE = "Orange";
    public static final String CUSTOM_BACKGROUND_COLOR_YELLOW = "Yellow";
    public static final String CUSTOM_BACKGROUND_COLOR_GREEN = "Green";
    public static final String CUSTOM_BACKGROUND_COLOR_BLUE = "Blue";
    public static final String CUSTOM_BACKGROUND_COLOR_PURPLE = "Purple";
    public static final String CUSTOM_BACKGROUND_COLOR_BLACK = "Black";

    public static final String DEFAULT_BACKGROUND_COLOR_BLUE = "Blue";
    public static final String DEFAULT_BACKGROUND_COLOR_PINK = "Pink";

    public static final String GET_PROFILE_BY_USER_ID = "userId";
    public static final String GET_PROFILE_BY_PROFILE_ID = "id";

    public static final String PROFILE_MALE = "Male";
    public static final String PROFILE_FEMALE = "Female";


    public enum FilterType {
        NONE,
        NAME,
        MALE,
        FEMALE,
        AGE
    }

    public enum SortType {
        ASCENDING,
        DESCENDING
    }
}
