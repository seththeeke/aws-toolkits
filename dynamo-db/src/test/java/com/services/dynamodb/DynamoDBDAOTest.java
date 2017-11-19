package com.services.dynamodb;

import java.util.List;

import org.aeonbits.owner.ConfigFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.services.config.AWSConfig;

@RunWith(MockitoJUnitRunner.class)
public class DynamoDBDAOTest {

	private DynamoDBDAO dynamoDb;

	@Test
	public void createTableTest() {
		AWSConfig config = ConfigFactory.create(AWSConfig.class);
		this.dynamoDb = new DynamoDBDAO(Regions.US_EAST_1, config.getAccessKey(), config.getSecretKey());
		this.dynamoDb.createTable("EventCatalog", 10L, 5L, "Id", "S");
	}

	@Test
	public void persistObjectTest() {
		AWSConfig config = ConfigFactory.create(AWSConfig.class);
		this.dynamoDb = new DynamoDBDAO(Regions.US_EAST_1, config.getAccessKey(), config.getSecretKey());
		for (int i = 0; i < 10; i++) {
			this.dynamoDb.persistObject("EventCatalog");
		}
	}

	@Test
	public void getObjectTest() {
		AWSConfig config = ConfigFactory.create(AWSConfig.class);
		this.dynamoDb = new DynamoDBDAO(Regions.US_EAST_1, config.getAccessKey(), config.getSecretKey());
		Item item = this.dynamoDb.getItem("EventCatalog");
		System.out.println(item.toJSON());
	}

	@Test
	public void getObjectsTest() {
		AWSConfig config = ConfigFactory.create(AWSConfig.class);
		this.dynamoDb = new DynamoDBDAO(Regions.US_EAST_1, config.getAccessKey(), config.getSecretKey());
		List<Item> items = this.dynamoDb.getItems("EventCatalog");
		items.stream().forEach(item -> System.out.println(item.toJSONPretty()));
	}

}
