package br.com.lepsistemas.telegram.infrastructure.log;

import static com.mongodb.MongoClient.getDefaultCodecRegistry;
import static org.bson.codecs.configuration.CodecRegistries.fromCodecs;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import org.apache.logging.log4j.mongodb3.LevelCodec;
import org.apache.logging.log4j.mongodb3.MongoDbDocumentObject;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;

public class LogMongoDbFactory {
	
	private static final String CONNECTION_STRING = System.getenv("LOG_MONGODB_URI");

	public static MongoClient client() {
		PojoCodecProvider documentCodec = PojoCodecProvider.builder().register(MongoDbDocumentObject.class).build();
		CodecRegistry codecs = fromRegistries(fromCodecs(new LevelCodec()), fromProviders(documentCodec), getDefaultCodecRegistry());
		
		MongoClientOptions.Builder options = MongoClientOptions.builder().codecRegistry(codecs);
		
		MongoClientURI uri = new MongoClientURI(CONNECTION_STRING, options);
		
		return new MongoClient(uri);
	}

}
