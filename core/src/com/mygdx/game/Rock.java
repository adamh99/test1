package com.mygdx.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Rock extends Actor {
    static Texture rockTex;
    static Sound soundEffect;
    Vector2 pos;
    int h,w;
    public Rock(Vector2 position,int w, int h){
        pos = position;
        this.w = w;
        this.h = h;
    }
    @Override
    public void act(float delta){
    pos.add(0,-100f*delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
    batch.draw(rockTex,pos.x,pos.y,w,h);
    }

    public static void setTexture(Texture tex){
        rockTex = tex;
    }

    public static void setSound(Sound sound){
        soundEffect = sound;
    }

    public boolean overlaps(Rectangle boundingBox){

        return new Rectangle(pos.x,pos.y,w,h).overlaps(boundingBox);
    }

    public Rectangle getBoundingBox(){

        return new Rectangle(pos.x,pos.y,w,h);
    }

}
