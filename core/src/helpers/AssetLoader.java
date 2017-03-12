package helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import configuration.Configuration;

public class AssetLoader {

    public static Texture logoTexture, dotT, top, spikeT, coinT, titleT, ballT;
    public static TextureRegion logo, square, dot, spike, coin, title, ball;

    public static Texture playButtonT, buttonsT, pauseButtonT, newBestT;
    public static TextureRegion playButton, rankButton, shareButton, rateButton, iapButton, newBest;
    public static TextureRegion noMusicButton;
    public static TextureRegion musicButton;
    public static TextureRegion soundButton;
    public static TextureRegion noSoundButton;
    public static TextureRegion pauseButton;

    public static Sound click, finish, hit, coinS;
    public static Music music;

    private static Preferences prefs;
    public static BitmapFont font, font08, font23, font12;

    public static void load1() {
        logoTexture = getAssetTexture("logo.png");
        logoTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        logo = new TextureRegion(logoTexture, 0, 0, logoTexture.getWidth(),
                logoTexture.getHeight());
        load();
    }

    public static void load() {
        square = new TextureRegion(getAssetTexture("square.png"));
        dotT = getAssetTexture("dot.png");
        dotT.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        dot = new TextureRegion(dotT);

        //TOP LAYER
        top = getAssetTexture("top.png");
        top.setFilter(TextureFilter.Linear, TextureFilter.Linear);

        //SPIKE
        spikeT = getAssetTexture("spike.png");
        spikeT.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        spike = new TextureRegion(spikeT);

        //BALL
        ballT = getAssetTexture("ball.png");
        ballT.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        ball = new TextureRegion(ballT);

        //COIN
        coinT = getAssetTexture("coin.png");
        coinT.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        coin = new TextureRegion(coinT);

        //title
        titleT = getAssetTexture("title.png");
        titleT.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        title = new TextureRegion(titleT);

        //BUTTONS
        playButtonT = getAssetTexture("play_button.png");
        playButtonT.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        playButton = new TextureRegion(playButtonT);

        buttonsT = getAssetTexture("buttons.png");
        buttonsT.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        rankButton = new TextureRegion(buttonsT, 0, 0, AssetLoader.buttonsT.getWidth() / 4,
                AssetLoader.buttonsT.getHeight());
        shareButton = new TextureRegion(buttonsT, AssetLoader.buttonsT.getWidth() / 4 * 1, 0,
                AssetLoader.buttonsT.getWidth() / 4,
                AssetLoader.buttonsT.getHeight());
        rateButton = new TextureRegion(buttonsT, AssetLoader.buttonsT.getWidth() / 4 * 2, 0,
                AssetLoader.buttonsT.getWidth() / 4,
                AssetLoader.buttonsT.getHeight());
        iapButton = new TextureRegion(buttonsT, AssetLoader.buttonsT.getWidth() / 4 * 3, 0,
                AssetLoader.buttonsT.getWidth() / 4,
                AssetLoader.buttonsT.getHeight());

        //PAUSEBUTTON
        pauseButtonT = getAssetTexture("pause_button.png");
        pauseButtonT.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        pauseButton = new TextureRegion(pauseButtonT);

        //NEWBEST
        newBestT = getAssetTexture("newbest.png");
        newBestT.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        newBest = new TextureRegion(newBestT);

        //MUSIC BUTTONS
        Texture musicButtons = getAssetTexture("musicbuttons.png");
        musicButtons.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        noMusicButton = new TextureRegion(musicButtons, 0, 0, musicButtons.getWidth() / 2,
                musicButtons.getHeight());
        musicButton = new TextureRegion(musicButtons, musicButtons.getWidth() / 2, 0,
                musicButtons.getWidth() / 2, musicButtons.getHeight());

        //SOUND BUTTONS
        Texture soundButtons = getAssetTexture("soundbuttons.png");
        soundButtons.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        noSoundButton = new TextureRegion(soundButtons, 0, 0, soundButtons.getWidth() / 2,
                soundButtons.getHeight());
        soundButton = new TextureRegion(soundButtons, soundButtons.getWidth() / 2, 0,
                soundButtons.getWidth() / 2, soundButtons.getHeight());

        //FONTS
        //LOADING FONT
        Texture tfont = new Texture(Gdx.files.internal("misc/font.png"), true);
        tfont.setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.Linear);

