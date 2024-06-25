package io.sy.data.service;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import io.sy.metadata.api.model.ObjectDefinition;
import io.sy.metadata.api.model.Record;
import io.sy.metadata.client.MetadataClient;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DataService {
    private final MongoDatabase mongoDatabase;
    private final MetadataClient metadataClient;

    @Autowired
    public DataService(
            MongoDatabase mongoDatabase,
            MetadataClient metadataClient
    ) {
        this.mongoDatabase = mongoDatabase;
        this.metadataClient = metadataClient;
    }

    public List<Record> getAllRecords(String objectName) {
        MongoCollection<Document> collection = mongoDatabase.getCollection(objectName);
        ObjectDefinition objectDefinition = metadataClient.getObjectDefinition(objectName);
        return collection
                .find()
                .into(new ArrayList<>())
                .stream()
                .map(document -> Record.fromBson(document, objectDefinition))
                .toList();
    }
}
