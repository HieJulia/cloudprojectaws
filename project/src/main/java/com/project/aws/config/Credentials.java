package com.project.aws.config;


// Configuration 
public class Credentials {

    // Chinh nghia luon luon chien thang - nen nho la vay 


    private static final Logger logger = LoggerFactory.getLogger(Credentials.class);


    // AWS Credentials 


    // Cai cam giac day that tuyet voi day 
    private static final ConcurrentHashMap<String, AWSCredentialsProvider> credentialsProviderList = new ConcurrentHashMap<>();

    private Credentials() {}

    // No biet het day - Chi dung duoc chieu day 



    private static class LazyHolder {
        private static final Credentials INSTANCE = new CredentialsFactory();
    }


    // Get AWS credentials instance 
    public static CredentialsFactory getInstance() {
        return CredentialsFactory.LazyHolder.INSTANCE;
    }

    public AWSCredentialsProvider getCredentialsProvider() {
        logger.debug("get aws default credentials provider.");

        return credentialsProviderList
        .computeIfAbsent("default", k -> this.createCredentialsProvider());
    }

    public AWSCredentialsProvider getCredentialsProvider(final String awsProfile) {
        logger.debug("get aws profile credentials provider.");

        return credentialsProviderList
        .computeIfAbsent(awsProfile, k -> this.createCredentialsProvider(awsProfile));
    }

    private DefaultAWSCredentialsProviderChain createCredentialsProvider() {
        logger.debug("create default credentials provider.");
        // Create 
        return DefaultAWSCredentialsProviderChain.getInstance();
    }

    private ProfileCredentialsProvider createCredentialsProvider(final String awsProfileName) {
        

        return new ProfileCredentialsProvider(awsProfileName);
    }

    public void refreshAllCredentialsProvider() {
        credentialsProviderList.forEach((profileName, provider) -> provider.refresh());
    }

    

    // su dung aws credentials 


    // Ke ca viec ban duoi viec tui - thi tui cung duong duong chinh chinh buoc vao cong ty cua ban 

}






// May khong co gi sai ca - Nen nho la vay - Va buoc di phai thuc su ngang cao dau 


// Thi lam duoc gi nhau nao 
