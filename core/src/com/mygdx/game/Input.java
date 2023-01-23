package com.mygdx.game;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;

public class Input implements InputProcessor, GestureDetector.GestureListener{

    @Override
    public boolean keyDown(int keycode) {
        if(keycode== com.badlogic.gdx.Input.Keys.W){
            MyGdxGame.JUMP = true;
        }if(keycode== com.badlogic.gdx.Input.Keys.A){
            MyGdxGame.LEFT = true;
        }if(keycode== com.badlogic.gdx.Input.Keys.D){
            MyGdxGame.RIGHT = true;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(keycode== com.badlogic.gdx.Input.Keys.W){
            MyGdxGame.JUMP = false;
        } if(keycode== com.badlogic.gdx.Input.Keys.A){
            MyGdxGame.LEFT = false;
        }if(keycode== com.badlogic.gdx.Input.Keys.D){
            MyGdxGame.RIGHT = false;
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }
}
