package com.toplel.util.objects.shader;

import org.lwjgl.util.vector.*;

public abstract class MyShaderTypes<T> {
/*
    T value;
    final String name;
    private MyShaderType(String name){

    }

    public MyShaderType<T> addType(String type, String name){
        
        MyShaderType shaderType;

        switch (type){

            case "float": shaderType = new MyShaderType<Float>(name); break;
            case "double": shaderType = new MyShaderType<Double>(name); break;
            case "bool": shaderType = new MyShaderType<Boolean>(name); break;
            case "uint": shaderType = new MyShaderType<Uint>(name); break;
            case "int": shaderType = new MyShaderType<Integer>(name); break;

            case "bvec2": shaderType = new MyShaderType<Vector2b>(name); break;
            case "bvec3": shaderType = new MyShaderType<Vector3b>(name);
            case "bvec4": shaderType = new MyShaderType<Vector4b>(name); break;

            case "ivec2": shaderType = new MyShaderType<Vector2i>(name); break;
            case "ivec3": shaderType = new MyShaderType<Vector3i>(name); break;
            case "ivec4": shaderType = new MyShaderType<Vector4i>(name); break;

            case "uvec2": shaderType = new MyShaderType<Vector2u>(name); break;
            case "uvec3": shaderType = new MyShaderType<Vector3u>(name); break;
            case "uvec4": shaderType = new MyShaderType<Vector4u>(name); break;

            case "vec2": shaderType = new MyShaderType<Vector2f>(name); break;
            case "vec3": shaderType = new MyShaderType<Vector3f>(name); break;
            case "vec4": shaderType = new MyShaderType<Vector4f>(name); break;

            case "dvec2": shaderType = new MyShaderType<Vector2d>(name); break;
            case "dvec3": shaderType = new MyShaderType<Vector3d>(name); break;
            case "dvec4": shaderType = new MyShaderType<Vector4d>(name); break;

            case "mat2": shaderType = new MyShaderType<Matrix2f>(name); break;
            case "mat3": shaderType = new MyShaderType<Matrix3f>(name); break;
            case "mat4": shaderType = new MyShaderType<Matrix4f>(name); break;

            case "sampler2d": shaderType = Type.SAMPLER2D; break;

            default:
                System.err.println("Uniform shaderType '" + type + "' is not a legal GLSL shaderType.");
                shaderType = Type.NULL;
        }
    }

    private static class Uniform{
        final String name;
        final Type type;
        public Uniform(String name, String type){
            this.name = name;
            switch (type){

                case "float": this.type = Type.FLOAT; break;
                case "double": this.type = Type.DOUBLE; break;
                case "bool": this.type = Type.BOOL; break;
                case "uint": this.type = Type.UINT; break;
                case "int": this.type = Type.INT; break;

                case "bvec2": this.type = Type.BVEC2; break;
                case "bvec3": this.type = Type.BVEC3; break;
                case "bvec4": this.type = Type.BVEC4; break;

                case "ivec2": this.type = Type.IVEC2; break;
                case "ivec3": this.type = Type.IVEC3; break;
                case "ivec4": this.type = Type.IVEC4; break;

                case "uvec2": this.type = Type.UVEC2; break;
                case "uvec3": this.type = Type.UVEC3; break;
                case "uvec4": this.type = Type.BVEC4; break;

                case "vec2": this.type = Type.VEC2; break;
                case "vec3": this.type = Type.VEC3; break;
                case "vec4": this.type = Type.VEC4; break;

                case "dvec2": this.type = Type.DVEC2; break;
                case "dvec3": this.type = Type.DVEC3; break;
                case "dvec4": this.type = Type.DVEC4; break;

                case "mat2": this.type = Type.MAT2; break;
                case "mat3": this.type = Type.MAT3; break;
                case "mat4": this.type = Type.MAT4; break;

                case "sampler2d": this.type = Type.SAMPLER2D; break;

                default:
                    System.err.println("Uniform type '" + type + "' is not a legal GLSL type.");
                    this.type = Type.NULL;
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Uniform uniform = (Uniform) o;

            if (name != null ? !name.equals(uniform.name) : uniform.name != null) return false;
            if (type != uniform.type) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = name != null ? name.hashCode() : 0;
            result = 31 * result + (type != null ? type.hashCode() : 0);
            return result;
        }
    }


    private static enum Type{
        FLOAT, DOUBLE, BOOL, UINT, INT,
        BVEC2, BVEC3, BVEC4, // Boolean vectors
        IVEC2, IVEC3, IVEC4, // Integer vectors
        UVEC2, UVEC3, UVEC4, // Unsigned integer vectors
        VEC2, VEC3, VEC4, // Float vectors
        DVEC2, DVEC3, DVEC4, // Double vectors
        MAT2, MAT3, MAT4,
        SAMPLER2D,
        NULL
    }
*/
}
