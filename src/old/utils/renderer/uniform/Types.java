package old.utils.renderer.uniform;

import org.lwjgl.opengl.GL20;
import myrenderer.ShaderProgram;

public abstract class Types<T> {

    final String typeName;

    public Types(String typeName){
        this.typeName = typeName;
    }

    public abstract void setUniform(ShaderProgram shaderProgram, String name, T input);

    public static final Types<Float> FLOAT = new Types<Float>("float") {
        @Override
        public void setUniform(ShaderProgram shaderProgram, String name, Float input) {
            GL20.glUniform1f(shaderProgram.getUniform(name), input);
        }
    };

}
