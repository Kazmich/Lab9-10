package com.clarity;

import java.io.InputStream;

import javax.faces.bean.ManagedBean;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

@ManagedBean(name="weatherService", eager=true)

public class WeatherService {
  private static final String YAHOO_APPLICATION_ID =
      "YOUR_ID_HERE";
  private static final String WEATHER_BASE_URL =
      "http://xml.weather.yahoo.com/" + "forecastrss?";
  private static final long serialVersionUID = 1L;

  public String getWeatherForZip(String zip,
      boolean isFarenheit) {
    String url =
        WEATHER_BASE_URL + "appid=" + YAHOO_APPLICATION_ID
            + "&" + "p=" + zip + "&u="
            + (isFarenheit ? "f" : "c");
    return getWeatherFromDocument(getWeatherDocument(url));
  }
  private String getWeatherFromDocument(Document document) {
    Element item =
        (Element) document.getElementsByTagName("item")
            .item(0);

    String title =
        ((Element) item.getElementsByTagName("title").item(0))
            .getFirstChild().getNodeValue();

    Element description =
        (Element) item.getElementsByTagName("description")
            .item(0);

    Element image =
        (Element) item.getElementsByTagName("img")
            .item(0);

    return "<div class='heading'>" + title + "</div>"
        + "<hr/>"
        + description.getFirstChild().getNodeValue();
  }
  private Document getWeatherDocument(String url) {
    Document document = null;
    try {
      HttpClient client = new HttpClient(); // Jakarta Commons
      GetMethod gm = new GetMethod(url);
      if (HttpServletResponse.SC_OK == client
          .executeMethod(gm)) {
        InputStream in = gm.getResponseBodyAsStream();
        document =
            DocumentBuilderFactory.newInstance()
                .newDocumentBuilder().parse(in);
      }
    }
    catch (Exception e) {
      try {
        document =
            DocumentBuilderFactory.newInstance()
                .newDocumentBuilder().parse(
                    "cannedForecast.xml");
      }
      catch (Exception e1) {
        e1.printStackTrace();
      }
    }
    return document;
  }
}
