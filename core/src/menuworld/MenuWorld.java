package menuworld;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;

import MainGame.ActionResolver;
import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import configuration.Configuration;
import configuration.Settings;
import gameobjects.CenterCircle;
import gameobjects.GameObject;
import gameobjects.Star;
import gameworld.GameRenderer;
import gameworld.GameWorld;
import helpers.AssetLoader;
import helpers.FlatColors;
import screens.GameScreen;
import screens.MenuScreen;
import tweens.Value;
import ui.MenuButton;
import ui.MusicButton;
import ui.SoundButton;
import ui.Text;

public class MenuWorld extends GameWorld {

    public float gameWidth, gameHeight;
    public Game game;

    public ArrayList<MenuButton> menuButtons = new ArrayList<MenuButton>();

    //GAMEOBJECTS
    public GameObject background, top, title, newBest;
    public CenterCircle centerCircle;
    public MusicButton musicButton;
    public SoundButton soundButton;
    public Text bestText, gamesText;
    public ArrayList<Star> stars = new ArrayList<Star>();
    public int numOfStars = 100;

    //BOX2D
    Box2DDebugRenderer debugRenderer;
    Matrix4 debugMatrix;
    public World worldB;
    private boolean available;

    public enum STATE {MENU, GAMEOVER}

    public STATE state;

    public MenuWorld(Game game, final ActionResolver actionResolver, float gameWidth,
                     float gameHeight,
                     STATE state, int score) {
        super(game, actionResolver, gameWidth, gameHeight);
        this.state = state;
        this.score = score;

        this.worldB = new World(new Vector2(0, Settings.MENU_GRAVITY), true);
        debugRenderer = new Box2DDebugRenderer();
        top = new GameObject(this, 0, 0, gameWidth, gameHeight, AssetLoader.square,
                FlatColors.WHITE, GameObject.Shape.RECTANGLE);
        top.fadeOut(.5f, 0f);
        background = new GameObject(this, 0, 0, gameWidth, gameHeight, AssetLoader.square,
                FlatColors.parseColor(Settings.BACKGROUND_COLOR_MENU), GameObject.Shape.RECTANGLE);
        centerCircle = new CenterCircle(this, gameWidth / 2 - (gameWidth - 170) / 2,
                gameHeight / 2 - (gameWidth - 170) / 2, gameWidth - 170,
                gameWidth - 170, AssetLoader.dot, Color.WHITE, GameObject.Shape.CIRCLE);

        MenuButton playButton = new MenuButton(this, gameWidth / 2, gameHeight / 2 - 130,
                Settings.PLAY_BUTTON_SIZE, Settings.PLAY_BUTTON_SIZE, AssetLoader.playButton,
                FlatColors.WHITE, GameObject.Shape.CIRCLE);
        MenuButton shareButton = new MenuButton(this, gameWidth / 2 - 150 - 95 - 25,
                gameHeight / 2 - 100,
                Settings.BUTTON_SIZE, Settings.BUTTON_SIZE, AssetLoader.shareButton,
                FlatColors.WHITE, GameObject.Shape.CIRCLE);
        MenuButton rankButton = new MenuButton(this, gameWidth / 2 + 150 + 95 + 25,
                gameHeight / 2 - 100, Settings.BUTTON_SIZE, Settings.BUTTON_SIZE,
                AssetLoader.rankButton,
                FlatColors.WHITE, GameObject.Shape.CIRCLE);
        MenuButton rateButton = new MenuButton(this, gameWidth / 2 + 150 + 60, gameHeight / 2 + 200,
                Settings.BUTTON_SIZE - 30, Settings.BUTTON_SIZE - 30, AssetLoader.rateButton,
                FlatColors.WHITE, GameObject.Shape.CIRCLE);

        MenuButton iapButton = new MenuButton(this, gameWidth / 2 - 150 - 60, gameHeight / 2 + 200,
                Settings.BUTTON_SIZE - 10, Settings.BUTTON_SIZE - 10, AssetLoader.iapButton,
                FlatColors.WHITE, GameObject.Shape.CIRCLE);

        menuButtons.add(playButton);
        menuButtons.add(shareButton);
        menuButtons.add(rankButton);
        menuButtons.add(rateButton);
        if (Configuration.IAP)
            menuButtons.add(iapButton);

        title = new GameObject(this, 0, gameHeight / 2 + 200, gameWidth,
                AssetLoader.title.getRegionHeight(), AssetLoader.title, FlatColors.WHITE,
                GameObject.Shape.RECTANGLE);
        title.effectY(title.getPosition().y + gameHeight, title.getPosition().y, .8f, 0f);

        bestText = new Text(this, 0, gameHeight / 2 + 80, gameWidth, 100, AssetLoader.square,
                FlatColors.WHITE,
                isGameOver() ? Configuration.SCORE_TEXT + score : Configuration.BEST_TEXT + AssetLoader
                        .getHighScore(), AssetLoader.font, FlatColors.DARK_BLACK, 0, Align.center);
        bestText.effectX(-gameWidth, 0, .8f, .2f);
        gamesText = new Text(this, 0, gameHeight / 2 + 70 - 100, gameWidth, 100, AssetLoader.square,
                FlatColors.WHITE, isGameOver() ? Configuration.BEST_TEXT + AssetLoader
                .getHighScore() : Configuration.GAMES_PLAYED_TEXT + AssetLoader
                .getGamesPlayed(), AssetLoader.font, FlatColors.DARK_BLACK, 0, Align.center);
        gamesText.effectX(gameWidth, 0, .8f, .2f);

        musicButton = new MusicButton(this,
                gameWidth / 2 - (AssetLoader.musicButton.getRegionWidth() / 1.2f) - 20,
                gameHeight - 135 - 30, AssetLoader.musicButton.getRegionWidth() / 1.2f,
                AssetLoader.noMusicButton.getRegionHeight() / 1.2f, AssetLoader.noMusicButton,
                AssetLoader.musicButton, Color.WHITE);

        soundButton = new SoundButton(this, gameWidth / 2 + 20, gameHeight - 135 - 30,
                AssetLoader.soundButton.getRegionWidth() / 1.2f,
                AssetLoader.soundButton.getRegionHeight() / 1.2f, AssetLoader.noSoundButton,
                AssetLoader.soundButton, Color.WHITE);

        available = Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer);
        checkIfMusicWasPlaying();

