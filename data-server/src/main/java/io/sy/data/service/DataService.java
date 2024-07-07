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
import java.util.Map;

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

    public void saveRecord(String objectName, Map<String, String> record) {
        MongoCollection<Document> collection = mongoDatabase.getCollection(objectName);
        ObjectDefinition objectDefinition = metadataClient.getObjectDefinition(objectName);
        Document document = new Document();
        record.forEach((key, value) -> {
            if (objectDefinition.getFields().stream().anyMatch(field -> field.getName().equals(key))) {
                document.append(key, value);
            }
        });
        collection.insertOne(document);
    }
}
