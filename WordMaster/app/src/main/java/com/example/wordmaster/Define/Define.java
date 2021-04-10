package com.example.wordmaster.model;

public class Define {
    //바텀네비게이션 프레그먼트
    public static final int HOME_FRAGMENT = 0;
    public static final int DICTIONARY_FRAGMENT = 1;
    public static final int TEST_FRAGMENT = 2;
    public static final int SEARCH_FRAGMENT = 3;
    public static final int MY_INFO_FRAGMENT = 4;

    // 메인액티비티 뷰 상태 (프레그먼트인지 뷰페이저인지)
    public static final int SHOW_FRAGMENT = 1;
    public static final int SHOW_VIEW_PAGER = 2;
    //그외 프레그먼트
    public static final int DICTIONARY_INFO_FRAGMENT = 5;
    public static final int TESTING_FRAGMENT = 6;
    public static final int TEST_RESULT_FRAGMENT = 7;

    // 단어장 공개여부
    public static final int PUBLIC = 1;
    public static final int PRIVATE = 2;
    public static final int ONLY_ME = 3;

    // 테스트 유형
    public static final int KOR2ENG = 0;
    public static final int ENG2KOR = 1;
    public static final int RANDOM = 2;

    // 리사이클러뷰 롱클릭시 다이얼로그 타입
    public static final int DIALOG_DICT_LIST = 100;
    public static final int DIALOG_DICT_WORD = 101;

    //파이어베이스 경로
    public static final String USER = "lsh0510";

    // 다이얼로그 모드
    public static final String CREATE = "create";
    public static final String UPDATE = "update";

}
