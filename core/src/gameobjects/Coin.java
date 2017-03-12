package gameobjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import gameworld.GameWorld;
import helpers.AssetLoader;
import tweens.Value;

/**
 * Created by ManuGil on 19/06/15.
 */
public class Coin extends GameObject {
    private Value angle = new Value();
    private float ang;
    private float angleInc;
    public boolean isScored = false;
    private float distance;

    public Coin(GameWorld world, float x, float y, float width, float height,
                TextureRegion texture,
                Color color, Shape shape) {
        super(world, x, y, width, height, texture, color, shape);
        distance = distance(position, new Vector2(world.gameWidth / 2, world.gameHeight / 2));
        /*Tween.to(getSprite(), SpriteAccessor.SCALE, .3f).target(0.8f).repeatYoyo(100000, 0f)
                .ease(TweenEquations.easeInOutSine).start(getManager());*/
        getSprite().setRotation(45);
        angleInc = Math.random() < 0.5f ? -MathUtils.random(2, 4) : MathUtils.random(2, 4);
        instaReset();
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        angle.setValue(angle.getValue() + angleInc);
        getSprite().setRotation(angle.getValue());
        getSprite().setPosition(calculatePosition(world.angle + ang).x,
                calculatePosition(world.angle + ang).y);
        rectangle.setPosition(getSprite().getX(), getSprite().getY());
        collisions();

    }

    private void collisions() {
        if (!isScored && Intersector.overlaps(world.ball.circle, getRectangle())) {
            isScored = true;
            //AssetLoader.addCoinNumber(1);
            world.addScore(1);
            //AssetLoader.coinS.play();
            fadeOut(.1f, 0f);
            scaleZero(.1f, 0f);
            reset();
            world.ball.coinEffect();
            if(AssetLoader.getSounds()) AssetLoader.coinS.play();
        }
    }

    private Vector2 calculatePosition(float ang) {
        float cx = world.centerCircle.sprite.getX() + (world.centerCircle.sprite.getWidth() / 2);
        float cy = world.centerCircle.sprite.getY() + (world.centerCircle.sprite.getHeight() / 2);
        return new Vector2((float) (cx + (distance) * Math.sin(Math.toRadians(-ang))),
                (float) (cy + (distance) * Math.cos(Math.toRadians(-ang))));
    }

    private float distance(Vector2 p1, Vector2 p2) {
        return (float) Math.sqrt(Math.pow((p2.x - p1.x), 2) + Math.pow((p2.y - p1.y), 2));
    }

    private void reset() {
        Value timer = new Value();
        //instaReset();
        Tween.to(timer, -1, 0f).target(1).delay((float) (.1f))
                .setCallbackTriggers(TweenCallback.COMPLETE)
                .setCallback(
                        new TweenCallback() {
                            @Override
                            public void onEvent(int type, BaseTween<?> source) {
                                instaReset();
                            }
                        }).start(getManager());

    }

    private void instaReset() {
        position.set(MathUtils.random(300, world.gameWidth - 300), MathUtils
                .random(world.gameHeight / 2 - 300,
                        world.gameHeight / 2 + 300));
        distance = distance(position,
                new Vector2(world.gameWidth / 2, world.gameHeight / 2));
        fadeIn(.1f, 0f);
        sprite.setScale(0);
        scale(0,1, .1f, 0.01f);
        ang = MathUtils.random(0, 360);
        isScored = false;
    }

}
