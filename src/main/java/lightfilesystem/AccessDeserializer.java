package lightfilesystem;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.DeserializationContext;

public class AccessDeserializer extends StdDeserializer<Access> {

    public AccessDeserializer() {
        super(Access.class);
    }

    @Override
    public Access deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String s = p.getValueAsString();
        return switch (s) {
            case "ALL" -> Access.ALL;
            case "NONE" -> Access.NONE;
            case "READ" -> Access.READ;
            case "WRITE" -> Access.WRITE;
            default -> throw ctxt.weirdStringException(s, Access.class, "Invalid region name");
        };
    }
}
