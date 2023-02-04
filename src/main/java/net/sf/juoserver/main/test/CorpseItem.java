package net.sf.juoserver.main.test;

import net.sf.juoserver.api.Item;
import net.sf.juoserver.api.ItemVisitor;

import java.util.Random;

public class CorpseItem implements Item {
    private static final int serial = 22222;
    @Override
    public void accept(ItemVisitor itemManager) {

    }

    @Override
    public int getBaseDamage() {
        return 0;
    }

    @Override
    public int getModelId() {
        return 0x2006;
    }

    @Override
    public int getSerialId() {
        return serial;
    }

    @Override
    public int getHue() {
        return 0;
    }

    @Override
    public String getName() {
        return "Corpse";
    }
}