        for (int i = 0; i < numOfStars; i++) {
            stars.add(new Star(this));
        }

        newBest = new GameObject(this, gameWidth / 2 + 320, gameHeight / 2 + 120, 150, 150,
                AssetLoader.newBest, FlatColors.WHITE, GameObject.Shape.CIRCLE);
        newBest.scale(1.3f, 1, .4f, .5f);
        newBest.fadeIn(.4f, .5f);
        newBest.sprite.setRotation(-10);

        if (isGameOver()) {
            Value timer = new Value();
            Tween.to(timer, -1, 1f).target(1).setCallbackTriggers(TweenCallback.COMPLETE)
                    .setCallback(new TweenCallback() {
                        @Override
                        public void onEvent(int type, BaseTween<?> source) {
                            if (!AssetLoader.getAds() &&
                                    Math.random() < Configuration.AD_FREQUENCY) {
                                actionResolver.showOrLoadInterstital();
                            }
                        }
                    }).start(top.getManager());
        }

    }


    @Override
    public void update(float delta) {
        this.worldB.step(1f / 60f, 6, 2);
        top.update(delta);
        background.update(delta);
        for (int i = 0; i < menuButtons.size(); i++) {
            menuButtons.get(i).update(delta);
        }
        if (state == STATE.GAMEOVER && score == AssetLoader.getHighScore())
            for (int i = 0; i < stars.size(); i++) {
                stars.get(i).update(delta);
            }
        title.update(delta);
        bestText.update(delta);
        musicButton.update(delta);
        soundButton.update(delta);
        gamesText.update(delta);
        centerCircle.update(delta);
        newBest.update(delta);
        if (available && Settings.ACCELEROMETER_MENU) {
            worldB.setGravity(
                    new Vector2(-Gdx.input.getAccelerometerX() * Gdx.input.getAccelerometerZ(),
                            -Gdx.input.getAccelerometerY() * Gdx.input.getAccelerometerZ()));
            //centerCircle.spriteC.setRotation(worldB.getGravity().lerp(worldB.getGravity(),0.6f).angle());
        }


    }

    @Override
    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer) {
        background.render(batch, shapeRenderer);
        centerCircle.render(batch, shapeRenderer);
        for (int i = 0; i < menuButtons.size(); i++) {
            menuButtons.get(i).render(batch, shapeRenderer);
        }
        if (isGameOver() && score == AssetLoader.getHighScore())
            for (int i = 0; i < stars.size(); i++) {
                stars.get(i).render(batch, shapeRenderer);
            }
        musicButton.draw(batch);
        soundButton.draw(batch);
        bestText.render(batch, shapeRenderer, GameRenderer.fontShader);
        title.render(batch, shapeRenderer);
        gamesText.render(batch, shapeRenderer, GameRenderer.fontShader);
        if (isGameOver() && score == AssetLoader.getHighScore())
            newBest.render(batch, shapeRenderer);
        top.render(batch, shapeRenderer);
    }

    private void checkIfMusicWasPlaying() {
        if (AssetLoader.getVolume()) {
            AssetLoader.music.setLooping(true);
            AssetLoader.music.play();
            AssetLoader.music.setVolume(Configuration.MUSIC_VOLUME);
            AssetLoader.setVolume(true);
        } else AssetLoader.music.pause();

        if (AssetLoader.music.isPlaying()) musicButton.setIsPressed(false);
        else musicButton.setIsPressed(true);

        if (AssetLoader.getSounds()) soundButton.setIsPressed(false);
        else soundButton.setIsPressed(true);
    }

    public World getWorldB() {
        return worldB;
    }

    public void goToGameScreen() {
        top.fadeIn(.5f, 0f);
        Value timer = new Value();
        Tween.to(timer, -1, .5f).target(0).setCallback(new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                MenuScreen.game
                        .setScreen(new GameScreen(MenuScreen.game, actionResolver));
            }
        }).setCallbackTriggers(TweenCallback.COMPLETE).start(centerCircle.getManager());
    }

    public boolean isGameOver() {
        return state == STATE.GAMEOVER;
    }

}