        font = new BitmapFont(Gdx.files.internal("misc/font.fnt"), new TextureRegion(tfont),
                true);
        font.getData().setScale(1.5f, -1.5f);
        font.setColor(FlatColors.WHITE);

        font08 = new BitmapFont(Gdx.files.internal("misc/font.fnt"), new TextureRegion(tfont),
                true);
        font08.getData().setScale(0.8f, -0.8f);
        font08.setColor(FlatColors.WHITE);

        font12 = new BitmapFont(Gdx.files.internal("misc/font.fnt"), new TextureRegion(tfont),
                true);
        font12.getData().setScale(1.2f, -1.2f);
        font12.setColor(FlatColors.WHITE);

        font23 = new BitmapFont(Gdx.files.internal("misc/font.fnt"), new TextureRegion(tfont),
                true);
        font23.getData().setScale(2.3f, -2.3f);
        font23.setColor(FlatColors.WHITE);

        //PREFERENCES
        prefs = Gdx.app.getPreferences(Configuration.GAME_NAME);

        if (!prefs.contains("highScore")) {
            prefs.putInteger("highScore", 0);
        }

        if (!prefs.contains("games")) {
            prefs.putInteger("games", 0);
        }

        //Sounds
        click = getAssetSound("sounds/click.wav");
        finish = getAssetSound("sounds/finish.wav");
        hit = getAssetSound("sounds/hit.wav");
        coinS = getAssetSound("sounds/coin.wav");
        music = Assets.manager.get("sounds/music.mp3", Music.class);
    }

    public static void dispose() {
        font.dispose();
        font08.dispose();
    }

    public static void setHighScore(int val) {
        prefs.putInteger("highScore", val);
        prefs.flush();
    }

    public static void setButtonsClicked(int val) {
        prefs.putInteger("buttonsClicked", val);
        prefs.flush();
    }

    public static int getHighScore() {
        return prefs.getInteger("highScore");
    }

    public static void addGamesPlayed() {
        prefs.putInteger("games", prefs.getInteger("games") + 1);
        prefs.flush();
    }

    public static int getGamesPlayed() {
        return prefs.getInteger("games");
    }

    public static void setAds(boolean removeAdsVersion) {
        prefs = Gdx.app.getPreferences(Configuration.GAME_NAME);
        prefs.putBoolean("ads", removeAdsVersion);
        prefs.flush();
    }

    public static boolean getAds() {
        Gdx.app.log("ADS", prefs.getBoolean("ads") + "");
        return prefs.getBoolean("ads", false);
    }


    public static int getCoinNumber() {
        return prefs.getInteger("bonus");
    }

    public static void addCoinNumber(int number) {
        prefs.putInteger("bonus", prefs.getInteger("bonus") + number);
        prefs.flush();
    }

    public static void prefs() {
        prefs = Gdx.app.getPreferences(Configuration.GAME_NAME);
    }

    public static void setVolume(boolean val) {
        prefs.putBoolean("volume", val);
        prefs.flush();
    }

    public static boolean getSounds() {
        return prefs.getBoolean("sound", true);
    }

    public static void setSounds(boolean val) {
        prefs.putBoolean("sound", val);
        prefs.flush();
    }

    public static boolean getVolume() {
        return prefs.getBoolean("volume");
    }


    public static Texture getAssetTexture(String fileName) {
        return Assets.manager.get(fileName, Texture.class);
    }

    public static Sound getAssetSound(String fileName) {
        return Assets.manager.get(fileName, Sound.class);
    }
}
