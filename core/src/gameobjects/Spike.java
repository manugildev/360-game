package gameobjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import gameworld.GameWorld;
import tweens.SpriteAccessor;

public class Spike extends GameObject {

    public float angle;

    public Spike(GameWorld world, float x, float y, float width, float height,
                 TextureRegion texture, Color color, Shape shape) {
        super(world, x - width / 2, y - height / 2, width, height, texture, color, shape);
        sprite.setScale(1f, 1f);
        scaleY(1, 0, .3f, 1f);
    }

    @Override
    public void update(float delta) {
        getManager().update(delta);
        velocity.add(acceleration.cpy().scl(delta));
        position.add(velocity.cpy().scl(delta));

        if (shape == Shape.RECTANGLE) rectangle.setPosition(position);
        else if (shape == Shape.CIRCLE)
            circle.setPosition(position.x + circle.radius, position.y + circle.radius);
        sprite.setOriginCenter();
        sprite.setPosition(position.x, position.y);
        sprite.setPosition(calculatePosition(angle + world.angle).x - sprite.getWidth() / 2,
                calculatePosition(angle + world.angle).y - sprite.getHeight() / 2);
        sprite.setRotation(angle + world.angle-180);
        circle.setPosition(sprite.getX() + circle.radius, sprite.getY() + circle.radius);
    }

    public void scaleY(float from, float to, float duration, float delay) {
        sprite.setScale(sprite.getScaleX(), from);
        Tween.to(getSprite(), SpriteAccessor.SCALEY, duration).target(to).delay(delay)
                .ease(TweenEquations.easeInOutSine).start(getManager());
    }

    public void setAngle(float a) {
        angle = a;
    }

    private Vector2 calculatePosition(float angle) {
        float cx = world.centerCircle.sprite.getX() + (world.centerCircle.sprite.getWidth() / 2);
        float cy = world.centerCircle.sprite.getY() + (world.centerCircle.sprite.getHeight() / 2);
        return new Vector2((float) (cx + (world.centerCircle.getCircle().radius)
                * Math.sin(Math.toRadians(-angle))),
                (float) (cy + (world.centerCircle.getCircle().radius) * Math
                        .cos(Math.toRadians(-angle))));
    }
}
