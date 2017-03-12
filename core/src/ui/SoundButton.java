package ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;
import gameworld.GameWorld;
import helpers.AssetLoader;
import tweens.SpriteAccessor;
import tweens.Value;
import tweens.ValueAccessor;

public class SoundButton {

    private float x, y, width, height;

    private TextureRegion buttonUp;
    private TextureRegion buttonDown;

    private Rectangle bounds;

    public boolean isPressed = false;

    private Value size = new Value();
    private TweenManager manager;
    private Value second = new Value();

    private Color color;
    public Sprite sprite;

    private GameWorld world;

    public SoundButton(GameWorld world, float x, float y, float width, float height,
                       TextureRegion buttonUp,
                       TextureRegion buttonDown, Color color) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.buttonUp = buttonUp;
        this.buttonDown = buttonDown;
        this.color = color;

        bounds = new Rectangle(x + (width / 2), y + (height / 2), width, height);
        sprite = new Sprite(buttonUp);
        sprite.setPosition(x, y);
        sprite.setSize(width, height);
        Tween.registerAccessor(Value.class, new ValueAccessor());
        manager = new TweenManager();

        if (AssetLoader.getSounds()) {
            isPressed = true;
        }


    }

    public boolean isClicked(int screenX, int screenY) {
        return bounds.contains(screenX, screenY);
    }

    public void draw(SpriteBatch batcher) {
        bounds.set(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
        batcher.setColor(color);
        if (isPressed) {
            sprite.setRegion(buttonDown);
        } else {
            sprite.setRegion(buttonUp);
        }
        sprite.draw(batcher);
    }

    public boolean isTouchDown(int screenX, int screenY) {
        if (bounds.contains(screenX, world.gameHeight - screenY)) {
            if(AssetLoader.getSounds()) AssetLoader.click.play();
            if (isPressed) {
                setIsPressed(false);
                AssetLoader.setSounds(true);
            } else {
                setIsPressed(true);
                AssetLoader.setSounds(false);
            }

        }

        return false;
    }

    public boolean isTouchUp(int screenX, int screenY) {
        if (bounds.contains(screenX, screenY) && isPressed) {
            isPressed = false;
            return true;
        }
        isPressed = false;
        return false;
    }

    public void start() {
        size.setValue(0);
        Tween.to(size, -1, .5f).target(1).repeatYoyo(0, 0)
                .ease(TweenEquations.easeInOutSine).start(manager);

    }

    public void end() {
        size.setValue(1);
        Tween.to(size, -1, .5f).target(0).repeatYoyo(0, 0)
                .ease(TweenEquations.easeInOutSine).start(manager);

    }

    public void update(float delta) {
        manager.update(delta);
        bounds = new Rectangle(x - (bounds.width / 2), y - (bounds.height / 2),
                width * size.getValue(), height * size.getValue());

    }


    public Rectangle getBounds() {
        return bounds;
    }

    public void setIsPressed(boolean bol) {
        isPressed = bol;
    }

    public void fadeIn(float to, float duration, float delay) {
        sprite.setAlpha(0);
        Tween.to(sprite, SpriteAccessor.ALPHA, duration).target(to).delay(delay)
                .ease(TweenEquations.easeInOutSine).start(manager);
    }
}