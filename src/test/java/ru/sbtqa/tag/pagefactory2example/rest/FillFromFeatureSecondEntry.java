package ru.sbtqa.tag.pagefactory2example.rest;

import ru.sbtqa.tag.api.EndpointEntry;
import ru.sbtqa.tag.api.Rest;
import ru.sbtqa.tag.api.annotation.Endpoint;
import ru.sbtqa.tag.api.annotation.Validation;

import static org.hamcrest.Matchers.equalTo;

@Endpoint(method = Rest.GET, path = "search/repositories", title = "найти этот проект на гитхаб через поиск")
public class FillFromFeatureSecondEntry extends EndpointEntry {

    @Validation(title = "количество полученных проектов")
    public void validate() {
        getResponse().body("total_count", equalTo(2));
    }
}
