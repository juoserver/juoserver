package net.sf.juoserver.protocol;

import net.sf.juoserver.api.Point2D;

public final class MobileUtils {

    private MobileUtils() {
    }

    public static int getDistance(Point2D location1, Point2D location2) {
        return (int) Math.hypot(location1.getX() - location2.getX(), location1.getY() - location2.getY());
    }
}
