package com.mygdx.game;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

public class GameScreen implements Screen {
    final Drop game;

    Texture rockImage;
    Texture crateImage;
    Sound rockSound;
    Music familyFriendlyMusic;
    OrthographicCamera camera;
    Rectangle bucket;
    Array<Rock> rocks;
    long lastDropTime;
    int dropsGathered;
    Stage stage;
    Crate crate;
    public GameScreen(final Drop game) {
        this.game = game;
        stage = new Stage();
        // load the images for the droplet and the bucket, 64x64 pixels each
        rockImage = new Texture(Gdx.files.internal("rock.png"));
        crateImage = new Texture(Gdx.files.internal("crate.png"));
        // load the drop sound effect and the rain background "music"
        rockSound = Gdx.audio.newSound(Gdx.files.internal("rock.mp3"));
        Rock.setTexture(rockImage);
        Rock.setSound(rockSound);
        crate = new Crate();
        crate.setTexture(crateImage);
        crate.pos = new Vector2(800 / 2 - 64 / 2, 20);
        crate.w = 64;
        crate.h = 64;
        stage.addActor(crate);

        familyFriendlyMusic = Gdx.audio.newMusic(Gdx.files.internal("familyfriendly.mp3"));
        familyFriendlyMusic.setLooping(true);


        // create the camera and the SpriteBatch
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);


        // create a Rectangle to logically represent the bucket
        bucket = new Rectangle();
        bucket.x = 800 / 2 - 64 / 2; // center the bucket horizontally
        bucket.y = 20; // bottom left corner of the bucket is 20 pixels above
        // the bottom screen edge
        bucket.width = 64;
        bucket.height = 64;

        // create the raindrops array and spawn the first raindrop
        rocks = new Array<Rock>();
        spawnRaindrop();

    }

    private void spawnRaindrop() {

        Rock rockIn = new Rock(new Vector2(MathUtils.random(0, 800 - 64),480), 64,64);
        rocks.add(rockIn);
        stage.addActor(rockIn);

        //raindrops.add(rockIn);
        lastDropTime = TimeUtils.nanoTime();
    }

    @Override
    public void render(float delta) {
        // clear the screen with a dark blue color. The
        // arguments to clear are the red, green
        // blue and alpha component in the range [0,1]
        // of the color to be used to clear the screen.
        ScreenUtils.clear(0, 0, 0.2f, 1);

        // tell the camera to update its matrices.
        camera.update();

        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.
        game.batch.setProjectionMatrix(camera.combined);

        // begin a new batch and draw the bucket and
        // all drops
        game.batch.begin();
        game.font.draw(game.batch, "Drops Collected: " + dropsGathered, 0, 480);
        //game.batch.draw(crateImage, bucket.x, bucket.y, bucket.width, bucket.height);
       // for (Rectangle raindrop : raindrops) {
        //    game.batch.draw(rockImage, raindrop.x, raindrop.y);
       // }
        stage.draw();

        game.batch.end();
        stage.act(delta);
        // process user input
        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            crate.pos.x = touchPos.x - 64 / 2;

        }
        if (Gdx.input.isKeyPressed(Keys.LEFT))
            crate.pos.x -= 200 * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Keys.RIGHT))
            crate.pos.x += 200 * Gdx.graphics.getDeltaTime();

        // make sure the bucket stays within the screen bounds
        if (crate.pos.x  < 0)
            crate.pos.x  = 0;
        if (crate.pos.x  > 800 - 64)
            crate.pos.x  = 800 - 64;

        // check if we need to create a new raindrop
        if (TimeUtils.nanoTime() - lastDropTime > 1000000000)
            spawnRaindrop();

        // move the raindrops, remove any that are beneath the bottom edge of
        // the screen or that hit the bucket. In the later case we increase the
        // value our drops counter and add a sound effect.
        Iterator<Rock> iter = rocks.iterator();
        while (iter.hasNext()) {
            Rock rock = iter.next();
            Rectangle boundingBox = rock.getBoundingBox();
            if (rock.pos.y + 64 < 0)
                rock.remove();
            else
            if (boundingBox.overlaps(crate.getBoundingBox())) {
                //dropsGathered++;
                System.out.println("bruh");
                if(rock.remove()){
                   dropsGathered++;
                }

                rockSound.play();


            }
        }
        System.out.println(stage.getActors().size);
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        // start the playback of the background music
        // when the screen is shown
        familyFriendlyMusic.play();
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        rockImage.dispose();
        crateImage.dispose();
        rockSound.dispose();
        familyFriendlyMusic.dispose();
    }

}