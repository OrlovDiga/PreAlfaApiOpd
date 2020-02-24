package app.parser.kinopoisk;

import app.domain.Actor;
import app.domain.Photo;
import app.services.ActorService;
import app.services.PhotoService;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomAttr;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlImage;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ParserKinopoisk {
    private final String TYPE_PHOTO = ".jpg";
    private final String XPATH_SAVE_FOR_PHOTO = "src/main/resources/images/source/";
    private final String URL_WEBSITE = "https://www.kinopoisk.ru";
    private final String XPATH__NAME_ACTOR = "html/head/title";
    private final String XPATH_FIRST_PHOTO_IN_PAGE_PHOTOS = "/html/body/main/div[4]/div[1]/table/tbody/tr/td[1]/div/table/tbody/tr[4]/td/div/table/tbody/tr[1]/td[1]/a/@href";
    private final String XPATH_FOR_GET_LIST_LINK_PHOTO =  "//*[@id=\"pages\"]/a/@href";
    private final String XPATH_PHOTO = "//*[@id=\"image\"]";

    //This var need for trim string.
    // Example: <title>Джим Керри — фильмы — КиноПоиск</title>
    // But you need only "Джим Керри", => need trim string "Джим Керри — фильмы — КиноПоиск" 21 symbols  from the end.
    private final int LENGTH_SUPERFLUOUS_CHAR = 21;
    private WebClient client;


    public ParserKinopoisk() {
        this.client = new WebClient();

        client.getOptions().setJavaScriptEnabled(false);
        client.getOptions().setCssEnabled(false);
    }


    public void savePhoto(String pathPhoto, HtmlPage page, Actor actor) throws IOException {
        String path = XPATH_SAVE_FOR_PHOTO + "md " + UUID.randomUUID() + TYPE_PHOTO;

        File image = new File(path);
        HtmlImage htmlImage = page.getFirstByXPath(pathPhoto);
        System.out.println(htmlImage);
        htmlImage.saveAs(image);

        Photo photo = new Photo();
        photo.setPath(path);
        photo.setActor(actor);

        PhotoService.INSTANCE.savePhoto(photo);
    }


    public void goIntoPagePhoto(String path, Actor actor) throws IOException {
        HtmlPage page = client.getPage(URL_WEBSITE + path);
        savePhoto(XPATH_PHOTO, page, actor);
    }


    String goIntoPageActor(int numActor) throws IOException {
        HtmlPage page = client.getPage(URL_WEBSITE + "/name/" + numActor);

        HtmlElement name = page.getFirstByXPath(XPATH__NAME_ACTOR);

        return name.asText().substring(0, name.asText().length() - LENGTH_SUPERFLUOUS_CHAR);
    }


    List<String> goIntoPagePhotosActor(Actor actor, int idActor) throws IOException {
        HtmlPage page = client.getPage(URL_WEBSITE + "/name/" + idActor + "/photos/");

        // if actor haven't photos on https://www.kinopoisk.ru/
        if (page.asXml().isEmpty()) {
            return null;
        }

        DomAttr href = page.getFirstByXPath(XPATH_FIRST_PHOTO_IN_PAGE_PHOTOS);

        //go into on first photo that we to know all href for actor's photos.
        HtmlPage pageFirstPhoto = client.getPage(URL_WEBSITE + href.getValue());
        savePhoto(XPATH_PHOTO, pageFirstPhoto, actor); //save first photo

        List<DomAttr> hrefList = pageFirstPhoto.getByXPath(XPATH_FOR_GET_LIST_LINK_PHOTO);

        //return list<part url> of photo's pages (without website url)
        return hrefList.isEmpty() ? null : hrefList.stream().map(DomAttr::getValue).collect(Collectors.toList());
    }


    public void startParser(int maxNum) throws IOException {
        for (int i = 1; i < maxNum; i++) {
            String name = goIntoPageActor(i);

            Actor actor = new Actor();
            actor.setName(name);
            actor.setProfileLink(URL_WEBSITE + "/name/" + i);
            ActorService.INSTANCE.saveActor(actor);

            List<String> links = goIntoPagePhotosActor(actor, i);

            if (links != null) {
                for (String link : links) {
                    goIntoPagePhoto(link, actor);
                }
            }
        }
    }
}
