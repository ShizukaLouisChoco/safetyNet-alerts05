package com.safetynet.alerts.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.dao.DataStorage;
import com.safetynet.alerts.dao.impl.DataStorageImpl;
import com.safetynet.alerts.model.AllData;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.web.servlet.ResultActions;

import java.io.File;
import java.io.IOException;
import java.util.List;

public abstract class AbstractControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    private final DataStorage dataStorage = new DataStorageImpl();
    //to object method
    @SneakyThrows
    protected <T> T toObject(ResultActions resultActions, Class<T> type) {
        String contentAsString = resultActions.andReturn().getResponse().getContentAsString();
        return objectMapper.readValue(contentAsString,type);
    }

    //for jsonString
    @SneakyThrows
    protected String asJsonString(final Object obj) {
        return objectMapper.writeValueAsString(obj);
    }

    @SneakyThrows
    protected <T> List<T> toList(ResultActions resultActions, Class<T> type) {
        String contentAsString = resultActions.andReturn().getResponse().getContentAsString();
        return objectMapper.readerForListOf(type).readValue(contentAsString);
    }

    public void createDataStorage ()throws IOException {
        File file = new ClassPathResource("data.json").getFile();
        var data = objectMapper.readValue(file, AllData.class);
        this.dataStorage.setAllData(data);
    }
}