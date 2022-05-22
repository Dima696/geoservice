
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;
import org.mockito.internal.util.collections.HashCodeAndEqualsSafeSet;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;
import ru.netology.sender.MessageSender;
import ru.netology.sender.MessageSenderImpl;

import java.awt.image.Kernel;
import java.util.*;


public class MyTest {
    @Test
    void sendsOnlyRussianText () {
        GeoService geoService = Mockito.mock(GeoService.class);
        Mockito.when(geoService.byIp(Mockito.startsWith("172.")))
                .thenReturn(new Location(null, Country.RUSSIA, null, 0));


        LocalizationService localizationService = Mockito.mock(LocalizationService.class);
        Mockito.when(localizationService.locale(Country.RUSSIA))
                .thenReturn("Добро пожаловать");


        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);
        Map<String, String> header = new HashMap<String, String>();
        header.put("x-real-ip", "172.123.35.45");
        Assertions.assertEquals(messageSender.send(header), "Добро пожаловать");

    }

    @Test
    void sendsOnlyEnglishText () {
        GeoService geoService = Mockito.mock(GeoService.class);
        Mockito.when(geoService.byIp(Mockito.startsWith("96.")))
                .thenReturn(new Location(null, Country.USA, null, 0));


        LocalizationService localizationService = Mockito.mock(LocalizationService.class);
        Mockito.when(localizationService.locale(Country.USA))
                .thenReturn("Welcome");


        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);
        Map<String, String> header = new HashMap<String, String>();
        header.put("x-real-ip", "96.44.183.149");
        Assertions.assertEquals(messageSender.send(header),"Welcome");
    }

    @Test
    void sendsOnlyGermanyText () {
       GeoService geoService = Mockito.mock(GeoService.class);
        Mockito.when(geoService.byIp(Mockito.matches("^(?!(96/172)[.]).*")))
                .thenReturn(new Location(null, Country.GERMANY, null, 0));


        LocalizationService localizationService = Mockito.mock(LocalizationService.class);
        Mockito.when(localizationService.locale(Country.GERMANY))
                .thenReturn("Welcome");


        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);
        Map<String, String> header = new HashMap<String, String>();
        header.put("x-real-ip", "102.234.64.237");
        Assertions.assertEquals(messageSender.send(header), "Welcome");
    }

    @Test
    void  sendsOnlyBrazilText () {
        GeoService geoService = Mockito.mock(GeoService.class);
        Mockito.when(geoService.byIp(Mockito.matches("^(?!(96/172)[.]).*")))
                .thenReturn(new Location(null, Country.BRAZIL, null, 0));


        LocalizationService localizationService = Mockito.mock(LocalizationService.class);
        Mockito.when(localizationService.locale(Country.BRAZIL))
                .thenReturn("Welcome");


        MessageSender messageSender= new MessageSenderImpl(geoService, localizationService);
        Map<String, String> header = new HashMap<String, String>();
        header.put("x-real-ip", "156.234.64.237");
        Assertions.assertEquals(messageSender.send(header), "Welcome");
    }

}
