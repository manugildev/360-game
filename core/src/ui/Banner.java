package ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import configuration.Configuration;
import gameobjects.GameObject;
import gameworld.GameRenderer;
import gameworld.GameWorld;
import helpers.AssetLoader;
import helpers.FlatColors;
import tweens.SpriteAccessor;
import tweens.Value;

public class Banner extends GameObject {

    public Text text;

    public Banner(GameWorld world, float x, float y, float width, float height,
                  TextureRegion texture,
                  Color color, Shape shape) {
        super(world, x, y, width, height, texture, color, shape);
        text = new Text(world, 0, y + ((height - 100) / 2), width, 100, AssetLoader.square,
                Color.WHITE, Configuration.PAUSE_TEXT, AssetLoader.font, FlatColors.WHITE, 15,
                Align.center);
        sprite.setAlpha(0.8f);
    }

    public void reset() {
        getManager().killAll();
        text = new Text(world, 0, rectangle.y + ((rectangle.height - 100) / 2), rectangle.width,
                100, AssetLoader.square, Color.WHITE, Configuration.PAUSE_TEXT, AssetLoader.font,
                FlatColors.WHITE,
                15, Align.center);
        sprite.setAlpha(0.8f);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        text.update(delta);
    }

    @Override
    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer) {
        super.render(batch, shapeRenderer);
        text.render(batch, shapeRenderer, GameRenderer.fontShader);
    }

    public void start() {
        sprite.setScale(1, 0);
        Tween.to(sprite, SpriteAccessor.SCALEY, .5f).target(1).start(getManager());
        fadeInFromTo(0, .8f, .5f, 0f);
        text.effectX(-world.gameWidth, 0, .5f, .2f);
    }

    public void startAndFinish() {
        sprite.setScale(1, 0);
        Tween.to(sprite, SpriteAccessor.SCALEY, .5f).target(1).start(getManager());
        fadeInFromTo(0, .8f, .5f, 0f);
        text.effectX(-world.gameWidth, 0, .5f, .2f);
        finish(1.5f);
    }

    public void finish(float delay) {
        Value timer = new Value();
        Tween.to(timer, -1, delay).target(1).setCallbackTriggers(TweenCallback.COMPLETE)
                .setCallback(new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        Tween.to(sprite, SpriteAccessor.SCALEY, .5f).target(0)
                                .delay(0.2f).start(getManager());
                        text.effectX(0, world.gameWidth, .4f, 0f);
                        fadeOutFrom(.8f, .5f, .2f);
                    }
                }).start(getManager());
    }

    public void setText(String str) {
        text.setText(str);
    }
}
