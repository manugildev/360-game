package gameobjects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;
import gameworld.GameWorld;
import helpers.AssetLoader;
import helpers.FlatColors;
import tweens.SpriteAccessor;

/**
 * Created by ManuGil on 09/02/15.
 */
public class Star {
    public Vector2 position, velocity, acceleration;
    private Sprite sprite;

    private GameWorld world;
    private int type;
    private float angle, angleInc;
    private TweenManager manager;

    public Star(GameWorld world) {
        FlatColors.organizeColors();
        this.world = world;
        sprite = new Sprite(AssetLoader.square);
        sprite.setRotation((float) (Math.random() * 360));
        float size = (float) Math.random() * 20 + 10;
        sprite.setSize(size, size);
        sprite.setPosition((float) Math.random() * world.gameWidth,
                (float) (Math.random() * (world.gameHeight)) * 1.5f);
        position = new Vector2(sprite.getX(), sprite.getY());
        velocity = new Vector2(MathUtils.random(-100, 100), -MathUtils.random(50, 100));
        acceleration = new Vector2(MathUtils.random(-40, 40), -100);
        type = Math.random() < 0.5f ? 1 : 0;
        sprite.setColor(FlatColors.colors.get(MathUtils.random(0, FlatColors.colors.size-1)));
        //sprite.setColor(FlatColors.RED);
        manager = new TweenManager();
        angleInc = MathUtils.random(-100, 100);

    }

    public void update(float delta) {
        angle += angleInc * delta;
        manager.update(delta);
        if (sprite.getY() < world.gameHeight + 100)
            velocity.add(acceleration.cpy().scl(delta));
        position.add(velocity.cpy().scl(delta));
        sprite.setPosition(position.x, position.y);
        if (sprite.getX() < -100 || sprite.getX() > world.gameWidth + 100) {
            resetPosition();
        }
        if (sprite.getY() < -sprite.getHeight() || sprite.getY() > world.gameHeight * 2) {
            resetPosition();
        }

        sprite.setRotation(angle);

        sprite.setOriginCenter();
        // }
    }

    private void resetPosition() {
        velocity.set(0, 0);
        position.set((float) Math.random() * world.gameWidth, MathUtils
                .random(world.gameHeight + sprite.getHeight() + 5, world.gameHeight + 100));
    }

    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer) {
        /*if (position.x < world.getHero()
                .getPoint().x + (world.gameWidth / 2) + 70 && position.x > world.getHero()
                .getPoint().x - (world.gameWidth / 2) - 70 && position.y > world.getHero()
                .getPoint().y - (world.gameHeight / 2) - 70 && position.y < world.getHero()
                .getPoint().y + (world.gameHeight / 2) + 70) {*/
        //sprite.setColor(world.parseColor("#FFFFFF", 0.5f));
        sprite.draw(batch);
        // }
    }

    public void scale(float from, float duration, float delay) {
        sprite.setScale(from);
        Tween.to(sprite, SpriteAccessor.SCALE, duration).target(1).delay(delay)
                .ease(TweenEquations.easeInOutSine).start(manager);
    }
}
