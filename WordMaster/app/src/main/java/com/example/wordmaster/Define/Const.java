package com.example.wordmaster.Define;

public class Const {
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
    public static final int TEST_MY_FRAGMENT = 8;
    public static final int SEARCH_INFO_FRAGMENT = 9;
    public static final int OTHER_DICT2DICTIONARY_INFO = 10;

    // 단어장 공개여부
    public static final int PUBLIC = 1;
    public static final int PRIVATE = 2;
    public static final int ONLY_ME = 3;

    // 테스트 유형
    public static final int KOR2ENG = 0;
    public static final int ENG2KOR = 1;
    public static final int RANDOM2RANDOM = 2;

    //출제 유형
    public static final int LINEAR = 0;
    public static final int RANDOM = 1;

    // 리사이클러뷰 롱클릭시 다이얼로그 타입
    public static final int DIALOG_DICT_LIST = 100;
    public static final int DIALOG_DICT_WORD = 101;

    // SearchFragment 비공개 공개 여부
    public static final int SEARCH_PUBLIC = 200;
    public static final int SEARCH_PRIVATE = 201;

    //파이어베이스 경로
    public static final String USER = "lsh0510";

    // 다이얼로그 모드
    public static final String CREATE = "create";
    public static final String UPDATE = "update";

    // 파이어베이스 레퍼런스
    public static final String FIREBASE_REFERENCE_USER_ACCOUNT = "UserAccount";
    public static final String FIREBASE_REFERENCE_WORD_STORE = "WordStore";
    public static final String FIREBASE_REFERENCE_WORD_LIST = "list";
    public static final String FIREBASE_REFERENCE_WORD_TEST = "WordTest";


    // 셰어드 메니저 에디터
    public static final String SHARED_USER_ID = "UserID";
    public static final String SHARED_USER_NAME = "UserName";
    public static final String SHARED_USER_PROFILE_URI = "UserProfileUri";
    public static final String SHARED_USER_EMAIL = "UserEmail";

    // ProfileFragment 활동기록
    public static final int ACTIVITY_GRID_NONE = 0;
    public static final int ACTIVITY_GRID_BEST = 1;
    public static final int ACTIVITY_GRID_GOOD = 2;
    public static final int ACTIVITY_GRID_NORMAL = 3;
    public static final int ACTIVITY_GRID_BAD = 4;
    public static final int ACTIVITY_GRID_WORST = 5;
    public static final int ACTIVITY_GRID_EMPTY = 6;

}
