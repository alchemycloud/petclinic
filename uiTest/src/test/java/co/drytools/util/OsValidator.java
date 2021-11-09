package co.drytools.util;

import java.awt.*;

public class OsValidator {

    private static final String OS = System.getProperty("os.name").toLowerCase();

    public static boolean isWindows() {

        return (OS.contains("win"));
    }

    public static boolean isMac() {

        return (OS.contains("mac"));
    }

    public static boolean isUnix() {

        return (OS.contains("nix") || OS.contains("nux") || OS.indexOf("aix") > 0);
    }

    public static boolean isSolaris() {

        return (OS.contains("sunos"));
    }

    private static boolean hasRetinaDisplay() {
        Object obj = Toolkit.getDefaultToolkit().getDesktopProperty("apple.awt.contentScaleFactor");
        if (obj instanceof Float) {
            Float f = (Float) obj;
            int scale = f.intValue();
            return (scale == 2); // 1 indicates a regular mac display.
        }
        return false;
    }
}
