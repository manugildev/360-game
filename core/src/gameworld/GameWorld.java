package gameworld;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Align;

import MainGame.ActionResolver;
import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import configuration.Configuration;
import configuration.Settings;
import gameobjects.Ball;
import gameobjects.CenterCircle;
import gameobjects.Coin;
import gameobjects.GameObject;
import gameobjects.SpikeManager;
import helpers.AssetLoader;
import helpers.ColorManager;
import helpers.FlatColors;
import menuworld.MenuWorld;
import screens.LoadingScreen;
import screens.MenuScreen;
import tweens.Value;
import ui.Banner;
import ui.Text;

public class GameWorld {
    public float gameWidth, gameHeight;
    public int score = 0;
    public ActionResolver actionResolver;
    public Game game;

    //GAMEOBJECTS
    public CenterCircle centerCircle;
    public Ball ball;
    public SpikeManager spikeManager;
    public GameObject background, colorSquare, top, pauseButton;
    public ColorManager colorManager;
    public Coin coin;
    public Text scoreText, bestText;

    //BOX2D
    public World worldB;
    Body body;
    Box2DDebugRenderer debugRenderer;
    Matrix4 debugMatrix;
    Sprite sprite;

    //GENERAL VARIABLES
    public float angle = 0;
    public float angleInc;

    public enum GameState {PAUSE, RUNNING}

    public GameState gameState;
    public Banner banner;


    public GameWorld(Game game, ActionResolver actionResolver, float gameWidth, float gameHeight) {
        this.game = game;
        this.gameWidth = gameWidth;
        this.gameHeight = gameHeight;
        this.actionResolver = actionResolver;
    }

    public void start() {
        worldB = new World(new Vector2(0, Settings.WORLD_GRAVITY), true);
        debugRenderer = new Box2DDebugRenderer();
        top = new GameObject(this, 0, 0, gameWidth, gameHeight, AssetLoader.square,
                FlatColors.WHITE, GameObject.Shape.RECTANGLE);
        top.fadeOut(.5f, 0f);

        background = new GameObject(this, 0, 0, gameWidth, gameHeight, AssetLoader.square,
                FlatColors.parseColor(Settings.BACKGROUND_COLOR), GameObject.Shape.RECTANGLE);
        colorSquare = new GameObject(this, 0, gameHeight / 2 - (gameWidth - 150) / 2 - 100,
                gameWidth, gameWidth - 150 + 200, AssetLoader.square,
                FlatColors.parseColor(Settings.BACKGROUND_COLOR), GameObject.Shape.RECTANGLE);

        centerCircle = new CenterCircle(this, gameWidth / 2 - (gameWidth - 150) / 2,
                gameHeight / 2 - (gameWidth - 150) / 2, gameWidth - 150,
                gameWidth - 150, AssetLoader.dot, Color.WHITE, GameObject.Shape.CIRCLE);
        ball = new Ball(this, gameWidth / 2 - (Settings.BALL_SIZE / 2),
                gameHeight / 2 - (Settings.BALL_SIZE / 2), Settings.BALL_SIZE, Settings.BALL_SIZE,
                AssetLoader.ball, FlatColors.WHITE,
                GameObject.Shape.CIRCLE);
        coin = new Coin(this, gameWidth / 2 - Settings.COIN_SIZE / 2,
                gameHeight / 2 - Settings.COIN_SIZE / 2 + 200, Settings.COIN_SIZE,
                Settings.COIN_SIZE, AssetLoader.coin, FlatColors.BLACK, GameObject.Shape.RECTANGLE);
        scoreText = new Text(this, 0, gameHeight / 2 - 50, gameWidth, 100, AssetLoader.square,
                Color.WHITE, "0", AssetLoader.font23, FlatColors.DARK_BLACK, -5,
                Align.center);
        bestText = new Text(this, 35, gameHeight - 150, gameWidth, 100, AssetLoader.square,
                FlatColors.WHITE,
                Configuration.BEST_TEXT_GAME + AssetLoader.getHighScore(), AssetLoader.font12,
                FlatColors.DARK_BLACK, 0,
                Align.left);
        bestText.effectX(-gameWidth, bestText.getPosition().x, .8f, .2f);


        spikeManager = new SpikeManager(this);
        colorManager = new ColorManager();

        pauseButton = new GameObject(this,
                gameWidth - (AssetLoader.pauseButton.getRegionWidth() / 1.2f) - 40,
                gameHeight - (AssetLoader.pauseButton.getRegionWidth() / 1.2f) - 40,
                AssetLoader.pauseButton.getRegionWidth() / 1.2f,
                AssetLoader.pauseButton.getRegionHeight() / 1.2f, AssetLoader.pauseButton,
                Color.WHITE, GameObject.Shape.RECTANGLE);


        banner = new Banner(this, 0, gameHeight / 2 - (Settings.BANNER_HEIGHT / 2), gameWidth,
                Settings.BANNER_HEIGHT, AssetLoader.square, Color.BLACK,
                GameObject.Shape.RECTANGLE);
        banner.setText(Configuration.PAUSE_TEXT);

    }

