package old.var.types;

import old.var.Variable;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Float implements Variable<java.lang.Float> {

    public float val;

    public Float(){};

    @Override
    public void setVariable(byte[] bytes) throws IllegalArgumentException{
        if(bytes.length != 4)
            throw new IllegalArgumentException("Not correct size.");
        val = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).getFloat();
    }

    @Override
    public String getName() {
        return "float";
    }

    @Override
    public java.lang.Float get() {
        return val;
    }
}
