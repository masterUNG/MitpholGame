package appewtc.masterung.metpholgame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

public class MyGdxGame extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture wallpaperTexture, cloudTexture,
			pigTexture, rainTexture, coinsTexture;
	private OrthographicCamera objOrthographicCamera;
	private BitmapFont nameBitmapFont;
	private int intCloudX = 0, intCloudY = 600;
	private boolean bolCloud = true;
	private Rectangle pigRectangle, rainRectangle, coinsRectangle;
	private Sound pigSound, dropSound;
	private Vector3 objVector3;
	private Array<Rectangle> rainArray;
	private long lastDropRain;
	private Iterator<Rectangle> rainIterator; // Iterator ==> Java.util
	private Music rainMusic, backgroundMusic;

	@Override
	public void create () {
		batch = new SpriteBatch();

		//Setup Screen
		objOrthographicCamera = new OrthographicCamera();
		objOrthographicCamera.setToOrtho(false, 1200, 800);

		//SetUp Wallpaper
		wallpaperTexture = new Texture("wallpapers_a_01_1200x800.png");

		//Setup BitmapFont
		nameBitmapFont = new BitmapFont();
		nameBitmapFont.setColor(Color.WHITE);
		nameBitmapFont.setScale(4);

		//Setup Cloud
		cloudTexture = new Texture("cloud.png");

		//Setup Pig
		pigTexture = new Texture("pig.png");

		//Setup pigRectangle
		pigRectangle = new Rectangle();
		pigRectangle.x = 568; // มาจาก 1200/2 - 64/2
		pigRectangle.y = 50;

		//Setup pigSound
		pigSound = Gdx.audio.newSound(Gdx.files.internal("pig.wav"));

		//Setup rainTexture
		rainTexture = new Texture("droplet.png");

		//Create Array of rain
		rainArray = new Array<Rectangle>();
		rainRandomDrop();

		//Setup dropSound
		dropSound = Gdx.audio.newSound(Gdx.files.internal("water_drop.wav"));

		//Setup rainMusic
		rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));

		//Setup backgroundMusic
		backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("bggame.mp3"));

		//Setup coinsTexture
		coinsTexture = new Texture("coins.png");


	}	// create เมธอดที่ทำหน้าที่ กำหนดค่าเริ่มต้นให้ object

	private void rainRandomDrop() {

		rainRectangle = new Rectangle();
		rainRectangle.x = MathUtils.random(0, 1136);	// Random from 0-> 1200-64
		rainRectangle.y = 800;
		rainRectangle.width = 64;
		rainRectangle.height = 64;

		rainArray.add(rainRectangle);
		lastDropRain = TimeUtils.nanoTime();

	}	// rainRandom

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//Setup Size
		objOrthographicCamera.update();
		batch.setProjectionMatrix(objOrthographicCamera.combined);

		//Start Drawable
		batch.begin();

		//Draw Wallpaper
		batch.draw(wallpaperTexture, 0, 0);

		//Draw Cloud
		batch.draw(cloudTexture, intCloudX, intCloudY);

		//Draw BitmapFont
		nameBitmapFont.draw(batch, "Mitrphol Game", 50, 750);

		//Draw Pig
		batch.draw(pigTexture, pigRectangle.x, pigRectangle.y);

		//Draw Rain
		for (Rectangle forRainRectangle : rainArray) {
			batch.draw(rainTexture, forRainRectangle.x, forRainRectangle.y);
		}	// for

		batch.end();

		//Move Cloud
		if (bolCloud) {

			if (intCloudX < 937) {
				intCloudX += 100 * Gdx.graphics.getDeltaTime();
			} else {
				bolCloud = !bolCloud;
			}

		} else {

			if (intCloudX > 0) {
				intCloudX -= 100 * Gdx.graphics.getDeltaTime();
			} else {
				bolCloud = !bolCloud;
			}

		} // if1

		//Active When Touch Screen
		if (Gdx.input.isTouched()) {

			//Sound Effect pig Move
			pigSound.play();

			//pig Move Left&Right
			objVector3 = new Vector3();
			objVector3.set(Gdx.input.getX(), Gdx.input.getY(), 0);

			if (objVector3.x < 600) {
				pigRectangle.x -= 10;
			} else {
				pigRectangle.x += 10;
			}

		}	// if1

		//Limit Rang of pig 0 - 1136
		if (pigRectangle.x < 0) {
			pigRectangle.x = 0;
		}
		if (pigRectangle.x > 1136) {
			pigRectangle.x = 1136;
		}

		//Start Drop Rain
		if (TimeUtils.nanoTime() - lastDropRain > 1E9) {
			rainRandomDrop();
		}

		rainIterator = rainArray.iterator();
		while (rainIterator.hasNext()) {

			Rectangle myRainRectangle = rainIterator.next();
			myRainRectangle.y -= 100 * Gdx.graphics.getDeltaTime();

			//Event rain to Floor
			if (myRainRectangle.y + 64 < 0) {

				//Sound Effect Water Drop
				dropSound.play();
				rainIterator.remove();

			}	// if

		}	// while

		//BackGround Music
		rainMusic.play();
		//backgroundMusic.setVolume(10);
		backgroundMusic.play();



	}	// rander คือเมธอดที่ ทำงานตาม loop time

}	// Main Class
