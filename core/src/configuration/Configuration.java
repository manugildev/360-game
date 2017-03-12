package configuration;

import com.badlogic.gdx.graphics.Color;

import helpers.FlatColors;

public class Configuration {
    public static final String GAME_NAME = "360";

    //ADMOB IDS
    public static final String AD_UNIT_ID_BANNER = "ca-app-pub-6147578034437241/1779202616";
    public static final String AD_UNIT_ID_INTERSTITIAL = "ca-app-pub-6147578034437241/4732669017";
    public static final float AD_FREQUENCY = 0.6f;

    //IAP
    public static final boolean IAP = true;
    public static final String PRODUCT_ID = "removeads";
    public static final String ENCODED_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqNCqMHG9wZJvKdhEJYagpukcejMYzCjHL1hq9AcCI1i+NXXBTPL3MiwgAAZjUD9STBWyP26GfOkrLb5g8jfckjSp8fFZEi/tVdmMTSOBVUaNNTWZIOmkg7DnKsIs2wmN9z0U9xJOJ0TFMpkZpZupRmCClNnhIYWv1gTxH1vz4VojrMc8xs3PudGl+xFJFMD9qV8AYW1X08yhCq9H8s/1cebxicBKG4sFJnliwXbWWi3OnwnOe32cL4XVjuN0ekHwYaaw1xT9DluIePFGRAOkMrDutsaVu/6VrcyPIFU0vAh6arW51qX+znbKBBTFbE8Y/DJgQIUjHTtSKuSp5eO7xwIDAQAB";

    //GOOGLE ANALYTICS
    public static final boolean ANALYTICS = true;
    public static final String ANALYTICS_ID = "UA-61248564-5";

    //LEADERBOARDS IDS
    public static final String LEADERBOARD_HIGHSCORE = "CgkIo42lw50XEAIQAQ";
    public static final String LEADERBOARD_GAMESPLAYED = "CgkIo42lw50XEAIQAg";

    //TEXTS
    public static final String BEST_TEXT = "High Score: ";
    public static final String GAMES_PLAYED_TEXT = "Games Played: ";
    public static final String SCORE_TEXT = "Score: ";
    public static final String BEST_TEXT_GAME = "Best: ";
    public static final String PAUSE_TEXT = "PAUSE";
    public static final String SHARE_MESSAGE = "Can you beat me at " + GAME_NAME + "?";
    public static final String DIALOG_TEXT = "Do you like 360?";
    public static final String DIALOG_YES = "Yes!";
    public static final String DIALOG_NO = "No";

    //DEBUG
    public static boolean FPS_COUNTER = false;
    public static Color FPS_COUNTER_COLOR = FlatColors.BLACK;
    public static boolean DEBUG = false;
    public static final float MUSIC_VOLUME = .8f;


}
