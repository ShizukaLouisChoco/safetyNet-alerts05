package com.safetynet.alerts.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.safetynet.alerts.dao.DataStorage;
import com.safetynet.alerts.dao.impl.DataStorageImpl;
import com.safetynet.alerts.model.AllData;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;

@Log4j2
@Configuration
public class StorageConfig {

    @Bean
    public DataStorage dataStorage() throws IOException{
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        File file = new ClassPathResource("data.json").getFile();
        log.info("File address ="+ file.getAbsolutePath());
        var data = objectMapper.readValue(file, AllData.class);
        var dataStorage = new DataStorageImpl();
        dataStorage.setAllData(data);
        return dataStorage;

    }
}