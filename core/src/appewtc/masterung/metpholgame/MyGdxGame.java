package appewtc.masterung.metpholgame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class MyGdxGame extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture wallpaperTexture, cloudTexture, pigTexture;
	private OrthographicCamera objOrthographicCamera;
	private BitmapFont nameBitmapFont;
	private int intCloudX = 0, intCloudY = 600;
	private boolean bolCloud = true;
	private Rectangle pigRectangle;
	
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


	}	// create เมธอดที่ทำหน้าที่ กำหนดค่าเริ่มต้นให้ object

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



	}	// rander คือเมธอดที่ ทำงานตาม loop time

}	// Main Class
