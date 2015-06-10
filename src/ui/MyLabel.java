package ui;

import math.MyMat3;
import math.MyRegion;
import math.MyVec3;
import renderer.MyRenderable;
import renderer.objects.MyShaderProgram;
import renderer.objects.MyVertexObject;

public class MyLabel extends MyElement implements MyRenderable {

    private String text;
    private float size; // in pixels
    private MyVec3 color;
    private float alpha;
    private float verticalSpacing, horizontalSpacing;
    private boolean verticalCentered, horizontalCentered;

    private static MyShaderProgram shaderProgram;
    private static MyVertexObject vertexObject;

    private MyFont font;

    public MyLabel(String fontPath){
        super();
        font = MyFont.addFont(fontPath);
    }

    @Override
    public void render() {
        MyMat3 vi_matrix = this.region.getProjectionMatrix();
        font.texture.bind();
        shaderProgram.bind();
        shaderProgram.setVec3("in_color", color);
        shaderProgram.setUniform1f("in_alpha", alpha);
        shaderProgram.setUniformMat3("vi_mat", vi_matrix);
        vertexObject.bind();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            shaderProgram.setRegion("in_uv_region", font.getRegion(c));
            MyMat3 md_matrix = MyMat3.getIdentity();

            md_matrix = md_matrix.translate()
        }
        vertexObject.draw();
        vertexObject.unbind();
        shaderProgram.unbind();
        font.texture.unbind();
    }
}
