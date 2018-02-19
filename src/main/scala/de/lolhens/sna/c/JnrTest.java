package de.lolhens.sna.c;

import jnr.ffi.LibraryLoader;
import jnr.ffi.Pointer;
import jnr.ffi.Struct;
import jnr.ffi.types.size_t;

public class JnrTest {
    public static interface User32 {
        Pointer GetActiveWindow();

        boolean OpenClipboard(Pointer hwnd);

        boolean EmptyClipboard();

        Pointer SetClipboardData(@size_t int format, Pointer data);
    }
}