    public void update(float delta) {
        worldB.step(1f / 60f, 6, 2);
        top.update(delta);
        background.update(delta);
        if (Settings.COLOR_CHANGING)
            colorSquare.setColor(colorManager.getColor());
        centerCircle.update(delta);

        if (!isPaused()) {
            angle += angleInc * delta;
            colorSquare.update(delta);
            spikeManager.update(delta);
            colorManager.update(delta);
            scoreText.update(delta);
            bestText.update(delta);
            coin.update(delta);
            ball.update(delta);
        }

        pauseButton.update(delta);
        banner.update(delta);

    }

    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer) {
        if (Configuration.DEBUG) {
            batch.end();
            debugMatrix = batch.getProjectionMatrix().cpy().scale(Settings.PTM, Settings.PTM, 0);
            batch.begin();
        }
        background.render(batch, shapeRenderer);
        colorSquare.render(batch, shapeRenderer);
        scoreText.render(batch, shapeRenderer, GameRenderer.fontShaderA);
        spikeManager.render(batch, shapeRenderer);
        coin.render(batch, shapeRenderer);
        ball.render(batch, shapeRenderer);
        centerCircle.render(batch, shapeRenderer);
        bestText.render(batch, shapeRenderer, GameRenderer.fontShader);
        pauseButton.render(batch, shapeRenderer);
        if (isPaused())
            banner.render(batch, shapeRenderer);
        top.render(batch, shapeRenderer);
        if (Configuration.DEBUG) debugRenderer.render(worldB, debugMatrix);
    }

    public void addScore(int i) {
        score += i;
        scoreText.setText(score + "");
        if (score >= AssetLoader.getHighScore())
            bestText.setText(Configuration.BEST_TEXT_GAME + score);
    }

    private void saveScoreLogic() {
        AssetLoader.addGamesPlayed();
        int gamesPlayed = AssetLoader.getGamesPlayed();
        actionResolver.submitScore(score);
        actionResolver.submitGamesPlayed(gamesPlayed);
        if (score > AssetLoader.getHighScore()) {
            AssetLoader.setHighScore(score);
        }
    }

    public void goToLoadingScreen() {
        game.setScreen(new LoadingScreen(game, actionResolver));
    }

    public void rotateLeft() {
        setAngleInc(-Settings.ROTATE_SPEED);
    }

    public void rotateRight() {
        setAngleInc(Settings.ROTATE_SPEED);
    }

    public void setAngleInc(float a) {
        angleInc = a;
    }

    public void finish() {
        ball.finish();
        goToGameScreen();
        saveScoreLogic();
    }

    public World getWorldB() {
        return worldB;
    }

    public void goToGameScreen() {
        top.fadeIn(1f, .3f);
        Value timer = new Value();
        Tween.to(timer, -1, 1.3f).target(0).setCallback(new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                MenuScreen.game
                        .setScreen(new MenuScreen(MenuScreen.game, actionResolver,
                                MenuWorld.STATE.GAMEOVER, score));
            }
        }).setCallbackTriggers(TweenCallback.COMPLETE).start(centerCircle.getManager());
    }

    public void setPauseMode() {
        if (isPaused()) {
            banner.finish(0f);
            Value timer = new Value();
            Tween.to(timer, -1, .7f).target(1).setCallbackTriggers(TweenCallback.COMPLETE)
                    .setCallback(new TweenCallback() {
                        @Override
                        public void onEvent(int type, BaseTween<?> source) {
                            gameState = GameState.RUNNING;
                        }
                    }).start(banner.getManager());
        } else {
            gameState = GameState.PAUSE;
            banner.reset();
            banner.start();
        }
    }


    public boolean isPaused() {
        return gameState == GameState.PAUSE;
    }
}
