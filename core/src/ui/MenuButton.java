package ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import configuration.Settings;
import gameobjects.GameObject;
import helpers.AssetLoader;
import menuworld.MenuWorld;

/**
 * Created by ManuGil on 14/03/15.
 */
public class MenuButton extends GameObject {

    public Body body;
    public MenuWorld w;

    public MenuButton(MenuWorld world, float x, float y, float width, float height,
                      TextureRegion texture, Color color, Shape shape) {
        super(world, x - width / 2, y - height / 2, width, height, texture, color, shape);
        this.w = world;
        createBox2DCircle();
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        sprite.setPosition((body.getPosition().x * Settings.PTM),
                (body.getPosition().y * Settings.PTM));
        circle.setPosition(body.getWorldPoint(body.getLocalCenter()).x * Settings.PTM,
                body.getWorldPoint(body.getLocalCenter()).y * Settings.PTM);
        sprite.setRotation((float) Math.toDegrees(body.getAngle()));
        sprite.setOrigin(0, 0);
        if (isPressed) {
            getSprite().setAlpha(0.8f);
        } else {
            getSprite().setAlpha(1f);
        }
    }

    @Override
    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer) {
        super.render(batch, shapeRenderer);

    }

    private void createBox2DCircle() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set((sprite.getX()) /
                Settings.PTM, (sprite.getY()) / Settings.PTM);
        body = w.worldB.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setPosition(new Vector2(sprite.getWidth() / 2 / Settings.PTM,
                sprite.getHeight() / 2 / Settings.PTM));
        shape.setRadius(circle.radius / Settings.PTM);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.filter.categoryBits = Settings.CATEGORY_BALL;
        fixtureDef.filter.maskBits = Settings.MASK_BALL;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0.7f;
        fixtureDef.restitution = 0.5f;
        body.createFixture(fixtureDef);
        shape.dispose();
    }

    @Override
    public boolean isTouchDown(int screenX, int screenY) {
        if (circle.contains(screenX,world.gameHeight- screenY)) {
            isPressed = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean isTouchUp(int screenX, int screenY) {
        if (circle.contains(screenX,world.gameHeight- screenY) && isPressed) {

            if(AssetLoader.getSounds()) AssetLoader.click.play();
            isPressed = false;
            return true;
        }
        isPressed = false;
        return false;
    }
}