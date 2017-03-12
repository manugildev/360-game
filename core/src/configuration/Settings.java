package configuration;

public class Settings {

    //GENERAL
    public static final String COLOR_LOADING_SCREEN_BACKGROUND = "#ecf0f1";
    public static final float LOGO_SCALE = .5f;
    public static final float BACK_ALPHA = .9f;

    //MENU
    public static final float MENU_GRAVITY = -25.9f;
    public static final float PLAY_BUTTON_SIZE = 300;
    public static final float BUTTON_SIZE = 150;
    public static final boolean ACCELEROMETER_MENU = true;
    public static final float BANNER_HEIGHT = 100;
    public static final String BACKGROUND_COLOR_MENU = "FCF8F7";

    //GAMEWORLD
    public static final float WORLD_GRAVITY = 0;
    public static final float PTM = 100f; //Pixels Per Metter (Don't Touch this)
    public static final float ROTATE_SPEED = 100f;
    public static final String BACKGROUND_COLOR = "#ecf0f1";
    public static final boolean COLOR_CHANGING = true;

    //BALL
    public static final float BALL_SIZE = 70;
    public static final float MAX_BALL_SPEED = 600;
    public static final int MAX_BALL_DIRECTION_VAR = 18;
    public static final boolean BALL_TAIL = true;

    //SPIKES
    public static final int MAX_NUM_SPIKES = 15;  //Up to 36

    //COIN
    public static final int COIN_SIZE = 60;

    //COLLISION
    public static final short CATEGORY_BALL = 0x0001;  // 0000000000000001 in binary
    public static final short CATEGORY_CENTER_CIRCLE = 0x0002; // 0000000000000010 in binary
    public static final short MASK_BALL = CATEGORY_CENTER_CIRCLE | CATEGORY_BALL; // or ~CATEGORY_PLAYER
    public static final short MASK_CENTER_CIRCLE = CATEGORY_BALL; // or ~CATEGORY_MONSTER


}
