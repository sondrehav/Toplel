package old.utils.renderer.uniform;

import org.lwjgl.opengl.GL20;
import renderer.MyShaderProgram;

public abstract class Types<T> {

    final String typeName;

    public Types(String typeName){
        this.typeName = typeName;
    }

    public abstract void setUniform(MyShaderProgram myShaderProgram, String name, T input);

    public static final Types<Float> FLOAT = new Types<Float>("float") {
        @Override
        public void setUniform(MyShaderProgram myShaderProgram, String name, Float input) {
            GL20.glUniform1f(myShaderProgram.getUniform(name), input);
        }
    };

}
