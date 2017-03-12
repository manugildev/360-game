package helpers;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

import configuration.Configuration;
import gameworld.GameWorld;

public class InputHandler implements InputProcessor {

    private GameWorld world;
    private float scaleFactorX;
    private float scaleFactorY;
    int activeTouch = 0;

    public InputHandler(GameWorld world, float scaleFactorX, float scaleFactorY) {
        this.scaleFactorX = scaleFactorX;
        this.scaleFactorY = scaleFactorY;
        this.world = world;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.R) {
        } else if (keycode == Input.Keys.F) {
        } else if (keycode == Input.Keys.D) {
            if (Configuration.DEBUG) Configuration.DEBUG = false;
            else Configuration.DEBUG = true;
        } else if (keycode == Input.Keys.M) {
        } else if (keycode == Input.Keys.S) {
        } else if (keycode == Input.Keys.L) {
            world.goToLoadingScreen();
        } else if (keycode == Input.Keys.RIGHT) {
            world.rotateRight();
        } else if (keycode == Input.Keys.LEFT) {
            world.rotateLeft();
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.RIGHT) {
            world.setAngleInc(0);
        } else if (keycode == Input.Keys.LEFT) {
            world.setAngleInc(0);
        }
        return false;
    }


    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        screenX = scaleX(screenX);
        screenY = scaleY(screenY);
        activeTouch++;
        if (world.isPaused()) {
            world.setPauseMode();
        } else {
            if (world.pauseButton.isTouchDown(screenX, screenY)) {
                world.setPauseMode();
            } else {
                if (screenX >= world.gameWidth / 2) {
                    world.setAngleInc(0);
                    world.rotateRight();
                } else {
                    world.setAngleInc(0);
                    world.rotateLeft();
                }
            }
        }

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        screenX = scaleX(screenX);
        screenY = scaleY(screenY);
        activeTouch--;
        if (activeTouch == 0) {
            world.setAngleInc(0);
        } else {
            if (screenX >= world.gameWidth / 2) {
                world.setAngleInc(0);
                world.rotateLeft();
            } else {
                world.setAngleInc(0);
                world.rotateRight();
            }
        }
        return false;
    }


    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        screenX = scaleX(screenX);
        screenY = scaleY(screenY);

        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    private int scaleX(int screenX) {
        return (int) (screenX / scaleFactorX);
    }

    private int scaleY(int screenY) {
        return (int) (world.gameHeight - screenY / scaleFactorY);
    }

}
