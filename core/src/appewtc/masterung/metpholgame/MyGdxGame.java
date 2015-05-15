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
			pigTexture, rainTexture, coinsTexture, wallpaper2Texture;
	private OrthographicCamera objOrthographicCamera;
	private BitmapFont nameBitmapFont, coinsDropBitmapFont,
			scoreBitmapFont, scoreFinalBitmapFont;
	private int intCloudX = 0, intCloudY = 600, intCoinsDrop = 0,
			intScore = 0, intFinalScore;
	private boolean bolCloud = true, bolStatus = false;
	private Rectangle pigRectangle, rainRectangle, coinsRectangle;
	private Sound pigSound, dropSound, coinsDropSound, photonSound;
	private Vector3 objVector3;
	private Array<Rectangle> rainArray, coinsArray;
	private long lastDropRain, lastDropCoins;
	private Iterator<Rectangle> rainIterator, coinsIterator; // Iterator ==> Java.util
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
		pigRectangle.y = 100;
		pigRectangle.width = 64;
		pigRectangle.height = 64;

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

		//Create coinsArray
		coinsArray = new Array<Rectangle>();
		coinsRandomDrop();

		//Setup coinsDropFont
		coinsDropBitmapFont = new BitmapFont();
		coinsDropBitmapFont.setColor(Color.WHITE);
		coinsDropBitmapFont.setScale(4);

		//Setup scoreFont
		scoreBitmapFont = new BitmapFont();
		scoreBitmapFont.setColor(Color.WHITE);
		scoreBitmapFont.setScale(4);

		//Setup coinsDropSound
		coinsDropSound = Gdx.audio.newSound(Gdx.files.internal("coins_drop.wav"));

		//Setup photonSound
		photonSound = Gdx.audio.newSound(Gdx.files.internal("phonton1.wav"));

		//Setup Wallpaper2
		wallpaper2Texture = new Texture("background1200x800.png");

		//Setup scoreFinalFont
		scoreFinalBitmapFont = new BitmapFont();
		scoreFinalBitmapFont.setColor(Color.RED);
		scoreFinalBitmapFont.setScale(4);

	}	// create เมธอดที่ทำหน้าที่ กำหนดค่าเริ่มต้นให้ object

	private void coinsRandomDrop() {

		coinsRectangle = new Rectangle();
		coinsRectangle.x = MathUtils.random(0, 1136);
		coinsRectangle.y = 800;
		coinsRectangle.width = 64;
		coinsRectangle.height = 64;

		coinsArray.add(coinsRectangle);
		lastDropCoins = TimeUtils.nanoTime();

	}	// coinsRandomDrop

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

		//Draw Coins
		for (Rectangle forCoinsRectangle : coinsArray) {
			batch.draw(coinsTexture, forCoinsRectangle.x, forCoinsRectangle.y);
		}	// for

		//Draw coinsDropFont
		coinsDropBitmapFont.draw(batch, "Coins Drop = " + Integer.toString(intCoinsDrop), 50, 100);

		//Draw scoreFont
		scoreBitmapFont.draw(batch, "Score = " + Integer.toString(intScore), 900, 100);

		//Draw When Finish Game
		if (bolStatus) {
			batch.draw(wallpaper2Texture, 0, 0);
			scoreFinalBitmapFont.draw(batch, "Score = " + Integer.toString(intFinalScore), 500, 500);
		}

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
			myRainRectangle.y -= 50 * Gdx.graphics.getDeltaTime();

			//Event rain to Floor
			if (myRainRectangle.y + 64 < 0) {

				//Sound Effect Water Drop
				dropSound.play();
				rainIterator.remove();

			}	// if

			//When OverLab With Pig
			if (myRainRectangle.overlaps(pigRectangle)) {
				intScore -= 1;
				photonSound.play();
				rainIterator.remove();

			}

		}	// while

		//Start Drop coins
		if (TimeUtils.nanoTime() - lastDropCoins > 1E9) {
			coinsRandomDrop();
		}

		coinsIterator = coinsArray.iterator();
		while (coinsIterator.hasNext()) {

			Rectangle myCoinsRectangle = coinsIterator.next();
			myCoinsRectangle.y -= 50 * Gdx.graphics.getDeltaTime();

			//When Coins into Floor
			if (myCoinsRectangle.y + 64 < 0) {
				dropSound.play();
				coinsIterator.remove();
				intCoinsDrop += 1;
				checkCoinsDrop();
			}	// if

			// When OverLap
			if (myCoinsRectangle.overlaps(pigRectangle)) {
				intScore += 1;
				coinsDropSound.play();
				coinsIterator.remove();

			}


		}	// while

		//BackGround Music
		rainMusic.play();
		//backgroundMusic.setVolume(10);
		backgroundMusic.play();


	}	// rander คือเมธอดที่ ทำงานตาม loop time

	private void checkCoinsDrop() {
		if (intCoinsDrop > 10) {
			dispose();

			if (!bolStatus) {
				intFinalScore = intScore;
			}

			bolStatus = true;

		}
	}


	@Override
	public void dispose() {
		super.dispose();
		dropSound.dispose();
		coinsDropSound.dispose();
		rainTexture.dispose();
		coinsTexture.dispose();
		cloudTexture.dispose();
		pigTexture.dispose();
		photonSound.dispose();
	}

	@Override
	public void pause() {
		super.pause();
		intCoinsDrop = 12345;
	}
}	// Main Class
