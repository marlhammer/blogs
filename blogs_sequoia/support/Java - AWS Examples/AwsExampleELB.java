import java.io.File;
import java.util.List;
import java.util.ArrayList;

import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.DescribeInstancesRequest;
import com.amazonaws.services.ec2.model.Reservation;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.Tag;
import com.amazonaws.services.elasticloadbalancing.AmazonElasticLoadBalancingClient;
import com.amazonaws.services.elasticloadbalancing.model.DescribeLoadBalancersResult;
import com.amazonaws.services.elasticloadbalancing.model.LoadBalancerDescription;
import com.amazonaws.services.elasticloadbalancing.model.RegisterInstancesWithLoadBalancerRequest;
import com.amazonaws.services.elasticloadbalancing.model.RegisterInstancesWithLoadBalancerResult;

public class AwsExampleELB {

	public static void main(String[] args) throws Exception {
		AmazonEC2Client ec2Client = new AmazonEC2Client(new ClasspathPropertiesFileCredentialsProvider());
		ec2Client.setRegion(Region.getRegion(Regions.US_WEST_2));

		DescribeInstancesResult ec2Result = ec2Client.describeInstances();

		Instance test3Instance = null;

		for (Reservation reservation : ec2Result.getReservations()) {
			for (Instance instance : reservation.getInstances()) {

				for (Tag tag : instance.getTags()) {
					if (tag.getKey().equals("Name") && tag.getValue().equals("Test 3")) {
						test3Instance = instance;
					}
				}
			}
		}

		System.out.println("Instance ID: [" + test3Instance.getInstanceId() + "]");

		AmazonElasticLoadBalancingClient elbClient = new AmazonElasticLoadBalancingClient(new ClasspathPropertiesFileCredentialsProvider());
		elbClient.setRegion(Region.getRegion(Regions.US_WEST_2));

		DescribeLoadBalancersResult elbResult = elbClient.describeLoadBalancers();

		String elbName = null;
		for (LoadBalancerDescription elb : elbResult.getLoadBalancerDescriptions()) {
			System.out.println(elb.getLoadBalancerName());
			elbName = elb.getLoadBalancerName();
		}

		System.out.println("ELB Name: [" + elbName + "]");

		List<com.amazonaws.services.elasticloadbalancing.model.Instance> instances = new ArrayList<com.amazonaws.services.elasticloadbalancing.model.Instance>();
		instances.add(new com.amazonaws.services.elasticloadbalancing.model.Instance(test3Instance.getInstanceId()));

		RegisterInstancesWithLoadBalancerResult elbRegisterResult = elbClient.registerInstancesWithLoadBalancer(new RegisterInstancesWithLoadBalancerRequest(elbName, instances));

		System.out.println(elbRegisterResult.getInstances().size());
	}
}

