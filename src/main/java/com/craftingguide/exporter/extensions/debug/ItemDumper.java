package com.craftingguide.exporter.extensions.debug;

import com.craftingguide.exporter.models.ItemModel;
import com.craftingguide.exporter.models.ModPackModel;
import com.craftingguide.util.Printer;
import java.io.IOException;

public class ItemDumper extends AbstractFileDumper {

    // AbstractFileDumper Methods //////////////////////////////////////////////////////////////////////////////////////

    protected void dump(ModPackModel modPack, Printer printer) throws IOException {
        for (ItemModel item : modPack.getAllItems()) {
            printer.line(String.format("%1$40s    %2$-40s", item.displayName, item.id));
        }
    }

    protected String getDumpDir() {
        return "./dumps/debug";
    }

    protected String getDumpFile() {
        return "items.txt";
    }
}
