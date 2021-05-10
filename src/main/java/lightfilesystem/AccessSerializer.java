package lightfilesystem;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class AccessSerializer extends StdSerializer<Access>  {

    public AccessSerializer() {
        super(Access.class);
    }

    @Override
    public void serialize(Access value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeString(
                switch (value) {
                    case ALL -> "ALL";
                    case READ -> "READ";
                    case WRITE -> "WRITE";
                    case NONE -> "NONE";
                }
        );
    }

}
