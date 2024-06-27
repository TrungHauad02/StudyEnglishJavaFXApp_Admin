package com.englishforadmin;

import java.net.URL;

public class GetResourceController {
    public static URL getFXMLResourcePath(String fileName) {
        GetResourceController getResourceController = new GetResourceController();
        return getResourceController.getClass().getResource(fileName);
    }
}
