package gameobjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import configuration.Settings;
import gameworld.GameWorld;
import helpers.AssetLoader;

public class CenterCircle extends GameObject {
    private int numOfSquares = 36;
    public Sprite spriteC;

    public CenterCircle(GameWorld world, float x, float y, float width, float height,
                        TextureRegion texture, Color color, Shape shape) {
        super(world, x, y, width, height, texture, color, shape);
        spriteC = new Sprite(AssetLoader.top);
        spriteC.setPosition(-AssetLoader.top.getWidth() / 2 + world.gameWidth / 2,
                world.gameHeight / 2 - (AssetLoader.top.getHeight() / 2));
        spriteC.setSize(AssetLoader.top.getWidth(), AssetLoader.top.getHeight());
        createCircle();
    }


    @Override
    public void update(float delta) {
        super.update(delta);
        spriteC.setRotation(world.angle);
    }

    @Override
    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer) {

            spriteC.draw(batch);
    }

    private void createCircle() {
        for (int i = 0; i < numOfSquares; i++) {
            Vector2 vec = calculatePosition((360 / numOfSquares) * i);
            createSquare(vec.x, vec.y, 85, 10, (float) (Math.toRadians(360 / numOfSquares) * i));
        }
    }

    public void createSquare(float x, float y, int width, int height, float angle) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x / Settings.PTM, y / Settings.PTM);
        Body body = world.getWorldB().createBody(bodyDef);
        body.setTransform(body.getPosition(), angle);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2 / Settings.PTM, height / 2 / Settings.PTM);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.friction = 1f;
        fixtureDef.filter.categoryBits = Settings.CATEGORY_CENTER_CIRCLE;
        fixtureDef.filter.maskBits = Settings.MASK_CENTER_CIRCLE;
        body.createFixture(fixtureDef).setUserData(this);
        shape.dispose();
    }

    private Vector2 calculatePosition(float angle) {
        float cx = sprite.getX() + (sprite.getWidth() / 2);
        float cy = sprite.getY() + (sprite.getHeight() / 2);
        return new Vector2(
                (float) (cx + (getCircle().radius + 18) * Math.sin(Math.toRadians(-angle))),
                (float) (cy + (getCircle().radius + 18) * Math.cos(Math.toRadians(-angle))));
    }
}
