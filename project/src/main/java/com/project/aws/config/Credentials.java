//package com.project.aws.config;
//
//
//// Configuration
//public class Credentials {
//
//    // Chinh nghia luon luon chien thang - nen nho la vay
//
//
//    private static final Logger logger = LoggerFactory.getLogger(Credentials.class);
//
//
//    // AWS Credentials
//
//
//    // Cai cam giac day that tuyet voi day
//    private static final ConcurrentHashMap<String, AWSCredentialsProvider> credentialsProviderList = new ConcurrentHashMap<>();
//
//    private Credentials() {}
//
//    // No biet het day - Chi dung duoc chieu day
//
//
//
//    private static class LazyHolder {
//        private static final Credentials INSTANCE = new CredentialsFactory();
//    }
//
//
//    // Get AWS credentials instance
//    public static CredentialsFactory getInstance() {
//        return CredentialsFactory.LazyHolder.INSTANCE;
//    }
//
//    public AWSCredentialsProvider getCredentialsProvider() {
//        // Get AWS credentials provider
//
//        return credentialsProviderList
//        .computeIfAbsent("default", k -> this.createCredentialsProvider());
//    }
//
//    public AWSCredentialsProvider getCredentialsProvider(final String awsProfile) {
//        // Get credentials provider
//
//        return credentialsProviderList
//        .computeIfAbsent(awsProfile, k -> this.createCredentialsProvider(awsProfile));
//    }
//
//    private DefaultAWSCredentialsProviderChain createCredentialsProvider() {
//
//        // Con lin nay cung vai
//        // Create
//        return DefaultAWSCredentialsProviderChain.getInstance();
//    }
//
//    private ProfileCredentialsProvider createCredentialsProvider(final String awsProfileName) {
//
//
//        return new ProfileCredentialsProvider(awsProfileName);
//    }
//
//    public void refreshAllCredentialsProvider() {
//        credentialsProviderList.forEach((profileName, provider) -> provider.refresh());
//    }
//
//
//}
//
//





// May lam gi qua duoc mat no - che day het lai gium em nha - kieu deo hieu duoc

//
//
