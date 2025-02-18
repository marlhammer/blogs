import java.io.File;
import java.util.Map;

import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.DeleteTableRequest;
import com.amazonaws.services.dynamodbv2.model.DescribeTableRequest;
import com.amazonaws.services.dynamodbv2.model.GetItemRequest;
import com.amazonaws.services.dynamodbv2.model.GetItemResult;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
import com.amazonaws.services.dynamodbv2.model.LocalSecondaryIndex;
import com.amazonaws.services.dynamodbv2.model.Projection;
import com.amazonaws.services.dynamodbv2.model.ProjectionType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import com.amazonaws.services.dynamodbv2.model.TableDescription;
import com.amazonaws.services.dynamodbv2.model.TableStatus;

public class AwsExampleDynamoDB {
    public static void main(String[] args) throws Exception {
    	AmazonDynamoDBClient client = new AmazonDynamoDBClient(new ClasspathPropertiesFileCredentialsProvider());

		// Forget this and you will just get no results... Awesome...
		client.setRegion(Region.getRegion(Regions.US_WEST_2));

		ListTablesResult listTablesResult = client.listTables();

		System.out.println("Tables:");
		for (String tableName : listTablesResult.getTableNames()) {
			System.out.println("    " + tableName);
		}
/*
		PutItemRequest putItemRequest = new PutItemRequest()
			.addItemEntry("WeaponId", new AttributeValue("1"))
			.addItemEntry("TimePeriod", new AttributeValue("Medieval"))
			.addItemEntry("Type", new AttributeValue("Sword"))
			.addItemEntry("Length", new AttributeValue("40 inches"))
			.addItemEntry("Name", new AttributeValue("Gothic Bastard Sword"))
			.withTableName("WeaponInventory")
			;

		client.putItem(putItemRequest);

		putItemRequest = new PutItemRequest()
			.addItemEntry("WeaponId", new AttributeValue("2"))
			.addItemEntry("TimePeriod", new AttributeValue("Medieval"))
			.addItemEntry("Type", new AttributeValue("Sword"))
			.addItemEntry("Length", new AttributeValue("25 inches"))
			.addItemEntry("Name", new AttributeValue("Short Sword"))
			.withTableName("WeaponInventory")
			;

		client.putItem(putItemRequest);

		putItemRequest = new PutItemRequest()
			.addItemEntry("WeaponId", new AttributeValue("3"))
			.addItemEntry("TimePeriod", new AttributeValue("Medieval"))
			.addItemEntry("Type", new AttributeValue("Axe"))
			.addItemEntry("Length", new AttributeValue("18 inches"))
			.addItemEntry("Name", new AttributeValue("Beared Throwing Ax"))
			.withTableName("WeaponInventory")
			;

		client.putItem(putItemRequest);
*/

		GetItemResult getItemResult = client.getItem(new GetItemRequest().withTableName("WeaponInventory").addKeyEntry("WeaponId", new AttributeValue("2")));

		Map<String, AttributeValue> item = getItemResult.getItem();

		System.out.println("Item:");
		for (String key : item.keySet()) {
			System.out.println("    Key: " + key + " - Value: " + item.get(key));
		}



		QueryResult queryResult = client.query(new QueryRequest()
			.withTableName("WeaponInventory")
			.addKeyConditionsEntry("WeaponId", new Condition()
				.withAttributeValueList(new AttributeValue("1"))
				.withComparisonOperator(ComparisonOperator.EQ)
			)
		);

		System.out.println(queryResult.getItems().size());

		queryResult = client.query(new QueryRequest()
			.withTableName("WeaponInventory")
			.addKeyConditionsEntry("WeaponId", new Condition()
				.withAttributeValueList(new AttributeValue("1"))
				.withComparisonOperator(ComparisonOperator.EQ)
			)
			.addKeyConditionsEntry("Type", new Condition()
				.withAttributeValueList(new AttributeValue("Sword"))
				.withComparisonOperator(ComparisonOperator.EQ)
			)
		);

		System.out.println(queryResult.getItems().size());

		queryResult = client.query(new QueryRequest()
			.withTableName("WeaponInventory")
			.addKeyConditionsEntry("Type", new Condition()
				.withAttributeValueList(new AttributeValue("Sword"))
				.withComparisonOperator(ComparisonOperator.EQ)
			)
		);

		System.out.println(queryResult.getItems().size());

	}
}
