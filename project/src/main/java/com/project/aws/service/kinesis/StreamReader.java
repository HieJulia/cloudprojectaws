package com.project.aws.service.kinesis;



        import java.io.UnsupportedEncodingException;
        import java.util.List;
        import java.util.stream.Collectors;

        import org.json.JSONObject;

        import com.amazonaws.auth.BasicAWSCredentials;
        import com.amazonaws.services.kinesis.*;
        import com.amazonaws.services.kinesis.model.*;
        import com.amazonaws.regions.*;

/**
 * Kinesis stream consumer App that shows received ARTIK Cloud messages
 *
 */
public class StreamReader
{
    private static final int EXPECTED_ARGUMENT_NUMBER = 8;

    private String amazonAccessKey = null;
    private String amazonSecretKey = null;
    private String amazonStreamName = null;
    private String amazonRegionName = null;


    /**
     * Read from stream
     *
     * + AWS Credential
     *
     * + AWS
     *
     *
     *
     *
     *
     * chinh vi cai su kho hieu day cua no - nay vay co tung thich khong vya
     * 
     *
     *
      */


    private void readFromStream() {

        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(amazonAccessKey, amazonSecretKey);
        @SuppressWarnings("deprecation")
        AmazonKinesisClient client = new AmazonKinesisClient(awsCredentials);
        client.setRegion(RegionUtils.getRegion(amazonRegionName));

        long recordNum = 0;
        final int INTERVAL = 2000;

        // Getting initial stream description from aws
        System.out.println(client.describeStream(amazonStreamName).toString());
        List<Shard> initialShardData = client.describeStream(amazonStreamName).getStreamDescription().getShards();
        System.out.println("\nlist of shards:");
        initialShardData.forEach(d->System.out.println(d.toString()));

        // Getting shardIterators (at beginning sequence number) for reach shard
        List<String> initialShardIterators = initialShardData.stream().map(s ->
                client.getShardIterator(new GetShardIteratorRequest()
                        .withStreamName(amazonStreamName)
                        .withShardId(s.getShardId())
                        .withStartingSequenceNumber(s.getSequenceNumberRange().getStartingSequenceNumber())
                        .withShardIteratorType(ShardIteratorType.AT_SEQUENCE_NUMBER)
                ).getShardIterator()
        ).collect(Collectors.toList());

        System.out.println("\nlist of ShardIterators:");
        initialShardIterators.forEach(i -> System.out.println(i));
        System.out.println("\nwaiting for messages....");

        // WARNING!!! Assume that only have one shard. So only use that shard
        String shardIterator = initialShardIterators.get(0);

        // Continuously read data records from a shard
        while (true) {
            // Create a new getRecordsRequest with an existing shardIterator
            // Set the maximum records to return to 25
            GetRecordsRequest getRecordsRequest = new GetRecordsRequest();
            getRecordsRequest.setShardIterator(shardIterator);
            getRecordsRequest.setLimit(25);

            GetRecordsResult recordResult = client.getRecords(getRecordsRequest);

            recordResult.getRecords().forEach(record -> {
                try {
                    String rec = new String(record.getData().array(), "UTF-8");
                    JSONObject fromKinesis = new JSONObject(rec);
                    System.out.println("\nKinesis record: " + record.toString());
                    System.out.println("ARTIK Cloud message: " + fromKinesis.toString());
                } catch (UnsupportedEncodingException e) {
                    System.out.println("Could not decode message from Kinesis stream result");
                    e.printStackTrace();
                }
            });

            recordNum += recordResult.getRecords().size();
            System.out.println("\nReceived " + recordNum +" records. sleep for " + INTERVAL/1000 +"s ...");
            try {
                Thread.sleep(INTERVAL);
            } catch (InterruptedException exception) {
                System.out.println("Receving InterruptedException. Exiting ...");
                return;
            }
            shardIterator = recordResult.getNextShardIterator();
        }

    }





}