package screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

import MainGame.ActionResolver;
import gameworld.GameRenderer;
import gameworld.GameWorld;
import helpers.AssetLoader;
import helpers.InputHandler;

public class GameScreen implements Screen {

    private static GameWorld world;
    private GameRenderer renderer;
    public float sW = Gdx.graphics.getWidth();
    public float sH = Gdx.graphics.getHeight();
    public float gameWidth = 1080;
    public float gameHeight = sH / (sW / gameWidth);

    public GameScreen(Game game, ActionResolver actionResolver) {
        world = new GameWorld(game, actionResolver, gameWidth, gameHeight);
        world.start();
        Gdx.input.setInputProcessor(new InputHandler(world, sW / gameWidth, sH / gameHeight));
        renderer = new GameRenderer(world);
        if (!AssetLoader.getAds()) actionResolver.viewAd(true);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        world.update(delta);
        renderer.render();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
        world.setPauseMode();
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
        world.setPauseMode();
    }

    @Override
    public void dispose() {
    }
}
