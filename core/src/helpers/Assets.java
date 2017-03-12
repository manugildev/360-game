package helpers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class Assets {

    public static AssetManager manager;

    public static void load() {
        manager = new AssetManager();
        Texture.setAssetManager(manager);

        //Textures
        manager.load("ball.png", Texture.class);
        manager.load("coin.png", Texture.class);
        manager.load("dot.png", Texture.class);
        manager.load("spike.png", Texture.class);
        manager.load("square.png", Texture.class);
        manager.load("title.png", Texture.class);
        manager.load("top.png", Texture.class);
        manager.load("newbest.png", Texture.class);

        //BUTTONS
        manager.load("play_button.png", Texture.class);
        manager.load("buttons.png", Texture.class);
        manager.load("musicbuttons.png", Texture.class);
        manager.load("soundbuttons.png", Texture.class);
        manager.load("pause_button.png", Texture.class);

        //Font
        manager.load("misc/font.fnt", BitmapFont.class);

        //Sounds
        manager.load("sounds/click.wav", Sound.class);
        manager.load("sounds/hit.wav", Sound.class);
        manager.load("sounds/finish.wav", Sound.class);
        manager.load("sounds/coin.wav", Sound.class);

        //Music
        manager.load("sounds/music.mp3", Music.class);

    }

    public static void dispose() {
        manager.dispose();
    }
}
