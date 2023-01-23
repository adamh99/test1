package com.mygdx.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Crate extends Actor {
    static Texture crateTex;

    Vector2 pos;
    int h,w;
    public Crate(){
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        batch.draw(crateTex,pos.x,pos.y,w,h);
    }

    public static void setTexture(Texture tex){
        crateTex = tex;
    }



    public Rectangle getBoundingBox(){

        return new Rectangle(pos.x,pos.y,w,h);
    }

}
