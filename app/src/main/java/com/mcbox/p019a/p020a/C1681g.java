package com.mcbox.p019a.p020a;

import java.io.File;
import java.io.FileFilter;

final class C1681g implements FileFilter {
    final /* synthetic */ String f3557a;

    C1681g(String str) {
        this.f3557a = str;
    }

    public boolean accept(File file) {
        return !file.getName().startsWith(this.f3557a);
    }
}
