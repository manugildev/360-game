package gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import configuration.Settings;
import gameworld.GameWorld;
import helpers.AssetLoader;

public class Ball extends GameObject {

    private Body body;
    private float velocityValue = 6;
    public boolean firstTouch = false;
    private ParticleEffect particleEffect, effect;

    public Ball(GameWorld world, float x, float y, float width, float height,
                TextureRegion texture,
                Color color, Shape shape) {
        super(world, x, y, width, height, texture, color, shape);
        circle.setRadius(width/2 - 10);
        createBox2dBall();
        world.worldB.setContactListener(new MyContactListener(world));
        setAcceleration(0, -100);

        //PARTICLES
        particleEffect = new ParticleEffect();
        particleEffect.load(Gdx.files.internal("misc/hit.p"), Gdx.files.internal(""));
        particleEffect.setPosition(-100, -100);
        particleEffect.start();

        effect = new ParticleEffect();
        effect.load(Gdx.files.internal("misc/tail.p"), Gdx.files.internal(""));
        effect.setPosition(-100, -100);
    }

    private void createBox2dBall() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set((sprite.getX() + sprite.getWidth() / 2) /
                Settings.PTM, (sprite.getY() + sprite.getHeight() / 2) / Settings.PTM);
        body = world.worldB.createBody(bodyDef);

        CircleShape c = new CircleShape();
        c.setPosition(new Vector2(sprite.getWidth() / 2 / Settings.PTM,
                sprite.getHeight() / 2 / Settings.PTM));
        c.setRadius(circle.radius / Settings.PTM);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = c;
        fixtureDef.filter.categoryBits = Settings.CATEGORY_BALL;
        fixtureDef.filter.maskBits = Settings.MASK_BALL;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 1f;
        body.createFixture(fixtureDef).setUserData(this);
        c.dispose();
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        sprite.setRotation((float) Math.toDegrees(body.getAngle()));
        sprite.setOrigin(0, 0);
        circle.setPosition(sprite.getX()+sprite.getWidth()/2,sprite.getY()+sprite.getHeight()/2);
        body.setTransform(sprite.getX() / Settings.PTM, sprite.getY() / Settings.PTM, 0);

        //PARTICLES
        particleEffect.update(delta);
        if (Settings.BALL_TAIL) {
            effect.update(delta);
            effect.setPosition(circle.x, circle.y);
        }
        if (velocity.len() < Settings.MAX_BALL_SPEED + 100) {
            velocity.add(acceleration.cpy().scl(delta));
        }

    }

    @Override
    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer) {
        particleEffect.draw(batch);
        if (velocity.len() > 0)
            effect.draw(batch);
        super.render(batch, shapeRenderer);

    }

    private void speedLimit() {
        if (body.getLinearVelocity().x > Settings.MAX_BALL_SPEED) {
            body.setLinearVelocity(Settings.MAX_BALL_SPEED, body.getLinearVelocity().y);
        }
        if (body.getLinearVelocity().x < -Settings.MAX_BALL_SPEED) {
            body.setLinearVelocity(-Settings.MAX_BALL_SPEED, body.getLinearVelocity().y);
        }
        if (body.getLinearVelocity().y > Settings.MAX_BALL_SPEED) {
            body.setLinearVelocity(body.getLinearVelocity().x, Settings.MAX_BALL_SPEED);
        }
        if (body.getLinearVelocity().y < Settings.MAX_BALL_SPEED) {
            body.setLinearVelocity(body.getLinearVelocity().x, -Settings.MAX_BALL_SPEED);
        }
    }

    public void hit() {
        if (particleEffect.isComplete()) {
            particleEffect.setPosition(circle.x, circle.y);
            particleEffect.start();
        }

        if(AssetLoader.getSounds()) AssetLoader.hit.play();

        setAcceleration(0, 0);
        setVelocity(new Vector2(world.gameWidth / 2 - circle.x,
                world.gameHeight / 2 - circle.y));
        setVelocity(getVelocity().nor().x * Settings.MAX_BALL_SPEED,
                getVelocity().nor().y * Settings.MAX_BALL_SPEED);
        setVelocity(getVelocity().rotate(MathUtils
                .random(-Settings.MAX_BALL_DIRECTION_VAR, Settings.MAX_BALL_DIRECTION_VAR)));
        if (firstTouch) {
            if (world.score > 10) world.spikeManager.hitWall(Settings.MAX_NUM_SPIKES);
            else world.spikeManager.hitWall(5 + world.score);
        }

        /*int rand = (int) Math.random() * 90 + 5;
        if (Math.random() < 0.5f)
            setVelocity(getVelocity().cpy().rotate((float) (rand + Math.random() * 50)));*/
    }


    public void coinEffect() {
        scale(1, 1.1f, .1f, 0f);
        scale(1.1f, 1f, .1f, .11f);
    }

    public void finish() {
        setAcceleration(new Vector2());
        setVelocity(new Vector2());
        scaleZero(.3f, 0f);

        if (AssetLoader.getSounds()) AssetLoader.finish.play();

        particleEffect.load(Gdx.files.internal("misc/finish.p"), Gdx.files.internal(""));
        particleEffect.setPosition(circle.x, circle.y);
        particleEffect.start();
    }
}

