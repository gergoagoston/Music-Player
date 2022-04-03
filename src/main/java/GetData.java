import com.mongodb.client.*;
import org.bson.Document;

import java.util.ArrayList;
//
//public class GetData {
//    private ArrayList<ArrayList<String[]>> users;
//    public GetData() {
//        MongoClient mongoClient = MongoClients.create("mongodb://127.0.0.1:27017/?directConnection=true&serverSelectionTimeoutMS=2000&appName=mongosh+1.3.0");
//        MongoDatabase db = mongoClient.getDatabase("sampleDB");
//
//        MongoCollection<Document> collection = db.getCollection("users");
//
//        try (MongoCursor<Document> cur = collection.find().iterator()) {
//
//            while (cur.hasNext()) {
//
//                var doc = cur.next();
//                var temp = new ArrayList<>(doc.values());
//                users.add(temp);
//            }
//        }
//    }
//
//    public static void main(String[] args) {
//        GetData t = new GetData();
//    }
//}
