package old.utils.renderer;

import renderer.ShaderProgram;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;

public interface Drawable {

    public default Sprite getSprite(){
        return null;
    }

    public default ShaderProgram getShaderProgram(){
        return null;
    }

    public default int getRenderType(){
        return GL_TRIANGLES;
    }

}
