package screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

import MainGame.ActionResolver;
import gameworld.GameRenderer;
import menuworld.InputHandlerMenu;
import menuworld.MenuWorld;

public class MenuScreen implements Screen {

    private static MenuWorld world;
    public static Game game;
    private GameRenderer renderer;
    public float sW = Gdx.graphics.getWidth();
    public float sH = Gdx.graphics.getHeight();
    public float gameWidth = 1080;
    public float gameHeight = sH / (sW / gameWidth);

    public MenuScreen(Game game, ActionResolver actionresolver, MenuWorld.STATE state, int score) {
        this.game = game;
        world = new MenuWorld(game, actionresolver, gameWidth, gameHeight, state, score);
        Gdx.input.setInputProcessor(new InputHandlerMenu(world, sW / gameWidth, sH
                / gameHeight));
        renderer = new GameRenderer(world);
    }

    @Override
    public void render(float delta) {
        world.update(delta);
        renderer.render();
    }

    @Override
    public void show() {
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
    }

}
