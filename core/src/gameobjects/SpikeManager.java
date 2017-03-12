package gameobjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

import gameworld.GameWorld;
import helpers.AssetLoader;
import helpers.FlatColors;

public class SpikeManager {
    public GameWorld world;
    public int numOfSpikes = 36;
    public ArrayList<Spike> spikes = new ArrayList<Spike>();
    public boolean hasCollided = false;

    public SpikeManager(GameWorld world) {
        this.world = world;
        createSpikes();
    }

    public void update(float delta) {
        for (int i = 0; i < numOfSpikes; i++) {
            spikes.get(i).update(delta);
        }
        checkCollisions();
    }

    private void checkCollisions() {
        if (!hasCollided) {
            for (int i = 0; i < numOfSpikes; i++) {
                if (spikes.get(i).sprite.getScaleY() != 0) {
                    if (Intersector.overlaps(spikes.get(i).circle, world.ball.circle)) {
                        //Gdx.app.log("HIT","ball hit spike");
                        hasCollided = true;
                        world.finish();
                    }
                }
            }
        }
    }

    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer) {
        for (int i = 0; i < numOfSpikes; i++) {
            spikes.get(i).render(batch, shapeRenderer);
        }
    }

    private void createSpikes() {
        for (int i = 0; i < numOfSpikes; i++) {
            Vector2 vec = calculatePosition((360 / numOfSpikes) * i);
            spikes.add(new Spike(world, vec.x, vec.y, 85, 85, AssetLoader.spike, FlatColors.WHITE,
                    GameObject.Shape.CIRCLE));
            spikes.get(i).sprite.setRotation(360 / numOfSpikes * (i));
            spikes.get(i).setAngle(360 / numOfSpikes * i);
        }
    }

    private Vector2 calculatePosition(float angle) {
        float cx = world.centerCircle.sprite.getX() + (world.centerCircle.sprite.getWidth() / 2);
        float cy = world.centerCircle.sprite.getY() + (world.centerCircle.sprite.getHeight() / 2);
        return new Vector2((float) (cx + (world.centerCircle.getCircle().radius)
                * Math.sin(Math.toRadians(-angle))),
                (float) (cy + (world.centerCircle.getCircle().radius) * Math
                        .cos(Math.toRadians(-angle))));
    }

    public void hitWall(int h) {
        ArrayList<Integer> nums = new ArrayList<Integer>();
        nums.clear();
        int numOfS = h;
        for (int i = 0; i < numOfS; i++) {
            int n;
            do {
                n = MathUtils.random(0, numOfSpikes - 1);
            } while (isOnNums(n, nums));
            nums.add(n);
        }
        for (int i = 0; i < spikes.size(); i++) {
            spikes.get(i).scaleY(spikes.get(i).sprite.getScaleY(), 0, .2f, 0f);
        }
        for (int i = 0; i < numOfS; i++) {
            spikes.get(nums.get(i)).scaleY(0, 1, .2f, .21f);
        }

    }

    public boolean isOnNums(int num, ArrayList<Integer> nums) {
        for (int j = 0; j < nums.size(); j++) {
            if (nums.get(j) == num) {
                return true;
            }
        }
        return false;
    }
}
