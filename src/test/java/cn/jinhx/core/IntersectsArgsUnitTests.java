package cn.jinhx.core;

import io.lettuce.core.codec.StringCodec;
import io.lettuce.core.protocol.CommandArgs;
import org.junit.Assert;
import org.junit.Test;

class IntersectsArgsUnitTests {
    @Test
    void shouldCircleHashResult() {
        IntersectsArgs args = IntersectsArgs.Builder.key("fleet", "key");
        CommandArgs<String, String> commandArgs = new CommandArgs<>(StringCodec.UTF8);
        Assert.assertEquals(commandArgs.toCommandString(), "");
    }
}
