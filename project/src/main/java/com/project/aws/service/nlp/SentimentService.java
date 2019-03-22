package com.project.aws.service.nlp;


import com.amazonaws.services.comprehend.AmazonComprehend;
import com.amazonaws.services.comprehend.model.DetectSentimentRequest;
import com.amazonaws.services.comprehend.model.DetectSentimentResult;
import com.amazonaws.services.comprehend.model.SentimentScore;
import com.amazonaws.services.comprehend.model.SentimentType;



public class SentimentService extends AwsComprehendService {




    // No ngoi no bom tin nhan ta - thay ko

    private static final String POSITIVE = SentimentType.POSITIVE.toString();


    private static final String NEGATIVE = SentimentType.NEGATIVE.toString();


    private static final String NEUTRAL = SentimentType.NEUTRAL.toString();


    private static final String MIXED = SentimentType.MIXED.toString();

    SentimentService(
            AmazonComprehend comprehendClient) {
        super(comprehendClient);
    }

    /**
     * Detect Sentiment
     *
     * @param text
     * @param languageCode
     * @param minScore
     * @return
     */
    public String detectSentiment(String text, String languageCode, float minScore) {


        DetectSentimentRequest detectSentimentRequest = new DetectSentimentRequest()
                .withText(text)
                .withLanguageCode(languageCode);


        // Validation
        if (!isLanguageCodeValid(languageCode)) {
            return null;
        }

        DetectSentimentResult detectSentimentResult = getComprehendClient().detectSentiment(detectSentimentRequest);

        String overallSentiment = detectSentimentResult.getSentiment();

        if (minScore > 0) {
            SentimentScore sentimentScore = detectSentimentResult.getSentimentScore();

            float overallSentimentScore;
            if (POSITIVE.equals(overallSentiment)) {
                overallSentimentScore = sentimentScore.getPositive();
            } else if (NEGATIVE.equals(overallSentiment)) {
                overallSentimentScore = sentimentScore.getNegative();
            } else if (NEUTRAL.equals(overallSentiment)) {
                overallSentimentScore = sentimentScore.getNeutral();
            } else  {

                overallSentimentScore = sentimentScore.getMixed();
            }
            if (overallSentimentScore < minScore) {
                return null;
            }
        }
        return overallSentiment;


    }
}
