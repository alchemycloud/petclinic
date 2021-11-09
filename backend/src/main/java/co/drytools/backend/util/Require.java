package co.drytools.backend.util;

import co.drytools.backend.exception.BadRequestException;
import co.drytools.backend.exception.ExceptionType;
import co.drytools.backend.exception.UnauthorizedException;

public final class Require {

    private Require() {
        // private constructor to prevent instantiation
    }

    public static void badRequestUnless(boolean condition, ExceptionType type) {
        if (!condition) throw new BadRequestException(type);
    }

    public static void accessDeniedUnless(boolean condition, ExceptionType type) {
        if (!condition) throw new UnauthorizedException(type);
    }
}
