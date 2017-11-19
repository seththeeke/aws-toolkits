package com.services.dynamodb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.BatchGetItemOutcome;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.TableKeysAndAttributes;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.CreateTableResult;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.services.AWSService;

public class DynamoDBDAO extends AWSService {

	private AmazonDynamoDB dynamoDBClient;
	private DynamoDB dynamoDb;

	public DynamoDBDAO(Regions region) {
		super(region);
	}

	public DynamoDBDAO(Regions region, String accessKey, String secretKey) {
		super(region, accessKey, secretKey);
	}

	public void createTable(String tableName, long readCapacityUnits, long writeCapacityUnits, String partitionKeyName,
			String partitionKeyType) {

		createTable(tableName, readCapacityUnits, writeCapacityUnits, partitionKeyName, partitionKeyType, null, null);
	}

	private void createTable(String tableName, long readCapacityUnits, long writeCapacityUnits, String partitionKeyName,
			String partitionKeyType, String sortKeyName, String sortKeyType) {

		try {

			ArrayList<KeySchemaElement> keySchema = new ArrayList<KeySchemaElement>();
			keySchema.add(new KeySchemaElement().withAttributeName(partitionKeyName).withKeyType(KeyType.HASH)); // Partition
																													// key

			ArrayList<AttributeDefinition> attributeDefinitions = new ArrayList<AttributeDefinition>();
			attributeDefinitions.add(
					new AttributeDefinition().withAttributeName(partitionKeyName).withAttributeType(partitionKeyType));

			if (sortKeyName != null) {
				keySchema.add(new KeySchemaElement().withAttributeName(sortKeyName).withKeyType(KeyType.RANGE)); // Sort
																													// key
				attributeDefinitions
						.add(new AttributeDefinition().withAttributeName(sortKeyName).withAttributeType(sortKeyType));
			}

			CreateTableRequest request = new CreateTableRequest().withTableName(tableName).withKeySchema(keySchema)
					.withProvisionedThroughput(new ProvisionedThroughput().withReadCapacityUnits(readCapacityUnits)
							.withWriteCapacityUnits(writeCapacityUnits));

			request.setAttributeDefinitions(attributeDefinitions);

			System.out.println("Issuing CreateTable request for " + tableName);
			CreateTableResult table = getDynamoDBClient().createTable(request);
			System.out.println("Waiting for " + tableName + " to be created...this may take a while...");

		} catch (Exception e) {
			System.err.println("CreateTable request failed for " + tableName);
			System.err.println(e.getMessage());
		}
	}

	public void persistObject(final String tableName) {
		DynamoDB dynamoDB = getDynamoDB();
		Table table = dynamoDB.getTable(tableName);

		try {

			System.out.println("Adding data to " + tableName);

			Item item = new Item().withPrimaryKey("Id", "1111").withString("type", "type")
					.withString("ISBN", "111-1111111111")
					.withStringSet("Authors", new HashSet<String>(Arrays.asList("Author1"))).withNumber("Price", 2)
					.withString("Dimensions", "8.5 x 11.0 x 0.5").withNumber("PageCount", 500)
					.withBoolean("InPublication", true).withString("ProductCategory", "Book");
			table.putItem(item);

		} catch (Exception e) {
			System.err.println("Failed to create item in " + tableName);
			System.err.println(e.getMessage());
		}
	}

	public Item getItem(final String tableName) {
		DynamoDB dynamoDB = getDynamoDB();
		Table table = dynamoDB.getTable(tableName);

		try {

			System.out.println("getting data to " + tableName);
			return table.getItem(new PrimaryKey("Id", "1111"));

		} catch (Exception e) {
			System.err.println("Failed to get item in " + tableName);
			System.err.println(e.getMessage());
		}
		return null;
	}

	public List<Item> getItems(final String table) {
		TableKeysAndAttributes forumTableKeysAndAttributes = new TableKeysAndAttributes("EventCatalog");
		// Add a partition key
		forumTableKeysAndAttributes.addHashOnlyPrimaryKeys("Id", "1111");

		System.out.println("Making the request.");

		BatchGetItemOutcome outcome = getDynamoDB().batchGetItem(forumTableKeysAndAttributes);
		List<Item> items = new ArrayList<>();
		outcome.getTableItems().keySet().stream().forEach(key -> items.addAll(outcome.getTableItems().get(key)));
		return items;
	}

	private DynamoDB getDynamoDB() {
		if (this.dynamoDb == null) {
			this.dynamoDb = new DynamoDB(getDynamoDBClient());
		}
		return this.dynamoDb;
	}

	private AmazonDynamoDB getDynamoDBClient() {
		if (this.dynamoDBClient == null) {
			if (getAccessKey() != null && getSecretKey() != null) {
				this.dynamoDBClient = AmazonDynamoDBClientBuilder.standard().withRegion(getRegion()).withCredentials(
						new AWSStaticCredentialsProvider(new BasicAWSCredentials(getAccessKey(), getSecretKey())))
						.build();
			} else {
				this.dynamoDBClient = AmazonDynamoDBClientBuilder.standard().withRegion(getRegion()).build();
			}
		}
		return this.dynamoDBClient;
	}

}
