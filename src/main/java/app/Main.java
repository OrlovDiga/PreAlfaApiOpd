package app;

import app.domain.Actor;
import app.parser.kinopoisk.ParserKinopoisk;

import java.io.IOException;
import java.util.Date;

public class Main {
    public static void main(String[] args) throws IOException {
        ParserKinopoisk parserKinopoisk = new ParserKinopoisk();
        parserKinopoisk.startParser(100);
    }
}
