package oldold.old.utils.renderer;

import oldold.renderer.objects.MyShaderProgram;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;

public interface Drawable {

    public default Sprite getSprite(){
        return null;
    }

    public default MyShaderProgram getShaderProgram(){
        return null;
    }

    public default int getRenderType(){
        return GL_TRIANGLES;
    }

}
