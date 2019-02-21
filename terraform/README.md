# Terraform 


## Terraform deployment


export TF_VAR_prefix <your-user-name>
./gradlew clean uploadToS3


cd terraform
export TF_VAR_prefix <your-user-name>
terraform init
terraform workspace new $TF_VAR_prefix


terraform plan
terraform apply


