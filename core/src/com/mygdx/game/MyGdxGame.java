package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture caveman_spritesheet;
	Animation<TextureRegion> walkAnimation;
	float stateTime;
	OrthographicCamera cam;
	static boolean JUMP = false, LEFT = false, RIGHT = false, BACK = false;

	float MAX_VELOCITY = 50f;
	World world ;
	BodyDef bodyDef;
	Body body;
	Box2DDebugRenderer debugRenderer;
	Vector2 vel;
	Vector2 pos;
	@Override
	public void create () {
		caveman_spritesheet = new Texture("spritesheet_caveman.png");
		TextureRegion[][] tmp = TextureRegion.split(new Texture("spritesheet_caveman.png"),
				caveman_spritesheet.getWidth() / 4,
				caveman_spritesheet.getHeight() / 4);

		TextureRegion[] walkFrames = new TextureRegion[4 * 4];
		int index = 0;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				walkFrames[index++] = tmp[i][j];
			}
		}
		walkAnimation = new Animation<TextureRegion>(0.025f, walkFrames);
		stateTime = 0f;


		batch = new SpriteBatch();

		cam = new OrthographicCamera();
		cam.setToOrtho(false, 400,240);

		world = new World(new Vector2(0, -200), false);
		// First we create a body definition
		bodyDef = new BodyDef();
// We set our body to dynamic, for something like ground which doesn't move we would set it to StaticBody
		bodyDef.type = BodyDef.BodyType.DynamicBody;
// Set our body's starting position in the world
		bodyDef.position.set(0, 30);

// Create our body in the world using our body definition
		body = world.createBody(bodyDef);

// Create a circle shape and set its radius to 6
		CircleShape circle = new CircleShape();
		circle.setRadius(6f);

// Create a fixture definition to apply our shape to
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = circle;
		fixtureDef.density = 0.8f;
		fixtureDef.friction = 1f;
		fixtureDef.restitution = 0.8f; // Make it bounce a little bit

// Create our fixture and attach it to the body
		Fixture fixture = body.createFixture(fixtureDef);

// Remember to dispose of any shapes after you're done with them!
// BodyDef and FixtureDef don't need disposing, but shapes do.
		circle.dispose();

		vel = body.getLinearVelocity();
		pos = body.getPosition();

		// Create our body definition
		BodyDef groundBodyDef = new BodyDef();
// Set its world position
		groundBodyDef.position.set(new Vector2(0, 10));

// Create a body from the definition and add it to the world
		Body groundBody = world.createBody(groundBodyDef);

// Create a polygon shape
		PolygonShape groundBox = new PolygonShape();
// Set the polygon shape as a box which is twice the size of our view port and 20 high
// (setAsBox takes half-width and half-height as arguments)
		groundBox.setAsBox(cam.viewportWidth, 10.0f);
// Create a fixture from our polygon shape and add it to our ground body
		groundBody.createFixture(groundBox, 0.0f);
// Clean up after ourselves
		groundBox.dispose();
		debugRenderer = new Box2DDebugRenderer();
	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 0, 0, 1);
		batch.setProjectionMatrix(cam.combined);
		stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time
		// apply left impulse, but only if max velocity is not reached yet
		if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.A) && vel.x > -MAX_VELOCITY) {
			body.applyLinearImpulse (-100000f*Gdx.graphics.getDeltaTime(), 0, pos.x, pos.y, true);
		}

// apply right impulse, but only if max velocity is not reached yet
		if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.D) && vel.x < MAX_VELOCITY) {
			body.applyLinearImpulse(100000f*Gdx.graphics.getDeltaTime(), 0, pos.x, pos.y, true);

		}

		if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.W) && vel.y < 100 && pos.y < 30) {
			body.setLinearVelocity(vel.x,0);
			body.applyForceToCenter(body.getLinearVelocity().x*3, 1000000000f*Gdx.graphics.getDeltaTime(), true);
			System.out.println(pos.y);
		}
		pos = body.getPosition();
		vel = body.getLinearVelocity();
		// Get current frame of animation for the current stateTime
		TextureRegion currentFrame = walkAnimation.getKeyFrame(stateTime, true);
		batch.begin();
		batch.draw(currentFrame, body.getPosition().x-16,body.getPosition().y-16); // Draw current frame at (50, 50)
		batch.end();
		world.step(1/60f, 6, 2);
		debugRenderer.render(world, cam.combined);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		caveman_spritesheet.dispose();
	}

}
