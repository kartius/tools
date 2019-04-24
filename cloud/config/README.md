In order to cooperate with Vault (Secrets manager server)
Link to download - https://www.vaultproject.io/downloads.html

Command to start:
$ vault server -dev -dev-root-token-id=roottoken
$ export VAULT_ADDR='http://127.0.0.1:8200'
$ vault status

$ vault secrets disable secret
$ vault secrets enable -path=secret kv

$ vault write secret/application spring.data.mongodb.password=s3cr3t
$ vault read secret/application

Do not forget about token which you should use for config server in order to get secret from Vault:
$ curl localhost:8888/application/default -H"X-Config-Token: roottoken"  

