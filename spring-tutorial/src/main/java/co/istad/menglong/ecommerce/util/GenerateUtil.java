package co.istad.menglong.ecommerce.util;

import java.util.concurrent.ThreadLocalRandom;

public class GenerateUtil {

    public static String randomProductCode() {
        String prefix = "ISTAD-PRO-";
        int randomNumber = ThreadLocalRandom.current().nextInt(0, 100000);
        return prefix + String.format("%05d", randomNumber);
    }

}
